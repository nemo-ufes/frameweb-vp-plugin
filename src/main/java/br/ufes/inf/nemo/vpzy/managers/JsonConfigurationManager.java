package br.ufes.inf.nemo.vpzy.managers;

import br.ufes.inf.nemo.vpzy.TemplateValidationException;
import br.ufes.inf.nemo.vpzy.engine.FreeMarkerEngine;
import br.ufes.inf.nemo.vpzy.engine.models.base.TemplateOption;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import br.ufes.inf.nemo.vpzy.utils.ApplicationManagerUtils;
import br.ufes.inf.nemo.vpzy.utils.FileUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.logging.Level;
import java.util.zip.ZipInputStream;

public class JsonConfigurationManager {

    public static final String TEMPLATE_FOLDER = "templates";
    public static final String CONFIG_FILE_NAME = "config.json";

    private final ObjectMapper mapper = new ObjectMapper();

    private final Map<String, TemplateOption> options = new HashMap<>();

    private final String pluginName;
    private final File templateFolder;

    public JsonConfigurationManager(String pluginName) {
        this.pluginName = pluginName;

        com.vp.plugin.ApplicationManager app = com.vp.plugin.ApplicationManager.instance();
        com.vp.plugin.VPPluginInfo info = app.getPluginInfo("br.ufes.inf.nemo.frameweb.vp");

        if (info != null) {
            this.templateFolder = new File(info.getPluginDir(), TEMPLATE_FOLDER);
        } else {
            this.templateFolder = new File(ApplicationManagerUtils.getWorkspaceLocation(), TEMPLATE_FOLDER);
        }

        load();
    }

    public Set<String> getTemplateNames() {
        return options.keySet();
    }

    /**
     * Loads the template options from the workspace. Each template option is stored in a separate folder with its name, and the configuration is stored in a config.json file inside that folder. If the templates folder does not exist, it will be created and populated with the default templates from the plugin resources.
     */
    private void load() {
        Logger.log(Level.FINER, "Loading {0} configurations from {1}. Folder exists? {2}", new Object[]{pluginName, templateFolder.getAbsolutePath(),templateFolder.exists()});
        if (!templateFolder.exists()) {
            try{
                importDefautTemplates();
            } catch (IOException e){
                Logger.log(Level.FINER, "Error loading configurations from {0}", templateFolder.getAbsolutePath());
                throw new RuntimeException("Error loading configurations from " + templateFolder.getAbsolutePath());
            }

        }
        File [] directories = templateFolder.listFiles(File::isDirectory);
        for (File dir : directories) {
            File configFile = new File(dir, CONFIG_FILE_NAME);

            if (configFile.exists()) {
                try(final InputStream inputStream = new FileInputStream(configFile)){
                    TemplateOption templateOption = mapper.readValue(inputStream, TemplateOption.class);
                    templateOption.validate();

                    // saving in the map using the name of template as key
                    options.put(templateOption.getName(), templateOption);
                    Logger.log(Level.FINEST, "Loaded configuration from {0}", dir.getName());
                } catch (Exception e){
                    Logger.log(Level.SEVERE, "Cannot read {0}. INVALID JSON format", configFile.getAbsolutePath());
                }
            }
        }
    }


    /**
     * Imports the default templates from the plugin resources to the workspace. This is done by extracting a zip file containing the default templates. If the templates folder already exists, it will not be overwritten.
     */
    private void importDefautTemplates() throws IOException {
        if (!this.templateFolder.exists()) {
            final boolean sucess = templateFolder.mkdir();
            if (!sucess) {
                Logger.log(Level.FINER, "Create templates folder at {0}", templateFolder.getAbsolutePath());
            } else {
                Logger.log(Level.SEVERE, "Could not create templates folder at {0}", templateFolder.getAbsolutePath());
                return;
            }
        }

        final InputStream pmdFolder = getClass().getClassLoader().getResourceAsStream(TEMPLATE_FOLDER + ".zip");

        if (pmdFolder != null){
            try (final ZipInputStream zipInputStream = new ZipInputStream(pmdFolder)){
                FileUtils.extract(zipInputStream, templateFolder);
            }
        }
        // after extract the folder call the load function
        load();
    }

    /**
     * Saves the template options to the workspace. Each template option is saved in a separate folder with its name, and the configuration is saved in a config.json file inside that folder.
     */
    public void save() {
        Logger.log(Level.FINER, "Saving {0} configurations from {1}", new Object[]{pluginName, templateFolder.getAbsolutePath()});

        File dir = null;
        for (TemplateOption option : options.values()) {
            dir = new File(templateFolder, option.getName());
            if (!dir.exists()) {
                dir.mkdir();
            }
            File configFile = new File(dir, CONFIG_FILE_NAME);

            try (final Writer outputStream = new FileWriter(configFile)){
                mapper.writerWithDefaultPrettyPrinter().writeValue(outputStream, option);
                Logger.log(Level.FINEST, "Saved config for {0}.", option.getName());
            }catch (IOException e){
                Logger.log(Level.SEVERE, "Cannot save config.json for {0}.", option.getName());
            }
        }
    }

    /**
     * Gets the template option by its name.
     * @return the template option for the given key, or null if the key does not exist.
     */
    public TemplateOption getProperty(String key) {
        return options.get(key);
    }

    /**
     * Sets the template option for a given key. If the key already exists, it will be overwritten.
     * @param key
     * @param option
     */
    public void setProperty(String key, TemplateOption option) {
        options.put(key, option);
    }

    /**
     * Gets all the template options. The key of the map is the name of the template, and the value is the template option.
     * @return a map containing all the template options, where the key is the name of the template and the value is the template option.
     */
    public Map<String, TemplateOption> getOptions() {
        return options;
    }

    /**
     * Imports a template folder from the given template option. The template option must be valid and contain all the necessary information to locate the templates. The templates will be copied to the workspace templates folder, and the configuration will be saved in a config.json file inside a folder with the name of the template option.
     * @param templateOption
     * @throws IOException
     * @throws TemplateValidationException
     */
    public void importTemplateFolder(final TemplateOption templateOption) throws IOException, TemplateValidationException {
        List<String> validationErrors = new ArrayList<>();

        if (templateOption == null) {
            throw new TemplateValidationException("Template option cannot be null.", null);
        }

        templateOption.validate();

        final File sourceTemplates = new File(templateOption.getTemplatePath());
        final String sourceTemplatesAbsolutePath = sourceTemplates.getAbsolutePath();

        if (!sourceTemplates.exists()) {
            validationErrors.add("Could not find templates folder at " + sourceTemplatesAbsolutePath);
        }

        if (templateOption.getEntity().invalidTemplate(sourceTemplatesAbsolutePath)) {
            validationErrors.add("Could not find entity template");
        }

        if (templateOption.getEnumeration().invalidTemplate(sourceTemplatesAbsolutePath)) {
            validationErrors.add("Could not find enumeration template");
        }

        if (templateOption.getMappedSuperclass().invalidTemplate(sourceTemplatesAbsolutePath)) {
            validationErrors.add("Could not find mapped superclass template");
        }

        if (templateOption.getTransientClass().invalidTemplate(sourceTemplatesAbsolutePath)) {
            validationErrors.add("Could not find transient class template");
        }

        if (templateOption.getEmbeddable().invalidTemplate(sourceTemplatesAbsolutePath)) {
            validationErrors.add("Could not find embeddable template");
        }

        if (templateOption.getDao().invalidTemplate(sourceTemplatesAbsolutePath)) {
            validationErrors.add("Could not find dao template");
        }

        if (templateOption.getService().invalidTemplate(sourceTemplatesAbsolutePath)) {
            validationErrors.add("Could not find service template");
        }

        if (templateOption.getController().invalidTemplate(sourceTemplatesAbsolutePath)) {
            validationErrors.add("Could not find controller template");
        }

        if (!FreeMarkerEngine.validateTemplateStructures(sourceTemplates)) {
            validationErrors.add("Invalid template syntax structure. Check the logs for details.");
        }

        if (!validationErrors.isEmpty()) {
            for (String error : validationErrors) {
                Logger.log(Level.SEVERE, error);
            }
            throw new TemplateValidationException("One or more template validation errors occurred.", null);
        }

        final String name = templateOption.getName();
        final Path resolve = templateFolder.toPath().resolve(name);

        try {
            FileUtils.copyFolder(sourceTemplates.toPath(), resolve, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            Logger.log(Level.SEVERE, "Could not copy templates folder to " + resolve);
            throw new TemplateValidationException("Error while copying templates folder.", e);
        }
    }

    /**
     * Gets the template folder located in the workspace where template configurations are stored.
     *
     * @return a File representing the templates directory in the workspace
     */
    public File getTemplateFolder() {
        return templateFolder;
    }

}
