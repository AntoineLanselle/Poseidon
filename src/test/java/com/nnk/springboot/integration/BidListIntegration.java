package com.nnk.springboot.integration;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class BidListIntegration {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private BidListRepository bidRepository;

	@Test 
	public void validateBid_shouldAddBidInDatabase() {
		// GIVEN
		
		// WHEN
		
		// THEN
		
	}
	
	@Test
	public void getBidList_shouldReturnListOfBidList() throws Exception {
		// GIVEN
		List<BidList> listBefore = bidRepository.findAll();
		BidList bidList = new BidList();
		bidList.setAccount("test");
		bidList.setType("test");
		bidList.setBidQuantity(2D);
		bidRepository.save(bidList);

		// WHEN
		mockMvc.perform(get("/bidList/all")).andReturn();

		// THEN
		assertEquals(0, listBefore.size());
		assertEquals(1, bidRepository.findAll().size());
	}
	
	

}
