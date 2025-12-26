package gui;

import connection.MySQL;
import gui.dialog.RoomRegistration;
import java.awt.Color;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
import raven.toast.Notifications;


public class RoomManagementSection extends javax.swing.JPanel {

    private final MainPanel mainPanelSection;

    HashMap<String, String> acTypeHashMap;
    HashMap<String, String> bedTypeHashMap;
    HashMap<String, String> statusHashMap;

    /**
     * @param mainPanel
     */
    public RoomManagementSection(MainPanel mainPanel) {

        initComponents();
        acTypeHashMap = new HashMap<>();
        bedTypeHashMap = new HashMap<>();
        statusHashMap = new HashMap<>();
        this.setBackground(new Color(255, 255, 255, 0));
        this.mainPanelSection = mainPanel;
        loadAcType();
        loadBedType();
        loadStatus();
        loadRooms("", null, null, null);
        loadStatusChangeRooms("");
        Notifications.getInstance().setJFrame(mainPanelSection);

    }

    private void clear() {

        roomNumberTextField.setText("");
        acTypeComboBox.setSelectedIndex(0);
        bedTypeComboBox.setSelectedIndex(0);
        statusComboBox.setSelectedIndex(0);
        loadRooms("", null, null, null);

    }

    private void loadAcType() {

        try {

            ResultSet acTypeData = MySQL.execute("SELECT * FROM `ac_type`");

            Vector<String> acTypeVector = new Vector<>();
            acTypeVector.add("Select A/C Type");

            while (acTypeData.next()) {

                acTypeVector.add(acTypeData.getString("ac_type_name"));
                acTypeHashMap.put(acTypeData.getString("ac_type_name"), acTypeData.getString("ac_type_id"));

            }

            acTypeComboBox.setModel(new DefaultComboBoxModel<>(acTypeVector));

        } catch (Exception e) {
            Log.log.warning(e.getMessage());
        }

    }

    private void loadBedType() {

        try {

            ResultSet bedTypeData = MySQL.execute("SELECT * FROM `bed_type`");

            Vector<String> bedTypeVector = new Vector<>();
            bedTypeVector.add("Select Bed Type");

            while (bedTypeData.next()) {

                bedTypeVector.add(bedTypeData.getString("bed_type_name"));
                bedTypeHashMap.put(bedTypeData.getString("bed_type_name"), bedTypeData.getString("bed_type_id"));

            }

            bedTypeComboBox.setModel(new DefaultComboBoxModel<>(bedTypeVector));

        } catch (Exception e) {
            Log.log.warning(e.getMessage());
        }

    }

    private void loadStatus() {

        try {

            ResultSet statusData = MySQL.execute("SELECT * FROM `room_status`");

            Vector<String> statusVector = new Vector<>();
            statusVector.add("Select Status");

            while (statusData.next()) {

                statusVector.add(statusData.getString("room_status_name"));
                statusHashMap.put(statusData.getString("room_status_name"), statusData.getString("room_status_id"));

            }

            statusComboBox.setModel(new DefaultComboBoxModel<>(statusVector));

        } catch (Exception e) {
            Log.log.warning(e.getMessage());
        }

    }

    public void loadRooms(String roomNumber, String acType, String bedType, String status) {

        try {

            String query = "SELECT * FROM `rooms` "
                    + "INNER JOIN `room_category` ON `rooms`.`room_category_room_category_id`=`room_category`.`room_category_id` "
                    + "INNER JOIN `room_status` ON `rooms`.`room_status_room_status_id`=`room_status`.`room_status_id` "
                    + "INNER JOIN `ac_type` ON `room_category`.`ac_type_ac_type_id`=`ac_type`.`ac_type_id` "
                    + "INNER JOIN `bed_type` ON `room_category`.`bed_type_bed_type_id`=`bed_type`.`bed_type_id` ";

            if (acType != null && bedType == null && status == null) {
                query += "WHERE `room_number` LIKE '%" + roomNumber + "%' AND `ac_type`.`ac_type_id`='" + acType + "' ORDER BY `room_number` ASC";

            } else if (bedType != null && acType == null && status == null) {
                query += "WHERE `room_number` LIKE '%" + roomNumber + "%' AND `bed_type`.`bed_type_id`='" + bedType + "' ORDER BY `room_number` ASC";

            } else if (status != null && acType == null && bedType == null) {
                query += "WHERE `room_number` LIKE '%" + roomNumber + "%' AND `room_status`.`room_status_id`='" + status + "' ORDER BY `room_number` ASC";

            } else if (acType == null && bedType != null && status != null) {
                query += "WHERE `room_number` LIKE '%" + roomNumber + "%' AND `bed_type`.`bed_type_id`='" + bedType + "' AND `room_status`.`room_status_id`='" + status + "' ORDER BY `room_number` ASC";

            } else if (acType != null && bedType == null && status != null) {
                query += "WHERE `room_number` LIKE '%" + roomNumber + "%' AND `ac_type`.`ac_type_id`='" + acType + "' AND `room_status`.`room_status_id`='" + status + "' ORDER BY `room_number` ASC";

            } else if (acType != null && bedType != null && status == null) {
                query += "WHERE `room_number` LIKE '%" + roomNumber + "%' AND `ac_type`.`ac_type_id`='" + acType + "' AND `bed_type`.`bed_type_id`='" + bedType + "' ORDER BY `room_number` ASC";

            } else if (acType != null && bedType != null && status != null) {
                query += "WHERE `room_number` LIKE '%" + roomNumber + "%' AND `ac_type`.`ac_type_id`='" + acType + "' AND `bed_type`.`bed_type_id`='" + bedType + "' AND `room_status`.`room_status_id`='" + status + "' ORDER BY `room_number` ASC";

            } else {
                query += "WHERE `room_number` LIKE '%" + roomNumber + "%' ORDER BY `room_number` ASC";
            }

            ResultSet roomDetails = MySQL.execute(query);

            DefaultTableModel roomTableModel = (DefaultTableModel) rommDetailsTable.getModel();
            roomTableModel.setRowCount(0);

            while (roomDetails.next()) {

                Vector<String> roomDetailsVector = new Vector<>();

                roomDetailsVector.add(roomDetails.getString("room_number"));
                roomDetailsVector.add(roomDetails.getString("ac_type_name"));
                roomDetailsVector.add(roomDetails.getString("bed_type_name"));
                roomDetailsVector.add(roomDetails.getString("price"));
                roomDetailsVector.add(roomDetails.getString("room_status_name"));

                roomTableModel.addRow(roomDetailsVector);

            }

        } catch (Exception e) {
            Log.log.warning(e.getMessage());
        }

    }

    private void filterRoom() {

        String slectedAcType = String.valueOf(acTypeComboBox.getSelectedItem());
        String slectedBedType = String.valueOf(bedTypeComboBox.getSelectedItem());
        String slectedStatus = String.valueOf(statusComboBox.getSelectedItem());

        String roomNumber = roomNumberTextField.getText();
        String acType = acTypeHashMap.get(slectedAcType);
        String bedTpe = bedTypeHashMap.get(slectedBedType);
        String status = statusHashMap.get(slectedStatus);

        loadRooms(roomNumber, acType, bedTpe, status);

    }

  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roundedPanel1 = new Component.RoundedPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        rommDetailsTable = new javax.swing.JTable();
        jLabel41 = new javax.swing.JLabel();
        roomNumberTextField = new Component.RoundedTextField();
        clearButton = new javax.swing.JButton();
        addNewRoomButton = new javax.swing.JButton();
        printRoomReportButton = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        acTypeComboBox = new javax.swing.JComboBox<>();
        bedTypeComboBox = new javax.swing.JComboBox<>();
        statusComboBox = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        changeStatusRoomNumberTextField = new Component.RoundedTextField();
        clearStatusChangeSectionButton = new javax.swing.JButton();
        changeRoomStatusButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        roomStatusChangeTable = new javax.swing.JTable();
        roomNumberLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();

        roundedPanel1.setBackground(new java.awt.Color(24, 24, 24));

        jTabbedPane2.setBackground(new java.awt.Color(24, 24, 24));

        jPanel3.setBackground(new java.awt.Color(24, 24, 24));

        rommDetailsTable.setBackground(new java.awt.Color(29, 29, 29));
        rommDetailsTable.setForeground(new java.awt.Color(255, 255, 255));
        rommDetailsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Room Number", "A/C Type", "Bed Type", "Price Per Day", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        rommDetailsTable.setInheritsPopupMenu(true);
        rommDetailsTable.setRowHeight(23);
        rommDetailsTable.setShowGrid(true);
        rommDetailsTable.setSurrendersFocusOnKeystroke(true);
        rommDetailsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rommDetailsTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(rommDetailsTable);

        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(153, 153, 153));
        jLabel41.setText(" ROOM NUMBER");

        roomNumberTextField.setForeground(new java.awt.Color(255, 255, 255));
        roomNumberTextField.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        roomNumberTextField.setPreferredSize(new java.awt.Dimension(91, 30));
        roomNumberTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                roomNumberTextFieldKeyReleased(evt);
            }
        });

        clearButton.setBackground(new java.awt.Color(102, 102, 102));
        clearButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/Clear.png"))); // NOI18N
        clearButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clearButton.setPreferredSize(new java.awt.Dimension(27, 27));
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonjButton1ActionPerformed(evt);
            }
        });

        addNewRoomButton.setBackground(java.awt.SystemColor.textHighlight);
        addNewRoomButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        addNewRoomButton.setForeground(new java.awt.Color(255, 255, 255));
        addNewRoomButton.setText(" Register New Room");
        addNewRoomButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addNewRoomButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewRoomButtonaddNewCustomerButtonActionPerformed(evt);
            }
        });

        printRoomReportButton.setBackground(new java.awt.Color(51, 51, 51));
        printRoomReportButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        printRoomReportButton.setForeground(new java.awt.Color(255, 255, 255));
        printRoomReportButton.setText(" Print Details");
        printRoomReportButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        printRoomReportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printRoomReportButtonActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 153, 153));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/info.png"))); // NOI18N
        jLabel4.setText(" Double click the table row to update the room details.");

        acTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        acTypeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acTypeComboBoxActionPerformed(evt);
            }
        });

        bedTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        bedTypeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bedTypeComboBoxActionPerformed(evt);
            }
        });

        statusComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        statusComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusComboBoxActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("ROOM MANAGEMENT");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(printRoomReportButton, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(roomNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(acTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(bedTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(statusComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                                .addComponent(addNewRoomButton)))
                        .addGap(12, 12, 12))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(roomNumberTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                        .addComponent(clearButton, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                        .addComponent(acTypeComboBox, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                        .addComponent(bedTypeComboBox, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                        .addComponent(statusComboBox, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                    .addComponent(addNewRoomButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(printRoomReportButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        jTabbedPane2.addTab("Main Room Section", jPanel3);

        jPanel4.setBackground(new java.awt.Color(24, 24, 24));

        jLabel42.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(153, 153, 153));
        jLabel42.setText(" ROOM NUMBER");

        changeStatusRoomNumberTextField.setForeground(new java.awt.Color(255, 255, 255));
        changeStatusRoomNumberTextField.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        changeStatusRoomNumberTextField.setPreferredSize(new java.awt.Dimension(91, 30));
        changeStatusRoomNumberTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                changeStatusRoomNumberTextFieldKeyReleased(evt);
            }
        });

        clearStatusChangeSectionButton.setBackground(new java.awt.Color(102, 102, 102));
        clearStatusChangeSectionButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/Clear.png"))); // NOI18N
        clearStatusChangeSectionButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clearStatusChangeSectionButton.setPreferredSize(new java.awt.Dimension(27, 27));
        clearStatusChangeSectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearStatusChangeSectionButtonjButton1ActionPerformed(evt);
            }
        });

        changeRoomStatusButton.setBackground(new java.awt.Color(51, 51, 51));
        changeRoomStatusButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        changeRoomStatusButton.setForeground(new java.awt.Color(255, 153, 51));
        changeRoomStatusButton.setText(" Change Status");
        changeRoomStatusButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        changeRoomStatusButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeRoomStatusButtonActionPerformed(evt);
            }
        });

        roomStatusChangeTable.setBackground(new java.awt.Color(29, 29, 29));
        roomStatusChangeTable.setForeground(new java.awt.Color(255, 255, 255));
        roomStatusChangeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Room Number", "Date of Departure", "A/C Type", "Bed Type", "Price Per Day", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        roomStatusChangeTable.setInheritsPopupMenu(true);
        roomStatusChangeTable.setRowHeight(23);
        roomStatusChangeTable.setShowGrid(true);
        roomStatusChangeTable.setSurrendersFocusOnKeystroke(true);
        roomStatusChangeTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                roomStatusChangeTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(roomStatusChangeTable);

        roomNumberLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        roomNumberLabel.setForeground(new java.awt.Color(204, 204, 204));
        roomNumberLabel.setText("None");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText(" Room Number   :");

        jLabel5.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(153, 153, 153));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/info.png"))); // NOI18N
        jLabel5.setText(" Double click the table row to update the room status of unoccupied rooms.");

        jLabel43.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setText("Changing the status of unreserved rooms");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("ROOM STATUS MANAGEMENT");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 1078, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(roomNumberLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(changeRoomStatusButton))
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(changeStatusRoomNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(clearStatusChangeSectionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel43, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel42)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(changeStatusRoomNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clearStatusChangeSectionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(roomNumberLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(changeRoomStatusButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        jTabbedPane2.addTab("Room Status Change", jPanel4);

        javax.swing.GroupLayout roundedPanel1Layout = new javax.swing.GroupLayout(roundedPanel1);
        roundedPanel1.setLayout(roundedPanel1Layout);
        roundedPanel1Layout.setHorizontalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );
        roundedPanel1Layout.setVerticalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2)
                .addGap(9, 9, 9))
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

    private void clearButtonjButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonjButton1ActionPerformed
        clear();
    }//GEN-LAST:event_clearButtonjButton1ActionPerformed

    private void addNewRoomButtonaddNewCustomerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNewRoomButtonaddNewCustomerButtonActionPerformed

        RoomRegistration roomRegistration = new RoomRegistration(mainPanelSection, true, this);
        roomRegistration.setLocationRelativeTo(mainPanelSection);
        roomRegistration.getRoomUpdateButton().setVisible(false);
        roomRegistration.setVisible(true);

    }//GEN-LAST:event_addNewRoomButtonaddNewCustomerButtonActionPerformed

    private void clearStatusChangeSectionButtonjButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearStatusChangeSectionButtonjButton1ActionPerformed
        clearStatusChangeSection();
    }//GEN-LAST:event_clearStatusChangeSectionButtonjButton1ActionPerformed

    private void roomNumberTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_roomNumberTextFieldKeyReleased

        filterRoom();

    }//GEN-LAST:event_roomNumberTextFieldKeyReleased

    private void acTypeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acTypeComboBoxActionPerformed

        filterRoom();
    }//GEN-LAST:event_acTypeComboBoxActionPerformed

    private void bedTypeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bedTypeComboBoxActionPerformed
        filterRoom();
    }//GEN-LAST:event_bedTypeComboBoxActionPerformed

    private void statusComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusComboBoxActionPerformed
        filterRoom();
    }//GEN-LAST:event_statusComboBoxActionPerformed

    private void rommDetailsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rommDetailsTableMouseClicked
        if (evt.getClickCount() == 2) {

            int selectedRow = rommDetailsTable.getSelectedRow();

            RoomRegistration roomRegistration = new RoomRegistration(mainPanelSection, true, this);

            roomRegistration.getRoomNumberTextField().setText(String.valueOf(rommDetailsTable.getValueAt(selectedRow, 0)));

            roomRegistration.getClearRoomFieldButton().setVisible(false);
            roomRegistration.getRoomRegisterButton().setVisible(false);
            roomRegistration.setTitle("Update Room Details");
            roomRegistration.getTitleLabel().setText("Update Room");
            roomRegistration.updateRoomDetails();
            roomRegistration.setLocationRelativeTo(mainPanelSection);
            roomRegistration.setVisible(true);

        }
    }//GEN-LAST:event_rommDetailsTableMouseClicked

    private void printRoomReportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printRoomReportButtonActionPerformed

        try {

            HashMap<String, Object> parameter = new HashMap<>();
            String filePath = "src//Reports/RoomReports.jasper";

            JRDataSource dataSource = new JRTableModelDataSource(rommDetailsTable.getModel());

            String datetime = new SimpleDateFormat("yyyy MMM dd - hh:mm:ss aaa").format(new Date());
            ResultSet counts = MySQL.execute("SELECT COUNT(*) AS roomCount, "
                    + "COUNT(CASE WHEN `room_status_room_status_id` = '1' THEN 1 END) AS availableRoomCount, "
                    + "COUNT(CASE WHEN `room_status_room_status_id` = '2' THEN 1 END) AS reservedRoomCount FROM `rooms`");

            if (counts.next()) {
                parameter.put("DateTime", datetime);
                parameter.put("TotalRooms", counts.getString("roomCount"));
                parameter.put("AvailableRooms", counts.getString("availableRoomCount"));
                parameter.put("ReservedRooms", counts.getString("reservedRoomCount"));
            }

            JasperPrint report = JasperFillManager.fillReport(filePath, parameter, dataSource);
            JasperViewer.viewReport(report, false);

        } catch (Exception e) {
            Log.log.warning(e.getMessage());
        }

    }//GEN-LAST:event_printRoomReportButtonActionPerformed

    private void changeStatusRoomNumberTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_changeStatusRoomNumberTextFieldKeyReleased
        loadStatusChangeRooms(changeStatusRoomNumberTextField.getText());
    }//GEN-LAST:event_changeStatusRoomNumberTextFieldKeyReleased

    private void roomStatusChangeTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_roomStatusChangeTableMouseClicked

        if (evt.getClickCount() == 2) {

            roomNumberLabel.setText((String) roomStatusChangeTable.getValueAt(roomStatusChangeTable.getSelectedRow(), 0));

        }

    }//GEN-LAST:event_roomStatusChangeTableMouseClicked

    private void changeRoomStatusButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeRoomStatusButtonActionPerformed

        String roomNumber = roomNumberLabel.getText();

        if (roomNumber.equals("None")) {
            JOptionPane.showMessageDialog(this, "Please Select Room", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure about this action ?", "Confirm Your Action", JOptionPane.YES_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {

                try {

                    MySQL.execute("UPDATE `rooms` SET `room_status_room_status_id`='1' WHERE `room_number`='" + roomNumber + "'");

                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.BOTTOM_CENTER, "The status of room number " + roomNumber + " will change to the Available status");
                    loadStatusChangeRooms("");
                    clearStatusChangeSection();

                } catch (Exception e) {
                    Log.log.warning(e.getMessage());
                }

            }

        }

    }//GEN-LAST:event_changeRoomStatusButtonActionPerformed

//    Room Status Change Section
    private void clearStatusChangeSection() {

        changeStatusRoomNumberTextField.setText("");
        loadStatusChangeRooms("");
        roomStatusChangeTable.clearSelection();
        roomNumberLabel.setText("None");

    }

    private void loadStatusChangeRooms(String roomNumber) {

        try {

            ResultSet roomResult = MySQL.execute("SELECT"
                    + "    `booking`.`rooms_room_number`,"
                    + "    MAX(`booking`.`check_out_date`) AS check_out_date,"
                    + "    `ac_type`.`ac_type_name`,"
                    + "    `bed_type`.`bed_type_name`,"
                    + "    `room_status`.`room_status_name`,"
                    + "    `room_category`.`price` FROM `booking` "
                    + "INNER JOIN `rooms` ON `booking`.`rooms_room_number` = `rooms`.`room_number`"
                    + "INNER JOIN `room_status` ON `rooms`.`room_status_room_status_id` = `room_status`.`room_status_id` "
                    + "INNER JOIN `room_category` ON `rooms`.`room_category_room_category_id` = `room_category`.`room_category_id` "
                    + "INNER JOIN `ac_type` ON `room_category`.`ac_type_ac_type_id` = `ac_type`.`ac_type_id` "
                    + "INNER JOIN `bed_type` ON `room_category`.`bed_type_bed_type_id` = `bed_type`.`bed_type_id` "
                    + "WHERE `booking`.`rooms_room_number` LIKE '%" + roomNumber + "%' AND `rooms`.`room_status_room_status_id` = '2' "
                    + "GROUP BY `booking`.`rooms_room_number`, "
                    + "    `ac_type`.`ac_type_name`, "
                    + "    `bed_type`.`bed_type_name`, "
                    + "    `room_status`.`room_status_name`, "
                    + "    `room_category`.`price` "
                    + "ORDER BY `check_out_date` ASC;");

            DefaultTableModel roomStatusChangeTableModel = (DefaultTableModel) roomStatusChangeTable.getModel();
            roomStatusChangeTableModel.setRowCount(0);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MMM-dd");

            while (roomResult.next()) {

                Date checkOutDate = format.parse(roomResult.getString("check_out_date"));

                Vector<String> roomsVector = new Vector<>();

                roomsVector.add(roomResult.getString("rooms_room_number"));
                roomsVector.add(newFormat.format(checkOutDate));
                roomsVector.add(roomResult.getString("ac_type_name"));
                roomsVector.add(roomResult.getString("bed_type_name"));
                roomsVector.add(roomResult.getString("price"));
                roomsVector.add(roomResult.getString("room_status_name"));

                roomStatusChangeTableModel.addRow(roomsVector);
            }

        } catch (Exception e) {
            Log.log.warning(e.getMessage());
        }

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> acTypeComboBox;
    private javax.swing.JButton addNewRoomButton;
    private javax.swing.JComboBox<String> bedTypeComboBox;
    private javax.swing.JButton changeRoomStatusButton;
    private Component.RoundedTextField changeStatusRoomNumberTextField;
    private javax.swing.JButton clearButton;
    private javax.swing.JButton clearStatusChangeSectionButton;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JButton printRoomReportButton;
    private javax.swing.JTable rommDetailsTable;
    private javax.swing.JLabel roomNumberLabel;
    private Component.RoundedTextField roomNumberTextField;
    private javax.swing.JTable roomStatusChangeTable;
    private Component.RoundedPanel roundedPanel1;
    private javax.swing.JComboBox<String> statusComboBox;
    // End of variables declaration//GEN-END:variables
}
