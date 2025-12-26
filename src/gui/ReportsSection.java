package gui;

import connection.MySQL;
import java.awt.Color;
import java.io.File;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import log.Log;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class ReportsSection extends javax.swing.JPanel {

    public ReportsSection() {
        initComponents();
        init();
    }

    private void init() {

        this.setBackground(new Color(255, 255, 255, 0));
        loadDailyIncome();

        String yearAndMonth = monthlyIncomeYearChooser.getYear() + "-" + String.format("%02d", monthlyIncomeMonthChooser.getMonth() + 1);
        loadMonthlyIncome(yearAndMonth);

    }

    private void loadDailyIncome() {

        try {
            String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

            ResultSet bookingUserResult = MySQL.execute("SELECT DISTINCT `first_name`, `last_name`, `users_NIC`, `booking_date` FROM `booking` INNER JOIN `users` ON `booking`.`users_NIC` = `users`.`NIC` "
                    + "WHERE `booking_date` = '" + today + "'");

            DefaultTableModel tableModel = (DefaultTableModel) dailyIncomeTable.getModel();
            tableModel.setRowCount(0);

            int count = 0;
            int totalBooking = 0;
            int totalIncome = 0;

            while (bookingUserResult.next()) {
                count++;

                ResultSet bookignResult = MySQL.execute("SELECT * FROM `booking` "
                        + "INNER JOIN `users` ON `booking`.`users_NIC` = `users`.`NIC` "
                        + "WHERE `users_NIC`='" + bookingUserResult.getString("users_NIC") + "' AND `payment_status_payment_status_id`='2' AND `booking_date`='" + today + "'");

                int roomCount = 0;
                int guestCount = 0;
                double total = 0;

                while (bookignResult.next()) {

                    roomCount++;
                    guestCount += Integer.parseInt(bookignResult.getString("guest_count"));
                    total += Double.parseDouble(bookignResult.getString("total_amount"));

                    totalBooking++;

                }

                totalIncome += total;

                Vector<String> dailyIncomeVector = new Vector<>();
                dailyIncomeVector.add(String.valueOf(count));
                dailyIncomeVector.add(bookingUserResult.getString("first_name") + " " + bookingUserResult.getString("last_name"));
                dailyIncomeVector.add(String.valueOf(roomCount));
                dailyIncomeVector.add(String.valueOf(guestCount));
                dailyIncomeVector.add(String.valueOf(total));

                tableModel.addRow(dailyIncomeVector);

            }

            dailyTotalBookingLabel.setText(String.valueOf(totalBooking));
            dailyTotalIncomeLabel.setText(new DecimalFormat("#,###").format(totalIncome));

        } catch (Exception e) {
            Log.log.warning(e.toString());
        }

    }

//    Monthly Income
    private void loadMonthlyIncome(String yearAndMonth) {
        try {

            ResultSet bookingUserResult = MySQL.execute("SELECT DISTINCT `first_name`, `last_name`, `users_NIC`, `booking_date` FROM `booking` INNER JOIN `users` ON `booking`.`users_NIC` = `users`.`NIC` "
                    + "WHERE `booking_date` LIKE '" + yearAndMonth + "%'");

            DefaultTableModel tableModel = (DefaultTableModel) monthlyIncomeTable.getModel();
            tableModel.setRowCount(0);

            int count = 0;
            int totalBooking = 0;
            int totalIncome = 0;

            while (bookingUserResult.next()) {
                count++;

                ResultSet bookignResult = MySQL.execute("SELECT * FROM `booking` "
                        + "INNER JOIN `users` ON `booking`.`users_NIC` = `users`.`NIC` "
                        + "WHERE `users_NIC`='" + bookingUserResult.getString("users_NIC") + "' AND `payment_status_payment_status_id`='2' AND `booking_date` LIKE '" + yearAndMonth + "%'");

                int roomCount = 0;
                int guestCount = 0;
                double total = 0;

                while (bookignResult.next()) {

                    roomCount++;
                    guestCount += Integer.parseInt(bookignResult.getString("guest_count"));
                    total += Double.parseDouble(bookignResult.getString("total_amount"));

                    totalBooking++;

                }

                totalIncome += total;

                Vector<String> monthlyIncomeVector = new Vector<>();
                monthlyIncomeVector.add(String.valueOf(count));
                monthlyIncomeVector.add(bookingUserResult.getString("first_name") + " " + bookingUserResult.getString("last_name"));
                monthlyIncomeVector.add(String.valueOf(roomCount));
                monthlyIncomeVector.add(String.valueOf(guestCount));
                monthlyIncomeVector.add(String.valueOf(total));

                tableModel.addRow(monthlyIncomeVector);

            }

            monthlyTotalBookingLabel.setText(String.valueOf(totalBooking));
            monthlyTotalIncomeLabel.setText(new DecimalFormat("#,###").format(totalIncome));

        } catch (Exception e) {
            Log.log.warning(e.toString());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        roundedPanel1 = new Component.RoundedPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        roundedPanel2 = new Component.RoundedPanel();
        jLabel45 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        dailyTotalBookingLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        dailyTotalIncomeLabel = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        dailyIncomeTable = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        saveDailyReportButton = new javax.swing.JButton();
        printDailyReportButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        roundedPanel3 = new Component.RoundedPanel();
        jLabel47 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        yearLabel = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        monthlyTotalIncomeLabel = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        monthlyIncomeMonthChooser = new com.toedter.calendar.JMonthChooser();
        monthlyIncomeYearChooser = new com.toedter.calendar.JYearChooser();
        monthlyTotalBookingLabel = new javax.swing.JLabel();
        monthLabel = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        monthlyIncomeTable = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        printMonthlyReportButton = new javax.swing.JButton();
        saveMonthlyReportButton = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        roundedPanel1.setBackground(new java.awt.Color(24, 24, 24));

        jTabbedPane1.setBackground(new java.awt.Color(24, 24, 24));

        jPanel1.setBackground(new java.awt.Color(24, 24, 24));

        jLabel44.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel44.setText(" DAILY INCOME REPORT'S DETAILS");

        jLabel45.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel45.setText(" REPORT SUMMARY");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText(" Total Booking           :");

        dailyTotalBookingLabel.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        dailyTotalBookingLabel.setForeground(new java.awt.Color(153, 153, 153));
        dailyTotalBookingLabel.setText("None");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText(" Total Income (Rs)    :");

        dailyTotalIncomeLabel.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        dailyTotalIncomeLabel.setForeground(new java.awt.Color(153, 153, 153));
        dailyTotalIncomeLabel.setText("None");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/Report.png"))); // NOI18N

        javax.swing.GroupLayout roundedPanel2Layout = new javax.swing.GroupLayout(roundedPanel2);
        roundedPanel2.setLayout(roundedPanel2Layout);
        roundedPanel2Layout.setHorizontalGroup(
            roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel2Layout.createSequentialGroup()
                .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundedPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(roundedPanel2Layout.createSequentialGroup()
                                .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jSeparator1))
                                .addGap(20, 20, 20))
                            .addGroup(roundedPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(dailyTotalIncomeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 56, Short.MAX_VALUE))))
                    .addGroup(roundedPanel2Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(roundedPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dailyTotalBookingLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        roundedPanel2Layout.setVerticalGroup(
            roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
                .addGap(61, 61, 61)
                .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(dailyTotalBookingLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(dailyTotalIncomeLabel))
                .addGap(14, 14, 14))
        );

        dailyIncomeTable.setBackground(new java.awt.Color(29, 29, 29));
        dailyIncomeTable.setForeground(new java.awt.Color(255, 255, 255));
        dailyIncomeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Customer Name", "Room Count", "Guest Count", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        dailyIncomeTable.setInheritsPopupMenu(true);
        dailyIncomeTable.setRowHeight(23);
        dailyIncomeTable.setShowGrid(true);
        dailyIncomeTable.setSurrendersFocusOnKeystroke(true);
        jScrollPane5.setViewportView(dailyIncomeTable);

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("DAILY INCOME MANAGEMENT");

        saveDailyReportButton.setBackground(new java.awt.Color(51, 51, 51));
        saveDailyReportButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        saveDailyReportButton.setForeground(new java.awt.Color(0, 0, 0));
        saveDailyReportButton.setText(" Save Details");
        saveDailyReportButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        saveDailyReportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveDailyReportButtonActionPerformed(evt);
            }
        });

        printDailyReportButton.setBackground(new java.awt.Color(51, 51, 51));
        printDailyReportButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        printDailyReportButton.setForeground(new java.awt.Color(255, 255, 255));
        printDailyReportButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/Print.png"))); // NOI18N
        printDailyReportButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        printDailyReportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printDailyReportButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 737, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(printDailyReportButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(saveDailyReportButton))
                            .addComponent(roundedPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel44)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jLabel44)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(roundedPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(saveDailyReportButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(printDailyReportButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane5))
                .addGap(14, 14, 14))
        );

        jTabbedPane1.addTab("Daily Income", jPanel1);

        jPanel2.setBackground(new java.awt.Color(24, 24, 24));

        jLabel46.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel46.setText(" MONTHLY INCOME REPORT'S DETAILS");

        jLabel47.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel47.setText(" REPORT SUMMARY");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText(" Total Room Booking   :");

        yearLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        yearLabel.setForeground(new java.awt.Color(153, 153, 153));
        yearLabel.setText("Year");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText(" Total Income (Rs)        :");

        monthlyTotalIncomeLabel.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        monthlyTotalIncomeLabel.setForeground(new java.awt.Color(153, 153, 153));
        monthlyTotalIncomeLabel.setText("None");

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/Report.png"))); // NOI18N

        jLabel48.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel48.setText(" FIND BY YEAR AND MONTH");

        monthlyIncomeMonthChooser.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                monthlyIncomeMonthChooserPropertyChange(evt);
            }
        });

        monthlyIncomeYearChooser.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                monthlyIncomeYearChooserPropertyChange(evt);
            }
        });

        monthlyTotalBookingLabel.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        monthlyTotalBookingLabel.setForeground(new java.awt.Color(153, 153, 153));
        monthlyTotalBookingLabel.setText("None");

        monthLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        monthLabel.setForeground(new java.awt.Color(153, 153, 153));
        monthLabel.setText("Month");

        javax.swing.GroupLayout roundedPanel3Layout = new javax.swing.GroupLayout(roundedPanel3);
        roundedPanel3.setLayout(roundedPanel3Layout);
        roundedPanel3Layout.setHorizontalGroup(
            roundedPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundedPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel48, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel47, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator3)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(roundedPanel3Layout.createSequentialGroup()
                        .addGroup(roundedPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(roundedPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(monthlyTotalIncomeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(roundedPanel3Layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addGroup(roundedPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(roundedPanel3Layout.createSequentialGroup()
                                        .addComponent(monthlyIncomeYearChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(monthlyIncomeMonthChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(roundedPanel3Layout.createSequentialGroup()
                                        .addComponent(yearLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(52, 52, 52)
                                        .addComponent(monthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(roundedPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(monthlyTotalBookingLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        roundedPanel3Layout.setVerticalGroup(
            roundedPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel48)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(roundedPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(monthLabel)
                    .addComponent(yearLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(roundedPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(monthlyIncomeYearChooser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(monthlyIncomeMonthChooser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addComponent(jLabel47)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(roundedPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(monthlyTotalBookingLabel)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(roundedPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(monthlyTotalIncomeLabel))
                .addGap(13, 13, 13))
        );

        monthlyIncomeTable.setBackground(new java.awt.Color(29, 29, 29));
        monthlyIncomeTable.setForeground(new java.awt.Color(255, 255, 255));
        monthlyIncomeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Customer Name", "Room Count", "Guest Count", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        monthlyIncomeTable.setInheritsPopupMenu(true);
        monthlyIncomeTable.setRowHeight(23);
        monthlyIncomeTable.setShowGrid(true);
        monthlyIncomeTable.setSurrendersFocusOnKeystroke(true);
        jScrollPane6.setViewportView(monthlyIncomeTable);

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("MONTHLY INCOME MANAGEMENT");

        printMonthlyReportButton.setBackground(new java.awt.Color(51, 51, 51));
        printMonthlyReportButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        printMonthlyReportButton.setForeground(new java.awt.Color(255, 255, 255));
        printMonthlyReportButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/Print.png"))); // NOI18N
        printMonthlyReportButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        printMonthlyReportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printMonthlyReportButtonActionPerformed(evt);
            }
        });

        saveMonthlyReportButton.setBackground(new java.awt.Color(51, 51, 51));
        saveMonthlyReportButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        saveMonthlyReportButton.setForeground(new java.awt.Color(0, 0, 0));
        saveMonthlyReportButton.setText(" Save Details");
        saveMonthlyReportButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        saveMonthlyReportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMonthlyReportButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 732, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(roundedPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(printMonthlyReportButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(saveMonthlyReportButton))))
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel46)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel15)
                .addGap(32, 32, 32)
                .addComponent(jLabel46)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(roundedPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(printMonthlyReportButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(saveMonthlyReportButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane6))
                .addGap(18, 18, 18))
        );

        jTabbedPane1.addTab("Monthly Income", jPanel2);

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

    private void printDailyReportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printDailyReportButtonActionPerformed

        if (dailyIncomeTable.getRowCount() != 0) {

            try {

                HashMap<String, Object> parameters = new HashMap<>();
                String path = "src//Reports/DailyIncome.jasper";

                JRDataSource dataSource = new JRTableModelDataSource(dailyIncomeTable.getModel());
                LocalDateTime dateTime = LocalDateTime.now();
                String date = DateTimeFormatter.ofPattern("yyyy-MMM-dd").format(dateTime);

                int reportNumber = new Random().nextInt(900000000) + 100000000;

                parameters.put("ReportNumber", String.valueOf(reportNumber));
                parameters.put("Date", date);
                parameters.put("TotalBooking", dailyTotalBookingLabel.getText());
                parameters.put("TotalIncome", dailyTotalIncomeLabel.getText());

                JasperPrint report = JasperFillManager.fillReport(path, parameters, dataSource);
                JasperViewer.viewReport(report, false);

            } catch (Exception e) {
                Log.log.warning(e.toString());
            }

        } else {
            JOptionPane.showMessageDialog(this, "The report cannot be printed because there is no data available.", "Print Report", JOptionPane.WARNING_MESSAGE);
        }


    }//GEN-LAST:event_printDailyReportButtonActionPerformed

    private void saveDailyReportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveDailyReportButtonActionPerformed

        if (dailyIncomeTable.getRowCount() != 0) {

            int option = JOptionPane.showConfirmDialog(this, "Do you want to save this daily income report?", "Confirmation of Report Saving", JOptionPane.YES_OPTION);

            if (option == JOptionPane.YES_OPTION) {

                try {

                    HashMap<String, Object> parameters = new HashMap<>();
                    String path = "src//Reports/DailyIncome.jasper";

                    JRDataSource dataSource = new JRTableModelDataSource(dailyIncomeTable.getModel());
                    LocalDateTime dateTime = LocalDateTime.now();
                    String date = DateTimeFormatter.ofPattern("yyyy-MMM-dd").format(dateTime);

                    int reportNumber = new Random().nextInt(900000000) + 100000000;

                    parameters.put("ReportNumber", String.valueOf(reportNumber));
                    parameters.put("Date", date);
                    parameters.put("TotalBooking", dailyTotalBookingLabel.getText());
                    parameters.put("TotalIncome", dailyTotalIncomeLabel.getText());

                    JasperPrint report = JasperFillManager.fillReport(path, parameters, dataSource);

                    String userHome = System.getProperty("user.home");
                    String oneDrivePath = userHome + File.separator + "OneDrive" + File.separator + "Documents";
                    String folderPath;

                    File oneDriveFolder = new File(oneDrivePath);
                    if (oneDriveFolder.exists()) {
                        folderPath = oneDrivePath + File.separator + "Tranquil Reports/Daily Reports";
                    } else {
                        folderPath = userHome + File.separator + "Documents" + File.separator + "Tranquil Reports/Daily Reports";
                    }

                    File folder = new File(folderPath);
                    if (!folder.exists()) {
                        folder.mkdirs();
                    }

                    String savePath = folderPath + File.separator + "Daily Report - " + date + ".pdf";
                    JasperExportManager.exportReportToPdfFile(report, savePath);

                    JOptionPane.showMessageDialog(this, "Report saved path is either `OneDrive/Documents/Tranquil Reports` or `Documents/Tranquil Reports`.", "Successfully Saved", JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception e) {
                    Log.log.warning(e.toString());
                }

            }

        } else {
            JOptionPane.showMessageDialog(this, "The report cannot be printed because there is no data available.", "Print Report", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_saveDailyReportButtonActionPerformed

    private void monthlyIncomeMonthChooserPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_monthlyIncomeMonthChooserPropertyChange

        String yearAndMonth = monthlyIncomeYearChooser.getYear() + "-" + String.format("%02d", monthlyIncomeMonthChooser.getMonth() + 1);
        loadMonthlyIncome(yearAndMonth);

    }//GEN-LAST:event_monthlyIncomeMonthChooserPropertyChange

    private void monthlyIncomeYearChooserPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_monthlyIncomeYearChooserPropertyChange
        String yearAndMonth = monthlyIncomeYearChooser.getYear() + "-" + String.format("%02d", monthlyIncomeMonthChooser.getMonth() + 1);
        loadMonthlyIncome(yearAndMonth);
    }//GEN-LAST:event_monthlyIncomeYearChooserPropertyChange

    private void printMonthlyReportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printMonthlyReportButtonActionPerformed

        if (monthlyIncomeTable.getRowCount() != 0) {

            try {

                HashMap<String, Object> parameters = new HashMap<>();
                String path = "src//Reports/MonthlyIncome.jasper";

                JRDataSource dataSource = new JRTableModelDataSource(monthlyIncomeTable.getModel());
                LocalDateTime dateTime = LocalDateTime.now();
                String date = DateTimeFormatter.ofPattern("yyyy-MMM").format(dateTime);
                String printDateTime = new SimpleDateFormat("yyyy-MMM-dd hh:mm:ss aaa").format(new Date());

                int reportNumber = new Random().nextInt(900000000) + 100000000;

                parameters.put("DateTime", printDateTime);
                parameters.put("ReportNumber", String.valueOf(reportNumber));
                parameters.put("Month", date);
                parameters.put("TotalBooking", monthlyTotalBookingLabel.getText());
                parameters.put("TotalIncome", monthlyTotalIncomeLabel.getText());

                JasperPrint report = JasperFillManager.fillReport(path, parameters, dataSource);
                JasperViewer.viewReport(report, false);

            } catch (Exception e) {
                Log.log.warning(e.toString());
            }

        } else {
            JOptionPane.showMessageDialog(this, "The report cannot be printed because there is no data available.", "Print Report", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_printMonthlyReportButtonActionPerformed

    private void saveMonthlyReportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMonthlyReportButtonActionPerformed

        if (monthlyIncomeTable.getRowCount() != 0) {

            int option = JOptionPane.showConfirmDialog(this, "Do you want to save this monthly income report?", "Confirmation of Report Saving", JOptionPane.YES_OPTION);

            if (option == JOptionPane.YES_OPTION) {

                try {

                    HashMap<String, Object> parameters = new HashMap<>();
                    String path = "src//Reports/MonthlyIncome.jasper";

                    JRDataSource dataSource = new JRTableModelDataSource(monthlyIncomeTable.getModel());
                    LocalDateTime dateTime = LocalDateTime.now();
                    String date = DateTimeFormatter.ofPattern("yyyy-MMM").format(dateTime);
                    String printDateTime = new SimpleDateFormat("yyyy-MMM-dd hh:mm:ss aaa").format(new Date());

                    int reportNumber = new Random().nextInt(900000000) + 100000000;

                    parameters.put("DateTime", printDateTime);
                    parameters.put("ReportNumber", String.valueOf(reportNumber));
                    parameters.put("Month", date);
                    parameters.put("TotalBooking", monthlyTotalBookingLabel.getText());
                    parameters.put("TotalIncome", monthlyTotalIncomeLabel.getText());

                    JasperPrint report = JasperFillManager.fillReport(path, parameters, dataSource);

                    String userHome = System.getProperty("user.home");
                    String oneDrivePath = userHome + File.separator + "OneDrive" + File.separator + "Documents";
                    String folderPath;

                    File oneDriveFolder = new File(oneDrivePath);
                    if (oneDriveFolder.exists()) {
                        folderPath = oneDrivePath + File.separator + "Tranquil Reports/Monthly Reports";
                    } else {
                        folderPath = userHome + File.separator + "Documents" + File.separator + "Tranquil Reports/Monthly Reports";
                    }

                    File folder = new File(folderPath);
                    if (!folder.exists()) {
                        folder.mkdirs();
                    }

                    String savePath = folderPath + File.separator + "Monthly Report - " + date + ".pdf";
                    JasperExportManager.exportReportToPdfFile(report, savePath);

                    JOptionPane.showMessageDialog(this, "Report saved path is either `OneDrive/Documents/Tranquil Reports` or `Documents/Tranquil Reports`.", "Successfully Saved", JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception e) {
                    Log.log.warning(e.toString());
                }

            }

        } else {
            JOptionPane.showMessageDialog(this, "The report cannot be printed because there is no data available.", "Print Report", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_saveMonthlyReportButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable dailyIncomeTable;
    private javax.swing.JLabel dailyTotalBookingLabel;
    private javax.swing.JLabel dailyTotalIncomeLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel monthLabel;
    private com.toedter.calendar.JMonthChooser monthlyIncomeMonthChooser;
    private javax.swing.JTable monthlyIncomeTable;
    private com.toedter.calendar.JYearChooser monthlyIncomeYearChooser;
    private javax.swing.JLabel monthlyTotalBookingLabel;
    private javax.swing.JLabel monthlyTotalIncomeLabel;
    private javax.swing.JButton printDailyReportButton;
    private javax.swing.JButton printMonthlyReportButton;
    private Component.RoundedPanel roundedPanel1;
    private Component.RoundedPanel roundedPanel2;
    private Component.RoundedPanel roundedPanel3;
    private javax.swing.JButton saveDailyReportButton;
    private javax.swing.JButton saveMonthlyReportButton;
    private javax.swing.JLabel yearLabel;
    // End of variables declaration//GEN-END:variables
}
