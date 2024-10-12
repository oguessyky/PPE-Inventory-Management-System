import javax.swing.*;
public class SupplierEdit extends EditForm {

    EditType editType;
    Supplier supplier;

    public SupplierEdit() {
        super("ADD SUPPLIER",
        new String[] {
            "Name :",
            "Address :"
        },
        new JComponent[] {
            new JTextField(),
            new JTextField()
        });
        this.editType = EditType.Add;
    }

    public SupplierEdit(boolean firstRun) {
        super("ADD SUPPLIER",
        new String[] {
            "Name :",
            "Address :"
        },
        new JComponent[] {
            new JTextField(),
            new JTextField()
        });
        this.editType = firstRun ? EditType.FirstRun : EditType.Add;
        backButton.setVisible(!firstRun);
    }

    public SupplierEdit(Supplier supplier) {
        super("EDIT SUPPLIER",
        new String[] {
            "Name :",
            "Address :"
        },
        new JComponent[] {
            new JTextField(supplier.getName()),
            new JTextField(supplier.getAddress())
        });
        this.editType = EditType.Update;
        this.supplier = supplier;
    }


    @Override
    protected void exit() {
        this.dispose();
        // TODO link page
    }

    @Override
    protected void submit() {
        String name = (String)getInputOf(0);
        String address = (String)getInputOf(1);

        if (name.isBlank()) {
            Main.showError(this, "Name cannot be blank!");
        } else if (name.contains(";")) {
            Main.showError(this, "Invalid Name!"); // can further restrict via regex if needed
        } else if (address.isBlank()) {
            Main.showError(this, "Address cannot be blank!");
        } else if (address.contains(";")) {
            Main.showError(this, "Invalid Address!"); // can further restrict via regex if needed
        } else {
            switch (editType) {
                case Update -> {
                    supplier.setName(name);
                    supplier.setAddress(address);
                    Records.updateRecords();
                }
                default -> {
                    Records.addSupplier(new Supplier(name,address));
                }
            }
            this.dispose();
            if (editType != EditType.FirstRun) {
                // TODO link page
            }
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SupplierEdit(true).setVisible(true);
            }
        });
    }

}
