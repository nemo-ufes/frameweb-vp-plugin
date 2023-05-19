package br.ufes.inf.nemo.vpzy.engine.models.entity;

import br.ufes.inf.nemo.vpzy.logging.Logger;
import com.vp.plugin.model.IOperation;
import com.vp.plugin.model.IParameter;
import java.util.Iterator;
import java.util.logging.Level;

public class MethodModel {
    private final String name;

    private final String type;

    private final String visibility;

    private final ParameterModel[] parameters;

    public MethodModel(final IOperation operation) {

        this.name = operation.getName();
        this.type = operation.getReturnTypeAsString();
        this.visibility = operation.getVisibility();

        @SuppressWarnings("unchecked") Iterator<IParameter> iterator = operation.parameterIterator();

        this.parameters = new ParameterModel[operation.parameterCount()];

        for (int i = 0; i < operation.parameterCount(); i++) {
            final IParameter parameter = iterator.next();
            this.parameters[i] = new ParameterModel(parameter.getName(), parameter.getTypeAsString());
            Logger.log(Level.INFO, "ParameterModel: " +  this.parameters[i].getName() + " - " +  this.parameters[i].getType());
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
