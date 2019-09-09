package app;

/* 
 * klasa odpowiedzialna za tworzenie baz SQLite, do których wrzucamy emaile 
 * w celu ich późniejszego przetwarzania
 */

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;

public class DatabaseConnection {
    public static final String DRIVER = "org.sqlite.JDBC";
    
    private Connection connection = null;
    private Statement statement = null;
    private String databaseFilename;
    private String searchString;
    private int counter;
    
    public DatabaseConnection() {
        this.databaseFilename = "mails.db";
    }
    
    // funkcja ustawiająca plik, który będzie służył za bazę danych
    public void setDatabaseFilename(String filename) {
        this.databaseFilename = filename;
    }
    
    // funkcja tworząca bazę danych dla emaili
    protected void setupDatabase() {
        File file = new File(databaseFilename);
        
        // jeżeli istnieje stara baza to usuwamy ją
        if(file.exists()) {
            file.delete();
        }
        
        setupConnection();
        createTables();
    }
    
    // funkcja tworząca połączenie z bazą
    protected void setupConnection() {
        // sprawdzamy czy obecny jest sterownik do bazy danych
        try {
            Class.forName(DatabaseConnection.DRIVER);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Brak sterownika JDBC!");
        }
 
        // próbujemy ustanowić połączenie z bazą SQLite
        // plik jest automatycznie tworzony
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + databaseFilename);
            statement = connection.createStatement();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Błąd podczas tworzenia połączenia!");
        }
    }
    
    // funkcja tworzy tabelę do przechowywania emaili
    private boolean createTables() {
        String createTable = "CREATE TABLE IF NOT EXISTS mails (\n" +
            " id integer PRIMARY KEY,\n" +
            " sender varchar(255) NOT NULL,\n" +
            " recipients varchar(255) NOT NULL,\n" +
            " subject varchar(255) NOT NULL,\n" +
            " sendTime varchar(255) NOT NULL,\n" +
            " content text NOT NULL,\n" +
            " seen tinyint(1) NOT NULL DEFAULT \"1\"\n" +
            ");";
        
        // oraz tabelę do przechowywania informacji o załącznikach
        String createAttackmentsTable = "CREATE TABLE IF NOT EXISTS attachments (\n" +
            " id integer PRIMARY KEY,\n" +
            " mid integer NOT NULL,\n" +
            " filename varchar(255) NOT NULL,\n" +
            " path text NOT NULL\n" +
            ");";

        try {
            statement.execute(createTable);
            statement.execute(createAttackmentsTable);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Błąd podczas tworzenia tabeli!");
            return false;
        } return true;
    }
    
    // funkcja usuwa emaile ze wcześniejszych wczytań wiadomości
    public void cleanupDatabase() {
        String deleteQuery = "DELETE FROM mails;";
        String deleteAttachmentsQuery = "DELETE FROM attachments;";

        try {
            statement.execute(deleteQuery);
            statement.execute(deleteAttachmentsQuery);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Błąd podczas tworzenia tabeli!");
        }
    }
    
    // funkcja dodająca email do tabeli w bazie danych
    public boolean insert(ParsedMessage parsedMessage) {
        try {
            PreparedStatement prepStmt = connection.prepareStatement(
                    "insert into mails values (?, ?, ?, ?, ?, ?, ?);");
            PreparedStatement prepAttachmentsStmt = connection.prepareStatement(
                    "insert into attachments values (null, ?, ?, ?);");
            
            prepStmt.setInt(1, parsedMessage.getMessageID());
            prepStmt.setString(2, parsedMessage.getFrom());
            prepStmt.setString(3, parsedMessage.getRecipients());
            prepStmt.setString(4, parsedMessage.getSubject());
            prepStmt.setString(5, parsedMessage.getSendDate());
            prepStmt.setString(6, parsedMessage.getContent());
            prepStmt.setBoolean(7, parsedMessage.getSeen());
            prepStmt.execute();
            
            // dla każdego pliku wykonujemy dodanie do bazy danych
            parsedMessage.getFilenames().forEach((t, u) -> {
                try {
                    prepAttachmentsStmt.setInt(1, parsedMessage.getMessageID());
                    prepAttachmentsStmt.setString(2, t);
                    prepAttachmentsStmt.setString(3, u);
                    prepAttachmentsStmt.execute();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Błąd podczas dodawania załączników!");
                }
            });
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Błąd podczas dodawania rekordu!");
            return false;
        } return true;
    }
    
    // pobieranie wszystkich wiadomości z tabeli
    public ArrayList<ParsedMessage> getAll() {
        return search(null);
    }
    
    // funkcja umożliwia pobranie ścieżki do załącznika
    // o danej nazwie z danej wiadomości
    public String getFilePath(int messageID, String filename) {
        try {
            searchString = "SELECT path FROM attachments WHERE mid = " + messageID + " AND filename LIKE '" + filename + "' LIMIT 1;";
            
            ResultSet result = statement.executeQuery(searchString);
            
            while(result.next()) {
                return result.getString("path");
            } return "";
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Błąd wyszukiwania pliku!");
            return "";
        }
    }
    
    // funkcja umożliwiająca wyszukanie emaili w bazie danych
    public ArrayList<ParsedMessage> search(HashMap<String, Object> searchData) {
        // ustanawiamy połączenie z bazą
        setupConnection();
        
        ArrayList<ParsedMessage> returnValue = new ArrayList<>();
        
        try {
            // domyślnie pobieramy wszystkie rekordy
            searchString = "SELECT * FROM mails";
            
            // jeżeli dostarczono dane do wyszukiwania dodajemy je do zapytania SQL
            if(searchData != null) {
                searchString = searchString.concat(" WHERE ");
                counter = 1;
                
                // dla każdego elementy wyszukiwania dodajemy element do zapytania SQL
                searchData.forEach((t, u) -> {
                    // jeżeli dostarczono łańcuch znaków używamy LIKE
                    // w przeciwnym przypadku używamy znaku równości
                    if(u instanceof String) {
                       searchString = searchString.concat(t + " LIKE '%" + u + "%'"); 
                    } else {
                        searchString = searchString.concat(t + " = " + u); 
                    }
                    
                    if(counter < searchData.size()) {
                        searchString = searchString.concat(" OR ");
                    }
                    counter++;
                });
            }
            
            searchString = searchString.concat(";");
            
            // wyszukujemy wiadomości
            ResultSet result = statement.executeQuery(searchString);
            
            int messageID;
            String from, recipients, subject, sendTime, content;
            boolean seen;
            
            // wracamy wszystkie wiadomości w formie obiektu ParsedMessage
            while(result.next()) {
                messageID = result.getInt("id");
                from = result.getString("sender");
                recipients = result.getString("recipients");
                subject = result.getString("subject");
                sendTime = result.getString("sendTime");
                content = result.getString("content");
                seen = result.getBoolean("seen");
                
                // do wiadomości pobieramy także załączniki
                String searchAttachmentsString = "SELECT * FROM attachments WHERE mid = " + messageID;
                ResultSet resultAttachmentsSet = statement.executeQuery(searchAttachmentsString);
                HashMap<String, String> files = new HashMap<>();
                
                // dodajemy je do listy plików
                while(resultAttachmentsSet.next()) {
                    files.put(resultAttachmentsSet.getString("filename"), resultAttachmentsSet.getString("path"));
                }
                
                // tworzymy wiadomość
                ParsedMessage parsedMessage = new ParsedMessage(messageID, from, recipients, subject, sendTime, content, seen);
                // dodajemy do niej pliki
                parsedMessage.addFiles(files);
                
                // dodajemy wiadomość z załącznikami do zwracanej listy wiadomości
                returnValue.add(parsedMessage);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Błąd wyszukiwania!");
            return null;
        } return returnValue;
    }
    
    // funkcja zamykająca połączenie z bazą danych
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Błąd podczas zamykania połączenia!");
        }
    }
}
