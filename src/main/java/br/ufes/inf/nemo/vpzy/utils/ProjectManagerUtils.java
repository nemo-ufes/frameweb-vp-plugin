package br.ufes.inf.nemo.vpzy.utils;

import java.util.logging.Level;
import com.vp.plugin.ProjectManager;
import com.vp.plugin.model.IProject;
import br.ufes.inf.nemo.vpzy.logging.Logger;

/**
 * Utility class that provides helper methods regarding the Project Manager in Visual Paradigm.
 *
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
 */
public final class ProjectManagerUtils {
  /** The Visual Paradigm project manager. */
  private static final ProjectManager projectManager =
      ApplicationManagerUtils.instance.getProjectManager();

  /**
   * Retrieves the currently opened project.
   * 
   * @return The currently opened project.
   */
  public static IProject getCurrentProject() {
    IProject currentProject = projectManager.getProject();
    Logger.log(Level.FINEST, "Retrieving current project: {0}", currentProject);
    return currentProject;
  }
}
