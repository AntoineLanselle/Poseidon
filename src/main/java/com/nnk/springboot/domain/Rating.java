package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * Entity Rating.
 * 
 * @author Antoine Lanselle
 */
@Entity
@Table(name = "rating")
public class Rating {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="moodys_rating")
	@NotBlank(message = "Moodys Rating is mandatory.")
	private String moodysRating;
	
	@Column(name="sand_p_rating")
	@NotBlank(message = "Sand P Rating is mandatory.")
	private String sandPRating;
	
	@Column(name="fitch_rating")
	@NotBlank(message = "Fitch Rating is mandatory.")
	private String fitchRating;
	
	@Column(name="order_number")
	@NotNull(message = "Must not be null.")
	@Digits(message="Enter an integer number.", fraction = 0, integer = 255)
	private Integer orderNumber;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMoodysRating() {
		return moodysRating;
	}

	public void setMoodysRating(String moodysRating) {
		this.moodysRating = moodysRating;
	}

	public String getSandPRating() {
		return sandPRating;
	}

	public void setSandPRating(String sandPRating) {
		this.sandPRating = sandPRating;
	}

	public String getFitchRating() {
		return fitchRating;
	}

	public void setFitchRating(String fitchRating) {
		this.fitchRating = fitchRating;
	}

	public Integer getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

}
