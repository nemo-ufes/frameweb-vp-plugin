<#ftl strip_whitespace=true>
<#import "utils/AttributeTemplate.ftl" as attr>
<#import "utils/BidirectionalMapping.ftl" as assoc>


package ${package.name};


import java.util.*;
import java.math.*;
import javax.persistence.*;
import javax.validation.constraints.*;

<#--Start of class-->
/** TODO: generated by FrameWeb. Should be documented. */
@Entity
public class ${class.name} {
    /** Serialization id. */
    private static final long serialVersionUID = 1L;

<#-- ATTRIBUTES FOR THE CLASS -->
/** Attributes for the class. */
<@attr.generate_attributes attributes=attributes/>
<#-- ASSOCIATIONS -->
/** Associations for the class. */

<#--end of class-->
}
