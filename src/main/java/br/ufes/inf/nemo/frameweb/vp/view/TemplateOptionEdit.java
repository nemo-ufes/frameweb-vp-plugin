package br.ufes.inf.nemo.frameweb.vp.view; // Ajustado para o pacote correto da sua view

import br.ufes.inf.nemo.frameweb.vp.FrameWebPlugin;
import br.ufes.inf.nemo.vpzy.engine.models.base.TemplateOption;
import com.vp.plugin.view.IDialog;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * TemplateOptionEdit is a Swing JPanel component that provides a user interface
 * for editing template options in the FrameWeb VP Plugin. It allows users to
 * configure the template name and output path for code generation templates.
 *
 * @author gmant (Gianluca Schmidt Mantovaneli)
 * @version 1.0
 */
public class TemplateOptionEdit extends javax.swing.JPanel {

    /** The template option object containing the template name and configuration */
    private String templateName;

    /** The template option object containing the current template configuration */
    private TemplateOption option;

    /** The parent dialog container for this panel */
    private IDialog containerDialog;

    /**
     * Constructs a TemplateOptionEdit panel with the specified template option.
     * Initializes the UI components and loads the template configuration from the
     * FrameWeb Plugin configuration manager.
     *
     * @param templateName the TemplateOption object containing the template name
     */
    public TemplateOptionEdit(String templateName) {
        this.templateName = templateName;
        initComponents();

        // Retrieves the configuration from the Manager using the received name
        this.option = FrameWebPlugin.instance().getGenerateCodeConfigManager().getProperty(String.valueOf(templateName));

        if (option != null) {
            jTextField1.setText(option.getName());
            jTextField1.setEditable(false); // The template/folder name should not be edited
            txtOutputText.setText(option.getTemplatePath());
        }
    }

    /**
     * Handles the save button action event. Saves the template path configuration
     * to the FrameWeb Plugin configuration manager and displays a confirmation message.
     * Closes the container dialog if available.
     *
     * @param evt the ActionEvent triggered by the save button click
     */
    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (option != null) {
            option.setTemplatePath(txtOutputText.getText());
            FrameWebPlugin.instance().getGenerateCodeConfigManager().save();

            JOptionPane.showMessageDialog(this, "Configuration saved!");

            if (containerDialog != null) {
                containerDialog.close();
            }
        }
    }

    /**
     * Initializes and configures all Swing UI components for this panel.
     * This method is auto-generated and sets up the layout, labels, buttons,
     * and text fields for the template option editing interface.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        fileChooserButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtOutputText = new javax.swing.JTextField();
        saveButton = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Template management"));

        fileChooserButton.setText("...");
        fileChooserButton.addActionListener(this::fileChooserButtonActionPerformed);

        jLabel1.setText("Template Name");
        jLabel2.setText("Output Path");
        txtOutputText.setText("path");

        saveButton.setText("Save");
        // Vinculando o botão de salvar à nossa nova lógica
        saveButton.addActionListener(this::saveButtonActionPerformed);

        jTextField1.setText("name");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(58, 58, 58)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(txtOutputText, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(fileChooserButton, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jTextField1))
                                .addGap(58, 58, 58))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(77, 77, 77)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(4, 4, 4)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel2)
                                                .addComponent(txtOutputText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(fileChooserButton, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 78, Short.MAX_VALUE)
                                .addComponent(saveButton)
                                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>                        

    /**
     * Handles the file chooser button action event. Opens a directory chooser dialog
     * allowing the user to select a directory path for the template output.
     * The selected path is then displayed in the output path text field.
     *
     * @param evt the ActionEvent triggered by the file chooser button click
     */
    private void fileChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION){
            txtOutputText.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }

    /**
     * Sets the container dialog that holds this panel. This dialog will be
     * closed when the configuration is saved.
     *
     * @param dialog the IDialog container for this panel
     */
    public void setContainerDialog(IDialog dialog) {
        this.containerDialog = dialog;
    }

    // UI Component declarations - do not modify
    /** Button for opening the file chooser dialog */
    private javax.swing.JButton fileChooserButton;
    /** Label for the template name field */
    private javax.swing.JLabel jLabel1;
    /** Label for the output path field */
    private javax.swing.JLabel jLabel2;
    /** Main panel container for the template management UI */
    private javax.swing.JPanel jPanel1;
    /** Text field displaying the template name (read-only) */
    private javax.swing.JTextField jTextField1;
    /** Button to save the template configuration */
    private javax.swing.JButton saveButton;
    /** Text field for entering and displaying the output path */
    private javax.swing.JTextField txtOutputText;


}