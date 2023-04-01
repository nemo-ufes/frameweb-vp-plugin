package br.ufes.inf.nemo.vpzy.engine;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FreeMarkerEngine {


    private final Configuration cfg;

    public FreeMarkerEngine(final String templatePath) {
        cfg = new Configuration(Configuration.VERSION_2_3_31);

        // Set the template loader to load templates from the "templates" folder
        // Set the template loader to load templates from the "templates" folder
        File templateDir = new File(templatePath);
        try {
            cfg.setTemplateLoader(new FileTemplateLoader(templateDir));
        } catch (IOException e) {
            // Handle exception
        }
    }

    public String getCode(final String templateName) throws IOException, TemplateException {
        Map<String, Object> data_Model = new HashMap<>();
        data_Model.put("package", new PackageModel("org.example"));
        data_Model.put("class", new ClassModel("Test"));

        data_Model.put("attributes", Arrays.asList(
                new AttributeModel("name", new TypeModel("String"), new Visibility("private"), false, 0, null),
                new AttributeModel("age", new TypeModel("Integer"), new Visibility("private"), false, 0, null),
                new AttributeModel("birthDate", new TypeModel("Date"), new Visibility("private"), false, 0, "DATE"),
                new AttributeModel("birthTime", new TypeModel("Date"), new Visibility("private"), false, 0, "TIME"),
                new AttributeModel("birthDateTime", new TypeModel("Date"), new Visibility("private"), false, 0, "TIMESTAMP"),
                new AttributeModel("email", new TypeModel("String"), new Visibility("private"), false, 255, null),
                new AttributeModel("address", new TypeModel("String"), new Visibility("private"), true, 255, null)
        ));


        Template template = this.cfg.getTemplate(templateName);
        Writer out = new StringWriter();
        template.process(data_Model, out);
        String javaCode = out.toString();
        System.out.println(javaCode);
        return javaCode;
    }


}
