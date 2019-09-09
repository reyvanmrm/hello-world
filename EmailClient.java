package app;

/* 
 * klasa uruchomieniowa programu, tworzy obiekt głównego okna programu
 */
public class EmailClient {
    static MainWindow oknoProgramu;
    
    // metoda main programu uruchamiająca główne okno
    public static void main(String[] args) {
        oknoProgramu = new MainWindow();
        oknoProgramu.setVisible(true);
    }
}
