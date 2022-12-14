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
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebPackage;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import br.ufes.inf.nemo.vpzy.managers.StereotypesManager;
import br.ufes.inf.nemo.vpzy.utils.ModelElementUtils;

/**
 * Controller that handles the Set FrameWeb Stereotype to Package action, activated by a context
 * menu (right-click) for UML Package elements.
 *
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
 */
public class SetFrameWebStereotypeToPackageContextController implements VPContextActionController {
  /**
   * Called when the menu containing the button is accessed allowing for action manipulation, such
   * as enable/disable or selecting the button. DOES NOT apply to this class.
   */
  @Override
  public void update(VPAction action, VPContext context) {}

  /** Called when the button is pressed. Sets the package's stereotype. */
  @Override
  public void performAction(VPAction action, VPContext context, ActionEvent event) {
    Logger.log(Level.CONFIG, "Performing action: Set FrameWeb Stereotype (Package) > {0}",
        event.getActionCommand());

    // Gets the FrameWeb stereotypes manager associated with the current project.
    StereotypesManager stereotypesManager =
        StereotypesManager.getInstance(FrameWebStereotypesManager.class);

    // Determines which FrameWeb package it is from the menu item that has been selected.
    FrameWebPackage frameWebPackage = FrameWebPackage.ofPluginUIID(action.getActionId());

    // Collects the model elements whose diagram elements are currently selected.
    Set<IModelElement> selectedModelElements = ModelElementUtils.getSelectedModelElements();

    // For each model element selected, applies the selected stereotype.
    IStereotype newStereotype = stereotypesManager.getStereotype(
        frameWebPackage.getStereotypeName(), IModelElementFactory.MODEL_TYPE_PACKAGE);
    for (IModelElement modelElement : selectedModelElements) {
      if (IModelElementFactory.MODEL_TYPE_PACKAGE.equals(modelElement.getModelType())) {
        Logger.log(Level.INFO, "Applying stereotype {0} to {1}",
            new Object[] {frameWebPackage.getStereotypeName(), modelElement.getName()});

        // Removes other FrameWeb stereotypes the package may have, as they are disjoint.
        IStereotype[] existingStereotypes = modelElement.toStereotypeModelArray();
        if (existingStereotypes != null) {
          for (IStereotype existingStereotype : existingStereotypes) {
            FrameWebPackage pkg = FrameWebPackage.ofStereotype(existingStereotype.getName());
            if (pkg != FrameWebPackage.NOT_A_FRAMEWEB_PACKAGE) {
              Logger.log(Level.CONFIG, "Removing disjoint stereotype {0} from {1}",
                  new Object[] {existingStereotype, modelElement.getName()});
              modelElement.removeStereotype(existingStereotype);
            }
          }
        }

        // Adds the new FrameWeb package stereotype.
        modelElement.addStereotype(newStereotype);
      }
    }
  }
}
