package br.ufes.inf.nemo.frameweb.vp.controllers;

import java.util.logging.Level;
import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPActionController;
import br.ufes.inf.nemo.frameweb.vp.FrameWebPlugin;
import br.ufes.inf.nemo.vpzy.logging.Logger;

/**
 * Controller that handles the Reload Plug-in action, activated by a toolbar button.
 *
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
 */
public class ReloadPluginController implements VPActionController {
  /**
   * Called when the menu containing the button is accessed allowing for action manipulation, such
   * as enable/disable or selecting the button. DOES NOT apply to this class.
   */
  @Override
  public void update(VPAction action) {}

  /** Called when the button is pressed. Reloads the FrameWeb Tools plug-in. */
  @Override
  public void performAction(VPAction action) {
    Logger.log(Level.INFO, "Reloading the FrameWeb Tools plug-in...");
    FrameWebPlugin.reload();
  }
}
