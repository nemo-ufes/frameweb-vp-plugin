package br.ufes.inf.nemo.frameweb.vp.utils.clazz;

import br.ufes.inf.nemo.vpzy.engine.models.base.AbstractAssociationModel;
import br.ufes.inf.nemo.vpzy.engine.models.base.AbstractAttributeModel;
import br.ufes.inf.nemo.vpzy.engine.models.base.AbstractClassModel;
import br.ufes.inf.nemo.vpzy.engine.models.base.AbstractMethodModel;
import br.ufes.inf.nemo.vpzy.engine.models.base.AbstractTemplateModel;
import br.ufes.inf.nemo.vpzy.engine.models.entity.EntityAssociationModel;
import br.ufes.inf.nemo.vpzy.engine.models.entity.EntityAttributeModel;
import br.ufes.inf.nemo.vpzy.engine.models.entity.EntityClassModel;
import br.ufes.inf.nemo.vpzy.engine.models.entity.EntityMethodModel;
import br.ufes.inf.nemo.vpzy.engine.models.entity.EntityTemplateModel;
import com.vp.plugin.model.IAttribute;
import com.vp.plugin.model.IClass;
import com.vp.plugin.model.IModelElement;
import com.vp.plugin.model.IOperation;
import com.vp.plugin.model.IRelationshipEnd;
import java.util.List;

/**
 * Processor class for entity classes.
 *
 * @author <a href="https://github.com/igorssilva">Igor Sunderhus e Silva</a>
 * @version 0.0.1
 */
public class EntityProcessor extends AbstractClassProcessor {
    private static final AbstractClassProcessor instance = new EntityProcessor();

    private EntityProcessor() {
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
     * No specific data is added to the data model.
     *
     * @param dataModel The data model.
     * @param clazz     The class processed.
     */
    @Override
    protected void addSpecificData(final AbstractTemplateModel dataModel, final IClass clazz) {
        // No specific data is added to the data model.
    }

    /**
     * Generates a entity class model
     *
     * @param clazz The class processed.
     * @return The class model.
     */
    @Override
    protected AbstractClassModel getClassModel(final IClass clazz) {
        return new EntityClassModel(clazz);
    }

    /**
     * Initializes an entity template model.
     *
     * @param pack                    Package of the class.
     * @param classModel              Class model.
     * @param path                    System path for the file.
     * @param abstractMethodModels    Methods of the class.
     * @param entityAttributeModels   Attributes of the class.
     * @param entityAssociationModels Associations of the class.
     * @return The template model for the engine.
     */
    @Override
    protected AbstractTemplateModel processTemplateModel(final IModelElement pack, final AbstractClassModel classModel,
            final String path, final List<AbstractMethodModel> abstractMethodModels,
            final List<AbstractAttributeModel> entityAttributeModels,
            final List<AbstractAssociationModel> entityAssociationModels) {
        return new EntityTemplateModel(pack, classModel, path, abstractMethodModels, entityAttributeModels,
                entityAssociationModels);
    }

    /**
     * Generates a entity attribute model
     *
     * @param attribute The attribute processed.
     * @return The attribute model.
     */
    @Override
    protected AbstractAttributeModel getAttributeModel(final IAttribute attribute) {
        return new EntityAttributeModel(attribute);
    }

    /**
     * Generates a entity method model
     *
     * @param operation The operation processed.
     * @return The method model.
     */
    @Override
    protected AbstractMethodModel getMethodModel(final IOperation operation) {
        return new EntityMethodModel(operation);
    }

    /**
     * Generates a entity association model
     *
     * @param relationship The relationship processed.
     * @return The association model.
     */
    @Override
    protected AbstractAssociationModel getAssociationModel(final IRelationshipEnd relationship) {
        return new EntityAssociationModel(relationship);
    }
}
