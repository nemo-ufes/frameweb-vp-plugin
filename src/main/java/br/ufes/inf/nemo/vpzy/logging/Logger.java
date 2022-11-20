package br.ufes.inf.nemo.vpzy.logging;

import java.util.logging.Level;

/**
 * A global (static) logger for the plug-in. This class tries to mimick the behavior of logging in a
 * more object-oriented application, in which each class has its {@code logger} attribute and
 * performs logging with {@code logger.log(...)}. Here, instead, classes should use
 * {@code Logger.log(...)}, calling one of the static methods of the class.
 * 
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
 */
public final class Logger {
  /** The default logger name. */
  private static final String DEFAULT_LOGGER_NAME = "Plug-in Log";

  /** The default logging level for the plug-in. */
  private static final Level DEFAULT_LOGGING_LEVEL = Level.INFO;

  /** The default logger to be used by the plug-in. */
  private static java.util.logging.Logger logger =
      java.util.logging.Logger.getLogger(DEFAULT_LOGGER_NAME);

  // Default logger configuration.
  static {
    logger.addHandler(new ViewManagerHandler());
    logger.setLevel(DEFAULT_LOGGING_LEVEL);
  }

  /**
   * Replace the default logger configuration with a plug-in specific configuration.
   */
  public static void setup(String loggerName, Level defaultLevel) {
    logger = java.util.logging.Logger.getLogger(loggerName);
    logger.addHandler(new ViewManagerHandler());
    logger.setLevel(defaultLevel);
  }

  /** Delegates the logging to the current logger. */
  public static void log(Level level, String msg) {
    logger.log(level, msg);
  }

  /** Delegates the logging to the current logger. */
  public static void log(Level level, String msg, Object param1) {
    logger.log(level, msg, param1);
  }

  /** Delegates the logging to the current logger. */
  public static void log(Level level, String msg, Object[] params) {
    logger.log(level, msg, params);
  }

  /** Delegates the logging to the current logger. */
  public static void log(Level level, String msg, Throwable thrown) {
    logger.log(level, msg, thrown);
  }
}
