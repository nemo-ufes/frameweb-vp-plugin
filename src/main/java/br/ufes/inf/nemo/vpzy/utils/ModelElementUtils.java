package br.ufes.inf.nemo.vpzy.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import com.vp.plugin.diagram.IDiagramElement;
import com.vp.plugin.diagram.IShapeUIModel;
import com.vp.plugin.model.IModelElement;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import br.ufes.inf.nemo.vpzy.view.Color;

/**
 * Utility class that provides helper methods regarding Model Elements in Visual Paradigm.
 *
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
 */
public final class ModelElementUtils {
  /**
   * Returns the model elements whose diagram elements are currently selected in the active diagram.
   * 
   * @return A set with the model elements whose diagram elements are currently selected in the
   *         active diagram.
   */
  public static Set<IModelElement> getSelectedModelElements() {
    Set<IModelElement> selectedModelElements = new HashSet<>();

    // Get the selected diagram elements.
    Set<IDiagramElement> diagramElements = DiagramElementUtils.getSelectedDiagramElements();
    for (IDiagramElement diagramElement : diagramElements) {
      // Retrieves the model element from the diagram element. Add it to the set.
      IModelElement modelElement = diagramElement.getModelElement();
      if (modelElement != null)
        selectedModelElements.add(modelElement);

      // Checks if the diagram element has selected members.
      if (diagramElement instanceof IShapeUIModel) {
        IShapeUIModel shapeUIModel = (IShapeUIModel) diagramElement;
        IModelElement[] selectedShapeMembers = shapeUIModel.getSelectedShapeMembers();
        if (selectedShapeMembers != null && selectedShapeMembers.length > 0)
          for (IModelElement selectedMember : selectedShapeMembers)
            selectedModelElements.add(selectedMember);
      }
    }
    Logger.log(Level.FINER, "Getting the selected model elements returns {0} objects",
        selectedModelElements.size());
    return selectedModelElements;
  }

  /**
   * Change the fill color of the diagram elements associated with a given model element to a given
   * color.
   * 
   * @param modelElement The given model element.
   * @param color The given color.
   */
  public static void changeFillColor(IModelElement modelElement, Color color) {
    for (IDiagramElement diagramElement : modelElement.getDiagramElements()) {
      if (diagramElement instanceof IShapeUIModel) {
        Logger.log(Level.FINE, "Changing color of {0} to {1}",
            new Object[] {modelElement.getName(), color});
        IShapeUIModel classUIModel = (IShapeUIModel) diagramElement;
        classUIModel.getFillColor().setColor1(color.getAwtColor());
      }
    }
  }
}
