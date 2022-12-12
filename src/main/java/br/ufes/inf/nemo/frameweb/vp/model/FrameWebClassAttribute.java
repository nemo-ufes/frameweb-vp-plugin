package br.ufes.inf.nemo.frameweb.vp.model;

import java.util.logging.Level;
import br.ufes.inf.nemo.vpzy.logging.Logger;

/**
 * Enumeration of FrameWeb class attributes and their respective names, stereotypes, etc., which can
 * be applied to class attributes in the plug-in.
 * 
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
 */
public enum FrameWebClassAttribute {
  /* Stereotypes for attributes of Entity Model classes: */
  PERSISTENT_CLASS_PERSISTENT("entity.persistent.persistent", "persistent", "persistent",
      FrameWebClass.PERSISTENT_CLASS, FrameWebClass.MAPPED_SUPERCLASS),

  PERSISTENT_CLASS_TRANSIENT("entity.persistent.transient", "transient", "transient",
      FrameWebClass.PERSISTENT_CLASS, FrameWebClass.MAPPED_SUPERCLASS),

  /* Not a FrameWeb class attribute (default value). */
  NOT_A_FRAMEWEB_CLASS_ATTRIBUTE("", "", "", FrameWebClass.NOT_A_FRAMEWEB_CLASS);

  /** The prefix used in the ID of context actions to set the attribute stereotypes. */
  private static final String PLUGIN_UI_CONTEXT_ACTION_PREFIX =
      "br.ufes.inf.nemo.frameweb.vp.actionset.context.class.attribute.menu.stereotype.";

  /** The ID of the package in the plugin UI configuration. */
  private String pluginUIID;

  /** The attribute's official name. */
  private String name;

  /** The name of the stereotype of the class attribute. */
  private String stereotypeName;

  /** The classes in which this type of attribute can be created. */
  private FrameWebClass[] frameWebClasses;

  private FrameWebClassAttribute(String pluginUIID, String name, String stereotypeName,
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
   * Provides the enum value that refers to a specific FrameWeb class attribute given its name.
   * 
   * @param name The name of the FrameWeb class attribute.
   * @return An enum value that represents a FrameWeb class attribute or
   *         {@code NOT_A_FRAMEWEB_CLASS_ATTRIBUTE} if no class attribute with the given name
   *         exists.
   */
  public static FrameWebClassAttribute of(String name) {
    FrameWebClassAttribute attribute = NOT_A_FRAMEWEB_CLASS_ATTRIBUTE;
    for (FrameWebClassAttribute obj : FrameWebClassAttribute.values()) {
      if (obj.name.equalsIgnoreCase(name)) {
        attribute = obj;
      }
    }

    Logger.log(Level.FINE, "Providing FrameWeb class attribute for name {0}: {1}",
        new Object[] {name, attribute});
    return attribute;
  }

  /**
   * Provides the enum value that refers to a specific FrameWeb class attribute given its plugin UI
   * ID.
   * 
   * @param pluginUIID The ID of the FrameWeb class attribute in the plugin UI configuration.
   * @return An enum value that represents a FrameWeb class attribute or
   *         {@code NOT_A_FRAMEWEB_CLASS_ATTRIBUTE} if no class attribute with the given UI ID
   *         exists.
   */
  public static FrameWebClassAttribute ofPluginUIID(String pluginUIID) {
    FrameWebClassAttribute attribute = NOT_A_FRAMEWEB_CLASS_ATTRIBUTE;
    for (FrameWebClassAttribute obj : FrameWebClassAttribute.values()) {
      String fullID = PLUGIN_UI_CONTEXT_ACTION_PREFIX + obj.pluginUIID;
      if (fullID.equalsIgnoreCase(pluginUIID)) {
        attribute = obj;
      }
    }

    Logger.log(Level.FINE, "Providing FrameWeb class attribute for plug-in UI ID {0}: {1}",
        new Object[] {pluginUIID, attribute});
    return attribute;
  }

  /**
   * Provides the enum value that refers to a specific FrameWeb class attribute given its stereotype
   * name.
   * 
   * @param stereotypeName The name of the stereotype.
   * @return An enum value that represents a FrameWeb class attribute or
   *         {@code NOT_A_FRAMEWEB_CLASS_ATTRIBUTE} if no class attribute with the given stereotype
   *         name exists.
   */
  public static FrameWebClassAttribute ofStereotype(String stereotype) {
    FrameWebClassAttribute attribute = NOT_A_FRAMEWEB_CLASS_ATTRIBUTE;
    for (FrameWebClassAttribute obj : FrameWebClassAttribute.values()) {
      if (obj.stereotypeName.equalsIgnoreCase(stereotype)) {
        attribute = obj;
      }
    }

    Logger.log(Level.FINE, "Providing FrameWeb class attribute for stereotype {0}: {1}",
        new Object[] {stereotype, attribute});
    return attribute;
  }
}
