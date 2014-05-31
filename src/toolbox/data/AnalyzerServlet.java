/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.data;

import javax.servlet.http.*;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import toolbox.Constants;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import java.util.Observer;
import java.util.Observable;
import java.util.regex.*;

/**
 *
 * @author paul
 */
@WebServlet("/AnalyzerServlet")
@MultipartConfig
public class AnalyzerServlet extends HttpServlet implements Observer {
    
    private static final String DEFAULT_FILE_NAME = "thefile.csv";
    private PrintWriter writer;
    protected static final String ENTER_COLUMNS_MESSAGE = "Please enter a list of columns";
    protected static final String INVALID_COLUMNS_FORMAT_MESSAGE = "Please enter columns formated as numbers separated by commas";
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        this.writer = response.getWriter();
        writer.println("<html><head><title>Analyzer Servlet</title><script src=\"ga.js\"></script></head><body>");
        Analyzer a = new Analyzer();
        //a.addWriterAppender(writer);
        a.addObserver(this);
        a.setEndLine("<br>\n");
        
        String fileName = DEFAULT_FILE_NAME;
        String columnStr = "";
        //fileName = "thefile.csv";
        File dir = new File(getServletContext().getRealPath(""));
        
        if(request.getSession().getAttribute("fileName") != null) {
            fileName = (String)request.getSession().getAttribute("fileName");
            fileName = getServletContext().getRealPath("") + File.separator + fileName;
            //a.analyzeFile(fileName, new int[] { 1, 2, 3, 4 }, ",");
        } else {
            writer.println("<br>did not receive a filename, using default:  " + fileName);
        }
        int[] columns = null;
        if(request.getParameter("columns") != null) {
            try {
                columns = getColumns(request.getParameter("columns"));
                a.analyzeFile(fileName, columns, ",");
            } catch(Exception e) {
                writer.println("<p>" + e.getMessage() + "</p>");
            }
        }
        //a.analyzeFile(fileName, new int[] { 1, 2, 5, 6, 7, 8, 9, 10, 11, 12, 13}, ",");
        writer.println("<br><br>Known issues:");
        writer.println("<ul>");
        writer.println("<li>when column 0 is entered, the list of columns is not computed correctly and the mutual information comparisons do not take place</li>");
        writer.println("</ul>");
        //RequestDispatcher dispatcher = request.getRequestDispatcher("footer.jsp");
        //dispatcher.include(request, response);
        writer.println("<object name=\"footer\" type=\"text/html\" data=\"footer.html\"></object>");
        writer.println("</body></html>");
    }
    
    //TODO:  remove spaces in the middle of the string so it is more user friendly
    protected int[] getColumns(String columnStr) throws Exception {
        if(columnStr == null || columnStr.length() == 0) {
            throw new Exception(AnalyzerServlet.ENTER_COLUMNS_MESSAGE);
        }
        
        columnStr = columnStr.trim();
        if(columnStr.startsWith(",")) {
            if(columnStr.length() == 1) {
                throw new Exception(AnalyzerServlet.INVALID_COLUMNS_FORMAT_MESSAGE);
            } else {
                //remove the , at the beginning
                columnStr = columnStr.substring(1);
            }
        }
        if(columnStr.endsWith(",")) {
            if(columnStr.length() == 1) {
                throw new Exception(AnalyzerServlet.INVALID_COLUMNS_FORMAT_MESSAGE);
            } else {
                //remove the , at the end
                columnStr = columnStr.substring(0, columnStr.length() - 1);
            }
        }
        Pattern p = Pattern.compile("[^\\d^,]");
        Matcher m = p.matcher(columnStr);
        if(m.find()) {
            //found a character that is not a number or comma
            //System.out.println(columnStr);
            //System.out.println(m.start() + " " + m.group());
            throw new Exception(AnalyzerServlet.INVALID_COLUMNS_FORMAT_MESSAGE);
        }
        
        
        String[] nums = columnStr.split(",");
        if(nums.length < 1) {
            //empty list of numbers
            throw new Exception(AnalyzerServlet.INVALID_COLUMNS_FORMAT_MESSAGE);
        }
        
        int[] result = new int[nums.length];
        for(int i = 0; i < nums.length; i++) {
            result[i] = Integer.parseInt(nums[i]);
        }
        return result;
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();
        System.out.println("demo");

        if (!ServletFileUpload.isMultipartContent(request)) {
            // if not, we stop here
            //PrintWriter writer = response.getWriter();
            writer.println("Error: Form must has enctype=multipart/form-data.");
            writer.flush();
            return;
        }

        // configures upload settings
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // sets temporary location to store files
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        //ServletFileUpload upload = new ServletFileUpload(factory);


        // constructs the directory path to store upload file
        // this path is relative to application's directory
        String uploadPath = getServletContext().getRealPath("")+ File.separator;// + UPLOAD_DIRECTORY;
        //writer.println("<br>uploadPath = " + uploadPath);writer.flush();

        // creates the directory if it does not exist
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String fileName = DEFAULT_FILE_NAME;
        try {
            // parses the request's content to extract file data
            //System.out.println(uploadPath);
            Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
            //writer.println("<br>filePart.getName() = " + filePart.getName());   //filePart.getName() gives the name of the file input item from the form page
            //writer.println("<br>filePart.getSubmittedFileName() = " + filePart.getSubmittedFileName()); //getSubmittedFileName() gives the real file name
            //writer.println("<br>fileName = " + fileName);
            String filePath = uploadPath + File.separator + fileName;   //use the generic file name so that new files overwrite the old so that we don't proliferate files on the server
            //writer.println("<br>file will be " + filePath);writer.flush();

            filePart.write(filePath);
            request.setAttribute("message","Upload has been done successfully!");
            //writer.println("demo Success");
        } catch (Exception ex) {
            request.setAttribute("message","There was an error: " + ex.getMessage());
            writer.println("demo Fail: " +   ex.getMessage() );
        }
        
        request.getSession().setAttribute("fileName", fileName);
        doGet(request, response);
    }
    
    public void update(Observable o, Object arg) {
        if(o instanceof Analyzer) {
            this.writer.println("<br>" + arg);
            this.writer.flush();
        }
    }
}


