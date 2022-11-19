package br.ufes.inf.nemo.vpzy.utils;

import java.util.Set;
import com.vp.plugin.DiagramManager;
import com.vp.plugin.diagram.IDiagramElement;
import com.vp.plugin.diagram.IDiagramUIModel;

/**
 * Utility class that provides helper methods regarding Diagram Elements in Visual Paradigm.
 *
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
 */
public final class DiagramElementUtils {
  /**
   * Returns the diagram elements which are currently selected in the active diagram.
   * 
   * @return A set with the diagram elements which are currently selected in the active diagram.
   */
  public static final Set<IDiagramElement> getSelectedDiagramElements() {
    DiagramManager diagramManager = ApplicationManagerUtils.instance.getDiagramManager();
    IDiagramUIModel diagram = diagramManager.getActiveDiagram();
    IDiagramElement[] elementsArray = diagram.getSelectedDiagramElement();
    return Set.of(elementsArray);
  }
}
