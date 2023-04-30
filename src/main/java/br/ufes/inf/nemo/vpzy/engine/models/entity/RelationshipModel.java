package br.ufes.inf.nemo.vpzy.engine.models.entity;

import br.ufes.inf.nemo.vpzy.logging.Logger;
import com.teamdev.jxbrowser.deps.org.checkerframework.checker.nullness.qual.NonNull;
import com.vp.plugin.model.IRelationshipEnd;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class RelationshipModel {
    public static final String TYPE_MODIFIER = "typeModifier";

    public static final String NAVIGABLE = "navigable";

    public static final String MULTIPLICITY = "multiplicity";

    public static final String TYPE = "type";

    public static final String NAME = "name";

    private static final Map<String, String> CARDINALITY_MAP = new HashMap<>();

    static {
        CARDINALITY_MAP.put("0..1", "ManyToOne");
        CARDINALITY_MAP.put("1", "ManyToOne");
        CARDINALITY_MAP.put("0..*", "OneToMany");
        CARDINALITY_MAP.put("1..*", "OneToMany");
        CARDINALITY_MAP.put("*", "OneToMany");
    }

    private final String sourceToTargetCardinality;

    private final String targetToSourceCardinality;

    private final boolean sourceToTargetNavigability;

    private final boolean targetToSourceNavigability;

    private final String sourceTypeName;

    private final String targetTypeName;

    private final String sourceName;

    private final String targetName;

    private final String targetCollection;

    private final String sourceCollection;

    public RelationshipModel(@NonNull final IRelationshipEnd from) {
        IRelationshipEnd to = from.getOppositeEnd();

        Map<String, Object> fromProperties = convertSemicolonStringToMap(from.toPropertiesString());

        Logger.log(Level.INFO, "from Properties: " + fromProperties);

        Map<String, Object> toProperties = convertSemicolonStringToMap(to.toPropertiesString());

        Logger.log(Level.INFO, "to Properties: " + toProperties);

        this.sourceToTargetCardinality = fromProperties.get(MULTIPLICITY) != null
                ? CARDINALITY_MAP.get(fromProperties.get(MULTIPLICITY))
                : "ManyToOne";
        this.targetToSourceCardinality = toProperties.get(MULTIPLICITY) != null
                ? CARDINALITY_MAP.get(toProperties.get(MULTIPLICITY))
                : "OneToMany";
        this.sourceTypeName = fromProperties.get(TYPE) != null ? (String) fromProperties.get(TYPE) : "Object";
        this.targetTypeName = toProperties.get(TYPE) != null ? (String) toProperties.get(TYPE) : "Object";
        this.sourceName = fromProperties.get(NAME) != null ? (String) fromProperties.get(NAME) : this.sourceTypeName;
        this.targetName = toProperties.get(NAME) != null ? (String) toProperties.get(NAME) : this.targetTypeName;
        this.targetCollection =
                fromProperties.get(TYPE_MODIFIER) != null ? (String) fromProperties.get(TYPE_MODIFIER) : "List";
        this.sourceCollection =
                toProperties.get(TYPE_MODIFIER) != null ? (String) toProperties.get(TYPE_MODIFIER) : "List";
        this.sourceToTargetNavigability =
                fromProperties.get(NAVIGABLE) != null && fromProperties.get(NAVIGABLE).equals("1");
        this.targetToSourceNavigability =
                toProperties.get(NAVIGABLE) != null && toProperties.get(NAVIGABLE).equals("1");

        Logger.log(Level.INFO, "RelationshipModel: " + this);

    }

    public static Map<String, Object> convertSemicolonStringToMap(String semicolonString) {
        Map<String, Object> map = new HashMap<>();
        String[] pairs = semicolonString.split(";");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=", 2);
            String key = keyValue[0].trim();
            Object value = keyValue.length > 1 ? keyValue[1] : null;
            map.put(key, value);
        }
        return map;
    }

    @Override
    public String toString() {
        return "RelationshipModel{" + "sourceToTargetCardinality='" + sourceToTargetCardinality + '\''
                + ", targetToSourceCardinality='" + targetToSourceCardinality + '\'' + ", sourceToTargetNavigability="
                + sourceToTargetNavigability + ", targetToSourceNavigability=" + targetToSourceNavigability
                + ", sourceTypeName='" + sourceTypeName + '\'' + ", targetTypeName='" + targetTypeName + '\''
                + ", sourceName='" + sourceName + '\'' + ", targetName='" + targetName + '\'' + ", targetCollection='"
                + targetCollection + '\'' + ", sourceCollection='" + sourceCollection + '\'' + '}';
    }

    private String getConstraintValue(String value) {
        // Remove everything before the equals sign in the string
        // Example: "not null = true" becomes "true"
        final int index = value.indexOf("=");
        if (index != -1) {
            value = value.substring(index + 1).trim();
        }
        return value;
    }

    public String getSourceToTargetCardinality() {
        return sourceToTargetCardinality;
    }

    public String getTargetToSourceCardinality() {
        return targetToSourceCardinality;
    }

    public boolean isSourceToTargetNavigability() {
        return sourceToTargetNavigability;
    }

    public boolean isTargetToSourceNavigability() {
        return targetToSourceNavigability;
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
}
