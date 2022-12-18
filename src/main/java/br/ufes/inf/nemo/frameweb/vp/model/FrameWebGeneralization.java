package br.ufes.inf.nemo.frameweb.vp.model;

import java.util.logging.Level;
import br.ufes.inf.nemo.vpzy.logging.Logger;

/**
 * Enumeration of FrameWeb generalization types and their respective names and stereotypes, which
 * can be applied to generalizations in the plug-in.
 * 
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
 */
public enum FrameWebGeneralization {
  /* Entity Model generalizations: */
  PERSISTENT_CLASS_JOIN("entity.persistent.join", "join", "join", FrameWebClass.PERSISTENT_CLASS,
      FrameWebClass.MAPPED_SUPERCLASS),

  PERSISTENT_CLASS_SINGLE_TABLE("entity.persistent.singletable", "single-table", "single-table",
      FrameWebClass.PERSISTENT_CLASS, FrameWebClass.MAPPED_SUPERCLASS),

  PERSISTENT_CLASS_UNION("entity.persistent.union", "union", "union",
      FrameWebClass.PERSISTENT_CLASS, FrameWebClass.MAPPED_SUPERCLASS),

  /* Not a FrameWeb generalization: */
  NOT_A_FRAMEWEB_GENERALIZATION("", "", "", FrameWebClass.NOT_A_FRAMEWEB_CLASS);

  /** The prefix used in the ID of context actions to set the generalization stereotypes. */
  private static final String PLUGIN_UI_CONTEXT_ACTION_PREFIX =
      "br.ufes.inf.nemo.frameweb.vp.actionset.context.generalization.menu.stereotype.";

  /** The ID of the generalization in the plugin UI configuration. */
  private String pluginUIID;

  /** The generalization's official name. */
  private String name;

  /** The name of the stereotype of the generalization. */
  private String stereotypeName;

  /** The classes whose generalizations can receive the stereotype. */
  private FrameWebClass[] frameWebClasses;

  private FrameWebGeneralization(String pluginUIID, String name, String stereotypeName,
      FrameWebClass... frameWebClasses) {
    this.pluginUIID = pluginUIID;
    this.name = name;
    this.stereotypeName = stereotypeName;
    this.frameWebClasses = frameWebClasses;
  }

  public String getPluginUIID() {
    return pluginUIID;
  }

  public String getName() {
    return name;
  }

  public String getStereotypeName() {
    return stereotypeName;
  }

  public FrameWebClass[] getFrameWebClasses() {
    return frameWebClasses;
  }

  /**
   * Provides the enum value that refers to a specific FrameWeb generalization given its name.
   * 
   * @param name The name of the FrameWeb generalization.
   * @return An enum value that represents a FrameWeb generalization or
   *         {@code NOT_A_FRAMEWEB_GENERALIZATION} if no generalization with the given name exists.
   */
  public static FrameWebGeneralization of(String name) {
    FrameWebGeneralization generalization = NOT_A_FRAMEWEB_GENERALIZATION;
    for (FrameWebGeneralization obj : FrameWebGeneralization.values()) {
      if (obj.name.equalsIgnoreCase(name)) {
        generalization = obj;
      }
    }

    Logger.log(Level.FINE, "Providing FrameWeb generalization for name {0}: {1}",
        new Object[] {name, generalization});
    return generalization;
  }

  /**
   * Provides the enum value that refers to a specific FrameWeb generalization given its plugin UI
   * ID.
   * 
   * @param pluginUIID The ID of the FrameWeb generalization in the plugin UI configuration.
   * @return An enum value that represents a FrameWeb generalization or
   *         {@code NOT_A_FRAMEWEB_GENERALIZATION} if no generalization with the given UI ID exists.
   */
  public static FrameWebGeneralization ofPluginUIID(String pluginUIID) {
    FrameWebGeneralization generalization = NOT_A_FRAMEWEB_GENERALIZATION;
    for (FrameWebGeneralization obj : FrameWebGeneralization.values()) {
      String fullID = PLUGIN_UI_CONTEXT_ACTION_PREFIX + obj.pluginUIID;
      if (fullID.equalsIgnoreCase(pluginUIID)) {
        generalization = obj;
      }
    }

    Logger.log(Level.FINE, "Providing FrameWeb generalization for plug-in UI ID {0}: {1}",
        new Object[] {pluginUIID, generalization});
    return generalization;
  }

  /**
   * Provides the enum value that refers to a specific FrameWeb generalization given its stereotype
   * name.
   * 
   * @param stereotypeName The name of the stereotype.
   * @return An enum value that represents a FrameWeb generalization or
   *         {@code NOT_A_FRAMEWEB_GENERALIZATION} if no generalization with the given stereotype
   *         name exists.
   */
  public static FrameWebGeneralization ofStereotype(String stereotypeName) {
    FrameWebGeneralization generalization = NOT_A_FRAMEWEB_GENERALIZATION;
    for (FrameWebGeneralization obj : FrameWebGeneralization.values()) {
      if (obj.stereotypeName.equalsIgnoreCase(stereotypeName)) {
        generalization = obj;
      }
    }

    Logger.log(Level.FINE, "Providing FrameWeb generalization for stereotype {0}: {1}",
        new Object[] {stereotypeName, generalization});
    return generalization;
  }
}
