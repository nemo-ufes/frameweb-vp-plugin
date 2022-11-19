package br.ufes.inf.nemo.frameweb.vp.model;

import br.ufes.inf.nemo.vpzy.view.Color;

/**
 * Enumeration of FrameWeb models and their respective names, stereotypes and colors, which can be
 * applied to packages in the plug-in.
 * 
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
 */
public enum FrameWebModel {
  APPLICATION_MODEL("Application Model", "FrameWeb Application Model", Color.PALE_GOLDEN_ROD),

  ENTITY_MODEL("Entity Model", "FrameWeb Entity Model", Color.PALE_GREEN),

  NAVIGATION_MODEL("Navigation Model", "FrameWeb Navigation Model", Color.PALE_TURQUOISE),

  PERSISTENCE_MODEL("Persistence Model", "FrameWeb Persistence Model", Color.LIGHT_SALMON),

  NOT_A_FRAMEWEB_MODEL("", "", Color.WHITE);

  /** The model's official name, which identifies it in the plug-in UI. */
  private String name;

  /** The name of the stereotype of the package that represents the model. */
  private String stereotype;

  /** The fill color of the package that represents the model. */
  private Color color;

  private FrameWebModel(String name, String stereotype, Color color) {
    this.name = name;
    this.stereotype = stereotype;
    this.color = color;
  }

  public String getName() {
    return name;
  }

  public String getStereotype() {
    return stereotype;
  }

  public Color getColor() {
    return color;
  }

  /**
   * Provides the enum value that refers to a specific FrameWeb model given its name.
   * 
   * @param name The name of the FrameWeb model.
   * @return An enum value that represents a FrameWeb model or {@code NOT_A_FRAMEWEB_MODEL} if no
   *         model with the given name exists.
   */
  public static FrameWebModel of(String name) {
    for (FrameWebModel obj : FrameWebModel.values())
      if (obj.name.equalsIgnoreCase(name))
        return obj;
    return NOT_A_FRAMEWEB_MODEL;
  }
}
