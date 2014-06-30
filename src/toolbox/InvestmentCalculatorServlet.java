/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.util.List;

import toolbox.stats.*;
import toolbox.util.MathUtil;

/**
 *
 * @author paul
 */
public class InvestmentCalculatorServlet extends HttpServlet {
    
    private static final String SNP_FILE = "sp500.csv";
    public static final String INITIAL_PARAM = "initial";
    public static final String CONTR_PARAM = "regContr";
    public static final String NUM_DAYS_PARAM = "numDays";
    public static final String NUM_YEARS_PARAM = "numDays";
    public static final String INTERVAL_PARAM = "interval";
    public static final String TARGET_PARAM = "target";
    
    public static final double DEFAULT_INITIAL = 16.66;
    public static final double DEFAULT_CONTRIBUTION = 100;
    public static final double DEFAULT_NUM_YEARS = 25;
    public static final double DAYS_PER_YEAR = 365.25;
    public static final int DEFAULT_NUM_DAYS = (int)(DAYS_PER_YEAR * DEFAULT_NUM_YEARS);
    public static final int DEFAULT_INTERVAL = 15;
    public static final double DEFAULT_TARGET = 1000000.0;
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        double initial = DEFAULT_INITIAL;
        double regularContribution = DEFAULT_CONTRIBUTION;
        double numYears = DEFAULT_NUM_YEARS;
        int numDays = DEFAULT_NUM_DAYS;
        int interval = DEFAULT_INTERVAL;
        double target = DEFAULT_TARGET;
        if(request.getParameter(INITIAL_PARAM) != null) {
            try {
                initial = Double.parseDouble(request.getParameter(INITIAL_PARAM));
            } catch(NumberFormatException e) {
                //use default
                out.println("<br>invalid initial investment entered, using default of $" + DEFAULT_INITIAL);
                initial = DEFAULT_INITIAL;
            }
        }
        if(request.getParameter(CONTR_PARAM) != null) {
            try {
                regularContribution = Double.parseDouble(request.getParameter(CONTR_PARAM));
            } catch(NumberFormatException e) {
                //use default
                out.println("<br>invalid contribution entered, using default of $" + DEFAULT_CONTRIBUTION);
            }
        }
        /*if(request.getParameter(NUM_DAYS_PARAM) != null) {
            try {
                numDays = Integer.parseInt(request.getParameter(NUM_DAYS_PARAM));
            } catch(NumberFormatException e) {
                out.println("<br>invalid num days param entered, using default of " + DEFAULT_NUM_DAYS);
            }
        }*/
        if(request.getParameter(NUM_YEARS_PARAM) != null) {
            try {
                numYears = Double.parseDouble(request.getParameter(NUM_YEARS_PARAM));
                numDays = (int)(DAYS_PER_YEAR * numYears);
            } catch(NumberFormatException e) {
                out.println("<br>invalid number of years entered, using default of " + DEFAULT_NUM_YEARS + " years");
            }
        }
        if(request.getParameter(INTERVAL_PARAM) != null) {
            try {
                interval = Integer.parseInt(request.getParameter(INTERVAL_PARAM));
            } catch(NumberFormatException e) {
                out.println("<br>invalid interval entered, using default of " + DEFAULT_INTERVAL + " days");
            }
        }
        if(request.getParameter(TARGET_PARAM) != null) {
            try {
                target = Double.parseDouble(request.getParameter(TARGET_PARAM));
            } catch(NumberFormatException e) {
                out.println("<br>invalid target entered, using default of " + DEFAULT_TARGET);
            }
        }
        
        InvestmentCalculator snp = new InvestmentCalculator();
        ////List<Double> doTargetContributionSim(String inputFilename, double initial, int numDays, double targetEnd, int interval, int numRuns)
        List<Double> results = snp.doTargetContributionSim(SNP_FILE, initial, numDays, target, interval, 10000);
        Summary sum = MathUtil.summary(results);
        out.println("<br>min:  " + sum.min);
        out.println("<br><b>median:  " + sum.median + "</b>");
        out.println("<br>mean:  " + sum.mean);
        out.println("<br>max:  " + sum.max);
        out.println("<br>sd:  " + sum.sd);
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }
}
