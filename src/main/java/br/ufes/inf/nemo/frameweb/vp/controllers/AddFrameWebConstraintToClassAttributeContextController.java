package br.ufes.inf.nemo.frameweb.vp.controllers;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPContext;
import com.vp.plugin.action.VPContextActionController;
import com.vp.plugin.model.IConstraintElement;
import com.vp.plugin.model.IModelElement;
import com.vp.plugin.model.factory.IModelElementFactory;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebClass;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebClassAttributeConstraint;
import br.ufes.inf.nemo.frameweb.vp.utils.FrameWebUtils;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import br.ufes.inf.nemo.vpzy.managers.ConstraintsManager;
import br.ufes.inf.nemo.vpzy.utils.ModelElementUtils;
import br.ufes.inf.nemo.vpzy.utils.ViewManagerUtils;

/**
 * Controller that handles the Add FrameWeb Constraint to Class Attribute action, activated by a
 * context menu (right-click) for UML Class Attribute elements.
 *
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
 */
public class AddFrameWebConstraintToClassAttributeContextController
    implements VPContextActionController {
  /**
   * Called when the menu containing the button is accessed allowing for action manipulation, such
   * as enable/disable or selecting the button.
   */
  @Override
  public void update(VPAction action, VPContext context) {
    // Checks if the attribute that was clicked belongs to a FrameWeb class.
    IModelElement modelElement = context.getModelElement();
    IModelElement parent = modelElement.getParent();
    if (parent != null) {
      FrameWebClass modelElementFrameWebClass = FrameWebUtils.getFrameWebClass(parent);

      // Enables the action if the constraint it represents can be applied to attributes of this
      // FrameWeb class.
      FrameWebClassAttributeConstraint frameWebClassAttributeConstraint =
          FrameWebClassAttributeConstraint.ofPluginUIID(action.getActionId());
      Logger.log(Level.FINE,
          "Updating action: Add FrameWeb Constraint (Class Attribute) > {0}. Attribute class: {1}. Action refers to classes: {2}",
          new Object[] {action.getLabel(), modelElementFrameWebClass,
              Arrays.toString(frameWebClassAttributeConstraint.getFrameWebClasses())});
      boolean enabled = false;
      for (FrameWebClass clazz : frameWebClassAttributeConstraint.getFrameWebClasses()) {
        if (modelElementFrameWebClass == clazz) {
          enabled = true;
        }
      }
      action.setEnabled(enabled);
    }
  }

  /** Called when the button is pressed. Adds the constraint to the class attribute. */
  @Override
  public void performAction(VPAction action, VPContext context, ActionEvent event) {
    Logger.log(Level.CONFIG, "Performing action: Add FrameWeb Constraint (Class Attribute) > {0}",
        event.getActionCommand());

    // Processes all selected attributes.
    Set<IModelElement> selectedModelElements = ModelElementUtils.getSelectedModelElements();
    for (IModelElement selectedModelElement : selectedModelElements) {
      if (IModelElementFactory.MODEL_TYPE_ATTRIBUTE.equals(selectedModelElement.getModelType())) {
        // Determines which FrameWeb Class Attribute Constraint to apply from the menu item.
        FrameWebClassAttributeConstraint frameWebClassAttributeConstraint =
            FrameWebClassAttributeConstraint.ofPluginUIID(action.getActionId());
        Logger.log(Level.INFO, "Adding constraint {0} to {1}", new Object[] {
            frameWebClassAttributeConstraint.getSpecification(), selectedModelElement.getName()});

        // Removes any disjoint constraints in the element, if any.
        FrameWebClassAttributeConstraint[] disjoints =
            frameWebClassAttributeConstraint.getDisjoints();
        if (disjoints != null) {
          Iterator<?> iterator = selectedModelElement.constraintsIterator();
          while (iterator.hasNext()) {
            Object obj = iterator.next();
            if (obj instanceof IConstraintElement) {
              IConstraintElement existingConstraintElement = (IConstraintElement) obj;
              for (FrameWebClassAttributeConstraint disjointConstraint : disjoints) {
                if (disjointConstraint.getPluginUIID()
                    .equals(existingConstraintElement.getName())) {
                  Logger.log(Level.CONFIG, "Removing disjoing stereotype {0} from {1}",
                      new Object[] {existingConstraintElement.getSpecification(),
                          selectedModelElement.getName()});
                  existingConstraintElement.removeConstrainedElement(selectedModelElement);
                }
              }
            }
          }
        }

        // If parameterized, complements the specification with a value from the user.
        String specification = frameWebClassAttributeConstraint.getSpecification();
        boolean parameterized = frameWebClassAttributeConstraint.isParameterized();
        if (parameterized) {
          // Uses a dialog to ask the user for the value of the parameter.
          String message = "Please provide a value for the constraint to be applied to "
              + selectedModelElement.getName() + ":\n"
              + frameWebClassAttributeConstraint.getSpecification() + "=";
          String value = ViewManagerUtils.showInputDialog(message, "Constraint value",
              ViewManagerUtils.QUESTION_MESSAGE);
          specification = specification + "=" + value;

          // If the user actually cancelled or provided no value, move on to the next element.
          if (value == null || value.trim().isEmpty()) {
            continue;
          }
        }

        // Adds the constraint to the selected attribute.
        ConstraintsManager constraintsManager = ConstraintsManager.getInstance();
        IConstraintElement constraintElement =
            constraintsManager.getConstraint(frameWebClassAttributeConstraint.getPluginUIID(),
                specification, parameterized);
        constraintElement.addConstrainedElement(selectedModelElement);
      }
    }
  }
}
