<#ftl strip_whitespace=true>
<#import "MethodOverrideTemplate.ftl" as method>
<#import "ClassGeneralizationTemplate.ftl" as generalization>
<#import "ClassRealizationTemplate.ftl" as realizations>
<#assign defaultGeneralization = "BaseJPADAO<${clazz.name?replace('DAOJPA', '')}>">
<#assign defaultRealization = "${clazz.name?replace('JPA', '')}">
package ${pack.name};

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseJPADAO;


/** TODO: generated by FrameWeb. Should be documented. */
@Stateless
public class ${clazz.name} <@generalization.generate_generalization generalization=clazz.generalization defaultGeneralization=defaultGeneralization/> <@realizations.generate_realization realizations=clazz.realizations defaultRealization=defaultRealization/> {

/** Serialization id (using default value, change if necessary). */
private static final long serialVersionUID = 1L;

/** TODO: generated by FrameWeb. Should be documented. */
@PersistenceContext
private EntityManager entityManager;

/** TODO: generated by FrameWeb. Should be documented. */
@Override
protected EntityManager getEntityManager() {
return entityManager;
}


<#-- METHODS -->
<@method.generate_methods methods=methods/>

}
