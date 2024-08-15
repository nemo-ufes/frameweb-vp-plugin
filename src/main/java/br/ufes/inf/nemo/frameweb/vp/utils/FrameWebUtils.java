package br.ufes.inf.nemo.frameweb.vp.utils;

import br.ufes.inf.nemo.frameweb.vp.model.FrameWebClass;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebPackage;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import com.vp.plugin.model.*;
import com.vp.plugin.model.factory.IModelElementFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

/**
 * Utility class that provides helper methods regarding FrameWeb model elements.
 */
public final class FrameWebUtils {
    private FrameWebUtils() {
        // Prevents instantiation.
    }

    /**
     * Checks if the given dependency or association end has constraints and, if so, append them to the
     * dependency name or to the association end role name to be shown in the diagram,
     * as Visual Paradigm doesn't show constraints that are added to dependencies/association ends.
     *
     * @param modelElement The given modelElement of a dependency/association end.
     */
    public static void displayConstraintsWithName(IModelElement modelElement) {
        if(modelElement instanceof IDependency || modelElement instanceof IAssociationEnd) {
            // Extracts the name/role name from the dependency/association end (it may already have constraints).
            String elementName = modelElement.getName();
            if (elementName == null) {
                elementName = "";
            } else {
                int idx = elementName.indexOf('{');
                if (idx == 0) {
                    elementName = "";
                } else if (idx > 0) {
                    elementName = elementName.substring(0, idx - 1);
                }
            }
            elementName = elementName.trim();

            // Builds a comma-separated list of constraints.
            StringBuilder builder = new StringBuilder();
            Iterator<?> iterator = modelElement.constraintsIterator();
            if (iterator != null) {
                while (iterator.hasNext()) {
                    Object obj = iterator.next();
                    if (obj instanceof IConstraintElement) {
                        IConstraintElement constraintElement = (IConstraintElement) obj;
                        builder.append(constraintElement.getSpecification().getValue());
                        builder.append(", ");
                    }
                }

                // If there are constraints in the dependency/association end, complements its name/role name with them.
                int length = builder.length();
                if (length > 0) {
                    builder.delete(length - 2, length);
                    builder.append('}');
                    builder.insert(0, " {");
                    builder.insert(0, elementName);
                    elementName = builder.toString();
                }
            }

            // Finally, sets the name/role name to the dependency/association end.
            modelElement.setName(elementName);
        }
    }

    /**
     * Returns a list of the FrameWeb classes in the given project.
     *
     * @param project The given project.
     * @return A list of the FrameWeb classes in the given project.
     */
    public static List<IClass> getFrameWebClasses(IProject project) {
        Iterator<?> iter = project.modelElementIterator(IModelElementFactory.MODEL_TYPE_CLASS);
        List<IClass> clazzes = new ArrayList<>();
        while (iter.hasNext()) {
            IClass clazz = (IClass) iter.next();
            FrameWebClass frameWebClass = getFrameWebClass(clazz);
            if (frameWebClass != FrameWebClass.NOT_A_FRAMEWEB_CLASS) {
                clazzes.add(clazz);
            }
        }
        return clazzes;
    }

    /**
     * Returns a list of the IClass in the given project that match the FrameWeb class passed.
     *
     * @param project The given project.
     * @param fwClass The FrameWeb class to be matched.
     * @return A list of the IClasses in the given project.
     */
    public static List<IClass> getFrameWebClasses(IProject project, FrameWebClass fwClass) {
        final Iterator<?> iter = project.allLevelModelElementIterator(IModelElementFactory.MODEL_TYPE_CLASS);
        final List<IClass> clazzes = new ArrayList<>();
        while (iter.hasNext()) {
            final IClass clazz = (IClass) iter.next();
            final FrameWebClass currentFWClass = getFrameWebClass(clazz);

            if (currentFWClass.equals(fwClass)) {
                clazzes.add(clazz);
            }

        }
        return clazzes;
    }

    /**
     * Identifies if a given model element represents a FrameWeb class. Returns
     * {@code FrameWebClass.NOT_A_FRAMEWEB_CLASS} if it doesn't.
     *
     * @param modelElement The given model element.
     * @return The {@code FrameWebClass} enum value corresponding to the given model element.
     */
    public static FrameWebClass getFrameWebClass(IModelElement modelElement) {
        FrameWebClass frameWebClass = FrameWebClass.NOT_A_FRAMEWEB_CLASS;

        // Looks for a FrameWeb class stereotype in the element, which must be a class.
        if (IModelElementFactory.MODEL_TYPE_CLASS.equals(modelElement.getModelType())) {
            IStereotype[] stereotypes = modelElement.toStereotypeModelArray();
            if (stereotypes != null) {
                for (IStereotype stereotype : stereotypes) {
                    FrameWebClass clazz = FrameWebClass.ofStereotype(stereotype.getName());
                    // If a FrameWeb class stereotype is found, stores it to be returned.
                    if (clazz != FrameWebClass.NOT_A_FRAMEWEB_CLASS) {
                        frameWebClass = clazz;
                    }
                }
            }

            // If a FrameWeb class stereotype was not found, check the package for the default.
            if (frameWebClass == FrameWebClass.NOT_A_FRAMEWEB_CLASS) {
                IModelElement parent = modelElement.getParent();
                if (parent != null) {
                    FrameWebPackage frameWebPackage = getFrameWebPackage(parent);
                    FrameWebClass clazz = frameWebPackage.getDefaultClassType();

                    // If a default class is found, stores it to be returned.
                    if (clazz != null && clazz != FrameWebClass.NOT_A_FRAMEWEB_CLASS) {
                        frameWebClass = clazz;
                    }
                }
            }
        }

        Logger.log(Level.FINER, "The FrameWeb class of {0} ({1}) is {2}",
                new Object[] { modelElement.getName(), modelElement.getModelType(), frameWebClass });
        return frameWebClass;
    }

    /**
     * Identifies if a given model element represents a FrameWeb package. Returns
     * {@code FrameWebPackage.NOT_A_FRAMEWEB_PACKAGE} if it doesn't.
     *
     * @param modelElement The given model element.
     * @return The {@code FrameWebPackage} enum value corresponding to the given model element.
     */
    public static FrameWebPackage getFrameWebPackage(IModelElement modelElement) {
        FrameWebPackage frameWebPackage = FrameWebPackage.NOT_A_FRAMEWEB_PACKAGE;

        // Looks for a FrameWeb package stereotype in the element, which must be a package.
        if (IModelElementFactory.MODEL_TYPE_PACKAGE.equals(modelElement.getModelType())) {
            IStereotype[] stereotypes = modelElement.toStereotypeModelArray();
            if (stereotypes != null) {
                for (IStereotype stereotype : stereotypes) {
                    FrameWebPackage pkg = FrameWebPackage.ofStereotype(stereotype.getName());
                    // If a FrameWeb package stereotype is found, stores it to be returned.
                    if (pkg != FrameWebPackage.NOT_A_FRAMEWEB_PACKAGE) {
                        frameWebPackage = pkg;
                    }
                }
            }
        }

        Logger.log(Level.FINER, "The FrameWeb package of {0} ({1}) is {2}",
                new Object[] { modelElement.getName(), modelElement.getModelType(), frameWebPackage });
        return frameWebPackage;
    }

}
