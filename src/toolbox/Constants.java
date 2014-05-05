/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox;

import org.apache.log4j.Level;

/**
 *
 * @author paul
 */
public class Constants {
    public static final Level DEFAULT_LOG_LEVEL = Level.INFO;
    public static final String DEFAULT_LOG_FORMAT = "%m%n";
    public static final String SAME_LINE_LOG_FORMAT = "%m";     //no carriage return, so the next logging statement is on the same line
    public static final String WEB_PAGE_LOG_FORMAT = "<br>%m%n";
    public static final String DEFAULT_SEPARATOR = ",";
    public static final String UNKNOWN = "unknown";
}
