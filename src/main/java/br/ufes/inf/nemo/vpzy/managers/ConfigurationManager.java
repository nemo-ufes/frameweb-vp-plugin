package br.ufes.inf.nemo.vpzy.managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.util.Properties;
import java.util.logging.Level;
import br.ufes.inf.nemo.frameweb.vp.FrameWebPlugin;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import br.ufes.inf.nemo.vpzy.utils.ApplicationManagerUtils;

/**
 * Utility class to manage the configuration of Visual Paradigm plug-ins.
 * 
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
 */
public class ConfigurationManager {
  /** Name of the configuration file. */
  private static final String CONFIG_FILE_NAME = "frameweb-tools.properties";

  /** The configuration manager for the plug-in. */
  private static ConfigurationManager manager;

  /** The contents of the configuration. */
  private Properties properties = new Properties();

  /** Constructor loads the configuration items from properties file. */
  protected ConfigurationManager() {
    load();
  }

  /**
   * Provides the ConfigurationsManager instance for the plug-in. If none exists, create a new
   * default one and store it for future use.
   * 
   * @return A ConfigurationsManager instance for the plug-in.
   */
  public static ConfigurationManager getInstance() {
    return getInstance(ConfigurationManager.class);
  }

  /**
   * Provides the ConfigurationsManager instancefor the plug-in. If none exists, create a new
   * instance from the given class and store it for future use.
   * 
   * @param clazz The given class, that must be StereotypesManager or one of its subclasses.
   * @return A ConfigurationsManager instance for the plug-in.
   */
  public static <T extends ConfigurationManager> ConfigurationManager getInstance(Class<T> clazz) {
    Logger.log(Level.FINER, "Providing {0} for plug-in {1}",
        new Object[] {clazz.getName(), FrameWebPlugin.PLUGIN_NAME});

    // Checks if the configuration manager for the plug-in has already been created.
    if (manager == null) {
      // Tries to instantiate the class that was specified.
      try {
        Logger.log(Level.FINE, "Creating a new {0} for plug-in {1}",
            new Object[] {clazz.getName(), FrameWebPlugin.PLUGIN_NAME});
        Constructor<T> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        manager = constructor.newInstance();
      }

      // If not possible, instantiate a default stereotypes manager.
      catch (Exception e) {
        Logger.log(Level.WARNING,
            "Cannot instantiate {0}. A {1} is thrown. Default ConfigurationsManager provided to plug-in {2}.",
            new Object[] {clazz.getName(), e.getClass().getName(), FrameWebPlugin.PLUGIN_NAME});
        manager = new ConfigurationManager();
      }
    }
    return manager;
  }

  /**
   * Loads the configuration items from a properties file (see {@code CONFIG_FILE_NAME}) in Visual
   * Paradigm's workspace directory. If not found, loads from the classpath the default
   * configurations and saves to the workspace for next time.
   * 
   * This method is called during the construction of a ConfigurationManager.
   */
  protected void load() {
    // Locate the config file in Visual Paradigm's workspace.
    File workspace = ApplicationManagerUtils.getWorkspaceLocation();
    File configFile = new File(workspace, CONFIG_FILE_NAME);

    Logger.log(Level.FINE, "Loading configuration from {0}. File exists? {1}",
        new Object[] {configFile.getAbsolutePath(), configFile.exists()});

    // If there's already a config file, load it.
    if (configFile.exists()) {
      try {
        properties.load(new FileInputStream(configFile));
        return;
      } catch (IOException e) {
        Logger.log(Level.SEVERE,
            "Cannot read {0}. Will try to use the default configurations instead.",
            new Object[] {configFile.getAbsolutePath()});
      }
    }

    // If the config file doesn't exist, create one based on default values and save.
    Logger.log(Level.FINE, "Will load configuration from classpath location {0} and save to {1}",
        new Object[] {CONFIG_FILE_NAME, configFile.getAbsolutePath()});
    InputStream input =
        ConfigurationManager.class.getClassLoader().getResourceAsStream(CONFIG_FILE_NAME);
    try {
      properties.load(input);
      save();
    } catch (IOException e) {
      Logger.log(Level.SEVERE,
          "Cannot read default configurations. Plug-in will not function properly.",
          new Object[] {configFile.getAbsolutePath()});
    }

    Logger.log(Level.FINER, "Configuration successfully loaded.");
  }

  /**
   * Saves the current configuration to a properties file (see {@code CONFIG_FILE_NAME}) in Visual
   * Paradigm's workspace directory, allowing users to persist configuration changes.
   */
  public void save() {
    // Locate the config file in Visual Paradigm's workspace.
    File workspace = ApplicationManagerUtils.getWorkspaceLocation();
    File configFile = new File(workspace, CONFIG_FILE_NAME);

    Logger.log(Level.FINE, "Saving configuration to {0}. File exists? {1}",
        new Object[] {configFile.getAbsolutePath(), configFile.exists()});

    // Stores the properties in the file.
    try {
      OutputStream output = new FileOutputStream(configFile);
      properties.store(output, "FrameWeb Tools Configuration");
    } catch (IOException e) {
      Logger.log(Level.SEVERE,
          "Cannot save configurations. Plug-in will not remember configuration changes.",
          new Object[] {configFile.getAbsolutePath()});
    }

    Logger.log(Level.FINER, "Configuration successfully saved.");
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
}
