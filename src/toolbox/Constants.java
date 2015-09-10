/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox;

import org.apache.logging.log4j.Level;

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
    
    public static final String FILENAME_CANNOT_BE_NULL = "bad input - filename cannot be null";
    public static final String FILENAME_CANNOT_BE_EMPTY = "bad input - filename cannot be empty";
    public static final String FEATURE_COLUMNS_CANNOT_BE_NULL = "bad input - feature columns cannot be null";
    public static final String FEATURE_COLUMNS_CANNOT_BE_EMPTY = "bad input - feature columns cannot be empty";
}
