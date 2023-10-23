package br.ufes.inf.nemo.vpzy.engine.models.entity;

import br.ufes.inf.nemo.frameweb.vp.model.FrameWebGeneralization;
import br.ufes.inf.nemo.vpzy.engine.models.base.AbstractClassModel;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import com.teamdev.jxbrowser.deps.org.checkerframework.checker.nullness.qual.NonNull;
import com.vp.plugin.model.IClass;
import com.vp.plugin.model.IGeneralization;
import com.vp.plugin.model.IRelationship;
import com.vp.plugin.model.IStereotype;
import java.util.Iterator;
import java.util.logging.Level;

/**
 * Class model for Entity classes.
 *
 * @author <a href="https://github.com/igorssilva">Igor Sunderhus e Silva</a>
 * @version 0.0.1
 */
public class EntityClassModel extends AbstractClassModel {
    /**
     * Class' inheritance stereotype.
     * Possible values: join, single-table, union.
     *
     */
    private final String inheritanceStereotype;

    public EntityClassModel(@NonNull final IClass clazz) {
        super(clazz);

        String inheritanceStereotypeString = null;
        @SuppressWarnings("unchecked") final Iterator<IRelationship> inheritanceStereotypeIterator = clazz.fromRelationshipIterator();
        while (inheritanceStereotypeString == null && inheritanceStereotypeIterator.hasNext()) {

            final IRelationship next = inheritanceStereotypeIterator.next();

            if (!(next instanceof IGeneralization)) {
                continue;
            }

            final IGeneralization gen = (IGeneralization) next;
            final IStereotype[] generalizationStereotypes =
                    gen.toStereotypesModelArray() == null ? new IStereotype[0] : gen.toStereotypesModelArray();

            FrameWebGeneralization f;

            for (final IStereotype iStereotype : generalizationStereotypes) {
                f = FrameWebGeneralization.of(iStereotype.getName());

                if (f != FrameWebGeneralization.NOT_A_FRAMEWEB_GENERALIZATION) {
                    inheritanceStereotypeString = f.getName();
                    Logger.log(Level.INFO, "####### Generalization Stereotype --- " + clazz.getName() + " ("
                                           + inheritanceStereotypeString + ")");
                    break;
                }
            }

        }

        this.inheritanceStereotype = inheritanceStereotypeString != null ? inheritanceStereotypeString : "";

    }

    public String getInheritanceStereotype() {
        return inheritanceStereotype;
    }
}
