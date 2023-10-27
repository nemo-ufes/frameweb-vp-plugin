package br.ufes.inf.nemo.vpzy.engine.models.base;

import br.ufes.inf.nemo.vpzy.logging.Logger;
import com.teamdev.jxbrowser.deps.org.checkerframework.checker.nullness.qual.NonNull;
import com.vp.plugin.model.IClass;
import com.vp.plugin.model.IGeneralization;
import com.vp.plugin.model.IRealization;
import com.vp.plugin.model.IRelationship;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
     * If it does not exist, it is an empty string.
     */
    private final String generalization;

    /**
     * The list of interfaces implemented by the class.
     * If it does not exist, it is an empty list.
     */
    private final List<String> realizations = new ArrayList<>();

    /**
     * Extracts the {@link IClass} information for the {@link AbstractClassModel}.
     *
     * @param clazz The class processed.
     */
    protected AbstractClassModel(@NonNull final IClass clazz) {
        this.name = clazz.getName();

        String genString = null;

        @SuppressWarnings("unchecked") final Iterator<IRelationship> inheritanceIterator = clazz.toRelationshipIterator();
        while (inheritanceIterator.hasNext()) {

            final IRelationship next = inheritanceIterator.next();

            final IClass parent = (IClass) next.getFrom();
            final IClass child = (IClass) next.getTo();

            if (next instanceof IGeneralization) {

                Logger.log(Level.FINE,
                        "####### Generalization  --- " + child.getName() + " (" + parent.getName() + ")");

                genString = parent.getName();

            } else if (next instanceof IRealization) {

                Logger.log(Level.FINE, "####### Realization  --- " + child.getName() + " (" + parent.getName() + ")");

                realizations.add(parent.getName());
            }

        }

        this.generalization = genString != null ? genString : "";

    }

    public String getName() {
        return name;
    }

    public String getGeneralization() {
        return generalization;
    }

    public List<String> getRealizations() {
        return realizations;
    }
}
