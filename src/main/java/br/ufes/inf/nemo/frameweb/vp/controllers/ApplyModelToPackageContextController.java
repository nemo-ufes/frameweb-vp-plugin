package br.ufes.inf.nemo.frameweb.vp.controllers;

import java.awt.event.ActionEvent;
import com.vp.plugin.ApplicationManager;
import com.vp.plugin.ViewManager;
import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPContext;
import com.vp.plugin.action.VPContextActionController;
import br.ufes.inf.nemo.frameweb.vp.FrameWebPlugin;

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
    ViewManager viewManager = ApplicationManager.instance().getViewManager();

    // TODO: is there a better way to see which item triggered this action than the action command?
    // TODO: replace the switch with an Enum that represents the FrameWeb model.
    switch (event.getActionCommand()) {
      case "Application Model":
        viewManager.showMessage("TODO: make the selected package an Application Model!",
            FrameWebPlugin.PLUGIN_NAME);
        break;
      case "Entity Model":
        viewManager.showMessage("TODO: make the selected package an Entity Model!",
            FrameWebPlugin.PLUGIN_NAME);
        break;
      case "Navigation Model":
        viewManager.showMessage("TODO: make the selected package an Navigation Model!",
            FrameWebPlugin.PLUGIN_NAME);
        break;
      case "Persistence Model":
        viewManager.showMessage("TODO: make the selected package an Persistence Model!",
            FrameWebPlugin.PLUGIN_NAME);
        break;
    }
  }
}
