package main.java;

/**
 *
 * @author alexa
 */
public final class Frigo {
    
    private static Frigo INSTANCE;
    
    private boolean etat1;
    private boolean etat2;
    private boolean etat3; 

    private Frigo() {
        this.etat1 = true;
        this.etat2 = true;
        this.etat3 = true;
    }
    
    public static Frigo getFrigo() {
        if(INSTANCE == null) {
            INSTANCE = new Frigo();
        }
        
        return INSTANCE;
    }
    
    public void setEtat(boolean etat1, boolean etat2, boolean etat3) {
        this.etat1 = etat1;
        this.etat2 = etat2;
        this.etat3 = etat3;
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
