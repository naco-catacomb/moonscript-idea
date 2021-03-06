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

package com.eightbitmage.moonscript.editor.highlighter;

import com.eightbitmage.moonscript.lang.lexer.MoonTokenTypes;
import com.intellij.codeInsight.editorActions.QuoteHandler;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.highlighter.HighlighterIterator;
import com.intellij.psi.tree.IElementType;

/**
 * Created by IntelliJ IDEA.
 * User: Jon S Akhtar
 * Date: Aug 1, 2010
 * Time: 11:52:14 PM
 */
public class MoonQuoteHandler implements QuoteHandler {
    @Override
    public boolean isClosingQuote(HighlighterIterator highlighterIterator, int i) {
        IElementType type = highlighterIterator.getTokenType();

        if (type == MoonTokenTypes.LONGSTRING_END)
            return true;

        if (type == MoonTokenTypes.STRING) {
            int j = highlighterIterator.getStart();
            int k = highlighterIterator.getEnd();
            return k - j >= 1 && i == k - 1;
        }
        return false;
    }

    @Override
    public boolean isOpeningQuote(HighlighterIterator highlighterIterator, int i) {
        IElementType type = highlighterIterator.getTokenType();

        if (type == MoonTokenTypes.LONGSTRING_BEGIN)
            return true;

        if (type == MoonTokenTypes.STRING) {
            int j = highlighterIterator.getStart();
            return i == j;
        }
        
        return false;
    }

    @Override
    public boolean hasNonClosedLiteral(Editor editor, HighlighterIterator highlighterIterator, int i) {
        return true;  
    }

    @Override
    public boolean isInsideLiteral(HighlighterIterator highlighterIterator) {
        IElementType type = highlighterIterator.getTokenType();

        if (type == MoonTokenTypes.LONGSTRING)
            return true;

        if (type == MoonTokenTypes.STRING) {
            return true;
        }

        return false;
    }
}

