package br.ufes.inf.nemo.frameweb.vp.view;

import br.ufes.inf.nemo.vpzy.engine.models.base.FileTypes;

/**
 * Basic layout for adding information about a file type and template.
 *
 * @author Igor Sunderhus e Silva (<a href="https://github.com/igorssilva">Github page</a>)
 */
public class FileTypeEdit extends javax.swing.JPanel {
    private javax.swing.JTextField extensionTextField;

    private javax.swing.JTextField templateFieldText;

    /**
     * Creates new form FileTypeEdit
     */
    public FileTypeEdit() {
        initComponents();
    }

    private void initComponents() {
        javax.swing.JLabel templateLabel;
        javax.swing.JLabel extensionLabel;
        java.awt.GridBagConstraints gridBagConstraints;

        templateLabel = new javax.swing.JLabel();
        templateFieldText = new javax.swing.JTextField();
        extensionLabel = new javax.swing.JLabel();
        extensionTextField = new javax.swing.JTextField();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        java.awt.GridBagLayout layout = new java.awt.GridBagLayout();
        layout.columnWidths = new int[] { 0, 5, 0 };
        layout.rowHeights = new int[] { 0, 5, 0 };
        setLayout(layout);

        templateLabel.setText("Template");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        add(templateLabel, gridBagConstraints);

        templateFieldText.setPreferredSize(new java.awt.Dimension(200, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        add(templateFieldText, gridBagConstraints);

        extensionLabel.setText("Extension");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        add(extensionLabel, gridBagConstraints);

        extensionTextField.setPreferredSize(new java.awt.Dimension(200, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        add(extensionTextField, gridBagConstraints);
    }

    public void setFields(final FileTypes fileType) {
        templateFieldText.setText(fileType.getTemplate());
        extensionTextField.setText(fileType.getExtension());
    }

    public FileTypes getFileType() {
        return new FileTypes(templateFieldText.getText(), extensionTextField.getText());
    }
}
