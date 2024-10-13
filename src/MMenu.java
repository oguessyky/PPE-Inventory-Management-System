import javax.swing.*;
public class MMenu extends JFrame {

    protected JButton[] menuButtons;

    /**
     * Creates new form Admin_Menu
     */
    public MMenu(JButton[] menuButtons) {
        
        
        jPanel1 = new javax.swing.JPanel();
        Admin_H = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        userManagement_menu.setBackground(new java.awt.Color(0, 51, 102));
        userManagement_menu.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 18)); // NOI18N
        userManagement_menu.setForeground(new java.awt.Color(255, 255, 255));
        userManagement_menu.setText("User management");
        userManagement_menu.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                userManagement_menuComponentHidden(evt);
            }
        });
        userManagement_menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userManagement_menuActionPerformed(evt);
            }
        });

        supplierManagement_menu.setBackground(new java.awt.Color(0, 102, 153));
        supplierManagement_menu.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 18)); // NOI18N
        supplierManagement_menu.setForeground(new java.awt.Color(255, 255, 255));
        supplierManagement_menu.setText("Supplier management");
        supplierManagement_menu.addActionListener((evt) -> { Main.hideMenu(); Main.manage(Main.DataType.Supplier); });

        hospitalManagement_menu.setBackground(new java.awt.Color(0, 153, 153));
        hospitalManagement_menu.setFont(new java.awt.Font("Swis721 Cn BT", 1, 18)); // NOI18N
        hospitalManagement_menu.setForeground(new java.awt.Color(255, 255, 255));
        hospitalManagement_menu.setText("Hospital management");
        hospitalManagement_menu.addActionListener((evt) -> { Main.hideMenu(); Main.manage(Main.DataType.Hospital); });

        inventoryManagement_menu.setBackground(new java.awt.Color(0, 204, 204));
        inventoryManagement_menu.setFont(new java.awt.Font("Swis721 Cn BT", 1, 18)); // NOI18N
        inventoryManagement_menu.setForeground(new java.awt.Color(255, 255, 255));
        inventoryManagement_menu.setText("Inventory management");

        jPanel1.setBackground(new java.awt.Color(0, 51, 102));

        Admin_H.setBackground(new java.awt.Color(255, 255, 255));
        Admin_H.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 48)); // NOI18N
        Admin_H.setForeground(new java.awt.Color(255, 255, 255));
        Admin_H.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Admin_H.setText("ADMIN MENU");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Admin_H, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(Admin_H)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(156, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(inventoryManagement_menu, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(supplierManagement_menu, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(userManagement_menu, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hospitalManagement_menu, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(141, 141, 141))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {hospitalManagement_menu, inventoryManagement_menu, supplierManagement_menu, userManagement_menu});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(userManagement_menu, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(supplierManagement_menu, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(hospitalManagement_menu, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(inventoryManagement_menu, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(58, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {hospitalManagement_menu, inventoryManagement_menu, supplierManagement_menu, userManagement_menu});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void userManagement_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userManagement_menuActionPerformed
        Main.hideMenu();
        Main.manage(Main.DataType.User);
    }//GEN-LAST:event_userManagement_menuActionPerformed

    private void userManagement_menuComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_userManagement_menuComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_userManagement_menuComponentHidden

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
      
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminMenu().setVisible(true);
            }
        });
        
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
            java.util.logging.Logger.getLogger(AdminMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Admin_H;
    private javax.swing.JButton hospitalManagement_menu;
    private javax.swing.JButton inventoryManagement_menu;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton supplierManagement_menu;
    private javax.swing.JButton userManagement_menu;
    // End of variables declaration//GEN-END:variables
}
