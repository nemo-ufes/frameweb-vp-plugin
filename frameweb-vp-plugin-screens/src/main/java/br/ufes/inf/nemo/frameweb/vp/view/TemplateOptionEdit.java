/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package br.ufes.inf.nemo.frameweb.vp.view;

import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author FULLM
 */
public class TemplateOptionEdit extends javax.swing.JPanel {

    /**
     * Creates new form TemplateOptionEdit
     */
    public TemplateOptionEdit() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        nameLabel = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        templatePathLabel = new javax.swing.JLabel();
        templatePathTextField = new javax.swing.JTextField();
        descriptionLabel = new javax.swing.JLabel();
        descriptionTextField = new javax.swing.JTextField();
        templateFilesTabbedPane = new javax.swing.JTabbedPane();
        entityFileTypeEdit = new br.ufes.inf.nemo.frameweb.vp.view.FileTypeEdit();
        enumerationFileTypeEdit = new br.ufes.inf.nemo.frameweb.vp.view.FileTypeEdit();
        mappedSuperclassFileTypeEdit = new br.ufes.inf.nemo.frameweb.vp.view.FileTypeEdit();
        transientFileTypeEdit = new br.ufes.inf.nemo.frameweb.vp.view.FileTypeEdit();
        embeddableFileTypeEdit = new br.ufes.inf.nemo.frameweb.vp.view.FileTypeEdit();
        daoFileTypeEdit = new br.ufes.inf.nemo.frameweb.vp.view.FileTypeEdit();
        controllerFileTypeEdit = new br.ufes.inf.nemo.frameweb.vp.view.FileTypeEdit();
        serviceFileTypeEdit = new br.ufes.inf.nemo.frameweb.vp.view.FileTypeEdit();
        saveButton = new javax.swing.JButton();
        outputPathLabel = new javax.swing.JLabel();
        outputPathTextField = new javax.swing.JTextField();

        setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Template Management", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Comic Sans MS", 0, 12))); // NOI18N
        setPreferredSize(new java.awt.Dimension(814, 470));
        java.awt.GridBagLayout layout1 = new java.awt.GridBagLayout();
        layout1.columnWidths = new int[] {0, 5, 0, 5, 0};
        layout1.rowHeights = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
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
        nameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameTextFieldActionPerformed(evt);
            }
        });
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
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                templatePathTextFieldMouseClicked(evt);
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
        descriptionTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                descriptionTextFieldActionPerformed(evt);
            }
        });
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
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveButtonMouseClicked(evt);
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
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                outputPathTextFieldMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        add(outputPathTextField, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void nameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameTextFieldActionPerformed

    private void descriptionTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_descriptionTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_descriptionTextFieldActionPerformed

    private void templatePathTextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_templatePathTextFieldMouseClicked
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            templatePathTextField.setText(selectedFile.getAbsolutePath());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_templatePathTextFieldMouseClicked

    private void outputPathTextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_outputPathTextFieldMouseClicked
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            outputPathTextField.setText(selectedFile.getAbsolutePath());
        }
    }//GEN-LAST:event_outputPathTextFieldMouseClicked

    private void saveButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveButtonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_saveButtonMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private br.ufes.inf.nemo.frameweb.vp.view.FileTypeEdit controllerFileTypeEdit;
    private br.ufes.inf.nemo.frameweb.vp.view.FileTypeEdit daoFileTypeEdit;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JTextField descriptionTextField;
    private br.ufes.inf.nemo.frameweb.vp.view.FileTypeEdit embeddableFileTypeEdit;
    private br.ufes.inf.nemo.frameweb.vp.view.FileTypeEdit entityFileTypeEdit;
    private br.ufes.inf.nemo.frameweb.vp.view.FileTypeEdit enumerationFileTypeEdit;
    private br.ufes.inf.nemo.frameweb.vp.view.FileTypeEdit mappedSuperclassFileTypeEdit;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JLabel outputPathLabel;
    private javax.swing.JTextField outputPathTextField;
    private javax.swing.JButton saveButton;
    private br.ufes.inf.nemo.frameweb.vp.view.FileTypeEdit serviceFileTypeEdit;
    private javax.swing.JTabbedPane templateFilesTabbedPane;
    private javax.swing.JLabel templatePathLabel;
    private javax.swing.JTextField templatePathTextField;
    private br.ufes.inf.nemo.frameweb.vp.view.FileTypeEdit transientFileTypeEdit;
    // End of variables declaration//GEN-END:variables
}
