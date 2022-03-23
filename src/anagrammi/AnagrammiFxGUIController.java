package anagrammi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class AnagrammiFxGUIController {

    @FXML private TextField syottoAlue;
    @FXML private Button kaynnistys;
    @FXML private TextArea vastausAlue;
    @FXML private Label varoitus;
    

    @FXML void handleTulosta() {
        vastausAlue.clear();
        syotaTeksti(syottoAlue);
    }
    
    /**
     * @param syottoalue tekstialue mistä merkkijono otetaanS
     */
    public void syotaTeksti(TextField syottoalue) {
        ArrayList<String> ar = new ArrayList<String>();
        ArrayList<String> kotus = new ArrayList<String>();
        
        lueTiedostosta(kotus);
        anagrammi(syottoalue.getText(), "", ar);
        ar = poistaDuplikaatit(ar);
        tulostaTeksti(ar, kotus);
    }
    
    /**
     * Lukee tiedoston mitä käsitellään
     * @param kotusLista lista jossa kotus-sanalistan sanat
     */
    public static void lueTiedostosta(ArrayList<String> kotusLista) {
        String tied = "kotus.txt";
        try (Scanner fi = new Scanner(new FileInputStream(new File(tied)))) {
            while (fi.hasNext()) {
                String rivi = fi.nextLine();
                kotusLista.add(rivi);
            }
            
        }catch (FileNotFoundException ex) {
            System.err.println("Tiedosto ei aukee " + ex.getMessage());
            return;
        }
    }
    
    /**
     * @param sana merkkijono joka tuodaan aliohjelmalle
     * @param sana1 tyhjä merkkijono johon täytetään uutta muodostettua sanaa
     * @param ar1 lista johon sanat tallennetaan
     */
    public void anagrammi(String sana, String sana1, ArrayList<String> ar1) {
        if (sana.length() >= 10) {
        	varoitus.setText("Syöttämäsi sana on liian pitkä!");
    		varoitus.setTextFill(Color.rgb(210, 39, 30));
    		ar1.add(sana1);
    		return;
        }
        
        if (sana.contains(" ")) {
        	varoitus.setText("Yksittäisiä sanoja vain kiitos!");
    		varoitus.setTextFill(Color.rgb(210, 39, 30));
    		ar1.add(sana1);
    		return;
        }
    	
    	if (sana.length() == 0) {
            ar1.add(sana1);
            return;
        }
        
        for (int i = 0; i < sana.length(); i++) {
            char a = sana.charAt(i);
            String s = sana.substring(0, i) + sana.substring(i+1);
            anagrammi(s, sana1 + a, ar1);
        }
    }
    
    
    /**
     * @param ar1 lista jossa sanat on
     * @param kotus1 lista jossa sanat on
     */
    public void tulostaTeksti(ArrayList<String> ar1, ArrayList<String> kotus1) {
        int k = 0;
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(vastausAlue)) {
            for (int i = 1; i < ar1.size(); i++) {
                String rivi = ar1.get(i);
                for (int j = 0; j < kotus1.size(); j++) {
                    if (rivi.equalsIgnoreCase(kotus1.get(j))) {
                        k++;
                        os.println("Sana " + k + ": " + rivi);
                    }
                }
            }
            os.println("Kaikki sanat muodostettu");
            if (k == 1) {
                os.println("Löytyi " + k + " sana");
            }
            else
                os.println("Löytyi " + k + " sanaa");
            syottoAlue.clear();
        }
    }
    
    /**
     * Poistetaan duplikaatit listasta
     * @param ar tarkasteltava lista
     * @return palauttaa listan missä ei ole duplikaatteja
     */
    public static ArrayList<String> poistaDuplikaatit(ArrayList<String> ar) {
        ArrayList<String> uusi = new ArrayList<String>();
        for (String jono : ar) {
            if (!uusi.contains(jono))
                uusi.add(jono);
        }
        
        return uusi;
    }

}
