package br.ufes.inf.nemo.vpzy.engine.models.entity;

import br.ufes.inf.nemo.frameweb.vp.model.FrameWebClassAttribute;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebClassAttributeConstraint;
import br.ufes.inf.nemo.vpzy.engine.models.base.AbstractAttributeModel;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import com.teamdev.jxbrowser.deps.org.checkerframework.checker.nullness.qual.NonNull;
import com.vp.plugin.model.IAttribute;
import com.vp.plugin.model.ICompositeValueSpecification;
import com.vp.plugin.model.IConstraintElement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;

/**
 * Attribute model for Entity classes.
 *
 * @author <a href="https://github.com/igorssilva">Igor Sunderhus e Silva</a>
 * @version 0.0.1
 */
public class EntityAttributeModel extends AbstractAttributeModel {
    /**
     * If the attribute is not null.
     */
    private final boolean notNull;

    /**
     * If the attribute is transient.
     */
    private final boolean isTransient;

    /**
     * The size of the attribute.
     */
    private final int size;

    /**
     * Attribute's date precision.
     * Possible values: date, time, timestamp.
     */
    private final String dateTimePrecision;

    /**
     * If the attribute is embedded.
     */
    private final boolean embedded;

    /**
     * If the attribute is a lob.
     */
    private final boolean lob;

    /**
     * If the attribute is the id attribute of an entity
     */
    private final boolean id;

    /**
     * If the attribute is the version attribute of an entity
     */
    private final boolean version;

    /**
     * The column name of the attribute.
     */
    private final String column;

    public EntityAttributeModel(@NonNull IAttribute attribute) {
        super(attribute);

        // Update the attribute values based on the map
        this.embedded = attribute.hasStereotype(FrameWebClassAttribute.PERSISTENT_CLASS_EMBEDDED.getStereotypeName());
        this.lob = attribute.hasStereotype(FrameWebClassAttribute.PERSISTENT_CLASS_LOB.getStereotypeName());
        this.id = attribute.hasStereotype(FrameWebClassAttribute.PERSISTENT_CLASS_ID.getStereotypeName());
        this.version = attribute.hasStereotype(FrameWebClassAttribute.PERSISTENT_CLASS_VERSION.getStereotypeName());
        this.isTransient = attribute.hasStereotype(FrameWebClassAttribute.PERSISTENT_CLASS_TRANSIENT.getStereotypeName()) || attribute.hasStereotype("transient");

        // Create a map with the default values for the attribute stereotypes.
        final Map<String, Object> attributeConstraints = generateConstraintsMap(attribute);

        this.notNull = attributeConstraints.containsKey(
                FrameWebClassAttributeConstraint.PERSISTENT_CLASS_NOT_NULL.getPluginUIID());
        this.size = Integer.parseInt((String) attributeConstraints.get(
                FrameWebClassAttributeConstraint.PERSISTENT_CLASS_SIZE.getPluginUIID()));
        this.dateTimePrecision = ((String) attributeConstraints.get(
                FrameWebClassAttributeConstraint.PERSISTENT_CLASS_PRECISION_TIME.getPluginUIID()));
        this.column = (String) attributeConstraints.get(
                FrameWebClassAttributeConstraint.PERSISTENT_CLASS_COLUMN.getPluginUIID());

    }

    /**
     * Map with all the constraints of the attribute. It uses the default values for the constraints in
     * {@link FrameWebClassAttributeConstraint}. The values then get updated based on the attribute constraints.
     *
     * @param attribute The attribute to analyze.
     * @return A map containing all the constraints of the attribute.
     */
    private Map<String, Object> generateConstraintsMap(final IAttribute attribute) {
        // Create a map with the default values for the attribute stereotypes.
        final Map<String, Object> attributeConstraints = new HashMap<>();
        attributeConstraints.put(FrameWebClassAttributeConstraint.PERSISTENT_CLASS_SIZE.getPluginUIID(), "0");
        attributeConstraints.put(FrameWebClassAttributeConstraint.PERSISTENT_CLASS_PRECISION_TIME.getPluginUIID(),
                null);
        attributeConstraints.put(FrameWebClassAttributeConstraint.PERSISTENT_CLASS_COLUMN.getPluginUIID(), "");

        @SuppressWarnings("unchecked") final Iterator<IConstraintElement> iterator = attribute.constraintsIterator();

        Logger.log(Level.FINE, "Attribute: " + attribute.getName());
        iterator.forEachRemaining(constraint -> {
            final ICompositeValueSpecification specification = constraint.getSpecification();
            String value = getConstraintValue(specification.getValue().toString());
            Logger.log(Level.FINE, "Constraint: " + constraint.getName() + ", specification: " + value);

            attributeConstraints.put(constraint.getName(), value);

        });
        return attributeConstraints;
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

    public boolean isEmbedded() {
        return embedded;
    }

    public boolean isLob() {
        return lob;
    }

    public boolean isId() {
        return id;
    }

    public boolean isVersion() {
        return version;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public int getSize() {
        return size;
    }

    public String getDateTimePrecision() {
        return dateTimePrecision;
    }

    public boolean isTransient() {
        return isTransient;
    }

    public String getColumn() {
        return column;
    }
}
