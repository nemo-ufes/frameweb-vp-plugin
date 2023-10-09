package br.ufes.inf.nemo.frameweb.vp.utils.clazz;

import br.ufes.inf.nemo.vpzy.engine.models.base.AbstractAssociationModel;
import br.ufes.inf.nemo.vpzy.engine.models.base.AbstractAttributeModel;
import br.ufes.inf.nemo.vpzy.engine.models.base.AbstractClassModel;
import br.ufes.inf.nemo.vpzy.engine.models.base.AbstractMethodModel;
import br.ufes.inf.nemo.vpzy.engine.models.dao.DaoAssociationModel;
import br.ufes.inf.nemo.vpzy.engine.models.dao.DaoAttributeModel;
import br.ufes.inf.nemo.vpzy.engine.models.dao.DaoClassModel;
import br.ufes.inf.nemo.vpzy.engine.models.dao.DaoMethodModel;
import com.vp.plugin.model.IAttribute;
import com.vp.plugin.model.IClass;
import com.vp.plugin.model.IOperation;
import com.vp.plugin.model.IRelationshipEnd;

/**
 * Processor class for dao classes.
 *
 * @author <a href="https://github.com/igorssilva">Igor Sunderhus e Silva</a>
 * @version 0.0.1
 */
public class DaoProcessor extends AbstractClassProcessor {
    private static final AbstractClassProcessor instance = new DaoProcessor();

    private DaoProcessor() {
        // Singleton factory
    }

    /**
     * Singleton instance getter.
     *
     * @return The singleton instance of this class.
     */
    public static AbstractClassProcessor getInstance() {
        return instance;
    }

    /**
     * Generates a dao class model
     *
     * @param clazz The class processed.
     * @return The class model.
     */
    @Override
    protected AbstractClassModel getClassModel(final IClass clazz) {
        return new DaoClassModel(clazz);
    }

    /**
     * Generates a dao attribute model
     *
     * @param attribute The attribute processed.
     * @return The attribute model.
     */
    @Override
    protected AbstractAttributeModel getAttributeModel(final IAttribute attribute) {
        return new DaoAttributeModel(attribute);
    }

    /**
     * Generates a dao method model
     *
     * @param operation The operation processed.
     * @return The method model.
     */
    @Override
    protected AbstractMethodModel getMethodModel(final IOperation operation) {
        return new DaoMethodModel(operation);
    }

    /**
     * Generates a dao association model
     *
     * @param relationship The relationship processed.
     * @return The association model.
     */
    @Override
    protected AbstractAssociationModel getAssociationModel(final IRelationshipEnd relationship) {
        return new DaoAssociationModel(relationship);
    }
}
