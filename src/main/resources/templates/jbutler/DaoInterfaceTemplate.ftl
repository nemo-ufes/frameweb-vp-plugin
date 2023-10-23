<#ftl strip_whitespace=true>
<#import "MethodInterfaceTemplate.ftl" as method>
<#import "InterfaceGeneralizationTemplate.ftl" as generalization>
<#assign defaultGeneralization = "BaseDAO<${clazz.name?replace('DAO', '')}>">
package ${pack.name};

import javax.ejb.Local;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;

/** TODO: generated by FrameWeb. Should be documented. */
@Local
public interface ${clazz.name} <@generalization.generate_generalization generalization=clazz.generalization defaultGeneralization=defaultGeneralization/> {
<#-- METHODS -->
<@method.generate_methods methods=methods/>
}
