package br.ufes.inf.nemo.frameweb.vp.utils;

import br.ufes.inf.nemo.frameweb.vp.FrameWebPlugin;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebClass;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebPackage;
import br.ufes.inf.nemo.vpzy.engine.FreeMarkerEngine;
import br.ufes.inf.nemo.vpzy.engine.models.base.FileTypes;
import br.ufes.inf.nemo.vpzy.engine.models.base.MethodModel;
import br.ufes.inf.nemo.vpzy.engine.models.base.TemplateOption;
import br.ufes.inf.nemo.vpzy.engine.models.entity.AttributeModel;
import br.ufes.inf.nemo.vpzy.engine.models.entity.ClassModel;
import br.ufes.inf.nemo.vpzy.engine.models.entity.RelationshipModel;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import br.ufes.inf.nemo.vpzy.managers.YamlConfigurationManager;
import br.ufes.inf.nemo.vpzy.utils.ProjectManagerUtils;
import com.vp.plugin.model.IAttribute;
import com.vp.plugin.model.IClass;
import com.vp.plugin.model.IOperation;
import com.vp.plugin.model.IPackage;
import com.vp.plugin.model.IProject;
import com.vp.plugin.model.IRelationshipEnd;
import com.vp.plugin.model.factory.IModelElementFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * Utility class for generating code from templates for the current project in Visual Paradigm.
 *
 * @author Igor Sunderhus e Silva (<a href="https://github.com/igorssilva">Github page</a>)
 */
public final class TemplateUtils {
    private TemplateUtils() {
        // Prevents instantiation.
    }

    /**
     * Generates the code for the FrameWeb project.
     *
     * @param templateName The name of the template used.
     * @param outputDir    The directory for the generated code.
     */
    public static void generateCode(final String templateName, final String outputDir) {
        final IProject project = ProjectManagerUtils.getCurrentProject();

        final TemplateOption templateOption = getTemplateOption(templateName);

        final FreeMarkerEngine engine = new FreeMarkerEngine(templateOption.getTemplatePath(), outputDir);

        @SuppressWarnings("unchecked") Iterator<IPackage> iter = project.allLevelModelElementIterator(
                IModelElementFactory.MODEL_TYPE_PACKAGE);

        // process packages
        iter.forEachRemaining(pack -> processPackage(pack, engine, templateOption));

    }

    /**
     * Generates the code for the FrameWeb project.
     *
     * @param templateOption The template option used.
     */
    public static void generateCode( final TemplateOption templateOption) {
        final IProject project = ProjectManagerUtils.getCurrentProject();

        final FreeMarkerEngine engine = new FreeMarkerEngine(templateOption.getTemplatePath(), templateOption.getOutputPath());

        @SuppressWarnings("unchecked") Iterator<IPackage> iter = project.allLevelModelElementIterator(
                IModelElementFactory.MODEL_TYPE_PACKAGE);

        // process packages
        iter.forEachRemaining(pack -> processPackage(pack, engine, templateOption));

    }

    /**
     * Gets the template option for the given template name. The template should be defined in the configuration file.
     *
     * @param templateName The name of the template.
     * @return The configurations for the template.
     * @throws IllegalArgumentException if the template option is not found or is invalid.
     */
    public static TemplateOption getTemplateOption(final String templateName) {
        final YamlConfigurationManager configurationManager = FrameWebPlugin.instance()
                .getGenerateCodeConfigManager();

        final TemplateOption templateOption = configurationManager.getProperty(templateName);

        if (templateOption == null) {
            throw new IllegalArgumentException("Template option not found: " + templateName);
        }

        templateOption.validate();

        return templateOption;

    }

    /**
     * Generates the code for a package in the FrameWeb project.
     *
     * @param pack           The package processed.
     * @param engine         The FreeMarker engine used to generate the code.
     * @param templateOption The template option used.
     */
    public static void processPackage(final IPackage pack, final FreeMarkerEngine engine,
            final TemplateOption templateOption) {

        final FrameWebPackage frameWebPackage = FrameWebUtils.getFrameWebPackage(pack);
        if (frameWebPackage == FrameWebPackage.NOT_A_FRAMEWEB_PACKAGE) {
            Logger.log(Level.FINE, "####### skipping " + pack.getName() + " (" + frameWebPackage + ")");
            return;
        }

        Logger.log(Level.FINE, "####### " + pack.getName() + " (" + frameWebPackage + ")");

        @SuppressWarnings("unchecked") Iterator<IClass> classIter = pack.childIterator(
                IModelElementFactory.MODEL_TYPE_CLASS);

        switch (frameWebPackage) {
            case ENTITY_PACKAGE:
                classIter.forEachRemaining(clazz -> processClass(clazz, engine, templateOption));
                break;
            case APPLICATION_PACKAGE:
                // TODO: process application package
                break;
            case CONTROLLER_PACKAGE:
                // TODO: process controller package
                break;

            case PERSISTENCE_PACKAGE:
                // TODO: process persistence package
                break;

            default:
                break;
        }

    }

    /**
     * Generates the code for a class in the FrameWeb project.
     *
     * @param clazz          The class processed.
     * @param engine         The FreeMarker engine used to generate the code.
     * @param templateOption The template option used.
     */
    public static void processClass(IClass clazz, final FreeMarkerEngine engine, final TemplateOption templateOption) {

        final FrameWebClass frameWebClass = FrameWebUtils.getFrameWebClass(clazz);

        Logger.log(Level.INFO, "################# " + clazz.getName() + " (" + frameWebClass + ")");

        switch (frameWebClass) {
            case PERSISTENT_CLASS:
                final FileTypes fileTypes;
                if (clazz.hasStereotypes("enumeration")) {
                    fileTypes = templateOption.getEnumeration();
                } else {
                    fileTypes = templateOption.getEntity();
                }
                processDomainClass(clazz, engine, fileTypes);
                break;

            case MAPPED_SUPERCLASS:
                processDomainClass(clazz, engine, templateOption.getMappedSuperclass());
                break;

            case TRANSIENT_CLASS:
                processDomainClass(clazz, engine, templateOption.getTransientClass());
                break;

            case CONTROLLER_CLASS:
                processController(clazz, engine);
                break;

            case SERVICE_CLASS:
                processService(clazz, engine);
                break;

            case DAO_CLASS:
                processDao(clazz, engine);
                break;
            default:
                break;
        }

    }

    /**
     * Generates the code for a domain class in the FrameWeb project.
     *
     * @param clazz     The class processed.
     * @param engine    The FreeMarker engine used to generate the code.
     * @param fileTypes the file types used.
     */
    public static void processDomainClass(final IClass clazz, final FreeMarkerEngine engine,
            final FileTypes fileTypes) {

        final Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("package", clazz.getParent());
        dataModel.put("class", new ClassModel(clazz));
        dataModel.put("path", packageNameToPath(clazz.getParent().getName()));

        final List<AttributeModel> attributeModels = processAttribute(clazz);

        dataModel.put("attributes", attributeModels);

        final List<RelationshipModel> relationshipModels = processAssociation(clazz);

        dataModel.put("associations", relationshipModels);

        Logger.log(Level.FINE, "################# " + clazz.getName() + " (" + dataModel + ")");

        final List<MethodModel> methodModels = processMethod(clazz);

        dataModel.put("methods", methodModels);

        try {
            engine.generateCode(fileTypes, dataModel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void processController(final IClass clazz, final FreeMarkerEngine engine) {
        // TODO: process controller
    }

    public static void processService(final IClass clazz, final FreeMarkerEngine engine) {
        // TODO: process service
    }

    public static void processDao(final IClass clazz, final FreeMarkerEngine engine) {
        // TODO: process dao
    }

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
    public static List<AttributeModel> processAttribute(final IClass clazz) {
        final List<AttributeModel> attributeModels = new ArrayList<>();

        @SuppressWarnings("unchecked") Iterator<IAttribute> attributeIter = clazz.attributeIterator();

        attributeIter.forEachRemaining(attribute -> {
            Logger.log(Level.INFO,
                    "################# Attribute " + attribute.getName() + " (" + attribute.getType() + ")");

            attributeModels.add(new AttributeModel(attribute));
        });
        return attributeModels;
    }

    /**
     * Generates the code for the associations of a class in the FrameWeb project.
     *
     * @param clazz The class processed.
     * @return The list of associations of the class.
     */
    public static List<RelationshipModel> processAssociation(final IClass clazz) {

        final List<RelationshipModel> relationshipModels = new ArrayList<>();

        @SuppressWarnings("unchecked") final Iterator<IRelationshipEnd> iterator = clazz.fromRelationshipEndIterator();
        iterator.forEachRemaining(relationship -> {

            Logger.log(Level.FINE,
                    "################# From relationship " + relationship.getName() + " (" + relationship.getModelType()
                    + ")");

            relationshipModels.add(new RelationshipModel(relationship));

        });

        @SuppressWarnings("unchecked") final Iterator<IRelationshipEnd> iterator2 = clazz.toRelationshipEndIterator();
        iterator2.forEachRemaining(relationship -> {

            Logger.log(Level.FINE,
                    "################# To relationship " + relationship.getName() + " (" + relationship.getModelType()
                    + ")");

            relationshipModels.add(new RelationshipModel(relationship));

        });

        return relationshipModels;
    }

    /**
     * Generates the code for the methods of a class in the FrameWeb project.
     *
     * @param clazz The class processed.
     * @return The list of methods of the class.
     */
    public static List<MethodModel> processMethod(final IClass clazz) {
        final List<MethodModel> methodModels = new ArrayList<>();

        @SuppressWarnings("unchecked") Iterator<IOperation> operationIterator = clazz.operationIterator();

        operationIterator.forEachRemaining(operation -> {
            Logger.log(Level.INFO,
                    "################# Attribute " + operation.getName() + " (" + operation.getReturnTypeAsString()
                    + ")");
            final MethodModel methodModel = new MethodModel(operation);
            methodModels.add(methodModel);
        });

        return methodModels;
    }

    /**
     * Gets the template options defined in the configuration file.
     *
     * @return The configurations for the templates.
     * @throws IllegalArgumentException if the template options are not found or are invalid.
     */
    public static Map<String, TemplateOption> getTemplateOptions() {
        final YamlConfigurationManager configurationManager = FrameWebPlugin.instance()
                .getGenerateCodeConfigManager();

        final Map<String, TemplateOption> templateOptions = configurationManager.getOptions();

        if (templateOptions == null) {
            throw new IllegalArgumentException("Template options not found");
        }

        return templateOptions;

    }
}
