package com.eightbitmage.moonscript.options;

import com.eightbitmage.moonscript.MoonBundle;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.PathManager;
import com.intellij.openapi.components.ExportableApplicationComponent;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * Options of MoonScript.
 */
@State(
        name = "MoonSettings",
        storages = {
                @Storage(id = "moon", file = "$APP_CONFIG$/moon.xml")
        }
)

public final class MoonSettings implements PersistentStateComponent<MoonSettings>, ExportableApplicationComponent {

    @NonNls
    static final String DEFAULT_OPTIONS = "";

    public static MoonSettings getInstance() {
        return ApplicationManager.getApplication().getComponent(MoonSettings.class);
    }

    public boolean ENABLED_COMPILATION = true;
    public String MOON_HOME = getDefaultMoonScriptHome();
    public String COMMAND_LINE_OPTIONS = DEFAULT_OPTIONS;

    public MoonSettings getState() {
        return this;
    }

    public void loadState(MoonSettings state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    @NotNull
    public File[] getExportFiles() {
        return new File[]{PathManager.getOptionsFile("moon")};
    }

    @NotNull
    public String getPresentableName() {
        return MoonBundle.message("color.settings.name");
    }

    @NotNull
    @NonNls
    public String getComponentName() {
        return "MoonSettings";
    }

    public void initComponent() {

    }

    public void disposeComponent() {

    }


    public static String getDefaultMoonScriptHome() {
        //return new File(new File(PathManager.getHomePath(), TOOLS_DIR), "moon").getPath();
        return new File("C:\\Dev\\Lua\\MoonScript\\moonc") .getPath();
    }
}
