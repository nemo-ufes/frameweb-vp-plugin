<#macro generate_associations_methods associations>
    <#list associations as association>
        <#assign targetTypeName = association.targetTypeName>
        <#assign targetName = association.targetName>
        <#assign domainClassName = targetTypeName?replace('Repository', '') >
        <#assign domainClassNameCamelCase = domainClassName?uncap_first>

        @Override
        public Optional<${domainClassName}> find${domainClassName}ById(Long id) {
        return ${targetName}.findById(id);
        }

        @Override
        public List<${domainClassName}> findAll${domainClassName}s() {
        return ${targetName}.findAll();
        }

        @Override
        public ${domainClassName} save${domainClassName}(${domainClassName} ${domainClassNameCamelCase}) {
        return ${targetName}.save(${domainClassNameCamelCase});
        }

        @Override
        public void delete${domainClassName}(${domainClassName} ${domainClassNameCamelCase}) {
        ${targetName}.delete(${domainClassNameCamelCase});
        }
    </#list>
</#macro>
