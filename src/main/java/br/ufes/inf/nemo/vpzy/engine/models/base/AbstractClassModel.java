package br.ufes.inf.nemo.vpzy.engine.models.base;

import br.ufes.inf.nemo.vpzy.logging.Logger;
import com.teamdev.jxbrowser.deps.org.checkerframework.checker.nullness.qual.NonNull;
import com.vp.plugin.model.IClass;
import com.vp.plugin.model.IGeneralization;
import com.vp.plugin.model.IRelationship;
import java.util.Iterator;
import java.util.logging.Level;

/**
 * Contains the common attributes for the class models.
 *
 * @author <a href="https://github.com/igorssilva">Igor Sunderhus e Silva</a>
 * @version 0.0.1
 */
public abstract class AbstractClassModel {
    /**
     * The name of the class.
     */
    private final String name;

    /**
     * The name of the parent class, if it exists.
     */
    private final String generalization;

    /**
     * Extracts the {@link IClass} information for the {@link AbstractClassModel}.
     * @param clazz The class processed.
     */
    protected AbstractClassModel(@NonNull final IClass clazz) {
        this.name = clazz.getName();


        String genString = null;
        @SuppressWarnings("unchecked")
        final Iterator<IRelationship> iterator = clazz.fromRelationshipIterator();
        while (genString == null && iterator.hasNext()) {

            final Object next = iterator.next();
            if (!(next instanceof IGeneralization)) {
                continue;
            }
            final IGeneralization gen = (IGeneralization) next;

            final String parentElement = gen.getParent().getParent().getName();
            final IClass parent = (IClass) gen.getFrom();
            final IClass child = (IClass) gen.getTo();
            genString = parent.getName();
            Logger.log(Level.FINE,
                    "####### " + parentElement + "  --- " + child.getName() + " (" + parent.getName() + ")");
        }

        this.generalization = genString;
    }

    public String getName() {
        return name;
    }

    public String getGeneralization() {
        return generalization;
    }
}
