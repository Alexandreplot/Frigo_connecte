package main.java;

import static java.lang.Thread.sleep;

/**
 *
 * @author alexa
 */
public class Frigo_goDanseur {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        Frigo frigidaire = Frigo.getFrigo();
        frigidaire.setEtat(true, true, true);
        Historique hist = new Historique(frigidaire);
        //Producer produceur = new Producer(); 
        System.out.println(hist.toJson());
        
        for (int i = 0; i<20; i++) {
            //changer l'etat 
            frigidaire.setEtat((i % 2 == 0), (i > 10), (i % 3 == 0)); 
            hist.setEtat(frigidaire);
            
            //System.out.println(hist.toJson());
            Producer.sent(hist.toJson());
            sleep(1000);
        }
    }
    
}
