package br.ufes.inf.nemo.vpzy.engine;

import br.ufes.inf.nemo.vpzy.logging.Logger;
import freemarker.cache.FileTemplateLoader;
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

public class FreeMarkerEngine {
    private final Configuration cfg;

    private String outputDirectory = "/src/main/java";

    public FreeMarkerEngine(final String templatePath, final String outputDirectory) {
        cfg = new Configuration(Configuration.VERSION_2_3_31);

        // Set the template loader to load templates from the "templates" folder
        File templateDir = new File(templatePath);

        this.outputDirectory = outputDirectory + this.outputDirectory;
        try {
            cfg.clearTemplateCache();
            cfg.setTemplateLoader(new FileTemplateLoader(templateDir));
        } catch (IOException e) {
            // Handle exception
        }
    }

    public void generateCode(final String templateName, final Map<String, Object> dataModel) throws IOException {
        Template template = this.cfg.getTemplate(templateName);

        try {

            // Define the file path
            final String pathString = String.format("%s/%s/%s.java", outputDirectory, dataModel.get("path"),
                    ((ClassModel) dataModel.get("class")).getName());
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
        }
    }

}
