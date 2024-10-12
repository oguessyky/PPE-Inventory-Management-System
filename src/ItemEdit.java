import javax.swing.*;
public class ItemEdit extends Form {

    EditType editType;
    Item item;

    public ItemEdit(String itemCode, String name) {
        super("ADD ITEM",
        new String[] {
            "Item Code :",
            "Name :",
            "Supplier :",
            "Quantity :"
        },
        new JComponent[] {
            new JTextField(itemCode),
            new JTextField(name),
            new JComboBox<>(Records.getSupplierList(Supplier.IsActive()).toArray(new Supplier[0])),
            new JSpinner(new SpinnerNumberModel(100,0,null,1))
        });
        backButton.setVisible(false);
        inputFields[0].setEnabled(false);
        inputFields[1].setEnabled(false);
        this.editType = EditType.FirstRun;
    }

    public ItemEdit(Item item) {
        super("EDIT ITEM",
        new String[] {
            "Item Code :",
            "Name :",
            "Supplier :",
            "Quantity :"
        },
        new JComponent[] {
            new JTextField(item.getItemCode()),
            new JTextField(item.getName()),
            new JComboBox<>(Records.getSupplierList(Supplier.IsActive()).toArray(new Supplier[0])),
            new JSpinner(new SpinnerNumberModel(item.getQuantity(),0,null,1))
        });
        this.item = item;
        setInputOf(2, item.getSupplier());
        inputFields[0].setEnabled(false);
        inputFields[1].setEnabled(false);
        inputFields[3].setEnabled(false);
        this.editType = EditType.Update;
    }


    @Override
    protected void exit() {
        this.dispose();
        // todo link page
    }

    @Override
    protected void submit() {
        Supplier supplier = (Supplier)getInputOf(2);
        switch (editType) {
            case Update -> {
                item.setSupplier(supplier);
            }
            default -> {
                String itemCode = (String)getInputOf(0);
                String name = (String)getInputOf(1);
                int quantity = (int)getInputOf(3);
                Records.addItem(new Item(itemCode, name, supplier, quantity));
            }
        }
        Records.updateRecords();
        this.dispose();
        if (editType != EditType.FirstRun) {
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
                new ItemEdit(Records.getItem("HC")).setVisible(true);
            }
        });
    }

}
