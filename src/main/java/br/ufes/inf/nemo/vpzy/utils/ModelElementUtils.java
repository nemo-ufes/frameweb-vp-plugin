package br.ufes.inf.nemo.vpzy.utils;

import java.util.HashSet;
import java.util.Set;
import com.vp.plugin.diagram.IDiagramElement;
import com.vp.plugin.model.IModelElement;

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
  public static final Set<IModelElement> getSelectedModelElements() {
    Set<IModelElement> selectedModelElements = new HashSet<>();
    Set<IDiagramElement> diagramElements = DiagramElementUtils.getSelectedDiagramElements();
    for (IDiagramElement diagramElement : diagramElements) {
      IModelElement modelElement = diagramElement.getModelElement();
      if (modelElement != null)
        selectedModelElements.add(modelElement);
    }
    return selectedModelElements;
  }
}
