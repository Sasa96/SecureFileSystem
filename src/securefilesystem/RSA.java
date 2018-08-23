/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securefilesystem;

import javax.crypto.*;
import java.security.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kord
 */
public class RSA {
    public static KeyPair keys;
    
    public static void GenerateKeyPair()
    {
        try {
            keys = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        } catch (Exception ex) {
        }
    }
    
    public static String encrypt(byte[] rawData) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, keys.getPublic());
            
            byte[] encrypted = cipher.doFinal(rawData);
            return encrypted.toString();
        } 
        
        catch (Exception ex) {
        }

        return null;
    }
    
     public static String Decrypt(byte[] rawData) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, keys.getPrivate());
            
            byte[] decrypted = cipher.doFinal(rawData);
            return decrypted.toString();
        } 
        
        catch (Exception ex) {
        
            System.out.println(ex.getMessage());
        
        }

        return null;
    }
}