package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exceptions.RessourceNotFoundException;

/**
 * Service for BidList.
 * 
 * @author Antoine Lanselle
 */
public interface BidListService {

	public List<BidList> findAllBids();

	public BidList findById(Integer id) throws RessourceNotFoundException;

	public BidList addBidList(BidList bidList);

	public BidList updateBidList(BidList bidList) throws RessourceNotFoundException;

	public void deleteBidList(BidList bidList) throws RessourceNotFoundException;

}
