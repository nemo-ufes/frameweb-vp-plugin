package br.ufes.inf.nemo.vpzy.managers;

import br.ufes.inf.nemo.vpzy.engine.models.base.TemplateOption;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import br.ufes.inf.nemo.vpzy.utils.ApplicationManagerUtils;
import br.ufes.inf.nemo.vpzy.utils.FileUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
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



    /**
     * Loads the template options from the workspace. Each template option is stored in a separate folder with its name, and the configuration is stored in a config.json file inside that folder. If the templates folder does not exist, it will be created and populated with the default templates from the plugin resources.
     */
    public void load() {
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
        for (File dir : Objects.requireNonNull(directories)) {
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
     * Gets the template option by its name.
     * @return the template option for the given key, or null if the key does not exist.
     */
    public TemplateOption getProperty(String key) {
        return options.get(key);
    }

    /**
     * Gets all the template options. The key of the map is the name of the template, and the value is the template option.
     * @return a map containing all the template options, where the key is the name of the template and the value is the template option.
     */
    public Map<String, TemplateOption> getOptions() {
        return options;
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
