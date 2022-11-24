package br.ufes.inf.nemo.frameweb.vp.utils;

import java.util.logging.Level;
import com.vp.plugin.model.IModelElement;
import com.vp.plugin.model.IStereotype;
import com.vp.plugin.model.factory.IModelElementFactory;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebPackage;
import br.ufes.inf.nemo.vpzy.logging.Logger;

/**
 * Utility class that provides helper methods regarding FrameWeb model elements.
 */
public final class FrameWebUtils {
  /**
   * Identifies if a given model element represents a FrameWeb package. Returns
   * {@code FrameWebPackage.NOT_A_FRAMEWEB_PACKAGE} if it doesn't.
   * 
   * @param modelElement The given model element.
   * @return The {@code FrameWebPackage} enum value corresponding to the given model element.
   */
  public static FrameWebPackage getFrameWebPackage(IModelElement modelElement) {
    FrameWebPackage frameWebPackage = FrameWebPackage.NOT_A_FRAMEWEB_PACKAGE;

    // Looks for a FrameWeb package stereotype in the element, which must be a package.
    if (IModelElementFactory.MODEL_TYPE_PACKAGE.equals(modelElement.getModelType())) {
      for (IStereotype stereotype : modelElement.toStereotypeModelArray()) {
        FrameWebPackage pkg = FrameWebPackage.ofStereotype(stereotype.getName());
        // If a FrameWeb package stereotype is found, stores it to be returned.
        if (pkg != FrameWebPackage.NOT_A_FRAMEWEB_PACKAGE)
          frameWebPackage = pkg;
      }
    }

    Logger.log(Level.FINER, "The FrameWeb package of {0} ({1}) is {2}",
        new Object[] {modelElement.getName(), modelElement.getModelType(), frameWebPackage});
    return frameWebPackage;
  }
}
