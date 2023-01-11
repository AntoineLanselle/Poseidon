package com.nnk.springboot.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exceptions.RessourceNotFoundException;
import com.nnk.springboot.repositories.BidListRepository;

/**
 * Service implementation for BidList.
 * 
 * @author Antoine Lanselle
 */
@Service
public class BidListServiceImpl implements BidListService {

	private static final Logger LOGGER = LogManager.getLogger(BidListServiceImpl.class);

	@Autowired
	private BidListRepository bidListRepository;

	/**
	 * Return all Bids in DataBase.
	 *
	 * @return List<Bidlist> all the Bids in the DataBase.
	 */
	@Override
	public List<BidList> findAllBids() {
		String info = "Returning all Bids from DataBase.";
		LOGGER.info(info);

		return bidListRepository.findAll();
	}

	/**
	 * Return specific Bid from DataBase with id in parameter.
	 *
	 * @param Integer the id of the Bid.
	 * @return BidList with id in parameter in DataBase.
	 * @throw RessourceNotFoundException when id in parameter is not in DataBase.
	 */
	@Override
	public BidList findById(Integer id) throws RessourceNotFoundException {
		String info = "Looking for Bid with id " + id + " in DataBase.";
		String error = "Fail: Bid with id " + id + " not found in DataBase.";
		LOGGER.info(info);

		return bidListRepository.findById(id).orElseThrow(() -> new RessourceNotFoundException(error));
	}

	/**
	 * Add Bid in parameter in DataBase.
	 *
	 * @param BidList you want to add in DataBase.
	 * @return BidList the added Bid.
	 */
	@Override
	public BidList addBidList(BidList bidList) {
		String info = "Adding Bid in DataBase.";
		LOGGER.info(info);

		return bidListRepository.save(bidList);
	}

	/**
	 * Update Bid with Bid id in parameter in DataBase with Bid in parameter.
	 *
	 * @param BidList the new Bid data we want to update.
	 * @return BidList the updated Bid.
	 * @throw RessourceNotFoundException when Bid id in parameter is not in
	 *        DataBase.
	 */
	@Override
	public BidList updateBidList(BidList bidList) throws RessourceNotFoundException {
		String info = "Updating Bid in DataBase.";
		LOGGER.info(info);

		if (bidListRepository.existsById(bidList.getId())) {
			return bidListRepository.save(bidList);
		} else {
			String error = "Fail: Bid with id " + bidList.getId() + " not found in DataBase.";
			throw new RessourceNotFoundException(error);
		}
	}

	/**
	 * Delete Bid with Bid id in parameter from DataBase.
	 *
	 * @param BidList the Bid you want to delete from DataBase.
	 * @throw RessourceNotFoundException when Bid id in parameter is not in
	 *        DataBase.
	 */
	@Override
	public void deleteBidList(BidList bidList) throws RessourceNotFoundException {
		String info = "Deleting Bid in DataBase.";
		LOGGER.info(info);

		if (bidListRepository.existsById(bidList.getId())) {
			bidListRepository.delete(bidList);
		} else {
			String error = "Fail: Bid with id " + bidList.getId() + " not found in DataBase.";
			throw new RessourceNotFoundException(error);
		}
	}

}
