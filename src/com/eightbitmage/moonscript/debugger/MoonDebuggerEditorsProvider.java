/*
 * Copyright 2011 Jon S Akhtar (Sylvanaar)
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.eightbitmage.moonscript.debugger;

import com.eightbitmage.moonscript.MoonFileType;
import com.eightbitmage.moonscript.lang.lexer.MoonElementType;
import com.eightbitmage.moonscript.lang.psi.MoonExpressionCodeFragment;
import com.eightbitmage.moonscript.lang.psi.MoonPsiElement;
import com.eightbitmage.moonscript.lang.psi.MoonPsiElementFactory;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.xdebugger.XSourcePosition;
import com.intellij.xdebugger.evaluation.XDebuggerEditorsProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by IntelliJ IDEA.
 * User: Jon S Akhtar
 * Date: 3/22/11
 * Time: 7:49 AM
 */
public class MoonDebuggerEditorsProvider extends XDebuggerEditorsProvider {
    private static final Logger log = Logger.getInstance("Lua.MoonDebuggerEditorsProvider");

    @NotNull
    @Override
    public FileType getFileType() {
        return MoonFileType.MOON_FILE_TYPE;
    }

    @NotNull
    @Override
    public Document createDocument(@NotNull Project project, @NotNull String text,
                                   @Nullable XSourcePosition sourcePosition) {

        log.debug("createDocument  " + text);

        VirtualFile contextVirtualFile = sourcePosition == null ? null : sourcePosition.getFile();
        MoonPsiElement context = null;
        int contextOffset = sourcePosition == null ? -1 : sourcePosition.getOffset();
        if (contextVirtualFile != null) context = getContextElement(contextVirtualFile, contextOffset, project);
        MoonExpressionCodeFragment codeFragment = MoonPsiElementFactory.getInstance(project)
                .createExpressionCodeFragment(text, context, true);

        assert codeFragment != null;
        
        Document file = PsiDocumentManager.getInstance(project).getDocument(codeFragment);

        if (file == null) {

        }

        return file;
    }

    public static MoonPsiElement getContextElement(VirtualFile virtualFile, int offset, Project project) {
        log.debug("getContextElement " + virtualFile.getUrl() + "  " + offset);

        if (!virtualFile.isValid()) return null;
        Document document = FileDocumentManager.getInstance().getDocument(virtualFile);
        if (document == null) return null;
        FileViewProvider viewProvider = PsiManager.getInstance(project).findViewProvider(virtualFile);
        if (viewProvider == null) return null;
        PsiFile file = viewProvider.getPsi(MoonFileType.MOON_LANGUAGE);
        if (file == null) return null;
        int lineEndOffset = document.getLineEndOffset(document.getLineNumber(offset));
        do {
            PsiElement element = file.findElementAt(offset);
            if (element != null) {
                if (!(element instanceof PsiWhiteSpace) && !(element instanceof PsiComment) && (element.getNode()
                        .getElementType() instanceof MoonElementType))
                    return (MoonPsiElement) element.getContext();
                offset = element.getTextRange().getEndOffset() + 1;
            } else {
                return null;
            }
        } while (offset < lineEndOffset);
        return null;
    }
}
