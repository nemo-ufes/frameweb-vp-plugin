package br.ufes.inf.nemo.vpzy.engine.models.base;

import com.vp.plugin.model.IModelElement;
import java.util.List;

/**
 * Defines the base properties that a template model must have.
 *
 * @author Igor Sunderhus e Silva
 * @version 0.0.1
 */
public abstract class AbstractTemplateModel {
    /**
     * The package of the class.
     */
    private final IModelElement pack;

    /**
     * The class model.
     */
    private final AbstractClassModel clazz;

    /**
     * The system path for the file.
     */
    private final String path;

    /**
     * The methods of the class.
     */
    private final List<AbstractMethodModel> methods;

    /**
     * The attributes of the class.
     */
    private final List<AbstractAttributeModel> attributes;

    /**
     *
     */
    private final List<AbstractAssociationModel> associations;

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
    protected AbstractTemplateModel(final IModelElement pack, final AbstractClassModel clazz, final String path,
            final List<AbstractMethodModel> methods, final List<AbstractAttributeModel> attributes,
            final List<AbstractAssociationModel> associations) {
        this.pack = pack;
        this.clazz = clazz;
        this.path = path;
        this.methods = methods;
        this.attributes = attributes;
        this.associations = associations;
    }

    public IModelElement getPack() {
        return pack;
    }

    public AbstractClassModel getClazz() {
        return clazz;
    }

    public String getPath() {
        return path;
    }

    public List<AbstractMethodModel> getMethods() {
        return methods;
    }

    public List<AbstractAttributeModel> getAttributes() {
        return attributes;
    }

    public List<AbstractAssociationModel> getAssociations() {
        return associations;
    }
}
