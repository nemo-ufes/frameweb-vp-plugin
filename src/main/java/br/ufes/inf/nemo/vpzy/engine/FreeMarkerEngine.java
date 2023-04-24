package br.ufes.inf.nemo.vpzy.engine;

import br.ufes.inf.nemo.vpzy.logging.Logger;
import br.ufes.inf.nemo.vpzy.utils.ModelElementUtils;
import com.vp.plugin.model.IClass;
import com.vp.plugin.model.IModelElement;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class FreeMarkerEngine {
    private final Configuration cfg;

    private String outputDirectory = "/src/main/java";

    public FreeMarkerEngine(final String templatePath) {
        cfg = new Configuration(Configuration.VERSION_2_3_31);

        // Set the template loader to load templates from the "templates" folder
        File templateDir = new File(templatePath);
        try {
            cfg.setTemplateLoader(new FileTemplateLoader(templateDir));
        } catch (IOException e) {
            // Handle exception
        }
    }

    public FreeMarkerEngine(final String templatePath, final String outputDirectory) {
        cfg = new Configuration(Configuration.VERSION_2_3_31);

        // Set the template loader to load templates from the "templates" folder
        File templateDir = new File(templatePath);

        this.outputDirectory = outputDirectory + this.outputDirectory;
        try {
            cfg.setTemplateLoader(new FileTemplateLoader(templateDir));
        } catch (IOException e) {
            // Handle exception
        }
    }

    public void generateCode(final String templateName, final String outputDirectory) throws IOException {

        final IModelElement selectedModelElements = ModelElementUtils.getSelectedModelElements()
                .stream()
                .findFirst()
                .orElseThrow();

        Map<String, Object> dataModel = new HashMap<>();

        final IClass classModel = (IClass) selectedModelElements;

        dataModel.put("package", new PackageModel("org.example"));
        dataModel.put("class", new ClassModel(classModel));

        List<AttributeModel> attributeModels = Arrays.stream(classModel.toAttributeArray())
                .map(AttributeModel::new)
                .collect(Collectors.toList());

        dataModel.put("attributes", attributeModels);

        Template template = this.cfg.getTemplate(templateName);

        try {
            // Write source code to file Test.java in the output directory.

            FileWriter writer = new FileWriter(outputDirectory + "/Test.java");
            template.process(dataModel, writer);
            writer.flush();
            writer.close();
            Logger.log(Level.INFO, "Code generated successfully");
            //ViewManagerUtils.showMessage("Code generated successfully");
        } catch (IOException | TemplateException e) {
            System.out.println("An error occurred while writing to file.");
            e.printStackTrace();
        }
    }

    public void generateCode(final String templateName, final Map<String, Object> dataModel) throws IOException {
        Template template = this.cfg.getTemplate(templateName);

        try {

            // Define the file path
            final String pathString = String.format("%s/%s/%s.java", outputDirectory, dataModel.get("path"),
                    ((IClass) dataModel.get("class")).getName());
            Path path = Paths.get(pathString);

            // Create the necessary directories if they don't already exist
            Files.createDirectories(path.getParent());

            // Write the data model to the file described by the template
            FileWriter writer = new FileWriter(pathString);
            template.process(dataModel, writer);
            writer.flush();
            writer.close();
            Logger.log(Level.INFO, "Code generated successfully");
            //ViewManagerUtils.showMessage("Code generated successfully");
        } catch (IOException | TemplateException e) {
            System.out.println("An error occurred while writing to file.");
            e.printStackTrace();
        }
    }

}
