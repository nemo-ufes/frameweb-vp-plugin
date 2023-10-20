<#ftl strip_whitespace=true>
<#-- CLASS REALIZATION DEFINITION -->
<#macro generate_realization realizations defaultRealization>
    <#list realizations>
        implements
        <#items as realization>
            #{realization}<#sep>, </#sep>
        </#items>
    <#else>
        <#if defaultRealization?has_content>
            implements ${defaultRealization}
        </#if>
    </#list>
</#macro>
