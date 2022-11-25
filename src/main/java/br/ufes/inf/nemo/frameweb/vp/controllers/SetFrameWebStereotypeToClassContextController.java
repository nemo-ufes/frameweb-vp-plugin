package br.ufes.inf.nemo.frameweb.vp.controllers;

import java.awt.event.ActionEvent;
import java.util.Set;
import java.util.logging.Level;
import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPContext;
import com.vp.plugin.action.VPContextActionController;
import com.vp.plugin.model.IModelElement;
import com.vp.plugin.model.IStereotype;
import com.vp.plugin.model.factory.IModelElementFactory;
import br.ufes.inf.nemo.frameweb.vp.managers.FrameWebStereotypesManager;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebClass;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebPackage;
import br.ufes.inf.nemo.frameweb.vp.utils.FrameWebUtils;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import br.ufes.inf.nemo.vpzy.managers.StereotypesManager;
import br.ufes.inf.nemo.vpzy.utils.ModelElementUtils;

/**
 * Controller that handles the Set FrameWeb Stereotype to Package action, activated by a context
 * menu (right-click) for UML Class elements.
 *
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
 */
public class SetFrameWebStereotypeToClassContextController implements VPContextActionController {
  /**
   * Called when the menu containing the button is accessed allowing for action manipulation, such
   * as enable/disable or selecting the button.
   */
  @Override
  public void update(VPAction action, VPContext context) {
    // Checks if the class that was clicked belongs to a package with a FrameWeb stereotype.
    IModelElement modelElement = context.getModelElement();
    IModelElement parent = modelElement.getParent();
    if (parent != null && IModelElementFactory.MODEL_TYPE_PACKAGE.equals(parent.getModelType())) {
      FrameWebPackage modelElementFrameWebPackage = FrameWebUtils.getFrameWebPackage(parent);
      if (modelElementFrameWebPackage != FrameWebPackage.NOT_A_FRAMEWEB_PACKAGE) {
        // Checks to which FrameWeb package the action belongs.
        FrameWebClass actionFrameWebClass = FrameWebClass.ofPluginUIID(action.getActionId());
        FrameWebPackage actionFrameWebPackage = actionFrameWebClass.getFrameWebPackage();
        Logger.log(Level.SEVERE,
            "actionId: {0}, actionFrameWebClass: {1}, actionFrameWebPackage: {2}",
            new Object[] {action.getActionId(), actionFrameWebClass, actionFrameWebPackage});

        // Disable actions that refer to a different package.
        // action.setEnabled(modelElementFrameWebPackage == actionFrameWebPackage);
      }
    }
  }

  /** Called when the button is pressed. Sets the class' stereotype. */
  @Override
  public void performAction(VPAction action, VPContext context, ActionEvent event) {
    Logger.log(Level.FINE, "Performing action: Set FrameWeb Stereotype (Class) > {0}",
        event.getActionCommand());

    // Gets the FrameWeb stereotypes manager associated with the current project.
    StereotypesManager stereotypesManager =
        StereotypesManager.getInstance(FrameWebStereotypesManager.class);

    // Determine which FrameWeb Model to apply from the menu item that has been selected.
    FrameWebClass frameWebClass = FrameWebClass.ofPluginUIID(action.getActionId());

    // Collect the model elements whose diagram elements are currently selected.
    Set<IModelElement> selectedModelElements = ModelElementUtils.getSelectedModelElements();

    // For each model element selected, apply a stereotype that refers to the FrameWeb model.
    for (IModelElement modelElement : selectedModelElements) {
      Logger.log(Level.INFO, "Applying stereotype {0} to {1}",
          new Object[] {frameWebClass.getStereotypeName(), modelElement.getName()});

      // Remove other FrameWeb stereotypes the class may have, as they are disjoint.
      IStereotype[] existingStereotypes = modelElement.toStereotypeModelArray();
      if (existingStereotypes != null) {
        for (IStereotype existingStereotype : existingStereotypes) {
          FrameWebClass clazz = FrameWebClass.ofStereotype(existingStereotype.getName());
          if (clazz != FrameWebClass.NOT_A_FRAMEWEB_CLASS)
            modelElement.removeStereotype(existingStereotype);
        }
      }

      // Add the new FrameWeb class stereotype.
      IStereotype newStereotype =
          stereotypesManager.getStereotype(frameWebClass.getStereotypeName());
      modelElement.addStereotype(newStereotype);
    }
  }
}
