package br.ufes.inf.nemo.frameweb.vp.controllers;

import java.awt.Component;
import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPActionController;
import com.vp.plugin.view.IDialog;
import com.vp.plugin.view.IDialogHandler;
import br.ufes.inf.nemo.frameweb.vp.FrameWebPlugin;
import br.ufes.inf.nemo.frameweb.vp.view.PluginSettingsPanel;
import br.ufes.inf.nemo.vpzy.utils.ViewManagerUtils;

/**
 * Controller that handles the Plug-in Settings action, activated by a toolbar button.
 *
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
 */
public class OpenPluginSettingsController implements VPActionController {
  /** The contents of the Plug-in Settings dialog. */
  private PluginSettingsPanel pluginSettingsPanel;

  /** The Plug-in Settings dialog. */
  private IDialog pluginSettingsDialog;

  /**
   * Called when the menu containing the button is accessed allowing for action manipulation, such
   * as enable/disable or selecting the button. DOES NOT apply to this class.
   */
  @Override
  public void update(VPAction action) {}

  /** Called when the button is pressed. Opens the Plug-in Settings window. */
  @Override
  public void performAction(VPAction action) {
    // If the dialog is already open, ignore.
    if (FrameWebPlugin.isPluginSettingsDialogOpen())
      return;
    else
      FrameWebPlugin.setPluginSettingsDialogOpen(true);

    // Open the dialog.
    ViewManagerUtils.showDialog(new PluginSettingsDialogHandler());
  }

  /**
   * A dialog handler that is used by Visual Paradigm to open a dialog based on a panel with its
   * contents.
   */
  protected class PluginSettingsDialogHandler implements IDialogHandler {
    /** Called once before the dialog is shown. Should return the contents of the dialog. */
    @Override
    public Component getComponent() {
      pluginSettingsPanel = new PluginSettingsPanel();
      return pluginSettingsPanel;
    }

    /** Called after getComponent(), dialog is created but not shown. Set outlook of the dialog. */
    @Override
    public void prepare(IDialog dialog) {
      pluginSettingsDialog = dialog;
      pluginSettingsDialog.setTitle(FrameWebPlugin.PLUGIN_NAME + " Settings");
      pluginSettingsDialog.setModal(false);
      pluginSettingsDialog.setResizable(true);
      pluginSettingsDialog.setSize(400, 200);
      pluginSettingsPanel.setContainerDialog(pluginSettingsDialog);
    }

    /** Called when the dialog is shown. */
    @Override
    public void shown() {}

    /** Called when the dialog is closed by the user clicking on the close button of the frame. */
    @Override
    public boolean canClosed() {
      FrameWebPlugin.setPluginSettingsDialogOpen(false);
      return true;
    }
  }
}
