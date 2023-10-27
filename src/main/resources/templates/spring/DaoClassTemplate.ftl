<#ftl strip_whitespace=true>
<#import "MethodOverrideTemplate.ftl" as method>
<#import "ClassGeneralizationTemplate.ftl" as generalization>
<#import "ClassRealizationTemplate.ftl" as realizations>
<#assign defaultGeneralization = "">
<#assign defaultRealization = "${class.name?replace('Impl', '')}">>
package ${package.name};


import org.springframework.stereotype.Repository;
import javax.persistence.PersistenceContext;

@Repository
public class ${class.name}
<@generalization.generate_generalization generalization=class.generalization defaultGeneralization=defaultGeneralization/> <@realizations.generate_realization realizations=class.realizations defaultRealization=defaultRealization/> {

@PersistenceContext
EntityManager entityManager;


<#-- METHODS -->
<@method.generate_methods methods=methods/>

}
