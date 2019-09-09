package app;

/* 
 * klasa odpowiedzialna za łączenie ze skrzynką pocztową, przetwarzania
 * wiadomości email do obiektów klasy ParsedMessage oraz dostarczania danych
 * do bazy danych i tabeli w oknie głównym programu
 */

import com.sun.mail.util.BASE64DecoderStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.AuthenticationFailedException;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.net.ssl.SSLHandshakeException;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;

public class Mailing {
    // zmienne połączenia z serwerem
    private String imapHost;
    private String user;
    private String password;
    
    // domyślnie nie ustawiamy danych połączenia
    public Mailing() {
        this.imapHost       = "";
        this.user           = "";
        this.password       = "";
    }
    
    // funkcja pozwalająca na ustawienie danych połączenia
    public boolean setCredentials(String imapHost, String user, String password) {
        this.imapHost = imapHost;
        this.user = user;
        this.password = password;
        
        return true;
    }
    
    // funkcja pobierająca foldery z serwera, zwracająca listę obiektów
    public ArrayList<Object> getFolders() {
        ArrayList<Object> returnValue = new ArrayList<>();

        try {
            // próbujemy nawiązać połączenie z serwerem
            Properties props = System.getProperties();
            props.setProperty("mail.store.protocol", "imaps");
            Session session = Session.getDefaultInstance(props, null);
            Store store = session.getStore("imaps");
            store.connect(imapHost, user, password);
            
            // pobieramy główną listę folderów
            Folder[] folders = store.getDefaultFolder().list();
            
            // dla każdego folderu
            for(Folder folder : folders) {
                // pobieramy jego nazwę
                Folder emailFolder = store.getFolder(folder.getFullName());
                
                // sprawdzamy czy folder zawiera podfoldery
                if(emailFolder.list().length == 0) {
                    // jeżeli nie zawiera to dodajemy folder do listy
                    returnValue.add(folder.getFullName());
                } else {
                    // w przeciwnym wypadku pobieramy listę folderów w tym folderze
                    // i dodajemy ją do listy zwracanej przez funkcję
                    // jako podlistę
                    ArrayList<Object> tempList = new ArrayList<>();
                    
                    for(Folder subFolder : emailFolder.list()) {
                        tempList.add(subFolder.getFullName());
                    }
                    
                    returnValue.add(tempList);
                }
            }
        // obsługujemy wyjątki związane z nieprawidłowymi danymi logowania itd.
        } catch (NoSuchProviderException ex) {
            JOptionPane.showMessageDialog(null, "Błąd podczas łączenia!");
            return null;
        } catch (AuthenticationFailedException ex) {
            JOptionPane.showMessageDialog(null, "Błędne dane logowania!");
            return null;
        } catch (MessagingException ex) {
            if(ex.getNextException() instanceof SSLHandshakeException) {
                JOptionPane.showMessageDialog(null, "Sprawdź czy Twój antywirus nie blokuje działania programu! Program zostanie zamknięty!");
                System.exit(1);
            } else {
                JOptionPane.showMessageDialog(null, "Błąd przetwarzania wiadomości!");
            }
        } return returnValue;
    }
    
    // gdy pobieramy wiadomości od razu po połączeniu pobieramy tylko Odebrane
    public ArrayList<ParsedMessage> downloadMessages() {
        return downloadMessages("INBOX");
    }
    
    // funkcja pobierająca wiadomości z konkretnego folderu na skrzynce email
    public ArrayList<ParsedMessage> downloadMessages(String folder) {
        ArrayList<ParsedMessage> returnValue = new ArrayList<>();
        
        try {
            //ustawienie właściwości takich jak używany protokół, nr portu, czas połączenia
            Properties properties = new Properties();              
            properties.put("mail.store.protocol", "imaps"); 
            properties.put("mail.imaps.host", imapHost);
            properties.put("mail.imaps.port", "993");
            properties.put("mail.imaps.connectiontimeout", "5000");
            properties.put("mail.imaps.timeout", "5000");
            
            Session emailSession = Session.getDefaultInstance(properties);

            // ustanawiamy połączenie z IMAP
            Store store = emailSession.getStore("imaps");
            store.connect(imapHost, user, password);
         
            // ładujemy wskazany folder
            Folder emailFolder = store.getFolder(folder);
            emailFolder.open(Folder.READ_ONLY);

            // ładujemy wiadomości
            Message[] messages = emailFolder.getMessages();
            // tworzymy połączenie z bazą danych
            DatabaseConnection databaseConnection = new DatabaseConnection();
            // oraz wstępnie konfigurujemy połączenie
            databaseConnection.setupDatabase();
            // czyścimy tabelę, szczególnie ważne przy zmianie folderu
            databaseConnection.cleanupDatabase();

            // oznaczamy wiadomości przez ID, żeby później móc je wczytywać
            int messageID = 1;
            // dla każdej wiadomości z folderu
            for (Message message : messages) {
                // przetwarzamy wiadomość i tworzymy z niej obiekt klasy ParsedMessage
                ParsedMessage parsedMessage = parseMessage(message, messageID++);
                // dodajemy obiekt do zwracanej listy
                returnValue.add(parsedMessage);
                // dodajemy wiadomość do bazy danych
                databaseConnection.insert(parsedMessage);
            }

            // zamykamy połączenie ze skrzynką pocztową
            emailFolder.close(false);
            store.close();
              
            return returnValue;
        // obsługujemy wyjątki
        } catch (NoSuchProviderException ex) {
            JOptionPane.showMessageDialog(null, "Błąd podczas łączenia!");
            return null;
        } catch (AuthenticationFailedException ex) {
            JOptionPane.showMessageDialog(null, "Błędne dane logowania!");
            return null;
        } catch (IOException | MessagingException ex) {
            JOptionPane.showMessageDialog(null, "Błąd przetwarzania wiadomości!");
            return null;
        }
    }
    
    // funkcja przetwarzająca wiadomość na obiekt ParsedMessage
    public static ParsedMessage parseMessage(Part p, int messageID) throws MessagingException, IOException {
        // tworzymy nową ParsedMessage
        ParsedMessage parsedMessage = new ParsedMessage();
                
        // jeżeli dostarczony obiekt jest wiadomością
        if (p instanceof Message) {
            // rzutujemy zmienną
            Message m = (Message) p;
            // ustawiamy podstawowe parametry ParsedMessage
            parsedMessage.setFrom(MimeUtility.decodeText(parseAddressArray(m.getFrom())));
            parsedMessage.setRecipients(MimeUtility.decodeText(parseAddressArray(m.getRecipients(Message.RecipientType.TO))));
            parsedMessage.setSubject(m.getSubject());
            parsedMessage.setSendDate(m.getSentDate().toString());
            parsedMessage.setMessageID(messageID);
            
            // pobieramy flagi wiadomości i oznaczamy czy została wcześniej odczytana
            // program nie będzie modyfikował tej flagi
            Flags flags = m.getFlags();
            parsedMessage.setSeen(flags.contains(Flags.Flag.SEEN));
        }
        
        // sprawdzamy czy wiadomość została dostarczona poprawnie z serwera
        // w przeciwnym wypadku rzutujemy ją na MimeMessage by uniknąć błędów
        try {
            p.getContentType();
        } catch (MessagingException e) {
            if (p instanceof MimeMessage && "Unable to load BODYSTRUCTURE".equalsIgnoreCase(e.getMessage())) {
                p = new MimeMessage((MimeMessage) p);
            }
        }
        
        // tworzymy tablicę na nazwy załączników
        HashMap<String, String> files = new HashMap<>();
        
        // jeżeli wiadomość jest tekstem ustawiamy go jako treść ParsedMessage
        if (p.isMimeType("text/*")) {
            parsedMessage.setContent(p.getContent().toString());
        // jeżeli wiadomość składa się z częsci
        } else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) p.getContent();
            int count = mp.getCount();

            // dla każdej części wiadomości dodajemy jej treść do treści ParsedMessage
            // sprawdzamy też czy część nie składa się z kolejnych części
            // i jeżeli jest tak to dodajemy je do treści ParsedMessage
            for (int i = 1; i < count; i++) {
                BodyPart bodyPart = mp.getBodyPart(i);

                if(bodyPart.isMimeType("multipart/*")) {
                    Multipart mpEx = (Multipart) bodyPart.getContent();

                    // sprawdzamy czy do częśći przypisany jest załącznik
                    if(bodyPart.getFileName() != null) {
                        // tworzymy plik tymczasowy
                        File attachment = File.createTempFile(bodyPart.getFileName(), ".tmp");
                        // pobieramy plik przy pomocy Apache Commons IO
                        FileUtils.copyInputStreamToFile(bodyPart.getInputStream(), attachment);
                        // dodajemy plik do tablicy załączników - nazwa, ścieżka
                        files.put(bodyPart.getFileName(), attachment.getAbsolutePath());
                    }
                    
                    int countEx = mpEx.getCount();
                    
                    for(int k = 0; k < countEx; k++) {
                        BodyPart bodyPartEx = mpEx.getBodyPart(k);
                        
                        // sprawdzamy czy do częśći przypisany jest załącznik
                        if(bodyPartEx.getFileName() != null) {
                            // tworzymy plik tymczasowy
                            File attachmentEx = File.createTempFile(bodyPartEx.getFileName(), ".tmp");
                            // pobieramy plik przy pomocy Apache Commons IO
                            FileUtils.copyInputStreamToFile(bodyPartEx.getInputStream(), attachmentEx);
                            // dodajemy plik do tablicy załączników - nazwa, ścieżka
                            files.put(bodyPartEx.getFileName(), attachmentEx.getAbsolutePath());
                        }
                        
                        if(bodyPartEx.getContent() instanceof BASE64DecoderStream) {
                            
                        } else {
                            parsedMessage.addContentPart(bodyPartEx.getContent().toString()); 
                        }
                    }
                } else {
                    parsedMessage.addContentPart(bodyPart.getContent().toString()); 
                }
            } 
        }

        // dodajemy listę załączników do elementu wiadomości
        parsedMessage.addFiles(files);
        return parsedMessage;
    }
    
    // funkcja przetwarzająca tablicę adresów
    private static String parseAddressArray(Address[] addressArray) {
        String returnValue = "";
        
        // jeżeli w tablicy znajdują się adresy
        if(addressArray != null) {
            // dodajemy wszystkie adresy do stringa, rozdzielone ,
            for(Address address : addressArray) {
                returnValue = returnValue.concat(", " + address.toString());
            }
        }
        
        // usuwamy pierwszy ,
        return returnValue.replaceFirst(", ", "");
    }
}

