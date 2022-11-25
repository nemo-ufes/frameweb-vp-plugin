package br.ufes.inf.nemo.frameweb.vp.controllers;

import java.awt.event.ActionEvent;
import java.util.Set;
import java.util.logging.Level;
import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPContext;
import com.vp.plugin.action.VPContextActionController;
import com.vp.plugin.model.IConstraint;
import com.vp.plugin.model.IModelElement;
import com.vp.plugin.model.factory.IModelElementFactory;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebClass;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebClassConstraint;
import br.ufes.inf.nemo.frameweb.vp.utils.FrameWebUtils;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import br.ufes.inf.nemo.vpzy.utils.ModelElementUtils;

/**
 * Controller that handles the Add FrameWeb Constraint to Class action, activated by a context menu
 * (right-click) for UML Class elements.
 *
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
 */
public class AddFrameWebConstraintToClassContextController implements VPContextActionController {
  /**
   * Called when the menu containing the button is accessed allowing for action manipulation, such
   * as enable/disable or selecting the button.
   */
  @Override
  public void update(VPAction action, VPContext context) {
    // Checks if the class that was clicked is a FrameWeb class.
    IModelElement modelElement = context.getModelElement();
    FrameWebClass modelElementFrameWebClass = FrameWebUtils.getFrameWebClass(modelElement);

    // Enables the action if the constraint it represents can be applied to the FrameWeb class.
    FrameWebClassConstraint frameWebClassConstraint =
        FrameWebClassConstraint.ofPluginUIID(action.getActionId());
    action.setEnabled(modelElementFrameWebClass == frameWebClassConstraint.getFrameWebClass());
  }

  /** Called when the button is pressed. Add the constraint to the class. */
  @Override
  public void performAction(VPAction action, VPContext context, ActionEvent event) {
    Logger.log(Level.FINE, "Performing action: Add FrameWeb Constraint (Class) > {0}",
        event.getActionCommand());

    // Determine which FrameWeb Class Constraint to apply from the menu item that has been selected.
    FrameWebClassConstraint frameWebClassConstraint =
        FrameWebClassConstraint.ofPluginUIID(action.getActionId());

    // Collect the model elements whose diagram elements are currently selected.
    Set<IModelElement> selectedModelElements = ModelElementUtils.getSelectedModelElements();

    // For each model element selected, adds the selected constraint.
    for (IModelElement modelElement : selectedModelElements) {
      Logger.log(Level.INFO, "Adding constraint {0} to {1}",
          new Object[] {frameWebClassConstraint.getExpression(), modelElement.getName()});
      IConstraint constraint = IModelElementFactory.instance().createConstraint();
      // TODO: if constraint is parameterized, ask for paramter.
      constraint.setExpression(frameWebClassConstraint.getExpression());
      modelElement.addChild(constraint);
    }
  }
}
