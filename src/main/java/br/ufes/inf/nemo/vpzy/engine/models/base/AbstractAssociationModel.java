package br.ufes.inf.nemo.vpzy.engine.models.base;

import com.teamdev.jxbrowser.deps.org.checkerframework.checker.nullness.qual.NonNull;
import com.vp.plugin.model.IAssociationEnd;
import com.vp.plugin.model.IRelationshipEnd;
import org.apache.commons.lang.StringUtils;

/**
 * Contains the common attributes for the association models.
 *
 * @author <a href="https://github.com/igorssilva">Igor Sunderhus e Silva</a>
 * @version 0.0.1
 */
public abstract class AbstractAssociationModel {
    /**
     * The type for the source of the relationship.
     */
    private final String sourceTypeName;

    /**
     * The type for the target of the relationship.
     */
    private final String targetTypeName;

    /**
     * The property name for the source of the relationship.
     * If the property name is not defined, the name of the class is used.
     */
    private final String sourceName;

    /**
     * The property name for the target of the relationship.
     * If the property name is not defined, the name of the class is used.
     */
    private final String targetName;

    /**
     * Extracts the {@link IAssociationEnd} information for the {@link IRelationshipEnd} model.
     *
     * @param source The source of the relationship.
     */
    protected AbstractAssociationModel(@NonNull final IRelationshipEnd source) {

        final IRelationshipEnd target = source.getOppositeEnd();

        final IAssociationEnd fromAssociation = (IAssociationEnd) source;

        final IAssociationEnd toAssociation = (IAssociationEnd) target;

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
}
