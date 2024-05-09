package br.ufes.inf.nemo.vpzy.utils;

import java.util.Set;
import java.util.logging.Level;
import com.vp.plugin.DiagramManager;
import com.vp.plugin.diagram.IDiagramElement;
import com.vp.plugin.diagram.IDiagramUIModel;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import com.vp.plugin.diagram.IShapeTypeConstants;
import com.vp.plugin.model.IModelElement;

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
  public static Set<IDiagramElement> getSelectedDiagramElements() {
    DiagramManager diagramManager = ApplicationManagerUtils.instance.getDiagramManager();
    IDiagramUIModel diagram = diagramManager.getActiveDiagram();
    IDiagramElement[] elementsArray = diagram.getSelectedDiagramElement();
    Logger.log(Level.FINER, "Getting the selected diagram elements returns {0} objects",
        elementsArray.length);
    return Set.of(elementsArray);
  }

  /**
   * Creates an auxiliary view of a model element in the diagram specified.
   *
   * @param diagram The diagram to which the element will be added
   * @param modelElement The element to be added
   * @return The IDiagramElement created for the auxiliary view added.
   */
  public static IDiagramElement createAuxiliaryViewInDiagram(IDiagramUIModel diagram, IModelElement modelElement){
      DiagramManager diagramManager = ApplicationManagerUtils.instance.getDiagramManager();
      return diagramManager.createDiagramElement(diagram, modelElement);
  }

  public static IDiagramElement createAssociation(IDiagramUIModel diagram, IDiagramElement modelElementFrom, IDiagramElement modelElementTo){
    DiagramManager diagramManager = ApplicationManagerUtils.instance.getDiagramManager();
    return diagramManager.createConnector(diagram, IShapeTypeConstants.SHAPE_TYPE_ASSOCIATION, modelElementFrom, modelElementTo, null);
  }
}
