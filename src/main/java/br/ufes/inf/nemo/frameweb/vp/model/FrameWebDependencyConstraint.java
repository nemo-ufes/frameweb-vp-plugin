package br.ufes.inf.nemo.frameweb.vp.model;

import br.ufes.inf.nemo.vpzy.logging.Logger;

import java.util.logging.Level;

/**
 * Enumeration of FrameWeb constraint types and their respective names, specifications, class types,
 * etc., which can be applied to dependency in the plug-in.
 *
 * @author <a href="https://github.com/gabrielgatti7">Gabriel Gatti da Silva </a>
 */
public enum FrameWebDependencyConstraint {
    /* Constraints for dependencies of Navigation Model classes: */
    NAVIGATION_DEPENDENCY_METHOD("navigation.method.value", "method=<value>", "method", true, String.class,
            FrameWebClass.CONTROLLER_CLASS, FrameWebClass.PAGE_COMPONENT, FrameWebClass.FORM_COMPONENT, FrameWebClass.BINARY_COMPONENT,
            FrameWebClass.PARTIAL_COMPONENT),

    NAVIGATION_DEPENDENCY_RESULT("navigation.result.value", "result=<value>", "result", true, String.class,
            FrameWebClass.CONTROLLER_CLASS, FrameWebClass.PAGE_COMPONENT, FrameWebClass.FORM_COMPONENT, FrameWebClass.BINARY_COMPONENT,
            FrameWebClass.PARTIAL_COMPONENT),

    NAVIGATION_DEPENDENCY_AJAX("navigation.ajax", "ajax", "ajax", false, null,
            FrameWebClass.CONTROLLER_CLASS, FrameWebClass.PAGE_COMPONENT, FrameWebClass.FORM_COMPONENT, FrameWebClass.BINARY_COMPONENT,
            FrameWebClass.PARTIAL_COMPONENT),

    NAVIGATION_DEPENDENCY_REDIRECT("navigation.redirect", "redirect", "redirect", false, null,
            FrameWebClass.CONTROLLER_CLASS, FrameWebClass.PAGE_COMPONENT, FrameWebClass.FORM_COMPONENT, FrameWebClass.BINARY_COMPONENT,
            FrameWebClass.PARTIAL_COMPONENT),

    /* Not a FrameWeb class dependency constraint (default value). */
    NOT_A_FRAMEWEB_DEPENDENCY_CONSTRAINT("", "", "", false, null, FrameWebClass.NOT_A_FRAMEWEB_CLASS);

    /**
     * The prefix used in the ID of context actions to add the constraint.
     */
    private static final String PLUGIN_UI_CONTEXT_ACTION_PREFIX =
            "br.ufes.inf.nemo.frameweb.vp.actionset.context.dependency.menu.constraint.";

    /**
     * The ID of the constraint in the plugin UI configuration.
     */
    private final String pluginUIID;

    /**
     * The constraint's official name.
     */
    private final String name;

    /**
     * The specification of the constraint.
     */
    private final String specification;

    /**
     * Indicates if the constraint takes a value.
     */
    private final boolean parameterized;

    /**
     * Indicates if the value type of the constraint.
     */
    private final Class<?> parameterType;

    /**
     * The classes to which the constraint can be applied.
     */
    private final FrameWebClass[] frameWebClasses;

    private FrameWebDependencyConstraint(final String pluginUIID, final String name, final String specification,
                                         final boolean parameterized, final Class<?> parameterType, final FrameWebClass... frameWebClasses) {
        this.pluginUIID = pluginUIID;
        this.name = name;
        this.specification = specification;
        this.parameterized = parameterized;
        this.parameterType = parameterType;
        this.frameWebClasses = frameWebClasses;
    }

    public String getPluginUIID() {
        return pluginUIID;
    }

    public String getName() {
        return name;
    }

    public String getSpecification() {
        return specification;
    }

    public boolean isParameterized() {
        return parameterized;
    }

    public Class<?> getParameterType() {
        return parameterType;
    }

    public FrameWebClass[] getFrameWebClasses() {
        return frameWebClasses;
    }

    /**
     * Provides the enum value that refers to a specific FrameWeb dependency constraint given its
     * name.
     *
     * @param name The name of the FrameWeb dependency constraint.
     * @return An enum value that represents a FrameWeb dependency constraint or
     * {@code NOT_A_FRAMEWEB_DEPENDENCY_CONSTRAINT} if no dependency constraint with
     * the given name exists.
     */
    public static FrameWebDependencyConstraint of(String name) {
        FrameWebDependencyConstraint constraint = NOT_A_FRAMEWEB_DEPENDENCY_CONSTRAINT;
        for (FrameWebDependencyConstraint obj : FrameWebDependencyConstraint.values()) {
            if (obj.name.equalsIgnoreCase(name)) {
                constraint = obj;
            }
        }

        Logger.log(Level.FINE, "Providing FrameWeb dependency constraint for name {0}: {1}",
                new Object[]{name, constraint});
        return constraint;
    }

    /**
     * Provides the enum value that refers to a specific FrameWeb dependency constraint given its
     * plugin UI ID.
     *
     * @param pluginUIID The ID of the FrameWeb dependency constraint in the plugin UI
     *                   configuration.
     * @return An enum value that represents a FrameWeb dependency constraint or
     * {@code NOT_A_FRAMEWEB_DEPENDENCY_CONSTRAINT} if no dependency constraint with
     * the given UI ID exists.
     */
    public static FrameWebDependencyConstraint ofPluginUIID(String pluginUIID) {
        FrameWebDependencyConstraint constraint = NOT_A_FRAMEWEB_DEPENDENCY_CONSTRAINT;
        for (FrameWebDependencyConstraint obj : FrameWebDependencyConstraint.values()) {
            String fullID = PLUGIN_UI_CONTEXT_ACTION_PREFIX + obj.pluginUIID;
            if (fullID.equalsIgnoreCase(pluginUIID)) {
                constraint = obj;
            }
        }

        Logger.log(Level.FINE,
                "Providing FrameWeb dependency constraint for plug-in UI ID {0}: {1}",
                new Object[]{pluginUIID, constraint});
        return constraint;
    }

    /**
     * Provides the enum value that refers to a specific FrameWeb dependency constraint given its
     * specification.
     *
     * @param specification The specification used by the dependency constraint.
     * @return An enum value that represents a FrameWeb dependency constraint or
     * {@code NOT_A_FRAMEWEB_DEPENDENCY_CONSTRAINT} if no dependency constraint with
     * the given specification exists.
     */
    public static FrameWebDependencyConstraint ofspecification(String specification) {
        FrameWebDependencyConstraint constraint = NOT_A_FRAMEWEB_DEPENDENCY_CONSTRAINT;
        for (FrameWebDependencyConstraint obj : FrameWebDependencyConstraint.values()) {
            if (obj.specification.equalsIgnoreCase(specification)) {
                constraint = obj;
            }
        }

        Logger.log(Level.FINE,
                "Providing FrameWeb dependency constraint for specification {0}: {1}",
                new Object[]{specification, constraint});
        return constraint;
    }
}