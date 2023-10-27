package br.ufes.inf.nemo.vpzy.managers;

import br.ufes.inf.nemo.vpzy.TemplateValidationException;
import br.ufes.inf.nemo.vpzy.engine.FreeMarkerEngine;
import br.ufes.inf.nemo.vpzy.engine.models.base.TemplateOption;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import br.ufes.inf.nemo.vpzy.utils.ApplicationManagerUtils;
import br.ufes.inf.nemo.vpzy.utils.FileUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.zip.ZipInputStream;

/**
 * Holds and validates all the properties that a template option must have.
 *
 * @author <a href="https://github.com/igorssilva">Igor Sunderhus e Silva</a>
 */
public class YamlConfigurationManager {
    public static final String TEMPLATE_FOLDER = "templates";

    /** The contents of the configuration. */
    private final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    private final Map<String, TemplateOption> options = new HashMap<>();

    /** Name of the plug-in. */
    private final String pluginName;

    /** Name of the configuration file. */
    private final String configFileName;

    /** The file in which to save the configuration. */
    private final File configFile;

    private final File templateFolder;

    /** Constructor loads the configuration items from the config file. */
    public YamlConfigurationManager(final String pluginName, final String configFileName) {
        this.pluginName = pluginName;
        this.configFileName = configFileName;

        // Locates the config file in Visual Paradigm's workspace and loads it.
        final File workspace = ApplicationManagerUtils.getWorkspaceLocation();
        configFile = new File(workspace, configFileName);
        templateFolder = new File(workspace, TEMPLATE_FOLDER);

        load();
    }

    /**
     * Loads the configuration items from a properties file (see {@code TEMPLATE_CONFIG_FILE_NAME}) in Visual Paradigm's
     * workspace directory. If not found, loads from the classpath the default configurations and saves to the workspace
     * for next time. This method is called during the construction of a YamlConfigurationManager.
     */
    protected void load() {
        Logger.log(Level.FINER, "Loading {0} configuration from {1}. File exists? {2}" ,
                new Object[] { pluginName, configFile.getAbsolutePath(), configFile.exists() });

        // If there's already a config file, loads it.
        if (configFile.exists()) {

            try (final InputStream inputStream = new FileInputStream(configFile)) {

                loadProperties(inputStream);
                return;
            } catch (IOException e) {
                Logger.log(Level.SEVERE, "Cannot read {0}. Will try to use the default {1} configurations instead." ,
                        new Object[] { configFile.getAbsolutePath(), pluginName });
            }
        }

        // If the config file doesn't exist, creates one based on default values and saves.

        try {
            importDefaultTemplates();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads the configuration items from a properties file (see {@code TEMPLATE_CONFIG_FILE_NAME}) in Visual
     * Paradigm's
     *
     * @param inputStream The input stream from which to read the properties.
     */
    private void loadProperties(final InputStream inputStream) throws IOException {
        TypeFactory typeFactory = mapper.getTypeFactory();
        MapType mapType = typeFactory.constructMapType(HashMap.class, String.class, TemplateOption.class);
        final Map<String, TemplateOption> data = mapper.readValue(inputStream, mapType);

        for (Map.Entry<String, TemplateOption> entry : data.entrySet()) {

            final String key = entry.getKey();

            final TemplateOption templateOption = entry.getValue();
            templateOption.validate();

            options.put(key, templateOption);
        }

        Logger.log(Level.FINEST, "Configuration successfully loaded for plug-in {0}, saved to {1}." ,
                new Object[] { pluginName, configFile.getAbsolutePath() });

    }

    /**
     * Imports the default templates from the classpath.
     */
    private void importDefaultTemplates() throws IOException {

        if (!this.templateFolder.exists()) {
            final boolean success = templateFolder.mkdir();
            if (success) {
                Logger.log(Level.FINEST, "Created templates folder at {0}." , templateFolder.getAbsolutePath());

            } else {
                Logger.log(Level.SEVERE, "Could not create templates folder at {0}." ,
                        templateFolder.getAbsolutePath());
            }
        }

        final InputStream pmdFolder = getClass().getClassLoader().getResourceAsStream(TEMPLATE_FOLDER + ".zip" );

        if (pmdFolder != null) {
            try (final ZipInputStream zipInputStream = new ZipInputStream(pmdFolder)) {
                // Extract the zip contents and keep in temp directory
                FileUtils.extract(zipInputStream, templateFolder);
            }
        }

        Logger.log(Level.FINE, "Loading {0} configuration from classpath location {1} and saving to {2}" ,
                new Object[] { pluginName, configFileName, configFile.getAbsolutePath() });
        try (final InputStream inputStream = getClass().getClassLoader().getResourceAsStream(configFileName)) {

            loadProperties(inputStream);

            save();
        } catch (IOException e) {
            Logger.log(Level.SEVERE,
                    "Cannot read {0} from classpath. Plug-in will not remember configuration changes." ,
                    configFileName);
        }
    }

    /**
     * Saves the current configuration to a properties file (see {@code TEMPLATE_CONFIG_FILE_NAME}) in Visual Paradigm's
     * workspace directory, allowing users to persist configuration changes.
     */
    public void save() {
        Logger.log(Level.FINER, "Saving {0} configuration to {1}. File exists? {2}" ,
                new Object[] { pluginName, configFile.getAbsolutePath(), configFile.exists() });

        // Stores the properties in the file.
        try (final FileWriter outputStream = new FileWriter(configFile)) {
            mapper.writeValue(outputStream, options);
            Logger.log(Level.FINEST, "Configuration successfully saved for plug-in {0}." , pluginName);
        } catch (IOException e) {
            Logger.log(Level.SEVERE,
                    "Cannot save {0} configurations. Plug-in will not remember configuration changes." , pluginName);
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the value for a configuration item given its key.
     *
     * @param key The given key.
     * @return The value of the configuration item, or {@code null} if no configuration item with the given key is
     * found.
     */
    public TemplateOption getProperty(String key) {
        final TemplateOption value = options.get(key);
        Logger.log(Level.FINEST, "Retrieving configuration for key {0}: {1}." , new Object[] { key, value });
        return value;
    }

    /**
     * Sets the value for a configuration item given its key and the new value to set.
     *
     * @param key   The given key.
     * @param value The new value to set.
     */
    public void setProperty(String key, TemplateOption value) {
        Logger.log(Level.FINEST, "Changing configuration for key {0}: {1}." , new Object[] { key, value });
        options.put(key, value);
    }

    public Map<String, TemplateOption> getOptions() {
        return options;
    }

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




    public File getTemplateFolder() {
        return templateFolder;
    }
}
