package br.ufes.inf.nemo.frameweb.vp.controllers;

import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPActionController;
import br.ufes.inf.nemo.frameweb.vp.FrameWebPlugin;
import br.ufes.inf.nemo.vpzy.utils.ViewManagerUtils;

/**
 * Controller that handles the Check Model action, activated by a toolbar button.
 *
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
 */
public class CheckModelController implements VPActionController {
  /**
   * Called when the menu containing the button is accessed allowing for action manipulation, such
   * as enable/disable or selecting the button. DOES NOT apply to this class.
   */
  @Override
  public void update(VPAction action) {}

  /** Called when the button is pressed. Performs FrameWeb model verification. */
  @Override
  public void performAction(VPAction action) {
    ViewManagerUtils.showMessage("TODO: Implement Check Model action!", FrameWebPlugin.PLUGIN_NAME);
  }
}
