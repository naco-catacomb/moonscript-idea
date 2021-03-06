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

package com.eightbitmage.moonscript.lang.psi.stubs.impl;

import com.eightbitmage.moonscript.lang.parser.MoonElementTypes;
import com.eightbitmage.moonscript.lang.psi.expressions.MoonFieldIdentifier;
import com.intellij.psi.stubs.NamedStubBase;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;

/**
 * Created by IntelliJ IDEA.
 * User: Jon S Akhtar
 * Date: 4/3/11
 * Time: 4:51 AM
 */
public class MoonFieldStub extends NamedStubBase<MoonFieldIdentifier> {
    public MoonFieldStub(StubElement parent, StringRef name) {
        super(parent, MoonElementTypes.FIELD_NAME, name);
    }
}
