package br.ufes.inf.nemo.frameweb.vp.managers;

import java.util.logging.Level;
import com.vp.plugin.model.IProject;
import com.vp.plugin.model.factory.IModelElementFactory;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebPackage;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import br.ufes.inf.nemo.vpzy.managers.StereotypesManager;

/**
 * Utility class that manages stereotypes in Visual Paradigm projects, including the stereotypes
 * needed by FrameWeb.
 *
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
 */
public class FrameWebStereotypesManager extends StereotypesManager {
  /** Constructor. */
  protected FrameWebStereotypesManager(IProject project) {
    super(project);
  }

  /**
   * Overrides the {@code StereotypeManager#init()} method in order to include in the project
   * FrameWeb-specific stereotypes.
   */
  @Override
  protected void init() {
    Logger.log(Level.FINE, "Initializing the FrameWeb Steteorypes Manager");

    // Initializes the stereotypes manager with default stereotypes.
    super.init();

    // Checks if the project has FrameWeb Model stereotypes, create the missing ones.
    for (FrameWebPackage model : FrameWebPackage.values()) {
      if (model != FrameWebPackage.NOT_A_FRAMEWEB_PACKAGE)
        checkStereotype(model.getStereotypeName(), IModelElementFactory.MODEL_TYPE_PACKAGE);
    }
  }
}
