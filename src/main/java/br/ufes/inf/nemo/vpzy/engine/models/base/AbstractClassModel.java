package br.ufes.inf.nemo.vpzy.engine.models.base;

import com.teamdev.jxbrowser.deps.org.checkerframework.checker.nullness.qual.NonNull;
import com.vp.plugin.model.IClass;

public abstract class AbstractClassModel {
    private final String name;

    protected AbstractClassModel(@NonNull final IClass clazz) {
        this.name = clazz.getName();

    }

    public String getName() {
        return name;
    }
}
