/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securefilesystem;
/**
 *
 * @author ASUS
 */
public class SecureFileSystem {

   public static void main(String[] args) {
        String key = "Bar12345Bar12345"; // 128 bit key
        String initVector = "RandomInitVector"; // 16 bytes IV
        
        RSA.GenerateKeyPair(); //Button in GUI
        String tmp = RSA.encrypt("Hello World".getBytes());
        System.out.println(RSA.Decrypt(tmp.getBytes()));
    }
}
