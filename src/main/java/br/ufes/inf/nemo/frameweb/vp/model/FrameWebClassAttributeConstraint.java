package br.ufes.inf.nemo.frameweb.vp.model;

/**
 * Enumeration of FrameWeb constraint types and their respective names, specifications, class types,
 * etc., which can be applied to class attributes in the plug-in.
 * 
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
 */
public enum FrameWebClassAttributeConstraint {
  /* Constraints for attributes of Entity Model classes: */
  PERSISTENT_CLASS_NULLABLE("entity.persistent.nullable", "Persistent Class attribute: nullable",
      "nullable", false, FrameWebClass.PERSISTENT_CLASS),

  PERSISTENT_CLASS_NOT_NULL("entity.persistent.notnull", "Persistent Class attribute: not null",
      "not null", false, FrameWebClass.PERSISTENT_CLASS),

  PERSISTENT_CLASS_PRECISION_DATE("entity.persistent.precision.date",
      "Persistent Class attribute: precision = date", "precision = date", false,
      FrameWebClass.PERSISTENT_CLASS),

  PERSISTENT_CLASS_PRECISION_TIME("entity.persistent.precision.time",
      "Persistent Class attribute: precision = time", "precision = time", false,
      FrameWebClass.PERSISTENT_CLASS),

  PERSISTENT_CLASS_PRECISION_TIMESTAMP("entity.persistent.precision.timestamp",
      "Persistent Class attribute: precision = timestamp", "precision = timestamp", false,
      FrameWebClass.PERSISTENT_CLASS),

  NOT_A_FRAMEWEB_CLASS_ATTRIBUTE_CONSTRAINT("", "", "", false, FrameWebClass.NOT_A_FRAMEWEB_CLASS);

  /** The prefix used in the ID of context actions to set the package stereotypes. */
  private static final String PLUGIN_UI_CONTEXT_ACTION_PREFIX =
      "br.ufes.inf.nemo.frameweb.vp.actionset.context.class.attribute.menu.constraint.";

  /** The ID of the package in the plugin UI configuration. */
  private String pluginUIID;

  /** The constraint's official name. */
  private String name;

  /** The specification of the constraint. */
  private String specification;

  /** Indicates if the constraint takes a value. */
  private boolean parameterized;

  /** The class to which the constraint can be applied. */
  private FrameWebClass frameWebClass;

  /** Array of constraints that are disjoint among themselves. */
  private FrameWebClassAttributeConstraint[] disjoints;

  private FrameWebClassAttributeConstraint(String pluginUIID, String name, String specification,
      boolean parameterized, FrameWebClass frameWebClass) {
    this.pluginUIID = pluginUIID;
    this.name = name;
    this.specification = specification;
    this.parameterized = parameterized;
    this.frameWebClass = frameWebClass;
  }

  /**
   * Builds the disjoint arrays for the constraints that have disjoint sets. This method is called
   * lazily from {@code getDefaultClassType()} as setting it in the constructor doesn't work because
   * of the circular reference and having it in a {@code static} block was sometimes not working
   * either and I don't know why.
   */
  private static void initDisjoints() {
    // Entity Model > Persistent Class > {null | not null}.
    FrameWebClassAttributeConstraint[] nullableDisjoints =
        {PERSISTENT_CLASS_NULLABLE, PERSISTENT_CLASS_NOT_NULL};
    for (FrameWebClassAttributeConstraint constraint : nullableDisjoints)
      constraint.disjoints = nullableDisjoints;

    // Entity Model > Persistent Class > precision = {date | time | timestamp}.
    FrameWebClassAttributeConstraint[] precisionDisjoints = {PERSISTENT_CLASS_PRECISION_DATE,
        PERSISTENT_CLASS_PRECISION_TIME, PERSISTENT_CLASS_PRECISION_TIMESTAMP};
    for (FrameWebClassAttributeConstraint constraint : precisionDisjoints)
      constraint.disjoints = precisionDisjoints;
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

  public FrameWebClass getFrameWebClass() {
    return frameWebClass;
  }

  public FrameWebClassAttributeConstraint[] getDisjoints() {
    // Lazily initialize the disjoint sets.
    if (PERSISTENT_CLASS_NULLABLE.disjoints == null) {
      initDisjoints();
    }
    return disjoints;
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
   * specification.
   * 
   * @param specification The specification used by the class attribute constraint.
   * @return An enum value that represents a FrameWeb class attribute constraint or
   *         {@code NOT_A_FRAMEWEB_CLASS_CONSTRAINT} if no class attribute constraint with the given
   *         specification exists.
   */
  public static FrameWebClassAttributeConstraint ofspecification(String specification) {
    for (FrameWebClassAttributeConstraint obj : FrameWebClassAttributeConstraint.values())
      if (obj.specification.equalsIgnoreCase(specification))
        return obj;
    return NOT_A_FRAMEWEB_CLASS_ATTRIBUTE_CONSTRAINT;
  }
}
