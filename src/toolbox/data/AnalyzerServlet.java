/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.data;

import javax.servlet.http.*;
import java.io.*;
import javax.servlet.ServletException;
import toolbox.Constants;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author paul
 */
@WebServlet("/AnalyzerServlet")
@MultipartConfig
public class AnalyzerServlet extends HttpServlet {
    
    private static final String UPLOAD_PATH = "C:\\Program Files\\Apache Software Foundation\\Tomcat 8.0\\webapps\\analyzer";
    private static final String UPLOAD_DIRECTORY = "C:\\Program Files\\Apache Software Foundation\\Tomcat 8.0\\webapps\\analyzer";
    private static final String DEFAULT_FILE_NAME = "thefile.csv";
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.println("<html><head><title>Analyzer Servlet</title></head><body>");
        Analyzer a = new Analyzer();
        a.addWriterAppender(writer);
        a.setEndLine("<br>\n");
        
        String fileName = DEFAULT_FILE_NAME;
        //fileName = "thefile.csv";
        File dir = new File(getServletContext().getRealPath(""));
        
        //writer.println("<br>in doGet()");
        if(request.getSession().getAttribute("fileName") != null) {
            fileName = (String)request.getSession().getAttribute("fileName");
            //writer.println("<br>using filename " + fileName);
            fileName = getServletContext().getRealPath("") + File.separator + fileName;
            //writer.println("<br>using filename " + fileName);
            //a.analyzeFile(fileName, new int[] { 1, 2, 5, 6, 7, 8, 9, 10, 11, 12, 13}, ",");
            a.analyzeFile(fileName, new int[] { 1, 2, 3, 4 }, ",");
        } else {
            writer.println("<br>did not receive a filename, using default:  " + fileName);
        }
        //a.analyzeFile(fileName, new int[] { 1, 2, 5, 6, 7, 8, 9, 10, 11, 12, 13}, ",");
        writer.println("</body></html>");
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
}


