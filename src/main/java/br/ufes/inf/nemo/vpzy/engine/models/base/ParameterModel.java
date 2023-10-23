package br.ufes.inf.nemo.vpzy.engine.models.base;

/**
 * Contains the common attributes for the parameter models.
 *
 * @author <a href="https://github.com/igorssilva">Igor Sunderhus e Silva</a>
 * @version 0.0.1
 */
public class ParameterModel {
    /**
     * The name of the parameter.
     */
    private final String name;

    /**
     * The name of the parameter's type.
     */
    private final String type;

    /**
     * Creates a new parameter model.
     *
     * @param name The name of the parameter.
     * @param type The name of the parameter's type.
     */
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
