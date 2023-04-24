package br.ufes.inf.nemo.vpzy.engine;

import com.teamdev.jxbrowser.deps.org.checkerframework.checker.nullness.qual.NonNull;
import com.vp.plugin.model.IClass;
import java.util.ArrayList;
import java.util.List;

public class ClassModel {
    private final String name;

    private final List<AttributeModel> attributes = new ArrayList<>();

    private final List<ClassModel> classes = new ArrayList<>();



    public ClassModel(String name) {
        this.name = name;
    }

    public ClassModel(@NonNull IClass clazz) {
        this.name = clazz.getName();
    }

    public String getName() {
        return name;
    }
}
