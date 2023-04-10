package br.ufes.inf.nemo.vpzy.engine;

import com.teamdev.jxbrowser.deps.org.checkerframework.checker.nullness.qual.NonNull;
import com.vp.plugin.model.IAttribute;

public class AttributeModel {
    private final String name;

    private final TypeModel type;

    private final Visibility visibility;

    private boolean isNull;

    private int size;

    private String dateTimePrecision;

    public AttributeModel(String name, TypeModel type, Visibility visibility, boolean isNull, int size,
            String dateTimePrecision) {
        this.name = name;
        this.type = type;
        this.visibility = visibility;
        this.isNull = isNull;
        this.size = size;
        this.dateTimePrecision = dateTimePrecision;
    }

    public AttributeModel(@NonNull IAttribute attribute) {
        this.name = attribute.getName();
        this.type = new TypeModel(attribute.getTypeAsString());
        this.visibility = new Visibility(attribute.getVisibility());

        // iterate the constraints list of attribute

    }

    public String getName() {
        return name;
    }

    public TypeModel getType() {
        return type;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public boolean isNull() {
        return isNull;
    }

    public int getSize() {
        return size;
    }

    public String getDateTimePrecision() {
        return dateTimePrecision;
    }
}
