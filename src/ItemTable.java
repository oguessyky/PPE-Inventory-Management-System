import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;
import javax.swing.*;

public class ItemTable extends DataTable {

    public ItemTable() {
        super("INVENTORY MANAGEMENT",
            new String[] {"Item Code","Item Name","Supplier Code","Supplier Name","Current Stock"},
            new Class<?>[] {String.class,String.class,String.class,String.class,int.class},
            new String[] {
                "Item Code :",
                "Item Name :",
                "Supplier :",
                "Min Stock",
                "Max Stock"
            },
            new JComponent[] {
                new JTextField(),
                new JTextField(),
                new JComboBox<Supplier>(),
                new JSpinner(new SpinnerNumberModel(0, 0, null, 1)),
                new JSpinner(new SpinnerNumberModel(Integer.MAX_VALUE, 0, null, 1))
            },
            new JButton[] {
                new JButton("View Transaction"),
                new JButton("Change Supplier"),
                new JButton("Receive Items"),
                new JButton("Distribute Items")
            }
        );
        
        ArrayList<Supplier> supplierList = Records.getSupplierList(Supplier.IsActive());
        supplierList.add(0,null);
        ((JComboBox<Supplier>)searchFields[2]).setModel(new DefaultComboBoxModel<>(supplierList.toArray(new Supplier[0])));


        for (JButton button : tableButtons) {
            button.setBackground(new java.awt.Color(0, 51, 102));
            button.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
            button.setForeground(new java.awt.Color(255, 255, 255));
        }

        tableButtons[0].addActionListener((evt) -> {
            dispose();
            // todo link page
        });

        tableButtons[1].addActionListener((evt) -> {
            dispose();
            Main.editForm(Main.DataType.Item, itemList.get(table.getSelectedRow()));
        });

        tableButtons[2].addActionListener((evt) -> {
            dispose();
            Main.editForm(Main.DataType.Transaction, Transaction.Type.Received);
        });

        tableButtons[3].addActionListener((evt) -> {
            dispose();
            Main.editForm(Main.DataType.Transaction, Transaction.Type.Distributed);
        });

        tableButtons[1].setVisible(Main.getUser().getUserType() == User.Type.Admin);
    }

    @Override
    protected void exit() {
        dispose();
        Main.showMenu();
    }

    @Override
    protected void dataEditSetEnabled(boolean enabled) {
        tableButtons[1].setEnabled(enabled);
    }

    @Override
    protected void updateTableData() {
        Predicate<Item> filter = Item.CodeContains((String)getInputOf(0))
        .and(Item.NameContains((String)getInputOf(1)))
        .and(Item.WithQuantityBetween((int)getInputOf(3), (int)getInputOf(4)));
        if (getInputOf(2) != null) {
            filter = filter.and(Item.WithSupplier((Supplier)getInputOf(2)));
        }
        String[] newHeader = tableHeader.clone();
        Comparator<Item> sorter = switch (selectedColumn) {
            case 0 -> Item.CodeComparator;
            case 1 -> Item.NameComparator;
            case 2 -> Item.SupplierCodeComparator;
            case 3 -> Item.SupplierNameComparator;
            case 4 -> Item.QuantityComparator;
            default -> throw new IndexOutOfBoundsException("Selected Column out of bounds");
        };

        if (orderASC) {
            newHeader[selectedColumn] += " \u25B2";
        } else {
            sorter = sorter.reversed();
            newHeader[selectedColumn] += " \u25BC";
        }
        sorter = sorter.thenComparing(Item.CodeComparator);
        itemList = Records.getItemList(filter,sorter);
        data = new Object[itemList.size()][tableHeader.length];
        Item item;
        Supplier itemSupplier;
        for (int idx = 0; idx < itemList.size(); idx++) {
            item = itemList.get(idx);
            itemSupplier = item.getSupplier();
            data[idx] = new Object[] {
                item.getItemCode(),
                item.getName(),
                itemSupplier.getPartnerCode(),
                itemSupplier.getName(),
                item.getQuantity()
            };
        }
        tableModel.setDataVector(data, newHeader);
    }
    public static void main(String[] args) {
        Records.readRecords();
        Main.initializeMenu(Records.getUser("21"));
        Main.hideMenu();
        new ItemTable().setVisible(true);
    }

    ArrayList<Item> itemList;
}
