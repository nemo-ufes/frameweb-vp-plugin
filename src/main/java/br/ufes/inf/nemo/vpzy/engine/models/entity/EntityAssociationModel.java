package br.ufes.inf.nemo.vpzy.engine.models.entity;

import br.ufes.inf.nemo.frameweb.vp.model.FrameWebAssociationEndConstraint;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebClass;
import br.ufes.inf.nemo.frameweb.vp.utils.FrameWebUtils;
import br.ufes.inf.nemo.vpzy.engine.models.base.AbstractAssociationModel;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import com.teamdev.jxbrowser.deps.org.checkerframework.checker.nullness.qual.NonNull;
import com.vp.plugin.model.IAssociationEnd;
import com.vp.plugin.model.IConstraintElement;
import com.vp.plugin.model.IRelationshipEnd;
import org.apache.commons.lang.builder.ToStringBuilder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;

/**
 * Association model for Entity classes.
 *
 * @author <a href="https://github.com/igorssilva">Igor Sunderhus e Silva</a>
 * @version 0.0.1
 */
public class EntityAssociationModel extends AbstractAssociationModel {
    private static final String ONE_TO_MANY = "OneToMany";

    private static final String ONE_TO_ONE = "OneToOne";

    private static final String MANY_TO_ONE = "ManyToOne";

    private static final Map<String, String> CARDINALITY_MAP = new HashMap<>();

    private static final String COLLECTION = "collection";

    private static final String FETCH = "fetch";

    private static final String CASCADE = "cascade";

    private static final String ORDER = "order";

    static {
        CARDINALITY_MAP.put("0..1", ONE_TO_ONE);
        CARDINALITY_MAP.put("1", MANY_TO_ONE);
        CARDINALITY_MAP.put("0..*", ONE_TO_MANY);
        CARDINALITY_MAP.put("1..*", ONE_TO_MANY);
        CARDINALITY_MAP.put("*", ONE_TO_MANY);
    }

    /**
     * Source to target relationship's cardinality. The possible cardinality values: {@value #ONE_TO_ONE},
     * {@value #ONE_TO_MANY}, {@value #MANY_TO_ONE}.
     */
    private final String sourceToTargetCardinality;

    /**
     * Target to source relationship's cardinality. The cardinality can be: {@value #ONE_TO_ONE}, {@value #ONE_TO_MANY},
     * {@value #MANY_TO_ONE}.
     */
    private final String targetToSourceCardinality;

    /**
     * If the source is transient.
     */
    private final boolean sourceTransient;

    /**
     * If the target is transient.
     */
    private final boolean targetTransient;

    /**
     * The target’s collection type. Possible values: bag, list, map, set.
     */
    private final String targetCollection;

    /**
     * The source’s collection type. Possible values: bag, list, map, set.
     */
    private final String sourceCollection;

    /**
     * The target’s fetch type. Possible values: eager, lazy.
     */
    private final String targetFetch;

    /**
     * The source’s fetch type. Possible values: eager, lazy.
     */
    private final String sourceFetch;

    /**
     * The target’s cascade type. Possible values: none, all, merge, persist, refresh, remove.
     */
    private final String targetCascade;

    /**
     * The source’s cascade type. Possible values: none, all, merge, persist, refresh, remove.
     */
    private final String sourceCascade;

    /**
     * The target’s order type. Possible values: natural, (comma separated list of property names).
     */
    private final String targetOrder;

    /**
     * The source’s order type. Possible values: natural, (comma separated list of property names).
     */
    private final String sourceOrder;

    public EntityAssociationModel(@NonNull final IRelationshipEnd source) {
        super(source);

        final Map<String, String> sourceConstraints = getConstraints(source);

        IRelationshipEnd target = source.getOppositeEnd();

        final Map<String, String> targetConstraints = getConstraints(target);

        IAssociationEnd fromAssociation = (IAssociationEnd) source;
        Logger.log(Level.FINE,
                String.format("fromAssociation >>>>> Name: %s, Multiplicity: %s, Type: %s", fromAssociation.getName(),
                        fromAssociation.getMultiplicity(), fromAssociation.getTypeAsString()));

        IAssociationEnd toAssociation = (IAssociationEnd) target;
        Logger.log(Level.FINE,
                String.format("toAssociation >>>>> Name: %s, Multiplicity: %s, Type: %s", toAssociation.getName(),
                        toAssociation.getMultiplicity(), toAssociation.getTypeAsString()));

        // Cardinalities for the relationship
        this.sourceToTargetCardinality = CARDINALITY_MAP.get(toAssociation.getMultiplicity());
        this.targetToSourceCardinality = CARDINALITY_MAP.get(fromAssociation.getMultiplicity());

        // Transient for the relationship
        this.sourceTransient = FrameWebUtils.getFrameWebClass(source.getModelElement())
                .equals(FrameWebClass.TRANSIENT_CLASS);

        this.targetTransient = FrameWebUtils.getFrameWebClass(target.getModelElement())
                .equals(FrameWebClass.TRANSIENT_CLASS);

        // Collection type in the relationship
        this.targetCollection = targetConstraints.get(COLLECTION);
        this.sourceCollection = sourceConstraints.get(COLLECTION);

        // Fetch type in the relationship
        this.targetFetch = targetConstraints.get(FETCH);
        this.sourceFetch = sourceConstraints.get(FETCH);

        // Cascade type in the relationship
        this.targetCascade = targetConstraints.get(CASCADE);
        this.sourceCascade = sourceConstraints.get(CASCADE);

        // Order type in the relationship
        this.targetOrder = targetConstraints.get(ORDER);
        this.sourceOrder = sourceConstraints.get(ORDER);

        Logger.log(Level.FINE, this.toString());

    }

    private Map<String, String> getConstraints(final IRelationshipEnd relationshipEnd) {
        final Map<String, String> constraints = getDefaultConstraints();

        @SuppressWarnings("unchecked") final Iterator<IConstraintElement> constraintsSource = relationshipEnd.constraintsIterator();

        constraintsSource.forEachRemaining(constraint -> {

            final String[] constraintType = constraint.getName().replace("entity.persistent.", "").split("\\.");
            final FrameWebAssociationEndConstraint frameWebAssociationEndConstraint = FrameWebAssociationEndConstraint.ofPluginUIIDWithoutActionPrefix(
                    constraint.getName());
            if (frameWebAssociationEndConstraint != null) {
                Logger.log(Level.FINE,
                        "Constraint: " + constraint.getName() + " - " + constraint.getSpecification().getValueAsString()
                        + " - " + frameWebAssociationEndConstraint.isParameterized());
                if (!frameWebAssociationEndConstraint.isParameterized()) {
                    constraints.put(constraintType[0], constraintType[1]);
                } else {
                    final String valueConstraint = constraint.getSpecification()
                            .getValueAsString()
                            .replace(constraintType[0] + "=", "");

                    constraints.put(constraintType[0], valueConstraint);
                }
            }
        });
        return constraints;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("sourceToTargetCardinality", sourceToTargetCardinality)
                .append("targetToSourceCardinality", targetToSourceCardinality)
                .append("sourceTransient", sourceTransient)
                .append("targetTransient", targetTransient)
                .append("sourceTypeName", this.getSourceTypeName())
                .append("targetTypeName", this.getTargetTypeName())
                .append("sourceName", this.getSourceName())
                .append("targetName", this.getTargetName())
                .append("targetCollection", targetCollection)
                .append("sourceCollection", sourceCollection)
                .append("targetFetch", targetFetch)
                .append("sourceFetch", sourceFetch)
                .append("targetCascade", targetCascade)
                .append("sourceCascade", sourceCascade)
                .append("targetOrder", targetOrder)
                .append("sourceOrder", sourceOrder)
                .toString();
    }

    private Map<String, String> getDefaultConstraints() {
        final Map<String, String> defaultConstraints = new HashMap<>();

        defaultConstraints.put(FETCH, "lazy");
        defaultConstraints.put(CASCADE, "none");
        defaultConstraints.put(COLLECTION, "list");
        defaultConstraints.put(ORDER, "natural");
        return defaultConstraints;
    }

    //<editor-fold desc="Boilerplate" default="folded">

    /**
     * Get the source to target relationship's cardinality.
     *
     * @return The source to target relationship's cardinality.
     */
    public String getSourceToTargetCardinality() {
        return sourceToTargetCardinality;
    }

    /**
     * Get the target to source relationship's cardinality.
     *
     * @return The target to source relationship's cardinality.
     */
    public String getTargetToSourceCardinality() {
        return targetToSourceCardinality;
    }

    /**
     * Get the target's collection type.
     *
     * @return The target's collection type.
     */
    public String getTargetCollection() {
        return targetCollection;
    }

    /**
     * Get the source's collection type.
     *
     * @return The source's collection type.
     */
    public String getSourceCollection() {
        return sourceCollection;
    }

    /**
     * Get the target's fetch type.
     *
     * @return The target's fetch type.
     */
    public String getTargetFetch() {
        return targetFetch;
    }

    /**
     * Get the source's fetch type.
     *
     * @return The source's fetch type.
     */
    public String getSourceFetch() {
        return sourceFetch;
    }

    /**
     * Get the target's cascade type.
     *
     * @return The target's cascade type.
     */
    public String getTargetCascade() {
        return targetCascade;
    }

    /**
     * Get the source's cascade type.
     *
     * @return The source's cascade type.
     */
    public String getSourceCascade() {
        return sourceCascade;
    }

    /**
     * Check if the source is transient.
     *
     * @return True if the source is transient, false otherwise.
     */
    public boolean isSourceTransient() {
        return sourceTransient;
    }

    /**
     * Check if the target is transient.
     *
     * @return True if the target is transient, false otherwise.
     */
    public boolean isTargetTransient() {
        return targetTransient;
    }

    /**
     * Get the target's order type.
     *
     * @return The target's order type.
     */
    public String getTargetOrder() {
        return targetOrder;
    }

    /**
     * Get the source's order type.
     *
     * @return The source's order type.
     */
    public String getSourceOrder() {
        return sourceOrder;
    }

    //</editor-fold>
}
