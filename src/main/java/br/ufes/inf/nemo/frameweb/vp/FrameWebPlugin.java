package br.ufes.inf.nemo.frameweb.vp;

import java.util.logging.Level;
import br.ufes.inf.nemo.vpzy.managers.YamlConfigurationManager;
import com.vp.plugin.VPPlugin;
import com.vp.plugin.VPPluginInfo;
import br.ufes.inf.nemo.frameweb.vp.listeners.FrameWebAssociationEndListener;
import br.ufes.inf.nemo.frameweb.vp.listeners.FrameWebClassListener;
import br.ufes.inf.nemo.frameweb.vp.listeners.FrameWebPackageListener;
import br.ufes.inf.nemo.vpzy.listeners.ListenersManager;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import br.ufes.inf.nemo.vpzy.managers.ConfigurationManager;
import br.ufes.inf.nemo.vpzy.utils.ApplicationManagerUtils;

/**
 * Implementation of VPPlugin responsible for configuring FrameWeb plug-in's behavior when loading
 * and unloading. This class also centralizes global information on the FrameWeb plug-in.
 *
 * @author Vítor E. Silva Souza (<a href="http://www.inf.ufes.br/~vitorsouza/">Ufes Page</a>)
 * @author Igor Sunderhus e Silva (<a href="https://github.com/igorssilva">Github page</a>)
 */
public class FrameWebPlugin implements VPPlugin {
  /* Plug-in information. */
  public static final String PLUGIN_VERSION_RELEASE = "0.1";
  public static final String PLUGIN_ID = "br.ufes.inf.nemo.frameweb.vp";
  public static final String PLUGIN_NAME = "FrameWeb Tools";
  public static final String PLUGIN_REPO = "https://github.com/nemo-ufes/frameweb-vp-plugin/";
  public static final String PLUGIN_REPO_OWNER = "NEMO/UFES";
  public static final String PLUGIN_REPO_NAME = "frameweb-vp-plugin";

  /** Name of the configuration file. */
  private static final String CONFIG_FILE_NAME = "frameweb-tools.properties";
  private static final String TEMPLATE_CONFIG_FILE_NAME = "template.yaml";

  /* Plug-in configuration keys. */
  public static final String CONFIG_LOGGING_LEVEL = "logging.level";
  public static final String CONFIG_DEFAULT_ENTITY_CLASS_STEREOTYPE =
      "default.entity.class.stereotype";
  public static final String CONFIG_DEFAULT_ENTITY_ATTRIBUTE_STEREOTYPE =
      "default.entity.attribute.stereotype";
  public static final String CONFIG_DEFAULT_ENTITY_ATTRIBUTE_CONSTRAINT_NULLABLE =
      "default.entity.attribute.constraint.nullable";
  public static final String CONFIG_DEFAULT_ENTITY_ATTRIBUTE_CONSTRAINT_PRECISION =
      "default.entity.attribute.constraint.precision";
  public static final String CONFIG_DEFAULT_ENTITY_ATTRIBUTE_CONSTRAINT_GENERATION =
      "default.entity.attribute.constraint.generation";
  public static final String CONFIG_DEFAULT_ENTITY_GENERALIZATION_STEREOTYPE =
      "default.entity.generalization.stereotype";
  public static final String CONFIG_DEFAULT_ENTITY_ASSOCIATION_END_CONSTRAINT_COLLECTION =
      "default.entity.association.end.constraint.collection";
  public static final String CONFIG_DEFAULT_ENTITY_ASSOCIATION_END_CONSTRAINT_CASCADE =
      "default.entity.association.end.constraint.cascade";

  public static final String CONFIG_DEFAULT_GENERATE_CODE_OUTPUT_FOLDER =
      "default.generate.code.output.folder";

  public static final String CONFIG_DEFAULT_GENERATE_CODE_TEMPLATE_FOLDER =
      "default.generate.code.template.folder";

  /** The active instance of the plug-in. */
  private static FrameWebPlugin activeInstance;

  /** Listeners manager for the plug-in. */
  private ListenersManager listenersManager;

  /** Configuration manager for the plug-in. */
  private ConfigurationManager configManager;

  /** Indicates if the Plug-in Settings Dialog is open. */
  private boolean pluginSettingsDialogOpen = false;

  /* code generation properties. */

  /** Indicates if the Generate Code Settings Dialog is open. */
  private boolean generateCodeSettingsDialogOpen = false;
  private YamlConfigurationManager generateCodeConfigManager;

  /** Returns the active instance of the plug-in. */
  public static FrameWebPlugin instance() {
    return activeInstance;
  }

  public ConfigurationManager getConfigManager() {
    return configManager;
  }

  public boolean isPluginSettingsDialogOpen() {
    return pluginSettingsDialogOpen;
  }

  public void setPluginSettingsDialogOpen(boolean pluginSettingsDialogOpen) {
    this.pluginSettingsDialogOpen = pluginSettingsDialogOpen;
  }


  /**
   * Sets up the plug-in (initialization).
   */
  private void setup() {
    // Creates the plug-in's listeners manager and sets up all the listeners.
    ListenersManager listenersManager = new ListenersManager();
    listenersManager.setup();
    listenersManager.addModelListener(new FrameWebPackageListener());
    listenersManager.addModelListener(new FrameWebClassListener());
    listenersManager.addModelListener(new FrameWebAssociationEndListener());

    // Loads the plug-in configuration.
    configManager = new ConfigurationManager(PLUGIN_NAME, CONFIG_FILE_NAME);
    generateCodeConfigManager = new YamlConfigurationManager(PLUGIN_NAME, TEMPLATE_CONFIG_FILE_NAME);

    // Sets up a specific logger for this plug-in.
    Logger.setup(PLUGIN_NAME, Level.parse(configManager.getProperty(CONFIG_LOGGING_LEVEL)));
  }

  /**
   * Shuts down the plug-in, deactivating resources.
   */
  private void shutdown() {
    // Shuts down the listeners manager.
    listenersManager.shutdown();
  }

  /**
   * Reloads the plug-in, in case its classes get updated. Used mostly by plug-in developers. And it
   * doesn't always work as expected, unfortunately...
   */
  public void reload() {
    shutdown();
    ApplicationManagerUtils.reloadPluginClasses(FrameWebPlugin.PLUGIN_ID);
    setup();
  }

  /**
   * Called by Visual Paradigm when the plug-in is loaded.
   *
   * @param pluginInfo Plugin information supplied by Visual Paradigm.
   */
  @Override
  public void loaded(VPPluginInfo pluginInfo) {
    FrameWebPlugin.activeInstance = this;
    setup();
  }

  /**
   * Called by Visual Paradigm when the plug-in is unloaded (i.e., Visual Paradigm will be exited).
   * This method is not called when the plug-in is reloaded.
   */
  @Override
  public void unloaded() {
    FrameWebPlugin.activeInstance = null;
  }

  /** Code generation methods */
  public boolean isGenerateCodeSettingsDialogOpen() {
    return generateCodeSettingsDialogOpen;
  }

  public void setGenerateCodeSettingsDialogOpen(boolean generateCodeSettingsDialogOpen) {
    this.generateCodeSettingsDialogOpen = generateCodeSettingsDialogOpen;
  }

    public YamlConfigurationManager getGenerateCodeConfigManager() {
        return generateCodeConfigManager;
    }
}
