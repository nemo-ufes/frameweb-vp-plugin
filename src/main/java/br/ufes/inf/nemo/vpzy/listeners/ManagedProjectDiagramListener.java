package br.ufes.inf.nemo.vpzy.listeners;

import com.vp.plugin.diagram.IDiagramUIModel;
import com.vp.plugin.model.IProject;
import com.vp.plugin.model.IProjectDiagramListener;
import br.ufes.inf.nemo.vpzy.utils.ViewManagerUtils;

/**
 * Default project diagram listener managed by the listeners manager. This listener will attach
 * diagram listeners registered under the listeners manager when diagrams are added.
 * 
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
 */
public class ManagedProjectDiagramListener implements IProjectDiagramListener {
  /** The listeners manager that created this listener and provides others. */
  protected ListenersManager manager;

  public ManagedProjectDiagramListener(ListenersManager manager) {
    this.manager = manager;
  }

  @Override
  public void diagramAdded(IProject project, IDiagramUIModel diagramUIModel) {
    ViewManagerUtils.showMessage("Diagram " + diagramUIModel.getType() + " : "
        + diagramUIModel.getName() + " added. Attaching listeners.");
    manager.attachDiagramListeners(diagramUIModel);
  }

  @Override
  public void diagramRemoved(IProject arg0, IDiagramUIModel arg1) {
    // Nothing to do here.
  }
}
