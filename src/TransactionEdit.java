import javax.swing.*;
public class TransactionEdit extends EditForm {

    Transaction.Type transactionType;

    public TransactionEdit(Transaction.Type transactionType) {
        super(switch (transactionType) {
            case Received -> "RECEIVE ITEMS";
            case Distributed -> "DISTRIBUTE ITEMS";
        },
        new String[] {
            "Item :",
            "Quantity :",
            switch (transactionType) {
                case Received -> "Supplier :";
                case Distributed -> "Hospital :";
            }
        },
        new JComponent[] {
            new JComboBox<>(Records.getItemList().toArray(new Item[0])),
            new JSpinner(new SpinnerNumberModel(1,1,null,1)),
            new JComboBox<>(switch (transactionType) {
                case Received -> Records.getSupplierList(Supplier.IsActive()).toArray(new Supplier[0]);
                case Distributed -> Records.getHospitalList(Hospital.IsActive()).toArray(new Hospital[0]);
            })
        });
        this.transactionType = transactionType;
        if (transactionType == Transaction.Type.Received) {
            setInputOf(2, ((Item)getInputOf(0)).getSupplier());
            inputFields[2].setEnabled(false);
            ((JComboBox<Item>)inputFields[0]).addActionListener((evt) -> {
                setInputOf(2, ((Item)getInputOf(0)).getSupplier());
            });
        }
    }

    @Override
    protected void exit() {
        this.dispose();
        Main.manage(Main.DataType.Item);
    }

    @Override
    protected void submit() {
        Item item = (Item)getInputOf(0);
        int quantity = (int)getInputOf(1);
        if (transactionType == Transaction.Type.Distributed && item.getQuantity() < quantity) {
            Main.showError(this, String.format("Insufficient stock!\nCurrent quantity: %d",item.getQuantity()));
        } else {
            Partner partner = (Partner)getInputOf(2);
            Records.addTransaction(new Transaction(item, quantity, transactionType, partner));
            this.dispose();
            Main.manage(Main.DataType.Item);
        }
    }
}
