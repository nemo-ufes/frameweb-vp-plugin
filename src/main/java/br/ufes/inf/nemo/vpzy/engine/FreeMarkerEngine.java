package br.ufes.inf.nemo.vpzy.engine;

import br.ufes.inf.nemo.vpzy.engine.models.base.FileTypes;
import br.ufes.inf.nemo.vpzy.engine.models.entity.ClassModel;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.logging.Level;

/**
 * Generates code from a template using FreeMarker.
 *
 * @author Igor Sunderhus e Silva (<a href="https://github.com/igorssilva">Github page</a>)
 */
public class FreeMarkerEngine {
    private final Configuration cfg;

    private final String outputDirectory;

    public FreeMarkerEngine(final String templatePath, final String outputDirectory) {
        cfg = new Configuration(Configuration.VERSION_2_3_31);


        this.outputDirectory = outputDirectory;
        cfg.clearTemplateCache();
        // Set the template loader to load templates from the classpath
        ClassTemplateLoader templateLoader1 = new ClassTemplateLoader(this.getClass(), templatePath);
        FileTemplateLoader templateLoader2 = null;
        try {
            // Set the template loader to load templates from the file system
            templateLoader2 = new FileTemplateLoader(new File(templatePath));
        } catch (IOException e) {
            // If the file system template loader fails, log the error and continue.
            // This is not a fatal error, as the classpath template loader should still work.
            Logger.log(Level.SEVERE, "An error occurred while loading the template.", e);
        }
        TemplateLoader[] loaders = { templateLoader1, templateLoader2 };
        MultiTemplateLoader multiTemplateLoader = new MultiTemplateLoader(loaders);
        cfg.setTemplateLoader(multiTemplateLoader);

    }

    public void generateCode(final FileTypes templateOption, final Map<String, Object> dataModel)
            throws IOException, TemplateException {
        Template template = this.cfg.getTemplate(templateOption.getTemplate());

        try {
            // Define the file path
            final String pathString = String.format("%s/%s/%s%s", outputDirectory, dataModel.get("path"),
                    ((ClassModel) dataModel.get("class")).getName(), templateOption.getExtension());
            Path path = Paths.get(pathString);

            // Create the necessary directories if they don't already exist
            Files.createDirectories(path.getParent());

            // Write the data model to the file described by the template
            FileWriter writer = new FileWriter(pathString);
            template.process(dataModel, writer);
            writer.flush();
            writer.close();
            Logger.log(Level.FINE, "Code generated successfully");
        } catch (IOException | TemplateException e) {
            Logger.log(Level.SEVERE, "An error occurred while writing to file.");
            e.printStackTrace();
            throw e;
        }
    }

}
