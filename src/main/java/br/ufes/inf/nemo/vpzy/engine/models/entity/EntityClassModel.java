package br.ufes.inf.nemo.vpzy.engine.models.entity;

import br.ufes.inf.nemo.vpzy.engine.models.base.AbstractClassModel;
import com.teamdev.jxbrowser.deps.org.checkerframework.checker.nullness.qual.NonNull;
import com.vp.plugin.model.IClass;

/**
 * Class model for Entity classes.
 *
 * @author <a href="https://github.com/igorssilva">Igor Sunderhus e Silva</a>
 * @version 0.0.1
 */
public class EntityClassModel extends AbstractClassModel {
    public EntityClassModel(@NonNull final IClass clazz) {
        super(clazz);
    }

}
