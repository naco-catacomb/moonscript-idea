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

package com.eightbitmage.moonscript.lang.psi.stubs;

import com.eightbitmage.moonscript.MoonFileType;
import com.eightbitmage.moonscript.lang.psi.MoonPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubSerializer;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * Created by IntelliJ IDEA.
 * User: Jon S Akhtar
 * Date: 1/23/11
 * Time: 8:19 PM
 */
public abstract class MoonStubElementType<S extends StubElement, T extends MoonPsiElement>
        extends IStubElementType<S, T>  implements StubSerializer<S> {

    public MoonStubElementType(@NonNls @NotNull String debugName) {
        super(debugName, MoonFileType.MOON_LANGUAGE);
    }

    public abstract PsiElement createElement(final ASTNode node);
//
//    public String getExternalId() {
//        return "lua." + super.toString();
//    }

}
