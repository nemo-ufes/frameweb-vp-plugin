<#ftl strip_whitespace=true>
<#import "MethodInterfaceTemplate.ftl" as method>
<#import "InterfaceGeneralizationTemplate.ftl" as generalization>
<#assign defaultGeneralization = "JpaRepository<${class.name?replace('Repository', '')}>">
package ${package.name};

import org.springframework.data.jpa.repository.JpaRepository;

/** TODO: generated by FrameWeb. Should be documented. */
public interface ${class.name} <@generalization.generate_generalization generalization=class.generalization defaultGeneralization=defaultGeneralization/> {


<#-- METHODS -->
<@method.generate_methods methods=methods/>

}