package gui.dialog;

import Component.RoundedTextField;
import com.toedter.calendar.JDateChooser;
import connection.MySQL;
import gui.CustomerSection;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import javax.swing.ButtonModel;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import log.Log;
import raven.toast.Notifications;

public class CustomerRegistration extends javax.swing.JDialog {

    private final CustomerSection customerSection;

    HashMap<String, String> nationalityHashMap;

    /**
     * @param parent
     * @param modal
     * @param customerpanel
     */
    public CustomerRegistration(java.awt.Frame parent, boolean modal, CustomerSection customerpanel) {
        super(parent, modal);
        initComponents();
        nationalityHashMap = new HashMap<>();
        customerNicTextField.setFocusable(true);
        this.customerSection = customerpanel;
        loadNationality();
        Notifications.getInstance().setJFrame((JFrame) parent);
    }

    private void clear() {

        customerNicTextField.setText("");
        customerFirstNameTextField.setText("");
        customerLastNameTextField.setText("");
        customerEmailTextField.setText("");
        customerMobileTextField.setText("");
        birthdayDateChooser.setDate(null);
        nationalityComboBox.setSelectedIndex(0);
        genderButtonGroup.clearSelection();

    }

    private void loadNationality() {

        try {

            ResultSet nationalityData = MySQL.execute("SELECT * FROM `nationality`");

            Vector<String> nationality = new Vector<>();
            nationality.add("Select Nationality");

            while (nationalityData.next()) {

                nationality.add(nationalityData.getString("nationality_name"));
                nationalityHashMap.put(nationalityData.getString("nationality_name"), nationalityData.getString("nationality_id"));

            }

            nationalityComboBox.setModel(new DefaultComboBoxModel<>(nationality));

        } catch (Exception e) {
            Log.log.warning(e.toString());
        }

    }

    public RoundedTextField getCustomerNicTextField() {
        return customerNicTextField;
    }

    public JLabel getTitleLabel() {
        return titleLabel;
    }

    public JButton getCustomerRegisterButton() {
        return customerRegisterButton;
    }

    public JButton getClearButton() {
        return clearButton;
    }

    public JButton getCustomerUpdateButton() {
        return customerUpdateButton;
    }

    public JDateChooser getBirthdayDateChooser() {
        return birthdayDateChooser;
    }

    public JRadioButton getMaleRadioButton() {
        return maleRadioButton;
    }

    public JRadioButton getFemaleRadioButton() {
        return femaleRadioButton;
    }

    public RoundedTextField getCustomerFirstNameTextField() {
        return customerFirstNameTextField;
    }

    public void updateCustomerDetailsLoad() {

        String nic = customerNicTextField.getText();

        try {

            ResultSet customerData = MySQL.execute("SELECT * FROM `users` "
                    + "INNER JOIN `gender` ON `users`.`gender_gender_id`=`gender`.`gender_id` WHERE `NIC`='" + nic + "' AND `user_type_user_type_id`='1'");

            if (customerData.next()) {

                customerFirstNameTextField.setText(customerData.getString("first_name"));
                customerLastNameTextField.setText(customerData.getString("last_name"));
                birthdayDateChooser.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(customerData.getString("birthday")));
                customerMobileTextField.setText(customerData.getString("mobile"));
                customerEmailTextField.setText(customerData.getString("email"));
                nationalityComboBox.setSelectedIndex(Integer.parseInt(customerData.getString("nationality_nationality_id")));

                if (customerData.getString("gender_type").equals("Male")) {
                    maleRadioButton.setSelected(true);
                }

                if (customerData.getString("gender_type").equals("Female")) {
                    femaleRadioButton.setSelected(true);
                }

            } else {
                JOptionPane.showMessageDialog(this, "Customer Not Found", "Oops !", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            Log.log.warning(e.toString());
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        genderButtonGroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        customerNicTextField = new Component.RoundedTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        customerFirstNameTextField = new Component.RoundedTextField();
        jLabel4 = new javax.swing.JLabel();
        customerLastNameTextField = new Component.RoundedTextField();
        jLabel5 = new javax.swing.JLabel();
        birthdayDateChooser = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        customerMobileTextField = new Component.RoundedTextField();
        jLabel7 = new javax.swing.JLabel();
        maleRadioButton = new javax.swing.JRadioButton();
        femaleRadioButton = new javax.swing.JRadioButton();
        jLabel8 = new javax.swing.JLabel();
        customerEmailTextField = new Component.RoundedTextField();
        jLabel9 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        customerRegisterButton = new javax.swing.JButton();
        customerUpdateButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();
        nationalityComboBox = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Customer Registration");

        titleLabel.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("Customer Registration");

        customerNicTextField.setForeground(new java.awt.Color(255, 255, 255));
        customerNicTextField.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        customerNicTextField.setPreferredSize(new java.awt.Dimension(250, 30));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 153, 153));
        jLabel2.setText(" NIC Number");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(153, 153, 153));
        jLabel3.setText(" First Name");

        customerFirstNameTextField.setForeground(new java.awt.Color(255, 255, 255));
        customerFirstNameTextField.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        customerFirstNameTextField.setPreferredSize(new java.awt.Dimension(250, 30));
        customerFirstNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customerFirstNameTextFieldActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 153, 153));
        jLabel4.setText(" Last Name");

        customerLastNameTextField.setForeground(new java.awt.Color(255, 255, 255));
        customerLastNameTextField.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        customerLastNameTextField.setPreferredSize(new java.awt.Dimension(250, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(153, 153, 153));
        jLabel5.setText("Date of Birth");

        birthdayDateChooser.setPreferredSize(new java.awt.Dimension(150, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(153, 153, 153));
        jLabel6.setText(" Mobile Number");

        customerMobileTextField.setForeground(new java.awt.Color(255, 255, 255));
        customerMobileTextField.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        customerMobileTextField.setPreferredSize(new java.awt.Dimension(250, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(153, 153, 153));
        jLabel7.setText(" Gender");

        genderButtonGroup.add(maleRadioButton);
        maleRadioButton.setText("Male");
        maleRadioButton.setActionCommand("1");

        genderButtonGroup.add(femaleRadioButton);
        femaleRadioButton.setText("Female");
        femaleRadioButton.setActionCommand("2");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(153, 153, 153));
        jLabel8.setText(" Email Address");

        customerEmailTextField.setForeground(new java.awt.Color(255, 255, 255));
        customerEmailTextField.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        customerEmailTextField.setPreferredSize(new java.awt.Dimension(250, 30));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(153, 153, 153));
        jLabel9.setText(" Nationality");

        customerRegisterButton.setBackground(java.awt.SystemColor.textHighlight);
        customerRegisterButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        customerRegisterButton.setForeground(new java.awt.Color(255, 255, 255));
        customerRegisterButton.setText(" Register");
        customerRegisterButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        customerRegisterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customerRegisterButtonActionPerformed(evt);
            }
        });

        customerUpdateButton.setBackground(new java.awt.Color(51, 51, 51));
        customerUpdateButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        customerUpdateButton.setForeground(java.awt.SystemColor.textHighlight);
        customerUpdateButton.setText(" Update");
        customerUpdateButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        customerUpdateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customerUpdateButtonActionPerformed(evt);
            }
        });

        clearButton.setBackground(new java.awt.Color(102, 102, 102));
        clearButton.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        clearButton.setForeground(new java.awt.Color(255, 255, 255));
        clearButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/Clear.png"))); // NOI18N
        clearButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        nationalityComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2)
                            .addComponent(customerNicTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3)
                            .addComponent(customerFirstNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(birthdayDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel8)
                            .addComponent(customerEmailTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6)
                            .addComponent(jLabel9)
                            .addComponent(jLabel7)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(maleRadioButton)
                                .addGap(18, 18, 18)
                                .addComponent(femaleRadioButton))
                            .addComponent(jLabel4)
                            .addComponent(customerMobileTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(customerLastNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(nationalityComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18))
                    .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(customerRegisterButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(customerUpdateButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(customerNicTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(customerMobileTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(customerFirstNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customerLastNameTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(customerEmailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nationalityComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(birthdayDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(maleRadioButton)
                            .addComponent(femaleRadioButton))))
                .addGap(42, 42, 42)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(customerRegisterButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customerUpdateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 9, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void customerRegisterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customerRegisterButtonActionPerformed

        String nic = customerNicTextField.getText();
        String firstName = customerFirstNameTextField.getText();
        String lastName = customerLastNameTextField.getText();
        Date birthdayDate = birthdayDateChooser.getDate();
        String mobile = customerMobileTextField.getText();
        String email = customerEmailTextField.getText();
        String selectedNationality = String.valueOf(nationalityComboBox.getSelectedItem());
        ButtonModel genderSelection = genderButtonGroup.getSelection();

        Date today = new Date();

        if (nic.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Enter a NIC Number");
        } else if (!nic.matches("^[0-9]+$") || nic.length() > 15) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Invalid NIC Number");
        } else if (firstName.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Enter a First Name");
        } else if (firstName.length() > 50) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "First name cannot exceed 50 characters");
        } else if (lastName.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Enter a Last Name");
        } else if (lastName.length() > 50) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Last name cannot exceed 50 characters");
        } else if (birthdayDate == null) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Please Choose birthday");
        } else if (birthdayDate.after(today)) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Birthday cannot be in the future");
        } else if (mobile.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Enter a Mobile Number");
        } else if (!mobile.matches("^07[0,1,2,4,5,6,7,8]{1}[0-9]{7}$")) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Invalid Mobile Number");
        } else if (email.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Enter a Email Address");
        } else if (!email.matches("^[A-Za-z0-9._-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Invalid Email Address");
        } else if (email.length() > 100) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Email cannot exceed 100 characters");
        } else if ("Select Nationality".equals(selectedNationality)) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Select Nationality");
        } else if (genderSelection == null) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Select Gender");
        } else {

            String birthday = new SimpleDateFormat("yyyy-MM-dd").format(birthdayDate);
            String gender = genderSelection.getActionCommand();
            String nationality = nationalityHashMap.get(selectedNationality);
            String registerDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(today);

            try {

                ResultSet customerResult = MySQL.execute("SELECT `NIC`,`mobile`,`email` FROM `users` WHERE `NIC`='" + nic + "' OR `mobile`='" + mobile + "' OR `email`='" + email + "'");

                if (customerResult.next()) {

                    String message = "This ";

                    if (nic.equals(customerResult.getString("NIC"))) {
                        message += "NIC number ";
                    }
                    if (mobile.equals(customerResult.getString("mobile"))) {
                        message += "Mobile number ";
                    }
                    if (email.equals(customerResult.getString("email"))) {
                        message += "Email address ";
                    }

                    JOptionPane.showMessageDialog(this, message + "already exists", "Oops !", JOptionPane.ERROR_MESSAGE);

                } else {

                    MySQL.execute("INSERT INTO `users` (`NIC`,`first_name`,`last_name`,`birthday`,`gender_gender_id`,`email`,`mobile`,`registered_date`,`nationality_nationality_id`,`user_type_user_type_id`,`user_status_user_status_id`) "
                            + "VALUES ('" + nic + "','" + firstName + "','" + lastName + "','" + birthday + "','" + gender + "','" + email + "','" + mobile + "','" + registerDate + "','" + nationality + "','1','1')");

                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.BOTTOM_CENTER, "Customer registration is successful");
                    this.customerSection.loadCustomer("");
                    this.dispose();
                }

            } catch (Exception e) {
                Log.log.warning(e.toString());
            }

        }

    }//GEN-LAST:event_customerRegisterButtonActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        clear();
    }//GEN-LAST:event_clearButtonActionPerformed

    private void customerUpdateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customerUpdateButtonActionPerformed

        String nic = customerNicTextField.getText();
        String firstName = customerFirstNameTextField.getText();
        String lastName = customerLastNameTextField.getText();
        String mobile = customerMobileTextField.getText();
        String email = customerEmailTextField.getText();
        String selectedNationality = String.valueOf(nationalityComboBox.getSelectedItem());

        if (firstName.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Enter a First Name");
        } else if (firstName.length() > 50) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "First name cannot exceed 50 characters");
        } else if (lastName.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Enter a Last Name");
        } else if (lastName.length() > 50) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Last name cannot exceed 50 characters");
        } else if (mobile.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Enter a Mobile Number");
        } else if (!mobile.matches("^07[0,1,2,4,5,6,7,8]{1}[0-9]{7}$")) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Invalid Mobile Number");
        } else if (email.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Enter a Email Address");
        } else if (!email.matches("^[A-Za-z0-9._-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Invalid Email Address");
        } else if (email.length() > 100) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Email cannot exceed 100 characters");
        } else if ("Select Nationality".equals(selectedNationality)) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Select Nationality");
        } else {

            String nationality = nationalityHashMap.get(selectedNationality);

            try {

                MySQL.execute("UPDATE `users` SET `first_name`='" + firstName + "', `last_name`='" + lastName + "', `email`='" + email + "', `mobile`='" + mobile + "', `nationality_nationality_id`='" + nationality + "' "
                        + "WHERE `NIC`='" + nic + "'");
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.BOTTOM_CENTER, firstName + "'s information is updated");
                this.customerSection.loadCustomer("");
                this.dispose();

            } catch (Exception e) {
                Log.log.warning(e.toString());
            }

        }
    }//GEN-LAST:event_customerUpdateButtonActionPerformed

    private void customerFirstNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customerFirstNameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_customerFirstNameTextFieldActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser birthdayDateChooser;
    private javax.swing.JButton clearButton;
    private Component.RoundedTextField customerEmailTextField;
    private Component.RoundedTextField customerFirstNameTextField;
    private Component.RoundedTextField customerLastNameTextField;
    private Component.RoundedTextField customerMobileTextField;
    private Component.RoundedTextField customerNicTextField;
    private javax.swing.JButton customerRegisterButton;
    private javax.swing.JButton customerUpdateButton;
    private javax.swing.JRadioButton femaleRadioButton;
    private javax.swing.ButtonGroup genderButtonGroup;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JRadioButton maleRadioButton;
    private javax.swing.JComboBox<String> nationalityComboBox;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}
