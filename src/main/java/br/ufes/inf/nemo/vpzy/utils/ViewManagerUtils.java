package br.ufes.inf.nemo.vpzy.utils;


import java.awt.Component;
import java.util.logging.Level;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import com.vp.plugin.ViewManager;
import com.vp.plugin.view.IDialogHandler;
import br.ufes.inf.nemo.vpzy.logging.Logger;

/**
 * Utility class that provides helper methods regarding the View Manager in Visual Paradigm.
 *
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
 */
public final class ViewManagerUtils {
  /** Value that indicates an error message type. */
  public static final int ERROR_MESSAGE = JOptionPane.ERROR_MESSAGE;

  /** Value that indicates an information message type. */
  public static final int INFORMATION_MESSAGE = JOptionPane.INFORMATION_MESSAGE;

  /** Value that indicates a plain message type. */
  public static final int PLAIN_MESSAGE = JOptionPane.PLAIN_MESSAGE;

  /** Value that indicates a question message type. */
  public static final int QUESTION_MESSAGE = JOptionPane.QUESTION_MESSAGE;

  /** Value that indicates a warning message type. */
  public static final int WARNING_MESSAGE = JOptionPane.WARNING_MESSAGE;

  /** The Visual Paradigm view manager. */
  private static final ViewManager viewManager = ApplicationManagerUtils.instance.getViewManager();

  /**
   * Prints a message in Visual Paradigm's message pane. Although this method is directly available,
   * we recommend the use of the {@code Logger} class for printing messages in the message pane.
   * 
   * @param message The message to print.
   * @param pluginName The name of the plug-in that is printing the message.
   * @see br.ufes.inf.nemo.vpzy.logging.Logger
   */
  public static void showMessage(String message) {
    viewManager.showMessage(message);
  }

  /**
   * Prints a message in Visual Paradigm's message pane, under a specific plug-in's tab. Although
   * this method is directly available, we recommend the use of the {@code Logger} class for
   * printing messages in the message pane.
   * 
   * @param message The message to print.
   * @param pluginName The name of the plug-in that is printing the message.
   * @see br.ufes.inf.nemo.vpzy.logging.Logger
   */
  public static void showMessage(String message, String pluginName) {
    viewManager.showMessage(message, pluginName);
  }

  /**
   * Shows a message dialog with a given message.
   * 
   * @param message The message to be displayed in the dialog.
   */
  public static void showMessageDialog(Object message) {
    Logger.log(Level.FINEST, "Showing message in a dialog: {0}", message);
    Component parentFrame = viewManager.getRootFrame();
    viewManager.showMessageDialog(parentFrame, message);
  }

  /**
   * Shows a message dialog with a given message and title, also indicating the message type.
   * 
   * @param message The message to be displayed in the dialog.
   * @param title The title of the diagram.
   * @param messageType The type of the message, one of the {@code *_MESSAGE} constants in this
   *        class.
   */
  public static void showMessageDialog(Object message, String title, int messageType) {
    Logger.log(Level.FINEST, "Showing message in a dialog, with title \"{0}\" and type {1}: {2}",
        new Object[] {title, messageType, message});
    Component parentFrame = viewManager.getRootFrame();
    viewManager.showMessageDialog(parentFrame, message, title, messageType);
  }

  /**
   * Shows a message dialog with a given message and title, also indicating the message type and a
   * customized icon.
   * 
   * @param message The message to be displayed in the dialog.
   * @param title The title of the diagram.
   * @param messageType The type of the message, one of the {@code *_MESSAGE} constants in this
   *        class.
   * @param icon The customized icon to use in the dialog.
   */
  public static void showMessageDialog(Object message, String title, int messageType,
      Icon icon) {
    Logger.log(Level.FINEST,
        "Showing message in a dialog, with title \"{0}\", type {1} and icon {2}: {3}",
        new Object[] {title, messageType, icon, message});
    Component parentFrame = viewManager.getRootFrame();
    viewManager.showMessageDialog(parentFrame, message, title, messageType, icon);
  }

  /**
   * Shows a dialog based on the given dialog handler.
   * 
   * @param dialogHandler The given dialog handler.
   */
  public static void showDialog(IDialogHandler dialogHandler) {
    Logger.log(Level.FINEST, "Showing a dialog based on dialog handler {0}",
        dialogHandler.getClass().getName());
    viewManager.showDialog(dialogHandler);
  }
}
