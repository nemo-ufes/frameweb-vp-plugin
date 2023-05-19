package br.ufes.inf.nemo.vpzy.engine.models.entity;

import br.ufes.inf.nemo.frameweb.vp.model.FrameWebClass;
import br.ufes.inf.nemo.frameweb.vp.utils.FrameWebUtils;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import com.teamdev.jxbrowser.deps.org.checkerframework.checker.nullness.qual.NonNull;
import com.vp.plugin.model.IAssociationEnd;
import com.vp.plugin.model.IConstraintElement;
import com.vp.plugin.model.IRelationshipEnd;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;

public class RelationshipModel {
    public static final String ONE_TO_MANY = "OneToMany";

    public static final String ONE_TO_ONE = "OneToOne";

    public static final String MANY_TO_ONE = "ManyToOne";

    private static final Map<String, String> CARDINALITY_MAP = new HashMap<>();

    public static final String COLLECTION = "collection";

    public static final String FETCH = "fetch";

    public static final String CASCADE = "cascade";

    static {
        CARDINALITY_MAP.put("0..1", ONE_TO_ONE);
        CARDINALITY_MAP.put("1", MANY_TO_ONE);
        CARDINALITY_MAP.put("0..*", ONE_TO_MANY);
        CARDINALITY_MAP.put("1..*", ONE_TO_MANY);
        CARDINALITY_MAP.put("*", ONE_TO_MANY);
    }

    private final String sourceToTargetCardinality;

    private final String targetToSourceCardinality;

    private final boolean sourceTransient;

    private final boolean targetTransient;

    private final String sourceTypeName;

    private final String targetTypeName;

    private final String sourceName;

    private final String targetName;

    private final String targetCollection;

    private final String sourceCollection;

    private final String targetFetch;

    private final String sourceFetch;

    private final String targetCascade;

    private final String sourceCascade;

    public RelationshipModel(@NonNull final IRelationshipEnd source) {

        final Map<String, String> sourceConstraints = getDefaultConstraints();

        @SuppressWarnings("unchecked") final Iterator<IConstraintElement> constraintsSource = source.constraintsIterator();

        constraintsSource.forEachRemaining(constraint -> {

            final String[] constraintType = constraint.getName().replace("entity.persistent.", "").split("\\.");
            sourceConstraints.put(constraintType[0], constraintType[1]);
        });

        IRelationshipEnd target = source.getOppositeEnd();

        final Map<String, String> targetConstraints = getDefaultConstraints();

        @SuppressWarnings("unchecked") final Iterator<IConstraintElement> constraintsTarget = source.constraintsIterator();

        constraintsTarget.forEachRemaining(constraint -> {

            final String[] constraintType = constraint.getName().replace("entity.persistent.", "").split("\\.");
            targetConstraints.put(constraintType[0], constraintType[1]);
        });

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

        // Names for the classes in the relationship
        this.sourceTypeName = fromAssociation.getTypeAsString();
        this.targetTypeName = toAssociation.getTypeAsString();

        // Replace between curly braces because the constraints show in the name.
        final String sourceNameFromAssociation =
                fromAssociation.getName() != null ? fromAssociation.getName().replaceAll("\\{.*}", "") : null;

        // Name for the property in the relationship
        this.sourceName = !StringUtils.isBlank(sourceNameFromAssociation)
                ? sourceNameFromAssociation
                : Character.toLowerCase(this.sourceTypeName.charAt(0)) + this.sourceTypeName.substring(1);

        final String targetNameFromAssociation =
                toAssociation.getName() != null ? toAssociation.getName().replaceAll("\\{.*}", "") : null;
        this.targetName = !StringUtils.isBlank(targetNameFromAssociation)
                ? targetNameFromAssociation
                : Character.toLowerCase(this.targetTypeName.charAt(0)) + this.targetTypeName.substring(1);

        // Collection type in the relationship
        this.targetCollection = targetConstraints.get(COLLECTION);
        this.sourceCollection = sourceConstraints.get(COLLECTION);

        // Fetch type in the relationship
        this.targetFetch = targetConstraints.get(FETCH);
        this.sourceFetch = sourceConstraints.get(FETCH);

        // Cascade type in the relationship
        this.targetCascade = targetConstraints.get(CASCADE);
        this.sourceCascade = sourceConstraints.get(CASCADE);

        Logger.log(Level.FINE, "RelationshipModel: " + this);

    }

    private Map<String, String> getDefaultConstraints() {
        final Map<String, String> defaultConstraints = new HashMap<>();

        defaultConstraints.put(FETCH, "lazy");
        defaultConstraints.put(CASCADE, "none");
        defaultConstraints.put(COLLECTION, "list");
        return defaultConstraints;
    }

    //<editor-fold desc="Boilerplate" default="folded">
    public String getSourceToTargetCardinality() {
        return sourceToTargetCardinality;
    }

    public String getTargetToSourceCardinality() {
        return targetToSourceCardinality;
    }

    public String getSourceTypeName() {
        return sourceTypeName;
    }

    public String getTargetTypeName() {
        return targetTypeName;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getTargetName() {
        return targetName;
    }

    public String getTargetCollection() {
        return targetCollection;
    }

    public String getSourceCollection() {
        return sourceCollection;
    }

    public String getTargetFetch() {
        return targetFetch;
    }

    public String getSourceFetch() {
        return sourceFetch;
    }

    public String getTargetCascade() {
        return targetCascade;
    }

    public String getSourceCascade() {
        return sourceCascade;
    }

    public boolean isSourceTransient() {
        return sourceTransient;
    }

    public boolean isTargetTransient() {
        return targetTransient;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("sourceToTargetCardinality", sourceToTargetCardinality)
                .append("targetToSourceCardinality", targetToSourceCardinality)
                .append("sourceTransient", sourceTransient)
                .append("targetTransient", targetTransient)
                .append("sourceTypeName", sourceTypeName)
                .append("targetTypeName", targetTypeName)
                .append("sourceName", sourceName)
                .append("targetName", targetName)
                .append("targetCollection", targetCollection)
                .append("sourceCollection", sourceCollection)
                .append("targetFetch", targetFetch)
                .append("sourceFetch", sourceFetch)
                .append("targetCascade", targetCascade)
                .append("sourceCascade", sourceCascade)
                .toString();
    }
    //</editor-fold>
}
