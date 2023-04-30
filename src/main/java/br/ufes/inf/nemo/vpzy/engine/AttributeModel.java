package br.ufes.inf.nemo.vpzy.engine;

import br.ufes.inf.nemo.frameweb.vp.model.FrameWebClassAttribute;
import br.ufes.inf.nemo.frameweb.vp.model.FrameWebClassAttributeConstraint;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import com.teamdev.jxbrowser.deps.org.checkerframework.checker.nullness.qual.NonNull;
import com.vp.plugin.model.IAttribute;
import com.vp.plugin.model.ICompositeValueSpecification;
import com.vp.plugin.model.IConstraintElement;
import com.vp.plugin.model.IStereotype;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;

public class AttributeModel {
    private final String name;

    private final String type;

    private final String visibility;

    private final boolean notNull;

    private final boolean isTransient;

    private final int size;

    private final String dateTimePrecision;

    private final boolean embedded;

    private final boolean lob;

    private final boolean id;

    private final boolean version;

    private final String column;

    public AttributeModel(@NonNull IAttribute attribute) {
        this.name = attribute.getName();
        this.type = attribute.getTypeAsString();
        this.visibility = attribute.getVisibility();

        // Create a map with the default values for the attribute stereotypes.
        final Map<String, Boolean> attributeStereotypes = new HashMap<>();
        attributeStereotypes.put(FrameWebClassAttribute.PERSISTENT_CLASS_EMBEDDED.getStereotypeName(), false);
        attributeStereotypes.put(FrameWebClassAttribute.PERSISTENT_CLASS_ID.getStereotypeName(), false);
        attributeStereotypes.put(FrameWebClassAttribute.PERSISTENT_CLASS_LOB.getStereotypeName(), false);
        attributeStereotypes.put(FrameWebClassAttribute.PERSISTENT_CLASS_VERSION.getStereotypeName(), false);

        @SuppressWarnings("unchecked") final Iterator<IStereotype> it = attribute.stereotypeModelIterator();

        // Update the map with the values from the stereotype array
        it.forEachRemaining(stereotype -> {
            Logger.log(Level.FINE, "Stereotype: " + stereotype.getName());
            attributeStereotypes.put(stereotype.getName(), true);
        });

        // Update the attribute values based on the map
        this.embedded = attributeStereotypes.get(FrameWebClassAttribute.PERSISTENT_CLASS_EMBEDDED.getStereotypeName());
        this.lob = attributeStereotypes.get(FrameWebClassAttribute.PERSISTENT_CLASS_LOB.getStereotypeName());
        this.id = attributeStereotypes.get(FrameWebClassAttribute.PERSISTENT_CLASS_ID.getStereotypeName());
        this.version = attributeStereotypes.get(FrameWebClassAttribute.PERSISTENT_CLASS_VERSION.getStereotypeName());

        // Create a map with the default values for the attribute stereotypes.
        final Map<String, Object> attributeConstraints = new HashMap<>();
        attributeConstraints.put(FrameWebClassAttributeConstraint.PERSISTENT_CLASS_SIZE.getPluginUIID(), "0");
        attributeConstraints.put(FrameWebClassAttributeConstraint.PERSISTENT_CLASS_PRECISION_TIME.getPluginUIID(),
                null);
        attributeConstraints.put(FrameWebClassAttributeConstraint.PERSISTENT_CLASS_COLUMN.getPluginUIID(), this.name);

        @SuppressWarnings("unchecked") final Iterator<IConstraintElement> iterator = attribute.constraintsIterator();

        Logger.log(Level.INFO, "Attribute: " + attribute.getName());
        iterator.forEachRemaining(constraint -> {
            final ICompositeValueSpecification specification = constraint.getSpecification();
            String value = specification.getValue().toString();
            // Remove everything before the equals sign in the string
            // Example: "not null = true" becomes "true"
            final int index = value.indexOf("=");
            if (index != -1) {
                value = value.substring(index + 1).trim();
            }
            Logger.log(Level.INFO, "Constraint: " + constraint.getName() + ", specification: " + value);

            attributeConstraints.put(constraint.getName(), value);

        });

        this.notNull = attributeConstraints.containsKey(
                FrameWebClassAttributeConstraint.PERSISTENT_CLASS_NOT_NULL.getPluginUIID());
        this.size = Integer.parseInt((String) attributeConstraints.get(
                FrameWebClassAttributeConstraint.PERSISTENT_CLASS_SIZE.getPluginUIID()));
        this.dateTimePrecision = ((String) attributeConstraints.get(
                FrameWebClassAttributeConstraint.PERSISTENT_CLASS_PRECISION_TIME.getPluginUIID()));
        this.isTransient = attributeConstraints.containsKey("transient");
        this.column = (String) attributeConstraints.get(
                FrameWebClassAttributeConstraint.PERSISTENT_CLASS_COLUMN.getPluginUIID());

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

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getVisibility() {
        return visibility;
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
