package com.vab.transactionmanager.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

public class TransactionDto {
    private BigDecimal amount;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public TransactionDto(BigDecimal amount) {
		super();
		this.amount = amount;
	}

	public TransactionDto() {
		super();
	}
    
}
