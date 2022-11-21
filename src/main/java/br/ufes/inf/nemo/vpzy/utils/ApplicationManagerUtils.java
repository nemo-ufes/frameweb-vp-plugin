package br.ufes.inf.nemo.vpzy.utils;

import java.io.File;
import com.vp.plugin.ApplicationManager;

/**
 * Utility class that provides helper methods regarding the Application Manager in Visual Paradigm.
 *
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
 */
public final class ApplicationManagerUtils {
  /** The Visual Paradigm view manager. */
  static final ApplicationManager instance = ApplicationManager.instance();

  /**
   * Reloads the classes of a plug-in given its ID.
   * 
   * @param pluginID The given ID.
   */
  public static void reloadPluginClasses(String pluginID) {
    instance.reloadPluginClasses(pluginID);
  }

  /**
   * Obtain the location of the current Visual Paradigm workspace.
   * 
   * @return A {@code File} object representing the location.
   */
  public static File getWorkspaceLocation() {
    return instance.getWorkspaceLocation();
  }
}
