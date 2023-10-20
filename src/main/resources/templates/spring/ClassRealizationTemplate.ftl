<#ftl strip_whitespace=true>
<#-- CLASS REALIZATION DEFINITION -->
<#macro generate_realization realizations defaultRealization>
    <#list realizations>
        implements
        <#items as realization>
            #{realization}<#sep>, </#sep>
        </#items>
        <#if defaultRealization?has_content>
            , ${defaultRealization}
        </#if>
    <#else>
        <#if defaultRealization?has_content>
            implements ${defaultRealization}
        </#if>
    </#list>
</#macro>
