package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.exceptions.RessourceNotFoundException;

/**
 * Service for Trade.
 * 
 * @author Antoine Lanselle
 */
public interface TradeService {

	public List<Trade> findAllTrades();

	public Trade findById(Integer id) throws RessourceNotFoundException;

	public Trade addTrade(Trade trade);

	public Trade updateTrade(Trade trade) throws RessourceNotFoundException;

	public void deleteTrade(Trade trade) throws RessourceNotFoundException;

	
}
