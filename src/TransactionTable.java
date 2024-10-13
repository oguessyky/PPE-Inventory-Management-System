import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Comparator;
import java.util.function.Predicate;
import javax.swing.*;
public class TransactionTable extends DataTable {

    DefaultComboBoxModel<Partner> supplierComboBoxModel;
    DefaultComboBoxModel<Partner> hospitalComboBoxModel;


    public TransactionTable() {
        super("INVENTORY MANAGEMENT",
            new String[] {
                "Transaction ID",
                "Item Code",
                "Item Name",
                "Quantity",
                "Type",
                "Partner Code",
                "Partner Name",
                "Date"
            },
            new Class<?>[] {String.class,String.class,String.class,int.class,Transaction.Type.class,String.class,String.class,String.class},
            new String[] {
                "Transaction ID :",
                "Item :",
                "Min Quantity",
                "Max Quantity",
                "Type :",
                "Partner :",
                "After Date :",
                "Before Date :"
            },
            new JComponent[] {
                new JTextField(),
                new JComboBox<Item>(),
                new JSpinner(new SpinnerNumberModel(0, 0, null, 1)),
                new JSpinner(new SpinnerNumberModel(Integer.MAX_VALUE, 0, null, 1)),
                new JComboBox<>(new Transaction.Type[] { null, Transaction.Type.Received, Transaction.Type.Distributed }),
                new JComboBox<Partner>(),
                new JSpinner(new SpinnerDateModel(new Date(0), null, null, Calendar.DAY_OF_MONTH)),
                new JSpinner(new SpinnerDateModel(new Date(Long.MAX_VALUE), null, null, Calendar.DAY_OF_MONTH))
            },
            new JButton[] {
                new JButton("View Stock"),
                new JButton("View Summary"),
                new JButton("Receive Items"),
                new JButton("Distribute Items")
            }
        );

        ArrayList<Supplier> supplierList = Records.getSupplierList(Supplier.IsActive());
        supplierList.add(0,null);
        supplierComboBoxModel = new DefaultComboBoxModel<>(supplierList.toArray(new Supplier[0]));

        ArrayList<Hospital> hospitalList = Records.getHospitalList(Supplier.IsActive());
        hospitalList.add(0,null);
        hospitalComboBoxModel = new DefaultComboBoxModel<>(hospitalList.toArray(new Hospital[0]));

        ArrayList<Item> itemList = Records.getItemList();
        itemList.add(0,null);
        ((JComboBox<Item>)searchFields[1]).setModel(new DefaultComboBoxModel<>(itemList.toArray(new Item[0])));

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
            // todo link page
        });

        tableButtons[2].addActionListener((evt) -> {
            dispose();
            Main.editForm(Main.DataType.Transaction, Transaction.Type.Received);
        });

        tableButtons[3].addActionListener((evt) -> {
            dispose();
            Main.editForm(Main.DataType.Transaction, Transaction.Type.Distributed);
        });

        searchFields[5].setEnabled(false);

        ((JComboBox<?>)searchFields[4]).addActionListener((evt) -> {
            if (getInputOf(4) != null) {
                ((JComboBox<Partner>)searchFields[5]).setModel(
                switch ((Transaction.Type)getInputOf(4)) {
                    case Received -> supplierComboBoxModel;
                    case Distributed -> hospitalComboBoxModel;
                });
                searchFields[5].setEnabled(true);
            } else {
                ((JComboBox<Partner>)searchFields[5]).setModel(new DefaultComboBoxModel<>());
                searchFields[5].setEnabled(false);
            }
        });

        ((JSpinner)searchFields[6]).setEditor(new JSpinner.DateEditor((JSpinner)searchFields[6],"dd/MM/yyyy"));
        ((JSpinner)searchFields[7]).setEditor(new JSpinner.DateEditor((JSpinner)searchFields[7],"dd/MM/yyyy"));
    }

    @Override
    protected void exit() {
        dispose();
        Main.showMenu();
    }

    @Override
    protected void dataEditSetEnabled(boolean enabled) {}

    @Override
    protected void updateTableData() {
        Predicate<Transaction> filter = Transaction.IDContains((String)getInputOf(0))
        .and(Transaction.WithQuantityBetween((int)getInputOf(2), (int)getInputOf(3)))
        .and(Transaction.WithDateBetween((Date)getInputOf(6), (Date)getInputOf(7)));
        if (getInputOf(1) != null) {
            filter = filter.and(Transaction.WithItem((Item)getInputOf(1)));
        }
        if (getInputOf(4) != null) {
            filter = filter.and(Transaction.WithType((Transaction.Type)getInputOf(4)));
            if (getInputOf(5) != null) {
                filter = filter.and(Transaction.WithPartner((Partner)getInputOf(5)));
            }
        }
        String[] newHeader = tableHeader.clone();
        Comparator<Transaction> sorter = switch (selectedColumn) {
            case 0 -> Transaction.IDComparator;
            case 1 -> Transaction.ItemComparator;
            case 2 -> Transaction.ItemComparator(Item.NameComparator);
            case 3 -> Transaction.QuantityComparator;
            case 4 -> Transaction.TypeComparator;
            case 5 -> Transaction.PartnerComparator;
            case 6 -> Transaction.PartnerComparator(Partner.NameComparator);
            case 7 -> Transaction.DateComparator;
            default -> throw new IndexOutOfBoundsException("Selected Column out of bounds");
        };

        if (orderASC) {
            newHeader[selectedColumn] += " \u25B2";
        } else {
            sorter = sorter.reversed();
            newHeader[selectedColumn] += " \u25BC";
        }
        sorter = sorter.thenComparing(Transaction.IDComparator);
        transactionList = Records.getTransactionList(filter,sorter);
        data = new Object[transactionList.size()][tableHeader.length];
        Transaction transaction;
        Item item;
        Partner partner;
        for (int idx = 0; idx < transactionList.size(); idx++) {
            transaction = transactionList.get(idx);
            item = transaction.getItem();
            partner = transaction.getPartner();
            data[idx] = new Object[] {
                transaction.getTransactionID(),
                item.getItemCode(),
                item.getName(),
                transaction.getQuantity(),
                transaction.getTransactionType(),
                partner.getPartnerCode(),
                partner.getName(),
                String.format("%1$td/%1$tm/%1$tY" , transaction.getDate())
            };
        }
        tableModel.setDataVector(data, newHeader);
    }
    public static void main(String[] args) {
        Records.readRecords();
        new TransactionTable().setVisible(true);
        System.out.print(Records.getTransactionList());
    }

    ArrayList<Transaction> transactionList;
    boolean viewSummary = false;
}
