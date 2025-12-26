package gui;

import connection.MySQL;
import gui.dialog.CustomerRegistration;
import java.awt.Color;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import log.Log;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class CustomerSection extends javax.swing.JPanel {

    private final MainPanel mainPanel;

    /**
     * @param mainPanelSection
     */
    public CustomerSection(MainPanel mainPanelSection) {
        initComponents();

        this.setBackground(new Color(255, 255, 255, 0));
        this.mainPanel = mainPanelSection;
        loadCustomer("");

    }

    public void loadCustomer(String NameOrNIC) {

        try {

            ResultSet customerData = MySQL.execute("SELECT * FROM `users` "
                    + "INNER JOIN `gender` ON `users`.`gender_gender_id`=`gender`.`gender_id` "
                    + "INNER JOIN `nationality` ON `users`.`nationality_nationality_id`=`nationality`.`nationality_id` "
                    + "WHERE `user_type_user_type_id`='1' AND (`NIC` LIKE '%" + NameOrNIC + "%' OR `first_name` LIKE '%" + NameOrNIC + "%' OR `last_name` LIKE '%" + NameOrNIC + "%') ORDER BY `registered_date` DESC");

            DefaultTableModel tableModel = (DefaultTableModel) customerTable.getModel();
            tableModel.setRowCount(0);

            while (customerData.next()) {

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                Date inputBirthday = dateFormat.parse(customerData.getString("birthday"));
                String birthday = dateFormat.format(inputBirthday);

                Vector<String> customerVector = new Vector<>();
                customerVector.add(customerData.getString("NIC"));
                customerVector.add(customerData.getString("first_name") + " " + customerData.getString("last_name"));
                customerVector.add(birthday);
                customerVector.add(customerData.getString("mobile"));
                customerVector.add(customerData.getString("email"));
                customerVector.add(customerData.getString("nationality_name"));
                customerVector.add(customerData.getString("gender_type"));
                customerVector.add(customerData.getString("registered_date"));

                tableModel.addRow(customerVector);

            }

        } catch (Exception e) {
            Log.log.warning(e.toString());
        }

    }

    private void clear() {
        customerNicOrNameTextField.setText("");
        loadCustomer("");

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        roundedPanel21 = new Component.RoundedPanel();
        jLabel41 = new javax.swing.JLabel();
        customerNicOrNameTextField = new Component.RoundedTextField();
        jLabel42 = new javax.swing.JLabel();
        clearButton = new javax.swing.JButton();
        printButton = new javax.swing.JButton();
        addNewCustomerButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        customerTable = new javax.swing.JTable();
        logInLabel = new javax.swing.JLabel();

        roundedPanel21.setBackground(new java.awt.Color(24, 24, 24));

        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(153, 153, 153));
        jLabel41.setText("CUSTOMER NAME OR NIC NUMBER");

        customerNicOrNameTextField.setForeground(new java.awt.Color(255, 255, 255));
        customerNicOrNameTextField.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        customerNicOrNameTextField.setPreferredSize(new java.awt.Dimension(91, 30));
        customerNicOrNameTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                customerNicOrNameTextFieldKeyReleased(evt);
            }
        });

        jLabel42.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel42.setText("ALL CUSTOMER'S PERSONAL DETAILS");

        clearButton.setBackground(new java.awt.Color(102, 102, 102));
        clearButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/Clear.png"))); // NOI18N
        clearButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clearButton.setPreferredSize(new java.awt.Dimension(27, 27));
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        printButton.setBackground(new java.awt.Color(51, 51, 51));
        printButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        printButton.setForeground(new java.awt.Color(255, 255, 255));
        printButton.setText(" Print Details");
        printButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        printButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printButtonActionPerformed(evt);
            }
        });

        addNewCustomerButton.setBackground(java.awt.SystemColor.textHighlight);
        addNewCustomerButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        addNewCustomerButton.setForeground(new java.awt.Color(255, 255, 255));
        addNewCustomerButton.setText("Register Custormer");
        addNewCustomerButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addNewCustomerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewCustomerButtonActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(153, 153, 153));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/info.png"))); // NOI18N
        jLabel5.setText(" Double click the table row to update the customer details.");

        customerTable.setBackground(new java.awt.Color(29, 29, 29));
        customerTable.setForeground(new java.awt.Color(255, 255, 255));
        customerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NIC Number", "Name", "Birthday", "Mobile Number", "Email Address", "Nationality", "Gender", "Registered Date"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
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

        logInLabel.setBackground(new java.awt.Color(255, 255, 255));
        logInLabel.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        logInLabel.setForeground(new java.awt.Color(204, 204, 204));
        logInLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logInLabel.setText("CUSTOMER MANAGEMENT");

        javax.swing.GroupLayout roundedPanel21Layout = new javax.swing.GroupLayout(roundedPanel21);
        roundedPanel21.setLayout(roundedPanel21Layout);
        roundedPanel21Layout.setHorizontalGroup(
            roundedPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel21Layout.createSequentialGroup()
                .addGroup(roundedPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(roundedPanel21Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(addNewCustomerButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(printButton))
                    .addGroup(roundedPanel21Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(roundedPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1072, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, roundedPanel21Layout.createSequentialGroup()
                                .addComponent(jLabel42)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5))
                            .addGroup(roundedPanel21Layout.createSequentialGroup()
                                .addGap(95, 95, 95)
                                .addComponent(logInLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(179, 179, 179)
                                .addComponent(customerNicOrNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, roundedPanel21Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel41)))
                .addGap(16, 16, 16))
        );
        roundedPanel21Layout.setVerticalGroup(
            roundedPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel21Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel41)
                .addGap(6, 6, 6)
                .addGroup(roundedPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(logInLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(roundedPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(customerNicOrNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(roundedPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(roundedPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(printButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addNewCustomerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundedPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundedPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addNewCustomerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNewCustomerButtonActionPerformed

        CustomerRegistration customerRegistration = new CustomerRegistration(mainPanel, true, this);
        customerRegistration.setLocationRelativeTo(mainPanel);
        customerRegistration.getCustomerUpdateButton().setVisible(false);
        customerRegistration.setVisible(true);

    }//GEN-LAST:event_addNewCustomerButtonActionPerformed

    private void customerNicOrNameTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_customerNicOrNameTextFieldKeyReleased
        String nameOrNIC = customerNicOrNameTextField.getText();
        loadCustomer(nameOrNIC);

    }//GEN-LAST:event_customerNicOrNameTextFieldKeyReleased

    private void customerTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_customerTableMouseClicked

        if (evt.getClickCount() == 2) {

            int selectedRow = customerTable.getSelectedRow();

            String nic = String.valueOf(customerTable.getValueAt(selectedRow, 0));

            CustomerRegistration customerRegistration = new CustomerRegistration(mainPanel, true, this);

            customerRegistration.setTitle("Customer Update");
            customerRegistration.getCustomerNicTextField().setText(nic);
            customerRegistration.getCustomerNicTextField().setEnabled(false);
            customerRegistration.getTitleLabel().setText("Update Customer");
            customerRegistration.getCustomerRegisterButton().setVisible(false);
            customerRegistration.getClearButton().setVisible(false);
            customerRegistration.getBirthdayDateChooser().setEnabled(false);
            customerRegistration.getMaleRadioButton().setEnabled(false);
            customerRegistration.getFemaleRadioButton().setEnabled(false);
            customerRegistration.getCustomerFirstNameTextField().grabFocus();

            customerRegistration.updateCustomerDetailsLoad();
            customerRegistration.setLocationRelativeTo(mainPanel);
            customerRegistration.setVisible(true);

        }

    }//GEN-LAST:event_customerTableMouseClicked

    private void printButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printButtonActionPerformed

        try {

            HashMap<String, Object> parameters = new HashMap<>();
            String filePath = "src//Reports/CusomerReports.jasper";

            JRDataSource dataSource = new JRTableModelDataSource(customerTable.getModel());

            LocalDateTime currentDateTime = LocalDateTime.now();
            String datetime = DateTimeFormatter.ofPattern("yyyy MMM dd - hh:mm:ss a").format(currentDateTime);

            ResultSet customerCount = MySQL.execute("SELECT COUNT(*) AS customerCount FROM `users` WHERE `user_type_user_type_id`='1'");

            parameters.put("DateTime", datetime);

            if (customerCount.next()) {
                parameters.put("CustomerCount", customerCount.getString("customerCount"));
            }

            JasperPrint report = JasperFillManager.fillReport(filePath, parameters, dataSource);
            JasperViewer.viewReport(report, false);

        } catch (Exception e) {
            Log.log.warning(e.toString());
        }


    }//GEN-LAST:event_printButtonActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        clear();
    }//GEN-LAST:event_clearButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addNewCustomerButton;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton clearButton;
    private Component.RoundedTextField customerNicOrNameTextField;
    private javax.swing.JTable customerTable;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel logInLabel;
    private javax.swing.JButton printButton;
    private Component.RoundedPanel roundedPanel21;
    // End of variables declaration//GEN-END:variables
}
