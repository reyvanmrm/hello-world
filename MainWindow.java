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
    
    // dane o serwerach przetrzymujemy w mapie pod numerami odpowiadającymi
    // tymi w ComboBoxie
    private void initServersMap(){
        imapServers = new HashMap<>();
        imapServers.put(0, "imap.gmail.com");
        imapServers.put(2, "imap.poczta.onet.pl");
        imapServers.put(1, "imap.wp.pl");
        imapServers.put(3, "poczta.interia.pl");
    }
    
    // dodajemy listener do tabeli, który po kliknięciu powoduje wyświetlenie okienka z wiadomością
    private void initTableListener() {
        jTable1.getSelectionModel().addListSelectionListener((ListSelectionEvent event) -> {
            if(jTable1.getModel().getRowCount() > 0) {
                FullMessage tempMessage = new FullMessage(null, true);
                // pobieramy ID wiadomości z tabeli
                int messageID = (int) (jTable1.getValueAt(jTable1.getSelectedRow(), 0));
                // tworzymy połączenie z bazą danych
                DatabaseConnection databaseConnection = new DatabaseConnection();
                // ustawiamy dane wyszukiwania wiadomości
                HashMap<String, Object> searchData = new HashMap<>();
                searchData.put("id", messageID);
                // wyszukujemy wiadomość w bazie
                ArrayList<ParsedMessage> search = databaseConnection.search(searchData);
                // jeżeli znaleziono dokładnie jedną wiadomość
                if(search != null && search.size() == 1) {
                    // pobieramy ją z wyników
                    ParsedMessage parsedMessage = search.get(0);
                
                    // wyświetlamy wiadomość
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
        // logujemy się do skrzynki najpierw ustawiając dane logowania
        // a następnie pobieramy foldery z serwera oraz wiadomości z folderu INBOX
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

    // otrzymaną listę folderów dodajemy do drzewa folderów w aplikacji
    private void applyFolders(ArrayList<Object> folders) {
        DefaultMutableTreeNode mainNode = new DefaultMutableTreeNode("Wiadomości");
        
        // dla każdego otrzymanego folderu
        folders.stream().forEach((t) -> {
            // jeżeli Object jest kolejną listą
            if(t instanceof ArrayList) {
                folderName = "Subfolder";
                
                // jeżeli lista zawiera elementy
                if(((ArrayList) t).size() > 0) {
                    // pobieramy pierwszy element, żeby otrzymać nazwę folderu nadrzędnego
                    String temp = ((ArrayList) t).get(0).toString();
                    
                    // dzielimy nazwę folderu i wyciągamy nazwę folderu nadrżednego
                    if(temp.contains("/")) {
                        folderName = temp.split("/")[0]; 
                    } else if(temp.contains(".")) {
                        folderName = temp.split("\\.")[0]; 
                    }
                }
                
                // tworzymy folder nadrzędny
                DefaultMutableTreeNode subNode = new DefaultMutableTreeNode(folderName);
                
                // dla folderu nadrzędnego pobieramy jego elementy
                ((ArrayList) t).stream().forEach((k) -> {
                    String temporary = k.toString();
                    
                    // ustawiamy zmienną, definiującą używany separator w nazwach folderów
                    if(temporary.contains("/")) {
                        ex = "/";
                    } else if(temporary.contains(".")) {
                        ex = "."; 
                    }
                    
                    // oraz dodajemy je do drzewa, jednocześnie usuwając nazwę folderu nadrzędnego
                    subNode.add(new DefaultMutableTreeNode(k.toString().replace(folderName + ex, "")));
                });
                
                mainNode.add(subNode);
            // jeżeli folder nie zawiera pod folderów dodajemy go do drzewa
            } else {
                mainNode.add(new DefaultMutableTreeNode(t.toString()));
            }            
        });
        
        // ustawiamy drzewo folderów
        jTree1.setModel(new DefaultTreeModel(mainNode));
    }
    
    private void applyMessages(ArrayList<ParsedMessage> messages) {
        applyMessages(messages, true);
    }
    
    // wszystkie otrzymane wiadomości są dodawane do tabeli w oknie programu
    private void applyMessages(ArrayList<ParsedMessage> messages, boolean loadAll) {
        jTable1.setModel(new DefaultTableModel(new Object [][] {}, new String [] {"ID", "Nadawca", "Odbiorca", "Temat", "Wysłano", "Treść"}));
        
        // sprawdzamy czy dostarczone zostały wiadomości
        if(messages != null && messages.size() > 0) {
            // dla każdej wiadomości wykonujemy dodawanie do tabeli
            messages.stream().forEach((parsedMessage) -> {
                addMessageRow(parsedMessage);
                
                if(messages.indexOf(parsedMessage) == messages.size() - 1) {
                    jButton1.setEnabled(true);
                    jButton1.setText("Zaloguj");
                }
            });
            
            // jeżeli wyszukiwano, a nie np. zmieniano folder wyświetlamy
            // informację o zakończeniu wyszukiwania
            if(!loadAll) {
                JOptionPane.showMessageDialog(null, "Wyszukiwanie zakończone!");
            }
        } else {
            // informujemy o braku wyników
            JOptionPane.showMessageDialog(null, "Wyszukiwanie zakończone! Nie znaleziono pasujących wyników!");
        }
    }
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // wyszukujemy po wciśnięciu przycisku wyszukiwania
        search(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // jeżeli resetujemy filtry wyszukiwania to pobieramy wszystkie 
        // wiadomości i usuwamy wyszukiwany tekst i zaznaczenia
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
        
        // jeżeli kliknięto prawidłowy folder wiadomości
        if(selectedNode != null && selectedNode.getChildCount() == 0 && !selectedNode.toString().equals("Wiadomości")) {
            folderName = "";
            DefaultMutableTreeNode parent = (DefaultMutableTreeNode) selectedNode.getParent();
            
            // jeżeli folder ma nadfolder to dodajemy go do nazwy folderu na serwerze
            if(parent != null && !parent.toString().equals("Wiadomości")) {
                folderName = parent.toString() + ex;
            }
            
            // dodajemy nazwę folderu do całej nazwy na serwerze
            folderName = folderName.concat(selectedNode.toString());
            
            // pobieramy wiadomości z serwera z danego folderu
            Mailing mailing = new Mailing();
            mailing.setCredentials(imapServers.get(jComboBox2.getSelectedIndex()), jTextField1.getText(), new String(jPasswordField1.getPassword()));
            ArrayList<ParsedMessage> messages = mailing.downloadMessages(folderName);

            // dodajemy wiadomości do tabeli
        }        
    }//GEN-LAST:event_jTree1ValueChanged

    private void jPasswordField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPasswordField1KeyPressed
        // jeżeli klikniemy enter w polu hasła automatycznie logujemy się do skrzynki
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jButton1.doClick();
        }
    }//GEN-LAST:event_jPasswordField1KeyPressed

    // funkcja służąca do wyszukiwania wiadomości
    // jeżeli loadAll = true to ładujemy wszystkie wiadomości z folderu
    private void search(boolean loadAll) {
        String searchingString = jTextField2.getText();
        HashMap<String, Object> searchData = new HashMap<>();
        
        // dodajemy do mapy pola do wyszukiwania zależnie od zaznaczeń CheckBoxów
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
        
        // jeżeli nie zaznaczono żadnego pola do wyszukania
        // usuwamy dane wyszukiwania
        if(searchData.isEmpty()) {
            searchData = null;
        }
        
        // pobieramy z bazy danych wszystkie wiadomości
        // lub tylko te odpowiadające danym w mapie wyszukiwania
        DatabaseConnection databaseConnection = new DatabaseConnection();
        ArrayList<ParsedMessage> messages = (loadAll) ? databaseConnection.getAll() : databaseConnection.search(searchData);
        
        // jeżeli znaleziono w bazie wiadomości to uzupełniamy tabelę znalezionymi wiadomościami
        applyMessages(messages, true);
    }
    
    // dodajemy pojedynczą wiadomość do tabeli
    public void addMessageRow(ParsedMessage parsedMessage) {
        // jeżeli wiadomość była wyświetlona wcześniej nie formatujemy wiersza
        if(parsedMessage.getSeen()) {
            ((DefaultTableModel) jTable1.getModel()).addRow(parsedMessage.getArray());
        } else {
            // w przeciwnym wypadku dodajemy pogrubienie, formatując dane przez HTML
            Object field1 = parsedMessage.getMessageID();
            Object field2 = "<html><b>" + parsedMessage.getFrom() + "</b></html>";
            Object field3 = "<html><b>" + parsedMessage.getRecipients() + "</b></html>";
            Object field4 = "<html><b>" + parsedMessage.getSubject() + "</b></html>";
            Object field5 = "<html><b>" + parsedMessage.getSendDate() + "</b></html>";
            Object field6 = parsedMessage.getContent();
            
            // dodajemy sformatowany wiersz do modelu tabeli
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
