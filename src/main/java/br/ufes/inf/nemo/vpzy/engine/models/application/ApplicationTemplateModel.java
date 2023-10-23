package br.ufes.inf.nemo.vpzy.engine.models.application;

import br.ufes.inf.nemo.vpzy.engine.models.base.AbstractAssociationModel;
import br.ufes.inf.nemo.vpzy.engine.models.base.AbstractAttributeModel;
import br.ufes.inf.nemo.vpzy.engine.models.base.AbstractClassModel;
import br.ufes.inf.nemo.vpzy.engine.models.base.AbstractMethodModel;
import br.ufes.inf.nemo.vpzy.engine.models.base.AbstractTemplateModel;
import com.vp.plugin.model.IModelElement;
import java.util.List;

/**
 * Holds the model for the application templates.
 *
 * @author Igor Sunderhus e Silva
 * @version 0.0.1
 */
public class ApplicationTemplateModel extends AbstractTemplateModel {
    /**
     * Creates a new template model.
     *
     * @param pack         The package of the class.
     * @param clazz        The class model.
     * @param path         The system path for the file.
     * @param methods      The methods of the class.
     * @param attributes   The attributes of the class.
     * @param associations The associations of the class.
     */
    public ApplicationTemplateModel(final IModelElement pack, final AbstractClassModel clazz, final String path,
            final List<AbstractMethodModel> methods, final List<AbstractAttributeModel> attributes,
            final List<AbstractAssociationModel> associations) {
        super(pack, clazz, path, methods, attributes, associations);
    }
}
