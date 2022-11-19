package br.ufes.inf.nemo.vpzy.listeners;

import java.beans.PropertyChangeListener;

/**
 * Listener that handles changes in model elements and is managed by the listeners manager. Beyond
 * the PropertyChangeListener that it implements, this kind of listener can specify a specific model
 * type in which it is interested, so it will only be attached to that specific type of element by
 * the listeners manager. If not specified, it will be attached to all model elements.
 * 
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
 */
public abstract class ManagedModelListener implements PropertyChangeListener {
  /** The type of model element in which the listener is interested. */
  private String modelType;

  /** Constructor for model listeners that apply to all elements. */
  public ManagedModelListener() {}

  /** Constructor for model listeners that apply to a specific model element. */
  public ManagedModelListener(String modelType) {
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
