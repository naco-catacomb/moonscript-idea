/*
 * Copyright 2000-2009 JetBrains s.r.o.
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
package com.eightbitmage.moonscript.intentions.control;

import com.eightbitmage.moonscript.intentions.MoonIntentionsBundle;
import com.eightbitmage.moonscript.lang.lexer.MoonTokenTypes;
import com.eightbitmage.moonscript.lang.psi.expressions.MoonBinaryExpression;
import com.eightbitmage.moonscript.lang.psi.expressions.MoonExpression;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.IncorrectOperationException;
import com.eightbitmage.moonscript.intentions.base.IntentionUtils;
import com.eightbitmage.moonscript.intentions.base.MutablyNamedIntention;
import com.eightbitmage.moonscript.intentions.base.PsiElementPredicate;
import org.jetbrains.annotations.NotNull;


public class FlipConjunctionIntention extends MutablyNamedIntention {
  protected String getTextForElement(PsiElement element) {
    final MoonBinaryExpression binaryExpression =
        (MoonBinaryExpression) element;
    final IElementType tokenType = binaryExpression.getOperationTokenType();
    final String conjunction;
    assert tokenType != null;
    if (tokenType.equals(MoonTokenTypes.AND)) {
      conjunction = MoonTokenTypes.AND.toString();
    } else {
      conjunction =  MoonTokenTypes.OR.toString();
    }
    return MoonIntentionsBundle.message("flip.conjunction.intention.name", conjunction);
  }

  @NotNull
  public PsiElementPredicate getElementPredicate() {
    return new ConjunctionPredicate();
  }

  public void processIntention(@NotNull PsiElement element)
      throws IncorrectOperationException {
    final MoonBinaryExpression exp =
        (MoonBinaryExpression) element;
    final IElementType tokenType = exp.getOperationTokenType();

    final MoonExpression lhs = exp.getLeftOperand();
    final String lhsText = lhs.getText();

    final MoonExpression rhs = exp.getRightOperand();
    final String rhsText = rhs.getText();

    final String conjunction;
    if (tokenType.equals(MoonTokenTypes.AND)) {
      conjunction = MoonTokenTypes.AND.toString();
    } else {
      conjunction = MoonTokenTypes.OR.toString();
    }

    final String newExpression =
        rhsText + conjunction + lhsText;
    IntentionUtils.replaceExpression(newExpression, exp);
  }

}
