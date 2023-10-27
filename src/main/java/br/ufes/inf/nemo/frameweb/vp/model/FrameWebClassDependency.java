package br.ufes.inf.nemo.frameweb.vp.model;

import br.ufes.inf.nemo.vpzy.logging.Logger;
import java.util.logging.Level;

/**
 *
 * Enumeration of FrameWeb Dependency types and their respective names and appliable frameweb classes, which can
 * be applied to classes in the plug-in.
 *
 * @author Igor Sunderhus e Silva
 * @version 0.0.1
 */
public enum FrameWebClassDependency {

    SERVICE_DEPENDENCY("application.service.interface", "Service Interface", FrameWebClass.CONTROLLER_CLASS),
    DAO_DEPENDENCY("persistent.dao.interface", "DAO Interface", FrameWebClass.SERVICE_CLASS),
    NOT_A_FRAMEWEB_DEPENDENCY("", "", FrameWebClass.NOT_A_FRAMEWEB_CLASS);

    /** The prefix used in the ID of context actions to set the class dependency. */
    private static final String PLUGIN_UI_CONTEXT_ACTION_PREFIX =
            "br.ufes.inf.nemo.frameweb.vp.actionset.context.class.menu.dependency.";

    /** The ID of the class in the plugin UI configuration. */
    private final String pluginUIID;

    /** The class's official name. */
    private final String name;

    private final FrameWebClass[] frameWebClasses;


    public String getPluginUIID() {
        return pluginUIID;
    }

    public String getName() {
        return name;
    }


    public FrameWebClass[] getFrameWebClasses() {
        return frameWebClasses;
    }

    FrameWebClassDependency(final String pluginUIID, final String name, final FrameWebClass... frameWebClasses) {
        this.pluginUIID = pluginUIID;
        this.name = name;
        this.frameWebClasses = frameWebClasses;
    }

    /**
     * Provides the enum value that refers to a specific FrameWeb dependency given its name.
     *
     * @param name The name of the FrameWeb dependency.
     * @return An enum value that represents a FrameWeb dependency or {@code NOT_A_FRAMEWEB_DEPENDENCY} if no
     *         dependency with the given name exists.
     */
    public static FrameWebClassDependency of(String name) {
        FrameWebClassDependency clazz = NOT_A_FRAMEWEB_DEPENDENCY;
        for (final FrameWebClassDependency obj : FrameWebClassDependency.values()) {
            if (obj.name.equalsIgnoreCase(name)) {
                clazz = obj;
            }
        }

        Logger.log(Level.FINE, "Providing FrameWeb dependency for name {0}: {1}",
                new Object[] {name, clazz});
        return clazz;
    }

    /**
     * Provides the enum value that refers to a specific FrameWeb dependency given its plugin UI ID.
     *
     * @param pluginUIID The ID of the FrameWeb dependency in the plugin UI configuration.
     * @return An enum value that represents a FrameWeb dependency or {@code NOT_A_FRAMEWEB_DEPENDENCY} if no
     *         dependency with the given UI ID exists.
     */
    public static FrameWebClassDependency ofPluginUIID(String pluginUIID) {
        FrameWebClassDependency clazz = NOT_A_FRAMEWEB_DEPENDENCY;
        for (final FrameWebClassDependency obj : FrameWebClassDependency.values()) {
            final String fullID = PLUGIN_UI_CONTEXT_ACTION_PREFIX + obj.pluginUIID;
            if (fullID.equalsIgnoreCase(pluginUIID)) {
                clazz = obj;
            }
        }

        Logger.log(Level.FINE, "Providing FrameWeb class for plug-in UI ID {0}: {1}",
                new Object[] {pluginUIID, clazz});
        return clazz;
    }
}
