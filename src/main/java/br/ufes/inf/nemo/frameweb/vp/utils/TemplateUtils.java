package br.ufes.inf.nemo.frameweb.vp.utils;

import br.ufes.inf.nemo.frameweb.vp.FrameWebPlugin;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebPackage;
import br.ufes.inf.nemo.frameweb.vp.utils.pack.AbstractPackageProcessor;
import br.ufes.inf.nemo.frameweb.vp.utils.pack.ApplicationPackageProcessor;
import br.ufes.inf.nemo.frameweb.vp.utils.pack.EntityPackageProcessor;
import br.ufes.inf.nemo.frameweb.vp.utils.pack.PersistentPackageProcessor;
import br.ufes.inf.nemo.vpzy.engine.FreeMarkerEngine;
import br.ufes.inf.nemo.vpzy.engine.models.base.TemplateOption;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import br.ufes.inf.nemo.vpzy.managers.JsonConfigurationManager;
import br.ufes.inf.nemo.vpzy.utils.ProjectManagerUtils;
import com.vp.plugin.model.IClass;
import com.vp.plugin.model.IPackage;
import com.vp.plugin.model.IProject;
import com.vp.plugin.model.factory.IModelElementFactory;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.logging.Level;

/**
 * Utility class for generating code from templates for the current project in Visual Paradigm.
 *
 * @author <a href="https://github.com/igorssilva">Igor Sunderhus e Silva</a>
 */
public final class TemplateUtils {
    private TemplateUtils() {
        // Prevents instantiation.
    }

    /**
     * Generates the code for a package in the FrameWeb project.
     *
     * @param pack           The package processed.
     * @param engine         The FreeMarker engine used to generate the code.
     * @param templateOption The template option used.
     */
    public static void processPackage(final IPackage pack, final FreeMarkerEngine engine,
            final TemplateOption templateOption) {

        final FrameWebPackage frameWebPackage = FrameWebUtils.getFrameWebPackage(pack);
        if (frameWebPackage == FrameWebPackage.NOT_A_FRAMEWEB_PACKAGE) {
            Logger.log(Level.INFO, "####### skipping " + pack.getName() + " (" + frameWebPackage + ")");
            return;
        }

        Logger.log(Level.INFO, "####### Processing Package " + pack.getName() + " (" + frameWebPackage + ")");

        @SuppressWarnings("unchecked") Iterator<IClass> classIter = pack.childIterator(
                IModelElementFactory.MODEL_TYPE_CLASS);

        final AbstractPackageProcessor packageProcessing;
        switch (frameWebPackage) {
            case ENTITY_PACKAGE:
                packageProcessing = EntityPackageProcessor.getInstance();
                break;
            case APPLICATION_PACKAGE:
                packageProcessing = ApplicationPackageProcessor.getInstance();
                break;
            case CONTROLLER_PACKAGE:
                // TODO: process controller package
                packageProcessing = null;
                break;
            case PERSISTENCE_PACKAGE:
                packageProcessing = PersistentPackageProcessor.getInstance();
                break;

            default:
                packageProcessing = null;
                break;
        }

        if (packageProcessing != null) {
            packageProcessing.process(classIter, engine, templateOption);
        }
    }

    /**
     * Generates the code for the FrameWeb project.
     *
     * @param templateOption The template option used.
     */
    public static void generateCode(final TemplateOption templateOption, String outputDir) {
        final IProject project = ProjectManagerUtils.getCurrentProject();
        final JsonConfigurationManager configurationManager = FrameWebPlugin.instance().getGenerateCodeConfigManager();
        final Path templatePath = Paths.get(configurationManager.getTemplateFolder().getPath(),
                templateOption.getName());
        final FreeMarkerEngine engine = new FreeMarkerEngine(templatePath.toString(), outputDir);

        @SuppressWarnings("unchecked") Iterator<IPackage> iter = project.allLevelModelElementIterator(
                IModelElementFactory.MODEL_TYPE_PACKAGE);

        // process packages
        iter.forEachRemaining(pack -> processPackage(pack, engine, templateOption));

    }
}
