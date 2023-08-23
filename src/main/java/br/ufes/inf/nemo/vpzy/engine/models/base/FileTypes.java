package br.ufes.inf.nemo.vpzy.engine.models.base;

/**
 * Holds and validates the information about a file type.
 *
 * @author Igor Sunderhus e Silva (<a href="https://github.com/igorssilva">Github page</a>)
 */
public class FileTypes {
    private String template = "template.ftl";

    private String extension = ".java";

    public FileTypes() {
        // Default Constructor for Yaml
    }

    public FileTypes(final String template, final String extension) {
        this.template = template;
        this.extension = extension;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(final String template) {
        this.template = template;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(final String extension) {
        this.extension = extension;
    }

    public void validate() {
        if (template == null || template.trim().isEmpty() || extension == null || extension.trim().isEmpty()) {
            throw new IllegalArgumentException("Missing required properties for file type");
        }
    }
}
