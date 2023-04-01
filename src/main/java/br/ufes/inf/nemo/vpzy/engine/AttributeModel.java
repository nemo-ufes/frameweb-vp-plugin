package br.ufes.inf.nemo.vpzy.engine;

public class AttributeModel {
    private final String name;
    private final TypeModel type;
    private final Visibility visibility;
    private final boolean isNull;
    private final int size;
    private final String dateTimePrecision;

    public AttributeModel(String name, TypeModel type, Visibility visibility, boolean isNull, int size, String dateTimePrecision) {
        this.name = name;
        this.type = type;
        this.visibility = visibility;
        this.isNull = isNull;
        this.size = size;
        this.dateTimePrecision = dateTimePrecision;
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
