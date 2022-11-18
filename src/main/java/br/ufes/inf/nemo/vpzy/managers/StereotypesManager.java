package br.ufes.inf.nemo.vpzy.managers;

import java.util.HashMap;
import java.util.Map;
import com.vp.plugin.ApplicationManager;
import com.vp.plugin.model.IModelElement;
import com.vp.plugin.model.IProject;
import com.vp.plugin.model.factory.IModelElementFactory;

/**
 * Utility class that manages stereotypes in Visual Paradigm projects.
 *
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
 */
public class StereotypesManager {
  /** A map that holds a stereotype manager per project. */
  private static final Map<String, StereotypesManager> MANAGERS = new HashMap<>();

  /** Project to which the manager is associated. */
  protected IProject project;

  /** Existing stereotypes in the current project, indexed by name. */
  protected Map<String, IModelElement> existingStereotypes;

  /** Constructor. */
  protected StereotypesManager(IProject project) {
    this.project = project;
  }

  /**
   * Provides the StereotypesManager instance associated with the currently opened project. If no
   * manager is yet associated with the project, create a new one and associate it.
   * 
   * @return A StereotypesManager instance for the currently opened project.
   */
  public static final StereotypesManager getInstance() {
    return getInstance(ApplicationManager.instance().getProjectManager().getProject());
  }

  /**
   * Provides the StereotypesManager instance associated with the given project. If no manager is
   * yet associated with the project, create a new one and associate it.
   * 
   * @param project The given project.
   * @return A StereotypesManager instance for the given project.
   */
  public static final StereotypesManager getInstance(IProject project) {
    String projectId = project.getId();
    if (!MANAGERS.containsKey(projectId))
      MANAGERS.put(projectId, new StereotypesManager(project));
    return MANAGERS.get(projectId);
  }

  /**
   * Provides a map of the existing stereotypes in the project, indexed by name.
   * 
   * @return The project's stereotype map.
   */
  public Map<String, IModelElement> getExistingStereotypes() {
    if (existingStereotypes == null) {
      existingStereotypes = new HashMap<>();
      IModelElement[] allStereotypes =
          project.toAllLevelModelElementArray(IModelElementFactory.MODEL_TYPE_STEREOTYPE);
      for (IModelElement element : allStereotypes)
        existingStereotypes.put(element.getName(), element);
    }
    return existingStereotypes;
  }
}
