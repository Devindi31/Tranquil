package gui;

import connection.MySQL;
import gui.dialog.SelectCustomer;
import gui.dialog.SelectRoom;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JLabel;
import raven.toast.Notifications;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import log.Log;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class RoomBookingSection extends javax.swing.JPanel {

    HashMap<String, String> bookinStatusMap;

    private final MainPanel mainPanel;

    /**
     * @param mainPanelSection
     */
    public RoomBookingSection(MainPanel mainPanelSection) {

        initComponents();
        bookinStatusMap = new HashMap<>();
        this.setBackground(new Color(255, 255, 255, 0));
        this.mainPanel = mainPanelSection;
        Notifications.getInstance().setJFrame(mainPanel);
        Date date = new Date();
        checkInDateChooser.setDate(date);
        checkOutDateChooser.setDate(date);
        updateBookingButton.setEnabled(false);
        deleteBookingButton.setEnabled(false);
        loadStatus();
        loadAllBookingDetails("", null);

    }

    private void clear() {

        roomNumberLabel.setText("None");
        Date date = new Date();
        checkInDateChooser.setDate(date);
        checkOutDateChooser.setDate(date);
        guestCountTextField.setText("");
        clearSelectedRoomDetails();
        paidAmountTextField.setText("");

    }

    private void clearSelectedRoomDetails() {

        Date date = new Date();

        viewDetailsRoomNumberLabel.setText("None");
        viewDetailsAcTypeLabel.setText("None");
        viewDetailsBedTypeLabel.setText("None");
        viewDetailsRoomStatusLabel.setText("None");
        viewDetailsPriceLabel.setText("None");
        roomBookingButton.setEnabled(true);
        updateBookingButton.setEnabled(false);
        deleteBookingButton.setEnabled(false);
        roomNumberLabel.setText("None");
        checkInDateChooser.setDate(date);
        checkOutDateChooser.setDate(date);
        guestCountTextField.setText("");
        selectRoomButton.setEnabled(true);
        paidAmountTextField.setText("");

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

    public void loadBooking(String customerNIC) {

        try {

            ResultSet bookingResult = MySQL.execute("SELECT * FROM `booking` "
                    + "INNER JOIN `users` ON `booking`.`users_NIC`=`users`.`NIC` "
                    + "INNER JOIN `rooms` ON `booking`.`rooms_room_number`=`rooms`.`room_number` "
                    + "INNER JOIN `room_category` ON `rooms`.`room_category_room_category_id`=`room_category`.`room_category_id`"
                    + "WHERE `users`.`NIC`='" + customerNIC + "' AND `payment_status_payment_status_id`='1'");

            DefaultTableModel bookingTableModel = (DefaultTableModel) bookingTable.getModel();
            bookingTableModel.setRowCount(0);

            double totalAmount = 0.00;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MMM-dd");

            while (bookingResult.next()) {

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

                bookingVector.add(bookingResult.getString("first_name") + " " + bookingResult.getString("last_name"));
                bookingVector.add(bookingResult.getString("room_number"));
                bookingVector.add(FormatedBookingDate);
                bookingVector.add(FormatedCheckInDate);
                bookingVector.add(FormatedCheckOutDateDate);
                bookingVector.add(bookingResult.getString("guest_count"));
                bookingVector.add(bookingResult.getString("Price"));
                bookingVector.add(String.valueOf(daysBetween));
                bookingVector.add(bookingResult.getString("total_amount"));

                bookingTableModel.addRow(bookingVector);

                totalAmount += Double.parseDouble(bookingResult.getString("total_amount"));

            }

            double paidAmount = 0.00;
            calculateBalance(totalAmount, paidAmount);

            totalAmountLabel.setText(new DecimalFormat("#").format(totalAmount));
            clearSelectedRoomDetails();

        } catch (Exception e) {
            Log.log.warning(e.toString());
        }

    }

    public JLabel getCustomerNicLabel() {
        return customerNicLabel;
    }

    public JLabel getCustomerNameLabel() {
        return customerNameLabel;
    }

    public JLabel getRoomNumberLabel() {
        return roomNumberLabel;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roundedPanel3 = new Component.RoundedPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        selectCustomerButton = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        customerNameLabel = new javax.swing.JLabel();
        customerNicLabel = new javax.swing.JLabel();
        selectRoomButton = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        roomNumberLabel = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        checkInDateChooser = new com.toedter.calendar.JDateChooser();
        jLabel98 = new javax.swing.JLabel();
        checkOutDateChooser = new com.toedter.calendar.JDateChooser();
        guestCountTextField = new Component.RoundedTextField();
        jLabel99 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        roomBookingButton = new javax.swing.JButton();
        deleteBookingButton = new javax.swing.JButton();
        updateBookingButton = new javax.swing.JButton();
        roundedPanel12 = new Component.RoundedPanel();
        jLabel101 = new javax.swing.JLabel();
        jLabel102 = new javax.swing.JLabel();
        jLabel103 = new javax.swing.JLabel();
        jLabel104 = new javax.swing.JLabel();
        jLabel105 = new javax.swing.JLabel();
        viewDetailsRoomNumberLabel = new javax.swing.JLabel();
        viewDetailsAcTypeLabel = new javax.swing.JLabel();
        viewDetailsBedTypeLabel = new javax.swing.JLabel();
        viewDetailsRoomStatusLabel = new javax.swing.JLabel();
        viewDetailsPriceLabel = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        balanceLabel = new javax.swing.JLabel();
        jLabel107 = new javax.swing.JLabel();
        paidAmountTextField = new Component.RoundedTextField();
        jLabel108 = new javax.swing.JLabel();
        jLabel109 = new javax.swing.JLabel();
        totalAmountLabel = new javax.swing.JLabel();
        confirmPaymentButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        bookingTable = new javax.swing.JTable();
        logInLabel = new javax.swing.JLabel();
        jLabel100 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        customerNameOrNIC = new Component.RoundedTextField();
        jLabel118 = new javax.swing.JLabel();
        printAllBookingDetailsButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        allBookingDetailsTable = new javax.swing.JTable();
        statusComboBox = new javax.swing.JComboBox<>();
        logInLabel1 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1012, 615));

        roundedPanel3.setBackground(new java.awt.Color(24, 24, 24));

        jTabbedPane1.setBackground(new java.awt.Color(24, 24, 24));

        jPanel1.setBackground(new java.awt.Color(24, 24, 24));

        jPanel3.setBackground(new java.awt.Color(24, 24, 24));

        selectCustomerButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        selectCustomerButton.setForeground(new java.awt.Color(255, 255, 255));
        selectCustomerButton.setText(" Select Customer");
        selectCustomerButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        selectCustomerButton.setPreferredSize(new java.awt.Dimension(143, 30));
        selectCustomerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectCustomerButtonActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(153, 153, 153));
        jLabel19.setText(" Customer NIC ");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(153, 153, 153));
        jLabel20.setText(" Customer Name");

        customerNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        customerNameLabel.setForeground(new java.awt.Color(255, 255, 255));
        customerNameLabel.setText("None");

        customerNicLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        customerNicLabel.setForeground(new java.awt.Color(255, 255, 255));
        customerNicLabel.setText("None");

        selectRoomButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        selectRoomButton.setForeground(new java.awt.Color(255, 255, 255));
        selectRoomButton.setText(" Select Room");
        selectRoomButton.setActionCommand("40");
        selectRoomButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        selectRoomButton.setPreferredSize(new java.awt.Dimension(123, 30));
        selectRoomButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectRoomButtonActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(153, 153, 153));
        jLabel23.setText("Room Number   :");

        roomNumberLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        roomNumberLabel.setForeground(new java.awt.Color(255, 255, 255));
        roomNumberLabel.setText("None");

        jLabel97.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel97.setForeground(new java.awt.Color(153, 153, 153));
        jLabel97.setText(" Check In Date");

        jLabel98.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel98.setForeground(new java.awt.Color(153, 153, 153));
        jLabel98.setText(" Check Out Date");

        guestCountTextField.setForeground(new java.awt.Color(255, 255, 255));
        guestCountTextField.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        guestCountTextField.setPreferredSize(new java.awt.Dimension(68, 30));
        guestCountTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guestCountTextFieldActionPerformed(evt);
            }
        });

        jLabel99.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel99.setForeground(new java.awt.Color(153, 153, 153));
        jLabel99.setText("Guest Count");

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(153, 153, 153));
        jLabel24.setText(":");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(153, 153, 153));
        jLabel25.setText(":");

        roomBookingButton.setBackground(java.awt.SystemColor.textHighlight);
        roomBookingButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        roomBookingButton.setForeground(new java.awt.Color(255, 255, 255));
        roomBookingButton.setText("  Book Room");
        roomBookingButton.setActionCommand("40");
        roomBookingButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        roomBookingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roomBookingButtonActionPerformed(evt);
            }
        });

        deleteBookingButton.setBackground(new java.awt.Color(51, 51, 51));
        deleteBookingButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        deleteBookingButton.setForeground(new java.awt.Color(255, 0, 0));
        deleteBookingButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/Delete.png"))); // NOI18N
        deleteBookingButton.setActionCommand("40");
        deleteBookingButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deleteBookingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBookingButtonActionPerformed(evt);
            }
        });

        updateBookingButton.setBackground(new java.awt.Color(51, 51, 51));
        updateBookingButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        updateBookingButton.setForeground(new java.awt.Color(255, 255, 255));
        updateBookingButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/Edit.png"))); // NOI18N
        updateBookingButton.setActionCommand("40");
        updateBookingButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        updateBookingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBookingButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(selectCustomerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(customerNicLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(customerNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(selectRoomButton, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(roomNumberLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(66, 66, 66)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(checkInDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel97, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel98, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkOutDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel99, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(290, 290, 290))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(guestCountTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(roomBookingButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteBookingButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateBookingButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(selectCustomerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel19)
                                    .addComponent(customerNicLabel)
                                    .addComponent(jLabel24)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(selectRoomButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel23)
                                    .addComponent(roomNumberLabel))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(customerNameLabel)
                            .addComponent(jLabel25)))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(deleteBookingButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(roomBookingButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(updateBookingButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel97, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel98, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel99, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(checkInDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(checkOutDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(guestCountTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        roundedPanel12.setBackground(new java.awt.Color(24, 24, 24));

        jLabel101.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel101.setForeground(new java.awt.Color(153, 153, 153));
        jLabel101.setText("Room Number ");

        jLabel102.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel102.setForeground(new java.awt.Color(153, 153, 153));
        jLabel102.setText("A/C Type ");

        jLabel103.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel103.setForeground(new java.awt.Color(153, 153, 153));
        jLabel103.setText("Bed Type ");

        jLabel104.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel104.setForeground(new java.awt.Color(153, 153, 153));
        jLabel104.setText("Room Status");

        jLabel105.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel105.setForeground(new java.awt.Color(102, 102, 102));
        jLabel105.setText("Price Per Day");

        viewDetailsRoomNumberLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        viewDetailsRoomNumberLabel.setForeground(new java.awt.Color(153, 153, 153));
        viewDetailsRoomNumberLabel.setText("None");

        viewDetailsAcTypeLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        viewDetailsAcTypeLabel.setForeground(new java.awt.Color(153, 153, 153));
        viewDetailsAcTypeLabel.setText("None");

        viewDetailsBedTypeLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        viewDetailsBedTypeLabel.setForeground(new java.awt.Color(153, 153, 153));
        viewDetailsBedTypeLabel.setText("None");

        viewDetailsRoomStatusLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        viewDetailsRoomStatusLabel.setForeground(new java.awt.Color(153, 153, 153));
        viewDetailsRoomStatusLabel.setText("None");

        viewDetailsPriceLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        viewDetailsPriceLabel.setForeground(new java.awt.Color(102, 102, 102));
        viewDetailsPriceLabel.setText("None");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(153, 153, 153));
        jLabel26.setText(":");

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(153, 153, 153));
        jLabel27.setText(":");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(153, 153, 153));
        jLabel28.setText(":");

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(153, 153, 153));
        jLabel29.setText(":");

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(153, 153, 153));
        jLabel30.setText(":");

        javax.swing.GroupLayout roundedPanel12Layout = new javax.swing.GroupLayout(roundedPanel12);
        roundedPanel12.setLayout(roundedPanel12Layout);
        roundedPanel12Layout.setHorizontalGroup(
            roundedPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel12Layout.createSequentialGroup()
                .addGroup(roundedPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(roundedPanel12Layout.createSequentialGroup()
                        .addGroup(roundedPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel101, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel102, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel103, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel104, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(roundedPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(roundedPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel105)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(roundedPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(viewDetailsPriceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(viewDetailsBedTypeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(viewDetailsAcTypeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(viewDetailsRoomNumberLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(viewDetailsRoomStatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        roundedPanel12Layout.setVerticalGroup(
            roundedPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel12Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(roundedPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel101)
                    .addComponent(viewDetailsRoomNumberLabel)
                    .addComponent(jLabel26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(roundedPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel102)
                    .addComponent(viewDetailsAcTypeLabel)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(roundedPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel103)
                    .addComponent(viewDetailsBedTypeLabel)
                    .addComponent(jLabel28))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(roundedPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel104)
                    .addComponent(viewDetailsRoomStatusLabel)
                    .addComponent(jLabel29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(roundedPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel105)
                    .addComponent(viewDetailsPriceLabel)
                    .addComponent(jLabel30))
                .addContainerGap())
        );

        balanceLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        balanceLabel.setForeground(new java.awt.Color(74, 156, 85));
        balanceLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        balanceLabel.setText("0.00");

        jLabel107.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel107.setForeground(new java.awt.Color(204, 204, 204));
        jLabel107.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel107.setText("Total Amount  :");

        paidAmountTextField.setForeground(new java.awt.Color(255, 255, 255));
        paidAmountTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        paidAmountTextField.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        paidAmountTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paidAmountTextFieldActionPerformed(evt);
            }
        });
        paidAmountTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                paidAmountTextFieldKeyReleased(evt);
            }
        });

        jLabel108.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel108.setForeground(new java.awt.Color(204, 204, 204));
        jLabel108.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel108.setText("Balance  :");

        jLabel109.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel109.setForeground(new java.awt.Color(204, 204, 204));
        jLabel109.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel109.setText("Paid Amount  :");

        totalAmountLabel.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        totalAmountLabel.setForeground(new java.awt.Color(204, 204, 204));
        totalAmountLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalAmountLabel.setText("0.00");

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

        bookingTable.setBackground(new java.awt.Color(29, 29, 29));
        bookingTable.setForeground(new java.awt.Color(255, 255, 255));
        bookingTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Customer Name", "Room Number", "Booking Date", "Check in Date", "Check out Date", "Guest Count", "Price Per Day", "Days", "Total"
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
        bookingTable.setInheritsPopupMenu(true);
        bookingTable.setRowHeight(23);
        bookingTable.setShowGrid(true);
        bookingTable.setSurrendersFocusOnKeystroke(true);
        bookingTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bookingTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(bookingTable);

        logInLabel.setBackground(new java.awt.Color(255, 255, 255));
        logInLabel.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        logInLabel.setForeground(new java.awt.Color(204, 204, 204));
        logInLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logInLabel.setText("ROOM BOOKING MANAGEMENT");

        jLabel100.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        jLabel100.setForeground(new java.awt.Color(153, 153, 153));
        jLabel100.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel100.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/info.png"))); // NOI18N
        jLabel100.setText(" Double click the table row to view the room details.");

        jLabel43.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel43.setText("CUSTOMER'S BOOKING DETAILS");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(roundedPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(911, 911, 911)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel107, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel109, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel108, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(paidAmountTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(confirmPaymentButton, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                                    .addComponent(totalAmountLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(balanceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel100, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(14, 14, 14))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel43)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(logInLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(logInLabel)
                .addGap(34, 34, 34)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel100)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(totalAmountLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(paidAmountTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel109, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(balanceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(confirmPaymentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel107)
                            .addGap(40, 40, 40)
                            .addComponent(jLabel108, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(roundedPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13))
        );

        jTabbedPane1.addTab("Room Booking", jPanel1);

        jPanel2.setBackground(new java.awt.Color(24, 24, 24));

        customerNameOrNIC.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        customerNameOrNIC.setPreferredSize(new java.awt.Dimension(120, 30));
        customerNameOrNIC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                customerNameOrNICKeyReleased(evt);
            }
        });

        jLabel118.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel118.setForeground(new java.awt.Color(153, 153, 153));
        jLabel118.setText("CUSTOMER NAME OR NIC NUMBER");

        printAllBookingDetailsButton.setBackground(new java.awt.Color(51, 51, 51));
        printAllBookingDetailsButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        printAllBookingDetailsButton.setForeground(new java.awt.Color(255, 255, 255));
        printAllBookingDetailsButton.setText("Print Details");
        printAllBookingDetailsButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        printAllBookingDetailsButton.setMaximumSize(new java.awt.Dimension(142, 31));
        printAllBookingDetailsButton.setMinimumSize(new java.awt.Dimension(142, 31));
        printAllBookingDetailsButton.setPreferredSize(new java.awt.Dimension(142, 31));
        printAllBookingDetailsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printAllBookingDetailsButtonActionPerformed(evt);
            }
        });

        clearButton.setBackground(new java.awt.Color(102, 102, 102));
        clearButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/Clear.png"))); // NOI18N
        clearButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clearButton.setMaximumSize(new java.awt.Dimension(30, 30));
        clearButton.setMinimumSize(new java.awt.Dimension(30, 30));
        clearButton.setPreferredSize(new java.awt.Dimension(30, 30));
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        allBookingDetailsTable.setBackground(new java.awt.Color(29, 29, 29));
        allBookingDetailsTable.setForeground(new java.awt.Color(255, 255, 255));
        allBookingDetailsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Customer Name", "Room Number", "Booking Date", "Check in Date", "Check out Date", "Guest Count", "Price Per Day", "Days", "Total", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        allBookingDetailsTable.setInheritsPopupMenu(true);
        allBookingDetailsTable.setRowHeight(23);
        allBookingDetailsTable.setShowGrid(true);
        allBookingDetailsTable.setSurrendersFocusOnKeystroke(true);
        jScrollPane4.setViewportView(allBookingDetailsTable);

        statusComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        statusComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusComboBoxActionPerformed(evt);
            }
        });

        logInLabel1.setBackground(new java.awt.Color(255, 255, 255));
        logInLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        logInLabel1.setForeground(new java.awt.Color(204, 204, 204));
        logInLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logInLabel1.setText("BOOKING DETAILS MANAGEMENT");

        jLabel42.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel42.setText("ALL BOOKING DETAILS");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(logInLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel42)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(printAllBookingDetailsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1603, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel118)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(customerNameOrNIC, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(statusComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(logInLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel118)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(clearButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(statusComboBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customerNameOrNIC, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(printAllBookingDetailsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("All Booking Details", jPanel2);

        javax.swing.GroupLayout roundedPanel3Layout = new javax.swing.GroupLayout(roundedPanel3);
        roundedPanel3.setLayout(roundedPanel3Layout);
        roundedPanel3Layout.setHorizontalGroup(
            roundedPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        roundedPanel3Layout.setVerticalGroup(
            roundedPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundedPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundedPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void selectCustomerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectCustomerButtonActionPerformed

        SelectCustomer selectCustomer = new SelectCustomer(mainPanel, true, this);
        selectCustomer.setLocationRelativeTo(mainPanel);
        selectCustomer.setVisible(true);

    }//GEN-LAST:event_selectCustomerButtonActionPerformed

    private void selectRoomButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectRoomButtonActionPerformed
        SelectRoom selectRoom = new SelectRoom(mainPanel, true, this);
        selectRoom.setLocationRelativeTo(mainPanel);
        selectRoom.setVisible(true);

    }//GEN-LAST:event_selectRoomButtonActionPerformed

    private void roomBookingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roomBookingButtonActionPerformed

        String customerNic = customerNicLabel.getText();
        String roomNumber = roomNumberLabel.getText();
        Date selectedDateIn = checkInDateChooser.getDate();
        Date selectedDateOut = checkOutDateChooser.getDate();
        String guestCount = guestCountTextField.getText();

        Date today = resetTime(new Date());

        Date selectedCheckInDate = null;
        Date selectedCheckOutDate = null;

        if (selectedDateIn != null && selectedDateOut != null) {
            selectedCheckInDate = resetTime(selectedDateIn);
            selectedCheckOutDate = resetTime(selectedDateOut);
        }

        if (customerNic.equals("None")) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Please Select Customer");
        } else if (roomNumber.equals("None")) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Please Select Room");
        } else if (selectedDateIn == null) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Please Choose Check In Date");
        } else if (selectedCheckInDate.before(today)) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Please Choose Valid Check In Date");
        } else if (selectedDateOut == null) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Please Choose Check Out Date");
        } else if (selectedCheckOutDate.before(today) || selectedCheckOutDate.equals(selectedCheckInDate)) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Please Choose Valid Check Out Date");
        } else if (guestCount.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Please Enter Guest Count");
        } else if (!guestCount.matches("^[0-9]+$")) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Invalid Guest Count");
        } else {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            try {

                ResultSet roomResult = MySQL.execute("SELECT * FROM `rooms` "
                        + "INNER JOIN `room_category` ON `rooms`.`room_category_room_category_id`=`room_category`.`room_category_id` WHERE `room_number`='" + roomNumber + "'");

                if (roomResult.next()) {

                    LocalDate localCheckInDate = selectedCheckInDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate localCheckOutDate = selectedCheckOutDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    long daysBetween = ChronoUnit.DAYS.between(localCheckInDate, localCheckOutDate);

                    String totalPrice = String.valueOf(Double.parseDouble(roomResult.getString("price")) * daysBetween);
                    String checkInDate = dateFormat.format(selectedDateIn);
                    String checkOutDate = dateFormat.format(selectedDateOut);
                    String bookingDate = dateFormat.format(new Date());

                    MySQL.execute("INSERT INTO `booking` (`users_NIC`,`rooms_room_number`,`booking_date`,`check_in_date`,`check_out_date`,`guest_count`,`total_amount`,`payment_status_payment_status_id`) "
                            + "VALUES('" + customerNic + "','" + roomNumber + "','" + bookingDate + "','" + checkInDate + "','" + checkOutDate + "','" + guestCount + "','" + totalPrice + "','1')");

                    MySQL.execute("UPDATE `rooms` SET `room_status_room_status_id`='2' WHERE `room_number`='" + roomNumber + "'");

                    loadBooking(customerNic);
                    loadAllBookingDetails("", null);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.BOTTOM_CENTER, "Room booking is successful");
                    clear();

                }

            } catch (Exception e) {
                Log.log.warning(e.toString());
            }

        }

    }//GEN-LAST:event_roomBookingButtonActionPerformed

    private void bookingTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bookingTableMouseClicked

        if (evt.getClickCount() == 2) {

            int selectedRow = bookingTable.getSelectedRow();

            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MMM-dd");

            String roomNumber = String.valueOf(bookingTable.getValueAt(selectedRow, 1));
            String guestCount = String.valueOf(bookingTable.getValueAt(selectedRow, 5));
            String selectedCheckInDate = String.valueOf(bookingTable.getValueAt(selectedRow, 3));
            String selectedCheckOutDate = String.valueOf(bookingTable.getValueAt(selectedRow, 4));

            Date checkInDate = null;
            Date checkOutDate = null;
            try {

                checkInDate = formatDate.parse(selectedCheckInDate);
                checkOutDate = formatDate.parse(selectedCheckOutDate);

            } catch (Exception e) {
                Log.log.warning(e.toString());
            }

            roomNumberLabel.setText(roomNumber);
            guestCountTextField.setText(guestCount);
            checkInDateChooser.setDate(checkInDate);
            checkOutDateChooser.setDate(checkOutDate);

            roomBookingButton.setEnabled(false);
            updateBookingButton.setEnabled(true);
            deleteBookingButton.setEnabled(true);
            selectRoomButton.setEnabled(false);

            try {

                ResultSet roomResults = MySQL.execute("SELECT * FROM `rooms` "
                        + "INNER JOIN `room_category` ON `rooms`.`room_category_room_category_id`=`room_category`.`room_category_id` "
                        + "INNER JOIN `room_status` ON `rooms`.`room_status_room_status_id`=`room_status`.`room_status_id` "
                        + "INNER JOIN `ac_type` ON `room_category`.`ac_type_ac_type_id`=`ac_type`.`ac_type_id` "
                        + "INNER JOIN `bed_type` ON `room_category`.`bed_type_bed_type_id`=`bed_type`.`bed_type_id` WHERE `room_number`='" + roomNumber + "'");

                if (roomResults.next()) {

                    viewDetailsRoomNumberLabel.setText(roomNumber);
                    viewDetailsAcTypeLabel.setText(roomResults.getString("ac_type_name"));
                    viewDetailsBedTypeLabel.setText(roomResults.getString("bed_type_name"));
                    viewDetailsRoomStatusLabel.setText(roomResults.getString("room_status_name"));
                    viewDetailsPriceLabel.setText(roomResults.getString("price"));

                }

            } catch (Exception e) {
                Log.log.warning(e.toString());
            }

        }

    }//GEN-LAST:event_bookingTableMouseClicked

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

    private void updateBookingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBookingButtonActionPerformed

        String customerNic = customerNicLabel.getText();
        String roomNumber = roomNumberLabel.getText();
        Date selectedDateIn = checkInDateChooser.getDate();
        Date selectedDateOut = checkOutDateChooser.getDate();
        String guestCount = guestCountTextField.getText();

        Date today = resetTime(new Date());

        Date selectedCheckInDate = null;
        Date selectedCheckOutDate = null;

        if (selectedDateIn != null && selectedDateOut != null) {
            selectedCheckInDate = resetTime(selectedDateIn);
            selectedCheckOutDate = resetTime(selectedDateOut);
        }

        if (selectedDateIn == null) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Please Choose Check In Date");
        } else if (selectedCheckInDate.before(today)) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Please Choose Valid Check In Date");
        } else if (selectedDateOut == null) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Please Choose Check Out Date");
        } else if (selectedCheckOutDate.before(today) || selectedCheckOutDate.equals(selectedCheckInDate)) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Please Choose Valid Check Out Date");
        } else if (guestCount.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Please Enter Guest Count");
        } else if (!guestCount.matches("^[0-9]+$")) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Invalid Guest Count");
        } else {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            try {

                ResultSet roomResult = MySQL.execute("SELECT * FROM `rooms` "
                        + "INNER JOIN `room_category` ON `rooms`.`room_category_room_category_id`=`room_category`.`room_category_id` WHERE `room_number`='" + roomNumber + "'");

                if (roomResult.next()) {

                    LocalDate localCheckInDate = selectedCheckInDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate localCheckOutDate = selectedCheckOutDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    long daysBetween = ChronoUnit.DAYS.between(localCheckInDate, localCheckOutDate);

                    String totalPrice = String.valueOf(Double.parseDouble(roomResult.getString("price")) * daysBetween);
                    String checkInDate = dateFormat.format(selectedDateIn);
                    String checkOutDate = dateFormat.format(selectedDateOut);

                    int confirm = JOptionPane.showConfirmDialog(this, "Do you want to update the Booking?", "Confirm Your Action", JOptionPane.YES_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        MySQL.execute("UPDATE `booking` SET `check_in_date`='" + checkInDate + "', `check_out_date`='" + checkOutDate + "', `guest_count`='" + guestCount + "', `total_amount`='" + totalPrice + "' "
                                + "WHERE `users_NIC`='" + customerNic + "' AND `rooms_room_number`='" + roomNumber + "' AND `payment_status_payment_status_id`='1'");

                        loadBooking(customerNic);
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.BOTTOM_CENTER, "Room update successful");
                        clear();
                    }

                }

            } catch (Exception e) {
                Log.log.warning(e.toString());
            }

        }

    }//GEN-LAST:event_updateBookingButtonActionPerformed

    private void deleteBookingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBookingButtonActionPerformed

        String customerNic = customerNicLabel.getText();
        String roomNumber = roomNumberLabel.getText();

        try {
            int confirm = JOptionPane.showConfirmDialog(this, "Do you want to delete the Booking?", "Confirm Your Action", JOptionPane.YES_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {

                String checkOutDate = new SimpleDateFormat("yyyy-MM-dd").format(checkOutDateChooser.getDate());

                ResultSet bookingResult = MySQL.execute("SELECT * FROM `booking` WHERE `users_NIC`='" + customerNic + "' AND `rooms_room_number`='" + roomNumber + "' AND `check_out_date`='" + checkOutDate + "' AND `payment_status_payment_status_id`='1'");

                if (bookingResult.next()) {

                    MySQL.execute("DELETE FROM `booking` WHERE `booking_id`='" + bookingResult.getString("booking_id") + "'");
                    MySQL.execute("UPDATE `rooms` SET `room_status_room_status_id`='1' WHERE `room_number`='" + roomNumber + "'");

                } else {

                    JOptionPane.showMessageDialog(this, "Booking Result Not Found", "Oops !", JOptionPane.ERROR_MESSAGE);

                }

                loadBooking(customerNic);
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.BOTTOM_CENTER, "The booking has been successfully removed.");
                clear();
            }

        } catch (Exception e) {
            Log.log.warning(e.toString());
        }


    }//GEN-LAST:event_deleteBookingButtonActionPerformed

    private void confirmPaymentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmPaymentButtonActionPerformed

        if (bookingTable.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Payment cannot be confirmed due to lack of details", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            String stringPaidAmount = paidAmountTextField.getText();
            String stringTotalAmount = totalAmountLabel.getText();
            String stringBalance = balanceLabel.getText();

            if (stringPaidAmount.isEmpty()) {

                JOptionPane.showMessageDialog(this, "The payment cannot be confirmed because the paid amount is empty", "Warning", JOptionPane.WARNING_MESSAGE);

            } else {
                try {

                    if (stringPaidAmount.matches("^[0-9]+$")) {
                    double paidAmount = Double.parseDouble(stringPaidAmount);
                    double totalAmount = Double.parseDouble(stringTotalAmount);

                    if (paidAmount < totalAmount) {
                        JOptionPane.showMessageDialog(this, "Payment cannot be made as the amount paid is less than the total amount", "Warning", JOptionPane.WARNING_MESSAGE);
                    } else {

                        String customerNIC = customerNicLabel.getText();
                        String customerName = customerNameLabel.getText();

                        Random random = new Random();
                        int bookingNumber = random.nextInt(90000000) + 10000000;
                        Date date = new Date();

                        String paymentDate = new SimpleDateFormat("yyyy-MM-dd").format(date);

                        ResultSet bookingDetails = MySQL.execute("SELECT * FROM `booking` WHERE `users_NIC`='" + customerNIC + "' AND `payment_status_payment_status_id`='1'");

                        while (bookingDetails.next()) {

                            MySQL.execute("INSERT INTO `payments` (`booking_booking_id`,`booking_number`,`total_amount`,`paid_amount`,`payment_date`) "
                                    + "VALUES('" + bookingDetails.getString("booking_id") + "','" + bookingNumber + "','" + totalAmount + "','" + paidAmount + "','" + paymentDate + "')");

                            MySQL.execute("UPDATE `booking` SET `payment_status_payment_status_id`='2' WHERE `booking_id`='" + bookingDetails.getString("booking_id") + "'");

                        }

                        HashMap<String, Object> parameters = new HashMap<>();
                        String filePath = "src//Reports/RoomBookingInvoice.jasper";

                        JRDataSource bookingData = new JRTableModelDataSource(bookingTable.getModel());
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
                                loadBooking(customerNIC);
                                loadAllBookingDetails("", null);
                            }
                        });

                    }

                    } else {
                        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_RIGHT, "Invalid input: Please enter a valid number.");

                    }
                } catch (Exception e) {
                    Log.log.warning(e.toString());
                }
            }

        }

    }//GEN-LAST:event_confirmPaymentButtonActionPerformed

//    AllBookingProcess Start
    private void clearAllBooking() {

        customerNameOrNIC.setText("");
        statusComboBox.setSelectedIndex(0);
        allBookingDetailsTable.clearSelection();
        loadAllBookingDetails("", null);

    }

    private void filter() {

        String customerNameOrNic = customerNameOrNIC.getText();
        String status = bookinStatusMap.get(String.valueOf(statusComboBox.getSelectedItem()));

        loadAllBookingDetails(customerNameOrNic, status);

    }

    private void customerNameOrNICKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_customerNameOrNICKeyReleased
        filter();
    }//GEN-LAST:event_customerNameOrNICKeyReleased

    private void statusComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusComboBoxActionPerformed
        filter();
    }//GEN-LAST:event_statusComboBoxActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        clearAllBooking();
    }//GEN-LAST:event_clearButtonActionPerformed

    private void printAllBookingDetailsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printAllBookingDetailsButtonActionPerformed

        try {

            HashMap<String, Object> parameters = new HashMap<>();
            String filePath = "src//Reports/AllBookingReports.jasper";

            JRDataSource allBookingTableDataSource = new JRTableModelDataSource(allBookingDetailsTable.getModel());
            String datetime = new SimpleDateFormat("yyyy MMM dd - hh:mm:ss aaa").format(new Date());

            ResultSet counts = MySQL.execute("SELECT COUNT(*) AS allBookingCount, "
                    + "COUNT(CASE WHEN `payment_status_payment_status_id` = '1' THEN 1 END) AS pendingBookingCount, "
                    + "COUNT(CASE WHEN `payment_status_payment_status_id` = '2' THEN 1 END) AS completeBookingCount FROM `booking`");

            if (counts.next()) {

                parameters.put("DateTime", datetime);
                parameters.put("TotalBooking", counts.getString("allBookingCount"));
                parameters.put("PendingBooking", counts.getString("pendingBookingCount"));
                parameters.put("CompleteBooking", counts.getString("completeBookingCount"));

            }

            JasperPrint allBookingReport = JasperFillManager.fillReport(filePath, parameters, allBookingTableDataSource);
            JasperViewer.viewReport(allBookingReport, false);

        } catch (Exception e) {
            Log.log.warning(e.toString());
        }

    }//GEN-LAST:event_printAllBookingDetailsButtonActionPerformed

    private void guestCountTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guestCountTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_guestCountTextFieldActionPerformed

    private void paidAmountTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paidAmountTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_paidAmountTextFieldActionPerformed

    private void loadStatus() {

        try {

            ResultSet statusResult = MySQL.execute("SELECT * FROM `payment_status`");

            Vector<String> statusVector = new Vector<>();
            statusVector.add("Select Status");

            while (statusResult.next()) {

                statusVector.add(statusResult.getString("payment_status_name"));
                bookinStatusMap.put(statusResult.getString("payment_status_name"), statusResult.getString("payment_status_id"));

            }

            statusComboBox.setModel(new DefaultComboBoxModel<>(statusVector));

        } catch (Exception e) {
            Log.log.warning(e.toString());
        }

    }

    private void loadAllBookingDetails(String customerNameOrNic, String status) {

        try {

            String query = "SELECT * FROM `booking` "
                    + "INNER JOIN `users` ON `booking`.`users_NIC`=`users`.`NIC` "
                    + "INNER JOIN `rooms` ON `booking`.`rooms_room_number`=`rooms`.`room_number` "
                    + "INNER JOIN `room_category` ON `rooms`.`room_category_room_category_id`=`room_category`.`room_category_id` "
                    + "INNER JOIN `payment_status` ON `booking`.`payment_status_payment_status_id`=`payment_status`.`payment_status_id`";

            if (status != null) {
                query += "WHERE (`users`.`NIC` LIKE '%" + customerNameOrNic + "%' OR `first_name` LIKE '%" + customerNameOrNic + "%' OR `last_name` LIKE '%" + customerNameOrNic + "%') AND `payment_status_payment_status_id`='" + status + "'";

            } else {
                query += "WHERE `users`.`NIC` LIKE '%" + customerNameOrNic + "%' OR `first_name` LIKE '%" + customerNameOrNic + "%' OR `last_name` LIKE '%" + customerNameOrNic + "%'";

            }

            ResultSet bookingDetails = MySQL.execute(query);

            DefaultTableModel bookingTableModel = (DefaultTableModel) allBookingDetailsTable.getModel();
            bookingTableModel.setRowCount(0);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MMM-dd");

            while (bookingDetails.next()) {

                Vector<String> bookingVector = new Vector<>();

                Date bookingDate = dateFormat.parse(bookingDetails.getString("booking_date"));
                Date checkInDate = dateFormat.parse(bookingDetails.getString("check_in_date"));
                Date checkOutDate = dateFormat.parse(bookingDetails.getString("check_out_date"));

                String FormatedBookingDate = newDateFormat.format(bookingDate);
                String FormatedCheckInDate = newDateFormat.format(checkInDate);
                String FormatedCheckOutDateDate = newDateFormat.format(checkOutDate);

                LocalDate localCheckInDate = checkInDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate localCheckOutDate = checkOutDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                long daysBetween = ChronoUnit.DAYS.between(localCheckInDate, localCheckOutDate);

                bookingVector.add(bookingDetails.getString("first_name") + " " + bookingDetails.getString("last_name"));
                bookingVector.add(bookingDetails.getString("room_number"));
                bookingVector.add(FormatedBookingDate);
                bookingVector.add(FormatedCheckInDate);
                bookingVector.add(FormatedCheckOutDateDate);
                bookingVector.add(bookingDetails.getString("guest_count"));
                bookingVector.add(bookingDetails.getString("price"));
                bookingVector.add(String.valueOf(daysBetween));
                bookingVector.add(bookingDetails.getString("total_amount"));
                bookingVector.add(bookingDetails.getString("payment_status_name"));

                bookingTableModel.addRow(bookingVector);

            }

        } catch (Exception e) {
            Log.log.warning(e.toString());
        }

    }

    private Date resetTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable allBookingDetailsTable;
    private javax.swing.JLabel balanceLabel;
    private javax.swing.JTable bookingTable;
    private com.toedter.calendar.JDateChooser checkInDateChooser;
    private com.toedter.calendar.JDateChooser checkOutDateChooser;
    private javax.swing.JButton clearButton;
    private javax.swing.JButton confirmPaymentButton;
    private javax.swing.JLabel customerNameLabel;
    private Component.RoundedTextField customerNameOrNIC;
    private javax.swing.JLabel customerNicLabel;
    private javax.swing.JButton deleteBookingButton;
    private Component.RoundedTextField guestCountTextField;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel logInLabel;
    private javax.swing.JLabel logInLabel1;
    private Component.RoundedTextField paidAmountTextField;
    private javax.swing.JButton printAllBookingDetailsButton;
    private javax.swing.JButton roomBookingButton;
    private javax.swing.JLabel roomNumberLabel;
    private Component.RoundedPanel roundedPanel12;
    private Component.RoundedPanel roundedPanel3;
    private javax.swing.JButton selectCustomerButton;
    private javax.swing.JButton selectRoomButton;
    private javax.swing.JComboBox<String> statusComboBox;
    private javax.swing.JLabel totalAmountLabel;
    private javax.swing.JButton updateBookingButton;
    private javax.swing.JLabel viewDetailsAcTypeLabel;
    private javax.swing.JLabel viewDetailsBedTypeLabel;
    private javax.swing.JLabel viewDetailsPriceLabel;
    private javax.swing.JLabel viewDetailsRoomNumberLabel;
    private javax.swing.JLabel viewDetailsRoomStatusLabel;
    // End of variables declaration//GEN-END:variables
}
