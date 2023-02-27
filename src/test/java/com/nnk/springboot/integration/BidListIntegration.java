package com.nnk.springboot.integration;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
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
// se connecter avec l admin
public class BidListIntegration {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private BidListRepository bidRepository;

	@BeforeEach
	public void setUp() {
		bidRepository.deleteAll();

		BidList bidList = new BidList();
		bidList.setAccount("test");
		bidList.setType("test");
		bidList.setBidQuantity(2.0);
		bidRepository.save(bidList);
	}
	
	@Test
	public void validateBid_shouldAddBidInDatabase() throws Exception {
		// GIVEN
		List<BidList> listBefore = bidRepository.findAll();

		// WHEN
		mockMvc.perform(post("/bidList/validate")
				 .param("account", "test")
				 .param("type", "test")
				 .param("bidQuantity", "2.0"))
		.andExpect(status().is3xxRedirection());

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals(2, bidRepository.findAll().size());
	}
	
	@Test
	public void updateBid_shouldUpdateBidInDatabase() throws Exception {
		// GIVEN
		List<BidList> listBefore = bidRepository.findAll();

		// WHEN
		mockMvc.perform(put("/bidList/update/{id}", 1)
				 .param("account", "newTest")
				 .param("type", "test")
				 .param("bidQuantity", "2.0"))
		.andExpect(status().is3xxRedirection());

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals("newTest", bidRepository.findById(1).get().getAccount());
		assertEquals(1, bidRepository.findAll().size());
	}
	
	@Test
	public void deleteBid_shouldDeleteBidInDatabase() throws Exception {
		// GIVEN
		List<BidList> listBefore = bidRepository.findAll();

		// WHEN
		mockMvc.perform(delete("/bidList/delete/{id}", 1))
		.andExpect(status().is3xxRedirection());

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals(0, bidRepository.findAll().size());
	}

	@Test
	public void addBidList_shouldAddBidListInRepository() throws Exception {
		// GIVEN
		List<BidList> listBefore = bidRepository.findAll();
		//reussir à donner un request body
		
		// WHEN
		mockMvc.perform(post("/bidList"))
		.andExpect(status().isOk());
		//.andExpect(content().value("BidList has been added in DataBase."));

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals(2, bidRepository.findAll().size());
	}	
	
	@Test
	public void getBidList_shouldReturnListOfBidList() throws Exception {
		// GIVEN
		List<BidList> listBefore = bidRepository.findAll();

		// WHEN
		mockMvc.perform(get("/bidList/all"));

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals(1, bidRepository.findAll().size());
	}
	
	@Test
	public void updateBidList_shouldUpdateBidListInDatabase() throws Exception {
		// GIVEN
		List<BidList> listBefore = bidRepository.findAll();
		//reussir à donner un request body
		
		// WHEN
		mockMvc.perform(put("/bidList"));

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals("newTest", bidRepository.findById(1).get().getAccount());
		assertEquals(1, bidRepository.findAll().size());
	}
	
	@Test
	public void deleteBidList_shouldDeleteBidListInDatabase() throws Exception {
		// GIVEN
		List<BidList> listBefore = bidRepository.findAll();
		
		// WHEN
		mockMvc.perform(delete("/bidList"));

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals(0, bidRepository.findAll().size());
	}

}
