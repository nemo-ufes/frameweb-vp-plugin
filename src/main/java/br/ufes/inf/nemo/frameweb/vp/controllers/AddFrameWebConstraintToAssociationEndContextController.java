package br.ufes.inf.nemo.frameweb.vp.controllers;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.Level;
import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPContext;
import com.vp.plugin.action.VPContextActionController;
import com.vp.plugin.model.IAssociationEnd;
import com.vp.plugin.model.IConstraintElement;
import com.vp.plugin.model.IModelElement;
import com.vp.plugin.model.factory.IModelElementFactory;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebAssociationEndConstraint;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebClass;
import br.ufes.inf.nemo.frameweb.vp.utils.FrameWebUtils;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import br.ufes.inf.nemo.vpzy.managers.ConstraintsManager;
import br.ufes.inf.nemo.vpzy.utils.ViewManagerUtils;

/**
 * Controller that handles the Add FrameWeb Constraint to Association End action, activated by a
 * context menu (right-click) for UML Association End elements.
 *
 * @author Vítor E. Silva Souza (<a href="http://www.inf.ufes.br/~vitorsouza/">...</a>)
 * @author Igor Sunderhus e Silva (<a href="https://github.com/igorssilva">...</a>)
 */
public class AddFrameWebConstraintToAssociationEndContextController
    implements VPContextActionController {
  /**
   * Called when the menu containing the button is accessed allowing for action manipulation, such
   * as enable/disable or selecting the button.
   */
  @Override
  public void update(VPAction action, VPContext context) {
    // Checks if what was clicked is an association end.
    IModelElement modelElement = context.getModelElement();
    if (modelElement instanceof IAssociationEnd) {
      IAssociationEnd associationEnd = (IAssociationEnd) modelElement;

      // Checks if both sides of the association refer to supported FrameWeb classes.
      IModelElement thisSideElement = associationEnd.getModelElement();
      IModelElement oppositeSideElement = associationEnd.getOppositeEnd().getModelElement();
      FrameWebClass thisSideElementFrameWebClass = FrameWebUtils.getFrameWebClass(thisSideElement);
      FrameWebClass oppositeSideElementFrameWebClass =
          FrameWebUtils.getFrameWebClass(oppositeSideElement);

      // Enables the action if the constraint it represents can be applied to association ends in
      // association between these FrameWeb classes.
      FrameWebAssociationEndConstraint frameWebAssociationEndConstraint =
          FrameWebAssociationEndConstraint.ofPluginUIID(action.getActionId());
      Logger.log(Level.FINE,
          "Updating action: Add FrameWeb Constraint (Association End) > {0}. Associated class: {1} -- {2}. Action refers to classes: {3}",
          new Object[] {action.getLabel(), thisSideElementFrameWebClass,
              oppositeSideElementFrameWebClass,
              Arrays.toString(frameWebAssociationEndConstraint.getFrameWebClasses())});
      boolean thisEnabled = false, oppositeEnabled = false;
      for (FrameWebClass clazz : frameWebAssociationEndConstraint.getFrameWebClasses()) {
        if (thisSideElementFrameWebClass == clazz) {
          thisEnabled = true;
        }
        if (oppositeSideElementFrameWebClass == clazz) {
          oppositeEnabled = true;
        }
      }
      action.setEnabled(thisEnabled && oppositeEnabled);
    }
  }

  /** Called when the button is pressed. Adds the constraint to the association end. */
  @Override
  public void performAction(VPAction action, VPContext context, ActionEvent event) {
    Logger.log(Level.CONFIG, "Performing action: Add FrameWeb Constraint (Association End) > {0}",
        event.getActionCommand());

    // Processes the selected association end.
    IModelElement selectedModelElement = context.getModelElement();
    if (IModelElementFactory.MODEL_TYPE_ASSOCIATION_END
        .equals(selectedModelElement.getModelType())) {
      IAssociationEnd associationEnd = (IAssociationEnd) selectedModelElement;
      String description = associationEnd.getOppositeEnd().getModelElement().getName() + " --> "
          + associationEnd.getModelElement().getName();

      // Determines which FrameWeb Association End Constraint to apply from the menu item.
      FrameWebAssociationEndConstraint frameWebAssociationEndConstraint =
          FrameWebAssociationEndConstraint.ofPluginUIID(action.getActionId());
      Logger.log(Level.INFO, "Adding constraint {0} to association end {1}",
          new Object[] {frameWebAssociationEndConstraint.getSpecification(), description});

      // Removes any disjoint constraints in the element, if any.
      FrameWebAssociationEndConstraint[] disjoints =
          frameWebAssociationEndConstraint.getDisjoints();
      if (disjoints != null) {
        Iterator<?> iterator = associationEnd.constraintsIterator();
        while (iterator.hasNext()) {
          Object obj = iterator.next();
          if (obj instanceof IConstraintElement) {
            IConstraintElement existingConstraintElement = (IConstraintElement) obj;
            for (FrameWebAssociationEndConstraint disjointConstraint : disjoints) {
              if (disjointConstraint.getPluginUIID().equals(existingConstraintElement.getName())) {
                Logger.log(Level.CONFIG, "Removing disjoint constraint {0} from {1}", new Object[] {
                    existingConstraintElement.getSpecification(), associationEnd.getName()});
                existingConstraintElement.removeConstrainedElement(associationEnd);
              }
            }
          }
        }
      }

      // If parameterized, complements the specification with a value from the user.
      String specification = frameWebAssociationEndConstraint.getSpecification();
      boolean parameterized = frameWebAssociationEndConstraint.isParameterized();
      if (parameterized) {
        // Uses a dialog to ask the user for the value of the parameter.
        String message =
            "Please provide a value for the constraint to be applied to the association end "
                + description + ":\n" + frameWebAssociationEndConstraint.getSpecification() + "=";
        String value = ViewManagerUtils.showInputDialog(message, "Constraint value",
            ViewManagerUtils.QUESTION_MESSAGE);

        // If the user actually cancelled or provided no value, don't add the constraint.
        if (value == null || value.trim().isEmpty()) {
          return;
        }

        specification = specification.replaceAll("<[a-zA-Z]*>", value);
      }

      // Adds the constraint to the selected association end.
      ConstraintsManager constraintsManager = ConstraintsManager.getInstance();
      IConstraintElement constraintElement = constraintsManager.getConstraint(
          frameWebAssociationEndConstraint.getPluginUIID(), specification, parameterized);
      constraintElement.addConstrainedElement(selectedModelElement);
    }
  }
}
