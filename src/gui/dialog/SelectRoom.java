package gui.dialog;

import connection.MySQL;
import gui.RoomBookingSection;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import log.Log;

public class SelectRoom extends javax.swing.JDialog {
    
    private final RoomBookingSection roomBookingSection;
    
    HashMap<String, String> acTypeHashMap;
    HashMap<String, String> bedTypeHashMap;

    /**
     * @param parent
     * @param modal
     */
    public SelectRoom(java.awt.Frame parent, boolean modal, RoomBookingSection roomBookingPanel) {
        super(parent, modal);
        initComponents();
        acTypeHashMap = new HashMap<>();
        bedTypeHashMap = new HashMap<>();
        this.roomBookingSection = roomBookingPanel;
        loadAcType();
        loadBedType();
        loadRoom("", null, null);
    }
    
    private void clear() {
        
        findRoomNumberTextField.setText("");
        acTypeComboBox.setSelectedIndex(0);
        bedTypeComboBox.setSelectedIndex(0);
        roomDetailsTable.clearSelection();
        selectedRoomNumberLabel.setText("None");
        loadRoom("", null, null);
        
    }
    
    private void loadAcType() {
        
        try {
            
            ResultSet acTypeResult = MySQL.execute("SELECT * FROM `ac_type`");
            
            Vector<String> acTypeVector = new Vector<>();
            acTypeVector.add("Select A/C Type");
            
            while (acTypeResult.next()) {
                
                acTypeVector.add(acTypeResult.getString("ac_type_name"));
                acTypeHashMap.put(acTypeResult.getString("ac_type_name"), acTypeResult.getString("ac_type_id"));
                
            }
            
            acTypeComboBox.setModel(new DefaultComboBoxModel<>(acTypeVector));
            
        } catch (Exception e) {
              Log.log.warning(e.toString());
        }
        
    }
    
    private void loadBedType() {
        
        try {
            
            ResultSet bedTypeResult = MySQL.execute("SELECT * FROM `bed_type`");
            
            Vector<String> bedTypeVector = new Vector<>();
            bedTypeVector.add("Select Bed Type");
            
            while (bedTypeResult.next()) {
                
                bedTypeVector.add(bedTypeResult.getString("bed_type_name"));
                bedTypeHashMap.put(bedTypeResult.getString("bed_type_name"), bedTypeResult.getString("bed_type_id"));
                
            }
            
            bedTypeComboBox.setModel(new DefaultComboBoxModel<>(bedTypeVector));
            
        } catch (Exception e) {
              Log.log.warning(e.toString());
        }
        
    }
    
    private void loadRoom(String roomNumber, String acType, String bedType) {
        
        try {
            
            String searchQuery = "SELECT * FROM `rooms` "
                    + "INNER JOIN `room_category` ON `rooms`.`room_category_room_category_id`=`room_category`.`room_category_id` "
                    + "INNER JOIN `room_status` ON `rooms`.`room_status_room_status_id`=`room_status`.`room_status_id` "
                    + "INNER JOIN `ac_type` ON `room_category`.`ac_type_ac_type_id`=`ac_type`.`ac_type_id` "
                    + "INNER JOIN `bed_type` ON `room_category`.`bed_type_bed_type_id`=`bed_type`.`bed_type_id`";
            
            if (acType != null && bedType == null) {
                searchQuery += " WHERE `room_number` LIKE '%" + roomNumber + "%' AND `ac_type_id`='" + acType + "' AND `rooms`.`room_status_room_status_id`='1' ORDER BY `room_number` ASC";
                
            } else if (acType == null && bedType != null) {
                searchQuery += " WHERE `room_number` LIKE '%" + roomNumber + "%' AND `bed_type_id`='" + bedType + "' AND `rooms`.`room_status_room_status_id`='1' ORDER BY `room_number` ASC";
                
            } else if (acType != null && bedType != null) {
                searchQuery += " WHERE `room_number` LIKE '%" + roomNumber + "%' AND `ac_type_id`='" + acType + "' AND `bed_type_id`='" + bedType + "' AND `rooms`.`room_status_room_status_id`='1' ORDER BY `room_number` ASC";
                
            } else {
                searchQuery += " WHERE `room_number` LIKE '%" + roomNumber + "%' AND `rooms`.`room_status_room_status_id`='1' ORDER BY `room_number` ASC";
                
            }
            
            ResultSet roomResult = MySQL.execute(searchQuery);
            
            DefaultTableModel roomtableModel = (DefaultTableModel) roomDetailsTable.getModel();
            roomtableModel.setRowCount(0);
            
            while (roomResult.next()) {
                
                Vector<String> roomVector = new Vector<>();
                roomVector.add(roomResult.getString("room_number"));
                roomVector.add(roomResult.getString("ac_type_name"));
                roomVector.add(roomResult.getString("bed_type_name"));
                roomVector.add(roomResult.getString("price"));
                
                roomtableModel.addRow(roomVector);
            }
            
        } catch (Exception e) {
              Log.log.warning(e.toString());
        }
        
    }
    
    private void filterRoom() {
        
        String selectedAcType = String.valueOf(acTypeComboBox.getSelectedItem());
        String selectedBedType = String.valueOf(bedTypeComboBox.getSelectedItem());
        
        String roomNumber = findRoomNumberTextField.getText();
        String acType = acTypeHashMap.get(selectedAcType);
        String bedType = bedTypeHashMap.get(selectedBedType);
        
        loadRoom(roomNumber, acType, bedType);
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        findRoomNumberTextField = new Component.RoundedTextField();
        clearButton = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        selectedRoomNumberLabel = new javax.swing.JLabel();
        doneButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        roomDetailsTable = new javax.swing.JTable();
        acTypeComboBox = new javax.swing.JComboBox<>();
        bedTypeComboBox = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Select Room");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 153, 153));
        jLabel1.setText(" ROOM NUMBER");

        findRoomNumberTextField.setPreferredSize(new java.awt.Dimension(117, 30));
        findRoomNumberTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                findRoomNumberTextFieldKeyReleased(evt);
            }
        });

        clearButton.setBackground(new java.awt.Color(102, 102, 102));
        clearButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/Clear.png"))); // NOI18N
        clearButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 153, 153));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/info.png"))); // NOI18N
        jLabel4.setText(" Double click the table row to select the room.");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 153, 153));
        jLabel2.setText(" Room Number :");

        selectedRoomNumberLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        selectedRoomNumberLabel.setForeground(new java.awt.Color(255, 255, 255));
        selectedRoomNumberLabel.setText("None");

        doneButton.setBackground(new java.awt.Color(51, 51, 51));
        doneButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        doneButton.setForeground(new java.awt.Color(74, 156, 85));
        doneButton.setText("Done");
        doneButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        doneButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doneButtonActionPerformed(evt);
            }
        });

        roomDetailsTable.setBackground(new java.awt.Color(29, 29, 29));
        roomDetailsTable.setForeground(new java.awt.Color(255, 255, 255));
        roomDetailsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Room Number", "A/C Type", "Bed Type", "Price Per Day"
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
        roomDetailsTable.setInheritsPopupMenu(true);
        roomDetailsTable.setRowHeight(23);
        roomDetailsTable.setShowGrid(true);
        roomDetailsTable.setSurrendersFocusOnKeystroke(true);
        roomDetailsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                roomDetailsTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(roomDetailsTable);

        acTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        acTypeComboBox.setMinimumSize(new java.awt.Dimension(76, 30));
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(selectedRoomNumberLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(doneButton))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                                    .addComponent(findRoomNumberTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addComponent(acTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(bedTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 301, Short.MAX_VALUE)))))
                .addGap(18, 18, 18))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(clearButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(acTypeComboBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bedTypeComboBox, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(findRoomNumberTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(26, 26, 26)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 431, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(doneButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(selectedRoomNumberLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void roomDetailsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_roomDetailsTableMouseClicked
        
        if (evt.getClickCount() == 2) {
            
            int selectedRow = roomDetailsTable.getSelectedRow();
            selectedRoomNumberLabel.setText(String.valueOf(roomDetailsTable.getValueAt(selectedRow, 0)));
            
        }

    }//GEN-LAST:event_roomDetailsTableMouseClicked

    private void findRoomNumberTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_findRoomNumberTextFieldKeyReleased
        filterRoom();
    }//GEN-LAST:event_findRoomNumberTextFieldKeyReleased

    private void acTypeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acTypeComboBoxActionPerformed
        filterRoom();
    }//GEN-LAST:event_acTypeComboBoxActionPerformed

    private void bedTypeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bedTypeComboBoxActionPerformed
        filterRoom();
    }//GEN-LAST:event_bedTypeComboBoxActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        clear();
    }//GEN-LAST:event_clearButtonActionPerformed

    private void doneButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doneButtonActionPerformed
        
        String roomNumber = selectedRoomNumberLabel.getText();
        
        if (roomNumber.equals("None")) {
            JOptionPane.showMessageDialog(this, "Please select a room", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            
            roomBookingSection.getRoomNumberLabel().setText(roomNumber);
            this.dispose();
            
        }

    }//GEN-LAST:event_doneButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> acTypeComboBox;
    private javax.swing.JComboBox<String> bedTypeComboBox;
    private javax.swing.JButton clearButton;
    private javax.swing.JButton doneButton;
    private Component.RoundedTextField findRoomNumberTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable roomDetailsTable;
    private javax.swing.JLabel selectedRoomNumberLabel;
    // End of variables declaration//GEN-END:variables
}
