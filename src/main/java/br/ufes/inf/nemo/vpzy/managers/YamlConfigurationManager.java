package br.ufes.inf.nemo.vpzy.managers;

import br.ufes.inf.nemo.vpzy.engine.models.base.FileTypes;
import br.ufes.inf.nemo.vpzy.engine.models.base.TemplateOption;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import br.ufes.inf.nemo.vpzy.utils.ApplicationManagerUtils;
import br.ufes.inf.nemo.vpzy.utils.FileUtils;
import br.ufes.inf.nemo.vpzy.utils.ViewManagerUtils;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.zip.ZipInputStream;

/**
 * Holds and validates all the properties that a template option must have.
 *
 * @author Igor Sunderhus e Silva (<a href="https://github.com/igorssilva">Github page</a>)
 */
public class YamlConfigurationManager {
    public static final String TEMPLATE_FOLDER = "templates";

    public static final String ERROR = "Error";

    /** The contents of the configuration. */
    private final Yaml yaml;

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

        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        Representer representer = new Representer();
        representer.addClassTag(TemplateOption.class, Tag.MAP);

        this.yaml = new Yaml(representer, dumperOptions);
        load();
    }

    /**
     * Loads the configuration items from a properties file (see {@code TEMPLATE_CONFIG_FILE_NAME}) in Visual Paradigm's
     * workspace directory. If not found, loads from the classpath the default configurations and saves to the workspace
     * for next time. This method is called during the construction of a YamlConfigurationManager.
     */
    protected void load() {
        Logger.log(Level.FINER, "Loading {0} configuration from {1}. File exists? {2}",
                new Object[] { pluginName, configFile.getAbsolutePath(), configFile.exists() });

        // If there's already a config file, loads it.
        if (configFile.exists()) {

            try (final InputStream inputStream = new FileInputStream(configFile)) {

                loadProperties(inputStream);
                return;
            } catch (IOException e) {
                Logger.log(Level.SEVERE, "Cannot read {0}. Will try to use the default {1} configurations instead.",
                        new Object[] { configFile.getAbsolutePath(), pluginName });
            }
        }

        // If the config file doesn't exist, creates one based on default values and saves.

        try {
            importDefaultTemplates();
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads the configuration items from a properties file (see {@code TEMPLATE_CONFIG_FILE_NAME}) in Visual Paradigm's
     *
     * @param inputStream The input stream from which to read the properties.
     */
    private void loadProperties(final InputStream inputStream) {

        final Map<String, Object> data2 = (Map<String, Object>) yaml.load(inputStream);

        for (Map.Entry<String, Object> entry : data2.entrySet()) {

            final String key = entry.getKey();

            final Map<String, Object> templateInfo = (Map<String, Object>) entry.getValue();

            final String templatePath = (String) templateInfo.get("templatePath");
            final String outputPath = (String) templateInfo.get("outputPath");
            final String description = (String) templateInfo.get("description");

            final FileTypes entity = extractFileType(templateInfo.get("entity"));

            final FileTypes enumeration = extractFileType(templateInfo.get("enumeration"));

            final FileTypes mappedSuperclass = extractFileType(templateInfo.get("mappedSuperclass"));

            final FileTypes transientClass = extractFileType(templateInfo.get("transientClass"));

            final FileTypes embeddable = extractFileType(templateInfo.get("embeddable"));

            final FileTypes dao = extractFileType(templateInfo.get("dao"));

            final FileTypes service = extractFileType(templateInfo.get("service"));

            final FileTypes controller = extractFileType(templateInfo.get("controller"));

            final TemplateOption templateOption = new TemplateOption(key, description, templatePath, outputPath, entity,
                    enumeration, mappedSuperclass, transientClass, embeddable, dao, service, controller);

            templateOption.validate();

            options.put(key, templateOption);
        }

        Logger.log(Level.FINEST, "Configuration successfully loaded for plug-in {0}, saved to {1}.",
                new Object[] { pluginName, configFile.getAbsolutePath() });

    }

    /**
     * Imports the default templates from the classpath.
     */
    private void importDefaultTemplates() throws IOException, URISyntaxException {

        if (!this.templateFolder.exists()) {
            final boolean success = templateFolder.mkdir();
            if (success) {
                Logger.log(Level.FINEST, "Created templates folder at {0}.", templateFolder.getAbsolutePath());

            } else {
                Logger.log(Level.SEVERE, "Could not create templates folder at {0}.", templateFolder.getAbsolutePath());
            }
        }

        final InputStream pmdFolder = getClass().getClassLoader().getResourceAsStream(TEMPLATE_FOLDER + ".zip");

        if (pmdFolder != null) {
            try (final ZipInputStream zipInputStream = new ZipInputStream(pmdFolder)) {
                // Extract the zip contents and keep in temp directory
                FileUtils.extract(zipInputStream, templateFolder);
            }
        }

        Logger.log(Level.FINE, "Loading {0} configuration from classpath location {1} and saving to {2}",
                new Object[] { pluginName, configFileName, configFile.getAbsolutePath() });
        try (final InputStream inputStream = YamlConfigurationManager.class.getClassLoader()
                .getResourceAsStream(configFileName)) {

            loadProperties(inputStream);

            save();
        } catch (IOException e) {
            Logger.log(Level.SEVERE, "Cannot read {0} from classpath. Plug-in will not remember configuration changes.",
                    configFileName);
        }
    }



    @SuppressWarnings("unchecked")
    private FileTypes extractFileType(final Object fileType) {
        final Map<String, Object> entity = (Map<String, Object>) fileType;
        final String entityTemplate = (String) entity.get("template");
        final String entityExtension = (String) entity.get("extension");

        return new FileTypes(entityTemplate, entityExtension);
    }



    /**
     * Saves the current configuration to a properties file (see {@code TEMPLATE_CONFIG_FILE_NAME}) in Visual Paradigm's
     * workspace directory, allowing users to persist configuration changes.
     */
    public void save() {
        Logger.log(Level.FINER, "Saving {0} configuration to {1}. File exists? {2}",
                new Object[] { pluginName, configFile.getAbsolutePath(), configFile.exists() });

        // Stores the properties in the file.
        try (final FileWriter outputStream = new FileWriter(configFile)) {

            yaml.dump(options, outputStream);
            Logger.log(Level.FINEST, "Configuration successfully saved for plug-in {0}.", pluginName);
        } catch (IOException e) {
            Logger.log(Level.SEVERE, "Cannot save {0} configurations. Plug-in will not remember configuration changes.",
                    pluginName);
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
        Logger.log(Level.FINEST, "Retrieving configuration for key {0}: {1}.", new Object[] { key, value });
        return value;
    }

    /**
     * Sets the value for a configuration item given its key and the new value to set.
     *
     * @param key   The given key.
     * @param value The new value to set.
     */
    public void setProperty(String key, TemplateOption value) {
        Logger.log(Level.FINEST, "Changing configuration for key {0}: {1}.", new Object[] { key, value });
        options.put(key, value);
    }

    public Map<String, TemplateOption> getOptions() {
        return options;
    }

    public void importTemplateFolder(final TemplateOption templateOption) throws IOException {

        if (templateOption == null) {

            ViewManagerUtils.showMessageDialog("Template Option not passed", ERROR, ViewManagerUtils.ERROR_MESSAGE);
            return;
        }

        templateOption.validate();

        final File sourceTemplates = new File(templateOption.getTemplatePath());

        final String sourceTemplatesAbsolutePath = sourceTemplates.getAbsolutePath();
        if (!sourceTemplates.exists()) {
            Logger.log(Level.SEVERE, "Could not find templates folder at {0}.", sourceTemplatesAbsolutePath);
            ViewManagerUtils.showMessageDialog("Could not find templates folder", ERROR,
                    ViewManagerUtils.ERROR_MESSAGE);
            return;
        }

        if (templateOption.getEntity().invalidTemplate(sourceTemplatesAbsolutePath)) {
            ViewManagerUtils.showMessageDialog("Could not find entity template", ERROR,
                    ViewManagerUtils.ERROR_MESSAGE);
            return;
        }

        if (templateOption.getEnumeration().invalidTemplate(sourceTemplatesAbsolutePath)) {
            ViewManagerUtils.showMessageDialog("Could not find enumeration template", ERROR,
                    ViewManagerUtils.ERROR_MESSAGE);
            return;
        }

        if (templateOption.getMappedSuperclass().invalidTemplate(sourceTemplatesAbsolutePath)) {
            ViewManagerUtils.showMessageDialog("Could not find mapped superclass template", ERROR,
                    ViewManagerUtils.ERROR_MESSAGE);
            return;
        }

        if (templateOption.getTransientClass().invalidTemplate(sourceTemplatesAbsolutePath)) {
            ViewManagerUtils.showMessageDialog("Could not find transient class template", ERROR,
                    ViewManagerUtils.ERROR_MESSAGE);
            return;
        }

        if (templateOption.getEmbeddable().invalidTemplate(sourceTemplatesAbsolutePath)) {
            ViewManagerUtils.showMessageDialog("Could not find embeddable template", ERROR,
                    ViewManagerUtils.ERROR_MESSAGE);
            return;
        }

        if (templateOption.getDao().invalidTemplate(sourceTemplatesAbsolutePath)) {
            ViewManagerUtils.showMessageDialog("Could not find dao template", ERROR, ViewManagerUtils.ERROR_MESSAGE);
            return;
        }

        if (templateOption.getService().invalidTemplate(sourceTemplatesAbsolutePath)) {
            ViewManagerUtils.showMessageDialog("Could not find service template", ERROR,
                    ViewManagerUtils.ERROR_MESSAGE);
            return;
        }

        if (templateOption.getController().invalidTemplate(sourceTemplatesAbsolutePath)) {
            ViewManagerUtils.showMessageDialog("Could not find controller template", ERROR,
                    ViewManagerUtils.ERROR_MESSAGE);
            return;
        }

        final String name = templateOption.getName();
        final Path resolve = templateFolder.toPath().resolve(name);
        FileUtils.copyFolder(sourceTemplates.toPath(), resolve, StandardCopyOption.REPLACE_EXISTING);

    }

    public File getTemplateFolder() {
        return templateFolder;
    }
}
