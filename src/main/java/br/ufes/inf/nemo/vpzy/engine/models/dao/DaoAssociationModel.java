package br.ufes.inf.nemo.vpzy.engine.models.dao;

import br.ufes.inf.nemo.vpzy.engine.models.base.AbstractAssociationModel;
import com.teamdev.jxbrowser.deps.org.checkerframework.checker.nullness.qual.NonNull;
import com.vp.plugin.model.IRelationshipEnd;

/**
 * Association model for Dao classes.
 *
 * @author <a href="https://github.com/igorssilva">Igor Sunderhus e Silva</a>
 * @version 0.0.1
 */
public class DaoAssociationModel extends AbstractAssociationModel {
    public DaoAssociationModel(final @NonNull IRelationshipEnd source) {
        super(source);
    }
}
