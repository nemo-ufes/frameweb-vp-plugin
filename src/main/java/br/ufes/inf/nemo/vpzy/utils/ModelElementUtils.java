package br.ufes.inf.nemo.vpzy.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import com.vp.plugin.diagram.IDiagramElement;
import com.vp.plugin.model.IModelElement;
import br.ufes.inf.nemo.vpzy.logging.Logger;

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
    Logger.log(Level.FINER, "Getting the selected model elements returns {0} objects",
        selectedModelElements.size());
    return selectedModelElements;
  }
}
