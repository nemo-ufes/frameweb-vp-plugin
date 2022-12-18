package br.ufes.inf.nemo.frameweb.vp.managers;

import java.util.logging.Level;
import com.vp.plugin.model.IProject;
import com.vp.plugin.model.factory.IModelElementFactory;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebClass;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebClassAttribute;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebGeneralization;
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
  protected FrameWebStereotypesManager(IProject project) {
    super(project);
  }

  /**
   * Overrides the {@code StereotypeManager#init()} method in order to include in the project
   * FrameWeb-specific stereotypes.
   */
  @Override
  protected void init() {
    Logger.log(Level.CONFIG, "Initializing the FrameWeb Steteorypes Manager");

    // Initializes the stereotypes manager with default stereotypes.
    super.init();

    // Checks if the project has FrameWeb Package stereotypes, create the missing ones.
    for (FrameWebPackage frameWebPackage : FrameWebPackage.values()) {
      if (frameWebPackage != FrameWebPackage.NOT_A_FRAMEWEB_PACKAGE) {
        String stereotypeName = frameWebPackage.getStereotypeName();
        if (stereotypeName != null && !stereotypeName.trim().isEmpty()) {
          Logger.log(Level.FINE, "Checking existence of FrameWeb package stereotype: {0}",
              stereotypeName);
          checkStereotype(stereotypeName, IModelElementFactory.MODEL_TYPE_PACKAGE);
        }
      }
    }

    // Checks if the project has FrameWeb Class stereotypes, create the missing ones.
    for (FrameWebClass frameWebClass : FrameWebClass.values()) {
      if (frameWebClass != FrameWebClass.NOT_A_FRAMEWEB_CLASS) {
        String stereotypeName = frameWebClass.getStereotypeName();
        if (stereotypeName != null && !stereotypeName.trim().isEmpty()) {
          Logger.log(Level.FINE, "Checking existence of FrameWeb class stereotype: {0}",
              stereotypeName);
          checkStereotype(stereotypeName, IModelElementFactory.MODEL_TYPE_CLASS);
        }
      }
    }

    // Checks if the project has FrameWeb Class Attribute stereotypes, create the missing ones.
    for (FrameWebClassAttribute frameWebClassAttribute : FrameWebClassAttribute.values()) {
      if (frameWebClassAttribute != FrameWebClassAttribute.NOT_A_FRAMEWEB_CLASS_ATTRIBUTE) {
        String stereotypeName = frameWebClassAttribute.getStereotypeName();
        if (stereotypeName != null && !stereotypeName.trim().isEmpty()) {
          Logger.log(Level.FINE, "Checking existence of FrameWeb class attribute stereotype: {0}",
              stereotypeName);
          checkStereotype(stereotypeName, IModelElementFactory.MODEL_TYPE_ATTRIBUTE);
        }
      }
    }

    // Checks if the project has FrameWeb Generalization stereotypes, create the missing ones.
    for (FrameWebGeneralization frameWebGeneralization : FrameWebGeneralization.values()) {
      if (frameWebGeneralization != FrameWebGeneralization.NOT_A_FRAMEWEB_GENERALIZATION) {
        String stereotypeName = frameWebGeneralization.getStereotypeName();
        if (stereotypeName != null && !stereotypeName.trim().isEmpty()) {
          Logger.log(Level.FINE, "Checking existence of FrameWeb generalization stereotype: {0}",
              stereotypeName);
          checkStereotype(stereotypeName, IModelElementFactory.MODEL_TYPE_GENERALIZATION);
        }
      }
    }
  }
}
