<#macro generate_associations associations className>

    <#list associations as association>
        <#assign sourceToTargetCardinality = association.sourceToTargetCardinality>
        <#assign targetToSourceCardinality = association.targetToSourceCardinality>
        <#assign sourceTypeName = association.sourceTypeName>
        <#assign targetTypeName = association.targetTypeName>
        <#assign sourceName = association.sourceName>
        <#assign targetName = association.targetName>
        <#assign targetCollection = association.targetCollection?cap_first>
        <#assign sourceCollection = association.sourceCollection?cap_first>
        <#assign sourceToTargetNavigability = association.sourceToTargetNavigability>
        <#assign targetToSourceNavigability = association.targetToSourceNavigability>
        <#if sourceTypeName == className && targetTypeName == className && sourceToTargetNavigability>
            /** Self association */
            <#if sourceToTargetCardinality == "OneToMany" || sourceToTargetCardinality == "ManyToOne">
                @ManyToOne
                public ${sourceTypeName} ${sourceName};
                @OneToMany(mappedBy="${sourceName}")
                public ${targetCollection}<${targetTypeName}> ${targetName};
            <#elseif sourceToTargetCardinality == "OneToOne">
                @OneToOne
                private ${sourceTypeName} ${sourceName};
                @OneToOne(mappedBy="${sourceName}")
                private ${targetTypeName} ${targetName};
            <#elseif sourceToTargetCardinality == "ManyToMany">
                @ManyToMany
                private ${targetCollection}<${sourceTypeName}> ${sourceName};
                @ManyToMany(mappedBy="${sourceName}")
                private ${targetCollection}<${targetTypeName}> ${targetName};
            </#if>
        <#elseif sourceTypeName == className && sourceToTargetNavigability>
            /** ${sourceTypeName} to ${targetTypeName} */
            <#if sourceToTargetCardinality == "OneToMany" >
                @OneToMany
                public ${targetCollection}<${targetTypeName}> ${targetName};
            <#elseif sourceToTargetCardinality == "OneToOne">
                @OneToOne
                private ${targetTypeName} ${targetName};
            <#elseif sourceToTargetCardinality == "ManyToMany">
                @ManyToMany
                private ${targetCollection}<${targetTypeName}> ${targetName};
            </#if>
        <#elseif targetTypeName == className && targetToSourceNavigability>
            /** ${targetTypeName} to ${sourceTypeName} */
            <#if targetToSourceCardinality == "OneToMany" >
                @OneToMany(mappedBy="${sourceName}")
                public ${sourceCollection}<${sourceTypeName}> ${targetName};
            <#elseif targetToSourceCardinality == "OneToOne">
                @OneToOne(mappedBy="${sourceName}")
                private ${sourceTypeName} ${targetName};
            <#elseif targetToSourceCardinality == "ManyToMany">
                @ManyToMany(mappedBy="${sourceName}")
                private ${sourceCollection}<${sourceTypeName}> ${targetName};
            </#if>
        </#if>
    </#list>

</#macro>

