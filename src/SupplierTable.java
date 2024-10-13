import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;
import javax.swing.*;
public class SupplierTable extends DataTable {
    public SupplierTable() {
        super("SUPPLIER MANAGEMENT",
            new String[] {"Supplier Code","Name","Address"},
            new Class<?>[] {String.class,String.class,String.class},
            new String[] {"Supplier Code :","Name :","Address :"},
            new JComponent[] {
                new JTextField(),
                new JTextField(),
                new JTextField()
            },
            new JButton[] {
                new JButton("Add Supplier"),
                new JButton("Edit Supplier"),
                new JButton("Set Inactive")
            }
        );

        tableButtons[0].setBackground(new java.awt.Color(0, 51, 102));
        tableButtons[0].setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tableButtons[0].setForeground(new java.awt.Color(255, 255, 255));
        tableButtons[0].addActionListener((evt) -> {
            dispose();
            Main.newForm(Main.DataType.Supplier);
        });

        tableButtons[1].setBackground(new java.awt.Color(0, 51, 102));
        tableButtons[1].setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tableButtons[1].setForeground(new java.awt.Color(255, 255, 255));
        tableButtons[1].addActionListener((evt) -> {
            dispose();
            Main.editForm(Main.DataType.Supplier, supplierList.get(table.getSelectedRow()));
        });

        tableButtons[2].setBackground(new java.awt.Color(153, 0, 51));
        tableButtons[2].setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tableButtons[2].setForeground(new java.awt.Color(255, 255, 255));
        tableButtons[2].addActionListener((evt) -> {
            Supplier supplier = supplierList.get(table.getSelectedRow());
            if (Records.getItemList(Item.WithSupplier(supplier)).isEmpty()) {
                supplier.setInactive();
                Records.updateRecords();
                updateTableData();
            } else {
                Main.showError(this, "Supplier in use!\nPlease change item supplier.");
            }
        });
    }

    @Override
    protected void dataEditSetEnabled(boolean enabled) {
        tableButtons[1].setEnabled(enabled);
        tableButtons[2].setEnabled(enabled);
    }

    @Override
    protected void updateTableData() {
        Predicate<Partner> filter = Supplier.IsActive()
        .and(Supplier.CodeContains((String)getInputOf(0)))
        .and(Supplier.NameContains((String)getInputOf(1)))
        .and(Supplier.AddressContains((String)getInputOf(2)));
        String[] newHeader = tableHeader.clone();
        Comparator<Partner> sorter = switch (selectedColumn) {
            case 0 -> Supplier.CodeComparator;
            case 1 -> Supplier.NameComparator;
            case 2 -> Supplier.AddressComparator;
            default -> throw new IndexOutOfBoundsException("Selected Column out of bounds");
        };

        if (orderASC) {
            newHeader[selectedColumn] += " \u25B2";
        } else {
            sorter = sorter.reversed();
            newHeader[selectedColumn] += " \u25BC";
        }
        sorter = sorter.thenComparing(Supplier.CodeComparator);
        supplierList = Records.getSupplierList(filter,sorter);
        data = new Object[supplierList.size()][tableHeader.length];
        Supplier supplier;
        for (int idx = 0; idx < supplierList.size(); idx++) {
            supplier = supplierList.get(idx);
            data[idx] = new Object[] {
                supplier.getPartnerCode(),
                supplier.getName(),
                supplier.getAddress()
            };
        }
        tableModel.setDataVector(data, newHeader);
    }

    ArrayList<Supplier> supplierList;
}
