package br.ufes.inf.nemo.frameweb.vp.utils.pack;

import br.ufes.inf.nemo.frameweb.vp.model.FrameWebClass;
import br.ufes.inf.nemo.frameweb.vp.utils.clazz.EntityProcessor;
import br.ufes.inf.nemo.vpzy.engine.models.base.FileTypes;
import br.ufes.inf.nemo.vpzy.engine.models.base.TemplateOption;
import com.vp.plugin.model.IClass;

/**
 * Processor for an entity package.
 *
 * @author <a href="https://github.com/igorssilva">Igor Sunderhus e Silva</a>
 * @version 0.0.1
 */
public class EntityPackageProcessor extends AbstractPackageProcessor {
    private static final AbstractPackageProcessor instance = new EntityPackageProcessor();

    private EntityPackageProcessor() {
        super(EntityProcessor.getInstance());
    }

    public static AbstractPackageProcessor getInstance() {
        return instance;
    }

    /**
     * Gets the file types used in the generation of the code. Only deals with the entity classes.
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
            case PERSISTENT_CLASS:
                if (clazz.hasStereotypes("enumeration")) {
                    return templateOption.getEnumeration();
                } else {
                    return templateOption.getEntity();
                }

            case MAPPED_SUPERCLASS:
                return templateOption.getMappedSuperclass();
            case TRANSIENT_CLASS:
                return templateOption.getTransientClass();
            default:
                return null;
        }
    }
}
