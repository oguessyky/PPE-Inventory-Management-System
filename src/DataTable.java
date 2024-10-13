/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

 /**
  *
  * @author User
  */
public abstract class DataTable extends JFrame {

    public DataTable(String title, String[] tableHeader, Class<?>[] fieldClasses, String[] searchLabels, JComponent[] searchFields, JButton[] tableButtons) {

        this.tableHeader = tableHeader;
        this.searchFields = searchFields;
        this.tableButtons = tableButtons;

        if (searchLabels.length != searchFields.length) {
            throw new AssertionError("Number of labels does not match number of search fields");
        }

        tableModel = new javax.swing.table.DefaultTableModel(data, tableHeader) {
            Class<?>[] types = fieldClasses;
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
        table = new JTable(tableModel);
        table.getTableHeader().setReorderingAllowed(false);

        table.getSelectionModel().addListSelectionListener((e) -> {if (!e.getValueIsAdjusting()) dataEditSetEnabled(table.getSelectedRow() != -1);});

        table.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedColumn = table.getTableHeader().columnAtPoint(e.getPoint());
                orderASC = (lastSelectedColumn == selectedColumn) ? orderASC ^ true : true;
                updateTableData();
                lastSelectedColumn = selectedColumn;
            }
        });

        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(2).setResizable(false);
        }

        scrollPane = new JScrollPane(table);

        header = new JPanel();
        header.setBackground(new java.awt.Color(0, 51, 102));

        titleLabel = new JLabel(title,SwingConstants.CENTER);
        titleLabel.setFont(new java.awt.Font("Swis721 Cn BT", 1, 36)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(255, 255, 255));

        backButton = new JButton("Back to Menu Page");
        backButton.setBackground(new java.awt.Color(0, 51, 102));
        backButton.setFont(new java.awt.Font("Swis721 Cn BT", 1, 14)); // NOI18N
        backButton.setForeground(new java.awt.Color(255, 255, 255));
        backButton.addActionListener((evt) -> {exit();});

        GroupLayout jPanel1Layout = new GroupLayout(header);
        header.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(backButton)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(titleLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(backButton, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );


        searchLabel = new JLabel("Search");
        searchLabel.setFont(new java.awt.Font("Segoe UI", 1, 30));

        searchForm = new JLayeredPane();
        searchForm.setLayer(searchLabel, JLayeredPane.DEFAULT_LAYER);

        GroupLayout jLayeredPane1Layout = new GroupLayout(searchForm);
        searchForm.setLayout(jLayeredPane1Layout);

        GroupLayout.ParallelGroup parallelGroup = jLayeredPane1Layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        GroupLayout.SequentialGroup sequentialGroup = jLayeredPane1Layout.createSequentialGroup()
            .addGap(17, 17, 17)
            .addComponent(searchLabel, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
            .addGap(30, 30, 30);

        for (int idx = 0; idx < tableHeader.length; idx++) {
            JLabel label = new JLabel(searchLabels[idx], SwingConstants.TRAILING);
            JComponent field = searchFields[idx];
            label.setFont(new java.awt.Font("Segoe UI", 1, 12));
            searchForm.setLayer(label, JLayeredPane.DEFAULT_LAYER);
            parallelGroup = parallelGroup.addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addComponent(label, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(field, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE));
            sequentialGroup = sequentialGroup
                .addGroup(jLayeredPane1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(label, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                    .addComponent(field))
                .addGap(18, 18, 18);
            if (field instanceof JTextField textField) {
                textField.addActionListener((evt) -> {updateTableData();});
            } else if (field instanceof JComboBox comboBox) {
                comboBox.addActionListener((evt) -> {updateTableData();});
            } else if (field instanceof JSpinner spinner) {
                spinner.addChangeListener((evt) -> {updateTableData();});
            }
        }

        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(searchLabel, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                .addGap(97, 97, 97))
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(parallelGroup)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(sequentialGroup
                .addGap(184, 184, 184))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        GroupLayout.SequentialGroup buttonSequentialGroup = layout.createSequentialGroup()
            .addGap(0, 0, Short.MAX_VALUE);
        GroupLayout.ParallelGroup buttonParallelGroup = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);

        for (JButton button : tableButtons) {
            buttonSequentialGroup = buttonSequentialGroup
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button);
            buttonParallelGroup = buttonParallelGroup.addComponent(button);
        }

        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(header, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(searchForm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(buttonSequentialGroup)
                    .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE))
                .addGap(15, 15, 15))
        );

        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(header, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 355, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(buttonParallelGroup))
                    .addComponent(searchForm))
                .addGap(0, 0, 0))
         );

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        pack();
    }

    protected abstract void exit();
    
    protected abstract void updateTableData();

    protected abstract void dataEditSetEnabled(boolean enabled);

    protected Object getInputOf(int inputIndex) {
        JComponent searchField = searchFields[inputIndex];
        if (searchField instanceof JTextField textField) {
            return textField.getText();
        } else if (searchField instanceof JComboBox comboBox) {
            return comboBox.getSelectedItem();
        } else if (searchField instanceof JSpinner spinner) {
            return spinner.getValue();
        }

        return null;
    }

    protected void setInputOf(int inputIndex,Object value) {
        JComponent searchField = searchFields[inputIndex];
        if (searchField instanceof JTextField textField) {
            textField.setText((String)value);
        } else if (searchField instanceof JComboBox comboBox) {
            comboBox.setSelectedItem(value);
        } else if (searchField instanceof JSpinner spinner) {
            spinner.setValue(value);
        }
    }
 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected JButton backButton;
    protected JLabel titleLabel;
    protected JLabel searchLabel;
    protected JLayeredPane searchForm;
    protected JPanel header;
    protected JScrollPane scrollPane;
    protected JTable table;
    // End of variables declaration//GEN-END:variables
    protected javax.swing.table.DefaultTableModel tableModel;
    protected String[] tableHeader;
    protected int selectedColumn = 0;
    protected int lastSelectedColumn;
    protected boolean orderASC = true;

    protected JComponent[] searchFields;
    protected JButton[] tableButtons;
    protected Object[][] data = new Object[][] {};
 }
 