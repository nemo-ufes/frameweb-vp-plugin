package br.ufes.inf.nemo.vpzy.listeners;

import com.vp.plugin.model.IModelElement;
import com.vp.plugin.model.IProject;
import com.vp.plugin.model.IProjectModelListener;

/**
 * Default project model listener managed by the listeners manager. This listener will attach model
 * listeners registered under the listeners manager when models are added.
 * 
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
 */
public class ManagedProjectModelListener implements IProjectModelListener {
  /** The listeners manager that created this listener and provides others. */
  protected ListenersManager manager;

  public ManagedProjectModelListener(ListenersManager manager) {
    this.manager = manager;
  }

  @Override
  public void modelAdded(IProject project, IModelElement modelElement) {
    // When new model elements are added, attach the appropriate listeners to them.
    manager.attachModelListeners(modelElement);
  }

  @Override
  public void modelRemoved(IProject arg0, IModelElement modelElement) {
    // Nothing to do here.
  }
}
