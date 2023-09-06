package br.ufes.inf.nemo.vpzy.engine.models.base;

import br.ufes.inf.nemo.vpzy.logging.Logger;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;

/**
 * Holds and validates the information about a file type.
 *
 * @author Igor Sunderhus e Silva (<a href="https://github.com/igorssilva">Github page</a>)
 */
public class FileTypes implements Serializable {
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

    public boolean invalidTemplate(final String templateFolder) {
        if (Files.notExists(Paths.get(templateFolder, this.template))) {
            Logger.log(Level.SEVERE, "Could not find {0} template at {1}.",
                    new Object[] { this.template, templateFolder });
            return true;
        }
        return false;
    }
}
