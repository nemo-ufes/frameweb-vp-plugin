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
     * The name of the template file. The template file must be located in the template folder defined in the
     * configuration file.
     */
    private String template = "template.ftl";

    /**
     * The extension for the output file.
     */
    private String extension = ".java";

    /**
     * Default constructor for Yaml.
     */
    public FileTypes() {
        // Default Constructor for Yaml
    }

    /**
     * Creates a new file type.
     *
     * @param template  The name of the template file.
     * @param extension The extension for the output file.
     */
    public FileTypes(final String template, final String extension) {
        this.template = template;
        this.extension = extension;
    }

    /**
     * The name of the template file. The template file must be located in the template folder defined in the
     * configuration file.
     *
     * @return The name of the template file.
     */
    public String getTemplate() {
        return template;
    }

    /**
     * {@link FileTypes#getTemplate}
     */
    public void setTemplate(final String template) {
        this.template = template;
    }

    /**
     * The extension for the output file. The extension must include the dot.
     *
     * @return The extension for the output file.
     */
    public String getExtension() {
        return extension;
    }

    /**
     * {@link FileTypes#getExtension}
     */
    public void setExtension(final String extension) {
        this.extension = extension;
    }

    /**
     * Validates the required properties for the file type. If any of the required properties is missing, an
     * {@link IllegalArgumentException} is thrown.
     */
    public void validate() {
        if (template == null || template.trim().isEmpty() || extension == null || extension.trim().isEmpty()) {
            throw new IllegalArgumentException("Missing required properties for file type");
        }
    }

    /**
     * Validates if the template file exists.
     *
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
