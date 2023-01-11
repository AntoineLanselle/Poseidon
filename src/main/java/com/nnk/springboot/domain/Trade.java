package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.sql.Timestamp;

/**
 * Entity Trade.
 * 
 * @author Antoine Lanselle
 */
@Entity
@Table(name = "trade")
public class Trade {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="account")
	@NotBlank(message = "Account is mandatory.")
	private String account;
	
	@Column(name="type")
	@NotBlank(message = "Type is mandatory.")
	private String type;
	
	@Column(name="buy_quantity")
	@NotNull(message="Enter a value.")
	@Digits(message="Enter a number in xx.x format.", fraction = 1, integer = 255)
	private Double buyQuantity;
	
	@Column(name="sell_quantity")
	private Double sellQuantity;
	
	@Column(name="buy_price")
	private Double buyPrice;
	
	@Column(name="sell_price")
	private Double sellPrice;
	
	@Column(name="benchmark")
	private String benchmark;
	
	@Column(name="trade_date")
	private Timestamp tradeDate;
	
	@Column(name="security")
	private String security;
	
	@Column(name="status")
	private String status;
	
	@Column(name="trader")
	private String trader;
	
	@Column(name="book")
	private String book;
	
	@Column(name="creation_name")
	private String creationName;
	
	@Column(name="creation_date")
	private Timestamp creationDate;
	
	@Column(name="revision_name")
	private String revisionName;
	
	@Column(name="revision_date")
	private Timestamp revisionDate;
	
	@Column(name="deal_name")
	private String dealName;
	
	@Column(name="deal_type")
	private String dealType;
	
	@Column(name="source_list_id")
	private String sourceListId;
	
	@Column(name="side")
	private String side;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id =id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getBuyQuantity() {
		return buyQuantity;
	}

	public void setBuyQuantity(Double buyQuantity) {
		this.buyQuantity = buyQuantity;
	}

	public Double getSellQuantity() {
		return sellQuantity;
	}

	public void setSellQuantity(Double sellQuantity) {
		this.sellQuantity = sellQuantity;
	}

	public Double getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(Double buyPrice) {
		this.buyPrice = buyPrice;
	}

	public Double getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(Double sellPrice) {
		this.sellPrice = sellPrice;
	}

	public String getBenchmark() {
		return benchmark;
	}

	public void setBenchmark(String benchmark) {
		this.benchmark = benchmark;
	}

	public Timestamp getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(Timestamp tradeDate) {
		this.tradeDate = tradeDate;
	}

	public String getSecurity() {
		return security;
	}

	public void setSecurity(String security) {
		this.security = security;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTrader() {
		return trader;
	}

	public void setTrader(String trader) {
		this.trader = trader;
	}

	public String getBook() {
		return book;
	}

	public void setBook(String book) {
		this.book = book;
	}

	public String getCreationName() {
		return creationName;
	}

	public void setCreationName(String creationName) {
		this.creationName = creationName;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public String getRevisionName() {
		return revisionName;
	}

	public void setRevisionName(String revisionName) {
		this.revisionName = revisionName;
	}

	public Timestamp getRevisionDate() {
		return revisionDate;
	}

	public void setRevisionDate(Timestamp revisionDate) {
		this.revisionDate = revisionDate;
	}

	public String getDealName() {
		return dealName;
	}

	public void setDealName(String dealName) {
		this.dealName = dealName;
	}

	public String getDealType() {
		return dealType;
	}

	public void setDealType(String dealType) {
		this.dealType = dealType;
	}

	public String getSourceListId() {
		return sourceListId;
	}

	public void setSourceListId(String sourceListId) {
		this.sourceListId = sourceListId;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

}
