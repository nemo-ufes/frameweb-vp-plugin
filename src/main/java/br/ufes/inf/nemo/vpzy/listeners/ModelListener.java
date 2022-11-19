package br.ufes.inf.nemo.vpzy.listeners;

import java.beans.PropertyChangeListener;

public abstract class ModelListener implements PropertyChangeListener {
  private String modelType;

  /** Constructor for model listeners that apply to all elements. */
  public ModelListener() {}

  /** Constructor for model listeners that apply to a specific model element. */
  public ModelListener(String modelType) {
    this.modelType = modelType;
  }

  /** Indicates if the listener applies to a specific model element. */
  public boolean hasModelType() {
    return modelType != null;
  }

  public String getModelType() {
    return modelType;
  }
}
