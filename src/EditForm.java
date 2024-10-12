import javax.swing.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author User
 */
public abstract class EditForm extends JFrame {

    /**
     * Creates new form update
     */
    public enum EditType {
        FirstRun,
        Add,
        Update
    }


    public EditForm(String title, String[] inputLabels, JComponent[] inputFields) {
        if (inputLabels.length != inputFields.length) {
            throw new AssertionError("Number of labels does not match number of fields");
        }
        this.inputFields = inputFields;
        jPanel1 = new javax.swing.JPanel();
        backButton = new javax.swing.JButton();
        confirmButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        backButton.setText("Back");
        backButton.addActionListener((evt) -> exit());

        confirmButton.setBackground(new java.awt.Color(0, 51, 102));
        confirmButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        confirmButton.setForeground(new java.awt.Color(255, 255, 255));
        confirmButton.setText("Confirm");

        confirmButton.addActionListener((evt) -> submit());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        
        GroupLayout.ParallelGroup labelGroup = jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER);
        GroupLayout.ParallelGroup fieldGroup = jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER);
        GroupLayout.SequentialGroup sequentialGroup = jPanel1Layout.createSequentialGroup().addGap(34, 34, 34);
        for (int idx = 0; idx < inputLabels.length; idx++) {
            JLabel label = new JLabel(inputLabels[idx], SwingConstants.TRAILING);
            label.setFont(new java.awt.Font("Segoe UI", 1, 12));
            labelGroup = labelGroup.addComponent(label, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE);
            fieldGroup = fieldGroup.addComponent(inputFields[idx], javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE);
            sequentialGroup = sequentialGroup
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inputFields[idx], javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18);
            inputFields[idx].setFocusTraversalKeysEnabled(true);
        }
        
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(backButton)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(confirmButton)
            .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addGroup(labelGroup)
                .addGap(20, 20, 20)
                .addGroup(fieldGroup)
                .addContainerGap(135, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, inputFields);

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sequentialGroup
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(backButton)
                    .addComponent(confirmButton))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, inputFields);

        jPanel2.setBackground(new java.awt.Color(0, 51, 152));
        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        titleLabel.setBackground(new java.awt.Color(255, 255, 255));
        titleLabel.setFont(new java.awt.Font("Swis721 Cn BT", 1, 48)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(255, 255, 255));
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText(title);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pack();
    }

    protected abstract void exit();

    protected abstract void submit();

    protected Object getInputOf(int inputIndex) {
        JComponent input = inputFields[inputIndex];
        if (input instanceof JPasswordField passwordField) {
            return String.valueOf(passwordField.getPassword());
        } else if (input instanceof JTextField textField) {
            return textField.getText();
        } else if (input instanceof JComboBox comboBox) {
            return comboBox.getSelectedItem();
        } else if (input instanceof JSpinner spinner) {
            return spinner.getValue();
        }

        return null;
    }

    protected void setInputOf(int inputIndex,Object value) {
        JComponent input = inputFields[inputIndex];
        if (input instanceof JTextField textField) {
            textField.setText((String)value);
        } else if (input instanceof JComboBox comboBox) {
            comboBox.setSelectedItem(value);
        } else if (input instanceof JButton button) {
            button.setText((String)value);
        } else if (input instanceof JSpinner spinner) {
            spinner.setValue(value);
        }
    }

    protected JButton backButton;
    protected JButton confirmButton;
    protected JLabel titleLabel;
    protected JPanel jPanel1;
    protected JPanel jPanel2;
    protected JComponent[] inputFields;
}
