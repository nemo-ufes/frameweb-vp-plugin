package br.ufes.inf.nemo.frameweb.vp.listeners;

import java.beans.PropertyChangeEvent;
import java.util.logging.Level;
import com.vp.plugin.model.IModelElement;
import com.vp.plugin.model.IStereotype;
import com.vp.plugin.model.factory.IModelElementFactory;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebClass;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebPackage;
import br.ufes.inf.nemo.frameweb.vp.utils.FrameWebUtils;
import br.ufes.inf.nemo.vpzy.listeners.ListenersManager;
import br.ufes.inf.nemo.vpzy.listeners.ManagedModelListener;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import br.ufes.inf.nemo.vpzy.utils.ModelElementUtils;

/**
 * Listener that handles changes in packages that have to do with FrameWeb, e.g., when a package
 * changes its stereotype because it has been set to be used as part of a FrameWeb model.
 * 
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
 */
public class FrameWebPackageListener extends ManagedModelListener {
  public FrameWebPackageListener() {
    // This listener applies only to package elements.
    super(IModelElementFactory.MODEL_TYPE_PACKAGE);
  }

  @Override
  public void propertyChange(PropertyChangeEvent event) {
    Object changeSource = event.getSource();
    String propertyName = event.getPropertyName();
    Logger.log(Level.FINER, "Package listener handling change on property {0} in source {1}",
        new Object[] {propertyName, changeSource});

    // Only handle changes in model elements that have the property name specified.
    if (changeSource instanceof IModelElement && propertyName != null) {
      IModelElement modelElement = (IModelElement) changeSource;

      // More specifically, only handle changes in elements this listener supports.
      if (getModelType().equals(modelElement.getModelType())) {
        // Handle changes according to the property that has been changed.
        switch (propertyName) {
          case IStereotype.PROP_STEREOTYPES:
            // The stereotype of the package has been changed.
            handlePackageStereotypeChange(modelElement);
            break;

          case ListenersManager.PROP_CHILD_ADDED:
            // A new element has been added to the package. Check if it's a class.
            if (event.getNewValue() instanceof IModelElement) {
              IModelElement child = (IModelElement) event.getNewValue();
              if (IModelElementFactory.MODEL_TYPE_CLASS.equals(child.getModelType())) {
                handleNewClassAdded(modelElement, child);
              }
            }
            break;
        }
      }
    }
  }

  /**
   * Handle the situation in which a package has changed stereotypes. Check if a FrameWeb package
   * stereotype has been applied and act accordingly.
   * 
   * @param modelElement The model element in which the change took place.
   */
  private void handlePackageStereotypeChange(IModelElement modelElement) {
    // If a FrameWeb package stereotype has been applied, change the package color.
    FrameWebPackage frameWebPackage = FrameWebUtils.getFrameWebPackage(modelElement);
    if (frameWebPackage != FrameWebPackage.NOT_A_FRAMEWEB_PACKAGE) {
      ModelElementUtils.changeFillColor(modelElement, frameWebPackage.getColor());
    }
  }

  /**
   * Handle the situation in which a new class has been added to the package. Check if the package
   * has a FrameWeb stereotype and if this stereotype has a default type of class. In such case,
   * apply the default class color to the new class.
   * 
   * @param parentPackage The package in which the class was added.
   * @param newClass The new class that was added to the package.
   */
  private void handleNewClassAdded(IModelElement parentPackage, IModelElement newClass) {
    // Checks if this is a FrameWeb package that has a default class type.
    FrameWebPackage frameWebPackage = FrameWebUtils.getFrameWebPackage(parentPackage);
    FrameWebClass defaultClassType = frameWebPackage.getDefaultClassType();
    if (defaultClassType != null) {
      // Sets the color of the element to the color of the default class type.
      ModelElementUtils.changeFillColor(newClass, defaultClassType.getColor());
    }
  }
}
