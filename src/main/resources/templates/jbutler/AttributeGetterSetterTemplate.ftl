<#ftl strip_whitespace=true>
<#-- GETTERS AND SETTERS FOR THE ATTRIBUTES -->
<#macro generate_getter_setter_attributes attributes>
    <#list attributes as attribute>
        /** Getter for ${attribute.name}. */
        public ${attribute.type} get${attribute.name?cap_first}() {
        return ${attribute.name};
        }

        /** Setter for ${attribute.name}. */
        public void set${attribute.name?cap_first}(final ${attribute.type} ${attribute.name}) {
        this.${attribute.name} = ${attribute.name};
        }
    </#list>
</#macro>
