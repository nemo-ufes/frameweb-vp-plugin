package br.ufes.inf.nemo.frameweb.vp.listeners;

import java.beans.PropertyChangeEvent;
import java.util.logging.Level;
import com.vp.plugin.diagram.IDiagramElement;
import com.vp.plugin.diagram.shape.IClassUIModel;
import com.vp.plugin.model.IModelElement;
import com.vp.plugin.model.IStereotype;
import com.vp.plugin.model.factory.IModelElementFactory;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebClass;
import br.ufes.inf.nemo.vpzy.listeners.ManagedModelListener;
import br.ufes.inf.nemo.vpzy.logging.Logger;

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
    Logger.log(Level.FINER, "Handling change on property {0} in source {1}",
        new Object[] {propertyName, changeSource});

    // Only handle changes in model elements that have the property name specified.
    if (changeSource instanceof IModelElement && propertyName != null) {
      IModelElement modelElement = (IModelElement) changeSource;

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
    // Look for a FrameWeb class stereotype in the model element.
    FrameWebClass model = FrameWebClass.NOT_A_FRAMEWEB_CLASS;
    for (IStereotype stereotype : modelElement.toStereotypeModelArray()) {
      model = FrameWebClass.ofStereotype(stereotype.getName());
    }

    // If a FrameWeb class stereotype has been applied, change the class color.
    if (model != FrameWebClass.NOT_A_FRAMEWEB_CLASS) {
      for (IDiagramElement diagramElement : modelElement.getDiagramElements()) {
        if (diagramElement instanceof IClassUIModel) {
          Logger.log(Level.FINE, "Changing color of {0} to {1} ({2})", new Object[] {
              diagramElement.getModelElement().getName(), model.getColor(), model.getName()});
          IClassUIModel classUIModel = (IClassUIModel) diagramElement;
          classUIModel.getFillColor().setColor1(model.getColor().getAwtColor());
        }
      }
    }
  }
}
