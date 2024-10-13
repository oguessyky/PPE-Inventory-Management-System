import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Comparator;
import java.util.Set;
import java.util.Map;
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
                new JSpinner(new SpinnerDateModel(new Date(Long.MAX_VALUE), null, null, Calendar.DAY_OF_MONTH))
            },
            new JButton[] {
                new JButton("View Stock"),
                new JButton("View Individual"),
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
        ((JComboBox<Item>)searchFields[0]).setModel(new DefaultComboBoxModel<>(itemList.toArray(new Item[0])));

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
    protected void exit() {
        dispose();
        Main.showMenu();
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
        Comparator<Transaction> sorter = switch (selectedColumn) {
            case 0 -> Transaction.ItemComparator;
            case 1 -> Transaction.ItemComparator(Item.NameComparator);
            case 2 -> Transaction.TypeComparator;
            case 3 -> Transaction.PartnerComparator;
            case 4 -> Transaction.PartnerComparator(Partner.NameComparator);
            case 5 -> null;
            case 6 -> null;
            case 7 -> null;
            default -> throw new IndexOutOfBoundsException("Selected Column out of bounds");
        };

        Comparator<Transaction.Summary.SummaryContent> sorter2 = switch (selectedColumn) {
            case 0 -> null;
            case 1 -> null;
            case 2 -> null;
            case 3 -> null;
            case 4 -> null;
            case 5 -> Transaction.Summary.totalComparator;
            case 6 -> Transaction.Summary.averageComparator;
            case 7 -> Transaction.Summary.countComparator;
            default -> throw new IndexOutOfBoundsException("Selected Column out of bounds");
        };

        if (orderASC) {
            newHeader[selectedColumn] += " \u25B2";
        } else {
            if (sorter != null) {
                sorter = sorter.reversed();
            } else {
                sorter2 = sorter2.reversed();
            }
            newHeader[selectedColumn] += " \u25BC";
        }
        if (sorter != null) {
            transactionList = Records.getTransactionList(filter,sorter);
        } else {
            transactionList = Records.getTransactionList(filter);
        }

        Set<Map.Entry<Transaction.Summary.SummaryKey,Transaction.Summary.SummaryContent>> summaryList;
        if (sorter2 != null) {
            summaryList = new Transaction.Summary(transactionList).getSummaryList(sorter2);
        } else {
            summaryList = new Transaction.Summary(transactionList).getSummaryList();
        }

        data = new Object[summaryList.size()][tableHeader.length];
        Transaction.Summary.SummaryKey summaryKey;
        Transaction.Summary.SummaryContent summaryContent;
        Item item;
        Partner partner;
        int idx = 0;

        for (Map.Entry<Transaction.Summary.SummaryKey,Transaction.Summary.SummaryContent> summary : summaryList) {
            summaryKey = summary.getKey();
            summaryContent = summary.getValue();
            item = summaryKey.getItem();
            partner = summaryKey.getPartner();
            data[idx++] = new Object[] {
                item.getItemCode(),
                item.getName(),
                (partner instanceof Supplier) ? Transaction.Type.Received : Transaction.Type.Distributed,
                partner.getPartnerCode(),
                partner.getName(),
                summaryContent.getTotalQuantity(),
                summaryContent.getAverageQuantity(),
                summaryContent.getCount()
            };
        }
        tableModel.setDataVector(data, newHeader);
    }
    public static void main(String[] args) {
        Records.readRecords();
        new TransactionSummaryTable().setVisible(true);
        System.out.print(Records.getTransactionList());
    }

    ArrayList<Transaction> transactionList;
}
