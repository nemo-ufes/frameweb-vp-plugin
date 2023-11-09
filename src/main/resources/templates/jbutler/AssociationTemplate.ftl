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
        <#assign targetCascade = association.targetCascade?upper_case>
        <#assign sourceCascade = association.sourceCascade?upper_case>
        <#assign targetFetch = association.targetFetch?upper_case>
        <#assign sourceFetch = association.sourceFetch?upper_case>
        <#assign sourceTransient = association.sourceTransient>
        <#assign targetTransient = association.targetTransient>
        <#assign sourceOrder = association.sourceOrder>
        <#assign targetOrder = association.targetOrder>

        <#if sourceTypeName == className && targetTypeName == className>
            /** Self association */
            <#if sourceToTargetCardinality == "OneToMany" || sourceToTargetCardinality == "ManyToOne">
                <#if sourceTransient>
                    @Transient
                <#else>
                    @ManyToOne
                    public ${sourceTypeName} ${sourceName};
                    @OneToMany(mappedBy="${sourceName}")
                    <#if sourceOrder?has_content>
                        @OrderBy("${sourceOrder}")
                    </#if>
                </#if>
                public ${targetCollection}<${targetTypeName}> ${targetName};
            <#elseif sourceToTargetCardinality == "OneToOne">
                <#if sourceTransient>
                    @Transient
                <#else>
                    @OneToOne
                </#if>
                private ${sourceTypeName} ${sourceName};
                <#if targetTransient>
                    @Transient
                <#else>
                    @OneToOne(mappedBy="${sourceName}")
                </#if>
                private ${targetTypeName} ${targetName};
            <#elseif sourceToTargetCardinality == "ManyToMany">
                <#if sourceTransient>
                    @Transient
                <#else>
                    @ManyToMany
                    <#if sourceOrder?has_content>
                        @OrderBy("${sourceOrder}")
                    </#if>
                </#if>
                private ${sourceCollection}<${sourceTypeName}> ${sourceName};
                <#if targetTransient>
                    @Transient
                <#else>
                    @ManyToMany(mappedBy="${sourceName}")
                    <#if targetOrder?has_content>
                        @OrderBy("${targetOrder}")
                    </#if>
                </#if>
                private ${targetCollection}<${targetTypeName}> ${targetName};
            </#if>
        <#elseif sourceTypeName == className>
            /** ${sourceTypeName} to ${targetTypeName} */
            <#if sourceToTargetCardinality == "OneToMany">
                <#if targetTransient >
                    @Transient
                <#else>
                    @OneToMany(fetch = FetchType.${targetFetch}, cascade = CascadeType.${targetCascade})
                </#if>
                <#if targetOrder?has_content>
                    @OrderBy("${targetOrder}")
                </#if>
                public ${targetCollection}<${targetTypeName}> ${targetName};
            <#elseif sourceToTargetCardinality == "ManyToOne">
                <#if targetTransient >
                    @Transient
                <#else>
                    @ManyToOne(fetch = FetchType.${targetFetch}, cascade = CascadeType.${targetCascade})
                </#if>
                public ${targetTypeName} ${targetName};
            <#elseif sourceToTargetCardinality == "OneToOne">
                <#if targetTransient >
                    @Transient
                <#else>
                    @OneToOne(fetch = FetchType.${targetFetch}, cascade = CascadeType.${targetCascade})
                </#if>
                private ${targetTypeName} ${targetName};
            <#elseif sourceToTargetCardinality == "ManyToMany">
                <#if targetTransient >
                    @Transient
                <#else>
                    @ManyToMany(fetch = FetchType.${targetFetch}, cascade = CascadeType.${targetCascade})
                </#if>
                <#if targetOrder?has_content>
                    @OrderBy("${targetOrder}")
                </#if>
                private ${targetCollection}<${targetTypeName}> ${targetName};
            </#if>
        <#elseif targetTypeName == className>
            /** ${targetTypeName} to ${sourceTypeName} */
            <#if targetToSourceCardinality == "OneToMany">
                <#if sourceTransient>
                    @Transient
                <#else>
                    @OneToMany(mappedBy="${sourceName}", fetch = FetchType.${sourceFetch}, cascade = CascadeType.${sourceCascade})
                </#if>
                <#if sourceOrder?has_content>
                    @OrderBy("${sourceOrder}")
                </#if>
                public ${sourceCollection}<${sourceTypeName}> ${targetName};
            <#elseif targetToSourceCardinality == "ManyToOne">
                <#if sourceTransient >
                    @Transient
                <#else>
                    @ManyToOne(mappedBy="${sourceName}", fetch = FetchType.${sourceFetch}, cascade = CascadeType.${sourceCascade})
                </#if>
                private ${sourceTypeName} ${targetName};
            <#elseif targetToSourceCardinality == "OneToOne">
                <#if sourceTransient >
                    @Transient
                <#else>
                    @OneToOne(mappedBy="${sourceName}", fetch = FetchType.${sourceFetch}, cascade = CascadeType.${sourceCascade})
                </#if>
                private ${sourceTypeName} ${targetName};
            <#elseif targetToSourceCardinality == "ManyToMany">
                <#if sourceTransient >
                    @Transient
                <#else>
                    @ManyToMany(mappedBy="${sourceName}", fetch = FetchType.${sourceFetch}, cascade = CascadeType.${sourceCascade})
                </#if>
                <#if sourceOrder?has_content>
                    @OrderBy("${sourceOrder}")
                </#if>
                private ${sourceCollection}<${sourceTypeName}> ${targetName};
            </#if>
        </#if>
    </#list>
</#macro>
