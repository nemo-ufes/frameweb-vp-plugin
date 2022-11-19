package br.ufes.inf.nemo.vpzy.utils;

import com.vp.plugin.ProjectManager;
import com.vp.plugin.model.IProject;

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
    return projectManager.getProject();
  }
}
