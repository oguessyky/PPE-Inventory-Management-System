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
            new JSpinner(new SpinnerNumberModel(0,0,null,1)),
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
        // todo link page
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
            // todo link page
        }
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(update.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(update.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(update.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(update.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        Records.readRecords();
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TransactionEdit(Transaction.Type.Received).setVisible(true);
            }
        });
    }

}
