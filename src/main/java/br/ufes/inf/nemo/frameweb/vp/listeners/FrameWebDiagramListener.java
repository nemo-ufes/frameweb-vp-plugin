package br.ufes.inf.nemo.frameweb.vp.listeners;

import java.util.logging.Level;
import com.vp.plugin.diagram.IDiagramElement;
import com.vp.plugin.diagram.IDiagramListener;
import com.vp.plugin.diagram.IDiagramUIModel;
import com.vp.plugin.diagram.shape.IClassUIModel;
import com.vp.plugin.model.IModelElement;
import com.vp.plugin.model.IStereotype;
import com.vp.plugin.model.factory.IModelElementFactory;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebClass;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebPackage;
import br.ufes.inf.nemo.vpzy.logging.Logger;

/**
 * Listener that handles changes in diagrams that have to do with FrameWeb, e.g., when a new class
 * is added in a FrameWeb package.
 * 
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
 */
public class FrameWebDiagramListener implements IDiagramListener {
  @Override
  public void diagramElementAdded(IDiagramUIModel diagramUIModel, IDiagramElement diagramElement) {
    // Checks if the new element is a class.
    IModelElement modelElement = diagramElement.getModelElement();
    Logger.log(Level.SEVERE, "modelElement = {0}, type = {1}",
        new Object[] {modelElement, modelElement.getModelType()});
    if (IModelElementFactory.MODEL_TYPE_CLASS.equals(modelElement.getModelType())) {
      // Checks that is has been added to a package.
      IModelElement parent = modelElement.getParent();
      Logger.log(Level.SEVERE, "parent = {0}, type = {1}",
          new Object[] {parent, parent.getModelType()});
      if (IModelElementFactory.MODEL_TYPE_PACKAGE.equals(parent.getModelType())) {
        // Checks that the package is a FrameWeb package.
        IStereotype[] existingStereotypes = parent.toStereotypeModelArray();
        if (existingStereotypes != null) {
          for (IStereotype existingStereotype : existingStereotypes) {
            FrameWebPackage frameWebPackage =
                FrameWebPackage.ofStereotype(existingStereotype.getName());

            // Checks if this package has a default class type.
            FrameWebClass defaultClassType = frameWebPackage.getDefaultClassType();
            Logger.log(Level.SEVERE, "frameWebPackage = {0}, defaultClass = {1}",
                new Object[] {frameWebPackage, defaultClassType});
            if (defaultClassType != null) {
              // Sets the color of the element to the color of the default class type.
              for (IDiagramElement element : modelElement.getDiagramElements()) {
                if (element instanceof IClassUIModel) {
                  Logger.log(Level.FINE, "Changing color of {0} to {1} ({2})",
                      new Object[] {element.getModelElement().getName(),
                          defaultClassType.getColor(), defaultClassType.getName()});
                  IClassUIModel classUIModel = (IClassUIModel) diagramElement;
                  classUIModel.getFillColor().setColor1(defaultClassType.getColor().getAwtColor());
                }
              }
            }
          }
        }
      }
    }
  }

  @Override
  public void diagramElementRemoved(IDiagramUIModel diagramUIModel,
      IDiagramElement diagramElement) {
    // Nothing to do here.
  }

  @Override
  public void diagramUIModelLoaded(IDiagramUIModel diagramUIModel) {
    // Nothing to do here.
  }

  @Override
  public void diagramUIModelPropertyChanged(IDiagramUIModel diagramUIModel, String propertyName,
      Object oldValue, Object newValue) {
    // Nothing to do here.
  }

  @Override
  public void diagramUIModelRenamed(IDiagramUIModel diagramUIModel) {
    // Nothing to do here.
  }
}
