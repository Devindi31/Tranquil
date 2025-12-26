package gui;

import connection.MySQL;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
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

public class PaymentSection extends javax.swing.JPanel {

    public PaymentSection() {
        initComponents();

        this.setBackground(new Color(255, 255, 255, 0));
        loadPendingPayments("");
    }

    private void clear() {

        customerNICTextField.setText("");
        pendingPaymentsCustomerTable.clearSelection();
        loadPendingPayments("");
        loadBookingDetails("");
        paidAmountTextField.setText("");

    }

    private void loadPendingPayments(String customerNIC) {

        try {

            ResultSet pendingPaymentsCustomerResults = MySQL.execute("SELECT DISTINCT `users`.`NIC`, `users`.`first_name`, `users`.`last_name`, `users`.`mobile`, `users`.`registered_date`, `nationality`.`nationality_name` "
                    + "FROM `booking`"
                    + "INNER JOIN `users` ON `booking`.`users_NIC` = `users`.`NIC`"
                    + "INNER JOIN `nationality` ON `users`.`nationality_nationality_id` = `nationality`.`nationality_id`"
                    + "WHERE `booking`.`users_NIC` LIKE '%" + customerNIC + "%' "
                    + "AND `users`.`user_type_user_type_id` = '1' "
                    + "AND `booking`.`payment_status_payment_status_id` = '1'");

            DefaultTableModel pendingPaymentCustomerTableModel = (DefaultTableModel) pendingPaymentsCustomerTable.getModel();
            pendingPaymentCustomerTableModel.setRowCount(0);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MMM-dd");

            while (pendingPaymentsCustomerResults.next()) {

                Date registeredDate = format.parse(pendingPaymentsCustomerResults.getString("registered_date"));
                String formatedRegisteredDate = newFormat.format(registeredDate);

                Vector<String> pendingPaymentVector = new Vector<>();

                pendingPaymentVector.add(pendingPaymentsCustomerResults.getString("first_name") + " " + pendingPaymentsCustomerResults.getString("last_name"));
                pendingPaymentVector.add(pendingPaymentsCustomerResults.getString("NIC"));
                pendingPaymentVector.add(pendingPaymentsCustomerResults.getString("mobile"));
                pendingPaymentVector.add(formatedRegisteredDate);
                pendingPaymentVector.add(pendingPaymentsCustomerResults.getString("nationality_name"));

                pendingPaymentCustomerTableModel.addRow(pendingPaymentVector);
            }

        } catch (Exception e) {
            Log.log.warning(e.toString());
        }

    }

    private void calculateBalance(double totalAmount, double paidAmount) {

        double balance = 0;

        if (paidAmountTextField.getText().isEmpty()) {
            balance = 0 - totalAmount;
            balanceLabel.setForeground(new java.awt.Color(255, 0, 0));
        } else {

            balance = paidAmount - totalAmount;

            if (paidAmount >= totalAmount) {
                balanceLabel.setForeground(new java.awt.Color(0, 204, 0));
            } else {
                balanceLabel.setForeground(new java.awt.Color(255, 0, 0));
            }

        }

        balanceLabel.setText(new DecimalFormat("#").format(balance));

    }

    private void loadBookingDetails(String customerNic) {

        try {

            ResultSet bookingResult = MySQL.execute("SELECT * FROM `booking` "
                    + "INNER JOIN `users` ON `booking`.`users_NIC`=`users`.`NIC` "
                    + "INNER JOIN `rooms` ON `booking`.`rooms_room_number`=`rooms`.`room_number` "
                    + "INNER JOIN `room_category` ON `rooms`.`room_category_room_category_id`=`room_category`.`room_category_id`"
                    + "WHERE `users`.`NIC`='" + customerNic + "' AND `payment_status_payment_status_id`='1'");

            DefaultTableModel detailsTableModel = (DefaultTableModel) bookingDetailsTable.getModel();
            detailsTableModel.setRowCount(0);

            double totalAmount = 0.00;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MMM-dd");

            int count = 0;
            while (bookingResult.next()) {
                count++;

                Date bookingDate = dateFormat.parse(bookingResult.getString("booking_date"));
                Date checkInDate = dateFormat.parse(bookingResult.getString("check_in_date"));
                Date checkOutDate = dateFormat.parse(bookingResult.getString("check_out_date"));

                String FormatedBookingDate = newDateFormat.format(bookingDate);
                String FormatedCheckInDate = newDateFormat.format(checkInDate);
                String FormatedCheckOutDateDate = newDateFormat.format(checkOutDate);

                LocalDate localCheckInDate = checkInDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate localCheckOutDate = checkOutDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                long daysBetween = ChronoUnit.DAYS.between(localCheckInDate, localCheckOutDate);

                Vector<String> bookingVector = new Vector<>();

                bookingVector.add(String.valueOf(count));
                bookingVector.add(bookingResult.getString("room_number"));
                bookingVector.add(FormatedBookingDate);
                bookingVector.add(FormatedCheckInDate);
                bookingVector.add(FormatedCheckOutDateDate);
                bookingVector.add(bookingResult.getString("guest_count"));
                bookingVector.add(bookingResult.getString("Price"));
                bookingVector.add(String.valueOf(daysBetween));
                bookingVector.add(bookingResult.getString("total_amount"));

                detailsTableModel.addRow(bookingVector);

                totalAmount += Double.parseDouble(bookingResult.getString("total_amount"));

            }

            totalAmountLabel.setText(new DecimalFormat("#").format(totalAmount));

            double paidAmount = 0.00;
            calculateBalance(totalAmount, paidAmount);

        } catch (Exception e) {
            Log.log.warning(e.toString());
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        roundedPanel1 = new Component.RoundedPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        bookingDetailsTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        customerNICTextField = new Component.RoundedTextField();
        jLabel5 = new javax.swing.JLabel();
        clearButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        pendingPaymentsCustomerTable = new javax.swing.JTable();
        roundedPanel2 = new Component.RoundedPanel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        totalAmountLabel = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        paidAmountTextField = new Component.RoundedTextField();
        jLabel12 = new javax.swing.JLabel();
        balanceLabel = new javax.swing.JLabel();
        confirmPaymentButton = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        CustomerNicTextField_02 = new Component.RoundedTextField();
        clearCompletePaymentButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        completePaymentsTable = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        completeTotalAmountLabel = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        completePaidAmountLabel = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        completeBalanceLabel = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        customerNameLabel = new javax.swing.JLabel();
        customerMobileLabel = new javax.swing.JLabel();
        customerEmailLabel = new javax.swing.JLabel();
        customerNationalityLabel = new javax.swing.JLabel();
        customerRegisteredDateLabel = new javax.swing.JLabel();
        printColmpleteAllPaymentsButton = new javax.swing.JButton();
        customerNationalityLabel1 = new javax.swing.JLabel();

        jLabel3.setText("jLabel3");

        jLabel4.setText("jLabel4");

        jLabel8.setText("jLabel8");

        roundedPanel1.setBackground(new java.awt.Color(24, 24, 24));

        jTabbedPane1.setBackground(new java.awt.Color(24, 24, 24));

        jPanel1.setBackground(new java.awt.Color(24, 24, 24));

        bookingDetailsTable.setBackground(new java.awt.Color(29, 29, 29));
        bookingDetailsTable.setForeground(new java.awt.Color(204, 204, 204));
        bookingDetailsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Room Number", "Booking Date", "Check in Date", "Check out Date", "Guest Count", "Price Per Day", "Days", "Total"
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
        bookingDetailsTable.setInheritsPopupMenu(true);
        bookingDetailsTable.setRowHeight(23);
        bookingDetailsTable.setShowGrid(true);
        bookingDetailsTable.setSurrendersFocusOnKeystroke(true);
        jScrollPane1.setViewportView(bookingDetailsTable);
        if (bookingDetailsTable.getColumnModel().getColumnCount() > 0) {
            bookingDetailsTable.getColumnModel().getColumn(0).setMinWidth(25);
            bookingDetailsTable.getColumnModel().getColumn(0).setPreferredWidth(25);
            bookingDetailsTable.getColumnModel().getColumn(0).setMaxWidth(25);
        }

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 204, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText(" PENDING PAYMENTS");

        jLabel2.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 153, 153));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/info.png"))); // NOI18N
        jLabel2.setText("You can view the booking details by clicking pending payments.");

        customerNICTextField.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        customerNICTextField.setMinimumSize(new java.awt.Dimension(68, 30));
        customerNICTextField.setPreferredSize(new java.awt.Dimension(117, 30));
        customerNICTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                customerNICTextFieldKeyReleased(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(153, 153, 153));
        jLabel5.setText(" CUSTOMER NIC NUMBER");

        clearButton.setBackground(new java.awt.Color(102, 102, 102));
        clearButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/Clear.png"))); // NOI18N
        clearButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("BOOKING DETAILS");

        pendingPaymentsCustomerTable.setBackground(new java.awt.Color(29, 29, 29));
        pendingPaymentsCustomerTable.setForeground(new java.awt.Color(255, 255, 255));
        pendingPaymentsCustomerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Customer Name", "Customer NIC", "Mobile Number", "Registered Date", "Nationality"
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
        pendingPaymentsCustomerTable.setInheritsPopupMenu(true);
        pendingPaymentsCustomerTable.setRowHeight(23);
        pendingPaymentsCustomerTable.setShowGrid(true);
        pendingPaymentsCustomerTable.setSurrendersFocusOnKeystroke(true);
        pendingPaymentsCustomerTable.getTableHeader().setReorderingAllowed(false);
        pendingPaymentsCustomerTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pendingPaymentsCustomerTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(pendingPaymentsCustomerTable);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(204, 204, 204));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Booking Payment Summary");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(204, 204, 204));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Total Amount :");

        totalAmountLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        totalAmountLabel.setForeground(new java.awt.Color(204, 204, 204));
        totalAmountLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalAmountLabel.setText("0.00");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(204, 204, 204));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Paid Amount :");

        paidAmountTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        paidAmountTextField.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        paidAmountTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                paidAmountTextFieldKeyReleased(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(204, 204, 204));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Balance :");

        balanceLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        balanceLabel.setForeground(new java.awt.Color(74, 156, 85));
        balanceLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        balanceLabel.setText("0.00");

        confirmPaymentButton.setBackground(new java.awt.Color(74, 156, 85));
        confirmPaymentButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        confirmPaymentButton.setForeground(new java.awt.Color(255, 255, 255));
        confirmPaymentButton.setText("Confirm Payment");
        confirmPaymentButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        confirmPaymentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmPaymentButtonActionPerformed(evt);
            }
        });

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/Payment-Section-Icon.png"))); // NOI18N

        javax.swing.GroupLayout roundedPanel2Layout = new javax.swing.GroupLayout(roundedPanel2);
        roundedPanel2.setLayout(roundedPanel2Layout);
        roundedPanel2Layout.setHorizontalGroup(
            roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, roundedPanel2Layout.createSequentialGroup()
                        .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(paidAmountTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(totalAmountLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, roundedPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(balanceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jSeparator2)
                    .addGroup(roundedPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(confirmPaymentButton)))
                .addContainerGap())
        );
        roundedPanel2Layout.setVerticalGroup(
            roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(totalAmountLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(paidAmountTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(balanceLabel))
                .addGap(18, 18, 18)
                .addComponent(confirmPaymentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 845, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(roundedPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(customerNICTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(customerNICTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(2, 2, 2)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                .addGap(27, 27, 27)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(roundedPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Pending Payments", jPanel1);

        jPanel2.setBackground(new java.awt.Color(24, 24, 24));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("COMPLETE PAYMENTS");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(153, 153, 153));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText(" CUSTOMER NIC NUMBER");

        CustomerNicTextField_02.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        CustomerNicTextField_02.setMinimumSize(new java.awt.Dimension(68, 30));
        CustomerNicTextField_02.setPreferredSize(new java.awt.Dimension(117, 30));
        CustomerNicTextField_02.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                CustomerNicTextField_02KeyReleased(evt);
            }
        });

        clearCompletePaymentButton.setBackground(new java.awt.Color(102, 102, 102));
        clearCompletePaymentButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/Clear.png"))); // NOI18N
        clearCompletePaymentButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clearCompletePaymentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearCompletePaymentButtonActionPerformed(evt);
            }
        });

        completePaymentsTable.setBackground(new java.awt.Color(29, 29, 29));
        completePaymentsTable.setForeground(new java.awt.Color(204, 204, 204));
        completePaymentsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Booking Number", "Room Number", "Booking Date", "Check in Date", "Check out date", "Guest Count", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        completePaymentsTable.setInheritsPopupMenu(true);
        completePaymentsTable.setRowHeight(23);
        completePaymentsTable.setShowGrid(true);
        completePaymentsTable.setSurrendersFocusOnKeystroke(true);
        jScrollPane3.setViewportView(completePaymentsTable);

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(204, 204, 204));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("All Total Amount :");

        completeTotalAmountLabel.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        completeTotalAmountLabel.setForeground(new java.awt.Color(204, 204, 204));
        completeTotalAmountLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        completeTotalAmountLabel.setText("0.00");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(204, 204, 204));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("All Paid Amount :");

        completePaidAmountLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        completePaidAmountLabel.setForeground(new java.awt.Color(204, 204, 204));
        completePaidAmountLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        completePaidAmountLabel.setText("0.00");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(74, 156, 85));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("All Balance :");

        completeBalanceLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        completeBalanceLabel.setForeground(new java.awt.Color(74, 156, 85));
        completeBalanceLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        completeBalanceLabel.setText("0.00");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(153, 153, 153));
        jLabel22.setText(" Customer Name   : ");

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(153, 153, 153));
        jLabel23.setText(" Mobile                  : ");

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(153, 153, 153));
        jLabel24.setText(" Registered Date   : ");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(153, 153, 153));
        jLabel25.setText(" Email Address      : ");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(153, 153, 153));
        jLabel26.setText(" Nationality           : ");

        customerNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        customerNameLabel.setText("None");

        customerMobileLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        customerMobileLabel.setText("None");

        customerEmailLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        customerEmailLabel.setText("None");

        customerNationalityLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        customerNationalityLabel.setText("None");

        customerRegisteredDateLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        customerRegisteredDateLabel.setText("None");

        printColmpleteAllPaymentsButton.setBackground(new java.awt.Color(51, 51, 51));
        printColmpleteAllPaymentsButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        printColmpleteAllPaymentsButton.setForeground(new java.awt.Color(255, 255, 255));
        printColmpleteAllPaymentsButton.setText(" Print Details");
        printColmpleteAllPaymentsButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        printColmpleteAllPaymentsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printColmpleteAllPaymentsButtonActionPerformed(evt);
            }
        });

        customerNationalityLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        customerNationalityLabel1.setText(" PAYMENTS DETAILS");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(customerEmailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(customerMobileLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(customerRegisteredDateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(customerNationalityLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(customerNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 340, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(CustomerNicTextField_02, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(clearCompletePaymentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(customerNationalityLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(printColmpleteAllPaymentsButton))
                            .addComponent(jScrollPane3)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel18)
                                            .addComponent(jLabel16))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(completeTotalAmountLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(completePaidAmountLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(completeBalanceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(5, 5, 5)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CustomerNicTextField_02, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(clearCompletePaymentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(customerNameLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(customerMobileLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(customerEmailLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(customerRegisteredDateLabel))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26)
                    .addComponent(customerNationalityLabel))
                .addGap(13, 13, 13)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(customerNationalityLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(printColmpleteAllPaymentsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(completeTotalAmountLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(completePaidAmountLabel))
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(completeBalanceLabel))
                .addGap(11, 11, 11))
        );

        jTabbedPane1.addTab("Complete Payments", jPanel2);

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

    private void customerNICTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_customerNICTextFieldKeyReleased

        String customerNic = customerNICTextField.getText();

        loadPendingPayments(customerNic);
//         loadBookingDetails(customerNic);
//         totalAmountLabel.setText("None");
    }//GEN-LAST:event_customerNICTextFieldKeyReleased

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        clear();
    }//GEN-LAST:event_clearButtonActionPerformed

    private void pendingPaymentsCustomerTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pendingPaymentsCustomerTableMouseClicked

        if (evt.getClickCount() == 2) {

            String customerNic = String.valueOf(pendingPaymentsCustomerTable.getValueAt(pendingPaymentsCustomerTable.getSelectedRow(), 1));

            loadBookingDetails(customerNic);

        }
    }//GEN-LAST:event_pendingPaymentsCustomerTableMouseClicked

    private void confirmPaymentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmPaymentButtonActionPerformed
        if (bookingDetailsTable.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Payment cannot be confirmed due to lack of details", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            String stringPaidAmount = paidAmountTextField.getText();
            String stringTotalAmount = totalAmountLabel.getText();
            String stringBalance = balanceLabel.getText();

            if (stringPaidAmount.isEmpty()) {

                JOptionPane.showMessageDialog(this, "The payment cannot be confirmed because the paid amount is empty", "Warning", JOptionPane.WARNING_MESSAGE);

            } else {

                if (stringPaidAmount.matches("^[0-9]+$")) {

                    double paidAmount = Double.parseDouble(stringPaidAmount);
                    double totalAmount = Double.parseDouble(stringTotalAmount);

                    if (paidAmount < totalAmount) {
                        JOptionPane.showMessageDialog(this, "Payment cannot be made as the amount paid is less than the total amount.", "Warning", JOptionPane.WARNING_MESSAGE);
                    } else {

                        String customerNIC = String.valueOf(pendingPaymentsCustomerTable.getValueAt(pendingPaymentsCustomerTable.getSelectedRow(), 1));
                        String customerName = String.valueOf(pendingPaymentsCustomerTable.getValueAt(pendingPaymentsCustomerTable.getSelectedRow(), 0));

                        Random random = new Random();
                        int bookingNumber = random.nextInt(90000000) + 10000000;

                        Date date = new Date();
                        String paymentDate = new SimpleDateFormat("yyyy-MM-dd").format(date);

                        try {

                            ResultSet bookingDetails = MySQL.execute("SELECT * FROM `booking` WHERE `users_NIC`='" + customerNIC + "' AND `payment_status_payment_status_id`='1'");

                            while (bookingDetails.next()) {

                                MySQL.execute("INSERT INTO `payments` (`booking_booking_id`,`booking_number`,`total_amount`,`paid_amount`,`payment_date`) "
                                        + "VALUES('" + bookingDetails.getString("booking_id") + "','" + bookingNumber + "','" + totalAmount + "','" + paidAmount + "','" + paymentDate + "')");

                                MySQL.execute("UPDATE `booking` SET `payment_status_payment_status_id`='2' WHERE `booking_id`='" + bookingDetails.getString("booking_id") + "'");

                            }

                            HashMap<String, Object> parameters = new HashMap<>();
                            String filePath = "src//Reports/RoomBookingInvoice.jasper";

                            JRDataSource bookingData = new JRTableModelDataSource(bookingDetailsTable.getModel());
                            String dateTime = new SimpleDateFormat("yyyy-MMM-dd  hh:mm:ss aaa").format(date);

                            ResultSet customerDetails = MySQL.execute("SELECT `mobile` FROM `users` WHERE `NIC`='" + customerNIC + "' AND `user_type_user_type_id`='1'");

                            String customerMobile = "";

                            if (customerDetails.next()) {
                                customerMobile = customerDetails.getString("mobile");
                            }

                            DecimalFormat priceFormat = new DecimalFormat("#,##0.00");

                            String formatedPaidAmount = priceFormat.format(paidAmount);
                            String formatedTotalAmount = priceFormat.format(totalAmount);
                            String formatedBalance = priceFormat.format(Double.parseDouble(stringBalance));

                            parameters.put("InvoiceNumber", "Invoice# " + bookingNumber);
                            parameters.put("DateTime", dateTime);
                            parameters.put("CustomerName", customerName);
                            parameters.put("CustomerMobile", customerMobile);
                            parameters.put("Total", formatedTotalAmount);
                            parameters.put("PaidAmount", formatedPaidAmount);
                            parameters.put("Balance", formatedBalance);

                            JasperPrint invoice = JasperFillManager.fillReport(filePath, parameters, bookingData);
                            JasperViewer viewer = new JasperViewer(invoice, false);

                            viewer.setVisible(true);

                            viewer.addWindowListener(new WindowAdapter() {
                                @Override
                                public void windowClosed(WindowEvent e) {
                                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.BOTTOM_CENTER, "Payment successful");
                                    clear();
                                }
                            });

                        } catch (Exception e) {
                            Log.log.warning(e.toString());
                        }

                    }

                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_RIGHT, "Invalid input: Please enter a valid number.");

                }

            }
        }
    }//GEN-LAST:event_confirmPaymentButtonActionPerformed

    private void paidAmountTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_paidAmountTextFieldKeyReleased

        String stringTotalAmount = totalAmountLabel.getText();
        String stringPaidAmount = paidAmountTextField.getText();

        double totalAmont = Double.parseDouble(stringTotalAmount);
        double paidAmount = 0.00;

        if (stringPaidAmount.isEmpty() && !stringTotalAmount.equals("0.00")) {

            calculateBalance(totalAmont, paidAmount);

        } else if (!stringTotalAmount.equals("0.00") && stringPaidAmount.matches("^[0-9]+$")) {

            paidAmount += Double.parseDouble(stringPaidAmount);
            calculateBalance(totalAmont, paidAmount);
        }

    }//GEN-LAST:event_paidAmountTextFieldKeyReleased

//    Complete Payment
    private void clearCompletePayments(DefaultTableModel completePaymentTableModel) {

        customerNameLabel.setText("");
        customerMobileLabel.setText("");
        customerEmailLabel.setText("");
        customerRegisteredDateLabel.setText("");
        customerNationalityLabel.setText("");
        completePaymentTableModel.setRowCount(0);
        completeTotalAmountLabel.setText("0.00");
        completePaidAmountLabel.setText("0.00");
        completeBalanceLabel.setText("0.00");

    }

    private void CustomerNicTextField_02KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CustomerNicTextField_02KeyReleased

        String customerNic = CustomerNicTextField_02.getText();

        try {

            ResultSet customerResult = MySQL.execute("SELECT * FROM `users` "
                    + "INNER JOIN `nationality` ON `users`.`nationality_nationality_id`=`nationality`.`nationality_id` WHERE `NIC`='" + customerNic + "' AND `user_type_user_type_id`='1'");

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MMM-dd");
            DefaultTableModel completePaymentTableModel = (DefaultTableModel) completePaymentsTable.getModel();
            if (customerResult.next()) {

                Date registerdDate = format.parse(customerResult.getString("registered_date"));

                customerNameLabel.setText(customerResult.getString("first_name") + " " + customerResult.getString("last_name"));
                customerMobileLabel.setText(customerResult.getString("mobile"));
                customerEmailLabel.setText(customerResult.getString("email"));
                customerRegisteredDateLabel.setText(newFormat.format(registerdDate));
                customerNationalityLabel.setText(customerResult.getString("nationality_name"));

                ResultSet bookingResult = MySQL.execute("SELECT * FROM `payments` "
                        + "INNER JOIN `booking` ON `payments`.`booking_booking_id`=`booking`.`booking_id` "
                        + "WHERE `booking`.`users_NIC`='" + customerNic + "' ORDER BY `booking_date` DESC");

                completePaymentTableModel.setRowCount(0);

                while (bookingResult.next()) {
                    Vector<String> completePaymentsVector = new Vector<>();

                    Date bookingDate = format.parse(bookingResult.getString("booking_date"));
                    Date checkInDate = format.parse(bookingResult.getString("check_in_date"));
                    Date checkOutDate = format.parse(bookingResult.getString("check_out_date"));

                    completePaymentsVector.add(bookingResult.getString("booking_number"));
                    completePaymentsVector.add(bookingResult.getString("rooms_room_number"));
                    completePaymentsVector.add(newFormat.format(bookingDate));
                    completePaymentsVector.add(newFormat.format(checkInDate));
                    completePaymentsVector.add(newFormat.format(checkOutDate));
                    completePaymentsVector.add(bookingResult.getString("guest_count"));
                    completePaymentsVector.add(bookingResult.getString("booking.total_amount"));

                    completePaymentTableModel.addRow(completePaymentsVector);

                }

                completePaymentCalculate(customerNic);

            } else {
                clearCompletePayments(completePaymentTableModel);
                completePaymentCalculate(customerNic);
            }

        } catch (Exception e) {
            Log.log.warning(e.toString());
        }

    }//GEN-LAST:event_CustomerNicTextField_02KeyReleased

    private void clearCompletePaymentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearCompletePaymentButtonActionPerformed

        DefaultTableModel completePaymentTableModel = (DefaultTableModel) completePaymentsTable.getModel();

        clearCompletePayments(completePaymentTableModel);
        CustomerNicTextField_02.setText("");
    }//GEN-LAST:event_clearCompletePaymentButtonActionPerformed

    private void printColmpleteAllPaymentsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printColmpleteAllPaymentsButtonActionPerformed

        if (customerNameLabel.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Can't print because there is no data", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            try {

                HashMap<String, Object> parameters = new HashMap<>();
                String filePath = "src//Reports/AllCompleteBokingReports.jasper";

                JRDataSource dataSource = new JRTableModelDataSource(completePaymentsTable.getModel());

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MMM-dd");
                SimpleDateFormat NewFormat = new SimpleDateFormat("yyyy-MMM-dd hh:mm:ss aaa");

                Date registeredDate = format.parse(customerRegisteredDateLabel.getText());

                parameters.put("DateTime", NewFormat.format(new Date()));
                parameters.put("CustomerName", customerNameLabel.getText());
                parameters.put("Mobile", customerMobileLabel.getText());
                parameters.put("Email", customerEmailLabel.getText());
                parameters.put("RegisteredDate", format.format(registeredDate));
                parameters.put("Nationality", customerNationalityLabel.getText());
                parameters.put("AllTotal", completeTotalAmountLabel.getText());
                parameters.put("AllPaidAmount", completePaidAmountLabel.getText());
                parameters.put("AllBalance", completeBalanceLabel.getText());

                JasperPrint report = JasperFillManager.fillReport(filePath, parameters, dataSource);
                JasperViewer.viewReport(report, false);

            } catch (Exception e) {
                Log.log.warning(e.toString());
            }

        }

    }//GEN-LAST:event_printColmpleteAllPaymentsButtonActionPerformed

    private void completePaymentCalculate(String customerNic) {

        try {
            ResultSet amountResult = MySQL.execute("SELECT DISTINCT `payments`.`booking_number`, `payments`.`total_amount`,`payments`.`paid_amount` FROM `payments` "
                    + "INNER JOIN `booking` ON `payments`.`booking_booking_id`=`booking`.`booking_id` "
                    + "INNER JOIN `users` ON `booking`.`users_NIC`=`users`.`NIC` "
                    + "WHERE `NIC`='" + customerNic + "' AND `user_type_user_type_id`='1' ");

            double total = 0.00;
            double paidAmount = 0.00;

            if (amountResult.isBeforeFirst()) {

                while (amountResult.next()) {
                    total += Double.parseDouble(amountResult.getString("total_amount"));
                    paidAmount += Double.parseDouble(amountResult.getString("paid_amount"));
                }

                double balance = paidAmount - total;

                completeTotalAmountLabel.setText(new DecimalFormat("#").format(total));
                completePaidAmountLabel.setText(new DecimalFormat("#").format(paidAmount));
                completeBalanceLabel.setText(new DecimalFormat("#").format(balance));

            } else {
                completeTotalAmountLabel.setText("0.00");
                completePaidAmountLabel.setText("0.00");
                completeBalanceLabel.setText("0.00");
            }

        } catch (Exception e) {
            Log.log.warning(e.toString());
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Component.RoundedTextField CustomerNicTextField_02;
    private javax.swing.JLabel balanceLabel;
    private javax.swing.JTable bookingDetailsTable;
    private javax.swing.JButton clearButton;
    private javax.swing.JButton clearCompletePaymentButton;
    private javax.swing.JLabel completeBalanceLabel;
    private javax.swing.JLabel completePaidAmountLabel;
    private javax.swing.JTable completePaymentsTable;
    private javax.swing.JLabel completeTotalAmountLabel;
    private javax.swing.JButton confirmPaymentButton;
    private javax.swing.JLabel customerEmailLabel;
    private javax.swing.JLabel customerMobileLabel;
    private Component.RoundedTextField customerNICTextField;
    private javax.swing.JLabel customerNameLabel;
    private javax.swing.JLabel customerNationalityLabel;
    private javax.swing.JLabel customerNationalityLabel1;
    private javax.swing.JLabel customerRegisteredDateLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private Component.RoundedTextField paidAmountTextField;
    private javax.swing.JTable pendingPaymentsCustomerTable;
    private javax.swing.JButton printColmpleteAllPaymentsButton;
    private Component.RoundedPanel roundedPanel1;
    private Component.RoundedPanel roundedPanel2;
    private javax.swing.JLabel totalAmountLabel;
    // End of variables declaration//GEN-END:variables
}
