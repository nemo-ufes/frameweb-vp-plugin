package br.ufes.inf.nemo.vpzy.engine.models.base;

import java.io.Serializable;

/**
 * Holds and validates all the properties that a template option must have.
 *
 * @author Igor Sunderhus e Silva (<a href="https://github.com/igorssilva">Github page</a>)
 */
public class TemplateOption implements Serializable {
    private static final String EXTENSION = ".java";

    private String name = "Template Name";

    private String description = "Template Description";

    private String templatePath = "templates";

    private String outputPath = "output";

    private FileTypes entity = new FileTypes("EntityClassTemplate.ftl", EXTENSION);

    private FileTypes enumeration = new FileTypes("EnumerationClassTemplate.ftl", EXTENSION);

    private FileTypes mappedSuperclass = new FileTypes("MappedSuperclassTemplate.ftl", EXTENSION);

    private FileTypes transientClass = new FileTypes("TransientClassTemplate.ftl", EXTENSION);

    private FileTypes embeddable = new FileTypes("EmbeddableClassTemplate.ftl", EXTENSION);

    private FileTypes dao = new FileTypes("DaoClassTemplate.ftl", EXTENSION);

    private FileTypes service = new FileTypes("ServiceClassTemplate.ftl", EXTENSION);

    private FileTypes controller = new FileTypes("ControllerClassTemplate.ftl", EXTENSION);

    public TemplateOption() {
        // Default Constructor for Yaml
    }

    public TemplateOption(final String name, final String description, final String templatePath,
            final String outputPath, final FileTypes entity, final FileTypes enumeration,
            final FileTypes mappedSuperclass, final FileTypes transientClass, final FileTypes embeddable,
            final FileTypes dao, final FileTypes service, final FileTypes controller) {
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
        this.service = service;
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
            || transientClass == null || embeddable == null || dao == null || service == null || controller == null) {
            throw new IllegalArgumentException("Missing required properties for template");
        }

        entity.validate();
        enumeration.validate();
        mappedSuperclass.validate();
        transientClass.validate();
        embeddable.validate();
        dao.validate();
        service.validate();
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

    public FileTypes getService() {
        return service;
    }

    public void setService(final FileTypes service) {
        this.service = service;
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
