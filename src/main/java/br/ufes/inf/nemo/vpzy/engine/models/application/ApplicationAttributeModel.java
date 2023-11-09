package br.ufes.inf.nemo.vpzy.engine.models.application;

import br.ufes.inf.nemo.vpzy.engine.models.base.AbstractAttributeModel;
import com.teamdev.jxbrowser.deps.org.checkerframework.checker.nullness.qual.NonNull;
import com.vp.plugin.model.IAttribute;

/**
 * Attribute model for Dao classes.
 *
 * @author <a href="https://github.com/igorssilva">Igor Sunderhus e Silva</a>
 * @version 0.0.1
 */
public class ApplicationAttributeModel extends AbstractAttributeModel {
    public ApplicationAttributeModel(final @NonNull IAttribute attribute) {
        super(attribute);
    }
}
