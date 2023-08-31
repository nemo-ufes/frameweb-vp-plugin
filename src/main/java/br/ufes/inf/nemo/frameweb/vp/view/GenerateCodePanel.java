/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package br.ufes.inf.nemo.frameweb.vp.view;

import br.ufes.inf.nemo.frameweb.vp.FrameWebPlugin;
import br.ufes.inf.nemo.frameweb.vp.utils.TemplateUtils;
import br.ufes.inf.nemo.vpzy.engine.models.base.TemplateOption;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import br.ufes.inf.nemo.vpzy.utils.ViewManagerUtils;
import com.vp.plugin.view.IDialog;
import com.vp.plugin.view.IDialogHandler;
import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Level;

/**
 * Defines the layout of the panel that will be shown in the dialog to generate the code.
 *
 * @author Igor Sunderhus e Silva (<a href="https://github.com/igorssilva">Github page</a>)
 */
public class GenerateCodePanel extends javax.swing.JPanel {
    private javax.swing.JComboBox<TemplateOption> templateComboBox;

    /**
     * Creates new form GenerateCodePanel
     */
    public GenerateCodePanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        javax.swing.JLabel templateLabel;
        java.awt.GridBagConstraints gridBagConstraints;

        templateLabel = new javax.swing.JLabel();
        templateComboBox = new javax.swing.JComboBox<>();
        JButton editButton = new JButton();
        JButton newButton = new JButton();
        JButton generateCodeButton = new JButton();

        setBorder(javax.swing.BorderFactory.createTitledBorder("Generate Code"));
        java.awt.GridBagLayout layout = new java.awt.GridBagLayout();
        layout.columnWidths = new int[] { 0, 5, 0, 5, 0 };
        layout.rowHeights = new int[] { 0, 5, 0, 5, 0 };
        setLayout(layout);

        templateLabel.setText("Template");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        add(templateLabel, gridBagConstraints);

        templateComboBox.setModel(getTemplateOptions());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 128;
        add(templateComboBox, gridBagConstraints);

        editButton.setText("Edit");
        editButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editButtonMouseClicked();
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(editButton, gridBagConstraints);

        newButton.setText("New");
        newButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                newButtonMouseClicked();
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(newButton, gridBagConstraints);

        generateCodeButton.setText("Generate");
        generateCodeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                generateCodeButtonMouseClicked();
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LAST_LINE_END;
        add(generateCodeButton, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    public static DefaultComboBoxModel<TemplateOption> getTemplateOptions() {

        final Map<String, TemplateOption> options = TemplateUtils.getTemplateOptions();
        final Collection<TemplateOption> values = options.values();
        final TemplateOption defaultItem = new TemplateOption();
        defaultItem.setDescription("Select a template");
        final TemplateOption[] a = new TemplateOption[] { defaultItem };
        return new DefaultComboBoxModel<>(values.toArray(a));
    }
    // End of variables declaration//GEN-END:variables

    private void editButtonMouseClicked() {

        final TemplateOption selectedItem = (TemplateOption) templateComboBox.getSelectedItem();
        ViewManagerUtils.showDialog(new TemplateOptionEditDialogHandler(selectedItem));
    }

    private void newButtonMouseClicked() {

        ViewManagerUtils.showDialog(new TemplateOptionEditDialogHandler(new TemplateOption()));
    }

    private void generateCodeButtonMouseClicked() {
        final TemplateOption selectedItem = (TemplateOption) templateComboBox.getSelectedItem();

        if (selectedItem == null || selectedItem.getName() == null) {
            ViewManagerUtils.showMessageDialog("Please select a template option", "Error",
                    ViewManagerUtils.ERROR_MESSAGE);
            return;
        }

        try {
            TemplateUtils.generateCode(selectedItem);
            ViewManagerUtils.showMessageDialog("Code generated successfully", "Success",
                    ViewManagerUtils.INFORMATION_MESSAGE);
        } catch (Exception e) {
            Logger.log(Level.SEVERE, "Error while generating code", e);
            ViewManagerUtils.showMessageDialog("Error while generating code", "Error", ViewManagerUtils.ERROR_MESSAGE);
        }
    }

    /**
     * A dialog handler that is used by Visual Paradigm to open a dialog based on a panel with its contents.
     */
    private static class TemplateOptionEditDialogHandler implements IDialogHandler {
        private final TemplateOption templateOption;

        public TemplateOptionEditDialogHandler(final TemplateOption templateOption) {
            this.templateOption = templateOption;
        }

        /**
         * Called once before the dialog is shown. Should return the contents of the dialog.
         */
        @Override
        public Component getComponent() {
            TemplateOptionEdit templateOptionEdit;
            try {
                templateOptionEdit = new TemplateOptionEdit(templateOption);
            } catch (Exception e) {
                Logger.log(Level.SEVERE, "Error while creating Generate Code Template Settings dialog", e);
                throw new RuntimeException(e);
            }
            return templateOptionEdit;
        }

        /**
         * Called after getComponent(), dialog is created but not shown. Sets outlook of the dialog.
         */
        @Override
        public void prepare(IDialog dialog) {
            dialog.setTitle(FrameWebPlugin.PLUGIN_NAME + " Code Generation Template Settings");
            dialog.setModal(false);
            dialog.setResizable(true);
            dialog.setSize(814, 470);
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

            return true;
        }

    }
}
