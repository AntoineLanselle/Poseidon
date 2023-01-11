package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * Entity CurvePoint.
 * 
 * @author Antoine Lanselle
 */
@Entity
@Table(name = "curvepoint")
public class CurvePoint {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="curve_id")
	@NotNull(message = "Must not be null.")
	@Digits(message="Enter an integer number.", fraction = 0, integer = 255)
	private Integer curveId;
	
	@Column(name="term")
	@Digits(message="Enter a number in xx.x format.", fraction = 1, integer = 255)
	private Double term;
	
	@Column(name="value")
	@Digits(message="Enter a number in xx.x format.", fraction = 1, integer = 255)
	private Double value;
	
	@Column(name="as_of_date")
	private Timestamp asOfDate;
	
	@Column(name="creation_date")
	private Timestamp creationDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCurveId() {
		return curveId;
	}

	public void setCurveId(Integer curveId) {
		this.curveId = curveId;
	}

	public Timestamp getAsOfDate() {
		return asOfDate;
	}

	public void setAsOfDate(Timestamp asOfDate) {
		this.asOfDate = asOfDate;
	}

	public Double getTerm() {
		return term;
	}

	public void setTerm(Double term) {
		this.term = term;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

}
