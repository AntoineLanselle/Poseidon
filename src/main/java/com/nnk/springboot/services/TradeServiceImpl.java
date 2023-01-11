package com.nnk.springboot.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.exceptions.RessourceNotFoundException;
import com.nnk.springboot.repositories.TradeRepository;

/**
 * Service implementation for Trade.
 * 
 * @author Antoine Lanselle
 */
@Service
public class TradeServiceImpl implements TradeService {

	private static final Logger LOGGER = LogManager.getLogger(TradeServiceImpl.class);

	@Autowired
	private TradeRepository tradeRepository;

	/**
	 * Return all Trades in DataBase.
	 *
	 * @return List<Trade> all the Trades in the DataBase.
	 */
	@Override
	public List<Trade> findAllTrades() {
		String info = "Returning all Trades from DataBase.";
		LOGGER.info(info);

		return tradeRepository.findAll();
	}

	/**
	 * Return specific Trade from DataBase with id in parameter.
	 *
	 * @param Integer the id of the Rule.
	 * @return Trade with id in parameter in DataBase.
	 * @throw RessourceNotFoundException when id in parameter is not in DataBase.
	 */
	@Override
	public Trade findById(Integer id) throws RessourceNotFoundException {
		String info = "Looking for Trade with id " + id + " in DataBase.";
		String error = "Fail: Trade with id " + id + " not found in DataBase.";
		LOGGER.info(info);

		return tradeRepository.findById(id).orElseThrow(() -> new RessourceNotFoundException(error));
	}

	/**
	 * Add Trade in parameter in DataBase.
	 *
	 * @param Trade you want to add in DataBase.
	 * @return Trade the added Trade.
	 */
	@Override
	public Trade addTrade(Trade trade) {
		String info = "Adding Trade in DataBase.";
		LOGGER.info(info);

		return tradeRepository.save(trade);
	}

	/**
	 * Update Trade with Trade id in parameter in DataBase with Trade in parameter.
	 *
	 * @param Trade the new Trade data we want to update.
	 * @return Trade the updated Trade.
	 * @throw RessourceNotFoundException when Trade id in parameter is not in
	 *        DataBase.
	 */
	@Override
	public Trade updateTrade(Trade trade) throws RessourceNotFoundException {
		String info = "Updating Trade in DataBase.";
		LOGGER.info(info);

		if (tradeRepository.existsById(trade.getId())) {
			return tradeRepository.save(trade);
		} else {
			String error = "Fail: Trade with id " + trade.getId() + " not found in DataBase.";
			throw new RessourceNotFoundException(error);
		}
	}

	/**
	 * Delete Trade with Trade id in parameter from DataBase.
	 *
	 * @param Trade the Trade you want to delete from DataBase.
	 * @throw RessourceNotFoundException when Trade id in parameter is not in
	 *        DataBase.
	 */
	@Override
	public void deleteTrade(Trade trade) throws RessourceNotFoundException {
		String info = "Deleting Trade in DataBase.";
		LOGGER.info(info);

		if (tradeRepository.existsById(trade.getId())) {
			tradeRepository.delete(trade);
		} else {
			String error = "Fail: Trade with id " + trade.getId() + " not found in DataBase.";
			throw new RessourceNotFoundException(error);
		}
	}

}
