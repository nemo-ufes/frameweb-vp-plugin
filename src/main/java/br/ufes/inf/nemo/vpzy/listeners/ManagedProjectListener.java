package br.ufes.inf.nemo.vpzy.listeners;

import java.util.logging.Level;
import com.vp.plugin.model.IModelElement;
import com.vp.plugin.model.IProject;
import com.vp.plugin.model.IProjectListener;
import br.ufes.inf.nemo.vpzy.logging.Logger;

/**
 * Default project listener managed by the listeners manager. This listener will attach project
 * diagram listeners and project model listeners registered under the listeners manager when
 * projects are created or opened.
 * 
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
 */
public class ManagedProjectListener implements IProjectListener {
  /** The listeners manager that created this listener and provides others. */
  protected ListenersManager manager;

  public ManagedProjectListener(ListenersManager manager) {
    this.manager = manager;
  }

  @Override
  public void projectAfterOpened(IProject project) {
    // Nothing to do here.
  }

  @Override
  public void projectNewed(IProject project) {
    Logger.log(Level.FINER, "Listener acting on: project {0} created", project.getName());

    // Attach project diagram and project model listeners to newly created project.
    manager.attachProjectDiagramListeners(project);
    manager.attachProjectModelListeners(project);
  }

  @Override
  public void projectOpened(IProject project) {
    Logger.log(Level.FINER, "Listener acting on: project {0} opened", project.getName());

    // Attach project diagram and project model listeners to opened project.
    manager.attachProjectDiagramListeners(project);
    manager.attachProjectModelListeners(project);

    // Attach model listeners to the existing elements of the model.
    for (IModelElement modelElement : project.toAllLevelModelElementArray()) {
      if (modelElement != null) {
        manager.attachModelListeners(modelElement);
      }
    }
  }

  @Override
  public void projectPreSave(IProject project) {
    // Nothing to do here.
  }

  @Override
  public void projectRenamed(IProject project) {
    // Nothing to do here.
  }

  @Override
  public void projectSaved(IProject project) {
    // Nothing to do here.
  }
}
