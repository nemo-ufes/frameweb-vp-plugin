package br.ufes.inf.nemo.vpzy.engine.models.application;

import br.ufes.inf.nemo.vpzy.engine.models.base.AbstractAssociationModel;
import com.teamdev.jxbrowser.deps.org.checkerframework.checker.nullness.qual.NonNull;
import com.vp.plugin.model.IRelationshipEnd;

/**
 * Association model for Dao classes.
 *
 * @author <a href="https://github.com/igorssilva">Igor Sunderhus e Silva</a>
 * @version 0.0.1
 */
public class ApplicationAssociationModel extends AbstractAssociationModel {
    public ApplicationAssociationModel(final @NonNull IRelationshipEnd source) {
        super(source);
    }
}
