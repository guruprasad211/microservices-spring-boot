package com.services.fraud;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Entity
public class FraudCheckHistory {
	@Id
	@SequenceGenerator(name = "fraud_id_seq", sequenceName = "fraud_id_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fraud_id_seq")
	private Integer id;
	private Integer custId;
	private Boolean isFraudster;
	private LocalDateTime createdDt;
}
