import java.util.ArrayList;
import javax.swing.*;
public class TransactionTable extends DataTable {
    public TransactionTable() {
        super("INVENTORY MANAGEMENT",
            new String[] {"Item Code","Item Name","Supplier Code","Supplier Name","Current Stock"},
            new Class<?>[] {String.class,String.class,String.class,String.class,int.class},
            new String[] {
                "Item Code :",
                "Item Name :",
                "Supplier Code :",
                "Supplier Name :",
                "Min Stock",
                "Max Stock"
            },
            new JComponent[] {
                new JTextField(),
                new JTextField(),
                new JTextField(),
                new JTextField(),
                new JSpinner(new SpinnerNumberModel(0, 0, null, 1)),
                new JSpinner(new SpinnerNumberModel(25, 0, null, 1))
            },
            new JButton[] {
                new JButton("View Stock"),
                new JButton("View Transaction"),
                new JButton("View Individual"),
                new JButton("View Summary"),
                new JButton("Change Supplier"),
                new JButton("Receive Items"),
                new JButton("Distribute Items")
            }
        );

        for (JButton button : tableButtons) {
            button.setBackground(new java.awt.Color(0, 51, 102));
            button.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
            button.setForeground(new java.awt.Color(255, 255, 255));
        }

        searchLabels[3].setVisible(false);
        searchFields[3].setVisible(false);

        tableButtons[0].addActionListener((evt) -> {
            dispose();
            Main.newForm(Main.DataType.Item);
        });

        tableButtons[1].addActionListener((evt) -> {
            dispose();
            Main.editForm(Main.DataType.Item, supplierList.get(table.getSelectedRow()));
        });

        tableButtons[2].addActionListener((evt) -> {
            // supplierList.get(table.getSelectedRow()).setInactive();
            Records.updateRecords();
            updateTableData();
        });
        

        dataEditSetEnabled(false);
        updateTableData();
    }

    @Override
    protected void exit() {
        dispose();
        Main.showMenu();
    }

    @Override
    protected void dataEditSetEnabled(boolean enabled) {
        tableButtons[1].setEnabled(enabled);
        tableButtons[2].setEnabled(enabled);
    }

    @Override
    protected void updateTableData() {
        // Predicate<Item> filter = Item.IsActive()
        // .and(Item.CodeContains((String)getInputOf(0)))
        // .and(Item.NameContains((String)getInputOf(1)))
        // .and(Item.AddressContains((String)getInputOf(1)));
        // String[] newHeader = tableHeader.clone();
        // Comparator<Item> sorter = switch (selectedColumn) {
        //     case 0 -> Item.CodeComparator;
        //     case 1 -> Item.NameComparator;
        //     case 2 -> Item.QuantityComparator;
        //     default -> throw new IndexOutOfBoundsException("Selected Column out of bounds");
        // };

        // if (orderASC) {
        //     newHeader[selectedColumn] += " \u25B2";
        // } else {
        //     sorter = sorter.reversed();
        //     newHeader[selectedColumn] += " \u25BC";
        // }
        // sorter = sorter.thenComparing(Item.CodeComparator);
        // supplierList = Records.getSupplierList(filter,sorter);
        // data = new Object[supplierList.size()][3];
        // Item supplier;
        // for (int idx = 0; idx < supplierList.size(); idx++) {
        //     supplier = supplierList.get(idx);
        //     data[idx] = new Object[] {
        //         supplier.getPartnerCode(),
        //         supplier.getName(),
        //         supplier.getAddress()
        //     };
        // }
        // tableModel.setDataVector(data, newHeader);
    }
    public static void main(String[] args) {
        Records.readRecords();
        new InventoryTable().setVisible(true);
    }

    ArrayList<Item> supplierList;
}
