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

package com.eightbitmage.moonscript.run;

import com.intellij.ide.util.BrowseFilesListener;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.ui.RawCommandLineEditor;

import javax.swing.*;
import java.awt.*;

/**
 * The configuration user interface to configure a new Lua run configuration.
 * <p/>
 * User: jansorg
 * Date: 10.07.2009
 * Time: 21:30:48
 */
public class MoonRunConfigurationForm implements MoonRunConfigurationParams {
    private JPanel rootPanel;
    private TextFieldWithBrowseButton scriptNameEdit;
    private RawCommandLineEditor commandLineEdit;
    private JPanel commonOptionsPlaceholder;
    private MoonCommonOptionsForm commonOptionsForm=null;
    private MoonRunConfiguration myMoonRunConfiguration;

    public MoonRunConfigurationForm(MoonRunConfiguration moonRunConfiguration) {
        this.myMoonRunConfiguration = moonRunConfiguration;

        assert myMoonRunConfiguration != null;

        try {
        commonOptionsForm = new MoonCommonOptionsForm(myMoonRunConfiguration);
        commonOptionsPlaceholder.add(commonOptionsForm.getRootPanel(), BorderLayout.CENTER);
        } catch (Throwable unused) {}
        scriptNameEdit.addBrowseFolderListener("Select script", "", myMoonRunConfiguration.getProject(), BrowseFilesListener.SINGLE_FILE_DESCRIPTOR);
    }

    public CommonMoonRunConfigurationParams getCommonParams() {
        return commonOptionsForm;
    }

    public String getScriptName() {
        return scriptNameEdit.getText();
    }

    public void setScriptName(String scriptName) {
        this.scriptNameEdit.setText(scriptName);
    }

    public String getScriptParameters() {
        return commandLineEdit.getText();
    }

    public void setScriptParameters(String scriptParameters) {
        commandLineEdit.setText(scriptParameters);
    }

    public JComponent getRootPanel() {
        return rootPanel;
    }
}
