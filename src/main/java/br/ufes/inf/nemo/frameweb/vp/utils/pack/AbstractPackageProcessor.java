package br.ufes.inf.nemo.frameweb.vp.utils.pack;

import br.ufes.inf.nemo.frameweb.vp.model.FrameWebClass;
import br.ufes.inf.nemo.frameweb.vp.utils.FrameWebUtils;
import br.ufes.inf.nemo.frameweb.vp.utils.clazz.AbstractClassProcessor;
import br.ufes.inf.nemo.vpzy.engine.FreeMarkerEngine;
import br.ufes.inf.nemo.vpzy.engine.models.base.FileTypes;
import br.ufes.inf.nemo.vpzy.engine.models.base.TemplateOption;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import com.vp.plugin.model.IClass;
import java.util.Iterator;
import java.util.logging.Level;

/**
 * Contains the common methods for the processors of the packages in the FrameWeb project.
 *
 * @author <a href="https://github.com/igorssilva">Igor Sunderhus e Silva</a>
 * @version 0.0.1
 */
public abstract class AbstractPackageProcessor {
    private final AbstractClassProcessor classProcessor;

    protected AbstractPackageProcessor(final AbstractClassProcessor classProcessor) {
        this.classProcessor = classProcessor;
    }

    public void process(final Iterator<IClass> classIter, final FreeMarkerEngine engine,
            final TemplateOption templateOption) {

        classIter.forEachRemaining(clazz -> {

            final FrameWebClass frameWebClass = FrameWebUtils.getFrameWebClass(clazz);

            Logger.log(Level.INFO, "################# Processing class: " + clazz.getName() + " (" + frameWebClass + ")");

            final FileTypes fileTypes = this.getFileTypes(clazz, frameWebClass, templateOption);
            if (fileTypes != null) {
                this.classProcessor.process(clazz, engine, fileTypes);
            } else {
                Logger.log(Level.WARNING, "################# " + clazz.getName() + " (" + frameWebClass
                                          + ") has no file types associated. Skipping generation");
            }
        });
    }

    protected abstract FileTypes getFileTypes(final IClass clazz, final FrameWebClass frameWebClass,
            final TemplateOption templateOption);
}
