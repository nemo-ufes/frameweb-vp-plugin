package br.ufes.inf.nemo.vpzy.engine.models.base;

import br.ufes.inf.nemo.vpzy.logging.Logger;
import com.vp.plugin.model.IOperation;
import com.vp.plugin.model.IParameter;
import java.util.Iterator;
import java.util.logging.Level;

/**
 * Contains the common attributes for the method models.
 *
 * @author <a href="https://github.com/igorssilva">Igor Sunderhus e Silva</a>
 * @version 0.0.1
 */
public abstract class AbstractMethodModel {
    /**
     * The name of the method.
     */
    private final String name;

    /**
     * The name of the method’s return type.
     */
    private final String type;

    /**
     * The visibility of the method. If no visibility is defined, the default visibility is defined by the template.
     * The visibility can be: public, protected, private, package.
     */
    private final String visibility;

    /**
     * The parameters of the method.
     */
    private final ParameterModel[] parameters;

    /**
     * Extracts the {@link IOperation} information for the {@link AbstractMethodModel}.
     *
     * @param operation The operation processed.
     */
    protected AbstractMethodModel(final IOperation operation) {

        this.name = operation.getName();
        this.type = operation.getReturnTypeAsString();
        this.visibility = operation.getVisibility();

        @SuppressWarnings("unchecked") Iterator<IParameter> iterator = operation.parameterIterator();

        this.parameters = new ParameterModel[operation.parameterCount()];

        for (int i = 0; i < operation.parameterCount(); i++) {
            final IParameter parameter = iterator.next();
            this.parameters[i] = new ParameterModel(parameter.getName(), parameter.getTypeAsString());
            Logger.log(Level.FINE,
                    "ParameterModel: " + this.parameters[i].getName() + " - " + this.parameters[i].getType());
        }

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

    public ParameterModel[] getParameters() {
        return parameters;
    }

}
