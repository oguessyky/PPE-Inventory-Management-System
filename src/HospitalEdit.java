import javax.swing.*;
public class HospitalEdit extends EditForm {

    EditType editType;
    Hospital hospital;

    public HospitalEdit() {
        super("ADD HOSPITAL",
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

    public HospitalEdit(boolean firstRun) {
        super("ADD HOSPITAL",
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

    public HospitalEdit(Hospital hospital) {
        super("EDIT HOSPITAL",
        new String[] {
            "Name :",
            "Address :"
        },
        new JComponent[] {
            new JTextField(hospital.getName()),
            new JTextField(hospital.getAddress())
        });
        this.editType = EditType.Update;
        this.hospital = hospital;
    }


    @Override
    protected void exit() {
        this.dispose();
        Main.manage(Main.DataType.Hospital);
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
                    hospital.setName(name);
                    hospital.setAddress(address);
                    Records.updateRecords();
                }
                default -> {
                    Records.addHospital(new Hospital(name,address));
                }
            }
            this.dispose();
            if (editType != EditType.FirstRun) {
                Main.manage(Main.DataType.Hospital);
            }
        }
    }
}
