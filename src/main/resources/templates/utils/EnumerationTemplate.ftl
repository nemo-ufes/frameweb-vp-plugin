<#ftl strip_whitespace=true>
package ${package.name};

/** TODO: generated by FrameWeb. Should be documented. */
public enum ${class.name} {
<#list attributes as attribute>
${attribute.name}<#if attribute_has_next>,</#if>
</#list>;
}