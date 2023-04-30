package br.ufes.inf.nemo.vpzy.engine;

import com.teamdev.jxbrowser.deps.org.checkerframework.checker.nullness.qual.NonNull;
import com.vp.plugin.model.IClass;

public class ClassModel {
    private final String name;

    public ClassModel(@NonNull IClass clazz) {
        this.name = clazz.getName();

    }

    public String getName() {
        return name;
    }

}
