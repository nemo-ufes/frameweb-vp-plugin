package br.ufes.inf.nemo.vpzy.engine.models.base;

import com.teamdev.jxbrowser.deps.org.checkerframework.checker.nullness.qual.NonNull;
import com.vp.plugin.model.IAttribute;

public abstract class AbstractAttributeModel {
    private final String name;

    private final String type;

    private final String visibility;

    protected AbstractAttributeModel(@NonNull final IAttribute attribute) {
        this.name = attribute.getName();
        this.type = attribute.getTypeAsString() + (attribute.getTypeModifier() != null ? attribute.getTypeModifier() : "");
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
