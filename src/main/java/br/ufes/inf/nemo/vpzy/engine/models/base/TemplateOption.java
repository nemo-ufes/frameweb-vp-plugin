package br.ufes.inf.nemo.vpzy.engine.models.base;

import java.io.Serializable;

/**
 * Holds and validates all the properties that a template option must have.
 *
 * @author <a href="https://github.com/igorssilva">Igor Sunderhus e Silva</a>
 */
public class TemplateOption implements Serializable {
    private static final String EXTENSION = ".java";

    /**
     * The name of the template option.
     * <p>
     * e.g. "Spring Boot"
     */
    private String name = "Template Name";

    /**
     * The description of the template option.
     * <p>
     * e.g. "Generates a Spring Boot project"
     */
    private String description = "Template Description";

    /**
     * The path to the template files. Should be an absolute path for the importing to work.
     * <p>
     * e.g. "C:\templates\spring"
     */
    private String templatePath = "templates";

    /**
     * The path to the output files. Should be an absolute path for the export to work.
     * <p>
     * e.g. ":C\output\spring"
     */
    private String outputPath = "output";

    /**
     * The file types for the entity class.
     */
    private FileTypes entity = new FileTypes("EntityClassTemplate.ftl", EXTENSION);

    /**
     * Template information for the enumeration class.
     */
    private FileTypes enumeration = new FileTypes("EnumerationClassTemplate.ftl", EXTENSION);

    /**
     * Template information for the mapped superclass.
     */
    private FileTypes mappedSuperclass = new FileTypes("MappedSuperclassTemplate.ftl", EXTENSION);

    /**
     * Template information for the transient class.
     */
    private FileTypes transientClass = new FileTypes("TransientClassTemplate.ftl", EXTENSION);

    /**
     * Template information for the embeddable class.
     */
    private FileTypes embeddable = new FileTypes("EmbeddableClassTemplate.ftl", EXTENSION);

    /**
     * Template information for the DAO class.
     */
    private FileTypes dao = new FileTypes("DaoClassTemplate.ftl", EXTENSION);

    /**
     * Template information for the DAO interface.
     */
    private FileTypes daoInterface = new FileTypes("DaoInterfaceTemplate.ftl", EXTENSION);

    /**
     * Template information for the Service class.
     */
    private FileTypes service = new FileTypes("ServiceClassTemplate.ftl", EXTENSION);

    /**
     * Template information for the Service interface.
     */
    private FileTypes serviceInterface = new FileTypes("ServiceInterfaceTemplate.ftl", EXTENSION);

    /**
     * Template information for the Controller class.
     */
    private FileTypes controller = new FileTypes("ControllerClassTemplate.ftl", EXTENSION);

    public TemplateOption() {
        // Default Constructor for Yaml
    }

    /**
     * Creates a new template option.
     *
     * @param name             The name of the template option.
     * @param description      The description of the template option.
     * @param templatePath     The path to the template files.
     * @param outputPath       The path to the output files.
     * @param entity           The file types for the entity class.
     * @param enumeration      Template information for the enumeration class.
     * @param mappedSuperclass Template information for the mapped superclass.
     * @param transientClass   Template information for the transient class.
     * @param embeddable       Template information for the embeddable class.
     * @param dao              Template information for the DAO class.
     * @param daoInterface     Template information for the DAO interface.
     * @param service          Template information for the Service class.
     * @param serviceInterface Template information for the Service interface.
     * @param controller       Template information for the Controller class.
     */
    public TemplateOption(final String name, final String description, final String templatePath,
            final String outputPath, final FileTypes entity, final FileTypes enumeration,
            final FileTypes mappedSuperclass, final FileTypes transientClass, final FileTypes embeddable,
            final FileTypes dao, final FileTypes daoInterface, final FileTypes service,
            final FileTypes serviceInterface, final FileTypes controller) {
        this.name = name;
        this.description = description;
        this.templatePath = templatePath;
        this.outputPath = outputPath;
        this.entity = entity;
        this.enumeration = enumeration;
        this.mappedSuperclass = mappedSuperclass;
        this.transientClass = transientClass;
        this.embeddable = embeddable;
        this.dao = dao;
        this.daoInterface = daoInterface;
        this.service = service;
        this.serviceInterface = serviceInterface;
        this.controller = controller;
    }

    /**
     * Validates the template option. If any of the required properties is null, throws an
     * {@link IllegalArgumentException}.
     */
    public void validate() {
        if (name == null || name.trim().isEmpty() || outputPath == null || outputPath.trim().isEmpty()
            || description == null || description.trim().isEmpty() || templatePath == null || templatePath.trim()
                    .isEmpty() || entity == null || enumeration == null || mappedSuperclass == null
            || transientClass == null || embeddable == null || dao == null || daoInterface == null || service == null
            || serviceInterface == null || controller == null) {
            throw new IllegalArgumentException("Missing required properties for template");
        }

        entity.validate();
        enumeration.validate();
        mappedSuperclass.validate();
        transientClass.validate();
        embeddable.validate();
        dao.validate();
        daoInterface.validate();
        service.validate();
        serviceInterface.validate();
        controller.validate();

    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(final String templatePath) {
        this.templatePath = templatePath;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(final String outputPath) {
        this.outputPath = outputPath;
    }

    public FileTypes getEntity() {
        return entity;
    }

    public void setEntity(final FileTypes entity) {
        this.entity = entity;
    }

    public FileTypes getEnumeration() {
        return enumeration;
    }

    public void setEnumeration(final FileTypes enumeration) {
        this.enumeration = enumeration;
    }

    public FileTypes getMappedSuperclass() {
        return mappedSuperclass;
    }

    public void setMappedSuperclass(final FileTypes mappedSuperclass) {
        this.mappedSuperclass = mappedSuperclass;
    }

    public FileTypes getTransientClass() {
        return transientClass;
    }

    public void setTransientClass(final FileTypes transientClass) {
        this.transientClass = transientClass;
    }

    public FileTypes getEmbeddable() {
        return embeddable;
    }

    public void setEmbeddable(final FileTypes embeddable) {
        this.embeddable = embeddable;
    }

    public FileTypes getDao() {
        return dao;
    }

    public void setDao(final FileTypes dao) {
        this.dao = dao;
    }

    public FileTypes getDaoInterface() {
        return daoInterface;
    }

    public void setDaoInterface(final FileTypes daoInterface) {
        this.daoInterface = daoInterface;
    }

    public FileTypes getService() {
        return service;
    }

    public void setService(final FileTypes service) {
        this.service = service;
    }

    public FileTypes getServiceInterface() {
        return serviceInterface;
    }

    public void setServiceInterface(final FileTypes serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    public FileTypes getController() {
        return controller;
    }

    public void setController(final FileTypes controller) {
        this.controller = controller;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
