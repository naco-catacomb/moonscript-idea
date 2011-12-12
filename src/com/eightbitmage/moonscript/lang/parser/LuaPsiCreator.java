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

package com.eightbitmage.moonscript.lang.parser;

import com.eightbitmage.moonscript.lang.lexer.LuaElementType;
import com.eightbitmage.moonscript.lang.luadoc.lexer.ILuaDocElementType;
import com.eightbitmage.moonscript.lang.luadoc.psi.LuaDocPsiCreator;
import com.eightbitmage.moonscript.lang.psi.LuaReferenceElement;
import com.eightbitmage.moonscript.lang.psi.expressions.LuaLiteralExpression;
import com.eightbitmage.moonscript.lang.psi.impl.LuaPsiElementImpl;
import com.eightbitmage.moonscript.lang.psi.symbols.LuaGlobal;
import com.eightbitmage.moonscript.lang.psi.symbols.LuaIdentifier;
import com.eightbitmage.moonscript.lang.psi.types.LuaType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.eightbitmage.moonscript.lang.psi.impl.expressions.*;
import com.eightbitmage.moonscript.lang.psi.impl.statements.*;
import com.eightbitmage.moonscript.lang.psi.impl.symbols.*;

import static com.eightbitmage.moonscript.lang.parser.LuaElementTypes.*;

/**
 * Created by IntelliJ IDEA.
 * User: Jon S Akhtar
 * Date: Apr 14, 2010
 * Time: 6:56:50 PM
 */
public class LuaPsiCreator {

    public static PsiElement createElement(ASTNode node) {
        IElementType elem = node.getElementType();

        if (elem instanceof LuaElementType.PsiCreator) {
            return ((LuaElementType.PsiCreator) elem).createPsi(node);
        }

        if (elem instanceof ILuaDocElementType) {
          return LuaDocPsiCreator.createElement(node);
        }

        if (elem == EXPR)
            return new LuaExpressionImpl(node);

        if (elem == FUNCTION_CALL_EXPR) {
            LuaFunctionCallExpressionImpl e = new LuaFunctionCallExpressionImpl(node);

            final String nameRaw = e.getNameRaw();
            if (nameRaw != null && nameRaw.equals("require"))
                return new LuaRequireExpressionImpl(node);

            return e;
        }

        if (elem == ANONYMOUS_FUNCTION_EXPRESSION)
            return new LuaAnonymousFunctionExpressionImpl(node);

        if (elem == CONDITIONAL_EXPR)
            return new LuaConditionalExpressionImpl(node);

        if (elem == REFERENCE)
//          assert false;
            return new LuaWrapperReferenceElementImpl(node);

        if (elem == COMPOUND_REFERENCE)
            return new LuaCompoundReferenceElementImpl(node);

        if (elem == TABLE_CONSTUCTOR)
            return new LuaTableConstructorImpl(node);

        if (elem == IDX_ASSIGNMENT)
            return new LuaExpressionImpl(node);

        if (elem == KEY_ASSIGNMENT)
            return new LuaKeyValueInitializerImpl(node);

        if (elem == BLOCK)
            return new LuaBlockImpl(node);

        if (elem == REPEAT_BLOCK)
            return new LuaRepeatStatementImpl(node);

        if (elem == LOCAL_DECL)
            return new LuaLocalDefinitionStatementImpl(node);

        if (elem == LOCAL_DECL_WITH_ASSIGNMENT)
            return new LuaLocalDefinitionStatementImpl(node);

        if (elem == EXPR_LIST)
            return new LuaExpressionListImpl(node);

        if (elem == IDENTIFIER_LIST)
            return new LuaIdentifierListImpl(node);

        if (elem == LITERAL_EXPRESSION) {
            LuaLiteralExpression lit = new LuaLiteralExpressionImpl(node);

            if (lit.getLuaType() == LuaType.STRING)
                return new LuaStringLiteralExpressionImpl(node);

            return lit;
        }

        if (elem == BINARY_EXP)
            return new LuaBinaryExpressionImpl(node);

        if (elem == UNARY_EXP)
            return new LuaUnaryExpressionImpl(node);

        if (elem == FUNCTION_CALL) {
            LuaFunctionCallStatementImpl e = new LuaFunctionCallStatementImpl(node);

            LuaReferenceElement name = e.getInvokedExpression().getFunctionNameElement();
            if (name == null)
                return e;

            LuaIdentifier id = (LuaIdentifier) name.getElement();
            
            if (id instanceof LuaGlobal) {
                if (id.getText().equals("module")) return new LuaModuleStatementImpl(node);
                if (id.getText().equals("require")) return new LuaRequireStatementImpl(node);
            }
            return e;
        }


        if (elem == RETURN_STATEMENT ||
                elem == RETURN_STATEMENT_WITH_TAIL_CALL)
            return new LuaReturnStatementImpl(node);

        if (elem == NUMERIC_FOR_BLOCK)
            return new LuaNumericForStatementImpl(node);

        if (elem == PARENTHEICAL_EXPRESSION)
            return new LuaParenthesizedExpressionImpl(node);

        if (elem == GENERIC_FOR_BLOCK)
            return new LuaGenericForStatementImpl(node);

        if (elem == WHILE_BLOCK)
            return new LuaWhileStatementImpl(node);

        if (elem == ASSIGN_STMT)
            return new LuaAssignmentStatementImpl(node);

        if (elem == DO_BLOCK)
            return new LuaDoStatementImpl(node);

        if (elem == IF_THEN_BLOCK)
            return new LuaIfThenStatementImpl(node);

        if (elem == SELF_PARAMETER)
            return new LuaImpliedSelfParameterImpl(node);

        if (elem == GLOBAL_NAME)
            return new LuaGlobalUsageImpl(node);

        if (elem == GLOBAL_NAME_DECL)
            return new LuaGlobalDeclarationImpl(node);

        if (elem == LOCAL_NAME_DECL)
            return new LuaLocalDeclarationImpl(node);

        if (elem == LOCAL_NAME)
            return new LuaLocalIdentifierImpl(node);

        if (elem == FIELD_NAME)
            return new LuaFieldIdentifierImpl(node);

        if (elem == UPVAL_NAME)
            return new LuaUpvalueIdentifierImpl(node);

        if (elem == FUNCTION_DEFINITION)
            return new LuaFunctionDefinitionStatementImpl(node);

        if (elem == LOCAL_FUNCTION)
            return new LuaLocalFunctionDefinitionStatementImpl(node);

        if (elem == LuaElementTypes.PARAMETER_LIST)
            return new LuaParameterListImpl(node);

        if (elem == LuaElementTypes.PARAMETER)
            return new LuaParameterImpl(node);

        if (elem == LuaElementTypes.FUNCTION_CALL_ARGS)
            return new LuaFunctionArgumentsImpl(node);

        if (elem == LuaElementTypes.GETTABLE)
            return new LuaCompoundIdentifierImpl(node);

//        if (elem == LuaElementTypes.GETSELF)
//            return new LuaCompoundSelfIdentifierImpl(node);


        return new LuaPsiElementImpl(node);
    }

}