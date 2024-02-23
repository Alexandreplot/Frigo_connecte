package main.java;

//MAJ à faire DP observateur
import java.time.*;
//import com.google.gson.*; 

/**
 *
 * @author alexa
 */
public class Historique {
    private LocalDateTime date; 
    
    private boolean etat1;
    private boolean etat2;
    private boolean etat3; 

    public Historique(LocalDateTime date, boolean etat1, boolean etat2, boolean etat3) {
        this.date = date;
        this.etat1 = etat1;
        this.etat2 = etat2;
        this.etat3 = etat3;
    }
    
    public Historique(boolean etat1, boolean etat2, boolean etat3) {
        this.date = LocalDateTime.now();
        this.etat1 = etat1;
        this.etat2 = etat2;
        this.etat3 = etat3;
    }
    
    public Historique(LocalDateTime date, Frigo frig) {
        this.date = date;
        this.etat1 = frig.getEtat1();
        this.etat2 = frig.getEtat2();
        this.etat3 = frig.getEtat3();
    }
    
    public Historique(Frigo frig) {
        this.date = LocalDateTime.now();
        this.etat1 = frig.getEtat1();
        this.etat2 = frig.getEtat2();
        this.etat3 = frig.getEtat3();
    }
    
    public String toJson(){
        /*GsonBuilder builder = new GsonBuilder(); 
        builder.setPrettyPrinting();  
        Gson gson = builder.create(); 
        return gson.toJson(this);*/
        
        String message = "{ 'dateheure_historique':" + this.getDate_formatSQL() + ", 'etat_frigo_1':" + this.getEtat1() + ", 'etat_frigo_2':" + this.getEtat2() + ", 'etat_frigo_3':" + this.getEtat3() + "}";
        return message;
    }
    
    public LocalDateTime getDate() {
        return date;
    }
    
    public String getDate_formatSQL() {
        String maDate = date.getYear() + "-" + date.getMonthValue() + "-" + date.getDayOfMonth() + " " + date.getHour() + ":" + date.getMinute() + ":" + date.getSecond();
        return maDate;
    }
    
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    
    public void setEtat(Frigo frig) {
        this.etat1 = frig.getEtat1();
        this.etat2 = frig.getEtat2();
        this.etat3 = frig.getEtat3();
    }
    
    public boolean getEtat1() {
        return etat1;
    }

    public boolean getEtat2() {
        return etat2;
    }

    public boolean getEtat3() {
        return etat3;
    }

    public void setEtat1(boolean etat1) {
        this.etat1 = etat1;
    }

    public void setEtat2(boolean etat2) {
        this.etat2 = etat2;
    }

    public void setEtat3(boolean etat3) {
        this.etat3 = etat3;
    }
}
