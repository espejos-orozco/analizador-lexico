package common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import models.Resultado;


/**
 *
 * @author LeoNa
 */
public class Common {
    
    public Resultado [] array_push(Resultado [] rs, Resultado r){
        Resultado [] rs1 = new Resultado[rs.length + 1];
        
        for (int i = 0; i < rs.length; i++) {
            rs1[i] = new Resultado(rs[i].getLexema(), rs[i].getAtributo(), 
                    rs[i].getTipo());
            
        }
        rs1[rs.length] = new Resultado(r.getLexema(), r.getAtributo(), 
                    r.getTipo());
        return rs1;
    }
    
    public String leerArchivo(String archivo) throws IOException{
        String cadena;
        String resultado = "";
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        while((cadena = b.readLine())!=null) {
            resultado += cadena;
        }
        
        b.close();
        System.out.println(resultado);
        return resultado;
    }
}
