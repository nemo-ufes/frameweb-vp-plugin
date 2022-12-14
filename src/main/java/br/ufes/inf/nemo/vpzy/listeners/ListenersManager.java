package br.ufes.inf.nemo.vpzy.listeners;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import com.vp.plugin.diagram.IDiagramListener;
import com.vp.plugin.diagram.IDiagramUIModel;
import com.vp.plugin.model.IModelElement;
import com.vp.plugin.model.IProject;
import com.vp.plugin.model.IProjectDiagramListener;
import com.vp.plugin.model.IProjectListener;
import com.vp.plugin.model.IProjectModelListener;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import br.ufes.inf.nemo.vpzy.utils.ProjectManagerUtils;

/**
 * Utility class that manages listeners in Visual Paradigm projects.
 * 
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
 */
public class ListenersManager {
  /** Value for property change name that indicates a child has been added. */
  public static final String PROP_CHILD_ADDED = "childAdded";

  /**
   * Value for property change name that indicates that something that references the element has
   * been added.
   */
  public static final String PROP_REFERENCED_BY_ADDED = "referencedByAdded";

  /**
   * Value for property change name that indicates that something that references the element has
   * been removed.
   */
  public static final String PROP_REFERENCED_BY_REMOVED = "referencedByRemoved";

  /** The project listener to be used with the plug-in. */
  protected IProjectListener projectListener;

  /** Project diagram listeners to use with the plug-in. */
  protected Set<IProjectDiagramListener> projectDiagramListeners = new HashSet<>();

  /** Project model listeners to use with the plug-in. */
  protected Set<IProjectModelListener> projectModelListeners = new HashSet<>();

  /** Diagram listeners to use with the plug-in. */
  protected Set<IDiagramListener> diagramListeners = new HashSet<>();

  /** Model (property change) listeners to use with the plug-in. */
  protected Set<ManagedModelListener> modelListeners = new HashSet<>();

  /**
   * Sets up the listeners for the plug-in.
   */
  public void setup() {
    Logger.log(Level.FINER, "Setting up the Listeners Manager with default listeners");

    // Creates a managed project listener for the plug-in. This listener will eventually ask the
    // manager to attach the others.
    projectListener = new ManagedProjectListener(this);

    // Creates default project diagram, project model and diagram listeners.
    projectDiagramListeners.add(new ManagedProjectDiagramListener(this));
    projectModelListeners.add(new ManagedProjectModelListener(this));

    // Attaches the project listener to the current project.
    IProject project = ProjectManagerUtils.getCurrentProject();
    project.addProjectListener(projectListener);
  }

  /**
   * Shuts down the listeners for the plug-in.
   */
  public void shutdown() {
    Logger.log(Level.FINER, "Shutting down the Listeners Manager");

    IProject project = ProjectManagerUtils.getCurrentProject();
    project.removeProjectListener(projectListener);

    for (IProjectDiagramListener listener : projectDiagramListeners)
      project.removeProjectDiagramListener(listener);

    for (IProjectModelListener listener : projectModelListeners)
      project.removeProjectModelListener(listener);
  }

  public void addDiagramListener(IDiagramListener listener) {
    Logger.log(Level.FINEST, "Adding {0} to the list of diagram listeners", listener.getClass());
    diagramListeners.add(listener);
  }

  public void addModelListener(ManagedModelListener listener) {
    Logger.log(Level.FINEST, "Adding {0} to the list of model listeners", listener.getClass());
    modelListeners.add(listener);
  }

  /**
   * Attaches all project diagram listeners registered under this manager to the given project. This
   * method is usually called by project listeners when projects are created or opened.
   * 
   * @param project The given project.
   */
  public void attachProjectDiagramListeners(IProject project) {
    Logger.log(Level.FINEST, "Attaching {0} project diagram listeners to project {1} ({2})",
        new Object[] {projectDiagramListeners.size(), project.getName(), project.getId()});
    for (IProjectDiagramListener listener : projectDiagramListeners)
      project.addProjectDiagramListener(listener);
  }

  /**
   * Attaches all project model listeners registered under this manager to the given project. This
   * method is usually called by project listeners when projects are created or opened.
   * 
   * @param project The given project.
   */
  public void attachProjectModelListeners(IProject project) {
    Logger.log(Level.FINEST, "Attaching {0} project model listeners to project {1} ({2})",
        new Object[] {projectModelListeners.size(), project.getName(), project.getId()});
    for (IProjectModelListener listener : projectModelListeners)
      project.addProjectModelListener(listener);
  }

  /**
   * Attaches all diagram listeners registered under this manager to the given diagram. This method
   * is usually called by project diagram listeners when diagrams are added.
   * 
   * @param project The given project.
   */
  public void attachDiagramListeners(IDiagramUIModel diagramUIModel) {
    Logger.log(Level.FINEST, "Attaching {0} diagram listeners to diagram {1}",
        new Object[] {diagramListeners.size(), diagramUIModel.getName()});
    for (IDiagramListener listener : diagramListeners)
      diagramUIModel.addDiagramListener(listener);
  }

  /**
   * Attaches all model listeners registered under this manager to the given model element. This
   * method is usually called by project model listeners when model elements are added.
   * 
   * @param project The given project.
   */
  public void attachModelListeners(IModelElement modelElement) {
    for (ManagedModelListener listener : modelListeners) {
      if (listener.hasModelType()) {
        if (listener.getModelType().equals(modelElement.getModelType())) {
          Logger.log(Level.FINEST,
              "Attaching typed model listener {0} to model element {1} (type: {2})",
              new Object[] {listener.getClass().getName(), modelElement.getName(),
                  modelElement.getModelType()});
          modelElement.addPropertyChangeListener(listener);
        }
      } else {
        Logger.log(Level.FINEST,
            "Attaching generic model listener {0} to model element {1} (type: {2})",
            new Object[] {listener.getClass().getName(), modelElement.getName(),
                modelElement.getModelType()});
        modelElement.addPropertyChangeListener(listener);
      }
    }
  }
}
