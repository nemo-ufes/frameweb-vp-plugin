package br.ufes.inf.nemo.frameweb.vp.controllers;

import java.util.logging.Level;
import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPActionController;
import br.ufes.inf.nemo.vpzy.logging.Logger;
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
    ViewManagerUtils.showMessageDialog("Sorry, this feature has not yet been implemented.",
        "Work in Progress", ViewManagerUtils.INFORMATION_MESSAGE);
  }
}
