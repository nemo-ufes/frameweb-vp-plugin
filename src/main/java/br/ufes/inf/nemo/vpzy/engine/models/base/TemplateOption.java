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

    /**
     * Get the name of the template option.
     *
     * @return The name of the template option.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the template option.
     *
     * @param name The name of the template option.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Get the description of the template option.
     *
     * @return The description of the template option.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the template option.
     *
     * @param description The description of the template option.
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Get the path to the template files. Should be an absolute path for the importing to work.
     *
     * @return The path to the template files.
     */
    public String getTemplatePath() {
        return templatePath;
    }

    /**
     * Set the path to the template files. Should be an absolute path for the importing to work.
     *
     * @param templatePath The path to the template files.
     */
    public void setTemplatePath(final String templatePath) {
        this.templatePath = templatePath;
    }

    /**
     * Get the path to the output files. Should be an absolute path for the export to work.
     *
     * @return The path to the output files.
     */
    public String getOutputPath() {
        return outputPath;
    }

    /**
     * Set the path to the output files. Should be an absolute path for the export to work.
     *
     * @param outputPath The path to the output files.
     */
    public void setOutputPath(final String outputPath) {
        this.outputPath = outputPath;
    }

    /**
     * Get the template information for the entity class.
     *
     * @return The template information for the entity class.
     */
    public FileTypes getEntity() {
        return entity;
    }

    /**
     * Set the template information for the entity class.
     *
     * @param entity The template information for the entity class.
     */
    public void setEntity(final FileTypes entity) {
        this.entity = entity;
    }

    /**
     * Get the template information for the enumeration class.
     *
     * @return The template information for the enumeration class.
     */
    public FileTypes getEnumeration() {
        return enumeration;
    }

    /**
     * Set the template information for the enumeration class.
     *
     * @param enumeration The template information for the enumeration class.
     */
    public void setEnumeration(final FileTypes enumeration) {
        this.enumeration = enumeration;
    }

    /**
     * Get the template information for the mapped superclass.
     *
     * @return The template information for the mapped superclass.
     */
    public FileTypes getMappedSuperclass() {
        return mappedSuperclass;
    }

    /**
     * Set the template information for the mapped superclass.
     *
     * @param mappedSuperclass The template information for the mapped superclass.
     */
    public void setMappedSuperclass(final FileTypes mappedSuperclass) {
        this.mappedSuperclass = mappedSuperclass;
    }

    /**
     * Get the template information for the transient class.
     *
     * @return The template information for the transient class.
     */
    public FileTypes getTransientClass() {
        return transientClass;
    }

    /**
     * Set the template information for the transient class.
     *
     * @param transientClass The template information for the transient class.
     */
    public void setTransientClass(final FileTypes transientClass) {
        this.transientClass = transientClass;
    }

    /**
     * Get the template information for the embeddable class.
     *
     * @return The template information for the embeddable class.
     */
    public FileTypes getEmbeddable() {
        return embeddable;
    }

    /**
     * Set the template information for the embeddable class.
     *
     * @param embeddable The template information for the embeddable class.
     */
    public void setEmbeddable(final FileTypes embeddable) {
        this.embeddable = embeddable;
    }

    /**
     * Get the template information for the DAO class.
     *
     * @return The template information for the DAO class.
     */
    public FileTypes getDao() {
        return dao;
    }

    /**
     * Set the template information for the DAO class.
     *
     * @param dao The template information for the DAO class.
     */
    public void setDao(final FileTypes dao) {
        this.dao = dao;
    }

    /**
     * Get the template information for the DAO interface.
     *
     * @return The template information for the DAO interface.
     */
    public FileTypes getDaoInterface() {
        return daoInterface;
    }

    /**
     * Set the template information for the DAO interface.
     *
     * @param daoInterface The template information for the DAO interface.
     */
    public void setDaoInterface(final FileTypes daoInterface) {
        this.daoInterface = daoInterface;
    }

    /**
     * Get the template information for the Service class.
     *
     * @return The template information for the Service class.
     */
    public FileTypes getService() {
        return service;
    }

    /**
     * Set the template information for the Service class.
     *
     * @param service The template information for the Service class.
     */
    public void setService(final FileTypes service) {
        this.service = service;
    }

    /**
     * Get the template information for the Service interface.
     *
     * @return The template information for the Service interface.
     */
    public FileTypes getServiceInterface() {
        return serviceInterface;
    }

    /**
     * Set the template information for the Service interface.
     *
     * @param serviceInterface The template information for the Service interface.
     */
    public void setServiceInterface(final FileTypes serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    /**
     * Get the template information for the Controller class.
     *
     * @return The template information for the Controller class.
     */
    public FileTypes getController() {
        return controller;
    }

    /**
     * Set the template information for the Controller class.
     *
     * @param controller The template information for the Controller class.
     */
    public void setController(final FileTypes controller) {
        this.controller = controller;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
