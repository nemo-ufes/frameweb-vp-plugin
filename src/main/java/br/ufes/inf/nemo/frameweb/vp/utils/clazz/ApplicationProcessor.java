package br.ufes.inf.nemo.frameweb.vp.utils.clazz;

import br.ufes.inf.nemo.vpzy.engine.models.application.ApplicationAssociationModel;
import br.ufes.inf.nemo.vpzy.engine.models.application.ApplicationAttributeModel;
import br.ufes.inf.nemo.vpzy.engine.models.application.ApplicationClassModel;
import br.ufes.inf.nemo.vpzy.engine.models.application.ApplicationMethodModel;
import br.ufes.inf.nemo.vpzy.engine.models.base.AbstractAssociationModel;
import br.ufes.inf.nemo.vpzy.engine.models.base.AbstractAttributeModel;
import br.ufes.inf.nemo.vpzy.engine.models.base.AbstractClassModel;
import br.ufes.inf.nemo.vpzy.engine.models.base.AbstractMethodModel;
import com.vp.plugin.model.IAttribute;
import com.vp.plugin.model.IClass;
import com.vp.plugin.model.IOperation;
import com.vp.plugin.model.IRelationshipEnd;
import java.util.Map;

/**
 * Processor class for Application classes.
 *
 * @author <a href="https://github.com/igorssilva">Igor Sunderhus e Silva</a>
 * @version 0.0.1
 */
public class ApplicationProcessor extends AbstractClassProcessor {
    private static final AbstractClassProcessor instance = new ApplicationProcessor();

    private ApplicationProcessor() {
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
     * Generates a Application class model
     *
     * @param clazz The class processed.
     * @return The class model.
     */
    @Override
    protected AbstractClassModel getClassModel(final IClass clazz) {
        return new ApplicationClassModel(clazz);
    }

    /**
     * No specific data is added to the data model.
     *
     * @param dataModel The data model.
     * @param clazz     The class processed.
     */
    @Override
    protected void addSpecificData(final Map<String, Object> dataModel, final IClass clazz) {
        // No specific data is added to the data model.
    }

    /**
     * Generates a Application attribute model
     *
     * @param attribute The attribute processed.
     * @return The attribute model.
     */
    @Override
    protected AbstractAttributeModel getAttributeModel(final IAttribute attribute) {
        return new ApplicationAttributeModel(attribute);
    }

    /**
     * Generates a Application method model
     *
     * @param operation The operation processed.
     * @return The method model.
     */
    @Override
    protected AbstractMethodModel getMethodModel(final IOperation operation) {
        return new ApplicationMethodModel(operation);
    }

    /**
     * Generates a Application association model
     *
     * @param relationship The relationship processed.
     * @return The association model.
     */
    @Override
    protected AbstractAssociationModel getAssociationModel(final IRelationshipEnd relationship) {
        return new ApplicationAssociationModel(relationship);
    }
}
