/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securefilesystem;

import java.io.*;

/**
 *
 * @author ASUS
 */
public class FileHandler {

    public static String ReadFile(String fileName, boolean HasHeader) {

        String line = null;
        String text = "";

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader
                    = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader
                    = new BufferedReader(fileReader);

            if (HasHeader) {
                for (int i = 1; i <= 4; i++) {
                    bufferedReader.readLine();
                }

            }

            while ((line = bufferedReader.readLine()) != null) {
                text = text + line + "\n";
            }
            //remove last \n
            //text = text.substring(0, text.length() - 1);

            // Always close files.
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println(
                    "file not Found Exception '"
                    + fileName + "'");
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file '"
                    + fileName + "'");
            // Or we could just do this: 
            // ex.printStackTrace();
        }

        return text;
    }

    public static void WriteFile(String text, String fileName, String CreatorID, String CreatorMail, boolean putHeader) {

        // The name of the file to open.
        String header = "";
        if (putHeader) {
            header = "creator ID: " + CreatorID + "\n"
                    + "creator email:" + CreatorMail + "\n"
                    + "file size: \n"
                    + "----------------------";
        }
        try {
            // Assume default encoding.
            FileWriter fileWriter
                    = new FileWriter(fileName);

            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter
                    = new BufferedWriter(fileWriter);

            // Note that write() does not automatically
            // append a newline character.
            if (putHeader) {
                bufferedWriter.write(header);
                bufferedWriter.newLine();
            }
            bufferedWriter.write(text);

            // Always close files.
            bufferedWriter.close();
        } catch (IOException ex) {
            System.out.println(
                    "Error writing to file '"
                    + fileName + "'");

        }

    }
    
    public static void clearFile(String fileName) throws IOException {
        FileWriter fwOb = new FileWriter(fileName, false); 
        PrintWriter pwOb = new PrintWriter(fwOb, false);
        pwOb.flush();
        pwOb.close();
        fwOb.close();
    }
}
