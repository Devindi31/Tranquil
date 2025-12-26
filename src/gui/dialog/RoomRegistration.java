package gui.dialog;

import Component.RoundedTextField;
import connection.MySQL;
import gui.RoomManagementSection;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import log.Log;
import raven.toast.Notifications;


public class RoomRegistration extends javax.swing.JDialog {

    private final RoomManagementSection roomManagementSection;

    HashMap<String, String> acTypeHashMap;
    HashMap<String, String> bedTypeHashMap;

    /**
     * @param parent
     * @param modal
     * @param roomManagementPanel
     */
    public RoomRegistration(java.awt.Frame parent, boolean modal, RoomManagementSection roomManagementPanel) {
        super(parent, modal);
        initComponents();
        acTypeHashMap = new HashMap<>();
        bedTypeHashMap = new HashMap<>();
        roomNumberTextField.setFocusable(false);
        acTypeComboBox.setFocusable(true);
        this.roomManagementSection = roomManagementPanel;
        loadACType();
        loadBedTpe();
        createRoomNumber();
        Notifications.getInstance().setJFrame((JFrame) parent);
    }

    private void clear() {

        acTypeComboBox.setSelectedIndex(0);
        bedTypeComboBox.setSelectedIndex(0);
        priceTextField.setText("");

    }

    private void loadACType() {

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
           Log.log.warning(e.toString());
        }

    }

    private void loadBedTpe() {

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
            Log.log.warning(e.toString());
        }

    }

    private void createRoomNumber() {

        int number = 0;

        try {

            ResultSet roomNumber = MySQL.execute("SELECT `room_number` FROM `rooms` ORDER BY `room_number` DESC LIMIT 1;");

            if (roomNumber.next()) {
                int currentRoomNumber = Integer.parseInt(roomNumber.getString("room_number"));
                number = currentRoomNumber + 1;
            } else {
                number = 1;
            }

        } catch (Exception e) {
            Log.log.warning(e.toString());
        }

        String formatedRoomNumber = String.format("%02d", number);
        roomNumberTextField.setText(formatedRoomNumber);

    }

    public RoundedTextField getRoomNumberTextField() {
        return roomNumberTextField;
    }

    public JButton getRoomRegisterButton() {
        return roomRegisterButton;
    }

    public JButton getRoomUpdateButton() {
        return roomUpdateButton;
    }

    public JButton getClearRoomFieldButton() {
        return clearRoomFieldButton;
    }

    public JLabel getTitleLabel() {
        return titleLabel;
    }

    private void insertQuery(String roomNumber, String roomCategoryID) {

        try {
            MySQL.execute("INSERT INTO `rooms` (`room_number`,`room_category_room_category_id`,`room_status_room_status_id`) "
                    + "VALUES ('" + roomNumber + "','" + roomCategoryID + "','1')");

            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.BOTTOM_CENTER, "Room " + roomNumber + " registration is successful");
            this.roomManagementSection.loadRooms("", null, null, null);
            this.dispose();

        } catch (Exception e) {
            Log.log.warning(e.toString());
        }

    }

    public void updateRoomDetails() {

        String roomNumber = roomNumberTextField.getText();

        try {

            ResultSet roomDetails = MySQL.execute("SELECT * FROM `rooms` "
                    + "INNER JOIN `room_category` ON `rooms`.`room_category_room_category_id`=`room_category`.`room_category_id` "
                    + "INNER JOIN `ac_type` ON `room_category`.`ac_type_ac_type_id`=`ac_type`.`ac_type_id` "
                    + "INNER JOIN `bed_type` ON `room_category`.`bed_type_bed_type_id`=`bed_type`.`bed_type_id` WHERE `rooms`.`room_number`='" + roomNumber + "'");

            if (roomDetails.next()) {

                String price = new DecimalFormat("#").format(roomDetails.getDouble("price"));

                acTypeComboBox.setSelectedItem(roomDetails.getString("ac_type_name"));
                bedTypeComboBox.setSelectedItem(roomDetails.getString("bed_type_name"));
                priceTextField.setText(price);

            } else {

                JOptionPane.showMessageDialog(this, "Room Not Found", "Oops !", JOptionPane.ERROR_MESSAGE);

            }

        } catch (Exception e) {
            Log.log.warning(e.toString());
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        roomNumberTextField = new Component.RoundedTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        priceTextField = new Component.RoundedTextField();
        jSeparator2 = new javax.swing.JSeparator();
        roomRegisterButton = new javax.swing.JButton();
        roomUpdateButton = new javax.swing.JButton();
        clearRoomFieldButton = new javax.swing.JButton();
        acTypeComboBox = new javax.swing.JComboBox<>();
        bedTypeComboBox = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Room Registration");

        titleLabel.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("Room Registration");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 153, 153));
        jLabel2.setText(" Room Number");

        roomNumberTextField.setForeground(new java.awt.Color(255, 255, 255));
        roomNumberTextField.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        roomNumberTextField.setPreferredSize(new java.awt.Dimension(250, 30));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(153, 153, 153));
        jLabel3.setText(" Room A/C Type");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 153, 153));
        jLabel4.setText(" Room Bed Type");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(153, 153, 153));
        jLabel5.setText(" Price Per Day");

        priceTextField.setForeground(new java.awt.Color(255, 255, 255));
        priceTextField.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        priceTextField.setPreferredSize(new java.awt.Dimension(250, 30));

        roomRegisterButton.setBackground(java.awt.SystemColor.textHighlight);
        roomRegisterButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        roomRegisterButton.setForeground(new java.awt.Color(255, 255, 255));
        roomRegisterButton.setText(" Register");
        roomRegisterButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        roomRegisterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roomRegisterButtonActionPerformed(evt);
            }
        });

        roomUpdateButton.setBackground(new java.awt.Color(51, 51, 51));
        roomUpdateButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        roomUpdateButton.setForeground(java.awt.SystemColor.textHighlight);
        roomUpdateButton.setText(" Update");
        roomUpdateButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        roomUpdateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roomUpdateButtonActionPerformed(evt);
            }
        });

        clearRoomFieldButton.setBackground(new java.awt.Color(102, 102, 102));
        clearRoomFieldButton.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        clearRoomFieldButton.setForeground(new java.awt.Color(255, 255, 255));
        clearRoomFieldButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/Clear.png"))); // NOI18N
        clearRoomFieldButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clearRoomFieldButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearRoomFieldButtonActionPerformed(evt);
            }
        });

        acTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        bedTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(acTypeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(roomNumberTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5)
                            .addComponent(priceTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                            .addComponent(jLabel4)
                            .addComponent(bedTypeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jSeparator2)
                    .addComponent(jSeparator1)
                    .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(roomRegisterButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(roomUpdateButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clearRoomFieldButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(roomNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(priceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(acTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bedTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(39, 39, 39)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(clearRoomFieldButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(roomUpdateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(roomRegisterButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void roomRegisterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roomRegisterButtonActionPerformed

        String roomNumber = roomNumberTextField.getText();
        String selectedAcType = String.valueOf(acTypeComboBox.getSelectedItem());
        String selectedBedType = String.valueOf(bedTypeComboBox.getSelectedItem());
        String price = priceTextField.getText();

        if (roomNumber.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Please generate the room number");
        } else if (!roomNumber.matches("^[0-9]+$")) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Invalid Room Number");
        } else if (selectedAcType.equals("Select A/C Type")) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Please select A/C type");
        } else if (selectedBedType.equals("Select Bed Type")) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Please select bed type");
        } else if (price.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Please enter a price");
        } else if (!price.matches("^[0-9]+$")) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Invalid Price");
        } else {

            try {

                ResultSet roomData = MySQL.execute("SELECT `room_number` FROM `rooms` WHERE `room_number`='" + roomNumber + "'");

                String acType = acTypeHashMap.get(selectedAcType);
                String bedType = bedTypeHashMap.get(selectedBedType);

                String searchQuery = "SELECT * FROM `room_category` "
                        + "WHERE `ac_type_ac_type_id`='" + acType + "' AND `bed_type_bed_type_id`='" + bedType + "'";

                if (roomData.next()) {
                    JOptionPane.showMessageDialog(this, "This Room Number already exists", "Oops !", JOptionPane.ERROR_MESSAGE);
                } else {

                    ResultSet roomCategoryData = MySQL.execute(searchQuery);

                    if (roomCategoryData.next()) {

                        String roomCategoryID = roomCategoryData.getString("room_category_id");
                        insertQuery(roomNumber, roomCategoryID);

                    } else {

                        MySQL.execute("INSERT INTO `room_category` (`price`,`ac_type_ac_type_id`,`bed_type_bed_type_id`) VALUES('" + price + "','" + acType + "','" + bedType + "')");

                        ResultSet roomCategoryData2 = MySQL.execute(searchQuery);

                        if (roomCategoryData2.next()) {

                            String roomCategoryID = roomCategoryData2.getString("room_category_id");
                            insertQuery(roomNumber, roomCategoryID);

                        }

                    }

                }

            } catch (Exception e) {
                Log.log.warning(e.toString());
            }

        }
    }//GEN-LAST:event_roomRegisterButtonActionPerformed

    private void clearRoomFieldButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearRoomFieldButtonActionPerformed
        clear();
    }//GEN-LAST:event_clearRoomFieldButtonActionPerformed

    private void roomUpdateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roomUpdateButtonActionPerformed

        String roomNumber = roomNumberTextField.getText();
        String selectedAcType = String.valueOf(acTypeComboBox.getSelectedItem());
        String selectedBedType = String.valueOf(bedTypeComboBox.getSelectedItem());
        String price = priceTextField.getText();

        if (roomNumber.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Please generate the room number");
        } else if (!roomNumber.matches("^[0-9]+$")) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Invalid Room Number");
        } else if (selectedAcType.equals("Select A/C Type")) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Please select A/C type");
        } else if (selectedBedType.equals("Select Bed Type")) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Please select bed type");
        } else if (price.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Please enter a price");
        } else if (!price.matches("^[0-9]+$")) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_CENTER, "Invalid Price");
        } else {

            try {

                String acType = acTypeHashMap.get(selectedAcType);
                String bedType = bedTypeHashMap.get(selectedBedType);

                String searchCategoryQuery = "SELECT * FROM `room_category` WHERE `ac_type_ac_type_id`='" + acType + "' AND `bed_type_bed_type_id`='" + bedType + "'";

                ResultSet roomDetails = MySQL.execute("SELECT * FROM `rooms` "
                        + "INNER JOIN `room_category` ON `rooms`.`room_category_room_category_id`=`room_category`.`room_category_id` "
                        + "INNER JOIN `ac_type` ON `room_category`.`ac_type_ac_type_id`=`ac_type`.`ac_type_id` "
                        + "INNER JOIN `bed_type` ON `room_category`.`bed_type_bed_type_id`=`bed_type`.`bed_type_id` WHERE `rooms`.`room_number`='" + roomNumber + "'");

                if (roomDetails.next()) {

                    if (acType.equals(roomDetails.getString("ac_type_ac_type_id")) && bedType.equals(roomDetails.getString("bed_type_bed_type_id"))) {

                        MySQL.execute("UPDATE `room_category` SET `price`='" + price + "' WHERE `room_category_id`='" + roomDetails.getString("room_category_id") + "'");
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.BOTTOM_CENTER, "Update is successful");
                        this.roomManagementSection.loadRooms("", null, null, null);
                        this.dispose();

                    } else {

                        ResultSet roomCategoryData = MySQL.execute(searchCategoryQuery);

                        if (roomCategoryData.next()) {

                            MySQL.execute("UPDATE `room_category` SET `price`='" + price + "' WHERE `room_category_id`='" + roomCategoryData.getString("room_category_id") + "'");
                            MySQL.execute("UPDATE `rooms` SET `room_category_room_category_id`='" + roomCategoryData.getString("room_category_id") + "' WHERE `room_number`='" + roomNumber + "'");

                            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.BOTTOM_CENTER, "Update is successful");
                            this.roomManagementSection.loadRooms("", null, null, null);
                            this.dispose();

                        } else {

                            MySQL.execute("INSERT INTO `room_category` (`price`,`ac_type_ac_type_id`,`bed_type_bed_type_id`) VALUES('" + price + "','" + acType + "','" + bedType + "')");

                            ResultSet roomCategoryData2 = MySQL.execute(searchCategoryQuery);

                            if (roomCategoryData2.next()) {

                                String categoryID = roomCategoryData2.getString("room_category_id");

                                MySQL.execute("UPDATE `rooms` SET `room_category_room_category_id`='" + categoryID + "' WHERE `room_number`='" + roomNumber + "'");
                                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.BOTTOM_CENTER, "Update is successful");
                                this.roomManagementSection.loadRooms("", null, null, null);
                                this.dispose();

                            }

                        }

                    }

                } else {
                    JOptionPane.showMessageDialog(this, "Room Not Found", "Oops !", JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception e) {
                Log.log.warning(e.toString());
            }

        }

    }//GEN-LAST:event_roomUpdateButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> acTypeComboBox;
    private javax.swing.JComboBox<String> bedTypeComboBox;
    private javax.swing.JButton clearRoomFieldButton;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private Component.RoundedTextField priceTextField;
    private Component.RoundedTextField roomNumberTextField;
    private javax.swing.JButton roomRegisterButton;
    private javax.swing.JButton roomUpdateButton;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}
