package br.ufes.inf.nemo.frameweb.vp.listeners;

import java.beans.PropertyChangeEvent;
import com.vp.plugin.diagram.IDiagramElement;
import com.vp.plugin.diagram.shape.IPackageUIModel;
import com.vp.plugin.model.IModelElement;
import com.vp.plugin.model.IStereotype;
import com.vp.plugin.model.factory.IModelElementFactory;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebModel;
import br.ufes.inf.nemo.vpzy.listeners.ModelListener;

public class FrameWebPackageListener extends ModelListener {
  public FrameWebPackageListener() {
    // This listener applies only to package elements.
    super(IModelElementFactory.MODEL_TYPE_PACKAGE);
  }

  @Override
  public void propertyChange(PropertyChangeEvent event) {
    // Only handle changes in model elements that have the property name specified.
    Object changeSource = event.getSource();
    String propertyName = event.getPropertyName();
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

  private void handlePackageStereotypeChange(IModelElement modelElement) {
    FrameWebModel model = FrameWebModel.NOT_A_FRAMEWEB_MODEL;
    for (IStereotype stereotype : modelElement.toStereotypeModelArray()) {
      model = FrameWebModel.ofStereotype(stereotype.getName());
    }

    if (model != FrameWebModel.NOT_A_FRAMEWEB_MODEL) {
      for (IDiagramElement diagramElement : modelElement.getDiagramElements()) {
        if (diagramElement instanceof IPackageUIModel) {
          IPackageUIModel packageUIModel = (IPackageUIModel) diagramElement;
          packageUIModel.getFillColor().setColor1(model.getColor().getAwtColor());
        }
      }
    }
  }
}
