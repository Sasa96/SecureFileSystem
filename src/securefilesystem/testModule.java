/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securefilesystem;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 *
 * @author kord
 */
public class testModule {

    long encryptiontime;
    long decryptiontime;

    public void timeTest() {

        try {
            long start = System.currentTimeMillis();
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            SecretKey secretKey = keyGen.generateKey();
            String text = securefilesystem.FileHandler.ReadFile("hello man.txt", false);
            String encrypted = securefilesystem.AES.encrypt(secretKey, "RandomInitVector", new String(text));

            long etest = System.currentTimeMillis() - start;

            start = System.currentTimeMillis();
            String secret = securefilesystem.AES.decrypt(secretKey, "RandomInitVector", encrypted);
            long dtest = System.currentTimeMillis() - start;

            
            encryptiontime = etest;
            decryptiontime = dtest;
        } catch (Exception ex) {
            Logger.getLogger(testModule.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
        

}
