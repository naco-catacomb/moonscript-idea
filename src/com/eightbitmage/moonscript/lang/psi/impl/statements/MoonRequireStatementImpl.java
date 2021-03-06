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

package com.eightbitmage.moonscript.lang.psi.impl.statements;

import com.eightbitmage.moonscript.lang.psi.statements.MoonRequireStatement;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiNamedElement;


/**
 * Created by IntelliJ IDEA.
 * User: Jon S Akhtar
 * Date: 3/7/11
 * Time: 11:23 AM
 */
public class MoonRequireStatementImpl extends MoonFunctionCallStatementImpl implements MoonRequireStatement {
    public MoonRequireStatementImpl(ASTNode node) {
        super(node);
    }

    @Override
    public String toString() {
        PsiNamedElement e = (PsiNamedElement) getFirstChild();
        String name = e == null?"null":e.getName();
        return "Require Stmt: " + StringUtil.notNullize(name);
    }


}
