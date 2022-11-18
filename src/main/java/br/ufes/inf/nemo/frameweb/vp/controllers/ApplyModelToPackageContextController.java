package br.ufes.inf.nemo.frameweb.vp.controllers;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Set;
import com.vp.plugin.ApplicationManager;
import com.vp.plugin.DiagramManager;
import com.vp.plugin.ViewManager;
import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPContext;
import com.vp.plugin.action.VPContextActionController;
import com.vp.plugin.diagram.IDiagramElement;
import com.vp.plugin.diagram.IDiagramUIModel;
import com.vp.plugin.model.IModelElement;
import com.vp.plugin.model.IStereotype;
import br.ufes.inf.nemo.frameweb.vp.FrameWebPlugin;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebModel;
import br.ufes.inf.nemo.vpzy.utils.DiagramElementUtils;
import br.ufes.inf.nemo.vpzy.utils.ModelElementUtils;

/**
 * Controller that handles the Apply Model to Package action, activated by a context menu
 * (right-click) for UML Package elements.
 *
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
 */
public class ApplyModelToPackageContextController implements VPContextActionController {
  /**
   * Called when the menu containing the button is accessed allowing for action manipulation, such
   * as enable/disable or selecting the button. DOES NOT apply to this class.
   */
  @Override
  public void update(VPAction action, VPContext context) {}

  /** Called when the button is pressed. Performs FrameWeb model verification. */
  @Override
  public void performAction(VPAction action, VPContext context, ActionEvent event) {
    // Create the view manager to log messages to the user.
    ViewManager viewManager = ApplicationManager.instance().getViewManager();

    // Determine which FrameWeb Model to apply from the menu item that has been selected.
    FrameWebModel model = FrameWebModel.of(event.getActionCommand());

    // Collect the model elements whose diagram elements are currently selected.
    Set<IModelElement> selectedModelElements = ModelElementUtils.getSelectedModelElements();

    // TODO: for each model element selected, apply a stereotype that refers to the FrameWeb model.
    for (IModelElement modelElement : selectedModelElements)
      viewManager.showMessage(
          "TODO: apply " + model.getName() + " stereotype to " + modelElement.getName(),
          FrameWebPlugin.PLUGIN_NAME);
  }
}
