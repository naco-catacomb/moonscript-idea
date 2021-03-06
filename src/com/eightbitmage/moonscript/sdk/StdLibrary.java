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

package com.eightbitmage.moonscript.sdk;

import com.eightbitmage.moonscript.lang.psi.MoonPsiFile;
import com.eightbitmage.moonscript.util.MoonFileUtil;
import com.intellij.openapi.vfs.JarFileSystem;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.openapi.vfs.newvfs.impl.VirtualDirectoryImpl;
import com.intellij.util.PathUtil;

/**
 * Created by IntelliJ IDEA.
 * User: Jon S Akhtar
 * Date: 2/9/11
 * Time: 3:07 AM
 */
public class StdLibrary {
    public static final String STDLIBRARY    = "stdlibrary";
    public static final String DEBUG_LIBRARY = "remdebug";
    public static final String LISTING_GENERATOR = "listing";
    
    public static VirtualFile getStdFileLocation() {
        VirtualFile dir = MoonFileUtil.getPluginVirtualDirectory();
        if (dir != null) {
            dir = dir.findChild(STDLIBRARY);

            if (dir != null) return dir;
        }

        String url = VfsUtil.pathToUrl(PathUtil.getJarPathForClass(MoonPsiFile.class));
        VirtualFile sdkFile = VirtualFileManager.getInstance().findFileByUrl(url);
        if (sdkFile != null) {
            VirtualFile jarFile = JarFileSystem.getInstance().getJarRootForLocalFile(sdkFile);
            if (jarFile != null) {
                return jarFile.findChild(STDLIBRARY);
            } else if (sdkFile instanceof VirtualDirectoryImpl) {
                return sdkFile.findChild(STDLIBRARY);
            }
        }

        return null;
    }

    public static VirtualFile getDebugModuleLocation() {
        VirtualFile dir = MoonFileUtil.getPluginVirtualDirectory();

        if (dir != null) dir = dir.findChild(DEBUG_LIBRARY);

        if (dir != null) return dir;

        return null;
    }

    public static VirtualFile getListingModuleLocation() {
        VirtualFile dir = MoonFileUtil.getPluginVirtualDirectory();

        if (dir != null) dir = dir.findChild(LISTING_GENERATOR);

        if (dir != null) return dir;

        return null;
    }
}
