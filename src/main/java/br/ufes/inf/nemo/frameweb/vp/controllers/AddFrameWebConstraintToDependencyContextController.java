package br.ufes.inf.nemo.frameweb.vp.controllers;

import br.ufes.inf.nemo.frameweb.vp.model.FrameWebClass;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebDependencyConstraint;
import br.ufes.inf.nemo.frameweb.vp.utils.FrameWebUtils;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import br.ufes.inf.nemo.vpzy.managers.ConstraintsManager;
import br.ufes.inf.nemo.vpzy.utils.ViewManagerUtils;
import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPContext;
import com.vp.plugin.action.VPContextActionController;
import com.vp.plugin.model.IConstraintElement;
import com.vp.plugin.model.IDependency;
import com.vp.plugin.model.IModelElement;
import com.vp.plugin.model.factory.IModelElementFactory;

import java.awt.event.ActionEvent;
import java.util.*;
import java.util.logging.Level;

/**
 * Controller that handles the Add FrameWeb Constraint to Dependency action, activated by a
 * context menu (right-click) for UML Dependency elements.
 *
 * @author <a href="https://github.com/gabrielgatti7">Gabriel Gatti da Silva</a>
 */
public class AddFrameWebConstraintToDependencyContextController implements VPContextActionController {
    /**
     * Called when the menu containing the button is accessed allowing for action manipulation, such
     * as enable/disable or selecting the button.
     */
    @Override
    public void update(VPAction action, VPContext context) {
        // Checks if what was clicked is a dependency.
        IModelElement modelElement = context.getModelElement();
        if (modelElement instanceof IDependency) {
            IDependency dependency = (IDependency) modelElement;

            // Creates a list of FrameWebClasses of the classes connected by the dependency element.
            List<FrameWebClass> connectedFrameWebClasses = new ArrayList<>();
            connectedFrameWebClasses.add(FrameWebUtils.getFrameWebClass(dependency.getFrom()));
            connectedFrameWebClasses.add(FrameWebUtils.getFrameWebClass(dependency.getTo()));

            // Enables the action if the constraint it represents can be applied to the dependency in
            // between these FrameWeb classes.
            FrameWebDependencyConstraint frameWebDependencyConstraint =
                    FrameWebDependencyConstraint.ofPluginUIID(action.getActionId());

            Logger.log(Level.FINE,
                    "Updating action: Add FrameWeb Constraint (Dependency) > {0}. Associated class: {1} -- {2}. Action refers to classes: {3}",
                    new Object[] {action.getLabel(), connectedFrameWebClasses.get(0),
                            connectedFrameWebClasses.get(1),
                            Arrays.toString(frameWebDependencyConstraint.getFrameWebClasses())});
            boolean thisEnabled = false, oppositeEnabled = false;
            for (FrameWebClass clazz : frameWebDependencyConstraint.getFrameWebClasses()) {
                if (connectedFrameWebClasses.get(0) == clazz) {
                    thisEnabled = true;
                }
                if (connectedFrameWebClasses.get(1) == clazz) {
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
        if (IModelElementFactory.MODEL_TYPE_DEPENDENCY
                .equals(selectedModelElement.getModelType())) {

            // Determines which FrameWeb Dependency Constraint to apply from the menu item.
            FrameWebDependencyConstraint frameWebDependencyConstraint =
                    FrameWebDependencyConstraint.ofPluginUIID(action.getActionId());

            // If parameterized, complements the specification with a value from the user.
            String specification = frameWebDependencyConstraint.getSpecification();
            boolean parameterized = frameWebDependencyConstraint.isParameterized();
            if (parameterized) {
                // Uses a dialog to ask the user for the value of the parameter.
                String message =
                        "Please provide a value for the constraint to be applied to the dependency " + ":\n"
                                + frameWebDependencyConstraint.getSpecification() + "=";
                String value = ViewManagerUtils.showInputDialog(message, "Constraint value",
                        ViewManagerUtils.QUESTION_MESSAGE);

                // If the user actually cancelled or provided no value, don't add the constraint.
                if (value == null || value.trim().isEmpty()) {
                    return;
                }

                specification = specification + "=" + value;
            }

            // Adds the constraint to the selected association end.
            ConstraintsManager constraintsManager = ConstraintsManager.getInstance();
            IConstraintElement constraintElement = constraintsManager.getConstraint(
                    frameWebDependencyConstraint.getPluginUIID(), specification, parameterized);
            constraintElement.addConstrainedElement(selectedModelElement);

            // Get the name of all constraints from the selected dependency
            List<String> constraintNames = new ArrayList<>();
            selectedModelElement.constraintsIterator().forEachRemaining(element ->{
                final IConstraintElement constraintElem = (IConstraintElement) element;
                constraintNames.add(constraintElem.getSpecification().getValueAsString());
            });

            // Create the name of the dependency based on the list of names
            String dependencyName = null;
            if(constraintNames.size() == 1){
                dependencyName = "{" + constraintNames.get(0) + "}";
            }
            else {
                for(int i = 0; i < constraintNames.size(); i++) {
                    if(i == 0)
                        dependencyName = "{" + constraintNames.get(i) + ", ";
                    else if(i == constraintNames.size()-1)
                        dependencyName = dependencyName + constraintNames.get(i) + "}";
                    else
                        dependencyName = dependencyName + constraintNames.get(i) + ", ";
                }
            }

            selectedModelElement.setName(dependencyName);
            Logger.log(Level.INFO, "Adding constraint {0} to the selected dependency element.",
                    new Object[]{frameWebDependencyConstraint.getSpecification()});
        }
    }
}