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
        Main.manage(Main.DataType.Supplier);
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
                Main.manage(Main.DataType.Supplier);
            }
        }
    }
}
