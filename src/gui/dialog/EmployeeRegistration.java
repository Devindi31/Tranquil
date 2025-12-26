package gui.dialog;

import Component.RoundedTextField;
import com.toedter.calendar.JDateChooser;
import connection.MySQL;
import gui.EmployeeSection;
import java.util.Date;
import java.util.HashMap;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import raven.toast.Notifications;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import log.Log;

public class EmployeeRegistration extends javax.swing.JDialog {

    private final EmployeeSection employeeSection;

    HashMap<String, String> nationalityHashMap;

    /**
     * @param parent
     * @param modal
     * @param employeeSectionPanel
     */
    public EmployeeRegistration(java.awt.Frame parent, boolean modal, EmployeeSection employeeSectionPanel) {
        super(parent, modal);
        initComponents();
        nationalityHashMap = new HashMap<>();
        this.employeeSection = employeeSectionPanel;
        Notifications.getInstance().setJFrame((JFrame) parent);
        loadNationaliy();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        genderButtonGroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        employeeNicTextField = new Component.RoundedTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        employeeFirstNameTextField = new Component.RoundedTextField();
        jLabel4 = new javax.swing.JLabel();
        employeeLastNameTextField = new Component.RoundedTextField();
        jLabel5 = new javax.swing.JLabel();
        employeeBirthdayChooser = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        employeeMobileTextField = new Component.RoundedTextField();
        jLabel7 = new javax.swing.JLabel();
        maleRadioButton = new javax.swing.JRadioButton();
        femaleRadioButton = new javax.swing.JRadioButton();
        jLabel8 = new javax.swing.JLabel();
        employeeEmailTextField = new Component.RoundedTextField();
        jLabel9 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        regiserEmployeeButton = new javax.swing.JButton();
        updateEmployeeButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        employeeNewPasswordField = new Component.RoundedPasswordField();
        jLabel11 = new javax.swing.JLabel();
        employeeComfirmPasswordField = new Component.RoundedPasswordField();
        showEmployeeNewPasswordButton = new javax.swing.JButton();
        showEmployeeComfirmPasswordButton = new javax.swing.JButton();
        employeeNationalityComboBox = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Employee Registration");

        titleLabel.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("Employee Registration");

        employeeNicTextField.setForeground(new java.awt.Color(255, 255, 255));
        employeeNicTextField.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        employeeNicTextField.setPreferredSize(new java.awt.Dimension(250, 30));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 153, 153));
        jLabel2.setText(" Employee NIC Number");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(153, 153, 153));
        jLabel3.setText(" First Name");

        employeeFirstNameTextField.setForeground(new java.awt.Color(255, 255, 255));
        employeeFirstNameTextField.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        employeeFirstNameTextField.setPreferredSize(new java.awt.Dimension(250, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 153, 153));
        jLabel4.setText(" Last Name");

        employeeLastNameTextField.setForeground(new java.awt.Color(255, 255, 255));
        employeeLastNameTextField.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        employeeLastNameTextField.setPreferredSize(new java.awt.Dimension(250, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(153, 153, 153));
        jLabel5.setText("  Birthday");

        employeeBirthdayChooser.setPreferredSize(new java.awt.Dimension(150, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(153, 153, 153));
        jLabel6.setText(" Mobile Number");

        employeeMobileTextField.setForeground(new java.awt.Color(255, 255, 255));
        employeeMobileTextField.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        employeeMobileTextField.setPreferredSize(new java.awt.Dimension(250, 30));

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

        employeeEmailTextField.setForeground(new java.awt.Color(255, 255, 255));
        employeeEmailTextField.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        employeeEmailTextField.setPreferredSize(new java.awt.Dimension(250, 30));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(153, 153, 153));
        jLabel9.setText(" Nationality");

        regiserEmployeeButton.setBackground(java.awt.SystemColor.textHighlight);
        regiserEmployeeButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        regiserEmployeeButton.setForeground(new java.awt.Color(255, 255, 255));
        regiserEmployeeButton.setText(" Register");
        regiserEmployeeButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        regiserEmployeeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regiserEmployeeButtonActionPerformed(evt);
            }
        });

        updateEmployeeButton.setBackground(new java.awt.Color(51, 51, 51));
        updateEmployeeButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        updateEmployeeButton.setForeground(java.awt.SystemColor.textHighlight);
        updateEmployeeButton.setText(" Update");
        updateEmployeeButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        updateEmployeeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateEmployeeButtonActionPerformed(evt);
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

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(153, 153, 153));
        jLabel10.setText(" New Password");

        employeeNewPasswordField.setPreferredSize(new java.awt.Dimension(64, 30));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(153, 153, 153));
        jLabel11.setText(" Comfirm Password");

        employeeComfirmPasswordField.setPreferredSize(new java.awt.Dimension(64, 30));

        showEmployeeNewPasswordButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/Show.png"))); // NOI18N
        showEmployeeNewPasswordButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        showEmployeeNewPasswordButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        showEmployeeNewPasswordButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                showEmployeeNewPasswordButtonMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                showEmployeeNewPasswordButtonMouseReleased(evt);
            }
        });

        showEmployeeComfirmPasswordButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/Show.png"))); // NOI18N
        showEmployeeComfirmPasswordButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        showEmployeeComfirmPasswordButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        showEmployeeComfirmPasswordButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                showEmployeeComfirmPasswordButtonMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                showEmployeeComfirmPasswordButtonMouseReleased(evt);
            }
        });

        employeeNationalityComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

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
                            .addComponent(employeeNicTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3)
                            .addComponent(employeeFirstNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(employeeBirthdayChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel8)
                            .addComponent(employeeEmailTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6)
                            .addComponent(employeeMobileTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel7)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(maleRadioButton)
                                .addGap(18, 18, 18)
                                .addComponent(femaleRadioButton))
                            .addComponent(employeeNationalityComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4)
                            .addComponent(employeeLastNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(regiserEmployeeButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateEmployeeButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(employeeNewPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(showEmployeeNewPasswordButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(52, 52, 52)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(employeeComfirmPasswordField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(showEmployeeComfirmPasswordButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(employeeNicTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(employeeMobileTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(employeeFirstNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(employeeLastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addComponent(employeeNationalityComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(employeeEmailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(jLabel9)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(employeeBirthdayChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(maleRadioButton)
                            .addComponent(femaleRadioButton))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(employeeNewPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(showEmployeeNewPasswordButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(showEmployeeComfirmPasswordButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(employeeComfirmPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 27, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(regiserEmployeeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateEmployeeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void clear() {

        employeeNicTextField.setText("");
        employeeFirstNameTextField.setText("");
        employeeLastNameTextField.setText("");
        employeeMobileTextField.setText("");
        employeeEmailTextField.setText("");
        employeeNewPasswordField.setText("");
        employeeComfirmPasswordField.setText("");
        employeeBirthdayChooser.setDate(null);
        employeeNationalityComboBox.setSelectedIndex(0);
        genderButtonGroup.clearSelection();

    }

    public JButton getRegiserEmployeeButton() {
        return regiserEmployeeButton;
    }

    public JButton getUpdateEmployeeButton() {
        return updateEmployeeButton;
    }

    public JButton getClearButton() {
        return clearButton;
    }

    public RoundedTextField getEmployeeNicTextField() {
        return employeeNicTextField;
    }

    public RoundedTextField getEmployeeFirstNameTextField() {
        return employeeFirstNameTextField;
    }

    public JDateChooser getEmployeeBirthdayChooser() {
        return employeeBirthdayChooser;
    }

    public JRadioButton getMaleRadioButton() {
        return maleRadioButton;
    }

    public JRadioButton getFemaleRadioButton() {
        return femaleRadioButton;
    }

    public JLabel getTitleLabel() {
        return titleLabel;
    }

    private void loadNationaliy() {

        try {

            ResultSet nationalityData = MySQL.execute("SELECT * FROM `nationality`");

            Vector<String> nationality = new Vector<>();
            nationality.add("Select Nationality");

            while (nationalityData.next()) {

                nationality.add(nationalityData.getString("nationality_name"));
                nationalityHashMap.put(nationalityData.getString("nationality_name"), nationalityData.getString("nationality_id"));

            }

            employeeNationalityComboBox.setModel(new DefaultComboBoxModel<>(nationality));

        } catch (Exception e) {
             Log.log.warning(e.toString());
        }

    }

    private void regiserEmployeeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regiserEmployeeButtonActionPerformed

        String employeeNic = employeeNicTextField.getText();
        String firstName = employeeFirstNameTextField.getText();
        String lastName = employeeLastNameTextField.getText();
        Date birthdayDate = employeeBirthdayChooser.getDate();
        String mobile = employeeMobileTextField.getText();
        String email = employeeEmailTextField.getText();
        String selectedNationality = String.valueOf(employeeNationalityComboBox.getSelectedItem());
        ButtonModel genderSelection = genderButtonGroup.getSelection();
        String newPassword = String.valueOf(employeeNewPasswordField.getPassword());
        String confirmPassword = String.valueOf(employeeComfirmPasswordField.getPassword());

        Date today = new Date();

        if (employeeNic.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Enter a NIC Number");
        } else if (!employeeNic.matches("^[0-9]+$") || employeeNic.length() > 15) {
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
        } else if (newPassword.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Create new password");
        } else if (!newPassword.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Your password must contain at least eight characters, at least one capital letter, one lowercase letter, one number and one special character");
        } else if (confirmPassword.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Please Comfirm your password");
        } else if (!confirmPassword.equals(newPassword)) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Your password and Confirm password do not match");
        } else {

            String birthday = new SimpleDateFormat("yyyy-MM-dd").format(birthdayDate);
            String gender = genderSelection.getActionCommand();
            String registerDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(today);
            String nationality = nationalityHashMap.get(selectedNationality);

            try {

                ResultSet employeeResult = MySQL.execute("SELECT `NIC`,`mobile`,`email` FROM `users` WHERE `NIC`='" + employeeNic + "' OR `mobile`='" + mobile + "' OR `email`='" + email + "'");

                if (employeeResult.next()) {

                    String message = "This ";

                    if (employeeNic.equals(employeeResult.getString("NIC"))) {
                        message += "NIC number ";
                    }
                    if (mobile.equals(employeeResult.getString("mobile"))) {
                        message += "Mobile number ";
                    }
                    if (email.equals(employeeResult.getString("email"))) {
                        message += "Email address ";
                    }

                    JOptionPane.showMessageDialog(this, message + "already exists", "Oops !", JOptionPane.ERROR_MESSAGE);

                } else {

                    MySQL.execute("INSERT INTO `users` (`NIC`,`first_name`,`last_name`,`birthday`,`gender_gender_id`,`email`,`mobile`,`registered_date`,`nationality_nationality_id`,`user_type_user_type_id`,`user_status_user_status_id`) "
                            + "VALUES ('" + employeeNic + "','" + firstName + "','" + lastName + "','" + birthday + "','" + gender + "','" + email + "','" + mobile + "','" + registerDate + "','" + nationality + "','3','1')");

                    MySQL.execute("INSERT INTO `login_details` (`users_NIC`,`password`) VALUES ('" + employeeNic + "','" + confirmPassword + "')");
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.BOTTOM_CENTER, "Employee registration is successful");
                    this.employeeSection.loadEmployees("");
                    this.dispose();
                }

            } catch (Exception e) {
                Log.log.warning(e.toString());
            }

        }

    }//GEN-LAST:event_regiserEmployeeButtonActionPerformed

    public void fillUpdateDetails() {

        String employeeNic = employeeNicTextField.getText();

        try {

            ResultSet updateResult = MySQL.execute("SELECT * FROM `users` "
                    + "INNER JOIN `gender` ON `users`.`gender_gender_id`=`gender`.`gender_id` "
                    + "INNER JOIN `login_details` ON `login_details`.`users_NIC`=`users`.`NIC` "
                    + "WHERE `NIC`='" + employeeNic + "' AND `user_type_user_type_id`='3'");

            if (updateResult.next()) {

                employeeFirstNameTextField.setText(updateResult.getString("first_name"));
                employeeLastNameTextField.setText(updateResult.getString("last_name"));
                employeeBirthdayChooser.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(updateResult.getString("birthday")));
                employeeMobileTextField.setText(updateResult.getString("mobile"));
                employeeEmailTextField.setText(updateResult.getString("email"));
                employeeNationalityComboBox.setSelectedIndex(Integer.parseInt(updateResult.getString("nationality_nationality_id")));

                if (updateResult.getString("gender_type").equals("Male")) {
                    maleRadioButton.setSelected(true);
                }

                if (updateResult.getString("gender_type").equals("Female")) {
                    femaleRadioButton.setSelected(true);
                }

                employeeNewPasswordField.setText(updateResult.getString("password"));
                employeeComfirmPasswordField.setText(updateResult.getString("password"));

            }

        } catch (Exception e) {
            Log.log.warning(e.toString());
        } 

    }

    private void showEmployeeNewPasswordButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_showEmployeeNewPasswordButtonMousePressed
        employeeNewPasswordField.setEchoChar('\u0000');
    }//GEN-LAST:event_showEmployeeNewPasswordButtonMousePressed

    private void showEmployeeNewPasswordButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_showEmployeeNewPasswordButtonMouseReleased
        employeeNewPasswordField.setEchoChar('\u2022');
    }//GEN-LAST:event_showEmployeeNewPasswordButtonMouseReleased

    private void showEmployeeComfirmPasswordButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_showEmployeeComfirmPasswordButtonMousePressed
        employeeComfirmPasswordField.setEchoChar('\u0000');
    }//GEN-LAST:event_showEmployeeComfirmPasswordButtonMousePressed

    private void showEmployeeComfirmPasswordButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_showEmployeeComfirmPasswordButtonMouseReleased
        employeeComfirmPasswordField.setEchoChar('\u2022');
    }//GEN-LAST:event_showEmployeeComfirmPasswordButtonMouseReleased

    private void updateEmployeeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateEmployeeButtonActionPerformed

        String employeeNic = employeeNicTextField.getText();
        String firstName = employeeFirstNameTextField.getText();
        String lastName = employeeLastNameTextField.getText();
        String mobile = employeeMobileTextField.getText();
        String email = employeeEmailTextField.getText();
        String selectedNationality = String.valueOf(employeeNationalityComboBox.getSelectedItem());
        String newPassword = String.valueOf(employeeNewPasswordField.getPassword());
        String confirmPassword = String.valueOf(employeeComfirmPasswordField.getPassword());

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
        } else if (newPassword.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Create new password");
        } else if (!newPassword.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Your password must contain at least eight characters, at least one capital letter, one lowercase letter, one number and one special character");
        } else if (confirmPassword.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Please Comfirm your password");
        } else if (!confirmPassword.equals(newPassword)) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Your password and Confirm password do not match");
        } else {

            String nationality = nationalityHashMap.get(selectedNationality);

            try {

                MySQL.execute("UPDATE `users` SET `first_name`='" + firstName + "', `last_name`='" + lastName + "', `email`='" + email + "', `mobile`='" + mobile + "', `nationality_nationality_id`='" + nationality + "' "
                        + "WHERE `NIC`='" + employeeNic + "'");

                MySQL.execute("UPDATE `login_details` SET `password`='" + confirmPassword + "' WHERE `users_NIC`='" + employeeNic + "'");

                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.BOTTOM_CENTER, firstName + "'s information is updated");
                this.employeeSection.loadEmployees("");
                this.dispose();

            } catch (Exception e) {
                Log.log.warning(e.toString());
            }

        }

    }//GEN-LAST:event_updateEmployeeButtonActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        clear();
    }//GEN-LAST:event_clearButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearButton;
    private com.toedter.calendar.JDateChooser employeeBirthdayChooser;
    private Component.RoundedPasswordField employeeComfirmPasswordField;
    private Component.RoundedTextField employeeEmailTextField;
    private Component.RoundedTextField employeeFirstNameTextField;
    private Component.RoundedTextField employeeLastNameTextField;
    private Component.RoundedTextField employeeMobileTextField;
    private javax.swing.JComboBox<String> employeeNationalityComboBox;
    private Component.RoundedPasswordField employeeNewPasswordField;
    private Component.RoundedTextField employeeNicTextField;
    private javax.swing.JRadioButton femaleRadioButton;
    private javax.swing.ButtonGroup genderButtonGroup;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JButton regiserEmployeeButton;
    private javax.swing.JButton showEmployeeComfirmPasswordButton;
    private javax.swing.JButton showEmployeeNewPasswordButton;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JButton updateEmployeeButton;
    // End of variables declaration//GEN-END:variables
}
