package br.ufes.inf.nemo.frameweb.vp;

import java.util.logging.Level;
import com.vp.plugin.VPPlugin;
import com.vp.plugin.VPPluginInfo;
import br.ufes.inf.nemo.frameweb.vp.listeners.FrameWebPackageListener;
import br.ufes.inf.nemo.vpzy.listeners.ListenersManager;
import br.ufes.inf.nemo.vpzy.logging.Logger;

/**
 * Implementation of VPPlugin responsible for configuring FrameWeb Plugin's behavior when loading
 * and unloading.
 *
 * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
 */
public class FrameWebPlugin implements VPPlugin {
  public static final String PLUGIN_VERSION_RELEASE = "0.1";
  public static final String PLUGIN_ID = "br.ufes.inf.nemo.frameweb.vp";
  public static final String PLUGIN_NAME = "FrameWeb Plugin";
  public static final String PLUGIN_REPO = "https://github.com/nemo-ufes/frameweb-vp-plugin/";
  public static final String PLUGIN_REPO_OWNER = "NEMO/UFES";
  public static final String PLUGIN_REPO_NAME = "frameweb-vp-plugin";

  /**
   * Called by Visual Paradigm when the plugin is loaded.
   *
   * @param pluginInfo Plugin information supplied by Visual Paradigm.
   */
  @Override
  public void loaded(VPPluginInfo pluginInfo) {
    // Creates the plug-in's listeners manager and sets up all the listeners.
    ListenersManager listenersManager = new ListenersManager();
    listenersManager.setup();
    listenersManager.addModelListener(new FrameWebPackageListener());

    // Sets up a specific logger for this plug-in.
    Logger.setup(PLUGIN_NAME, Level.INFO);
  }

  /**
   * Called by Visual Paradigm when the plugin is unloaded (i.e., Visual Paradigm will be exited).
   * This method is not called when the plugin is reloaded.
   */
  @Override
  public void unloaded() {}
}
