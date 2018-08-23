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

        //String encrypted = AES.encrypt(key, initVector, "Hello World");
        //System.out.println(encrypted);
        //System.out.println(AES.decrypt(key, initVector, encrypted));
        
        Cipher cipher;
        Cipher cipher2;
        try{
            KeyPair keys = KeyPairGenerator.getInstance("RSA").generateKeyPair();
            cipher = Cipher.getInstance("RSA");
            
            cipher2 = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, keys.getPublic()); 
            
            cipher2.init(Cipher.DECRYPT_MODE,keys.getPrivate());
         
            
         
        }
        
        catch(Exception e)
        {
            
        };
        
     // generate public and private keys
        KeyPair keyPair = RSA.buildKeyPair();
        PublicKey pubKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        
        // encrypt the message
        byte [] encrypted = RSA.encrypt(pubKey, "This is a secret message");     
        System.out.println(new String(encrypted));  // <<encrypted message>>
        
        // decrypt the message
        byte[] secret = RSA.decrypt(privateKey, encrypted);                                 
        System.out.println(new String(secret));     // This is a secret message

    }
}
