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

package com.eightbitmage.moonscript.lang.psi.impl.expressions;

import com.eightbitmage.moonscript.lang.lexer.LuaTokenTypes;
import com.eightbitmage.moonscript.lang.luadoc.psi.api.LuaDocComment;
import com.eightbitmage.moonscript.lang.luadoc.psi.impl.LuaDocCommentUtil;
import com.eightbitmage.moonscript.lang.parser.LuaElementTypes;
import com.eightbitmage.moonscript.lang.psi.expressions.LuaExpression;
import com.eightbitmage.moonscript.lang.psi.expressions.LuaExpressionList;
import com.eightbitmage.moonscript.lang.psi.expressions.LuaTableConstructor;
import com.eightbitmage.moonscript.lang.psi.statements.LuaAssignmentStatement;
import com.eightbitmage.moonscript.lang.psi.util.LuaAssignment;
import com.eightbitmage.moonscript.lang.psi.visitor.LuaElementVisitor;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.tree.TokenSet;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

/**
 * Created by IntelliJ IDEA.
 * User: Jon S Akhtar
 * Date: Jun 16, 2010
 * Time: 10:43:51 PM
 */
public class LuaTableConstructorImpl extends LuaExpressionListImpl implements LuaTableConstructor {
    TokenSet BRACES = TokenSet.create(LuaTokenTypes.LCURLY, LuaTokenTypes.RCURLY);
    TokenSet INITS = TokenSet.create(LuaElementTypes.KEY_ASSIGNMENT, LuaElementTypes.IDX_ASSIGNMENT);
    
    public LuaTableConstructorImpl(ASTNode node) {
        super(node);
    }

    @Override
    public String toString() {
        return "Table Constructor (Field Count " + count() + ")";
    }

    public LuaExpression[] getInitializers() {
        return findChildrenByClass(LuaExpression.class);
    }

        @Override
    public void accept(LuaElementVisitor visitor) {
        visitor.visitTableConstructor(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof LuaElementVisitor) {
            ((LuaElementVisitor) visitor).visitTableConstructor(this);
        } else {
            visitor.visitElement(this);
        }
    }

    @Override
    public LuaDocComment getDocComment() {
        return LuaDocCommentUtil.findDocComment(this);
    }

    @Override
    public boolean isDeprecated() {
        return false;
    }

    @Override
    public String getName() {
        LuaExpressionList exprlist = PsiTreeUtil.getParentOfType(this, LuaExpressionList.class);
        if (exprlist == null) return null;

        PsiElement assignment = exprlist.getParent();

        if (assignment instanceof LuaAssignmentStatement)
            for(LuaAssignment assign : ((LuaAssignmentStatement) assignment).getAssignments())
                if (assign.getValue() == this)
                    return assign.getSymbol().getName();

        return null;
    }
}