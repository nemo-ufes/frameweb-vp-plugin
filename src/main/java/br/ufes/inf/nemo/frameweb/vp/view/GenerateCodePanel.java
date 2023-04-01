package br.ufes.inf.nemo.frameweb.vp.view;

import br.ufes.inf.nemo.frameweb.vp.FrameWebPlugin;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import br.ufes.inf.nemo.vpzy.managers.ConfigurationManager;
import com.vp.plugin.view.IDialog;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.Level;

/**
 * Defines the layout of the panel that will be shown in the dialog to generate the code.
 *
 * @author Igor Sunderhus e Silva (<a href="https://github.com/igorssilva">Github page</a>)
 */
public class GenerateCodePanel extends JPanel implements ActionListener {
    /**
     * Instance of the FrameWeb plug-in running.
     */
    private final FrameWebPlugin plugin = FrameWebPlugin.instance();

    /**
     * Provides the configuration values and lets us change them.
     */
    private final ConfigurationManager configManager = plugin.getConfigManager();

    private final JTextField templateFolderField = new JTextField(), outputFolderField = new JTextField();

    private final GridBagConstraints c = new GridBagConstraints();

    /**
     * The dialog to which this panel serves as contents.
     */
    private IDialog containerDialog;

    public GenerateCodePanel() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException,
            IllegalAccessException {

        setLayout(new GridBagLayout());
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;
        initComponents(templateFolderField, outputFolderField);
        readConfig();
    }

    private void initComponents(final JTextField templateFolderField, final JTextField outputFolderField) {

        setBorder(BorderFactory.createTitledBorder("Generate Code Settings"));

        c.gridy = 0;
        generateFileChooserField(templateFolderField, "Template directory: ");

        c.gridy = 1;
        generateFileChooserField(outputFolderField, "Generated Code directory: ");

        c.insets = new Insets(10, 5, 5, 5);
        c.gridy = 2;
        c.gridx = 2;
        c.weightx = 1;
        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        JButton generateCodeButton = new JButton("Generate Code");
        generateCodeButton.addActionListener(this);
        add(generateCodeButton, c);
    }

    private void readConfig() {
        Logger.log(Level.CONFIG, "Reading current FrameWeb Tools Code Generation configuration values");
        String value;

        value = configManager.getProperty(FrameWebPlugin.CONFIG_DEFAULT_GENERATE_CODE_TEMPLATE_FOLDER);
        if (value != null) {
            templateFolderField.setText(value);
        }

        value = configManager.getProperty(FrameWebPlugin.CONFIG_DEFAULT_GENERATE_CODE_OUTPUT_FOLDER);
        if (value != null) {
            outputFolderField.setText(value);
        }
    }

    private void generateFileChooserField(JTextField field, String labelText) {

        final JLabel label = new JLabel(labelText);

        c.gridx = 0;
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(label, c);

        c.gridx = 1;
        c.weightx = 1.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(field, c);

        final Icon icon = UIManager.getIcon("FileView.directoryIcon");
        final Image iconImage = iconToImage(icon); // convert Icon to Image
        // Create a new ImageIcon with the folder icon
        final ImageIcon folderIcon = new ImageIcon(iconImage);
        // Create a new JButton with the folder icon
        final JButton outputButton = new JButton(folderIcon);
        outputButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = chooser.showOpenDialog(GenerateCodePanel.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                field.setText(selectedFile.getAbsolutePath());
            }
        });

        c.gridx = 2;
        c.weightx = 0;
        c.fill = GridBagConstraints.NONE;
        add(outputButton, c);
    }

    // convert Icon to Image
    private static Image iconToImage(Icon icon) {
        BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        icon.paintIcon(null, g, 0, 0);
        g.dispose();
        return image;
    }

    public void setContainerDialog(IDialog containerDialog) {
        this.containerDialog = containerDialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Call method to generate templates with input and output directories

        saveConfig(templateFolderField.getText(), outputFolderField.getText());

        // Closes the generate code dialog.
        containerDialog.close();
        plugin.setGenerateCodeSettingsDialogOpen(false);
    }

    private void saveConfig(final String templateDir, final String outputDir) {

        Logger.log(Level.CONFIG, "Saving FrameWeb Tools Code Generation configuration values");
        configManager.setProperty(FrameWebPlugin.CONFIG_DEFAULT_GENERATE_CODE_OUTPUT_FOLDER, outputDir);
        configManager.setProperty(FrameWebPlugin.CONFIG_DEFAULT_GENERATE_CODE_TEMPLATE_FOLDER, templateDir);

        // Saves the configuration.
        configManager.save();
    }

}
