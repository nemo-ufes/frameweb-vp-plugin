package br.ufes.inf.nemo.frameweb.vp.model;

import br.ufes.inf.nemo.vpzy.view.Color;

/**
 * Enumeration of FrameWeb class types and their respective names, stereotypes and colors, which can
 * be applied to classes in the plug-in.
 * 
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
 */
public enum FrameWebClass {
  PERSISTENT_CLASS("Persistent Class", "persistent", Color.MEDIUM_SEA_GREEN,
      FrameWebPackage.ENTITY_PACKAGE),

  TRANSIENT_CLASS("Transient Class", "transient", Color.LAWN_GREEN, FrameWebPackage.ENTITY_PACKAGE),

  MAPPED_SUPERCLASS("Mapped Superclass", "mapped", Color.MEDIUM_SPRING_GREEN,
      FrameWebPackage.ENTITY_PACKAGE),

  NOT_A_FRAMEWEB_CLASS("", "", Color.WHITE, FrameWebPackage.NOT_A_FRAMEWEB_PACKAGE);

  /** The class's official name, which identifies it in the plug-in UI. */
  private String name;

  /** The name of the stereotype of the class. */
  private String stereotypeName;

  /** The fill color of the class. */
  private Color color;

  /** The package in which this class belongs. */
  private FrameWebPackage frameWebPackage;

  private FrameWebClass(String name, String stereotypeName, Color color,
      FrameWebPackage frameWebPackage) {
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
   * Provides the enum value that refers to a specific FrameWeb class given its stereotype name.
   * 
   * @param stereotypeName The name of the stereotype.
   * @return An enum value that represents a FrameWeb class or {@code NOT_A_FRAMEWEB_CLASS} if no
   *         model with the given stereotype name exists.
   */
  public static FrameWebClass ofStereotype(String stereotypeName) {
    for (FrameWebClass obj : FrameWebClass.values())
      if (obj.stereotypeName.equalsIgnoreCase(stereotypeName))
        return obj;
    return NOT_A_FRAMEWEB_CLASS;
  }
}
