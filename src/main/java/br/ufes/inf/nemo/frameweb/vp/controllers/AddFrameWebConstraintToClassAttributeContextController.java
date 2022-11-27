package br.ufes.inf.nemo.frameweb.vp.controllers;

import java.awt.event.ActionEvent;
import java.util.logging.Level;
import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPContext;
import com.vp.plugin.action.VPContextActionController;
import com.vp.plugin.model.ICompositeValueSpecification;
import com.vp.plugin.model.IConstraintElement;
import com.vp.plugin.model.IModelElement;
import com.vp.plugin.model.factory.IModelElementFactory;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebClass;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebClassAttributeConstraint;
import br.ufes.inf.nemo.frameweb.vp.utils.FrameWebUtils;
import br.ufes.inf.nemo.vpzy.logging.Logger;

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
    // FIXME: not working for classes without stereotype (default persistent classes).

    // Checks if the attribute that was clicked belongs to a FrameWeb class.
    IModelElement modelElement = context.getModelElement();
    IModelElement parent = modelElement.getParent();
    if (parent != null) {
      FrameWebClass modelElementFrameWebClass = FrameWebUtils.getFrameWebClass(parent);

      // Enables the action if the constraint it represents can be applied to attributes of this
      // FrameWeb class.
      FrameWebClassAttributeConstraint frameWebClassAttributeConstraint =
          FrameWebClassAttributeConstraint.ofPluginUIID(action.getActionId());
      action.setEnabled(
          modelElementFrameWebClass == frameWebClassAttributeConstraint.getFrameWebClass());
    }
  }

  /** Called when the button is pressed. Add the constraint to the class attribute. */
  @Override
  public void performAction(VPAction action, VPContext context, ActionEvent event) {
    Logger.log(Level.FINE, "Performing action: Add FrameWeb Constraint (Class Attribute) > {0}",
        event.getActionCommand());

    // Determines the selected attribute.
    IModelElement selectedModelElement = context.getModelElement();
    if (selectedModelElement != null
        && IModelElementFactory.MODEL_TYPE_ATTRIBUTE.equals(selectedModelElement.getModelType())) {
      // Determine which FrameWeb Class Attribute Constraint to apply from the selected menu item.
      FrameWebClassAttributeConstraint frameWebClassAttributeConstraint =
          FrameWebClassAttributeConstraint.ofPluginUIID(action.getActionId());

      // Adds the constraint to the selected attribute.
      Logger.log(Level.INFO, "Adding constraint {0} to {1}",
          new Object[] {frameWebClassAttributeConstraint.getSpecification(),
              selectedModelElement.getName()});
      IConstraintElement constraintElement =
          IModelElementFactory.instance().createConstraintElement();
      ICompositeValueSpecification constraintSpec =
          IModelElementFactory.instance().createCompositeValueSpecification();
      constraintElement.setName(frameWebClassAttributeConstraint.getName());
      constraintSpec.setValue(frameWebClassAttributeConstraint.getSpecification());
      constraintElement.setSpecification(constraintSpec);
      constraintElement.addConstrainedElement(selectedModelElement);

      // FIXME: have a constraint manager to avoid repetition.
      // FIXME: check if this can be applied to multiple elements at once.
    }
  }
}
