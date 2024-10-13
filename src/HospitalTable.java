import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;
import javax.swing.*;
public class HospitalTable extends DataTable {
    public HospitalTable() {
        super("HOSPITAL MANAGEMENT",
            new String[] {"Hospital Code","Name","Address"},
            new Class<?>[] {String.class,String.class,String.class},
            new String[] {"Hospital Code :","Name :","Address :"},
            new JComponent[] {
                new JTextField(),
                new JTextField(),
                new JTextField()
            },
            new JButton[] {
                new JButton("Add Hospital"),
                new JButton("Edit Hospital"),
                new JButton("Set Inactive")
            }
        );

        tableButtons[0].setBackground(new java.awt.Color(0, 51, 102));
        tableButtons[0].setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tableButtons[0].setForeground(new java.awt.Color(255, 255, 255));
        tableButtons[0].addActionListener((evt) -> {
            dispose();
            Main.newForm(Main.DataType.Hospital);
        });

        tableButtons[1].setBackground(new java.awt.Color(0, 51, 102));
        tableButtons[1].setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tableButtons[1].setForeground(new java.awt.Color(255, 255, 255));
        tableButtons[1].addActionListener((evt) -> {
            dispose();
            Main.editForm(Main.DataType.Hospital,hospitalList.get(table.getSelectedRow()));
        });

        tableButtons[2].setBackground(new java.awt.Color(153, 0, 51));
        tableButtons[2].setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tableButtons[2].setForeground(new java.awt.Color(255, 255, 255));
        tableButtons[2].addActionListener((evt) -> {
            hospitalList.get(table.getSelectedRow()).setInactive();
            Records.updateRecords();
            updateTableData();
        });
    }

    @Override
    protected void exit() {
        dispose();
        Main.showMenu();
    }

    @Override
    protected void dataEditSetEnabled(boolean enabled) {
        tableButtons[1].setEnabled(enabled);
        tableButtons[2].setEnabled(enabled);
    }

    @Override
    protected void updateTableData() {
        Predicate<Partner> filter = Hospital.IsActive()
        .and(Hospital.CodeContains((String)getInputOf(0)))
        .and(Hospital.NameContains((String)getInputOf(1)))
        .and(Hospital.AddressContains((String)getInputOf(2)));
        String[] newHeader = tableHeader.clone();
        Comparator<Partner> sorter = switch (selectedColumn) {
            case 0 -> Hospital.CodeComparator;
            case 1 -> Hospital.NameComparator;
            case 2 -> Hospital.AddressComparator;
            default -> throw new IndexOutOfBoundsException("Selected Column out of bounds");
        };

        if (orderASC) {
            newHeader[selectedColumn] += " \u25B2";
        } else {
            sorter = sorter.reversed();
            newHeader[selectedColumn] += " \u25BC";
        }
        sorter = sorter.thenComparing(Hospital.CodeComparator);
        hospitalList = Records.getHospitalList(filter,sorter);
        data = new Object[hospitalList.size()][tableHeader.length];
        Hospital hospital;
        for (int idx = 0; idx < hospitalList.size(); idx++) {
            hospital = hospitalList.get(idx);
            data[idx] = new Object[] {
                hospital.getPartnerCode(),
                hospital.getName(),
                hospital.getAddress()
            };
        }
        tableModel.setDataVector(data, newHeader);
    }
    
    ArrayList<Hospital> hospitalList;
}
