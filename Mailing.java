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
    private String imapHost;
    private String user;
    private String password;
    
    public Mailing() {
        this.imapHost       = "";
        this.user           = "";
        this.password       = "";
    }
    
    public boolean setCredentials(String imapHost, String user, String password) {
        this.imapHost = imapHost;
        this.user = user;
        this.password = password;
        
        return true;
    }
    
    public ArrayList<Object> getFolders() {
        ArrayList<Object> returnValue = new ArrayList<>();

        try {
            Properties props = System.getProperties();
            props.setProperty("mail.store.protocol", "imaps");
            Session session = Session.getDefaultInstance(props, null);
            Store store = session.getStore("imaps");
            store.connect(imapHost, user, password);
            
            Folder[] folders = store.getDefaultFolder().list();
            
            for(Folder folder : folders) {
                Folder emailFolder = store.getFolder(folder.getFullName());
                
                if(emailFolder.list().length == 0) {
                    returnValue.add(folder.getFullName());
                } else {

                    ArrayList<Object> tempList = new ArrayList<>();
                    
                    for(Folder subFolder : emailFolder.list()) {
                        tempList.add(subFolder.getFullName());
                    }
                    
                    returnValue.add(tempList);
                }
            }
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
    
    public ArrayList<ParsedMessage> downloadMessages() {
        return downloadMessages("INBOX");
    }
    
    public ArrayList<ParsedMessage> downloadMessages(String folder) {
        ArrayList<ParsedMessage> returnValue = new ArrayList<>();
        
        try {
            Properties properties = new Properties();              
            properties.put("mail.store.protocol", "imaps"); 
            properties.put("mail.imaps.host", imapHost);
            properties.put("mail.imaps.port", "993");
            properties.put("mail.imaps.connectiontimeout", "5000");
            properties.put("mail.imaps.timeout", "5000");
            
            Session emailSession = Session.getDefaultInstance(properties);

            Store store = emailSession.getStore("imaps");
            store.connect(imapHost, user, password);
         
            Folder emailFolder = store.getFolder(folder);
            emailFolder.open(Folder.READ_ONLY);

            Message[] messages = emailFolder.getMessages();
            DatabaseConnection databaseConnection = new DatabaseConnection();
            databaseConnection.setupDatabase();
            databaseConnection.cleanupDatabase();

            int messageID = 1;
            for (Message message : messages) {
                ParsedMessage parsedMessage = parseMessage(message, messageID++);
                returnValue.add(parsedMessage);
                databaseConnection.insert(parsedMessage);
            }

            emailFolder.close(false);
            store.close();
              
            return returnValue;
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
    
    public static ParsedMessage parseMessage(Part p, int messageID) throws MessagingException, IOException {
        ParsedMessage parsedMessage = new ParsedMessage();
                
        if (p instanceof Message) {
            Message m = (Message) p;
            parsedMessage.setFrom(MimeUtility.decodeText(parseAddressArray(m.getFrom())));
            parsedMessage.setRecipients(MimeUtility.decodeText(parseAddressArray(m.getRecipients(Message.RecipientType.TO))));
            parsedMessage.setSubject(m.getSubject());
            parsedMessage.setSendDate(m.getSentDate().toString());
            parsedMessage.setMessageID(messageID);
            

            Flags flags = m.getFlags();
            parsedMessage.setSeen(flags.contains(Flags.Flag.SEEN));
        }
        
        try {
            p.getContentType();
        } catch (MessagingException e) {
            if (p instanceof MimeMessage && "Unable to load BODYSTRUCTURE".equalsIgnoreCase(e.getMessage())) {
                p = new MimeMessage((MimeMessage) p);
            }
        }
        
        HashMap<String, String> files = new HashMap<>();
        
        if (p.isMimeType("text/*")) {
            parsedMessage.setContent(p.getContent().toString());
        } else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) p.getContent();
            int count = mp.getCount();


            for (int i = 1; i < count; i++) {
                BodyPart bodyPart = mp.getBodyPart(i);

                if(bodyPart.isMimeType("multipart/*")) {
                    Multipart mpEx = (Multipart) bodyPart.getContent();

                    if(bodyPart.getFileName() != null) {
                        File attachment = File.createTempFile(bodyPart.getFileName(), ".tmp");
                        FileUtils.copyInputStreamToFile(bodyPart.getInputStream(), attachment);
                        files.put(bodyPart.getFileName(), attachment.getAbsolutePath());
                    }
                    
                    int countEx = mpEx.getCount();
                    
                    for(int k = 0; k < countEx; k++) {
                        BodyPart bodyPartEx = mpEx.getBodyPart(k);
                        
                        if(bodyPartEx.getFileName() != null) {
                            File attachmentEx = File.createTempFile(bodyPartEx.getFileName(), ".tmp");
                            FileUtils.copyInputStreamToFile(bodyPartEx.getInputStream(), attachmentEx);
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

        parsedMessage.addFiles(files);
        return parsedMessage;
    }
    

    private static String parseAddressArray(Address[] addressArray) {
        String returnValue = "";
        
        if(addressArray != null) {
            for(Address address : addressArray) {
                returnValue = returnValue.concat(", " + address.toString());
            }
        }
        

        return returnValue.replaceFirst(", ", "");
    }
}

