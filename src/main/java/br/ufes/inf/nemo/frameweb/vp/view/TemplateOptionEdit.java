/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package br.ufes.inf.nemo.frameweb.vp.view;

import br.ufes.inf.nemo.frameweb.vp.FrameWebPlugin;
import br.ufes.inf.nemo.vpzy.engine.models.base.TemplateOption;
import br.ufes.inf.nemo.vpzy.managers.YamlConfigurationManager;
import br.ufes.inf.nemo.vpzy.utils.ViewManagerUtils;
import com.vp.plugin.view.IDialog;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Defines the layout of the panel that will be shown in the dialog to edit the configuration of a template option.
 *
 * @author Igor Sunderhus e Silva (<a href="https://github.com/igorssilva">Github page</a>)
 */
public class TemplateOptionEdit extends javax.swing.JPanel {
    private final TemplateOption templateOption;

    /**
     * Instance of the FrameWeb plug-in running.
     */
    private final FrameWebPlugin plugin = FrameWebPlugin.instance();

    /**
     * Provides the configuration values and lets us change them.
     */
    private final YamlConfigurationManager configManager = plugin.getGenerateCodeConfigManager();

    private IDialog containerDialog;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private FileTypeEdit controllerFileTypeEdit;

    private FileTypeEdit daoFileTypeEdit;

    private javax.swing.JTextField descriptionTextField;

    private FileTypeEdit embeddableFileTypeEdit;

    private FileTypeEdit entityFileTypeEdit;

    private FileTypeEdit enumerationFileTypeEdit;

    private FileTypeEdit mappedSuperclassFileTypeEdit;

    private javax.swing.JTextField nameTextField;

    private javax.swing.JTextField outputPathTextField;

    private FileTypeEdit serviceFileTypeEdit;

    private javax.swing.JTextField templatePathTextField;

    private FileTypeEdit transientFileTypeEdit;

    /**
     * Creates new form TemplateOptionEdit
     */
    public TemplateOptionEdit(final TemplateOption templateOption) {
        if (templateOption == null) {
            throw new IllegalArgumentException("Template Option cannot be null");
        }
        this.templateOption = templateOption;
        initComponents();
        loadTemplateOption();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    private void initComponents() {
        javax.swing.JLabel descriptionLabel;
        javax.swing.JLabel nameLabel;
        javax.swing.JLabel outputPathLabel;
        javax.swing.JButton saveButton;
        javax.swing.JTabbedPane templateFilesTabbedPane;
        javax.swing.JLabel templatePathLabel;
        java.awt.GridBagConstraints gridBagConstraints;

        nameLabel = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        templatePathLabel = new javax.swing.JLabel();
        templatePathTextField = new javax.swing.JTextField();
        descriptionLabel = new javax.swing.JLabel();
        descriptionTextField = new javax.swing.JTextField();
        templateFilesTabbedPane = new javax.swing.JTabbedPane();
        entityFileTypeEdit = new FileTypeEdit();
        enumerationFileTypeEdit = new FileTypeEdit();
        mappedSuperclassFileTypeEdit = new FileTypeEdit();
        transientFileTypeEdit = new FileTypeEdit();
        embeddableFileTypeEdit = new FileTypeEdit();
        daoFileTypeEdit = new FileTypeEdit();
        controllerFileTypeEdit = new FileTypeEdit();
        serviceFileTypeEdit = new FileTypeEdit();
        saveButton = new javax.swing.JButton();
        outputPathLabel = new javax.swing.JLabel();
        outputPathTextField = new javax.swing.JTextField();

        setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Template Management",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Comic Sans MS", Font.PLAIN, 12))); // NOI18N
        setPreferredSize(new java.awt.Dimension(814, 470));
        java.awt.GridBagLayout layout1 = new java.awt.GridBagLayout();
        layout1.columnWidths = new int[] { 0, 5, 0, 5, 0 };
        layout1.rowHeights = new int[] { 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0 };
        setLayout(layout1);

        nameLabel.setText("Name");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        add(nameLabel, gridBagConstraints);

        nameTextField.setToolTipText("Name of the Template");
        nameTextField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        nameTextField.setMinimumSize(new java.awt.Dimension(200, 22));
        nameTextField.setPreferredSize(new java.awt.Dimension(200, 22));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        add(nameTextField, gridBagConstraints);

        templatePathLabel.setText("Template Path");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        add(templatePathLabel, gridBagConstraints);

        templatePathTextField.setToolTipText("Path to the folder containing the template files");
        templatePathTextField.setPreferredSize(new java.awt.Dimension(200, 22));
        templatePathTextField.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                templatePathTextFieldMouseClicked();
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        add(templatePathTextField, gridBagConstraints);

        descriptionLabel.setText("Description");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        add(descriptionLabel, gridBagConstraints);

        descriptionTextField.setToolTipText("Description for the Template");
        descriptionTextField.setPreferredSize(new java.awt.Dimension(200, 22));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        add(descriptionTextField, gridBagConstraints);

        templateFilesTabbedPane.setPreferredSize(new java.awt.Dimension(700, 100));

        entityFileTypeEdit.setPreferredSize(getSize());
        templateFilesTabbedPane.addTab("Entity", entityFileTypeEdit);
        templateFilesTabbedPane.addTab("Enumeration", enumerationFileTypeEdit);
        templateFilesTabbedPane.addTab("MappedSuperclass", mappedSuperclassFileTypeEdit);
        templateFilesTabbedPane.addTab("Transient", transientFileTypeEdit);
        templateFilesTabbedPane.addTab("Embeddable", embeddableFileTypeEdit);
        templateFilesTabbedPane.addTab("DAO", daoFileTypeEdit);
        templateFilesTabbedPane.addTab("Controller", controllerFileTypeEdit);
        templateFilesTabbedPane.addTab("Service", serviceFileTypeEdit);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(templateFilesTabbedPane, gridBagConstraints);

        saveButton.setText("Save");
        saveButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        saveButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveButtonMouseClicked();
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LAST_LINE_END;
        add(saveButton, gridBagConstraints);

        outputPathLabel.setText("Output Path");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        add(outputPathLabel, gridBagConstraints);

        outputPathTextField.setPreferredSize(new java.awt.Dimension(200, 22));
        outputPathTextField.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                outputPathTextFieldMouseClicked();
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        add(outputPathTextField, gridBagConstraints);
    }

    private void loadTemplateOption() {
        if (templateOption != null) {
            nameTextField.setText(templateOption.getName());
            descriptionTextField.setText(templateOption.getDescription());
            templatePathTextField.setText(templateOption.getTemplatePath());
            outputPathTextField.setText(templateOption.getOutputPath());
            entityFileTypeEdit.setFields(templateOption.getEntity());
            enumerationFileTypeEdit.setFields(templateOption.getEnumeration());
            mappedSuperclassFileTypeEdit.setFields(templateOption.getMappedSuperclass());
            transientFileTypeEdit.setFields(templateOption.getTransientClass());
            embeddableFileTypeEdit.setFields(templateOption.getEmbeddable());
            daoFileTypeEdit.setFields(templateOption.getDao());
            controllerFileTypeEdit.setFields(templateOption.getController());
            serviceFileTypeEdit.setFields(templateOption.getService());
        }
    }

    private void templatePathTextFieldMouseClicked() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            templatePathTextField.setText(selectedFile.getAbsolutePath());
        }
    }

    private void saveButtonMouseClicked() {

        try {
            this.templateOption.setName(nameTextField.getText());
            this.templateOption.setDescription(descriptionTextField.getText());
            this.templateOption.setTemplatePath(templatePathTextField.getText());
            this.templateOption.setOutputPath(outputPathTextField.getText());
            this.templateOption.setEntity(entityFileTypeEdit.getFileType());
            this.templateOption.setEnumeration(enumerationFileTypeEdit.getFileType());
            this.templateOption.setMappedSuperclass(mappedSuperclassFileTypeEdit.getFileType());
            this.templateOption.setTransientClass(transientFileTypeEdit.getFileType());
            this.templateOption.setEmbeddable(embeddableFileTypeEdit.getFileType());
            this.templateOption.setDao(daoFileTypeEdit.getFileType());
            this.templateOption.setController(controllerFileTypeEdit.getFileType());
            this.templateOption.setService(serviceFileTypeEdit.getFileType());

            this.templateOption.validate();

            configManager.setProperty(templateOption.getName(), templateOption);

            // Saves the configuration.
            configManager.save();

            // Imports the template folder.
            configManager.importTemplateFolder(this.templateOption);

            GenerateCodePanel.getTemplateOptions();

            ViewManagerUtils.showMessageDialog("Template Option saved successfully", "Success",
                    ViewManagerUtils.INFORMATION_MESSAGE);

            // Closes the configuration dialog.
            containerDialog.close();
        } catch (IllegalArgumentException | IOException e) {
            ViewManagerUtils.showMessageDialog(e.getMessage(), "Error", ViewManagerUtils.ERROR_MESSAGE);
        }

    }

    private void outputPathTextFieldMouseClicked() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            outputPathTextField.setText(selectedFile.getAbsolutePath());
        }
    }

    public void setContainerDialog(final IDialog containerDialog) {
        this.containerDialog = containerDialog;
    }

}