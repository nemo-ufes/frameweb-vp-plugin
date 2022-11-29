package br.ufes.inf.nemo.frameweb.vp.listeners;

import java.beans.PropertyChangeEvent;
import java.util.logging.Level;
import com.vp.plugin.model.IModelElement;
import com.vp.plugin.model.IStereotype;
import com.vp.plugin.model.factory.IModelElementFactory;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebClass;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebPackage;
import br.ufes.inf.nemo.frameweb.vp.utils.FrameWebUtils;
import br.ufes.inf.nemo.vpzy.listeners.ManagedModelListener;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import br.ufes.inf.nemo.vpzy.utils.ModelElementUtils;

/**
 * Listener that handles changes in classes that have to do with FrameWeb, e.g., when a c ass
 * changes its stereotype because it has been set to be used as part of a FrameWeb model.
 * 
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
 */
public class FrameWebClassListener extends ManagedModelListener {
  public FrameWebClassListener() {
    // This listener applies only to class elements.
    super(IModelElementFactory.MODEL_TYPE_CLASS);
  }

  @Override
  public void propertyChange(PropertyChangeEvent event) {
    Object changeSource = event.getSource();
    String propertyName = event.getPropertyName();
    Logger.log(Level.FINE, "FrameWeb Class listener handling change on property {0} in source {1}",
        new Object[] {propertyName, changeSource});

    // Only handle changes in model elements that have the property name specified.
    if (changeSource instanceof IModelElement && propertyName != null) {
      IModelElement modelElement = (IModelElement) changeSource;
      Logger.log(Level.FINE, "FrameWeb Class listener identified source name: {0}, and type: {1}",
          new Object[] {modelElement.getName(), modelElement.getModelType()});

      // More specifically, only handle changes in elements this listener supports.
      if (getModelType().equals(modelElement.getModelType())) {
        // Handle changes according to the property that has been changed.
        switch (propertyName) {
          case IStereotype.PROP_STEREOTYPES:
            handleClassStereotypeChange(modelElement);
            break;
        }
      }
    }
  }

  /**
   * Handle the situation in which a class has changed stereotypes. Check if a FrameWeb class
   * stereotype has been applied and act accordingly.
   * 
   * @param modelElement The model element in which the change took place.
   */
  private void handleClassStereotypeChange(IModelElement modelElement) {
    // Looks for a FrameWeb class stereotype in the model element.
    FrameWebClass frameWebClass = FrameWebClass.NOT_A_FRAMEWEB_CLASS;
    for (IStereotype stereotype : modelElement.toStereotypeModelArray()) {
      frameWebClass = FrameWebClass.ofStereotype(stereotype.getName());
    }
    Logger.log(Level.CONFIG, "FrameWeb Class listener handling stereotype change: {0}",
        frameWebClass);

    // If a FrameWeb class stereotype has been applied, changes the class color.
    if (frameWebClass != FrameWebClass.NOT_A_FRAMEWEB_CLASS) {
      ModelElementUtils.changeFillColor(modelElement, frameWebClass.getColor());
    }

    // Otherwise, checks if the package to which the class belongs has a default class type.
    else {
      IModelElement parent = modelElement.getParent();
      if (parent != null && IModelElementFactory.MODEL_TYPE_PACKAGE.equals(parent.getModelType())) {
        FrameWebPackage frameWebPackage = FrameWebUtils.getFrameWebPackage(parent);
        FrameWebClass defaultClassType = frameWebPackage.getDefaultClassType();
        Logger.log(Level.CONFIG,
            "FrameWeb Class listener identified class {0} with no stereotype in package: {1} (type: {2}, default class type: {3})",
            new Object[] {modelElement.getName(), parent.getName(), frameWebPackage,
                defaultClassType});

        // Sets the color of the element to the color of the default class type, if any.
        if (defaultClassType != null) {
          ModelElementUtils.changeFillColor(modelElement, defaultClassType.getColor());
        }
      }
    }
  }
}
