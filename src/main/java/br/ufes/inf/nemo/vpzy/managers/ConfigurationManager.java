package br.ufes.inf.nemo.vpzy.managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import br.ufes.inf.nemo.vpzy.utils.ApplicationManagerUtils;

/**
 * Utility class to manage the configuration of Visual Paradigm plug-ins.
 * 
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
 */
public class ConfigurationManager {
  /** The contents of the configuration. */
  private Properties properties = new Properties();

  /** Name of the plug-in. */
  private String pluginName;

  /** Name of the configuration file. */
  private String configFileName;

  /** The file in which to save the configuration. */
  private File configFile;

  /** Constructor loads the configuration items from the config file. */
  public ConfigurationManager(String pluginName, String configFileName) {
    this.pluginName = pluginName;
    this.configFileName = configFileName;

    // Locate the config file in Visual Paradigm's workspace and loads it.
    File workspace = ApplicationManagerUtils.getWorkspaceLocation();
    configFile = new File(workspace, configFileName);
    load();
  }

  /**
   * Loads the configuration items from a properties file (see {@code CONFIG_FILE_NAME}) in Visual
   * Paradigm's workspace directory. If not found, loads from the classpath the default
   * configurations and saves to the workspace for next time.
   * 
   * This method is called during the construction of a ConfigurationManager.
   */
  protected void load() {
    Logger.log(Level.FINE, "Loading {0} configuration from {1}. File exists? {2}",
        new Object[] {pluginName, configFile.getAbsolutePath(), configFile.exists()});

    // If there's already a config file, load it.
    if (configFile.exists()) {
      try {
        properties.load(new FileInputStream(configFile));
        return;
      } catch (IOException e) {
        Logger.log(Level.SEVERE,
            "Cannot read {0}. Will try to use the default {1} configurations instead.",
            new Object[] {configFile.getAbsolutePath(), pluginName});
      }
    }

    // If the config file doesn't exist, create one based on default values and save.
    Logger.log(Level.FINE, "Will load configuration from classpath location {0} and save to {1}",
        new Object[] {configFileName, configFile.getAbsolutePath()});
    InputStream input =
        ConfigurationManager.class.getClassLoader().getResourceAsStream(configFileName);
    try {
      properties.load(input);
      save();
    } catch (IOException e) {
      Logger.log(Level.SEVERE,
          "Cannot read default configurations. Plug-in {0} will not function properly.",
          pluginName);
    }

    Logger.log(Level.FINER, "Configuration successfully loaded for plug-in {0}.", pluginName);
  }

  /**
   * Saves the current configuration to a properties file (see {@code CONFIG_FILE_NAME}) in Visual
   * Paradigm's workspace directory, allowing users to persist configuration changes.
   */
  public void save() {
    Logger.log(Level.FINE, "Saving {0} configuration to {1}. File exists? {2}",
        new Object[] {pluginName, configFile.getAbsolutePath(), configFile.exists()});

    // Stores the properties in the file.
    try {
      OutputStream output = new FileOutputStream(configFile);
      properties.store(output, pluginName + " Configuration");
    } catch (IOException e) {
      Logger.log(Level.SEVERE,
          "Cannot save {0} configurations. Plug-in will not remember configuration changes.",
          pluginName);
    }

    Logger.log(Level.FINER, "Configuration successfully saved for plug-in {0}.", pluginName);
  }

  /**
   * Get the value for a configuration item given its key.
   * 
   * @param key The given key.
   * @return The value of the configuration item, or {@code null} if no configuration item with the
   *         given key is found.
   */
  public String getProperty(String key) {
    return properties.getProperty(key);
  }

  /**
   * Set the value for a configuration item given its key and the new value to set.
   * 
   * @param key The given key.
   * @param value The new value to set.
   */
  public void setProperty(String key, String value) {
    properties.setProperty(key, value);
  }
}
