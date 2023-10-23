<#ftl strip_whitespace=true>
<#import "MethodOverrideTemplate.ftl" as method>
<#import "ClassGeneralizationTemplate.ftl" as generalization>
<#import "ClassRealizationTemplate.ftl" as realizations>
<#assign defaultGeneralization = "">
<#assign defaultRealization = "${clazz.name?replace('Impl', '')}">
package ${pack.name};


import org.springframework.stereotype.Repository;
import javax.persistence.PersistenceContext;

@Repository
public class ${clazz.name}
<@generalization.generate_generalization generalization=clazz.generalization defaultGeneralization=defaultGeneralization/> <@realizations.generate_realization realizations=clazz.realizations defaultRealization=defaultRealization/> {

@PersistenceContext
EntityManager entityManager;


<#-- METHODS -->
<@method.generate_methods methods=methods/>

}
