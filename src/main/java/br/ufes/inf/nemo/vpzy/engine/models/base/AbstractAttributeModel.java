package br.ufes.inf.nemo.vpzy.engine.models.base;

import com.teamdev.jxbrowser.deps.org.checkerframework.checker.nullness.qual.NonNull;
import com.vp.plugin.model.IAttribute;

/**
 * Contains the common attributes for the attribute models.
 *
 * @author <a href="https://github.com/igorssilva">Igor Sunderhus e Silva</a>
 * @version 0.0.1
 */
public abstract class AbstractAttributeModel {
    /**
     * The name of the attribute.
     */
    private final String name;

    /**
     * The type of the attribute.
     */
    private final String type;

    /**
     * The visibility of the attribute. If no visibility is defined, the default visibility is defined by the template.
     */
    private final String visibility;

    /**
     * Extracts the {@link IAttribute} information for the {@link AbstractAttributeModel}.
     *
     * @param attribute The attribute processed.
     */
    protected AbstractAttributeModel(@NonNull final IAttribute attribute) {
        this.name = attribute.getName();
        this.type =
                attribute.getTypeAsString() + (attribute.getTypeModifier() != null ? attribute.getTypeModifier() : "");
        this.visibility = attribute.getVisibility();
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getVisibility() {
        return visibility;
    }

}
