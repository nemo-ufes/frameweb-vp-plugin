package br.ufes.inf.nemo.vpzy.engine.models.base;

/**
 * Holds and validates all the properties that a template option must have.
 *
 * @author Igor Sunderhus e Silva (<a href="https://github.com/igorssilva">Github page</a>)
 */
public class TemplateOption {
    private String description;

    private String templatePath;

    private String outputPath;

    private FileTypes entity;

    private FileTypes enumeration;

    private FileTypes mappedSuperclass;

    private FileTypes transientClass;

    private FileTypes embeddable;

    private FileTypes dao;

    private FileTypes service;

    private FileTypes controller;

    /**
     * Validates the template option. If any of the required properties is null, throws an
     * {@link IllegalArgumentException}.
     */
    public void validate() {
        if (outputPath == null || entity == null || description == null || templatePath == null || enumeration == null
            || mappedSuperclass == null || transientClass == null || embeddable == null || dao == null
            || service == null || controller == null) {
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
}
