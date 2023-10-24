package br.ufes.inf.nemo.vpzy.engine.models.base;

import br.ufes.inf.nemo.vpzy.logging.Logger;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;

/**
 * Holds and validates the information for the template of a given file type.
 *
 * @author <a href="https://github.com/igorssilva">Igor Sunderhus e Silva</a>
 */
public class FileTypes implements Serializable {
    /**
     * The name of the template file.
     * The template file must be located in the template folder defined in the configuration file.
     */
    private String template = "template.ftl";

    /**
     * The extension for the output file.
     */
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


    public String getExtension() {
        return extension;
    }

    public void setTemplate(final String template) {
        this.template = template;
    }

    public void setExtension(final String extension) {
        this.extension = extension;
    }

    /**
     * Validates the required properties for the file type.
     * If any of the required properties is missing, an {@link IllegalArgumentException} is thrown.
     */
    public void validate() {
        if (template == null || template.trim().isEmpty() || extension == null || extension.trim().isEmpty()) {
            throw new IllegalArgumentException("Missing required properties for file type");
        }
    }

    /**
     * Validates if the template file exists.
     * @param templateFolder The folder where the template file is located.
     * @return True if the template file does not exist, false otherwise.
     */
    public boolean invalidTemplate(final String templateFolder) {
        if (Files.notExists(Paths.get(templateFolder, this.template))) {
            Logger.log(Level.SEVERE, "Could not find {0} template at {1}.",
                    new Object[] { this.template, templateFolder });
            return true;
        }
        return false;
    }
}
