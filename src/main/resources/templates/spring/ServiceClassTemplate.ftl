<#ftl strip_whitespace=true>
<#import "ServiceAttributeTemplate.ftl" as attr>
<#import "AttributeGetterSetterTemplate.ftl" as attrgs>
<#import "MethodOverrideTemplate.ftl" as method>
<#import "ClassGeneralizationTemplate.ftl" as generalization>
<#import "ClassRealizationTemplate.ftl" as realizations>
<#import "ServiceAssociationTemplate.ftl" as assoc>
<#import "ServiceAssociationMethodsTemplate.ftl" as assocmethods>
<#assign defaultGeneralization = "">
<#assign defaultRealization = "${clazz.name?replace('Impl', '')}">
package ${pack.name};


import org.springframework.stereotype.Service;

@Service
public class ${clazz.name}
<@generalization.generate_generalization generalization=clazz.generalization defaultGeneralization=defaultGeneralization/> <@realizations.generate_realization realizations=clazz.realizations defaultRealization=defaultRealization/> {

<#-- ASSOCIATIONS -->
<@assoc.generate_associations associations=associations/>

<#-- ATTRIBUTES -->
<@attr.generate_attributes attributes=attributes/>

<#-- METHODS -->
<@method.generate_methods methods=methods/>


<#-- GETTERS AND SETTERS ATTRIBUTES -->
<@attrgs.generate_getter_setter_attributes attributes=attributes/>

<#-- ASSOCIATIONS METHODS -->
<@assocmethods.generate_associations_methods associations=associations/>


}
