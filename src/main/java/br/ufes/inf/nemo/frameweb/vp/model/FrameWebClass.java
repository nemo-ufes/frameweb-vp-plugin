package br.ufes.inf.nemo.frameweb.vp.model;

import java.util.logging.Level;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import br.ufes.inf.nemo.vpzy.view.Color;

/**
 * Enumeration of FrameWeb class types and their respective names, stereotypes and colors, which can
 * be applied to classes in the plug-in.
 * 
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
 * @author <a href="https://github.com/igorssilva">Igor Sunderhus e Silva</a>
 */
public enum FrameWebClass {
  /* Application Model classes: */
  SERVICE_CLASS("application.service", "Service Class", "service", Color.GOLD,
      FrameWebPackage.APPLICATION_PACKAGE),

  SERVICE_INTERFACE("", "Service Interface", "service-interface", Color.GOLD,
      FrameWebPackage.APPLICATION_PACKAGE),

  /* Entity Model classes: */
  PERSISTENT_CLASS("entity.persistent", "Persistent Class", "persistent", Color.MEDIUM_SEA_GREEN,
      FrameWebPackage.ENTITY_PACKAGE),

  TRANSIENT_CLASS("entity.transient", "Transient Class", "transient", Color.LAWN_GREEN,
      FrameWebPackage.ENTITY_PACKAGE),

  MAPPED_SUPERCLASS("entity.mapped", "Mapped Superclass", "mapped", Color.MEDIUM_SPRING_GREEN,
      FrameWebPackage.ENTITY_PACKAGE),

  /* Navigation Model classes: */
  CONTROLLER_CLASS("", "Controller Class", "controller", Color.TURQUOISE,
      FrameWebPackage.CONTROLLER_PACKAGE),

  PAGE_COMPONENT("", "Web Page", "page", Color.SKY_BLUE, FrameWebPackage.VIEW_PACKAGE),

  FORM_COMPONENT("", "Web Form", "form", Color.DEEP_SKY_BLUE, FrameWebPackage.VIEW_PACKAGE),

  /* Persistence Model classes: */
  DAO_CLASS("persistence.dao.class", "DAO Class", "dao", Color.TOMATO, FrameWebPackage.PERSISTENCE_PACKAGE),
  DAO_INTERFACE("persistence.dao.interface", "DAO Interface", "dao-interface", Color.TOMATO, FrameWebPackage.PERSISTENCE_PACKAGE),

  /* Not a FrameWeb class: */
  NOT_A_FRAMEWEB_CLASS("", "", "", Color.WHITE, FrameWebPackage.NOT_A_FRAMEWEB_PACKAGE);

  /** The prefix used in the ID of context actions to set the class stereotypes. */
  private static final String PLUGIN_UI_CONTEXT_ACTION_PREFIX =
      "br.ufes.inf.nemo.frameweb.vp.actionset.context.class.menu.stereotype.";

  /** The ID of the class in the plugin UI configuration. */
  private String pluginUIID;

  /** The class's official name. */
  private String name;

  /** The name of the stereotype of the class. */
  private String stereotypeName;

  /** The fill color of the class. */
  private Color color;

  /** The package in which this class belongs. */
  private FrameWebPackage frameWebPackage;

  private FrameWebClass(String pluginUIID, String name, String stereotypeName, Color color,
      FrameWebPackage frameWebPackage) {
    this.pluginUIID = pluginUIID;
    this.name = name;
    this.stereotypeName = stereotypeName;
    this.color = color;
    this.frameWebPackage = frameWebPackage;
  }

  public String getName() {
    return name;
  }

  public String getStereotypeName() {
    return stereotypeName;
  }

  public Color getColor() {
    return color;
  }

  public FrameWebPackage getFrameWebPackage() {
    return frameWebPackage;
  }

  /**
   * Provides the enum value that refers to a specific FrameWeb class given its name.
   * 
   * @param name The name of the FrameWeb class.
   * @return An enum value that represents a FrameWeb class or {@code NOT_A_FRAMEWEB_CLASS} if no
   *         class with the given name exists.
   */
  public static FrameWebClass of(String name) {
    FrameWebClass clazz = NOT_A_FRAMEWEB_CLASS;
    for (FrameWebClass obj : FrameWebClass.values()) {
      if (obj.name.equalsIgnoreCase(name)) {
        clazz = obj;
      }
    }

    Logger.log(Level.FINE, "Providing FrameWeb class for name {0}: {1}",
        new Object[] {name, clazz});
    return clazz;
  }

  /**
   * Provides the enum value that refers to a specific FrameWeb class given its plugin UI ID.
   * 
   * @param pluginUIID The ID of the FrameWeb class in the plugin UI configuration.
   * @return An enum value that represents a FrameWeb class or {@code NOT_A_FRAMEWEB_CLASS} if no
   *         class with the given UI ID exists.
   */
  public static FrameWebClass ofPluginUIID(String pluginUIID) {
    FrameWebClass clazz = NOT_A_FRAMEWEB_CLASS;
    for (FrameWebClass obj : FrameWebClass.values()) {
      String fullID = PLUGIN_UI_CONTEXT_ACTION_PREFIX + obj.pluginUIID;
      if (fullID.equalsIgnoreCase(pluginUIID)) {
        clazz = obj;
      }
    }

    Logger.log(Level.FINE, "Providing FrameWeb class for plug-in UI ID {0}: {1}",
        new Object[] {pluginUIID, clazz});
    return clazz;
  }

  /**
   * Provides the enum value that refers to a specific FrameWeb class given its stereotype name.
   * 
   * @param stereotypeName The name of the stereotype.
   * @return An enum value that represents a FrameWeb class or {@code NOT_A_FRAMEWEB_CLASS} if no
   *         class with the given stereotype name exists.
   */
  public static FrameWebClass ofStereotype(String stereotypeName) {
    FrameWebClass clazz = NOT_A_FRAMEWEB_CLASS;
    for (FrameWebClass obj : FrameWebClass.values()) {
      if (obj.stereotypeName.equalsIgnoreCase(stereotypeName)) {
        clazz = obj;
      }
    }

    Logger.log(Level.FINE, "Providing FrameWeb class for stereotype {0}: {1}",
        new Object[] {stereotypeName, clazz});
    return clazz;
  }
}
