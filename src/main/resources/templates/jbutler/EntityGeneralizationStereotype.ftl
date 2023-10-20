<#ftl strip_whitespace=true>
<#-- GENERALIZATION DEFINITION -->
<#macro generate_generalization_stereotype stereotype>
    <#if stereotype?has_content>
        @Inheritance(strategy = InheritanceType.
        <#if stereotype == 'join'>
        JOINED
        <#elseif stereotype == 'single-table'>
        SINGLE_TABLE
        <#elseif stereotype == 'union'>
        TABLE_PER_CLASS
        </#if>
        )
    <#else>
    <#-- No inheritance stereotype -->
    </#if>
</#macro>
