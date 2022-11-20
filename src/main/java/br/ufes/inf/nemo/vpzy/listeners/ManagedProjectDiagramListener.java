package br.ufes.inf.nemo.vpzy.listeners;

import java.util.logging.Level;
import com.vp.plugin.diagram.IDiagramUIModel;
import com.vp.plugin.model.IProject;
import com.vp.plugin.model.IProjectDiagramListener;
import br.ufes.inf.nemo.vpzy.logging.Logger;

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
    Logger.log(Level.FINER, "Listener acting on: diagram {0} added to project {1}",
        new Object[] {diagramUIModel.getName(), project.getName()});
    manager.attachDiagramListeners(diagramUIModel);
  }

  @Override
  public void diagramRemoved(IProject arg0, IDiagramUIModel arg1) {
    // Nothing to do here.
  }
}
