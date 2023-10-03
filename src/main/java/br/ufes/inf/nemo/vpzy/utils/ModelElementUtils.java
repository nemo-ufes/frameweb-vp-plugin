package br.ufes.inf.nemo.vpzy.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import com.vp.plugin.diagram.IDiagramElement;
import com.vp.plugin.diagram.IShapeUIModel;
import com.vp.plugin.diagram.shape.IClassUIModel;
import com.vp.plugin.model.IModelElement;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import br.ufes.inf.nemo.vpzy.view.Color;

/**
 * Utility class that provides helper methods regarding Model Elements in Visual Paradigm.
 *
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
 * @author Igor Sunderhus e Silva (<a href="https://github.com/igorssilva">Github page</a>)
 */
public final class ModelElementUtils {
  /**
   * Returns the model elements whose diagram elements are currently selected in the active diagram.
   * 
   * @return A set with the model elements whose diagram elements are currently selected in the
   *         active diagram.
   */
  public static Set<IModelElement> getSelectedModelElements() {
    Logger.log(Level.FINEST, "Retrieving all selected model elements");
    Set<IModelElement> selectedModelElements = new HashSet<>();

    // Gets the selected diagram elements.
    Set<IDiagramElement> diagramElements = DiagramElementUtils.getSelectedDiagramElements();
    Logger.log(Level.FINEST, "Checking {0} selected diagram elements", diagramElements.size());
    for (IDiagramElement diagramElement : diagramElements) {
      // Retrieves the model element from the diagram element. Add it to the set.
      IModelElement modelElement = diagramElement.getModelElement();
      if (modelElement != null) {
        Logger.log(Level.FINEST,
            "A selected diagram element represents model element: {0} ({1}), will be added to the set",
            new Object[] {modelElement.getName(), modelElement.getModelType()});
        selectedModelElements.add(modelElement);
      }

      // Checks if the diagram element has selected members. Add them to the set.
      if (diagramElement instanceof IShapeUIModel) {
        IShapeUIModel shapeUIModel = (IShapeUIModel) diagramElement;
        IModelElement[] selectedShapeMembers = shapeUIModel.getSelectedShapeMembers();
        if (selectedShapeMembers != null && selectedShapeMembers.length > 0) {
          Logger.log(Level.FINEST, "Checking {0} selected members from a selected diagram element",
              selectedShapeMembers.length);
          for (IModelElement selectedMember : selectedShapeMembers) {
            Logger.log(Level.FINEST, "Add selected member to the set: {0} ({1})",
                new Object[] {selectedMember.getName(), selectedMember.getModelType()});
            selectedModelElements.add(selectedMember);
          }
        }
      }
    }

    // Finally, returns the set of selected elements.
    Logger.log(Level.FINER, "Getting the selected model elements returns {0} objects",
        selectedModelElements.size());
    return selectedModelElements;
  }

  /**
   * Changes the fill color of the diagram elements associated with a given model element to a given
   * color.
   * 
   * @param modelElement The given model element.
   * @param color The given color.
   */
  public static void changeFillColor(IModelElement modelElement, Color color) {
    for (IDiagramElement diagramElement : modelElement.getDiagramElements()) {
      if (diagramElement instanceof IShapeUIModel) {
        Logger.log(Level.FINER, "Changing color of {0} to {1}",
            new Object[] {modelElement.getName(), color});
        IShapeUIModel classUIModel = (IShapeUIModel) diagramElement;
        classUIModel.getFillColor().setColor1(color.getAwtColor());
      }
    }
  }

  public static void changeInterfaceBall(final IModelElement modelElement, final boolean isInterface) {

    for (IDiagramElement diagramElement : modelElement.getDiagramElements()) {
      if (diagramElement instanceof IClassUIModel) {
        Logger.log(Level.FINER, "Changing shape of {0}", new Object[] { modelElement.getName() });

        IClassUIModel classUIModel2 = (IClassUIModel) diagramElement;
        classUIModel2.setShowTypeOption(IClassUIModel.TYPE_NAME_ONLY);
        if (isInterface) {
          modelElement.addStereotype("Interface");
          classUIModel2.setInterfaceBall(true);
          classUIModel2.setShowStereotypeIconName(IShapeUIModel.SHOW_STEREOTYPE_ICON_NAME_YES);
        } else {
          modelElement.removeStereotype("Interface");
          classUIModel2.setInterfaceBall(false);
        }
      }
    }

  }
}
