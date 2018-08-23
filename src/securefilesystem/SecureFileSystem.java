/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securefilesystem;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;

/**
 *
 * @author ASUS
 */
public class SecureFileSystem {

   public static void main(String[] args) throws Exception {
        String key = "Bar12345Bar12345"; // 128 bit key
        String initVector = "RandomInitVector"; // 16 bytes IV


        RSA.buildKeyPair(); //called by a button in GUI
        
        
        // encrypt the message

        byte [] encrypted = RSA.encrypt("This is a secret message");     

        System.out.println(new String(encrypted));  // <<encrypted message>>
        
        // decrypt the message
        byte[] secret = RSA.decrypt(encrypted);                                 
        System.out.println(new String(secret));     // This is a secret message

    }
}
