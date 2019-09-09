package app;

/* 
 * klasa odpowiedzialna za główne okno programu, wywyłuje pozostałe akcje
 * oraz wyświetla dane
 */

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class MainWindow extends javax.swing.JFrame {
    private HashMap<Integer, String> imapServers;
    private String folderName;
    private String ex;
    
    public MainWindow() {
        initComponents();
        initTableListener();
        initServersMap();
    }
    
    private void initServersMap(){
        imapServers = new HashMap<>();
        imapServers.put(0, "imap.gmail.com");
        imapServers.put(2, "imap.poczta.onet.pl");
        imapServers.put(1, "imap.wp.pl");
        imapServers.put(3, "poczta.interia.pl");
    }
    
    private void initTableListener() {
        jTable1.getSelectionModel().addListSelectionListener((ListSelectionEvent event) -> {
            if(jTable1.getModel().getRowCount() > 0) {
                FullMessage tempMessage = new FullMessage(null, true);
                int messageID = (int) (jTable1.getValueAt(jTable1.getSelectedRow(), 0));
                DatabaseConnection databaseConnection = new DatabaseConnection();
                HashMap<String, Object> searchData = new HashMap<>();
                searchData.put("id", messageID);
                ArrayList<ParsedMessage> search = databaseConnection.search(searchData);
                if(search != null && search.size() == 1) {
                    ParsedMessage parsedMessage = search.get(0);
                
                    tempMessage.setData(parsedMessage);
                    tempMessage.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Błąd wczytywania wiadomości!");
                }                
            }
        });
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel6 = new javax.swing.JPanel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jTextField2 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jButton3 = new javax.swing.JButton();
        jCheckBox5 = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("KlientPoczty");
        setResizable(false);

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Gmail", "WP", "Onet", "Interia" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel2.setText("Wybierz e-mail:");

        jTextField1.setText("testjavamail@onet.pl");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel3.setText("Login:");

        jPasswordField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPasswordField1KeyPressed(evt);
            }
        });

        jLabel4.setText("Hasło:");

        jButton1.setText("Zaloguj");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(86, 86, 86)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(36, 36, 36))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(4, 4, 4)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Wiadomości");
        jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTree1.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTree1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jTree1);

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nadawca", "Odbiorca", "Temat", "Wysłano", "Treść"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(jTable1);

        jTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jButton2.setText("Wyszukaj");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jCheckBox1.setText("Nadawca");

        jCheckBox2.setText("Odbiorca");

        jCheckBox3.setText("Temat");

        jCheckBox4.setText("Treść");

        jButton3.setText("Usuń filtr");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jCheckBox5.setText("Nieprzeczytane");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jCheckBox1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox4))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jCheckBox2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox5)))
                .addGap(6, 40, Short.MAX_VALUE)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCheckBox1)
                            .addComponent(jCheckBox3)
                            .addComponent(jCheckBox4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCheckBox2)
                            .addComponent(jCheckBox5))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField2)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jButton1.setEnabled(false);
        jButton1.setText("Pobieram...");
        Mailing mailing = new Mailing();
        mailing.setCredentials(imapServers.get(jComboBox2.getSelectedIndex()), jTextField1.getText(), new String(jPasswordField1.getPassword()));
        ArrayList<Object> folders = mailing.getFolders();
        if(folders != null) {
            Thread thread = new Thread(() -> {
                ArrayList<ParsedMessage> messages = mailing.downloadMessages();

                applyFolders(folders);
                applyMessages(messages);
            });

            thread.start();
        } else {
            jButton1.setEnabled(true);
            jButton1.setText("Zaloguj");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void applyFolders(ArrayList<Object> folders) {
        DefaultMutableTreeNode mainNode = new DefaultMutableTreeNode("Wiadomości");
        
        folders.stream().forEach((t) -> {
            if(t instanceof ArrayList) {
                folderName = "Subfolder";
                
                if(((ArrayList) t).size() > 0) {
                    String temp = ((ArrayList) t).get(0).toString();
                    
                    if(temp.contains("/")) {
                        folderName = temp.split("/")[0]; 
                    } else if(temp.contains(".")) {
                        folderName = temp.split("\\.")[0]; 
                    }
                }
                
                DefaultMutableTreeNode subNode = new DefaultMutableTreeNode(folderName);
                
 
                ((ArrayList) t).stream().forEach((k) -> {
                    String temporary = k.toString();
                    
                    if(temporary.contains("/")) {
                        ex = "/";
                    } else if(temporary.contains(".")) {
                        ex = "."; 
                    }
                    
                    subNode.add(new DefaultMutableTreeNode(k.toString().replace(folderName + ex, "")));
                });
                
                mainNode.add(subNode);
            } else {
                mainNode.add(new DefaultMutableTreeNode(t.toString()));
            }            
        });
        
        jTree1.setModel(new DefaultTreeModel(mainNode));
    }
    
    private void applyMessages(ArrayList<ParsedMessage> messages) {
        applyMessages(messages, true);
    }
    
    private void applyMessages(ArrayList<ParsedMessage> messages, boolean loadAll) {
        jTable1.setModel(new DefaultTableModel(new Object [][] {}, new String [] {"ID", "Nadawca", "Odbiorca", "Temat", "Wysłano", "Treść"}));
        
        if(messages != null && messages.size() > 0) {
            messages.stream().forEach((parsedMessage) -> {
                addMessageRow(parsedMessage);
                
                if(messages.indexOf(parsedMessage) == messages.size() - 1) {
                    jButton1.setEnabled(true);
                    jButton1.setText("Zaloguj");
                }
            });
            

            if(!loadAll) {
                JOptionPane.showMessageDialog(null, "Wyszukiwanie zakończone!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Wyszukiwanie zakończone! Nie znaleziono pasujących wyników!");
        }
    }
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        search(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        search(true);
        jTextField1.setText("");
        jCheckBox1.setSelected(false);
        jCheckBox2.setSelected(false);
        jCheckBox3.setSelected(false);
        jCheckBox4.setSelected(false);
        jCheckBox5.setSelected(false);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTree1ValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTree1ValueChanged
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) jTree1.getLastSelectedPathComponent(); 
        
        if(selectedNode != null && selectedNode.getChildCount() == 0 && !selectedNode.toString().equals("Wiadomości")) {
            folderName = "";
            DefaultMutableTreeNode parent = (DefaultMutableTreeNode) selectedNode.getParent();
            
            if(parent != null && !parent.toString().equals("Wiadomości")) {
                folderName = parent.toString() + ex;
            }
            
            folderName = folderName.concat(selectedNode.toString());
            
            Mailing mailing = new Mailing();
            mailing.setCredentials(imapServers.get(jComboBox2.getSelectedIndex()), jTextField1.getText(), new String(jPasswordField1.getPassword()));
            ArrayList<ParsedMessage> messages = mailing.downloadMessages(folderName);

        }        
    }//GEN-LAST:event_jTree1ValueChanged

    private void jPasswordField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPasswordField1KeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jButton1.doClick();
        }
    }//GEN-LAST:event_jPasswordField1KeyPressed

    private void search(boolean loadAll) {
        String searchingString = jTextField2.getText();
        HashMap<String, Object> searchData = new HashMap<>();
        
        if(jCheckBox1.isSelected()) {
            searchData.put("sender", searchingString);
        }
        
        if(jCheckBox2.isSelected()) {
            searchData.put("recipients", searchingString);
        }
        
        if(jCheckBox3.isSelected()) {
            searchData.put("subject", searchingString);
        }
        
        if(jCheckBox4.isSelected()) {
            searchData.put("content", searchingString);
        }
        
        if(jCheckBox5.isSelected()) {
            searchData.put("seen", 0);
        }
        
        if(searchData.isEmpty()) {
            searchData = null;
        }

        DatabaseConnection databaseConnection = new DatabaseConnection();
        ArrayList<ParsedMessage> messages = (loadAll) ? databaseConnection.getAll() : databaseConnection.search(searchData);
        
        applyMessages(messages, true);
    }
    
    public void addMessageRow(ParsedMessage parsedMessage) {
        if(parsedMessage.getSeen()) {
            ((DefaultTableModel) jTable1.getModel()).addRow(parsedMessage.getArray());
        } else {
            Object field1 = parsedMessage.getMessageID();
            Object field2 = "<html><b>" + parsedMessage.getFrom() + "</b></html>";
            Object field3 = "<html><b>" + parsedMessage.getRecipients() + "</b></html>";
            Object field4 = "<html><b>" + parsedMessage.getSubject() + "</b></html>";
            Object field5 = "<html><b>" + parsedMessage.getSendDate() + "</b></html>";
            Object field6 = parsedMessage.getContent();
            
            ((DefaultTableModel) jTable1.getModel()).addRow(new Object[] {field1, field2, field3, field4, field5, field6});
        }
        
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        int totalWidth = jTable1.getColumnModel().getTotalColumnWidth();
        jTable1.getColumnModel().getColumn(0).setMinWidth((int) (totalWidth * 0.1));
        jTable1.getColumnModel().getColumn(0).setMaxWidth((int) (totalWidth * 0.1));
    }
    
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(() -> {
            new MainWindow().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTree jTree1;
    // End of variables declaration//GEN-END:variables
}
