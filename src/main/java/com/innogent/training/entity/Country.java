
package com.innogent.training.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Country implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer countryId;

	@Column(length = 60)
	private String countryName;

}
