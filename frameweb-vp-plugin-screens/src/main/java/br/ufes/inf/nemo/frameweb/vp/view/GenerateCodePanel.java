/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package br.ufes.inf.nemo.frameweb.vp.view;

/**
 *
 * @author FULLM
 */
public class GenerateCodePanel extends javax.swing.JPanel {

    /**
     * Creates new form GenerateCodePanel
     */
    public GenerateCodePanel() {
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

        templateLabel = new javax.swing.JLabel();
        templateComboBox = new javax.swing.JComboBox<>();
        editButton = new javax.swing.JButton();
        newButton = new javax.swing.JButton();
        generateCodeButton = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createTitledBorder("Generate Code"));
        java.awt.GridBagLayout layout = new java.awt.GridBagLayout();
        layout.columnWidths = new int[] {0, 5, 0, 5, 0};
        layout.rowHeights = new int[] {0, 5, 0, 5, 0};
        setLayout(layout);

        templateLabel.setText("Template");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        add(templateLabel, gridBagConstraints);

        templateComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 128;
        add(templateComboBox, gridBagConstraints);

        editButton.setText("Edit");
        editButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editButtonMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(editButton, gridBagConstraints);

        newButton.setText("New");
        newButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                newButtonMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(newButton, gridBagConstraints);

        generateCodeButton.setText("Generate");
        generateCodeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                generateCodeButtonMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LAST_LINE_END;
        add(generateCodeButton, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void editButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editButtonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_editButtonMouseClicked

    private void newButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newButtonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_newButtonMouseClicked

    private void generateCodeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_generateCodeButtonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_generateCodeButtonMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton editButton;
    private javax.swing.JButton generateCodeButton;
    private javax.swing.JButton newButton;
    private javax.swing.JComboBox<String> templateComboBox;
    private javax.swing.JLabel templateLabel;
    // End of variables declaration//GEN-END:variables
}