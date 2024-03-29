package br.ufes.inf.nemo.frameweb.vp.controllers;

import br.ufes.inf.nemo.frameweb.vp.model.FrameWebClass;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebClassDependency;
import br.ufes.inf.nemo.frameweb.vp.utils.FrameWebUtils;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import br.ufes.inf.nemo.vpzy.utils.ProjectManagerUtils;
import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPContext;
import com.vp.plugin.action.VPContextActionController;
import com.vp.plugin.diagram.IDiagramElement;
import com.vp.plugin.diagram.IDiagramUIModel;
import com.vp.plugin.model.IClass;
import com.vp.plugin.model.IModelElement;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.logging.Level;

/**
 * Controller that handles the Add Dependency to Class action, activated by a context menu (right-click) for FrameWeb
 * Class elements.
 *
 * @author Igor Sunderhus e Silva <a href="https://github.com/igorssilva">Igor Sunderhus e Silva</a>
 * @version 0.0.1
 */
public class AddFrameWebDependencyToClassContextController implements VPContextActionController {
    /** Called when the button is pressed. Adds the dependency to the class and diagram. */
    @Override
    public void performAction(final VPAction action, final VPContext context, final ActionEvent event) {

        Logger.log(Level.CONFIG, "Performing action: Add FrameWeb Dependency (Class) > {0}", event.getActionCommand());

        // Processes the selected element
        final IModelElement modelElement = context.getModelElement();

        final FrameWebClass modelElementFrameWebClass = FrameWebUtils.getFrameWebClass(modelElement);

        if (modelElementFrameWebClass == FrameWebClass.NOT_A_FRAMEWEB_CLASS) {
            Logger.log(Level.WARNING, "The selected element is not a FrameWeb class, ignoring action.");
            return;
        }

        final IDiagramUIModel currentDiagram = context.getDiagram();


        context.getDiagram().diagramElementIterator().forEachRemaining(element -> {
            final IDiagramElement diagramElement = (IDiagramElement) element;
            final IModelElement diagramElementModel = diagramElement.getModelElement();
            Logger.log(Level.INFO, "DiagramElement: {0}, ModelType: {1}",
                    new Object[] { diagramElementModel.getName(), diagramElementModel.getModelType() });
        });

        final IModelElement parentModel = currentDiagram.getParentModel();
        Logger.log(Level.INFO, "ModelElement: {0}, ModelType: {1}, Diagram: {2}, Parent Model: {3}",
                new Object[] { modelElement.getName(), modelElement.getModelType(), currentDiagram.getName(),
                        parentModel.getName() });

        // Determines which FrameWeb Class Dependency to apply from the menu item.
        FrameWebClassDependency frameWebClassDependency = FrameWebClassDependency.ofPluginUIID(action.getActionId());
        Logger.log(Level.INFO, "Adding {0} to {1}",
                new Object[] { frameWebClassDependency.getName(), modelElement.getName() });

        final List<IClass> frameWebClasses = FrameWebUtils.getFrameWebClasses(ProjectManagerUtils.getCurrentProject(),
                FrameWebClass.of(frameWebClassDependency.getName()));

        // logs the classes that will be added as dependencies
        for (final IClass clazz : frameWebClasses) {
            final IModelElement clazzPackage = clazz.getParent();

            Logger.log(Level.INFO, "Adding dependency {0} - {1} to {2}",
                    new Object[] { clazz.getName(), clazzPackage.getName(), modelElement.getName() });

            // Adds the dependency to the class.




        }

    }

    /**
     * Called when the menu containing the button is accessed allowing for action manipulation, such as enable/disable
     * or selecting the button.
     */
    @Override
    public void update(final VPAction action, final VPContext context) {

        // Checks if the attribute that was clicked belongs to a FrameWeb class.
        final IModelElement modelElement = context.getModelElement();
        if (modelElement != null) {
            final FrameWebClass modelElementFrameWebClass = FrameWebUtils.getFrameWebClass(modelElement);

            final FrameWebClassDependency frameWebClassAction = FrameWebClassDependency.ofPluginUIID(
                    action.getActionId());
            boolean enabled = false;
            for (final FrameWebClass clazz : frameWebClassAction.getFrameWebClasses()) {
                if (modelElementFrameWebClass == clazz) {
                    enabled = true;
                    break;
                }
            }
            action.setEnabled(enabled);
        }
    }
}
