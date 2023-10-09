<#macro generate_methods methods>
    <#list methods as method>
        @Override
        ${method.visibility} <#if method.type?has_content>${method.type}<#else>void</#if>  ${method.name}(
        <#list method.parameters as parameter>
            final <#if parameter.type?has_content>${parameter.type}</#if>  <#if parameter.name?has_content>${parameter.name}</#if><#if parameter_has_next>, </#if>
        </#list>) {
        return <#if method.type?has_content>null</#if>;
        }
    </#list>
</#macro>
