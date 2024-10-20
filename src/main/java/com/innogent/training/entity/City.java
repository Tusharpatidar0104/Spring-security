package com.innogent.training.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class City {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer cityId;

	@Column(length = 50)
	private String cityName;

	@ManyToOne(targetEntity = States.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "stateId", referencedColumnName = "stateId")
	private States state;
}
