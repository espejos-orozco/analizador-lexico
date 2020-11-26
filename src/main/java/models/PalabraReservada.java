package models;

/**
 *
 * @author LeoNa
 */
public class PalabraReservada {
    private String lexema;
    private int atributo;

    public PalabraReservada(String lexema, int atributo) {
        this.lexema = lexema;
        this.atributo = atributo;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public int getAtributo() {
        return atributo;
    }

    public void setAtributo(int atributo) {
        this.atributo = atributo;
    }
    
    
}
