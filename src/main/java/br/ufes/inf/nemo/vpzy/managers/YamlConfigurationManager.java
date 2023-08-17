package br.ufes.inf.nemo.vpzy.managers;

import br.ufes.inf.nemo.vpzy.logging.Logger;
import br.ufes.inf.nemo.vpzy.utils.ApplicationManagerUtils;
import br.ufes.inf.nemo.vpzy.utils.ReflectionUtil;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * Holds and validates all the properties that a template option must have.
 *
 * @author Igor Sunderhus e Silva (<a href="https://github.com/igorssilva">Github page</a>)
 */
public class YamlConfigurationManager<T> {
    /** The contents of the configuration. */
    private final Yaml yaml;

    private final Map<String, T> options = new HashMap<>();

    /** Name of the plug-in. */
    private final String pluginName;

    /** Name of the configuration file. */
    private final String configFileName;

    /** The file in which to save the configuration. */
    private final File configFile;

    /** Constructor loads the configuration items from the config file. */
    public YamlConfigurationManager(String pluginName, String configFileName) {
        this.pluginName = pluginName;
        this.configFileName = configFileName;

        // Locates the config file in Visual Paradigm's workspace and loads it.
        final File workspace = ApplicationManagerUtils.getWorkspaceLocation();
        configFile = new File(workspace, configFileName);

        final Class<?> domainClass = ReflectionUtil.determineTypeArgument(this.getClass());
        this.yaml = new Yaml(new Constructor(new TypeDescription(domainClass)));
        load();
    }

    /**
     * Loads the configuration items from a properties file (see {@code CONFIG_FILE_NAME}) in Visual Paradigm's
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
        Logger.log(Level.FINE, "Loading {0} configuration from classpath location {1} and saving to {2}",
                new Object[] { pluginName, configFileName, configFile.getAbsolutePath() });
        try (final InputStream inputStream = ConfigurationManager.class.getClassLoader()
                .getResourceAsStream(configFileName)) {

            loadProperties(inputStream);

            save();
        } catch (IOException e) {
            Logger.log(Level.SEVERE, "Cannot read {0} from classpath. Plug-in will not remember configuration changes.",
                    configFileName);
        }
    }

    private void loadProperties(final InputStream inputStream) {
        final Props data = yaml.loadAs(inputStream, Props.class);

        if (data != null) {
            options.clear();
            options.putAll(data.map);
        }
        Logger.log(Level.FINEST, "Configuration successfully loaded for plug-in {0}, saved to {1}.",
                new Object[] { pluginName, configFile.getAbsolutePath() });
    }

    /**
     * Saves the current configuration to a properties file (see {@code CONFIG_FILE_NAME}) in Visual Paradigm's
     * workspace directory, allowing users to persist configuration changes.
     */
    public void save() {
        Logger.log(Level.FINER, "Saving {0} configuration to {1}. File exists? {2}",
                new Object[] { pluginName, configFile.getAbsolutePath(), configFile.exists() });

        // Stores the properties in the file.
        try (final FileWriter outputStream = new FileWriter(configFile)) {

            final Props data = new Props();
            data.map = options;
            yaml.dump(data, outputStream);
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
    public T getProperty(String key) {
        final T value = options.get(key);
        Logger.log(Level.FINEST, "Retrieving configuration for key {0}: {1}.", new Object[] { key, value });
        return value;
    }

    /**
     * Sets the value for a configuration item given its key and the new value to set.
     *
     * @param key   The given key.
     * @param value The new value to set.
     */
    public void setProperty(String key, T value) {
        Logger.log(Level.FINEST, "Changing configuration for key {0}: {1}.", new Object[] { key, value });
        options.put(key, value);
    }

    private class Props {
        Map<String, T> map;
    }

}
