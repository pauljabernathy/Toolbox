/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.random;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

import toolbox.util.ListArrayUtil;

/**
 *
 * @author paul
 */
public class RandomServlet extends HttpServlet {
    
    //to allow for more than one way to specify the distribution; who knows if this is the best design, but seems user friendly
    public static final String[] UNIFS = { "unif", "uniform" };
    public static final String[] NORMS = { "norm", "normal", "gaussian" };
    public static final String[] BINOMS = { "binom", "binomial" };
    public static final String[] EXP = { "exp", "exponential" };
    public static final String[] POISSON = { "pois", "poiss", "poisson" };
    
    public static final String COUNT = "count";                 //for all of them
    public static final String MIN = "min";                     //for uniform
    public static final String MAX = "max";                     //for uniform
    public static final String MEAN = "mean";                   //for normal
    public static final String STANDARD_DEVIATION = "std";      //for normal
    public static final String PROBABILITY = "p";               //for binomial
    public static final String SUCCESS = "success";             //for binomial
    public static final String FAILURE = "failure";             //for binonial
    public static final String LAMBDA = "lambda";               //for exponential
    
    public static final String DISTRIBUTION_TYPE_PARAM = "distribution";
    public static final String OUTPUT_CHANNEL_PARAM = "output";     //browser vs web service
    public static final String OUTPUT_FORMAT_PARAM = "format";
    
    public static final int BROWSER_OUTPUT = 1;
    public static final int WEB_SERVICE_OUTPUT = 2;
    public static final String BROWSER_OUTPUT_STR = "web";
    public static final String WEB_SERVICE_OUTPUT_STR = "ws";
    
    public static final String HTML_FORMAT = "html";
    public static final String XML_FORMAT = "xml";
    //TODO:  one day have JSON; I guess I'd have to learn it first...
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if(this.isWebService(request.getParameter(OUTPUT_CHANNEL_PARAM))) {
            doGetWebService(request, response);
        } else {
            doGetBrowser(request, response);
        }
    }
    
    private void doGetBrowser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String dist = "none";
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        if(request.getParameter(DISTRIBUTION_TYPE_PARAM) != null) {
            dist = request.getParameter(DISTRIBUTION_TYPE_PARAM);
        }
        
        int count = 0;
        //double[] result = new double[] { };
        List result = new ArrayList<Double>();
        try {
            count = Integer.parseInt(request.getParameter(COUNT));
            if(ListArrayUtil.contains(UNIFS, dist)) {
                if(request.getParameter(MIN) != null) {
                    //out.println("min = " + Double.parseDouble(request.getParameter(MIN)));
                }
                if(request.getParameter(MAX) != null) {
                    //out.println("max = " + Double.parseDouble(request.getParameter(MAX)));
                }
                result = doUniform(request);
            } else if(ListArrayUtil.contains(NORMS, dist)) {
                result = doNormal(request);
            } else if(ListArrayUtil.contains(BINOMS, dist)) {
                result = doBinomial(request);
            } else if(ListArrayUtil.contains(EXP, dist)) {
                result = doExponential(request);
            } else if(ListArrayUtil.contains(POISSON, dist)) {
                //do Possion
                //out.println("poisson");
            } else {
                out.println("Please give a valid distribution");
            }
            out.println(ListArrayUtil.listToString(result));
        }
        catch(NumberFormatException e) {
            out.println("please enter a valid count");
        }
        catch(Exception e) {
            out.println(e.getMessage());
            //out.println("</body></html>");
            return;
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("footer.jsp");
        dispatcher.include(request, response);
        out.println("</body></html>");
    }
    
    private List<Double> doUniform(HttpServletRequest request) throws Exception {
        try {
            double min = 0.0;
            if(request.getParameter(MIN) != null) {
                min = Double.parseDouble(request.getParameter(MIN));
            }
            double max = 1.0;
            if(request.getParameter(MAX) != null) {
                max = Double.parseDouble(request.getParameter(MAX));
            }
            //double[] nums = Random.getUniformDoubles(Integer.parseInt(request.getParameter(COUNT)), min, max);
            List<Double> nums = Random.getUniformDoublesList(Integer.parseInt(request.getParameter(COUNT)), min, max);
            return nums;
        } catch(Exception e) {
            //return("please make sure that the mean and standard deviation are entered correctly");
            throw new Exception("please make sure that the min and max are entered correctly");
        }
    }
    
    private List<Double> doNormal(HttpServletRequest request) throws Exception{
        
        try {
            double mu = Double.parseDouble(request.getParameter(MEAN));
            double sigma = Double.parseDouble(request.getParameter(STANDARD_DEVIATION));
            //double[] nums = Random.rnorm(Integer.parseInt(request.getParameter(COUNT)), mu, sigma);
            List<Double> nums = Random.rnormList(Integer.parseInt(request.getParameter(COUNT)), mu, sigma);
            return nums;
        } catch(Exception e) {
            //return("please make sure that the mean and standard deviation are entered correctly");
            throw new Exception("please make sure that the mean and standard deviation are entered correctly");
        }
    }
    
    private List doBinomial(HttpServletRequest request) throws Exception {
        try {
            double p = Double.parseDouble(request.getParameter(PROBABILITY));
            //int[] nums = Random.rbinom(Integer.parseInt(request.getParameter(COUNT)), p);
            if(request.getParameter(SUCCESS) != null && request.getParameter(FAILURE) != null & !request.getParameter(SUCCESS).equals("") && !request.getParameter(FAILURE).equals("")) {
                List<String> result = Random.rbinom(Integer.parseInt(request.getParameter(COUNT)), p, request.getParameter(SUCCESS), request.getParameter(FAILURE));
                return result;
            } else {
                List<Integer> nums = Random.rbinom(Integer.parseInt(request.getParameter(COUNT)), p, 1, 0);
                return nums;
            }
        } catch(Exception e) {
            throw new Exception("please make sure that the probability is entered correctly");
        }
    }
    
    private List<Double> doExponential(HttpServletRequest request) throws Exception {
        try {
            double lambda = Double.parseDouble(request.getParameter(LAMBDA));
            //double[] nums = Random.rexp(Integer.parseInt(request.getParameter(COUNT)), lambda);
            List<Double> nums = Random.rexpList(Integer.parseInt(request.getParameter(COUNT)), lambda);
            return nums;
        } catch(Exception e) {
            //return("please make sure that the mean and standard deviation are entered correctly");
            throw new Exception("please make sure that the rate is entered correctly");
        }
    }
    
    private void doGetWebService(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String dist = "none";
        PrintWriter out = response.getWriter();
        if(request.getParameter(DISTRIBUTION_TYPE_PARAM) != null) {
            dist = request.getParameter(DISTRIBUTION_TYPE_PARAM);
        }
        
        int count = 0;
        //double[] result = new double[] { };
        List result = new ArrayList<Double>();
        try {
            count = Integer.parseInt(request.getParameter(COUNT));
            if(ListArrayUtil.contains(UNIFS, dist)) {
                result = doUniform(request);
            } else if(ListArrayUtil.contains(NORMS, dist)) {
                result = doNormal(request);
            } else if(ListArrayUtil.contains(BINOMS, dist)) {
                result = doBinomial(request);
            } else if(ListArrayUtil.contains(EXP, dist)) {
                result = doExponential(request);
            } else if(ListArrayUtil.contains(POISSON, dist)) {
                //do Possion
                //out.println("poisson");
            } else {
                out.println("Please give a valid distribution");
            }
            out.println(toXML(result));
        }
        catch(NumberFormatException e) {
            out.println("please enter a valid count");
        }
        catch(Exception e) {
            out.println(e.getMessage());
            return;
        }
    }
    
    private boolean isWebService(String param) {
        if(param == null) {
            return false;
        }
        if(param.equals(WEB_SERVICE_OUTPUT_STR)) {
            return true;
        } 
        try {
            if(Integer.parseInt(param) == WEB_SERVICE_OUTPUT) {
                return true;
            }
        } catch(NumberFormatException e) {
            //nothing to do here - this just means we couldn't parse the number, but the user could have been entering it in text format; no need to throw the Exception
        }
        return false;
    }
    
    protected String toXML(double[] numbers) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("\n");
        sb.append("<numbers>").append("\n");
        if(numbers != null) {
        for(double num : numbers) {
            sb.append("\t<number value=\"").append(num).append("\"/>\n");
        }
        }
        sb.append("</numbers>");
        return sb.toString();
    }
    
    protected String toXML(List numbers) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("\n");
        sb.append("<numbers>").append("\n");
        if(numbers != null) {
        for(Object num : numbers) {
            sb.append("\t<number value=\"").append(num).append("\"/>\n");
        }
        }
        sb.append("</numbers>");
        return sb.toString();
    }
}
