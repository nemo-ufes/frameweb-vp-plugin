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
      Color.PALE_GOLDEN_ROD,
      null),

  CONTROLLER_PACKAGE("controller", "Controller Package", "FrameWeb Controller Package",
      Color.PALE_TURQUOISE,
      null),

  ENTITY_PACKAGE("entity", "Entity Package", "FrameWeb Entity Package", Color.PALE_GREEN,
      FrameWebClass.PERSISTENT_CLASS),

  PERSISTENCE_PACKAGE("persistence", "Persistence Package", "FrameWeb Persistence Package",
      Color.LIGHT_SALMON,
      null),

  VIEW_PACKAGE("view", "View Package", "FrameWeb View Package", Color.LIGHT_BLUE, null),

  NOT_A_FRAMEWEB_PACKAGE("", "", "", Color.WHITE, null);

  /** The prefix used in the ID of context actions to set the package stereotypes. */
  private static final String PLUGIN_UI_CONTEXT_ACTION_PREFIX =
      "br.ufes.inf.nemo.frameweb.vp.actionset.context.package.menu.stereotype.";

  /** The ID of the package in the plugin UI configuration. */
  private String pluginUIID;

  /** The package's official name, which identifies it in the plug-in UI. */
  private String name;

  /** The name of the stereotype of the package. */
  private String stereotypeName;

  /** The fill color of the package. */
  private Color color;

  /** The default type of class in the package, if any. */
  private FrameWebClass defaultClassType;

  private FrameWebPackage(String pluginUIID, String name, String stereotypeName, Color color,
      FrameWebClass defaultClassType) {
    this.pluginUIID = pluginUIID;
    this.name = name;
    this.stereotypeName = stereotypeName;
    this.color = color;
    this.defaultClassType = defaultClassType;
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
   *         no package with the given name exists.
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
   *         no model with the given stereotype name exists.
   */
  public static FrameWebPackage ofStereotype(String stereotypeName) {
    for (FrameWebPackage obj : FrameWebPackage.values())
      if (obj.stereotypeName.equalsIgnoreCase(stereotypeName))
        return obj;
    return NOT_A_FRAMEWEB_PACKAGE;
  }
}
