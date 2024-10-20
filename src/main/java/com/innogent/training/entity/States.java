package com.innogent.training.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "states")
@Setter
@Getter
public class States {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer stateId;

	@Column(length = 20)
	private String stateName;

	@ManyToOne(targetEntity = Country.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Country country;

}
