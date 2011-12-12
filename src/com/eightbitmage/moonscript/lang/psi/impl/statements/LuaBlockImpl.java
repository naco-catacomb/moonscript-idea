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

import com.eightbitmage.moonscript.lang.psi.controlFlow.Instruction;
import com.eightbitmage.moonscript.lang.psi.controlFlow.impl.ControlFlowBuilder;
import com.eightbitmage.moonscript.lang.psi.impl.LuaPsiElementImpl;
import com.eightbitmage.moonscript.lang.psi.statements.LuaBlock;
import com.eightbitmage.moonscript.lang.psi.statements.LuaStatementElement;
import com.eightbitmage.moonscript.lang.psi.util.LuaPsiUtils;
import com.eightbitmage.moonscript.lang.psi.visitor.LuaElementVisitor;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.util.CachedValue;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;

/**
 * Created by IntelliJ IDEA.
 * User: Jon S Akhtar
 * Date: Jun 12, 2010
 * Time: 10:17:49 PM
 */
public class LuaBlockImpl extends LuaPsiElementImpl implements LuaBlock {
    public LuaBlockImpl(ASTNode node) {
        super(node);
    }
    
    public void accept(LuaElementVisitor visitor) {
        visitor.visitBlock(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof LuaElementVisitor) {

            ((LuaElementVisitor) visitor).visitBlock(this);
        } else {
            visitor.visitElement(this);
        }
    }

    public LuaStatementElement[] getStatements() {
        return findChildrenByClass(LuaStatementElement.class);
    }

    @Override
    public LuaStatementElement[] getAllStatements() {
        return getStatements();
    }

    //    public boolean processDeclarations(@NotNull PsiScopeProcessor processor,
//                                       @NotNull ResolveState resolveState,
//                                       PsiElement lastParent,
//                                       @NotNull PsiElement place) {
//
//        PsiElement parent = place.getParent();
//        while (parent != null && !(parent instanceof LuaPsiFile)) {
//            if (parent == this) {
//                if (!processor.execute(this, resolveState)) return false;
//            }
//
//            parent = parent.getParent();
//        }
//
//        return true;
//    }

//    public boolean processDeclarations(@NotNull PsiScopeProcessor processor, @NotNull ResolveState state, PsiElement lastParent, @NotNull PsiElement place) {
//        if (lastParent != null && lastParent.getParent() == this) {
//
//        }
////        return ResolveUtil.processChildren(this, processor, state, lastParent, place);
//    }

    @Override
    public boolean processDeclarations(@NotNull PsiScopeProcessor processor, @NotNull ResolveState state, PsiElement lastParent, @NotNull PsiElement place) {
        return LuaPsiUtils.processChildDeclarations(this, processor, state, lastParent, place);
    }

    public boolean shouldChangeModificationCount(PsiElement place) {
        return true;
    }

    @Override
    public Instruction[] getControlFlow() {
        assert isValid();
        CachedValue<Instruction[]> controlFlow = getUserData(CONTROL_FLOW);
        if (controlFlow == null) {
            controlFlow = CachedValuesManager.getManager(getProject()).createCachedValue(new CachedValueProvider<Instruction[]>() {
                        @Override
                        public Result<Instruction[]> compute() {
                            return Result.create(new ControlFlowBuilder(getProject()).buildControlFlow(LuaBlockImpl.this), getContainingFile());
                        }
                    }, false);
            putUserData(CONTROL_FLOW, controlFlow);
        }

        return controlFlow.getValue();
    }

    @Override
    public LuaStatementElement addStatementBefore(@NotNull LuaStatementElement statement,
                                                  LuaStatementElement anchor) throws IncorrectOperationException {
        return (LuaStatementElement) addBefore(statement, anchor);
    }
}
