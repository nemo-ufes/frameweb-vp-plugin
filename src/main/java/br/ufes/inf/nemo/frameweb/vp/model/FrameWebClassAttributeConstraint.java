package br.ufes.inf.nemo.frameweb.vp.model;

import java.util.logging.Level;
import br.ufes.inf.nemo.vpzy.logging.Logger;

/**
 * Enumeration of FrameWeb constraint types and their respective names, specifications, class types,
 * etc., which can be applied to class attributes in the plug-in.
 * 
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
 */
public enum FrameWebClassAttributeConstraint {
  /* Constraints for attributes of Entity Model classes: */
  PERSISTENT_CLASS_COLUMN("entity.persistent.column", "column = <name>", "column", true,
      FrameWebClass.PERSISTENT_CLASS, FrameWebClass.MAPPED_SUPERCLASS),

  PERSISTENT_CLASS_NOT_NULL("entity.persistent.notnull", "not null",
      "not null", false, FrameWebClass.PERSISTENT_CLASS, FrameWebClass.MAPPED_SUPERCLASS),

  PERSISTENT_CLASS_NULLABLE("entity.persistent.nullable", "nullable", "nullable", false,
      FrameWebClass.PERSISTENT_CLASS, FrameWebClass.MAPPED_SUPERCLASS),

  PERSISTENT_CLASS_PRECISION_DATE("entity.persistent.precision.date",
      "precision=date", "precision=date", false,
      FrameWebClass.PERSISTENT_CLASS, FrameWebClass.MAPPED_SUPERCLASS),

  PERSISTENT_CLASS_PRECISION_TIME("entity.persistent.precision.time",
      "precision=time", "precision=time", false,
      FrameWebClass.PERSISTENT_CLASS, FrameWebClass.MAPPED_SUPERCLASS),

  PERSISTENT_CLASS_PRECISION_TIMESTAMP("entity.persistent.precision.timestamp",
      "precision=timestamp", "precision=timestamp", false,
      FrameWebClass.PERSISTENT_CLASS, FrameWebClass.MAPPED_SUPERCLASS),

  /* Not a FrameWeb class attribute constraint (default value). */
  NOT_A_FRAMEWEB_CLASS_ATTRIBUTE_CONSTRAINT("", "", "", false, FrameWebClass.NOT_A_FRAMEWEB_CLASS);

  /** The prefix used in the ID of context actions to add the constraint. */
  private static final String PLUGIN_UI_CONTEXT_ACTION_PREFIX =
      "br.ufes.inf.nemo.frameweb.vp.actionset.context.class.attribute.menu.constraint.";

  /** The ID of the constraint in the plugin UI configuration. */
  private String pluginUIID;

  /** The constraint's official name. */
  private String name;

  /** The specification of the constraint. */
  private String specification;

  /** Indicates if the constraint takes a value. */
  private boolean parameterized;

  /** The classes to which the constraint can be applied. */
  private FrameWebClass[] frameWebClasses;

  /** Array of constraints that are disjoint among themselves. */
  private FrameWebClassAttributeConstraint[] disjoints;

  private FrameWebClassAttributeConstraint(String pluginUIID, String name, String specification,
      boolean parameterized, FrameWebClass... frameWebClasses) {
    this.pluginUIID = pluginUIID;
    this.name = name;
    this.specification = specification;
    this.parameterized = parameterized;
    this.frameWebClasses = frameWebClasses;
  }

  /**
   * Builds the disjoint arrays for the constraints that have disjoint sets. This method is called
   * lazily from {@code getDisjoints()} as setting it in the constructor doesn't work because of the
   * circular reference and having it in a {@code static} block was sometimes not working either and
   * I don't know why.
   */
  private static void initDisjoints() {
    Logger.log(Level.CONFIG,
        "Initializing disjoint arrays of FrameWeb class attribute constraints");

    // Entity Model > Persistent Class > {null | not null}.
    FrameWebClassAttributeConstraint[] nullableDisjoints =
        {PERSISTENT_CLASS_NULLABLE, PERSISTENT_CLASS_NOT_NULL};
    for (FrameWebClassAttributeConstraint constraint : nullableDisjoints) {
      constraint.disjoints = nullableDisjoints;
    }

    // Entity Model > Persistent Class > precision={date | time | timestamp}.
    FrameWebClassAttributeConstraint[] precisionDisjoints = {PERSISTENT_CLASS_PRECISION_DATE,
        PERSISTENT_CLASS_PRECISION_TIME, PERSISTENT_CLASS_PRECISION_TIMESTAMP};
    for (FrameWebClassAttributeConstraint constraint : precisionDisjoints) {
      constraint.disjoints = precisionDisjoints;
    }
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

  public FrameWebClass[] getFrameWebClasses() {
    return frameWebClasses;
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
   *         {@code NOT_A_FRAMEWEB_CLASS_ATTRIBUTE_CONSTRAINT} if no class attribute constraint with
   *         the given name exists.
   */
  public static FrameWebClassAttributeConstraint of(String name) {
    FrameWebClassAttributeConstraint constraint = NOT_A_FRAMEWEB_CLASS_ATTRIBUTE_CONSTRAINT;
    for (FrameWebClassAttributeConstraint obj : FrameWebClassAttributeConstraint.values()) {
      if (obj.name.equalsIgnoreCase(name)) {
        constraint = obj;
      }
    }

    Logger.log(Level.FINE, "Providing FrameWeb class attribute constraint for name {0}: {1}",
        new Object[] {name, constraint});
    return constraint;
  }

  /**
   * Provides the enum value that refers to a specific FrameWeb class attribute constraint given its
   * plugin UI ID.
   * 
   * @param pluginUIID The ID of the FrameWeb class attribute constraint in the plugin UI
   *        configuration.
   * @return An enum value that represents a FrameWeb class attribute constraint or
   *         {@code NOT_A_FRAMEWEB_CLASS_ATTRIBUTE_CONSTRAINT} if no class attribute constraint with
   *         the given UI ID exists.
   */
  public static FrameWebClassAttributeConstraint ofPluginUIID(String pluginUIID) {
    FrameWebClassAttributeConstraint constraint = NOT_A_FRAMEWEB_CLASS_ATTRIBUTE_CONSTRAINT;
    for (FrameWebClassAttributeConstraint obj : FrameWebClassAttributeConstraint.values()) {
      String fullID = PLUGIN_UI_CONTEXT_ACTION_PREFIX + obj.pluginUIID;
      if (fullID.equalsIgnoreCase(pluginUIID)) {
        constraint = obj;
      }
    }

    Logger.log(Level.FINE,
        "Providing FrameWeb class attribute constraint for plug-in UI ID {0}: {1}",
        new Object[] {pluginUIID, constraint});
    return constraint;
  }

  /**
   * Provides the enum value that refers to a specific FrameWeb class attribute constraint given its
   * specification.
   * 
   * @param specification The specification used by the class attribute constraint.
   * @return An enum value that represents a FrameWeb class attribute constraint or
   *         {@code NOT_A_FRAMEWEB_CLASS_ATTRIBUTE_CONSTRAINT} if no class attribute constraint with
   *         the given specification exists.
   */
  public static FrameWebClassAttributeConstraint ofspecification(String specification) {
    FrameWebClassAttributeConstraint constraint = NOT_A_FRAMEWEB_CLASS_ATTRIBUTE_CONSTRAINT;
    for (FrameWebClassAttributeConstraint obj : FrameWebClassAttributeConstraint.values()) {
      if (obj.specification.equalsIgnoreCase(specification)) {
        constraint = obj;
      }
    }

    Logger.log(Level.FINE,
        "Providing FrameWeb class attribute constraint for specification {0}: {1}",
        new Object[] {specification, constraint});
    return constraint;
  }
}
