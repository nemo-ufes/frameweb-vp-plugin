package br.ufes.inf.nemo.vpzy.engine.models.entity;

import br.ufes.inf.nemo.vpzy.engine.models.base.AbstractClassModel;
import com.teamdev.jxbrowser.deps.org.checkerframework.checker.nullness.qual.NonNull;
import com.vp.plugin.model.IClass;

public class ClassModel extends AbstractClassModel {
    public ClassModel(@NonNull final IClass clazz) {
        super(clazz);
    }

}
