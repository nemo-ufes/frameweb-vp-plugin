package br.ufes.inf.nemo.frameweb.vp.model;

public enum FrameWebModel {
  APPLICATION_MODEL("Application Model", "FrameWeb Application Model"),

  ENTITY_MODEL("Entity Model", "FrameWeb Entity Model"),

  NAVIGATION_MODEL("Navigation Model", "FrameWeb Navigation Model"),

  PERSISTENCE_MODEL("Persistence Model", "FrameWeb Persistence Model"),

  NOT_A_FRAMEWEB_MODEL("", "");

  private String name;

  private String stereotype;

  private FrameWebModel(String name, String stereotype) {
    this.name = name;
    this.stereotype = stereotype;
  }

  public String getName() {
    return name;
  }

  public String getStereotype() {
    return stereotype;
  }

  public static FrameWebModel of(String name) {
    for (FrameWebModel obj : FrameWebModel.values())
      if (obj.name.equalsIgnoreCase(name))
        return obj;
    return NOT_A_FRAMEWEB_MODEL;
  }
}
