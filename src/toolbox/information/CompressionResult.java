/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package toolbox.information;

import java.util.Map;
import java.util.HashMap;

/**
 *
 * @author paul
 */
public class CompressionResult {
    
    public Map<Character, int[]> lookup;
    public int[] data;
    
    public CompressionResult(Map<Character, int[]> lookup, int[] data) {
        this.lookup = lookup;
        this.data = data;
    }
}
