/*
 * Copyright 2010 Jon S Akhtar (Sylvanaar)
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
package com.eightbitmage.moonscript.findUsages;

import com.eightbitmage.moonscript.lang.lexer.MoonLexer;
import com.eightbitmage.moonscript.lang.lexer.MoonTokenTypes;
import com.eightbitmage.moonscript.lang.psi.MoonNamedElement;
import com.intellij.lang.cacheBuilder.DefaultWordsScanner;
import com.intellij.lang.cacheBuilder.WordsScanner;
import com.intellij.lang.findUsages.FindUsagesProvider;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * @author ven
 */
public class MoonFindUsagesProvider implements FindUsagesProvider {
        @NotNull
        public WordsScanner getWordsScanner() {
            return  new DefaultWordsScanner(new MoonLexer(),
                MoonTokenTypes.IDENTIFIERS_SET, MoonTokenTypes.COMMENT_SET, MoonTokenTypes.LITERALS_SET) {{
                setMayHaveFileRefsInLiterals(true);
            }};
        }

        public boolean canFindUsagesFor(@NotNull final PsiElement psiElement) {
            return psiElement instanceof MoonNamedElement;
        }

        @Nullable
        public String getHelpId(@NotNull final PsiElement psiElement) {
            return null;
        }

        @NotNull
        public String getType(@NotNull final PsiElement element) {
            return "identifier";
        }

        @NotNull
        public String getDescriptiveName(@NotNull final PsiElement element) {
            return getName(element);
        }

        @NotNull
        public String getNodeText(@NotNull final PsiElement element, final boolean useFullName) {
            final StringBuilder sb = new StringBuilder(getType(element));
            if (sb.length() > 0) {
                sb.append(" ");
            }

            sb.append(getName(element));
            return sb.toString();
        }

        @NotNull
        private String getName(@NotNull final PsiElement element) {
            if (element instanceof MoonNamedElement) {
                return StringUtil.notNullize(((MoonNamedElement) element).getName());
            }
            return "";
        }
}