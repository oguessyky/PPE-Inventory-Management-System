import javax.swing.*;
public class ItemEdit extends Form {

    String itemCode, name;

    public ItemEdit(String itemCode, String name) {
        super("ADD ITEM",
        new String[] {
            "Item Code :",
            "Name :",
            "Supplier :",
            "Quantity :"
        },
        new JComponent[] {
            new JTextField(),
            new JTextField(),
            new JComboBox<>(Records.getSupplierList(Supplier.IsActive()).toArray(new Supplier[0])),
            new JSpinner(new SpinnerNumberModel(100,0,null,1))
        });
        backButton.setVisible(false);
        inputFields[0].setEnabled(false);
        inputFields[1].setEnabled(false);
        setInputOf(0, itemCode);
        setInputOf(1, name);
        this.itemCode = itemCode;
        this.name = name;
    }


    @Override
    protected void exit() {
        this.dispose();
    }

    @Override
    protected void submit() {
        Supplier supplier = (Supplier)getInputOf(2);
        int quantity = (int)getInputOf(3);
        Records.addItem(new Item(itemCode, name, supplier, quantity));
        Records.updateRecords();
        this.dispose();
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ItemEdit("HS","headsomething").setVisible(true);
            }
        });
    }

}
