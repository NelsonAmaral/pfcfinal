/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.UnsupportedEncodingException;

/**
 *
 * @author Nelson.Amaral
 */
public class ValidaFormes {

        public static String formulario(String s) throws UnsupportedEncodingException {
            s = s.replaceAll("(?i)<script.*?>.*?</script.*?>", "EntradaBloqueada");
            s = s.replaceAll("(?i)<.*?javascript:.*?>.*?</.*?>", "EntradaBloqueada");
            s = s.replaceAll("(?i)<.*?\\s+on.*?>.*?</.*?>", "EntradaBloqueada");
            s = s.replaceAll("DELETE FROM", "EntradaBloqueada");
            s = s.replaceAll("\n", "");
            
            String resultado = new String(s.getBytes("ISO-8859-1"), "UTF-8");

            return resultado;
        }
           
        public static String removeMascara(String s) throws UnsupportedEncodingException{
            
            s = s.replaceAll("[^0-9]*", "");
            s = formulario(s);
            return s;
        }
       
}
