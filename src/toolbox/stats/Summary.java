/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.stats;

/**
 *
 * @author paul
 */
public class Summary {
    
    public double min;
    public double firstQ;
    public double median;
    public double mean;
    public double thirdQ;
    public double max;
    public double sd;
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String sep = "\t";
        sb.append("min").append(sep).append("1st Q").append(sep).append("median").append(sep).append("mean").append(sep).append("3rd Q").append(sep).append("max").append(sep).append("sd");
        sb.append("\n");
        sb.append(min).append(sep).append(firstQ).append(sep).append(median).append(sep).append(mean).append(sep).append(thirdQ).append(sep).append(max).append(sep).append(sd);
        return sb.toString();
    }
}
