<#ftl strip_whitespace=true>
<#-- INTERFACE GENERALIZATION DEFINITION -->
<#macro generate_generalization generalization defaultGeneralization>
    <#if generalization?has_content>
        extends ${generalization}
    <#elseif defaultGeneralization?has_content>
        extends ${defaultGeneralization}
    </#if>
</#macro>
