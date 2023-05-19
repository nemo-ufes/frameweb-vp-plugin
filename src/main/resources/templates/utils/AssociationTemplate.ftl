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

        <#if sourceTypeName == className && targetTypeName == className>
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
        <#elseif sourceTypeName == className>
            /** ${sourceTypeName} to ${targetTypeName} */
            <#if sourceToTargetCardinality == "OneToMany" >
                <#if targetTransient >
                    @Transient
                <#else>
                    @OneToMany(fetch = FetchType.${targetFetch}, cascade = CascadeType.${targetCascade})
                </#if>
                public ${targetCollection}<${targetTypeName}> ${targetName};
            <#elseif sourceToTargetCardinality == "ManyToOne" >
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
                private ${targetCollection}<${targetTypeName}> ${targetName};
            </#if>
        <#elseif targetTypeName == className>
            /** ${targetTypeName} to ${sourceTypeName} */
            <#if targetToSourceCardinality == "OneToMany" >

                <#if sourceTransient >
                    @Transient
                <#else>
                    @OneToMany(mappedBy="${sourceName}", fetch = FetchType.${sourceFetch}, cascade = CascadeType.${sourceCascade})
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
                private ${sourceCollection}<${sourceTypeName}> ${targetName};
            </#if>
        </#if>
    </#list>

</#macro>

