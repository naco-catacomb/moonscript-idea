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

package com.eightbitmage.moonscript.library;

import com.intellij.facet.ui.libraries.LibraryInfo;
import com.intellij.framework.library.DownloadableLibraryType;
import com.intellij.ide.util.frameworkSupport.CustomLibraryDescriptionImpl;
import org.jetbrains.annotations.NotNull;

/**
 * Created by IntelliJ IDEA.
 * User: Jon S Akhtar
 * Date: 4/21/11
 * Time: 8:23 PM
 */
public class MoonLibraryDescription extends CustomLibraryDescriptionImpl {

    public MoonLibraryDescription(@NotNull DownloadableLibraryType downloadableLibraryType) {
        super(downloadableLibraryType);    //To change body of overridden methods use File | Settings | File Templates.
    }

//    public MoonLibraryDescription(@org.jetbrains.annotations.NotNull LibraryInfo[] libraryInfos,
//                                  @org.jetbrains.annotations.NotNull String defaultLibraryName) {
//        super(libraryInfos, defaultLibraryName);
//    }
}
