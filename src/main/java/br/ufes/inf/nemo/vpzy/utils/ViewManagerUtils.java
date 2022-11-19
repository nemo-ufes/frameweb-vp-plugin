package br.ufes.inf.nemo.vpzy.utils;

import com.vp.plugin.ViewManager;

/**
 * Utility class that provides helper methods regarding the View Manager in Visual Paradigm.
 *
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
 */
public final class ViewManagerUtils {
  /** The Visual Paradigm view manager. */
  private static final ViewManager viewManager = ApplicationManagerUtils.instance.getViewManager();

  /**
   * Prints a message in Visual Paradigm's message pane.
   * 
   * @param message The message to print.
   * @param pluginName The name of the plug-in that is printing the message.
   */
  public static final void showMessage(String message) {
    viewManager.showMessage(message);
  }

  /**
   * Prints a message in Visual Paradigm's message pane, under a specific plug-in's tab.
   * 
   * @param message The message to print.
   * @param pluginName The name of the plug-in that is printing the message.
   */
  public static final void showMessage(String message, String pluginName) {
    viewManager.showMessage(message, pluginName);
  }
}
