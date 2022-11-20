package br.ufes.inf.nemo.vpzy.logging;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * A customized formatter for Visual Paradigm plug-in log handlers (Logger).
 * 
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
 */
public class LogFormatter extends Formatter {
  /** Pattern for the formatting of the date/time of the log message. */
  private static final String DATE_TIME_FORMAT_PATTERN = "dd/MM/yyyy HH:mm:ss.SSS";

  /** The formatter for the date/time of the log message. */
  private static final DateTimeFormatter DATE_TIME_FORMATTER =
      DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_PATTERN);

  @Override
  public String format(LogRecord record) {
    String formattedMessage = formatMessage(record);
    if (record.getThrown() != null)
      formattedMessage = formattedMessage + " (" + record.getThrown().toString() + ")";
    String outputFormat = "[%1$s] %2$s: %3$s";
    return String.format(outputFormat,
        DATE_TIME_FORMATTER.format(LocalDateTime.ofInstant(record.getInstant(), ZoneOffset.UTC)),
        record.getLevel().getName(), formattedMessage);
  }
}
