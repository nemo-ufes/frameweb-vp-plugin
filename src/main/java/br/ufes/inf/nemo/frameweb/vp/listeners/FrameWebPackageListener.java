package br.ufes.inf.nemo.frameweb.vp.listeners;

import java.beans.PropertyChangeEvent;
import java.util.logging.Level;
import com.vp.plugin.diagram.IDiagramElement;
import com.vp.plugin.diagram.shape.IPackageUIModel;
import com.vp.plugin.model.IModelElement;
import com.vp.plugin.model.IStereotype;
import com.vp.plugin.model.factory.IModelElementFactory;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebModel;
import br.ufes.inf.nemo.vpzy.listeners.ManagedModelListener;
import br.ufes.inf.nemo.vpzy.logging.Logger;

/**
 * Listener that handles changes in packages that have to do with FrameWeb, e.g., when a package
 * changes its stereotype because it has been set to be used as a FrameWeb model.
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
    Logger.log(Level.FINER, "Handling change on property {0} in source {1}",
        new Object[] {propertyName, changeSource});

    // Only handle changes in model elements that have the property name specified.
    if (changeSource instanceof IModelElement && propertyName != null) {
      IModelElement modelElement = (IModelElement) changeSource;

      // More specifically, only handle changes in package elements.
      if (getModelType().equals(modelElement.getModelType())) {
        // Handle changes according to the property that has been changed.
        switch (propertyName) {
          case IStereotype.PROP_STEREOTYPES:
            handlePackageStereotypeChange(modelElement);
            break;
        }
      }
    }
  }

  /**
   * Handle the situation in which a package has changed stereotypes. Check if a FrameWeb model
   * stereotype has been applied and act accordingly.
   * 
   * @param modelElement The model element in which the change took place.
   */
  private void handlePackageStereotypeChange(IModelElement modelElement) {
    // Look for a FrameWeb model stereotype in the model element.
    FrameWebModel model = FrameWebModel.NOT_A_FRAMEWEB_MODEL;
    for (IStereotype stereotype : modelElement.toStereotypeModelArray()) {
      model = FrameWebModel.ofStereotype(stereotype.getName());
    }

    // If a FrameWeb model stereotype has been applied, change the package color.
    if (model != FrameWebModel.NOT_A_FRAMEWEB_MODEL) {
      for (IDiagramElement diagramElement : modelElement.getDiagramElements()) {
        if (diagramElement instanceof IPackageUIModel) {
          Logger.log(Level.FINE, "Changing color of {0} to {1} ({2})", new Object[] {
              diagramElement.getModelElement().getName(), model.getColor(), model.getName()});
          IPackageUIModel packageUIModel = (IPackageUIModel) diagramElement;
          packageUIModel.getFillColor().setColor1(model.getColor().getAwtColor());
        }
      }
    }
  }
}
