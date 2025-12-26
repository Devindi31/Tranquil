package gui.dialog;

import connection.MySQL;
import gui.RoomBookingSection;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import log.Log;

public class SelectCustomer extends javax.swing.JDialog {

  
    private final RoomBookingSection roomBookingPanel;

    /**
     * @param parent
     * @param modal
     * @param mainPanelSection
     */
    public SelectCustomer(java.awt.Frame parent, boolean modal, RoomBookingSection roomBookingSection) {
        super(parent, modal);
        initComponents();
        this.roomBookingPanel = roomBookingSection;
        loadCustomer("");
    }

    private void clear() {

        findCustomerNICTextField.setText("");
        loadCustomer("");
        selectedCustomerNICLabel.setText("None");
        selectedCustomerNameLabel.setText("None");

    }

    private void loadCustomer(String nameOrNic) {

        try {

            ResultSet userData = MySQL.execute("SELECT `NIC`,`first_name`,`last_name`,`mobile` FROM `users` "
                    + "WHERE `user_type_user_type_id`='1' AND (`NIC` LIKE '%" + nameOrNic + "%' OR `first_name` LIKE '%" + nameOrNic + "%' OR `last_name` LIKE '%" + nameOrNic + "%') ORDER BY `registered_date` DESC");

            DefaultTableModel tableModel = (DefaultTableModel) customerTable.getModel();
            tableModel.setRowCount(0);

            while (userData.next()) {

                Vector<String> userVector = new Vector<>();

                userVector.add(userData.getString("NIC"));
                userVector.add(userData.getString("first_name") + " " + userData.getString("last_name"));
                userVector.add(userData.getString("mobile"));

                tableModel.addRow(userVector);

            }

        } catch (Exception e) {
              Log.log.warning(e.toString());
        }

    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        findCustomerNICTextField = new Component.RoundedTextField();
        jLabel1 = new javax.swing.JLabel();
        clearButton = new javax.swing.JButton();
        doneButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        selectedCustomerNICLabel = new javax.swing.JLabel();
        selectedCustomerNameLabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        customerTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Select Customer");
        setPreferredSize(new java.awt.Dimension(933, 620));

        findCustomerNICTextField.setPreferredSize(new java.awt.Dimension(117, 30));
        findCustomerNICTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                findCustomerNICTextFieldKeyReleased(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 153, 153));
        jLabel1.setText(" CUSTOMER NIC OR NAME");

        clearButton.setBackground(new java.awt.Color(102, 102, 102));
        clearButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/Clear.png"))); // NOI18N
        clearButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        doneButton.setBackground(new java.awt.Color(51, 51, 51));
        doneButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        doneButton.setForeground(new java.awt.Color(74, 156, 85));
        doneButton.setText("Done");
        doneButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        doneButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doneButtonActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 153, 153));
        jLabel2.setText(" Customer NIC :");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(153, 153, 153));
        jLabel3.setText("Customer Name :");

        selectedCustomerNICLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        selectedCustomerNICLabel.setForeground(new java.awt.Color(255, 255, 255));
        selectedCustomerNICLabel.setText("None");

        selectedCustomerNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        selectedCustomerNameLabel.setForeground(new java.awt.Color(255, 255, 255));
        selectedCustomerNameLabel.setText("None");

        jLabel4.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 153, 153));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/info.png"))); // NOI18N
        jLabel4.setText(" Double click the table row to select the customer.");

        customerTable.setBackground(new java.awt.Color(29, 29, 29));
        customerTable.setForeground(new java.awt.Color(255, 255, 255));
        customerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NIC Number", "Customer Name", "Mobile Number"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        customerTable.setInheritsPopupMenu(true);
        customerTable.setRowHeight(23);
        customerTable.setShowGrid(true);
        customerTable.setSurrendersFocusOnKeystroke(true);
        customerTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                customerTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(customerTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(selectedCustomerNICLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(selectedCustomerNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 223, Short.MAX_VALUE)
                        .addComponent(doneButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(findCustomerNICTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)))
                .addGap(18, 18, 18))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(clearButton, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                            .addComponent(findCustomerNICTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4)
                        .addGap(3, 3, 3)))
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 431, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(doneButton, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(selectedCustomerNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(selectedCustomerNICLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void customerTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_customerTableMouseClicked

        if (evt.getClickCount() == 2) {

            int selectedRow = customerTable.getSelectedRow();

            String nic = String.valueOf(customerTable.getValueAt(selectedRow, 0));
            String name = String.valueOf(customerTable.getValueAt(selectedRow, 1));

            selectedCustomerNICLabel.setText(nic);
            selectedCustomerNameLabel.setText(name);

        }
    }//GEN-LAST:event_customerTableMouseClicked

    private void findCustomerNICTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_findCustomerNICTextFieldKeyReleased

        String nameOrNic = findCustomerNICTextField.getText();
        loadCustomer(nameOrNic);

    }//GEN-LAST:event_findCustomerNICTextFieldKeyReleased

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        clear();
    }//GEN-LAST:event_clearButtonActionPerformed

    private void doneButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doneButtonActionPerformed

        if (selectedCustomerNICLabel.getText().equals("None")) {

            JOptionPane.showMessageDialog(this, "Please select a customer", "Warning", JOptionPane.WARNING_MESSAGE);

        } else {

            roomBookingPanel.getCustomerNicLabel().setText(selectedCustomerNICLabel.getText());
            roomBookingPanel.getCustomerNameLabel().setText(selectedCustomerNameLabel.getText());
            roomBookingPanel.loadBooking(selectedCustomerNICLabel.getText());
            this.dispose();

        }

    }//GEN-LAST:event_doneButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearButton;
    private javax.swing.JTable customerTable;
    private javax.swing.JButton doneButton;
    private Component.RoundedTextField findCustomerNICTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel selectedCustomerNICLabel;
    private javax.swing.JLabel selectedCustomerNameLabel;
    // End of variables declaration//GEN-END:variables
}
