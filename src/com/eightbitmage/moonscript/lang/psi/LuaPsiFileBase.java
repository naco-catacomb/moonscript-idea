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

package com.eightbitmage.moonscript.lang.psi;

import com.eightbitmage.moonscript.lang.psi.expressions.LuaDeclarationExpression;
import com.eightbitmage.moonscript.lang.psi.statements.LuaBlock;
import com.eightbitmage.moonscript.lang.psi.util.LuaStatementOwner;
import com.eightbitmage.moonscript.lang.psi.util.LuaVariableDeclarationOwner;
import com.intellij.psi.PsiFile;

/**
 * Created by IntelliJ IDEA.
 * User: Jon S Akhtar
 * Date: Jun 13, 2010
 * Time: 7:28:26 PM
 */
public interface LuaPsiFileBase extends PsiFile, LuaVariableDeclarationOwner, LuaStatementOwner, LuaPsiElement, LuaBlock {

//  LuaStatementElement[] getStatements();

  LuaFunctionDefinition[] getFunctionDefs();

  LuaDeclarationExpression[] getSymbolDefs();
}
