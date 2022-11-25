package br.ufes.inf.nemo.frameweb.vp.controllers;

import java.awt.event.ActionEvent;
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
import br.ufes.inf.nemo.vpzy.utils.ModelElementUtils;

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

    // Determine which FrameWeb Class Attribute Constraint to apply from the selected menu item.
    FrameWebClassAttributeConstraint frameWebClassAttributeConstraint =
        FrameWebClassAttributeConstraint.ofPluginUIID(action.getActionId());

    // Collect the model elements whose diagram elements are currently selected.
    // FIXME: this gets the selected classes from the diagram. Not what I need!
    // Explore action, context and event objects to see if I can get the selected attributes.
    // If successful, maybe consider changing this in the other controllers as well?
    Set<IModelElement> selectedModelElements = ModelElementUtils.getSelectedModelElements();

    // For each model element selected, adds the selected constraint.
    for (IModelElement modelElement : selectedModelElements) {
      Logger.log(Level.INFO, "Adding constraint {0} to {1}",
          new Object[] {frameWebClassAttributeConstraint.getExpression(), modelElement.getName()});

      Logger.log(Level.SEVERE, "modelElement: {0}, name: {1}, type: {2}",
          new Object[] {modelElement, modelElement.getName(), modelElement.getModelType()});
    }
  }
}
