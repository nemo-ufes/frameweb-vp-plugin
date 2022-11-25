package br.ufes.inf.nemo.frameweb.vp.model;

import br.ufes.inf.nemo.vpzy.view.Color;

/**
 * Enumeration of FrameWeb class types and their respective names, stereotypes and colors, which can
 * be applied to classes in the plug-in.
 * 
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
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
  DAO_CLASS("", "DAO Class", "dao", Color.TOMATO, FrameWebPackage.PERSISTENCE_PACKAGE),

  /* Not a FrameWeb class: */
  NOT_A_FRAMEWEB_CLASS("", "", "", Color.WHITE, FrameWebPackage.NOT_A_FRAMEWEB_PACKAGE);

  /** The prefix used in the ID of context actions to set the package stereotypes. */
  private static final String PLUGIN_UI_CONTEXT_ACTION_PREFIX =
      "br.ufes.inf.nemo.frameweb.vp.actionset.context.class.menu.stereotype.";

  /** The ID of the package in the plugin UI configuration. */
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
    for (FrameWebClass obj : FrameWebClass.values())
      if (obj.name.equalsIgnoreCase(name))
        return obj;
    return NOT_A_FRAMEWEB_CLASS;
  }

  /**
   * Provides the enum value that refers to a specific FrameWeb class given its plugin UI ID.
   * 
   * @param pluginUIID The ID of the FrameWeb class in the plugin UI configuration.
   * @return An enum value that represents a FrameWeb class or {@code NOT_A_FRAMEWEB_CLASS} if no
   *         class with the given UI ID exists.
   */
  public static FrameWebClass ofPluginUIID(String pluginUIID) {
    for (FrameWebClass obj : FrameWebClass.values()) {
      String fullID = PLUGIN_UI_CONTEXT_ACTION_PREFIX + obj.pluginUIID;
      if (fullID.equalsIgnoreCase(pluginUIID))
        return obj;
    }
    return NOT_A_FRAMEWEB_CLASS;
  }

  /**
   * Provides the enum value that refers to a specific FrameWeb class given its stereotype name.
   * 
   * @param stereotypeName The name of the stereotype.
   * @return An enum value that represents a FrameWeb class or {@code NOT_A_FRAMEWEB_CLASS} if no
   *         class with the given stereotype name exists.
   */
  public static FrameWebClass ofStereotype(String stereotypeName) {
    for (FrameWebClass obj : FrameWebClass.values())
      if (obj.stereotypeName.equalsIgnoreCase(stereotypeName))
        return obj;
    return NOT_A_FRAMEWEB_CLASS;
  }
}
