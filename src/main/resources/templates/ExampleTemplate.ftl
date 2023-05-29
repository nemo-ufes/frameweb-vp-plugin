package ${packageName};

import javax.persistence.*;

@Entity
@Table(name="${tableName}")
public class ${className} {

@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private Long id;

<#list fields as field>
	@${field.annotation}
	private ${field.type} ${field.name};
</#list>

// Constructors

public ${className}() {}

// Accessors

public Long getId() {
return id;
}

<#list fields as field>
	public ${field.type} get${field.upperName}() {
	return ${field.name};
	}

	public void set${field.upperName}(${field.type} ${field.name}) {
	this.${field.name} = ${field.name};
	}

</#list>

// Overrides

@Override
public String toString() {
return "${className}[id=" + id + <#list fields as field> ", ${field.name}=" + ${field.name} </#list> + "]";
}
}
