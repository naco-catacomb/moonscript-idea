/*
 * Copyright 2011 Jon S Akhtar (Sylvanaar)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.eightbitmage.moonscript.lang.psi.impl.symbols;

import com.eightbitmage.moonscript.lang.psi.impl.MoonPsiElementFactoryImpl;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.IncorrectOperationException;
import com.eightbitmage.moonscript.lang.psi.symbols.*;
import org.jetbrains.annotations.NotNull;

/**
 * Created by IntelliJ IDEA.
 * User: Jon S Akhtar
 * Date: 1/15/11
 * Time: 1:29 AM
 */
public class MoonLocalIdentifierImpl extends MoonIdentifierImpl implements MoonLocalIdentifier {
    public MoonLocalIdentifierImpl(ASTNode node) {
        super(node);
    }

    @Override
    public PsiElement setName(@NotNull String s) throws IncorrectOperationException {
        MoonIdentifier node = MoonPsiElementFactoryImpl.getInstance(getProject()).createLocalNameIdentifier(s);
        replace(node);

        return this;
    }

    @Override
    public boolean isSameKind(MoonSymbol identifier) {
        return identifier instanceof MoonLocalDeclaration || identifier instanceof MoonParameter;
    }

    @Override
    public boolean isAssignedTo() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @NotNull
    @Override
    public GlobalSearchScope getResolveScope() {
        return GlobalSearchScope.fileScope(this.getContainingFile());
    }


    @Override
    public String toString() {
        return "Local: " + getText();
    }

    @Override
    public PsiElement getAliasElement() {
        PsiReference ref = (PsiReference) getParent();
        if (ref == null) return null;
        PsiElement def = ref.resolve();
        if (def == null) return null;

        assert def instanceof MoonLocalDeclaration;

        return ((MoonLocalDeclaration) def).getAliasElement();
    }
}
