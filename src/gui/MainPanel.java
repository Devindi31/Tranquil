package gui;

import com.formdev.flatlaf.FlatLaf;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MainPanel extends javax.swing.JFrame {

    public MainPanel() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        init();
        loadPanel(new WelcomeScreen());
    }

    private void init() {

        ImageIcon icon = new ImageIcon(getClass().getResource("/images/icon/Icon.png"));
        this.setIconImage(icon.getImage());

        new Timer(0, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                Date date = new Date();

                mainPanelDateTimeLabel.setText(new SimpleDateFormat("MMM dd yyyy   hh:mm:ss aa").format(date));

            }
        }).start();

    }

    private void loadPanel(JPanel panel) {

        if (!panel.isShowing()) {
            changePanel.removeAll();
            changePanel.add(panel, BorderLayout.CENTER);
            FlatLaf.updateUI();
        }

    }

    public JLabel getUserNameAndPositionLabel() {
        return userNameAndPositionLabel;
    }

    public JButton getEmployeeSectionButton() {
        return employeeSectionButton;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        roundedPanel2 = new Component.RoundedPanel();
        logOutButton = new javax.swing.JButton();
        homeButton = new javax.swing.JButton();
        customerSectionButton = new javax.swing.JButton();
        bookingSectionButton = new javax.swing.JButton();
        paymentSectionButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        roomSectionButton = new javax.swing.JButton();
        employeeSectionButton = new javax.swing.JButton();
        reportsSectionButton = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        mainPanelDateTimeLabel = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        userNameAndPositionLabel = new javax.swing.JLabel();
        dateTimeLabel = new javax.swing.JLabel();
        changePanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tranquil");

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel6.setPreferredSize(new java.awt.Dimension(943, 60));

        roundedPanel2.setBackground(new java.awt.Color(51, 51, 51));

        logOutButton.setBackground(new java.awt.Color(51, 51, 51));
        logOutButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/Log-Out.png"))); // NOI18N
        logOutButton.setToolTipText("Log Out");
        logOutButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logOutButton.setPreferredSize(new java.awt.Dimension(35, 35));
        logOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logOutButtonActionPerformed(evt);
            }
        });

        homeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/Menu.png"))); // NOI18N
        homeButton.setToolTipText("Home");
        homeButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        homeButton.setPreferredSize(new java.awt.Dimension(35, 35));
        homeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                homeButtonActionPerformed(evt);
            }
        });

        customerSectionButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/Customer.png"))); // NOI18N
        customerSectionButton.setToolTipText("Customer Section");
        customerSectionButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        customerSectionButton.setMaximumSize(new java.awt.Dimension(34, 36));
        customerSectionButton.setMinimumSize(new java.awt.Dimension(34, 36));
        customerSectionButton.setPreferredSize(new java.awt.Dimension(35, 35));
        customerSectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customerSectionButtonActionPerformed(evt);
            }
        });

        bookingSectionButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/Booking.png"))); // NOI18N
        bookingSectionButton.setToolTipText("Room Booking Section");
        bookingSectionButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bookingSectionButton.setPreferredSize(new java.awt.Dimension(35, 35));
        bookingSectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bookingSectionButtonActionPerformed(evt);
            }
        });

        paymentSectionButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/Payment.png"))); // NOI18N
        paymentSectionButton.setToolTipText("Payment Section");
        paymentSectionButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        paymentSectionButton.setPreferredSize(new java.awt.Dimension(35, 35));
        paymentSectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paymentSectionButtonActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator2.setBackground(new java.awt.Color(51, 51, 51));
        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        roomSectionButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/Room-Management.png"))); // NOI18N
        roomSectionButton.setToolTipText("Room Management");
        roomSectionButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        roomSectionButton.setPreferredSize(new java.awt.Dimension(35, 35));
        roomSectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roomSectionButtonActionPerformed(evt);
            }
        });

        employeeSectionButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/Employee-Management.png"))); // NOI18N
        employeeSectionButton.setToolTipText("Employee Management");
        employeeSectionButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        employeeSectionButton.setPreferredSize(new java.awt.Dimension(35, 35));
        employeeSectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeeSectionButtonActionPerformed(evt);
            }
        });

        reportsSectionButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/Invoice.png"))); // NOI18N
        reportsSectionButton.setToolTipText("Reports");
        reportsSectionButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        reportsSectionButton.setPreferredSize(new java.awt.Dimension(35, 35));
        reportsSectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportsSectionButtonActionPerformed(evt);
            }
        });

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator5.setForeground(new java.awt.Color(51, 51, 51));
        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout roundedPanel2Layout = new javax.swing.GroupLayout(roundedPanel2);
        roundedPanel2.setLayout(roundedPanel2Layout);
        roundedPanel2Layout.setHorizontalGroup(
            roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(homeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 1, Short.MAX_VALUE)
                .addGap(216, 216, 216)
                .addComponent(customerSectionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(bookingSectionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(paymentSectionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(employeeSectionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roomSectionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(reportsSectionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(226, 226, 226)
                .addComponent(jSeparator5, javax.swing.GroupLayout.DEFAULT_SIZE, 1, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(logOutButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        roundedPanel2Layout.setVerticalGroup(
            roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3)
                    .addComponent(reportsSectionButton, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(customerSectionButton, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(homeButton, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(logOutButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bookingSectionButton, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(paymentSectionButton, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(jSeparator2)
                    .addComponent(roomSectionButton, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(employeeSectionButton, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(jSeparator4)
                    .addComponent(jSeparator5, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundedPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundedPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.add(jPanel6, java.awt.BorderLayout.PAGE_END);

        jPanel1.setLayout(new java.awt.BorderLayout(0, 5));

        jPanel2.setBackground(new java.awt.Color(44, 44, 44));
        jPanel2.setPreferredSize(new java.awt.Dimension(943, 30));

        mainPanelDateTimeLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        mainPanelDateTimeLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        mainPanelDateTimeLabel.setText("Date & Time");

        nameLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        nameLabel.setText("Name :");

        userNameAndPositionLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        userNameAndPositionLabel.setText("Login user Name and position");

        dateTimeLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        dateTimeLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        dateTimeLabel.setText("Date & Time :");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(userNameAndPositionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 211, Short.MAX_VALUE)
                .addComponent(dateTimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainPanelDateTimeLabel)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(mainPanelDateTimeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(dateTimeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(nameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(userNameAndPositionLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        changePanel.setMinimumSize(new java.awt.Dimension(943, 462));
        changePanel.setLayout(new java.awt.BorderLayout());
        jPanel1.add(changePanel, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel1, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void changeIcon(String home, String customer, String booking, String payment, String room, String employee, String report) {

        homeButton.setIcon(new ImageIcon(getClass().getResource("/images/icon/" + home)));
        customerSectionButton.setIcon(new ImageIcon(getClass().getResource("/images/icon/" + customer)));
        bookingSectionButton.setIcon(new ImageIcon(getClass().getResource("/images/icon/" + booking)));
        paymentSectionButton.setIcon(new ImageIcon(getClass().getResource("/images/icon/" + payment)));
        roomSectionButton.setIcon(new ImageIcon(getClass().getResource("/images/icon/" + room)));
        employeeSectionButton.setIcon(new ImageIcon(getClass().getResource("/images/icon/" + employee)));
        reportsSectionButton.setIcon(new ImageIcon(getClass().getResource("/images/icon/" + report)));

    }

    private void homeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homeButtonActionPerformed
        loadPanel(new WelcomeScreen());
        changeIcon("Menu-blue.png", "Customer.png", "Booking.png", "Payment.png", "Room-Management.png", "Employee-Management.png", "Invoice.png");
    }//GEN-LAST:event_homeButtonActionPerformed

    private void customerSectionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customerSectionButtonActionPerformed
        loadPanel(new CustomerSection(this));
        changeIcon("Menu.png", "Customer-blue.png", "Booking.png", "Payment.png", "Room-Management.png", "Employee-Management.png", "Invoice.png");
    }//GEN-LAST:event_customerSectionButtonActionPerformed

    private void bookingSectionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookingSectionButtonActionPerformed
        loadPanel(new RoomBookingSection(this));
        changeIcon("Menu.png", "Customer.png", "Booking-blue.png", "Payment.png", "Room-Management.png", "Employee-Management.png", "Invoice.png");
    }//GEN-LAST:event_bookingSectionButtonActionPerformed

    private void paymentSectionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paymentSectionButtonActionPerformed
        loadPanel(new PaymentSection());
        changeIcon("Menu.png", "Customer.png", "Booking.png", "Payment-blue.png", "Room-Management.png", "Employee-Management.png", "Invoice.png");
    }//GEN-LAST:event_paymentSectionButtonActionPerformed

    private void roomSectionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roomSectionButtonActionPerformed
        loadPanel(new RoomManagementSection(this));
        changeIcon("Menu.png", "Customer.png", "Booking.png", "Payment.png", "Room-Management-blue.png", "Employee-Management.png", "Invoice.png");
    }//GEN-LAST:event_roomSectionButtonActionPerformed

    private void employeeSectionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeeSectionButtonActionPerformed
        loadPanel(new EmployeeSection(this));
        changeIcon("Menu.png", "Customer.png", "Booking.png", "Payment.png", "Room-Management.png", "Employee-Management-blue.png", "Invoice.png");
    }//GEN-LAST:event_employeeSectionButtonActionPerformed

    private void reportsSectionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportsSectionButtonActionPerformed
        loadPanel(new ReportsSection());
        changeIcon("Menu.png", "Customer.png", "Booking.png", "Payment.png", "Room-Management.png", "Employee-Management.png", "Invoice-blue.png");
    }//GEN-LAST:event_reportsSectionButtonActionPerformed

    private void logOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logOutButtonActionPerformed
        this.dispose();
        new SignIn().setVisible(true);
    }//GEN-LAST:event_logOutButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainPanel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bookingSectionButton;
    private javax.swing.JPanel changePanel;
    private javax.swing.JButton customerSectionButton;
    private javax.swing.JLabel dateTimeLabel;
    private javax.swing.JButton employeeSectionButton;
    private javax.swing.JButton homeButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JButton logOutButton;
    private javax.swing.JLabel mainPanelDateTimeLabel;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JButton paymentSectionButton;
    private javax.swing.JButton reportsSectionButton;
    private javax.swing.JButton roomSectionButton;
    private Component.RoundedPanel roundedPanel2;
    private javax.swing.JLabel userNameAndPositionLabel;
    // End of variables declaration//GEN-END:variables
}
