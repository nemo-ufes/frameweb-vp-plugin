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
  /** The logger to be used by the plug-in. */
  private static java.util.logging.Logger logger;

  /** Sets up logging for the plug-in. */
  public static void setup(String loggerName, Level level) {
    logger = java.util.logging.Logger.getLogger(loggerName);
    logger.setLevel(level);

    // Only add a handler if not already present.
    if (logger.getHandlers().length == 0)
      logger.addHandler(new ViewManagerHandler());
  }

  /* Change the logging level. */
  public static void setLevel(Level level) {
    logger.setLevel(level);
  }

  /** Delegates the logging to the current logger. */
  public static void log(Level level, String msg) {
    if (logger != null)
      logger.log(level, msg);
  }

  /** Delegates the logging to the current logger. */
  public static void log(Level level, String msg, Object param1) {
    if (logger != null)
      logger.log(level, msg, param1);
  }

  /** Delegates the logging to the current logger. */
  public static void log(Level level, String msg, Object[] params) {
    if (logger != null)
      logger.log(level, msg, params);
  }

  /** Delegates the logging to the current logger. */
  public static void log(Level level, String msg, Throwable thrown) {
    if (logger != null)
      logger.log(level, msg, thrown);
  }
}
