/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itleon.zl.analizador.lexico;

import models.PalabraReservada;
import models.Resultado;
import common.Common;
import java.io.IOException;
/**
 *
 * @author LeoNa
 */
public class Analizador {
    Common common = new Common();
    public Resultado[] resultados;
    String codigo;
    char[] caracteres_simples;
    PalabraReservada[] palabraReservadas;


    public final void cargarCaracteresSimples() {
        caracteres_simples = new char[]{';', '=', '+', '-', '*', '(', ')', '{',
            '}'};
    }

    public final void cargarPalabrasReservadas() {
        palabraReservadas = new PalabraReservada[5];
        palabraReservadas[0] = new PalabraReservada("class", 300);
        palabraReservadas[1] = new PalabraReservada("float", 301);
        palabraReservadas[2] = new PalabraReservada("int", 302);
        palabraReservadas[3] = new PalabraReservada("read", 303);
        palabraReservadas[4] = new PalabraReservada("write", 304);
    }

    public final void LeerArchivo() throws IOException {
        try {
            this.codigo = common.leerArchivo("codigo.txt");
        } catch (Exception e) {
            this.codigo = "";
        }
        
    }

    public Analizador() throws IOException {
        this.resultados = new Resultado[0];
        this.cargarCaracteresSimples();
        this.cargarPalabrasReservadas();
        this.LeerArchivo();
    }

    public void procesar() {
        int tipo_dato_actual = -1;
        String unidad_lexica_actual = "";
        boolean unidad_lexica_valida = false;
        int tipo_inicial = -1;
        int atributo = -1;
        
        for (int i = 0; codigo.length() > i; i++) {
            char c = codigo.charAt(i);
            tipo_dato_actual = tipoSimbolo(c);
            
            if(tipo_inicial == -1){
                tipo_inicial = tipo_dato_actual;
            }
            
            if(tipo_dato_actual == 5 || tipo_dato_actual == 7 || i == codigo.length()){ // Es un espacio o un caracter simple
                Resultado r = new Resultado(unidad_lexica_actual); //[305 => Número natural]
                //Aquí se compara si es una palabra reservada
                atributo = isPalabraReservada(unidad_lexica_actual);
                if(atributo > -1){
                    r.setAtributo(atributo); //Palabras reservadas 
                    r.setTipo("Palabras reservadas");
                    resultados = common.array_push(resultados, r);
                }else{
                    //Si no es una palabra reservada, se revisa que sea una palabra válida
                    if(unidad_lexica_valida){
                        switch(tipo_inicial){
                            case 1: // Solo digitos
                                r.setAtributo(305); //Digito
                                r.setTipo("Número Natural");
                                resultados = common.array_push(resultados, r);
                                break;
                            case 10: // Decimales
                                r.setAtributo(306); //Decimal
                                r.setTipo("Decimal");
                                resultados = common.array_push(resultados, r);
                                break;    
                            case 11:
                                r.setAtributo(307); //Identificador
                                r.setTipo("Identificador");
                                resultados = common.array_push(resultados, r);
                                break;
                        }
                    }else{
                        if(tipo_inicial != 5 && tipo_inicial != 7){
                            r.setAtributo(-1); //Error
                            r.setTipo("Error");
                            resultados = common.array_push(resultados, r);
                        }
                    }
                    if(tipo_dato_actual == 7){
                        //Se revisa si el signo actual es un caracter simple admitido
                        atributo = isCaracterValido(c);
                        if(atributo > -1){
                            r.setLexema(c); //Caracteres simples
                            r.setAtributo(atributo);
                            r.setTipo("Caracteres simples");
                            resultados = common.array_push(resultados, r);
                        }else{
                            r.setLexema(c); //Error
                            r.setAtributo(-1);
                            r.setTipo("Error");
                            resultados = common.array_push(resultados, r);
                        }
                    }
                }
                
                unidad_lexica_actual = "";
                tipo_inicial = -1;
                unidad_lexica_valida = false;  
            } else{
                unidad_lexica_actual+= c;
                switch(tipo_inicial){
                    case 1: //Digito
                        if(tipo_dato_actual == 1){
                            unidad_lexica_valida = true;
                        }else if(tipo_dato_actual == 4){
                            tipo_inicial = 10;
                            unidad_lexica_valida = false;
                        }
                        break;
                    case 10: //Digito después de punto
                        if(tipo_dato_actual == 1){
                            unidad_lexica_valida = true;
                        }else if(tipo_dato_actual == 4){
                            unidad_lexica_valida = false;
                            tipo_inicial = 12;
                        }
                        break;
                    case 2: //Mayuscula [Identificador]
                        unidad_lexica_valida = false;
                        tipo_inicial = 11;
                        break;
                    case 11: //identificador [Continuación]
                        if(tipo_dato_actual == 6 || tipo_dato_actual == 3 
                                || tipo_dato_actual == 1){
                            unidad_lexica_valida = true;
                        }else{
                            unidad_lexica_valida = false;
                            tipo_inicial = 12;
                        }
                        break;
                    default:
                        
                }
            }
        }
    }
    
    public int isCaracterValido(char simboloComparar){
        int atributo = -1;
        for (int i = 0; i < caracteres_simples.length; i++) {
            if(caracteres_simples[i] == simboloComparar){
                atributo = Character.codePointAt(String.valueOf(
                        caracteres_simples[i]), 0);
                break;
            }
        }
        return atributo;
    }
    
    public int isPalabraReservada(String simboloComparar){
        int atributo = -1;
        for (int i = 0; i < palabraReservadas.length; i++) {
            if(palabraReservadas[i].getLexema().equals(simboloComparar)){
                atributo = palabraReservadas[i].getAtributo();
                break;
            }
        }
        return atributo;
    }
    
    private int tipoSimbolo(char simbolo){
        int tipo = 0;
        int code = Character.codePointAt(String.valueOf(simbolo), 0);
        
        if(code >= 48 && code <= 57) tipo = 1; //Digito
        else if(code >= 65 && code <= 90) tipo = 2; // Mayuscua
        else if(code >= 97 && code <= 122) tipo = 3;// Minuscula
        else if(code == 46) tipo = 4; //Punto
        else if(code == 32) tipo = 5; //Espacio
        else if(code == 95) tipo = 6; //Guión Bajo
        else tipo = 7;
        
        return tipo;
    }
    
}
