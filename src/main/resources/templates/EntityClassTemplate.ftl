<#import "utils/AttributeTemplate.ftl" as attr>
<#import "utils/BidirectionalMapping.ftl" as assoc>

package ${package.name};


import java.util.*;
import java.math.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;

<#--Start of class-->
/** TODO: generated by FrameWeb. Should be documented. */
@Entity
public class ${class.name} extends PersistentObjectSupport implements Comparable<${class.name}> {
/** Serialization id. */
private static final long serialVersionUID = 1L;

<#-- ATTRIBUTES FOR THE CLASS -->
<@attr.generate_attributes attributes=attributes/>



<#-- ASSOCIATIONS -->





<#--end of class-->
}
