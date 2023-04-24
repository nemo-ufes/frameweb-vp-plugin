package br.ufes.inf.nemo.frameweb.vp.utils;

import br.ufes.inf.nemo.frameweb.vp.model.FrameWebClass;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebPackage;
import br.ufes.inf.nemo.vpzy.engine.FreeMarkerEngine;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import br.ufes.inf.nemo.vpzy.utils.ProjectManagerUtils;
import com.vp.plugin.model.IAssociationEnd;
import com.vp.plugin.model.IAttribute;
import com.vp.plugin.model.IClass;
import com.vp.plugin.model.IConstraintElement;
import com.vp.plugin.model.IModelElement;
import com.vp.plugin.model.IPackage;
import com.vp.plugin.model.IProject;
import com.vp.plugin.model.IStereotype;
import com.vp.plugin.model.factory.IModelElementFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * Utility class that provides helper methods regarding FrameWeb model elements.
 */
public final class FrameWebUtils {
    /**
     * Checks if the given association end has constraints and, if so, append them to the association end name to be
     * shown in the diagram, as Visual Paradigm doesn't show constraints that are added to association ends.
     *
     * @param associationEnd The given association end.
     */
    public static void displayConstraintsWithRoleName(IAssociationEnd associationEnd) {
        // Extracts the role name from the association (it may already have constraints).
        String roleName = associationEnd.getName();
        if (roleName == null) {
            roleName = "";
        } else {
            int idx = roleName.indexOf('{');
            if (idx == 0) {
                roleName = "";
            } else if (idx > 0) {
                roleName = roleName.substring(0, idx - 1);
            }
        }
        roleName = roleName.trim();

        // Builds a comma-separated list of constraints.
        StringBuilder builder = new StringBuilder();
        Iterator<?> iterator = associationEnd.constraintsIterator();
        if (iterator != null) {
            while (iterator.hasNext()) {
                Object obj = iterator.next();
                if (obj instanceof IConstraintElement) {
                    IConstraintElement constraintElement = (IConstraintElement) obj;
                    builder.append(constraintElement.getSpecification().getValue());
                    builder.append(", ");
                }
            }

            // If there are constraints in the association end, complements its role name with them.
            int length = builder.length();
            if (length > 0) {
                builder.delete(length - 2, length);
                builder.append('}');
                builder.insert(0, " {");
                builder.insert(0, roleName);
                roleName = builder.toString();
            }
        }

        // Finally, sets the role name to the association end.
        associationEnd.setName(roleName);
    }

    /**
     * Returns a list of the FrameWeb classes in the given project.
     *
     * @param project The given project.
     * @return A list of the FrameWeb classes in the given project.
     */
    public static List<IClass> getFrameWebClasses(IProject project) {
        Iterator iter = project.modelElementIterator(IModelElementFactory.MODEL_TYPE_CLASS);
        List<IClass> clazzes = new ArrayList<IClass>();
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
            for (IStereotype stereotype : modelElement.toStereotypeModelArray()) {
                FrameWebPackage pkg = FrameWebPackage.ofStereotype(stereotype.getName());
                // If a FrameWeb package stereotype is found, stores it to be returned.
                if (pkg != FrameWebPackage.NOT_A_FRAMEWEB_PACKAGE) {
                    frameWebPackage = pkg;
                }
            }
        }

        Logger.log(Level.FINER, "The FrameWeb package of {0} ({1}) is {2}",
                new Object[] { modelElement.getName(), modelElement.getModelType(), frameWebPackage });
        return frameWebPackage;
    }

    /**
     * Transforma pacotes com qualquer separador além de "/" para um caminho no sistema de arquivos
     *
     * @param packageName
     * @return packagePath
     */
    public static String packageNameToPath(String packageName) {
        // Por alguma razao a Workbench do Eclipse as vezes nao se da muito bem com diretorios contendo
        // letras maiusculas, caso isso venha a ocorrer, descomente o metodo abaixo.
        return packageName.replaceAll("[^A-Za-z0-9]", "/"); // .toLowerCase();
    }


    /**
     * Generates the code for the FrameWeb project.
     * @param templateDir
     * @param outputDir
     */
    public static void generateCode(final String templateDir, final String outputDir) {
        final IProject project = ProjectManagerUtils.getCurrentProject();

        final FreeMarkerEngine engine = new FreeMarkerEngine(templateDir, outputDir);

        Iterator iter = project.allLevelModelElementIterator(IModelElementFactory.MODEL_TYPE_PACKAGE);

        // process entity model
        while (iter.hasNext()) {
            IPackage pack = (IPackage) iter.next();
            processPackage(pack, engine);
        }

    }

    private static void processPackage(final IPackage pack, final FreeMarkerEngine engine) {

        if (getFrameWebPackage(pack) == FrameWebPackage.NOT_A_FRAMEWEB_PACKAGE) {
            Logger.log(Level.SEVERE,
                    "####### skipping " + pack.getName() + " (" + FrameWebUtils.getFrameWebPackage(pack) + ")");
            return;
        }

        Logger.log(Level.SEVERE, "####### " + pack.getName() + " (" + FrameWebUtils.getFrameWebPackage(pack) + ")");

        Iterator classIter = pack.childIterator(IModelElementFactory.MODEL_TYPE_CLASS);
        while (classIter.hasNext()) {
            IClass child = (IClass) classIter.next();

            processClass(child, engine);

        }

    }

    private static void processClass(IClass clazz, final FreeMarkerEngine engine) {

        if (getFrameWebClass(clazz) == FrameWebClass.NOT_A_FRAMEWEB_CLASS) {
            Logger.log(Level.SEVERE,
                    "################# skipping " + clazz.getName() + " (" + FrameWebUtils.getFrameWebClass(clazz)
                            + ")");
            return;
        }

        final Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("package", clazz.getParent());
        dataModel.put("class", clazz);
        dataModel.put("path", packageNameToPath(clazz.getParent().getName()));

        List<IAttribute> attributeModels = new ArrayList<>();

        Iterator attributeIter = clazz.childIterator(IModelElementFactory.MODEL_TYPE_ATTRIBUTE);
        while (attributeIter.hasNext()) {
            IAttribute child = (IAttribute) attributeIter.next();
            attributeModels.add(child);
        }

        dataModel.put("attributes", attributeModels);

        try {
            engine.generateCode("EntityClassTemplate.ftl", dataModel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
