package br.ufes.inf.nemo.frameweb.vp.controllers;

import java.awt.event.ActionEvent;
import java.util.Arrays;
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
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebClassAttribute;
import br.ufes.inf.nemo.frameweb.vp.utils.FrameWebUtils;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import br.ufes.inf.nemo.vpzy.managers.StereotypesManager;
import br.ufes.inf.nemo.vpzy.utils.ModelElementUtils;

/**
 * Controller that handles the Set FrameWeb Stereotype to Class Attribute action, activated by a
 * context menu (right-click) for UML Class Attribute elements.
 *
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
 */
public class SetFrameWebStereotypeToClassAttributeContextController
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

      // Enables the action if the attribute it represents can be created in this FrameWeb class.
      FrameWebClassAttribute frameWebClassAttribute =
          FrameWebClassAttribute.ofPluginUIID(action.getActionId());
      Logger.log(Level.FINE,
          "Updating action: Add FrameWeb Stereotype (Class Attribute) > {0}. Attribute class: {1}. Action refers to classes: {2}",
          new Object[] {action.getLabel(), modelElementFrameWebClass,
              Arrays.toString(frameWebClassAttribute.getFrameWebClasses())});
      boolean enabled = false;
      for (FrameWebClass clazz : frameWebClassAttribute.getFrameWebClasses()) {
        if (modelElementFrameWebClass == clazz) {
          enabled = true;
        }
      }
      action.setEnabled(enabled);
    }
  }

  /** Called when the button is pressed. Sets the class attribute's stereotype. */
  @Override
  public void performAction(VPAction action, VPContext context, ActionEvent event) {
    Logger.log(Level.CONFIG, "Performing action: Set FrameWeb Stereotype (Class Attribute) > {0}",
        event.getActionCommand());

    // Gets the FrameWeb stereotypes manager associated with the current project.
    StereotypesManager stereotypesManager =
        StereotypesManager.getInstance(FrameWebStereotypesManager.class);

    // Determines which FrameWeb class attribute it is from the menu item that has been selected.
    FrameWebClassAttribute frameWebClassAttribute =
        FrameWebClassAttribute.ofPluginUIID(action.getActionId());

    // Collects the model elements whose diagram elements are currently selected.
    Set<IModelElement> selectedModelElements = ModelElementUtils.getSelectedModelElements();

    // For each class attribute selected, applies the selected stereotype.
    IStereotype newStereotype = stereotypesManager.getStereotype(
        frameWebClassAttribute.getStereotypeName(), IModelElementFactory.MODEL_TYPE_ATTRIBUTE);
    for (IModelElement modelElement : selectedModelElements) {
      if (IModelElementFactory.MODEL_TYPE_ATTRIBUTE.equals(modelElement.getModelType())) {
        Logger.log(Level.INFO, "Applying stereotype {0} to {1}",
            new Object[] {frameWebClassAttribute.getStereotypeName(), modelElement.getName()});

        // Removes other FrameWeb stereotypes the class attribute may have, as they are disjoint.
        IStereotype[] existingStereotypes = modelElement.toStereotypeModelArray();
        if (existingStereotypes != null) {
          for (IStereotype existingStereotype : existingStereotypes) {
            FrameWebClassAttribute attr =
                FrameWebClassAttribute.ofStereotype(existingStereotype.getName());
            if (attr != FrameWebClassAttribute.NOT_A_FRAMEWEB_CLASS_ATTRIBUTE) {
              Logger.log(Level.CONFIG, "Removing disjoint stereotype {0} from {1}",
                  new Object[] {existingStereotype, modelElement.getName()});
              modelElement.removeStereotype(existingStereotype);
            }
          }
        }

        // Adds the new FrameWeb class attribute stereotype.
        modelElement.addStereotype(newStereotype);
      }
    }
  }
}
