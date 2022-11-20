package br.ufes.inf.nemo.vpzy.logging;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import br.ufes.inf.nemo.vpzy.utils.ViewManagerUtils;

/**
 * A customized log handler for Visual Paradigm plug-ins that displays the logging messages in the
 * tool's message page using the View Manager.
 * 
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
 */
public class ViewManagerHandler extends Handler {
  public ViewManagerHandler() {
    setFormatter(new LogFormatter());
  }

  @Override
  public void publish(LogRecord record) {
    String message = getFormatter().format(record);
    ViewManagerUtils.showMessage(message, record.getLoggerName());
  }

  @Override
  public void flush() {
    // Nothing to do here.
  }

  @Override
  public void close() throws SecurityException {
    // Nothing to do here.
  }
}
