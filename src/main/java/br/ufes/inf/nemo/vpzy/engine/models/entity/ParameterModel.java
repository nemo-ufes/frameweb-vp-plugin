package br.ufes.inf.nemo.vpzy.engine.models.entity;

class ParameterModel {
    private final String name;

    private final String type;

    public ParameterModel(final String name, final String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
