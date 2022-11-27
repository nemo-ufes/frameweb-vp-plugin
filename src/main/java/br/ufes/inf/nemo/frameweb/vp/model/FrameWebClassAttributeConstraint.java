package br.ufes.inf.nemo.frameweb.vp.model;

/**
 * Enumeration of FrameWeb constraint types and their respective names, expressions, class types,
 * etc., which can be applied to class attributes in the plug-in.
 * 
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
 */
public enum FrameWebClassAttributeConstraint {
  /* Application Model classes: */
  PERSISTENT_CLASS_NULLABLE("entity.persistent.nullable", "Persistent Class attribute: nullable",
      "nullable", false, FrameWebClass.PERSISTENT_CLASS),

  NOT_A_FRAMEWEB_CLASS_ATTRIBUTE_CONSTRAINT("", "", "", false, FrameWebClass.NOT_A_FRAMEWEB_CLASS);

  /** The prefix used in the ID of context actions to set the package stereotypes. */
  private static final String PLUGIN_UI_CONTEXT_ACTION_PREFIX =
      "br.ufes.inf.nemo.frameweb.vp.actionset.context.class.attribute.menu.constraint.";

  /** The ID of the package in the plugin UI configuration. */
  private String pluginUIID;

  /** The constraint's official name. */
  private String name;

  /** The expression of the constraint. */
  private String expression;

  /** Indicates if the constraint takes a value. */
  private boolean parameterized;

  /** The class to which the constraint can be applied. */
  private FrameWebClass frameWebClass;

  private FrameWebClassAttributeConstraint(String pluginUIID, String name, String expression,
      boolean parameterized, FrameWebClass frameWebClass) {
    this.pluginUIID = pluginUIID;
    this.name = name;
    this.expression = expression;
    this.parameterized = parameterized;
    this.frameWebClass = frameWebClass;
  }

  public String getName() {
    return name;
  }

  public String getExpression() {
    return expression;
  }

  public boolean isParameterized() {
    return parameterized;
  }

  public FrameWebClass getFrameWebClass() {
    return frameWebClass;
  }

  /**
   * Provides the enum value that refers to a specific FrameWeb class attribute constraint given its
   * name.
   * 
   * @param name The name of the FrameWeb class attribute constraint.
   * @return An enum value that represents a FrameWeb class attribute constraint or
   *         {@code NOT_A_FRAMEWEB_CLASS_CONSTRAINT} if no class attribute constraint with the given
   *         name exists.
   */
  public static FrameWebClassAttributeConstraint of(String name) {
    for (FrameWebClassAttributeConstraint obj : FrameWebClassAttributeConstraint.values())
      if (obj.name.equalsIgnoreCase(name))
        return obj;
    return NOT_A_FRAMEWEB_CLASS_ATTRIBUTE_CONSTRAINT;
  }

  /**
   * Provides the enum value that refers to a specific FrameWeb class attribute constraint given its
   * plugin UI ID.
   * 
   * @param pluginUIID The ID of the FrameWeb class attribute constraint in the plugin UI
   *        configuration.
   * @return An enum value that represents a FrameWeb class attribute constraint or
   *         {@code NOT_A_FRAMEWEB_CLASS_CONSTRAINT} if no class attribute constraint with the given
   *         UI ID exists.
   */
  public static FrameWebClassAttributeConstraint ofPluginUIID(String pluginUIID) {
    for (FrameWebClassAttributeConstraint obj : FrameWebClassAttributeConstraint.values()) {
      String fullID = PLUGIN_UI_CONTEXT_ACTION_PREFIX + obj.pluginUIID;
      if (fullID.equalsIgnoreCase(pluginUIID))
        return obj;
    }
    return NOT_A_FRAMEWEB_CLASS_ATTRIBUTE_CONSTRAINT;
  }

  /**
   * Provides the enum value that refers to a specific FrameWeb class attribute constraint given its
   * expression.
   * 
   * @param expression The expression used by the class attribute constraint.
   * @return An enum value that represents a FrameWeb class attribute constraint or
   *         {@code NOT_A_FRAMEWEB_CLASS_CONSTRAINT} if no class attribute constraint with the given
   *         expression exists.
   */
  public static FrameWebClassAttributeConstraint ofExpression(String expression) {
    for (FrameWebClassAttributeConstraint obj : FrameWebClassAttributeConstraint.values())
      if (obj.expression.equalsIgnoreCase(expression))
        return obj;
    return NOT_A_FRAMEWEB_CLASS_ATTRIBUTE_CONSTRAINT;
  }
}