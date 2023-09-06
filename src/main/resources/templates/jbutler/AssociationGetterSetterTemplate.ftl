<#ftl strip_whitespace=true>
<#-- GETTERS AND SETTERS FOR THE ASSOCIATIONS -->
<#macro generate_getter_setter_associations associations className>

    <#list associations as association>
        <#assign sourceToTargetCardinality = association.sourceToTargetCardinality>
        <#assign targetToSourceCardinality = association.targetToSourceCardinality>
        <#assign sourceTypeName = association.sourceTypeName>
        <#assign targetTypeName = association.targetTypeName>
        <#assign sourceName = association.sourceName>
        <#assign targetName = association.targetName>
        <#assign targetCollection = association.targetCollection?cap_first>
        <#assign sourceCollection = association.sourceCollection?cap_first>
        <#assign targetCascade = association.targetCascade?upper_case>
        <#assign sourceCascade = association.sourceCascade?upper_case>
        <#assign targetFetch = association.targetFetch?upper_case>
        <#assign sourceFetch = association.sourceFetch?upper_case>
        <#assign sourceTransient = association.sourceTransient>
        <#assign targetTransient = association.targetTransient>

        <#if sourceTypeName == className && targetTypeName == className>
            <#if sourceToTargetCardinality == "OneToMany" || sourceToTargetCardinality == "ManyToOne">
                /** Getter for ${sourceName}. */
                public ${sourceTypeName} get${sourceName?cap_first}() {
                return ${sourceName};
                }

                /** Setter for ${sourceName}. */
                public void set${sourceName?cap_first}(final ${sourceTypeName} ${sourceName}) {
                this.${sourceName} = ${sourceName};
                }

                /** Getter for ${targetName}. */
                public ${targetCollection}<${targetTypeName}> get${targetName?cap_first}() {
                return ${targetName};
                }

                /** Setter for ${targetName}. */
                public void set${targetName?cap_first}(final ${targetCollection}<${targetTypeName}> ${targetName}) {
                this.${targetName} = ${targetName};
                }
            <#elseif sourceToTargetCardinality == "OneToOne">
                /** Getter for ${sourceName}. */
                public ${sourceTypeName} get${sourceName?cap_first}() {
                return ${sourceName};
                }

                /** Setter for ${sourceName}. */
                public void set${sourceName?cap_first}(final ${sourceTypeName} ${sourceName}) {
                this.${sourceName} = ${sourceName};
                }

                /** Getter for ${targetName}. */
                public ${targetTypeName} get${targetName?cap_first}() {
                return ${targetName};
                }

                /** Setter for ${targetName}. */
                public void set${targetName?cap_first}(final ${targetTypeName} ${targetName}) {
                this.${targetName} = ${targetName};
                }
            <#elseif sourceToTargetCardinality == "ManyToMany">
                /** Getter for ${sourceName}. */
                public ${sourceCollection}<${sourceTypeName}> get${sourceName?cap_first}() {
                return ${sourceName};
                }

                /** Setter for ${sourceName}. */
                public void set${sourceName?cap_first}(final ${sourceCollection}<${sourceTypeName}> ${sourceName}) {
                this.${sourceName} = ${sourceName};
                }

                /** Getter for ${targetName}. */
                public ${targetCollection}<${targetTypeName}> get${targetName?cap_first}() {
                return ${targetName};
                }

                /** Setter for ${targetName}. */
                public void set${targetName?cap_first}(final ${targetCollection}<${targetTypeName}> ${targetName}) {
                this.${targetName} = ${targetName};
                }
            </#if>
        <#elseif sourceTypeName == className>
            <#if sourceToTargetCardinality == "OneToMany" || sourceToTargetCardinality == "ManyToMany">
                /** Getter for ${targetName}. */
                public ${targetCollection}<${targetTypeName}> get${targetName?cap_first}() {
                return ${targetName};
                }

                /** Setter for ${targetName}. */
                public void set${targetName?cap_first}(final ${targetCollection}<${targetTypeName}> ${targetName}) {
                this.${targetName} = ${targetName};
                }
            <#elseif sourceToTargetCardinality == "ManyToOne"  || sourceToTargetCardinality == "OneToOne">
                /** Getter for ${targetName}. */
                public ${targetTypeName} get${targetName?cap_first}() {
                return ${targetName};
                }

                /** Setter for ${targetName}. */
                public void set${targetName?cap_first}(final ${targetTypeName} ${targetName}) {
                this.${targetName} = ${targetName};
                }
            </#if>
        <#elseif targetTypeName == className>
            <#if targetToSourceCardinality == "OneToMany" || targetToSourceCardinality == "ManyToMany">
                /** Getter for ${sourceName}. */
                public ${sourceCollection}<${sourceTypeName}> get${sourceName?cap_first}() {
                return ${sourceName};
                }

                /** Setter for ${sourceName}. */
                public void set${sourceName?cap_first}(final ${sourceCollection}<${sourceTypeName}> ${sourceName}) {
                this.${sourceName} = ${sourceName};
                }
            <#elseif targetToSourceCardinality == "ManyToOne" || targetToSourceCardinality == "OneToOne">
                /** Getter for ${sourceName}. */
                public ${sourceTypeName} get${sourceName?cap_first}() {
                return ${sourceName};
                }

                /** Setter for ${sourceName}. */
                public void set${sourceName?cap_first}(final ${sourceTypeName} ${sourceName}) {
                this.${sourceName} = ${sourceName};
                }
            </#if>
        </#if>
    </#list>


</#macro>
