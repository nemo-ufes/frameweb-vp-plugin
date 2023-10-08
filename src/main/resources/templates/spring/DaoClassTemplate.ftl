<#ftl strip_whitespace=true>
<#import "MethodOverrideTemplate.ftl" as method>
package ${package.name};


import org.springframework.stereotype.Repository;
import javax.persistence.PersistenceContext;

@Repository
public class ${class.name} <#if class.generalization?has_content>extends ${class.generalization}</#if> implements ${class.name?replace('Impl', '')} {

@PersistenceContext
EntityManager entityManager;


<#-- METHODS -->
<@method.generate_methods methods=methods/>

}
