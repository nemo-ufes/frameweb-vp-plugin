package br.ufes.inf.nemo.frameweb.vp.controllers;

import br.ufes.inf.nemo.frameweb.vp.FrameWebPlugin;
import br.ufes.inf.nemo.frameweb.vp.view.GenerateCodePanel;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import br.ufes.inf.nemo.vpzy.utils.ViewManagerUtils;
import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPActionController;
import com.vp.plugin.view.IDialog;
import com.vp.plugin.view.IDialogHandler;
import java.awt.*;
import java.util.logging.Level;

/**
 * Controller that handles the Generate Code action, activated by a toolbar button.
 *
 * @author Igor Sunderhus e Silva (<a href="https://github.com/igorssilva">Github page</a>)
 * @version 1.0
 */
public class GenerateCodeController implements VPActionController {
    public static final int WIDTH = 800;

    public static final int HEIGHT = 200;

    /**
     * Called when the button is pressed. Performs code generation.
     */
    @Override
    public void performAction(VPAction vpAction) {
        Logger.log(Level.CONFIG, "Performing action: Open Generate Code Settings");
        final FrameWebPlugin plugin = FrameWebPlugin.instance();
        // If the dialog is already open, ignores the action.
        if (plugin.isGenerateCodeSettingsDialogOpen()) {
            Logger.log(Level.CONFIG, "Generate Code Settings dialog is already open, ignoring action.");
            return;
        }

        // Otherwise, opens the dialog.
        plugin.setGenerateCodeSettingsDialogOpen(true);
        ViewManagerUtils.showDialog(new GenerateCodeSettingsDialogHandler());

    }

    /**
     * Called when the menu containing the button is accessed allowing for action manipulation, such as enable/disable
     * or selecting the button. DOES NOT apply to this class.
     */
    @Override
    public void update(VPAction vpAction) {
        // No action.
    }

    /**
     * A dialog handler that is used by Visual Paradigm to open a dialog based on a panel with its contents.
     */
    protected static class GenerateCodeSettingsDialogHandler implements IDialogHandler {

        private final GenerateCodePanel generateCodePanel;
        public GenerateCodeSettingsDialogHandler() {
            try {
                Logger.log(Level.CONFIG, "Creating Generate Code Settings dialog");
                generateCodePanel = new GenerateCodePanel();
                Logger.log(Level.CONFIG, "Generate Code Settings dialog created");
            } catch (Exception e) {
                Logger.log(Level.SEVERE, "Error while creating Generate Code Settings dialog", e);
                throw new RuntimeException(e);
            }
        }


        /**
         * Called once before the dialog is shown. Should return the contents of the dialog.
         */
        @Override
        public Component getComponent() {
            return generateCodePanel;
        }

        /**
         * Called after getComponent(), dialog is created but not shown. Sets outlook of the dialog.
         */
        @Override
        public void prepare(IDialog dialog) {
            dialog.setTitle(FrameWebPlugin.PLUGIN_NAME + " Code Generation Settings");
            dialog.setModal(false);
            dialog.setResizable(true);
            dialog.setSize(WIDTH, HEIGHT);
            generateCodePanel.setContainerDialog(dialog);
        }

        /**
         * Called when the dialog is shown.
         */
        @Override
        public void shown() {
            // no action
        }

        /**
         * Called when the dialog is closed by the user clicking on the close button of the frame.
         */
        @Override
        public boolean canClosed() {

            FrameWebPlugin.instance().setGenerateCodeSettingsDialogOpen(false);
            return true;
        }

    }
}
