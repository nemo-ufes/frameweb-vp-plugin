<#macro generate_associations associations>
    <#list associations as association>
        <#assign sourceTypeName = association.sourceTypeName>
        <#assign targetTypeName = association.targetTypeName>
        <#assign sourceName = association.sourceName>
        <#assign targetName = association.targetName>
        @Autowired
        private ${targetTypeName} ${targetName};
    </#list>
</#macro>
