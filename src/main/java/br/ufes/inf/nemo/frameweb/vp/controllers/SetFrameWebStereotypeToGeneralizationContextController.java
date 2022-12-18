package br.ufes.inf.nemo.frameweb.vp.controllers;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPContext;
import com.vp.plugin.action.VPContextActionController;
import com.vp.plugin.model.IGeneralization;
import com.vp.plugin.model.IGeneralizationSet;
import com.vp.plugin.model.IModelElement;
import com.vp.plugin.model.IStereotype;
import com.vp.plugin.model.factory.IModelElementFactory;
import br.ufes.inf.nemo.frameweb.vp.managers.FrameWebStereotypesManager;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebClass;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebGeneralization;
import br.ufes.inf.nemo.frameweb.vp.utils.FrameWebUtils;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import br.ufes.inf.nemo.vpzy.managers.StereotypesManager;

/**
 * Controller that handles the Set FrameWeb Stereotype to Generalization action, activated by a
 * context menu (right-click) for UML Generalization elements.
 *
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
 */
public class SetFrameWebStereotypeToGeneralizationContextController
    implements VPContextActionController {
  /**
   * Called when the menu containing the button is accessed allowing for action manipulation, such
   * as enable/disable or selecting the button.
   */
  @Override
  public void update(VPAction action, VPContext context) {
    // Checks if what was clicked is a generalization.
    IModelElement modelElement = context.getModelElement();
    if (modelElement instanceof IGeneralization) {
      IGeneralization generalization = (IGeneralization) modelElement;

      // Checks if both sides of the generalization refer to supported FrameWeb classes.
      IModelElement fromElement = generalization.getFrom();
      IModelElement toElement = generalization.getTo();
      FrameWebClass fromElementFrameWebClass = FrameWebUtils.getFrameWebClass(fromElement);
      FrameWebClass toElementFrameWebClass = FrameWebUtils.getFrameWebClass(toElement);

      // Enables the action if the stereotype it represents can be applied to generalizations
      // between these FrameWeb classes.
      FrameWebGeneralization frameWebGeneralization =
          FrameWebGeneralization.ofPluginUIID(action.getActionId());
      Logger.log(Level.FINE,
          "Updating action: Add FrameWeb Stereotype (Generalization) > {0}. Associated classes: {1} -- {2}. Action refers to classes: {3}",
          new Object[] {action.getLabel(), fromElementFrameWebClass, toElementFrameWebClass,
              Arrays.toString(frameWebGeneralization.getFrameWebClasses())});
      boolean fromEnabled = false, toEnabled = false;
      for (FrameWebClass clazz : frameWebGeneralization.getFrameWebClasses()) {
        if (fromElementFrameWebClass == clazz) {
          fromEnabled = true;
        }
        if (toElementFrameWebClass == clazz) {
          toEnabled = true;
        }
      }
      action.setEnabled(fromEnabled && toEnabled);
    }
  }

  /** Called when the button is pressed. Sets the generalization's stereotype. */
  @Override
  public void performAction(VPAction action, VPContext context, ActionEvent event) {
    Logger.log(Level.CONFIG, "Performing action: Set FrameWeb Stereotype (Generalization) > {0}",
        event.getActionCommand());

    // Gets the FrameWeb stereotypes manager associated with the current project.
    StereotypesManager stereotypesManager =
        StereotypesManager.getInstance(FrameWebStereotypesManager.class);

    // Determines which FrameWeb generalization it is from the menu item that has been selected.
    FrameWebGeneralization frameWebGeneralization =
        FrameWebGeneralization.ofPluginUIID(action.getActionId());

    // Processes the selected generalization.
    IModelElement selectedModelElement = context.getModelElement();
    if (IModelElementFactory.MODEL_TYPE_GENERALIZATION
        .equals(selectedModelElement.getModelType())) {
      IGeneralization generalization = (IGeneralization) selectedModelElement;
      String description =
          generalization.getFrom().getName() + " --> " + generalization.getTo().getName();

      // Retrieves the stereotype to be applied.
      IStereotype newStereotype =
          stereotypesManager.getStereotype(frameWebGeneralization.getStereotypeName(),
              IModelElementFactory.MODEL_TYPE_GENERALIZATION);
      Logger.log(Level.INFO, "Applying stereotype {0} to generalization {1}",
          new Object[] {frameWebGeneralization.getStereotypeName(), description});

      // Removes other FrameWeb stereotypes the generalization set may have, as they are disjoint.
      Set<IGeneralization> genSetMembers = new HashSet<>();
      genSetMembers.add(generalization);
      IGeneralizationSet genSet = generalization.getGeneralizationSet();
      if (genSet != null) {
        genSetMembers.addAll(Arrays.asList(genSet.toGeneralizationArray()));
      }
      for (IGeneralization genSetMember : genSetMembers) {
        IStereotype[] existingStereotypes = genSetMember.toStereotypeModelArray();
        if (existingStereotypes != null) {
          for (IStereotype existingStereotype : existingStereotypes) {
            FrameWebGeneralization existingGeneralization =
                FrameWebGeneralization.ofStereotype(existingStereotype.getName());
            if (existingGeneralization != FrameWebGeneralization.NOT_A_FRAMEWEB_GENERALIZATION) {
              Logger.log(Level.CONFIG,
                  "Removing disjoint stereotype {0} from the generalization set of generalization {1}",
                  new Object[] {existingStereotype, description});
              genSetMember.removeStereotype(existingStereotype);
            }
          }
        }
      }

      // Sets the new FrameWeb generalization stereotype.
      generalization.addStereotype(newStereotype);
    }
  }
}
