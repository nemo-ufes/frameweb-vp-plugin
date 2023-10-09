<#ftl strip_whitespace=true>
<#import "MethodOverrideTemplate.ftl" as method>
package ${package.name};

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseJPADAO;


/** TODO: generated by FrameWeb. Should be documented. */
@Stateless
public class ${class.name} extends BaseJPADAO<${class.name?replace('DAOJPA', '')}> implements ${class.name?replace('JPA', '')} {
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
