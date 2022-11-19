package br.ufes.inf.nemo.vpzy.managers;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import com.vp.plugin.ApplicationManager;
import com.vp.plugin.model.IModelElement;
import com.vp.plugin.model.IProject;
import com.vp.plugin.model.IStereotype;
import com.vp.plugin.model.factory.IModelElementFactory;
import br.ufes.inf.nemo.frameweb.vp.managers.FrameWebStereotypesManager;
import br.ufes.inf.nemo.vpzy.utils.ViewManagerUtils;

/**
 * Utility class that manages stereotypes in Visual Paradigm projects.
 * 
 * This class can be extended by plug-ins that want to provide a specific set of stereotypes. In
 * this case, a protected constructor with an IProject parameter needs to call the superclass
 * constructor with the same parameter and the init() method can be overridden so the plug-in
 * stereotypes manager creates the specific stereotypes it wants to serve. If default stereotypes
 * are to be included, super.init() should be called in the overridden method. For example:
 * 
 * <pre>{@code @Override
 * protected void init() {
 *   super.init();
 *
 *   for (String stereotypeName : myStereotypes) {
 *     if (!existingStereotypes.containsKey(stereotypeName)) {
 *       IStereotype newStereotype = IModelElementFactory.instance().createStereotype();
 *       newStereotype.setName(stereotypeName);
 *       newStereotype.setBaseType(IModelElementFactory.MODEL_TYPE_CLASS); // Adjust if needed.
 *       existingStereotypes.put(stereotypeName, newStereotype);
 *     }
 *   }
 * }
 * }</pre>
 *
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
 */
public class StereotypesManager {
  /** A map that holds a stereotype manager per project. */
  private static final Map<String, StereotypesManager> MANAGERS = new HashMap<>();

  /** Project to which the manager is associated. */
  protected IProject project;

  /** Existing stereotypes in the current project, indexed by name. */
  protected Map<String, IStereotype> existingStereotypes;

  /** Constructor. */
  protected StereotypesManager(IProject project) {
    this.project = project;
    init();
  }

  /**
   * Provides a StereotypesManager instance associated with the currently opened project. If no
   * manager is yet associated with the project, create a new default instance and associate it.
   * 
   * @return A StereotypesManager instance for the currently opened project.
   */
  public static final StereotypesManager getInstance() {
    return getInstance(ApplicationManager.instance().getProjectManager().getProject());
  }

  /**
   * Provides the StereotypesManager instance associated with the given project. If no manager is
   * yet associated with the project, create a new default instance and associate it.
   * 
   * @param project The given project.
   * @return A StereotypesManager instance for the given project.
   */
  public static final StereotypesManager getInstance(IProject project) {
    return getInstance(project, StereotypesManager.class);
  }

  /**
   * Provides the StereotypesManager instance associated with the currently opened project. If no
   * manager is yet associated with the project, create a new instance from the given class and
   * associate it.
   * 
   * @param clazz The given class, that must be StereotypesManager or one of its subclasses.
   * @return A StereotypesManager instance for the given project.
   */
  public static final <T extends StereotypesManager> StereotypesManager getInstance(
      Class<T> clazz) {
    return getInstance(ApplicationManager.instance().getProjectManager().getProject(), clazz);
  }

  /**
   * Provides the StereotypesManager instance associated with the given project. If no manager is
   * yet associated with the project, create a new instance from the given class and associate it.
   * 
   * @param project The given project.
   * @param clazz The given class, that must be StereotypesManager or one of its subclasses.
   * @return A StereotypesManager instance for the given project.
   */
  public static final <T extends StereotypesManager> StereotypesManager getInstance(
      IProject project, Class<T> clazz) {
    String projectId = project.getId();
    if (!MANAGERS.containsKey(projectId)) {
      // Tries to instantiate the class that was specified.
      StereotypesManager instance = null;
      try {
        Constructor<T> constructor = clazz.getDeclaredConstructor(IProject.class);
        constructor.setAccessible(true);
        instance = constructor.newInstance(project);
      }

      // If not possible, instantiate a default stereotypes manager.
      catch (Exception e) {
        ViewManagerUtils.showMessage(
            "Plug-in error: cannot instantiate " + clazz.getName() + ". A " + e.getClass().getName()
                + " is thrown. A default StereotypesManager is provided instead.");
        instance = new StereotypesManager(project);
      }

      // Stores the manager.
      MANAGERS.put(projectId, instance);
    }
    return MANAGERS.get(projectId);
  }

  /**
   * Initializes the stereotypes manager, i.e., indexes the existing stereotypes in order to make
   * them available to client classes that need them.
   */
  protected void init() {
    existingStereotypes = new HashMap<>();
    IModelElement[] allStereotypes =
        project.toAllLevelModelElementArray(IModelElementFactory.MODEL_TYPE_STEREOTYPE);
    for (IModelElement element : allStereotypes)
      existingStereotypes.put(element.getName(), (IStereotype) element);
  }

  /**
   * Creates a new stereotype in the project.
   * 
   * @param stereotypeName The name of the stereotype.
   * @param baseType The name of the base type of the stereotype, taken from
   *        {@code IModelElementFactory}.
   */
  protected void createStereotype(String stereotypeName, String baseType) {
    IStereotype newStereotype = IModelElementFactory.instance().createStereotype();
    newStereotype.setName(stereotypeName);
    newStereotype.setBaseType(baseType);
    existingStereotypes.put(stereotypeName, newStereotype);
  }

  /**
   * Checks if a stereotype with a given name exists and, if not, create it.
   * 
   * @param stereotypeName The name of the stereotype.
   * @param baseType The name of the base type of the stereotype, taken from
   *        {@code IModelElementFactory}.
   */
  protected void checkStereotype(String stereotypeName, String baseType) {
    if (!existingStereotypes.containsKey(stereotypeName))
      createStereotype(stereotypeName, baseType);
  }

  /**
   * Provides an existing stereotype, given its name.
   * 
   * @param stereotypeName The stereotype name.
   * @return The IStereotype instance with the given name, or {@code null} if no stereotype exists
   *         with that name.
   */
  public IStereotype getStereotype(String stereotypeName) {
    return existingStereotypes.get(stereotypeName);
  }
}
