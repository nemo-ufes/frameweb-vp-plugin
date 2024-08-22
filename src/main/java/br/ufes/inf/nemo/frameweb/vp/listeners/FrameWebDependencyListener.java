package br.ufes.inf.nemo.frameweb.vp.listeners;

import java.beans.PropertyChangeEvent;
import java.util.logging.Level;
import com.vp.plugin.model.IAssociationEnd;
import com.vp.plugin.model.IDependency;
import com.vp.plugin.model.IModelElement;
import com.vp.plugin.model.factory.IModelElementFactory;
import br.ufes.inf.nemo.frameweb.vp.utils.FrameWebUtils;
import br.ufes.inf.nemo.vpzy.listeners.ListenersManager;
import br.ufes.inf.nemo.vpzy.listeners.ManagedModelListener;
import br.ufes.inf.nemo.vpzy.logging.Logger;

/**
 * Listener that handles changes in dependencies that have to do with FrameWeb, e.g., when a
 * dependency gets a constraint as part of a FrameWeb model.
 *
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
 * @author <a href="https://github.com/gabrielgatti7">Gabriel Gatti da Silva </a>
 */
public class FrameWebDependencyListener extends ManagedModelListener {
    public FrameWebDependencyListener() {
        // This listener applies only to dependency elements.
        super(IModelElementFactory.MODEL_TYPE_DEPENDENCY);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        Object changeSource = event.getSource();
        String propertyName = event.getPropertyName();
        Logger.log(Level.FINE,
                    "FrameWeb Dependency listener handling change on property {0} in source {1}",
                new Object[] {propertyName, changeSource});

        // Only handle changes in model elements that have the property name specified.
        if (changeSource instanceof IModelElement && propertyName != null) {
            IModelElement modelElement = (IModelElement) changeSource;
            Logger.log(Level.FINE,
                    "FrameWeb Dependency listener identified source name: {0}, and type: {1}",
                    new Object[] {modelElement.getName(), modelElement.getModelType()});

            // More specifically, only handle changes in elements this listener supports.
            if (getModelType().equals(modelElement.getModelType())) {
                // Handle changes according to the property that has been changed.
                switch (propertyName) {
                    case ListenersManager.PROP_REFERENCED_BY_ADDED:
                    case ListenersManager.PROP_REFERENCED_BY_REMOVED:
                        // Visual Paradigm doesn't show dependency constraints. Display them as names.
                        FrameWebUtils.displayConstraintsWithName(modelElement);
                        break;
                }
            }
        }
    }
}
