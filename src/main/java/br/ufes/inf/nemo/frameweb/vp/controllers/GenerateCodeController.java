package br.ufes.inf.nemo.frameweb.vp.controllers;

import br.ufes.inf.nemo.frameweb.vp.FrameWebPlugin;
import br.ufes.inf.nemo.frameweb.vp.view.GenerateCodePanel;
import br.ufes.inf.nemo.vpzy.engine.FreeMarkerEngine;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import br.ufes.inf.nemo.vpzy.managers.ConfigurationManager;
import br.ufes.inf.nemo.vpzy.utils.ViewManagerUtils;
import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPActionController;
import com.vp.plugin.view.IDialog;
import com.vp.plugin.view.IDialogHandler;
import freemarker.template.TemplateException;
import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;

/**
 * Controller that handles the Generate Code action, activated by a toolbar button.
 *
 * @author Vítor E. Silva Souza (<a href="http://www.inf.ufes.br/~vitorsouza/">...</a>)
 * @author Igor Sunderhus e Silva (<a href="https://github.com/igorssilva">Github page</a>)
 * @version 1.0
 */
public class GenerateCodeController implements VPActionController {
    public static final int WIDTH = 800;

    public static final int HEIGHT = 200;

    /**
     * Instance of the FrameWeb plug-in running.
     */
    private final FrameWebPlugin plugin = FrameWebPlugin.instance();

    /**
     * Provides the configuration values and lets us change them.
     */
    private final ConfigurationManager configManager = plugin.getConfigManager();

    /**
     * The contents of the Plug-in Settings dialog.
     */
    private GenerateCodePanel generateCodePanel;

    /**
     * Called when the button is pressed. Performs code generation.
     */
    @Override
    public void performAction(VPAction vpAction) {
        Logger.log(Level.CONFIG, "Performing action: Open Generate Code Settings");
        FrameWebPlugin plugin = FrameWebPlugin.instance();

        // If the dialog is already open, ignores the action.
        if (plugin.isGenerateCodeSettingsDialogOpen()) {
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

    }

    /**
     * A dialog handler that is used by Visual Paradigm to open a dialog based on a panel with its contents.
     */
    protected class GenerateCodeSettingsDialogHandler implements IDialogHandler {
        /**
         * Called once before the dialog is shown. Should return the contents of the dialog.
         */
        @Override
        public Component getComponent() {
            try {
                generateCodePanel = new GenerateCodePanel();
            } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
                    IllegalAccessException e) {
                e.printStackTrace();
                Logger.log(Level.SEVERE, "Error while creating Generate Code Settings dialog", e);
                throw new RuntimeException(e);
            }
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
        }

        /**
         * Called when the dialog is closed by the user clicking on the close button of the frame.
         */
        @Override
        public boolean canClosed() {

            generateTemplates();
            FrameWebPlugin.instance().setGenerateCodeSettingsDialogOpen(false);
            return true;
        }

        private void generateTemplates() {
            final String templateDir = configManager.getProperty(
                    FrameWebPlugin.CONFIG_DEFAULT_GENERATE_CODE_TEMPLATE_FOLDER);

            final String outputDir = configManager.getProperty(
                    FrameWebPlugin.CONFIG_DEFAULT_GENERATE_CODE_OUTPUT_FOLDER);

            // TODO generate code from templates for the current vp project model using the templates in the input directory and write the generated code to the output directory

            FreeMarkerEngine engine = new FreeMarkerEngine(templateDir);

            String sourceCode;
            try {
                sourceCode = engine.getCode("EntityClassTemplate.ftl");
            } catch (IOException | TemplateException e) {
                Logger.log(Level.SEVERE, "Error while generating code from template");
                throw new RuntimeException(e);
            }

            System.out.println(sourceCode);

            // Write source code to file Test.java in the output directory

            try {
                FileWriter writer = new FileWriter(outputDir + "/Test.java");
                writer.write(sourceCode);
                writer.close();
                System.out.println("Successfully wrote to file.");
            } catch (IOException e) {
                System.out.println("An error occurred while writing to file.");
                e.printStackTrace();
            }

        }
    }
}
