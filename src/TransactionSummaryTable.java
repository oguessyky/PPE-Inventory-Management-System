import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.function.Predicate;
import javax.swing.*;
public class TransactionSummaryTable extends DataTable {

    DefaultComboBoxModel<Partner> supplierComboBoxModel;
    DefaultComboBoxModel<Partner> hospitalComboBoxModel;

    public TransactionSummaryTable() {
        super("INVENTORY MANAGEMENT",
            new String[] {
                "Item Code",
                "Item Name",
                "Type",
                "Partner Code",
                "Partner Name",
                "Total Quantity",
                "Average Quantity",
                "Transaction Count"
            },
            new Class<?>[] {String.class,String.class,Transaction.Type.class,String.class,String.class,int.class,int.class,double.class},
            new String[] {
                "Item :",
                "Type :",
                "Partner :",
                "After Date :",
                "Before Date :"
            },
            new JComponent[] {
                new JComboBox<Item>(),
                new JComboBox<>(new Transaction.Type[] { null, Transaction.Type.Received, Transaction.Type.Distributed }),
                new JComboBox<Partner>(),
                new JSpinner(new SpinnerDateModel(new Date(0), null, null, Calendar.DAY_OF_MONTH)),
                new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH))
            },
            new JButton[] {
                new JButton("View Stock"),
                new JButton("View Individual Transactions"),
                new JButton("Receive Items"),
                new JButton("Distribute Items")
            }
        );

        ArrayList<Supplier> supplierList = new ArrayList<>(Records.getSupplierList(Supplier.IsActive()));
        supplierList.add(0,null);
        supplierComboBoxModel = new DefaultComboBoxModel<>(supplierList.toArray(new Supplier[0]));

        ArrayList<Hospital> hospitalList = new ArrayList<>(Records.getHospitalList(Supplier.IsActive()));
        hospitalList.add(0,null);
        hospitalComboBoxModel = new DefaultComboBoxModel<>(hospitalList.toArray(new Hospital[0]));

        ArrayList<Item> itemList = new ArrayList<>(Records.getItemList());
        itemList.add(0,null);
        ((JComboBox<Item>)searchFields[0]).setModel(new DefaultComboBoxModel<>(itemList.toArray(new Item[0])));

        for (JButton button : tableButtons) {
            button.setBackground(new java.awt.Color(0, 51, 102));
            button.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
            button.setForeground(new java.awt.Color(255, 255, 255));
        }

        tableButtons[0].addActionListener((evt) -> {
            dispose();
            Main.manage(Main.DataType.Item);
        });

        tableButtons[1].addActionListener((evt) -> {
            dispose();
            Main.manage(Main.DataType.Transaction);
        });

        tableButtons[2].addActionListener((evt) -> {
            dispose();
            Main.editForm(Main.DataType.Transaction, Transaction.Type.Received);
        });

        tableButtons[3].addActionListener((evt) -> {
            dispose();
            Main.editForm(Main.DataType.Transaction, Transaction.Type.Distributed);
        });

        searchFields[2].setEnabled(false);

        ((JComboBox<?>)searchFields[1]).addActionListener((evt) -> {
            if (getInputOf(1) != null) {
                ((JComboBox<Partner>)searchFields[2]).setModel(
                switch ((Transaction.Type)getInputOf(1)) {
                    case Received -> supplierComboBoxModel;
                    case Distributed -> hospitalComboBoxModel;
                });
                searchFields[2].setEnabled(true);
            } else {
                ((JComboBox<Partner>)searchFields[2]).setModel(new DefaultComboBoxModel<>());
                searchFields[2].setEnabled(false);
            }
        });

        ((JSpinner)searchFields[3]).setEditor(new JSpinner.DateEditor((JSpinner)searchFields[3],"dd/MM/yyyy"));
        ((JSpinner)searchFields[4]).setEditor(new JSpinner.DateEditor((JSpinner)searchFields[4],"dd/MM/yyyy"));
    }

    @Override
    protected void dataEditSetEnabled(boolean enabled) {}

    @Override
    protected void updateTableData() {
        Predicate<Transaction> filter = Transaction.WithDateBetween((Date)getInputOf(3), (Date)getInputOf(4));
        if (getInputOf(0) != null) {
            filter = filter.and(Transaction.WithItem((Item)getInputOf(0)));
        }
        if (getInputOf(1) != null) {
            filter = filter.and(Transaction.WithType((Transaction.Type)getInputOf(1)));
            if (getInputOf(2) != null) {
                filter = filter.and(Transaction.WithPartner((Partner)getInputOf(2)));
            }
        }
        String[] newHeader = tableHeader.clone();
        Comparator<Transaction.Summary> sorter = switch (selectedColumn) {
            case 0 -> Transaction.Summary.ItemComparator;
            case 1 -> Transaction.Summary.ItemComparator(Item.NameComparator);
            case 2 -> Transaction.Summary.TypeComparator;
            case 3 -> Transaction.Summary.PartnerComparator;
            case 4 -> Transaction.Summary.PartnerComparator(Partner.NameComparator);
            case 5 -> Transaction.Summary.TotalComparator;
            case 6 -> Transaction.Summary.AverageComparator;
            case 7 -> Transaction.Summary.CountComparator;
            default -> throw new IndexOutOfBoundsException("Selected Column out of bounds");
        };

        if (orderASC) {
            newHeader[selectedColumn] += " \u25B2";
        } else {
            sorter = sorter.reversed();
            newHeader[selectedColumn] += " \u25BC";
        }
        summaryList = Transaction.Summary.getSummaryList(Records.getTransactionList(filter),sorter);

        data = new Object[summaryList.size()][tableHeader.length];
        Transaction.Summary summary;
        Item item;
        Partner partner;

        for (int idx = 0; idx < summaryList.size(); idx++) {
            summary = summaryList.get(idx);
            item = summary.getItem();
            partner = summary.getPartner();
            data[idx] = new Object[] {
                item.getItemCode(),
                item.getName(),
                summary.getType(),
                partner.getPartnerCode(),
                partner.getName(),
                summary.getTotalQuantity(),
                summary.getAverageQuantity(),
                summary.getCount()
            };
        }
        tableModel.setDataVector(data, newHeader);
    }

    ArrayList<Transaction.Summary> summaryList;
}
