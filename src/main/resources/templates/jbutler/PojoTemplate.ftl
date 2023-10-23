<#ftl strip_whitespace=true>
<#import "AttributeTemplate.ftl" as attr>
<#import "AttributeGetterSetterTemplate.ftl" as attrgs>
<#import "AssociationTemplate.ftl" as assoc>
<#import "AssociationGetterSetterTemplate.ftl" as assocgs>
<#import "MethodTemplate.ftl" as method>


package ${pack.name};


import java.util.*;
import java.math.*;
import javax.validation.constraints.*;

<#--Start of class-->
/** TODO: generated by FrameWeb. Should be documented. */
public class ${clazz.name} {
/** Serialization id. */
private static final long serialVersionUID = 1L;

<#-- ASSOCIATIONS -->
<@assoc.generate_associations associations=associations className=clazz.name/>

<#-- ATTRIBUTES -->
<@attr.generate_attributes attributes=attributes/>


<#-- METHODS -->
<@method.generate_methods methods=methods/>

// region Boilerplate Code
<#-- GETTERS AND SETTERS ASSOCIATIONS -->
<@assocgs.generate_getter_setter_associations associations=associations className=clazz.name/>

<#-- GETTERS AND SETTERS ATTRIBUTES -->
<@attrgs.generate_getter_setter_attributes attributes=attributes/>

// endregion

<#--end of class-->
}
