package br.ufes.inf.nemo.vpzy.engine;

import br.ufes.inf.nemo.vpzy.engine.models.base.AbstractTemplateModel;
import br.ufes.inf.nemo.vpzy.engine.models.base.FileTypes;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import br.ufes.inf.nemo.vpzy.utils.ProjectManagerUtils;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;

/**
 * Configuration and code generation using templates for FreeMarker.
 *
 * @author <a href="https://github.com/igorssilva">Igor Sunderhus e Silva</a>
 */
public class FreeMarkerEngine {
    private final Configuration cfg;

    private final String outputDirectory;

    /**
     * Configuration given the template path and the output directory.
     *
     * @param templatePath    The path to the template directory.
     * @param outputDirectory The path to the output directory.
     */
    public FreeMarkerEngine(final String templatePath, final String outputDirectory) {
        cfg = new Configuration(Configuration.VERSION_2_3_31);

        this.outputDirectory = outputDirectory;
        cfg.clearTemplateCache();
        FileTemplateLoader templateLoader = null;
        try {
            // Set the template loader to load templates from the file system.
            templateLoader = new FileTemplateLoader(new File(templatePath));
        } catch (IOException e) {
            // If the file system template loader fails, log the error and continue.
            // This is not a fatal error, as the classpath template loader should still work.
            Logger.log(Level.SEVERE, "An error occurred while loading the template." , e);
        }
        cfg.setTemplateLoader(templateLoader);

    }

    public static boolean validateTemplateStructures(final File rootTemplateFolder) {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        // Set the root template folder
        if (rootTemplateFolder.exists() && rootTemplateFolder.isDirectory()) {
            return validateTemplatesInFolder(rootTemplateFolder, cfg);
        } else {
            Logger.log(Level.SEVERE, "Root template folder does not exist or is not a directory." );
            return false;
        }
    }

    private static boolean validateTemplatesInFolder(File folder, Configuration cfg) {
        File[] files = folder.listFiles();
        boolean validStructure = true;

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // Recursively process subfolders
                    if (!validateTemplatesInFolder(file, cfg)) {
                        validStructure = false;
                    }
                } else if (file.isFile() && isTemplateFile(file)) {
                    try (FileReader reader = new FileReader(file)) {
                        new Template(file.getName(), reader, cfg);
                        // No need to process, just parsing is enough to check for syntax errors
                    } catch (IOException e) {
                        // Handle template loading error
                        Logger.log(Level.SEVERE, "Error loading template: " + file.getAbsolutePath(), e);
                        validStructure = false;
                    }
                }
            }
        }

        return validStructure;
    }

    private static boolean isTemplateFile(File file) {
        // You can define the criteria for identifying template files, e.g., file extension
        return file.getName().endsWith(".ftl" );
    }

    /**
     * Generates code given a template option and a data model.
     *
     * @param templateOption The template option to be used.
     * @param dataModel      The data model to be used.
     * @throws IOException       If an error occurs while writing to file.
     * @throws TemplateException If an error occurs while processing the template.
     */
    public void generateCode(final FileTypes templateOption, final AbstractTemplateModel dataModel)
            throws IOException, TemplateException {
        Template template = this.cfg.getTemplate(templateOption.getTemplate());

        try {
            // Define the file path
            final String pathString = String.format("%s/%s/%s%s" ,
                    outputDirectory.replace("{projectName}" , ProjectManagerUtils.getCurrentProject().getName()),
                    dataModel.getPath(), dataModel.getClazz().getName(), templateOption.getExtension());
            Path path = Paths.get(pathString);

            // Create the necessary directories if they don't already exist
            Files.createDirectories(path.getParent());

            // Write the data model to the file described by the template
            FileWriter writer = new FileWriter(pathString);
            template.process(dataModel, writer);
            writer.flush();
            writer.close();
            Logger.log(Level.FINE, "Code generated successfully" );
        } catch (IOException | TemplateException e) {
            Logger.log(Level.SEVERE, "An error occurred while writing to file." );
            throw e;
        }
    }

}
