package gui;

import connection.MySQL;
import gui.dialog.EmployeeRegistration;
import java.awt.Color;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import log.Log;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;
import raven.toast.Notifications;

public class EmployeeSection extends javax.swing.JPanel {

    private final MainPanel mainPanelSection;

    /**
     * @param mainPanel
     */
    public EmployeeSection(MainPanel mainPanel) {

        initComponents();

        this.setBackground(new Color(255, 255, 255, 0));
        this.mainPanelSection = mainPanel;
        loadEmployees("");
        checkArrangeAttendance();
        Notifications.getInstance().setJFrame(mainPanel);
        loadAttendanceDetails("");

    }

    /**
     * @param employeeNameOrNic
     */
    public void loadEmployees(String employeeNameOrNic) {

        try {

            ResultSet employeeData = MySQL.execute("SELECT * FROM `users` "
                    + "INNER JOIN `gender` ON `users`.`gender_gender_id`=`gender`.`gender_id` "
                    + "INNER JOIN `nationality` ON `users`.`nationality_nationality_id`=`nationality`.`nationality_id` "
                    + "INNER JOIN `login_details` ON `login_details`.`users_NIC`=`users`.`NIC` "
                    + "WHERE `user_type_user_type_id`='3' AND (`NIC` LIKE '%" + employeeNameOrNic + "%' OR `first_name` LIKE '%" + employeeNameOrNic + "%' OR `last_name` LIKE '%" + employeeNameOrNic + "%') ORDER BY `registered_date` DESC");

            DefaultTableModel tableModel = (DefaultTableModel) employeeTable.getModel();
            tableModel.setRowCount(0);

            while (employeeData.next()) {

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                Date inputBirthday = dateFormat.parse(employeeData.getString("birthday"));
                String birthday = dateFormat.format(inputBirthday);

                Vector<String> customerVector = new Vector<>();
                customerVector.add(employeeData.getString("NIC"));
                customerVector.add(employeeData.getString("first_name") + " " + employeeData.getString("last_name"));
                customerVector.add(birthday);
                customerVector.add(employeeData.getString("mobile"));
                customerVector.add(employeeData.getString("email"));
                customerVector.add(employeeData.getString("password"));
                customerVector.add(employeeData.getString("nationality_name"));
                customerVector.add(employeeData.getString("gender_type"));
                customerVector.add(employeeData.getString("registered_date"));

                tableModel.addRow(customerVector);

            }

            ResultSet countResult = MySQL.execute("SELECT COUNT(*) AS `total_employee_count` FROM `users` WHERE `user_type_user_type_id`='3'");

            if (countResult.next()) {
                totalEmployeesCountLabel.setText(String.valueOf(countResult.getString("total_employee_count")));
            }

        } catch (Exception e) {
            Log.log.warning(e.toString());
        }

    }

   // Employee Attendance Section
    private void clearArrangeSection() {

        NicTextField.setText("");
        attendanceTable.clearSelection();
        employeeNameLabel.setText("None");
        employeeNICLabel.setText("None");
        monthlyAttendCountLabel.setText("None");
        todayAttendCountLabel.setText("None");
        loadAttendanceDetails("");

    }

    private void checkArrangeAttendance() {

        try {

            LocalDate today = LocalDate.now();
            ResultSet arrangeResult = MySQL.execute("SELECT * FROM `employee_attendance` WHERE `date_time` LIKE '" + today + "%'");

            if (arrangeResult.next()) {

                arrangeAttendanceButton.setEnabled(false);

            } else {

                finalizedAttendanceButton.setEnabled(false);
                markAttendanceButton.setEnabled(false);
                clearAttendanceSectionButton.setEnabled(false);
                NicTextField.setEnabled(false);
                attendanceTable.setEnabled(false);

                JOptionPane.showMessageDialog(this, "Please arrange employee attendance first.", "Reminder", JOptionPane.INFORMATION_MESSAGE);

            }

        } catch (Exception e) {
            Log.log.warning(e.toString());
        }

    }

    private void loadAttendanceDetails(String employeeNIC) {

        try {

            ResultSet attendanceResults = MySQL.execute("SELECT * FROM `employee_attendance` "
                    + "INNER JOIN `users` ON `employee_attendance`.`employee_NIC` = `users`.`NIC` "
                    + "INNER JOIN `attendance_status` ON `employee_attendance`.`attendance_status_attendance_status_id`=`attendance_status`.`attendance_status_id`"
                    + "WHERE `employee_NIC` LIKE '" + employeeNIC + "%' ORDER BY `date_time` DESC");

            DefaultTableModel tableModel = (DefaultTableModel) attendanceTable.getModel();
            tableModel.setRowCount(0);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");

            while (attendanceResults.next()) {

                Date attendanceDateTime = format.parse(attendanceResults.getString("employee_attendance.date_time"));
                String dateTime = newFormat.format(attendanceDateTime);

                Vector<String> attendanceVector = new Vector<>();

                attendanceVector.add(attendanceResults.getString("employee_NIC"));
                attendanceVector.add(attendanceResults.getString("first_name") + " " + attendanceResults.getString("last_name"));
                attendanceVector.add(attendanceResults.getString("status"));
                attendanceVector.add(dateTime);

                tableModel.addRow(attendanceVector);
            }

            LocalDate today = LocalDate.now();
            ResultSet attendResult = MySQL.execute("SELECT COUNT(employee_attendance_id) AS attendCount FROM `employee_attendance` WHERE `date_time` LIKE '" + today + "%' AND `attendance_status_attendance_status_id`='2' ");

            if (attendResult.next()) {
                todayAttendCountLabel.setText(attendResult.getString("attendCount"));
            }

        } catch (Exception e) {
            Log.log.warning(e.toString());
        }

    }

    private void fillEmployeeDetails(String employeeNic) {

        try {

            LocalDateTime currentDateTime = LocalDateTime.now();
            String month = DateTimeFormatter.ofPattern("yyyy-MM").format(currentDateTime);

            ResultSet employeeDetails = MySQL.execute("SELECT `users`.`first_name`,`users`.`last_name`,`users`.`NIC` FROM `users` "
                    + "WHERE `NIC`='" + employeeNic + "'");

            if (employeeDetails.next()) {

                employeeNameLabel.setText(employeeDetails.getString("first_name") + " " + employeeDetails.getString("last_name"));
                employeeNICLabel.setText(employeeDetails.getString("NIC"));

                ResultSet attendCount = MySQL.execute("SELECT COUNT(employee_attendance_id) AS attendCount FROM `employee_attendance` "
                        + "WHERE `date_time` LIKE '" + month + "%' AND `attendance_status_attendance_status_id` = '2' AND `employee_NIC`='" + employeeNic + "'");

                if (attendCount.next()) {
                    monthlyAttendCountLabel.setText(attendCount.getString("attendCount"));
                }

            } else {

                employeeNameLabel.setText("None");
                employeeNICLabel.setText("None");
                monthlyAttendCountLabel.setText("None");
            }

        } catch (Exception e) {
            Log.log.warning(e.toString());
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roundedPanel1 = new Component.RoundedPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        employeeNameOrNicTextField = new Component.RoundedTextField();
        clearButton = new javax.swing.JButton();
        addNewEmployeeButton = new javax.swing.JButton();
        printButton = new javax.swing.JButton();
        jLabel42 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        employeeTable = new javax.swing.JTable();
        logInLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        arrangeAttendanceButton = new javax.swing.JButton();
        finalizedAttendanceButton = new javax.swing.JButton();
        jLabel43 = new javax.swing.JLabel();
        NicTextField = new Component.RoundedTextField();
        clearAttendanceSectionButton = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        attendanceTable = new javax.swing.JTable();
        markAttendanceButton = new javax.swing.JButton();
        jLabel44 = new javax.swing.JLabel();
        roundedPanel2 = new Component.RoundedPanel();
        jLabel45 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        employeeNameLabel = new javax.swing.JLabel();
        employeeNICLabel = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        monthlyAttendCountLabel = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        totalEmployeesCountLabel = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        todayAttendCountLabel = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        logInLabel1 = new javax.swing.JLabel();

        roundedPanel1.setBackground(new java.awt.Color(24, 24, 24));

        jPanel1.setBackground(new java.awt.Color(24, 24, 24));

        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(153, 153, 153));
        jLabel41.setText(" EMPLOYEE NIC");

        employeeNameOrNicTextField.setForeground(new java.awt.Color(255, 255, 255));
        employeeNameOrNicTextField.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        employeeNameOrNicTextField.setPreferredSize(new java.awt.Dimension(91, 30));
        employeeNameOrNicTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                employeeNameOrNicTextFieldKeyReleased(evt);
            }
        });

        clearButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/Clear.png"))); // NOI18N
        clearButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clearButton.setPreferredSize(new java.awt.Dimension(27, 27));
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        addNewEmployeeButton.setBackground(java.awt.SystemColor.textHighlight);
        addNewEmployeeButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        addNewEmployeeButton.setForeground(new java.awt.Color(255, 255, 255));
        addNewEmployeeButton.setText("Register Employee");
        addNewEmployeeButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addNewEmployeeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewEmployeeButtonActionPerformed(evt);
            }
        });

        printButton.setBackground(new java.awt.Color(51, 51, 51));
        printButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        printButton.setForeground(new java.awt.Color(255, 255, 255));
        printButton.setText("Print Details");
        printButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        printButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printButtonActionPerformed(evt);
            }
        });

        jLabel42.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel42.setText("EMPLOEE'S DETAILS");

        jLabel5.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(153, 153, 153));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/info.png"))); // NOI18N
        jLabel5.setText(" Double click the table row to update the employee details.");

        employeeTable.setBackground(new java.awt.Color(29, 29, 29));
        employeeTable.setForeground(new java.awt.Color(255, 255, 255));
        employeeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NIC Number", "Name", "Birthday", "Mobile Number", "Email Address", "Password", "Nationality", "Gender", "Registered Date"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        employeeTable.setInheritsPopupMenu(true);
        employeeTable.setRowHeight(23);
        employeeTable.setShowGrid(true);
        employeeTable.setSurrendersFocusOnKeystroke(true);
        employeeTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                employeeTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(employeeTable);

        logInLabel.setBackground(new java.awt.Color(255, 255, 255));
        logInLabel.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        logInLabel.setForeground(new java.awt.Color(204, 204, 204));
        logInLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logInLabel.setText("EMPLOYEES MANAGEMENT");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel42)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addComponent(logInLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(454, 454, 454)
                        .addComponent(jLabel41))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(employeeNameOrNicTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(addNewEmployeeButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(printButton)))))
                .addGap(16, 16, 16))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel41)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(employeeNameOrNicTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(logInLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel42))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addNewEmployeeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(printButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        jTabbedPane1.addTab("Employee Main Panel", jPanel1);

        jPanel2.setBackground(new java.awt.Color(24, 24, 24));

        arrangeAttendanceButton.setBackground(new java.awt.Color(51, 51, 51));
        arrangeAttendanceButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        arrangeAttendanceButton.setForeground(new java.awt.Color(255, 255, 255));
        arrangeAttendanceButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/Arrange.png"))); // NOI18N
        arrangeAttendanceButton.setText("Arrange Attendance");
        arrangeAttendanceButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        arrangeAttendanceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                arrangeAttendanceButtonActionPerformed(evt);
            }
        });

        finalizedAttendanceButton.setBackground(new java.awt.Color(74, 156, 85));
        finalizedAttendanceButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        finalizedAttendanceButton.setForeground(new java.awt.Color(255, 255, 255));
        finalizedAttendanceButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/Finalize.png"))); // NOI18N
        finalizedAttendanceButton.setText(" Finalizes Attendance");
        finalizedAttendanceButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        finalizedAttendanceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                finalizedAttendanceButtonActionPerformed(evt);
            }
        });

        jLabel43.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(153, 153, 153));
        jLabel43.setText(" EMPLOYEE NIC");

        NicTextField.setForeground(new java.awt.Color(255, 255, 255));
        NicTextField.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        NicTextField.setPreferredSize(new java.awt.Dimension(91, 30));
        NicTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                NicTextFieldKeyReleased(evt);
            }
        });

        clearAttendanceSectionButton.setBackground(new java.awt.Color(102, 102, 102));
        clearAttendanceSectionButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/Clear.png"))); // NOI18N
        clearAttendanceSectionButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clearAttendanceSectionButton.setPreferredSize(new java.awt.Dimension(27, 27));
        clearAttendanceSectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearAttendanceSectionButtonActionPerformed(evt);
            }
        });

        attendanceTable.setBackground(new java.awt.Color(29, 29, 29));
        attendanceTable.setForeground(new java.awt.Color(255, 255, 255));
        attendanceTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NIC Number", "Name", "Status", "Date"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        attendanceTable.setInheritsPopupMenu(true);
        attendanceTable.setRowHeight(23);
        attendanceTable.setShowGrid(true);
        attendanceTable.setSurrendersFocusOnKeystroke(true);
        attendanceTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                attendanceTableMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(attendanceTable);

        markAttendanceButton.setBackground(java.awt.SystemColor.textHighlight);
        markAttendanceButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        markAttendanceButton.setForeground(new java.awt.Color(255, 255, 255));
        markAttendanceButton.setText(" Mark Attendance");
        markAttendanceButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        markAttendanceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                markAttendanceButtonActionPerformed(evt);
            }
        });

        jLabel44.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel44.setText("ATTENDANCE DETAILS");

        jLabel45.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel45.setText("Employee Details");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("Name :");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("NIC Number :");

        employeeNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        employeeNameLabel.setForeground(new java.awt.Color(153, 153, 153));
        employeeNameLabel.setText("None");

        employeeNICLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        employeeNICLabel.setForeground(new java.awt.Color(153, 153, 153));
        employeeNICLabel.setText("None");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("Monthly Attendance :");

        monthlyAttendCountLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        monthlyAttendCountLabel.setForeground(new java.awt.Color(153, 153, 153));
        monthlyAttendCountLabel.setText("None");

        jLabel46.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel46.setText("All Employee Summary");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Total Employees Count :");

        totalEmployeesCountLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        totalEmployeesCountLabel.setForeground(new java.awt.Color(153, 153, 153));
        totalEmployeesCountLabel.setText("None");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setText("Today Attend Count      :");

        todayAttendCountLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        todayAttendCountLabel.setForeground(new java.awt.Color(153, 153, 153));
        todayAttendCountLabel.setText("None");

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/Summary.png"))); // NOI18N

        javax.swing.GroupLayout roundedPanel2Layout = new javax.swing.GroupLayout(roundedPanel2);
        roundedPanel2.setLayout(roundedPanel2Layout);
        roundedPanel2Layout.setHorizontalGroup(
            roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel2Layout.createSequentialGroup()
                .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(roundedPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(roundedPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(totalEmployeesCountLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(roundedPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(todayAttendCountLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(roundedPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(monthlyAttendCountLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(roundedPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(employeeNICLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(roundedPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(employeeNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap())
        );
        roundedPanel2Layout.setVerticalGroup(
            roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundedPanel2Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(employeeNICLabel))
                    .addGroup(roundedPanel2Layout.createSequentialGroup()
                        .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(employeeNameLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(monthlyAttendCountLabel)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel46)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(totalEmployeesCountLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(todayAttendCountLabel))
                .addContainerGap())
        );

        jLabel7.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(153, 153, 153));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/info.png"))); // NOI18N
        jLabel7.setText(" Double click the table row to mark the attendance.");

        logInLabel1.setBackground(new java.awt.Color(255, 255, 255));
        logInLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        logInLabel1.setForeground(new java.awt.Color(204, 204, 204));
        logInLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logInLabel1.setText("EMPLOYEE'S ATTENDANCE MANAGEMENT");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(logInLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel43)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(NicTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(markAttendanceButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(clearAttendanceSectionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(arrangeAttendanceButton)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(finalizedAttendanceButton)
                        .addGap(8, 8, 8))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 867, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(roundedPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(logInLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel44)
                            .addComponent(jLabel7)))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(finalizedAttendanceButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(arrangeAttendanceButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel43)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(clearAttendanceSectionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(NicTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(markAttendanceButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(roundedPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Employee Attendance", jPanel2);

        javax.swing.GroupLayout roundedPanel1Layout = new javax.swing.GroupLayout(roundedPanel1);
        roundedPanel1.setLayout(roundedPanel1Layout);
        roundedPanel1Layout.setHorizontalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        roundedPanel1Layout.setVerticalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundedPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundedPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addNewEmployeeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNewEmployeeButtonActionPerformed

        EmployeeRegistration employeeRegistration = new EmployeeRegistration(mainPanelSection, true, this);
        employeeRegistration.setLocationRelativeTo(mainPanelSection);

        employeeRegistration.getUpdateEmployeeButton().setVisible(false);
        employeeRegistration.setVisible(true);

    }//GEN-LAST:event_addNewEmployeeButtonActionPerformed

    private void employeeNameOrNicTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_employeeNameOrNicTextFieldKeyReleased
        loadEmployees(employeeNameOrNicTextField.getText());
    }//GEN-LAST:event_employeeNameOrNicTextFieldKeyReleased

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        employeeNameOrNicTextField.setText("");
        loadEmployees("");
    }//GEN-LAST:event_clearButtonActionPerformed

    private void employeeTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_employeeTableMouseClicked

        if (evt.getClickCount() == 2) {

            String nic = (String) employeeTable.getValueAt(employeeTable.getSelectedRow(), 0);
            EmployeeRegistration employeeRegistration = new EmployeeRegistration(mainPanelSection, true, this);
            employeeRegistration.setTitle("Employee Update");
            employeeRegistration.getTitleLabel().setText("Employee Update");
            employeeRegistration.getEmployeeNicTextField().setText(nic);
            employeeRegistration.getEmployeeNicTextField().setEnabled(false);
            employeeRegistration.getEmployeeBirthdayChooser().setEnabled(false);
            employeeRegistration.getMaleRadioButton().setEnabled(false);
            employeeRegistration.getFemaleRadioButton().setEnabled(false);
            employeeRegistration.getRegiserEmployeeButton().setVisible(false);
            employeeRegistration.getClearButton().setVisible(false);
            employeeRegistration.getEmployeeFirstNameTextField().grabFocus();
            employeeRegistration.fillUpdateDetails();
            employeeRegistration.setLocationRelativeTo(mainPanelSection);
            employeeRegistration.setVisible(true);

        }

    }//GEN-LAST:event_employeeTableMouseClicked

    private void printButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printButtonActionPerformed

        try {
            HashMap<String, Object> parameters = new HashMap<>();
            String filePath = "src//Reports/EmployeeReports.jasper";

            JRDataSource employeeTableDataSource = new JRTableModelDataSource(employeeTable.getModel());

            LocalDateTime currentDateTime = LocalDateTime.now();
            String datetime = DateTimeFormatter.ofPattern("yyyy MMM dd - hh:mm:ss a").format(currentDateTime);
            ResultSet employeeCount = MySQL.execute("SELECT COUNT(*) AS employeeCount FROM `users` WHERE `user_type_user_type_id`='3'");

            parameters.put("DateTime", datetime);

            if (employeeCount.next()) {
                parameters.put("TotalEmployees", employeeCount.getString("employeeCount"));
            }

            JasperPrint report = JasperFillManager.fillReport(filePath, parameters, employeeTableDataSource);
            JasperViewer.viewReport(report, false);

        } catch (Exception e) {
            Log.log.warning(e.toString());
        }

    }//GEN-LAST:event_printButtonActionPerformed

    private void arrangeAttendanceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_arrangeAttendanceButtonActionPerformed

        try {

            ResultSet employeeResult = MySQL.execute("SELECT `NIC` FROM `users` WHERE `user_type_user_type_id`='3' AND `user_status_user_status_id`='1'");

            LocalDateTime currentDateTime = LocalDateTime.now();
            String dateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss").format(currentDateTime);

            while (employeeResult.next()) {

                MySQL.execute("INSERT INTO `employee_attendance` (`date_time`,`attendance_status_attendance_status_id`,`employee_NIC`) VALUES('" + dateTime + "','" + 1 + "','" + employeeResult.getString("NIC") + "')");

            }

            loadAttendanceDetails("");
            Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.BOTTOM_CENTER, "Attendance arrange is complete");

            finalizedAttendanceButton.setEnabled(true);
            markAttendanceButton.setEnabled(true);
            clearAttendanceSectionButton.setEnabled(true);
            NicTextField.setEnabled(true);
            attendanceTable.setEnabled(true);
            arrangeAttendanceButton.setEnabled(false);
            NicTextField.grabFocus();

        } catch (Exception e) {
            Log.log.warning(e.toString());
        }

    }//GEN-LAST:event_arrangeAttendanceButtonActionPerformed

    private void NicTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NicTextFieldKeyReleased

        String nic = NicTextField.getText();

        loadAttendanceDetails(nic);
        fillEmployeeDetails(nic);
    }//GEN-LAST:event_NicTextFieldKeyReleased

    private void clearAttendanceSectionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearAttendanceSectionButtonActionPerformed
        clearArrangeSection();
    }//GEN-LAST:event_clearAttendanceSectionButtonActionPerformed

    private void markAttendanceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_markAttendanceButtonActionPerformed

        String nic = NicTextField.getText();

        if (!nic.isEmpty()) {

            try {

                ResultSet employeeResult = MySQL.execute("SELECT * FROM `users` WHERE `NIC`='" + nic + "' AND `user_type_user_type_id`='3'");

                if (employeeResult.next()) {

                    LocalDate today = LocalDate.now();
                    ResultSet attendanceResult = MySQL.execute("SELECT * FROM `employee_attendance` WHERE `employee_NIC`='" + nic + "' AND `date_time` LIKE '" + today + "%' "
                            + "AND (`attendance_status_attendance_status_id`='2' OR `attendance_status_attendance_status_id`='3')");

                    if (attendanceResult.next()) {
                        JOptionPane.showMessageDialog(this, "Attendance is Already Marked", "Warning", JOptionPane.WARNING_MESSAGE);
                    } else {

                        LocalDateTime currentDateTime = LocalDateTime.now();
                        String updateDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss").format(currentDateTime);

                        MySQL.execute("UPDATE `employee_attendance` "
                                + "SET `attendance_status_attendance_status_id` = '2', `date_time` = '" + updateDateTime + "' "
                                + "WHERE `employee_NIC` = '" + nic + "' AND `date_time` LIKE '" + today + "%'");
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.BOTTOM_CENTER, "Attendance is marked");
                        loadAttendanceDetails("");
                        fillEmployeeDetails("");
                        clearArrangeSection();

                    }

                } else {

                    JOptionPane.showMessageDialog(this, "The Employee was Not Found", "Oops !", JOptionPane.ERROR_MESSAGE);

                }

            } catch (Exception e) {
                Log.log.warning(e.toString());
            }

        } else {
            JOptionPane.showMessageDialog(this, "Please type the Employee NIC", "Warning", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_markAttendanceButtonActionPerformed

    private void finalizedAttendanceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_finalizedAttendanceButtonActionPerformed

        int action = JOptionPane.showConfirmDialog(this, "Are you sure you want to finalize the attendance ?", "Confirm Finalizes Attendance", JOptionPane.YES_OPTION);

        if (action == JOptionPane.YES_OPTION) {

            try {

                ResultSet attendanceResult = MySQL.execute("SELECT * FROM `employee_attendance` WHERE `attendance_status_attendance_status_id`='1'");

                while (attendanceResult.next()) {

                    MySQL.execute("UPDATE `employee_attendance` SET `attendance_status_attendance_status_id`='3' WHERE `employee_attendance_id`='" + attendanceResult.getString("employee_attendance_id") + "'");

                }

                loadAttendanceDetails("");
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.BOTTOM_CENTER, "Attendance has been finalized");

            } catch (Exception e) {
                Log.log.warning(e.toString());
            }

        }

    }//GEN-LAST:event_finalizedAttendanceButtonActionPerformed

    private void attendanceTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_attendanceTableMouseClicked

        int selectedRow = attendanceTable.getSelectedRow();

        if (evt.getClickCount() == 2) {
            if (selectedRow > -1) {

                String nic = String.valueOf(attendanceTable.getValueAt(selectedRow, 0));

                NicTextField.setText(nic);
                fillEmployeeDetails(nic);

            }
        }

    }//GEN-LAST:event_attendanceTableMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Component.RoundedTextField NicTextField;
    private javax.swing.JButton addNewEmployeeButton;
    private javax.swing.JButton arrangeAttendanceButton;
    private javax.swing.JTable attendanceTable;
    private javax.swing.JButton clearAttendanceSectionButton;
    private javax.swing.JButton clearButton;
    private javax.swing.JLabel employeeNICLabel;
    private javax.swing.JLabel employeeNameLabel;
    private Component.RoundedTextField employeeNameOrNicTextField;
    private javax.swing.JTable employeeTable;
    private javax.swing.JButton finalizedAttendanceButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel logInLabel;
    private javax.swing.JLabel logInLabel1;
    private javax.swing.JButton markAttendanceButton;
    private javax.swing.JLabel monthlyAttendCountLabel;
    private javax.swing.JButton printButton;
    private Component.RoundedPanel roundedPanel1;
    private Component.RoundedPanel roundedPanel2;
    private javax.swing.JLabel todayAttendCountLabel;
    private javax.swing.JLabel totalEmployeesCountLabel;
    // End of variables declaration//GEN-END:variables
}
