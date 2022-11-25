package br.ufes.inf.nemo.frameweb.vp.model;

import br.ufes.inf.nemo.vpzy.view.Color;

/**
 * Enumeration of FrameWeb package types and their respective names, stereotypes and colors, which
 * can be applied to packages in the plug-in.
 * 
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
 */
public enum FrameWebPackage {
  APPLICATION_PACKAGE("application", "Application Package", "FrameWeb Application Package",
      Color.PALE_GOLDEN_ROD),

  CONTROLLER_PACKAGE("controller", "Controller Package", "FrameWeb Controller Package",
      Color.PALE_TURQUOISE),

  ENTITY_PACKAGE("entity", "Entity Package", "FrameWeb Entity Package", Color.PALE_GREEN),

  PERSISTENCE_PACKAGE("persistence", "Persistence Package", "FrameWeb Persistence Package",
      Color.LIGHT_SALMON),

  VIEW_PACKAGE("view", "View Package", "FrameWeb View Package", Color.LIGHT_BLUE),

  NOT_A_FRAMEWEB_PACKAGE("", "", "", Color.WHITE);

  /** The prefix used in the ID of context actions to set the package stereotypes. */
  private static final String PLUGIN_UI_CONTEXT_ACTION_PREFIX =
      "br.ufes.inf.nemo.frameweb.vp.actionset.context.package.menu.stereotype.";

  /* Set the default class of each package in a static block to avoid a circular reference. */
  static {
    APPLICATION_PACKAGE.defaultClassType = FrameWebClass.SERVICE_CLASS;
    CONTROLLER_PACKAGE.defaultClassType = FrameWebClass.CONTROLLER_CLASS;
    ENTITY_PACKAGE.defaultClassType = FrameWebClass.PERSISTENT_CLASS;
    PERSISTENCE_PACKAGE.defaultClassType = FrameWebClass.DAO_CLASS;
  }

  /** The ID of the package in the plugin UI configuration. */
  private String pluginUIID;

  /** The package's official name. */
  private String name;

  /** The name of the stereotype of the package. */
  private String stereotypeName;

  /** The fill color of the package. */
  private Color color;

  /** The default type of class in the package, if any. */
  private FrameWebClass defaultClassType;

  private FrameWebPackage(String pluginUIID, String name, String stereotypeName, Color color) {
    this.pluginUIID = pluginUIID;
    this.name = name;
    this.stereotypeName = stereotypeName;
    this.color = color;
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

  public FrameWebClass getDefaultClassType() {
    return defaultClassType;
  }

  /**
   * Provides the enum value that refers to a specific FrameWeb package given its name.
   * 
   * @param name The name of the FrameWeb package.
   * @return An enum value that represents a FrameWeb package or {@code NOT_A_FRAMEWEB_PACKAGE} if
   *         no package with the given name exists.
   */
  public static FrameWebPackage of(String name) {
    for (FrameWebPackage obj : FrameWebPackage.values())
      if (obj.name.equalsIgnoreCase(name))
        return obj;
    return NOT_A_FRAMEWEB_PACKAGE;
  }

  /**
   * Provides the enum value that refers to a specific FrameWeb package given its plugin UI ID.
   * 
   * @param pluginUIID The ID of the FrameWeb package in the plugin UI configuration.
   * @return An enum value that represents a FrameWeb package or {@code NOT_A_FRAMEWEB_PACKAGE} if
   *         no package with the given UI ID exists.
   */
  public static FrameWebPackage ofPluginUIID(String pluginUIID) {
    for (FrameWebPackage obj : FrameWebPackage.values()) {
      String fullID = PLUGIN_UI_CONTEXT_ACTION_PREFIX + obj.pluginUIID;
      if (fullID.equalsIgnoreCase(pluginUIID))
        return obj;
    }
    return NOT_A_FRAMEWEB_PACKAGE;
  }

  /**
   * Provides the enum value that refers to a specific FrameWeb package given its stereotype name.
   * 
   * @param stereotypeName The name of the stereotype.
   * @return An enum value that represents a FrameWeb package or {@code NOT_A_FRAMEWEB_PACKAGE} if
   *         no package with the given stereotype name exists.
   */
  public static FrameWebPackage ofStereotype(String stereotypeName) {
    for (FrameWebPackage obj : FrameWebPackage.values())
      if (obj.stereotypeName.equalsIgnoreCase(stereotypeName))
        return obj;
    return NOT_A_FRAMEWEB_PACKAGE;
  }
}
