package br.ufes.inf.nemo.vpzy.managers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import com.vp.plugin.model.ICompositeValueSpecification;
import com.vp.plugin.model.IConstraintElement;
import com.vp.plugin.model.IModelElement;
import com.vp.plugin.model.IProject;
import com.vp.plugin.model.factory.IModelElementFactory;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import br.ufes.inf.nemo.vpzy.utils.ProjectManagerUtils;

/**
 * Utility class that manages constraints in Visual Paradigm projects.
 * 
 * Constraints can be created on demand, this manager avoids having duplicated constraint elements
 * in the project. There is no need, however, to create all constraints upfront, as done by the
 * stereotypes manager. This would not work with constraints because some of them are parametereized
 * (e.g., {@code column = column_name}).
 *
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
 */
public class ConstraintsManager {
  /** A map that holds a constraint manager per project. */
  private static final Map<String, ConstraintsManager> MANAGERS = new HashMap<>();

  /** Project to which the manager is associated. */
  protected IProject project;

  /** Existing constraints in the current project, indexed by name. */
  protected Map<String, IConstraintElement> existingConstraints;

  protected ConstraintsManager(IProject project) {
    this.project = project;
    init();
  }

  /**
   * Provides a ConstraintsManager instance associated with the currently opened project. If no
   * manager is yet associated with the project, create a new default instance and associate it.
   * 
   * @return A ConstraintsManager instance for the currently opened project.
   */
  public static ConstraintsManager getInstance() {
    return getInstance(ProjectManagerUtils.getCurrentProject());
  }

  /**
   * Provides the ConstraintsManager instance associated with the given project. If no manager is
   * yet associated with the project, create a new instance and associate it.
   * 
   * @param project The given project.
   * @return A ConstraintsManager instance for the given project.
   */
  public static ConstraintsManager getInstance(IProject project) {
    String projectId = project.getId();
    Logger.log(Level.FINER, "Providing ConstraintsManager to project {0} (ID: {1})",
        new Object[] {project.getName(), projectId});

    // Checks if the constraints manager for this project has already been created and returns.
    if (!MANAGERS.containsKey(projectId)) {
      MANAGERS.put(projectId, new ConstraintsManager(project));
    }
    return MANAGERS.get(projectId);
  }

  /**
   * Initializes the constraints manager, i.e., indexes the existing constraints in order to make
   * them available to client classes that need them.
   */
  protected void init() {
    existingConstraints = new HashMap<>();
    IModelElement[] allConstraints =
        project.toAllLevelModelElementArray(IModelElementFactory.MODEL_TYPE_CONSTRAINT_ELEMENT);
    Logger.log(Level.INFO, "Initializing Constraints Manager with {0} constraints",
        allConstraints.length);
    for (IModelElement element : allConstraints)
      existingConstraints.put(element.getName(), (IConstraintElement) element);
  }

  /**
   * Creates a new constraint element for the project.
   * 
   * @param name The name of the constraint (internal).
   * @param specification The specification of the constraint (the expression that is shown in the
   *        model).
   * @return The newly created IConstraintElement.
   */
  protected IConstraintElement createConstraint(String name, String specification) {
    Logger.log(Level.FINE, "Registering a new constraint {0} with specification {1}",
        new Object[] {name, specification});
    IConstraintElement newConstraintElement =
        IModelElementFactory.instance().createConstraintElement();
    newConstraintElement.setName(name);

    // The specification has to be wrapped in a composite value specification.
    ICompositeValueSpecification constraintSpec =
        IModelElementFactory.instance().createCompositeValueSpecification();
    constraintSpec.setValue(specification);
    newConstraintElement.setSpecification(constraintSpec);

    return newConstraintElement;
  }

  /**
   * Provides a constraint element, given its name. If it doesn't exist yet, create it.
   * 
   * @param name The name of the constraint (internal).
   * @param specification The specification of the constraint (the expression that is shown in the
   *        model).
   * @param parameterized Indicates if the constraint is parameterized. Parameterized constraints
   *        cannot be shared and, thus, need a unique key for the map.
   * @return The IConstraintElement instance with the given name.
   */
  public IConstraintElement getConstraint(String name, String specification,
      boolean parameterized) {
    Logger.log(Level.FINER,
        "Checking the existence of constraint {0} with specification {1} (parameterized: {2})",
        new Object[] {name, specification, parameterized});

    // Establish the key for the constraint. Parameterized constraints must have unique keys as they
    // cannot be shared among different elements. Each can have a different parameter in the spec.
    String key = name;
    if (parameterized)
      key += "-" + UUID.randomUUID().toString();

    // If the constraint already exists, gets it from the map. Otherwise, creates and stores one.
    IConstraintElement constraintElement = null;
    if (existingConstraints.containsKey(key)) {
      constraintElement = existingConstraints.get(key);
    } else {
      constraintElement = createConstraint(name, specification);
      existingConstraints.put(key, constraintElement);
    }

    return constraintElement;
  }
}
