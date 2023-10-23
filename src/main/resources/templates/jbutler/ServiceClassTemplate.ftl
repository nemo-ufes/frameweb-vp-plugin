<#ftl strip_whitespace=true>
<#import "ServiceAttributeTemplate.ftl" as attr>
<#import "AttributeGetterSetterTemplate.ftl" as attrgs>
<#import "MethodOverrideTemplate.ftl" as method>
<#import "ClassGeneralizationTemplate.ftl" as generalization>
<#import "ClassRealizationTemplate.ftl" as realizations>
<#import "ServiceAssociationTemplate.ftl" as assoc>
<#assign defaultGeneralization = "">
<#assign defaultRealization = "${class.name?replace('Impl|Bean', '', 'r')}">
package ${package.name};


import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class ${class.name}
<@generalization.generate_generalization generalization=class.generalization defaultGeneralization=defaultGeneralization/> <@realizations.generate_realization realizations=class.realizations defaultRealization=defaultRealization/> {

<#-- ASSOCIATIONS -->
<@assoc.generate_associations associations=associations/>

<#-- ATTRIBUTES -->
<@attr.generate_attributes attributes=attributes/>

<#-- METHODS -->
<@method.generate_methods methods=methods/>


<#-- GETTERS AND SETTERS ATTRIBUTES -->
<@attrgs.generate_getter_setter_attributes attributes=attributes/>


}
