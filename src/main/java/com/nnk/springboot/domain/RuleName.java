package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

/**
 * Entity RuleName.
 * 
 * @author Antoine Lanselle
 */
@Entity
@Table(name = "rulename")
public class RuleName {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="name")
	@NotBlank(message = "Name is mandatory.")
	private String name;
	
	@Column(name="description")
	@NotBlank(message = "Description is mandatory.")
	private String description;
	
	@Column(name="json")
	@NotBlank(message = "Json is mandatory.")
	private String json;
	
	@Column(name="template")
	@NotBlank(message = "Template is mandatory.")
	private String template;
	
	@Column(name="sql_str")
	@NotBlank(message = "Sql is mandatory.")
	private String sqlStr;
	
	@Column(name="sql_part")
	@NotBlank(message = "Sql Part is mandatory.")
	private String sqlPart;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getSqlStr() {
		return sqlStr;
	}

	public void setSqlStr(String sqlStr) {
		this.sqlStr = sqlStr;
	}

	public String getSqlPart() {
		return sqlPart;
	}

	public void setSqlPart(String sqlPart) {
		this.sqlPart = sqlPart;
	}

}
