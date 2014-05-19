/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.data;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.*;

import toolbox.*;
import toolbox.information.Shannon;
import toolbox.io.CSVReader;
import toolbox.stats.Histogram;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;

/**
 *
 * @author paul
 */
public class Analyzer extends java.util.Observable {
    
    private static Logger logger;
    private String endLine;
    
    public Analyzer() {
        endLine = "\n";
    }
    
    static {
        logger = Utilities.getLogger(Analyzer.class, Level.INFO);
    }
    
    public static void main(String[] args) {
        Analyzer a = new Analyzer();
        int[] columns = new int[] { 1, 2, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        //a.analyzeFile("titanic.csv", columns, ",");
        a.analyzeFile("test1.csv", new int[] { 1, 2, 3, 4 }, ",");
        int[] array = new int[] { 1, 2, 3 };
        
        List<int[]> c = Utilities.getCondensedPermutations(array);
        for(int i = 0; i < c.size(); i++) {
            if(c.get(i).length == 2) {
                //Utilities.showArray(c.get(i));
            }
        }
        
        List<int[]> pairs = Utilities.getCondensedPermutations(columns);
        for(int i = 0; i < pairs.size(); i++) {
            if(pairs.get(i).length == 2) {
                //Utilities.showArray(pairs.get(i));
            }
        }
    }
    
    public void addWriterAppender(java.io.Writer writer) {
        logger.addAppender(new WriterAppender(new PatternLayout(Constants.WEB_PAGE_LOG_FORMAT), writer));
    }
    
    public void analyzeFile(String filename, String columnSeparator) {
        Logger logger = Utilities.getLogger(this.getClass(), Level.DEBUG);
        try {
            List<List<String>> columns = CSVReader.getAllColumns(filename, columnSeparator);
            for(int i = 0; i < columns.size(); i++) {
                List<String> current = columns.get(i);
                Histogram h = new Histogram(current);
                logger.info("column " + i);
                logger.info(h.toString("-------"));
                logger.info("entropy = " + h.getEntropy());
                logger.info("");
            }
        } catch(IOException e) {
            logger.error(e.getClass() + " in analyzeFile(" + filename + ", " + columnSeparator + "):  " + e.getMessage());
        }
    }
    
    public void analyzeFile(String filename, int[] cols, String columnSeparator) {
        //Logger logger = Utilities.getLogger(this.getClass(), Level.DEBUG);
        super.setChanged();
        try {
            List<List<String>> columnData = CSVReader.getColumns(filename, cols, columnSeparator);
            HashMap<Integer, Integer> colIndexMap = new HashMap<Integer, Integer>();
            for(int i = 0; i < columnData.size(); i++) {
                super.setChanged();
                List<String> current = columnData.get(i);
                colIndexMap.put(cols[i], i);
                Histogram h = new Histogram(current);
                //logger.info("column " + cols[i]);
                //super.notifyObservers("column " + cols[i]);super.setChanged();
                this.info("column " + cols[i]);
                //logger.info("<p>" + h.asXHTMLTable() + "</p>");
                //super.notifyObservers("<p>" + h.asXHTMLTable() + "</p>");super.setChanged();
                this.info("<p>" + h.asXHTMLTable() + "</p>");
                //logger.info("");
                //super.notifyObservers("");super.setChanged();
                this.info("");
            }
            
            List<int[]> pairs = Utilities.getCondensedPermutations(cols);
            for(int i = 0; i < pairs.size(); i++) {
                if(pairs.get(i).length == 2) {
                    //Utilities.showArray(pairs.get(i));
                    this.info(Utilities.arrayToString(pairs.get(i)));
                    int leftDataIndex = colIndexMap.get(pairs.get(i)[0]);
                    int rightDataIndex = colIndexMap.get(pairs.get(i)[1]);
                    this.info("mutual information = " + Shannon.getMutualInformation(columnData.get(leftDataIndex), columnData.get(rightDataIndex)));
                    this.info("entropy ratio = " + Shannon.getEntropyRatio(columnData.get(leftDataIndex), columnData.get(rightDataIndex)));
                }
            }
        } catch(IOException e) {
            logger.error(e.getClass() + " in analyzeFile(" + filename + ", " + Utilities.arrayToString(cols) + ", " + columnSeparator + "):  " + e.getMessage());
        }
    }

    public String getEndLine() {
        return endLine;
    }

    public void setEndLine(String endLine) {
        this.endLine = endLine;
    }
    
    /*public void postFile() {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://example.com/test");
        MultipartEntity entity = new MultipartEntity();
        entity.addPart("file1", new FileBody(new File("filetoUpload.jpg"), "application/jpg"));
        post.setEntity(entity);
        client.execute(post);
        client.getConnectionManager().shutdown();
    }*/
    
    private void info(String message) {
        if(logger.isInfoEnabled()) {
            logger.info(message);
            super.notifyObservers(message);
            super.setChanged();
        }
    }
}
