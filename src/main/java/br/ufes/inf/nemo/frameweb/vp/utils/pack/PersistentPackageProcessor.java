package br.ufes.inf.nemo.frameweb.vp.utils.pack;

import br.ufes.inf.nemo.frameweb.vp.model.FrameWebClass;
import br.ufes.inf.nemo.frameweb.vp.utils.clazz.DaoProcessor;
import br.ufes.inf.nemo.vpzy.engine.models.base.FileTypes;
import br.ufes.inf.nemo.vpzy.engine.models.base.TemplateOption;
import com.vp.plugin.model.IClass;

/**
 * Processor for a persistent package.
 *
 * @author <a href="https://github.com/igorssilva">Igor Sunderhus e Silva</a>
 * @version 0.0.1
 */
public class PersistentPackageProcessor extends AbstractPackageProcessor {
    private static final AbstractPackageProcessor instance = new PersistentPackageProcessor();

    private PersistentPackageProcessor() {
        super(DaoProcessor.getInstance());
    }

    public static AbstractPackageProcessor getInstance() {
        return instance;
    }

    /**
     * Gets the file types used in the generation of the code. Only deals with the persistent classes.
     *
     * @param clazz          The class processed.
     * @param frameWebClass  The FrameWeb class type.
     * @param templateOption The template option.
     * @return The file description for code generation.
     */
    @Override
    protected FileTypes getFileTypes(final IClass clazz, final FrameWebClass frameWebClass,
            final TemplateOption templateOption) {
        switch (frameWebClass) {
            case DAO_CLASS:
                return templateOption.getDao();
            case DAO_INTERFACE:
                return templateOption.getDaoInterface();
            default:
                return null;
        }
    }
}
