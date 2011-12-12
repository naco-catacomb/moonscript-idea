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

package com.eightbitmage.moonscript.lang.psi.impl.statements;

import com.eightbitmage.moonscript.lang.parser.LuaElementTypes;
import com.eightbitmage.moonscript.lang.psi.expressions.LuaConditionalExpression;
import com.eightbitmage.moonscript.lang.psi.expressions.LuaExpression;
import com.eightbitmage.moonscript.lang.psi.statements.LuaBlock;
import com.eightbitmage.moonscript.lang.psi.statements.LuaIfThenStatement;
import com.eightbitmage.moonscript.lang.psi.visitor.LuaElementVisitor;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: Jon S Akhtar
 * Date: Jun 10, 2010
 * Time: 10:40:55 AM
 */
public class LuaIfThenStatementImpl extends LuaStatementElementImpl implements LuaIfThenStatement {

    public LuaIfThenStatementImpl(ASTNode node) {
        super(node);
    }

    public void accept(LuaElementVisitor visitor) {
      visitor.visitIfThenStatement(this);
    }
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof LuaElementVisitor) {
            ((LuaElementVisitor) visitor).visitIfThenStatement(this);
        } else {
            visitor.visitElement(this);
        }
    }

    @Override
    public LuaExpression getIfCondition() {
        return findChildByClass(LuaConditionalExpression.class);
    }

    @Override
    public LuaExpression[] getElseIfConditions() {
        return findChildrenByClass(LuaConditionalExpression.class);        
    }

    @Override
    public LuaBlock getIfBlock() {
        return findChildrenByClass(LuaBlock.class)[0];
    }

    @Override
    public LuaBlock[] getElseIfBlocks() {
        LuaBlock[] b = findChildrenByClass(LuaBlock.class);
        return Arrays.copyOfRange(b, 1, getElseBlock()==null? b.length : b.length-1);
    }

    @Override
    public LuaBlock getElseBlock() {
        if (findChildByType(LuaElementTypes.ELSE) != null) {
            LuaBlock[] b = findChildrenByClass(LuaBlock.class);
            return b[b.length-1];
        }
        return null;
    }
}