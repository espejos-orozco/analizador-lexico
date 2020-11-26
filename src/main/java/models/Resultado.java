/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author LeoNa
 */
public class Resultado {
    private String lexema;
    private int atributo;
    private String tipo;

    public Resultado(String lexema) {
        this.lexema = lexema;
    }

    public Resultado(String lexema, int atributo, String tipo) {
        this.lexema = lexema;
        this.atributo = atributo;
        this.tipo = tipo;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public void setLexema(char lexema) {
        this.lexema = "" + lexema;
    }

    public int getAtributo() {
        return atributo;
    }

    public void setAtributo(int atributo) {
        this.atributo = atributo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
