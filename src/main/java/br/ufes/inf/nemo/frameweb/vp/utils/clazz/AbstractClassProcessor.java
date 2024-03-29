package br.ufes.inf.nemo.frameweb.vp.utils.clazz;

import br.ufes.inf.nemo.vpzy.engine.FreeMarkerEngine;
import br.ufes.inf.nemo.vpzy.engine.models.base.AbstractAssociationModel;
import br.ufes.inf.nemo.vpzy.engine.models.base.AbstractAttributeModel;
import br.ufes.inf.nemo.vpzy.engine.models.base.AbstractClassModel;
import br.ufes.inf.nemo.vpzy.engine.models.base.AbstractMethodModel;
import br.ufes.inf.nemo.vpzy.engine.models.base.AbstractTemplateModel;
import br.ufes.inf.nemo.vpzy.engine.models.base.FileTypes;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import br.ufes.inf.nemo.vpzy.utils.ViewManagerUtils;
import com.vp.plugin.model.IAttribute;
import com.vp.plugin.model.IClass;
import com.vp.plugin.model.IModelElement;
import com.vp.plugin.model.IOperation;
import com.vp.plugin.model.IRelationshipEnd;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

/**
 * Abstract class with the common methods for the processors of the classes in the FrameWeb project.
 *
 * @author <a href="https://github.com/igorssilva">Igor Sunderhus e Silva</a>
 * @version 0.0.1
 */
public abstract class AbstractClassProcessor {
    /**
     * Generates the code for an entity class in the FrameWeb project.
     * <p>
     * The data model is defined by the specific implementation of {@link AbstractTemplateModel}
     *
     * @param clazz     The class processed.
     * @param engine    The FreeMarker engine used to generate the code.
     * @param fileTypes the file types used.
     */
    public void process(final IClass clazz, final FreeMarkerEngine engine, final FileTypes fileTypes) {

        final AbstractTemplateModel dataModel = getTemplateModel(clazz);

        addSpecificData(dataModel, clazz);

        try {
            engine.generateCode(fileTypes, dataModel);
        } catch (IOException | TemplateException e) {
            ViewManagerUtils.showMessageDialog("Error generating code for " + clazz.getName()
                                               + " class. Code of this class will not be generated. Please, look at the loggers for more information.");
            Logger.log(Level.SEVERE, "Error generating code for " + clazz.getName() + " class.", e);
        }
    }

    private AbstractTemplateModel getTemplateModel(final IClass clazz) {
        final IModelElement pack = clazz.getParent();
        final AbstractClassModel classModel = getClassModel(clazz);
        final String path = packageNameToPath(pack.getName());

        Logger.log(Level.FINE, "################# " + clazz.getName());

        final List<AbstractAttributeModel> entityAttributeModels = processAttribute(clazz);

        final List<AbstractMethodModel> abstractMethodModels = processMethod(clazz);

        final List<AbstractAssociationModel> entityAssociationModels = processAssociation(clazz);

        return processTemplateModel(pack, classModel, path, abstractMethodModels, entityAttributeModels,
                entityAssociationModels);
    }

    /**
     * Delegates the addition of specific data to the subclasses.
     *
     * @param dataModel The data model.
     * @param clazz     The class processed.
     */
    protected abstract void addSpecificData(final AbstractTemplateModel dataModel, final IClass clazz);

    /**
     * Abstract method to delegate the generation of the class model to the subclasses.
     *
     * @param clazz The class processed.
     * @return The class model.
     */
    protected abstract AbstractClassModel getClassModel(final IClass clazz);

    /**
     * Transforms a java package name with "." separator to a file system path
     *
     * @param packageName The package name
     * @return packagePath
     */
    public static String packageNameToPath(String packageName) {
        return packageName.replaceAll("[^A-Za-z0-9]", "/");
    }

    /**
     * Generates the code for the attributes of a class in the FrameWeb project.
     *
     * @param clazz The class processed.
     * @return The list of attributes of the class.
     */
    public List<AbstractAttributeModel> processAttribute(final IClass clazz) {
        final List<AbstractAttributeModel> entityAttributeModels = new ArrayList<>();

        @SuppressWarnings("unchecked") Iterator<IAttribute> attributeIter = clazz.attributeIterator();

        attributeIter.forEachRemaining(attribute -> {
            Logger.log(Level.FINE,
                    "################# Attribute " + attribute.getName() + " (" + attribute.getType() + ")");

            entityAttributeModels.add(getAttributeModel(attribute));
        });
        return entityAttributeModels;
    }

    /**
     * Generates the code for the methods of a class in the FrameWeb project.
     *
     * @param clazz The class processed.
     * @return The list of methods of the class.
     */
    public List<AbstractMethodModel> processMethod(final IClass clazz) {
        final List<AbstractMethodModel> entityMethodModels = new ArrayList<>();

        @SuppressWarnings("unchecked") Iterator<IOperation> operationIterator = clazz.operationIterator();

        operationIterator.forEachRemaining(operation -> {
            Logger.log(Level.FINE,
                    "################# Attribute " + operation.getName() + " (" + operation.getReturnTypeAsString()
                    + ")");
            entityMethodModels.add(getMethodModel(operation));
        });

        return entityMethodModels;
    }

    /**
     * Generates the code for the associations of an entity class in the FrameWeb project.
     *
     * @param clazz The class processed.
     * @return The list of associations of the class.
     */
    public List<AbstractAssociationModel> processAssociation(final IClass clazz) {

        final List<AbstractAssociationModel> entityAssociationModels = new ArrayList<>();

        @SuppressWarnings("unchecked") final Iterator<IRelationshipEnd> iterator = clazz.fromRelationshipEndIterator();
        iterator.forEachRemaining(relationship -> {

            Logger.log(Level.FINE,
                    "################# From relationship " + relationship.getName() + " (" + relationship.getModelType()
                    + ")");

            entityAssociationModels.add(getAssociationModel(relationship));

        });

        @SuppressWarnings("unchecked") final Iterator<IRelationshipEnd> iterator2 = clazz.toRelationshipEndIterator();
        iterator2.forEachRemaining(relationship -> {

            Logger.log(Level.FINE,
                    "################# To relationship " + relationship.getName() + " (" + relationship.getModelType()
                    + ")");

            entityAssociationModels.add(getAssociationModel(relationship));

        });

        return entityAssociationModels;
    }

    /**
     * Delegates the generation of the template model to the subclasses.
     *
     * @param pack                    Package of the class.
     * @param classModel              Class model.
     * @param path                    System path for the file.
     * @param abstractMethodModels    Methods of the class.
     * @param entityAttributeModels   Attributes of the class.
     * @param entityAssociationModels Associations of the class.
     * @return The template model for the engine.
     */
    protected abstract AbstractTemplateModel processTemplateModel(final IModelElement pack,
            final AbstractClassModel classModel, final String path,
            final List<AbstractMethodModel> abstractMethodModels,
            final List<AbstractAttributeModel> entityAttributeModels,
            final List<AbstractAssociationModel> entityAssociationModels);

    /**
     * Delegates the generation of the attribute model to the subclasses.
     *
     * @param attribute The attribute processed.
     * @return The attribute model.
     */
    protected abstract AbstractAttributeModel getAttributeModel(final IAttribute attribute);

    /**
     * Delegates the generation of the method model to the subclasses.
     *
     * @param operation The operation processed.
     * @return The method model.
     */
    protected abstract AbstractMethodModel getMethodModel(final IOperation operation);

    /**
     * Delegates the generation of the association model to the subclasses.
     *
     * @param relationship The relationship processed.
     * @return The association model.
     */
    protected abstract AbstractAssociationModel getAssociationModel(final IRelationshipEnd relationship);

}
