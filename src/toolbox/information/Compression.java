/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package toolbox.information;

import toolbox.util.ListArrayUtil;
import toolbox.stats.*;
import java.util.List;

/**
 *
 * @author paul
 */
public class Compression {
    
    public static CompressionResult compress(String text) {
        CompressionResult result = null;
        Histogram histogram = getCharFrequencies(text);
        System.out.println(histogram.toString());
        try {
            List<ProbDist> halves = histogram.getProbDist().split(.5, true);
            System.out.println(halves.get(0));
            System.out.println(halves.get(1));
        } catch(ProbabilityException e) {
            System.err.println("Probability Exception trying to split the histogram:  " + e.getMessage());
        }
        
        return result;
    }
    
    public static Histogram getCharFrequencies(String text) {
        return new Histogram(convertToChar(text));
    }
    
    public static Character[] convertToChar(String text) {
        if(text == null || text.equals("")) {
            return new Character[0];
        }
        Character[] result = new Character[text.length()];
        for(int i = 0; i < result.length; i++) {
            result[i] = Character.toLowerCase(text.charAt(i));
        }
        return result;
    }
    
    public static int[] getASCIIBinary(String text) {
        if(text == null || text.equals("")) {
            return new int[0];
        }
        int bitsPerChar = 8;
        int[] result = new int[text.length() * bitsPerChar];
        int[] current = null;//new int[bitsPerChar];
        for(int i = 0; i < text.length(); i++) {
            System.out.println((int)(text.charAt(0)));
            current = ListArrayUtil.toBinaryArray((int)(text.charAt(0)), bitsPerChar);
            result[i * bitsPerChar] = current[0];
            result[i * bitsPerChar + 1] = current[1];
            result[i * bitsPerChar + 2] = current[2];
            result[i * bitsPerChar + 3] = current[3];
            result[i * bitsPerChar + 4] = current[4];
            result[i * bitsPerChar + 5] = current[5];
            result[i * bitsPerChar + 6] = current[6];
            result[i * bitsPerChar + 7] = current[7];
        }
        return result;
    }
}
