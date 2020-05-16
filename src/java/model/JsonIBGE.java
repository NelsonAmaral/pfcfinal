/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONObject;

/**
 *
 * @author nelson_amaral
 */
public class JsonIBGE {

    public JsonIBGE() throws MalformedURLException, IOException {
        try{
        
        String url = "";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        

        BufferedReader in = new BufferedReader(
               new InputStreamReader(con.getInputStream())
        );
        String inputLine;
        StringBuffer response = new StringBuffer();
        while((inputLine = in.readLine()) != null){
            response.append(inputLine);
        }
        in.close();
        
            //System.out.println(response.toString());
            
            JSONObject objJson = new JSONObject(response.toString());
            
            System.out.println("Estado :"+ objJson.getString("microrregiao.mesorregiao.UF.nome"));
        
        }catch(Exception e){
            System.out.println("JsonIBGE :"+e);
        }
    }
    
}
