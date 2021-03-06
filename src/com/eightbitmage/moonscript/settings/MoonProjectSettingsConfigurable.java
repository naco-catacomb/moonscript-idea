/*
 * Copyright 2010 Joachim Ansorg, mail@ansorg-it.com
 * File: LuaProjectSettingsConfigurable.java, Class: LuaProjectSettingsConfigurable
 * Last modified: 2010-03-24
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.eightbitmage.moonscript.settings;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.eightbitmage.moonscript.MoonIcons;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class MoonProjectSettingsConfigurable implements SearchableConfigurable {
    private MoonProjectSettingsPane settingsPanel;



    @Override
    @Nls
    public String getDisplayName() {
        return "Lua";
    }

    @Override
    public Icon getIcon() {
        return MoonIcons.MOON_ICON;
    }

    @Override
    public String getHelpTopic() {
        return null;
    }


    @Override
    public JComponent createComponent() {
        if (settingsPanel == null) {
            settingsPanel = new MoonProjectSettingsPane();
        }

        return settingsPanel.getPanel();
    }

    @Override
    public boolean isModified() {
        if (settingsPanel == null) {
            return false;
        }

        return settingsPanel.isModified(MoonProjectSettings.getInstance());
    }

    @Override
    public void apply() throws ConfigurationException {
        settingsPanel.storeSettings(MoonProjectSettings.getInstance());
    }

    @Override
    public void reset() {
        settingsPanel.setData(MoonProjectSettings.getInstance());
    }

    public void disposeUIResources() {
        if (settingsPanel != null) {
            this.settingsPanel.dispose();
            this.settingsPanel = null;
        }
    }

    @NotNull
    @Override
    public String getId() {
        return "moon.project.configurable";
    }

    @Override
    public Runnable enableSearch(String option) {
        return null;
    }
}