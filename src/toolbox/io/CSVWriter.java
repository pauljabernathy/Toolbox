/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.io;

import java.io.*;

/**
 *
 * @author paul
 */
public class CSVWriter {
    
    public static void writeArray(double[] nums, String filename, String header, String separator) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(filename));
        if(header != null && !header.equals("")) {
            writer.println(header);
        }
        for(double num : nums) {
            writer.print(num);
            writer.print(separator);
        }
        writer.flush();
        writer.close();
    }
}
