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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class FreeMarkerEngine {
    private final Configuration cfg;

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

    public void getCode(final String templateName, final String outputDirectory) throws IOException {

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

}
