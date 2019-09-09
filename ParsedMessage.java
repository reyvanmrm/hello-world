package app;

import java.util.HashMap;

/* 
 * klasa opisująca wiadomości email wczytywane przez program
 * jej obiekty są przetworzonymi wiadomościami email
 */
public class ParsedMessage {
    private String from;
    private String recipients;
    private String subject;
    private String content;
    private String sendDate;
    private boolean seen;
    private int messageID;
    private HashMap<String, String> filenames;
    
    public ParsedMessage() {
        filenames = new HashMap<>();
        content = "";
    }
        
    public ParsedMessage(int messageID, String from, String recipients, String subject, String sendDate, String content, boolean seen) {
        this.messageID = messageID;
        this.from = from;
        this.recipients = recipients;
        this.subject = subject;
        this.content = content;
        this.sendDate = sendDate;
        this.seen = seen;
        filenames = new HashMap<>();
    }
    
    public void setFrom(String from) {
        this.from = from;
    }
    
    public String getFrom() {
        return this.from;
    }
    
    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }
    
    public String getRecipients() {
        return this.recipients;
    }
    
    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    public String getSubject() {
        return this.subject;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getContent() {
        return this.content;
    }
    
    public void setSendDate(String date) {
        this.sendDate = date;
    }
    
    public String getSendDate() {
        return this.sendDate;
    }
    
    public void setSeen(boolean seen) {
        this.seen = seen;
    }
    
    public boolean getSeen() {
        return this.seen;
    }
    
    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }
    
    public int getMessageID() {
        return this.messageID;
    }
    
    public HashMap<String, String> getFilenames() {
        return filenames;
    }
    
    public void addFiles(HashMap<String, String> files) {
        // wrzucamy całe files do filenames
        filenames.putAll(files);
    }
    
    public void addContentPart(String contentPart) {
        this.content = this.content.concat(contentPart);
    }
    public Object[] getArray() {
        return new Object[] {messageID, from, recipients, subject, sendDate, content, seen};
    }
    
    public void printMessage() {
        System.out.println("****************");
        System.out.println("Przypisane ID: " + messageID);
        System.out.println("Nadawca: " + from);
        System.out.println("Odbiorca: " + recipients);
        System.out.println("Temat: " + subject);
        System.out.println("Wysłano: " + sendDate);
        System.out.println("Treść: " + content);
        // wyświetlamy ilość załączników
        System.out.println("Załączniki: " + filenames.size());
        
        if(seen) {
            System.out.println("Wyświetlona");
        }
        
        System.out.println("");
    }
}
