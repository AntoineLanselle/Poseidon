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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@WithMockUser(username = "admin", roles = { "ADMIN" })
public class BidListIntegration {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private BidListRepository bidRepository;

	private ObjectMapper objectMapper = new ObjectMapper();

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
		mockMvc.perform(
				post("/bidList/validate").param("account", "test2").param("type", "test2").param("bidQuantity", "2.0"))
				.andExpect(status().is3xxRedirection());

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals(2, bidRepository.findAll().size());
		assertEquals("test2", bidRepository.findAll().get(1).getAccount());
	}

	@Test
	public void updateBid_shouldUpdateBidInDatabase() throws Exception {
		// GIVEN
		List<BidList> listBefore = bidRepository.findAll();

		// WHEN
		mockMvc.perform(post("/bidList/update/{id}", listBefore.get(0).getId()).param("account", "newTest")
				.param("type", "test").param("bidQuantity", "2.0")).andExpect(status().is3xxRedirection());

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals("newTest", bidRepository.findById(listBefore.get(0).getId()).get().getAccount());
		assertEquals(1, bidRepository.findAll().size());
	}

	@Test
	public void deleteBid_shouldDeleteBidInDatabase() throws Exception {
		// GIVEN
		List<BidList> listBefore = bidRepository.findAll();

		// WHEN
		mockMvc.perform(get("/bidList/delete/{id}", listBefore.get(0).getId())).andExpect(status().is3xxRedirection());
		// the id changes at each test so we don't put a constant variable

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals(0, bidRepository.findAll().size());
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
	public void addBidList_shouldAddBidListInRepository() throws Exception {
		// GIVEN
		List<BidList> listBefore = bidRepository.findAll();

		BidList bidListEntity = new BidList();
		bidListEntity.setAccount("testAccount");
		bidListEntity.setType("testType");
		bidListEntity.setBidQuantity(2.0);

		MediaType MEDIA_TYPE_JSON = new MediaType("application", "json");
		MockHttpServletRequestBuilder request = post("/bidList");
		request.content(objectMapper.writeValueAsString(bidListEntity));
		request.accept(MEDIA_TYPE_JSON);
		request.contentType(MEDIA_TYPE_JSON);

		// WHEN
		mockMvc.perform(request).andExpect(status().isOk());

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals(2, bidRepository.findAll().size());
		assertEquals("testAccount", bidRepository.findAll().get(1).getAccount());
	}

	@Test
	public void updateBidList_shouldUpdateBidListInDatabase() throws Exception {
		// GIVEN
		List<BidList> listBefore = bidRepository.findAll();

		BidList bidListEntity = new BidList();
		bidListEntity.setId(listBefore.get(0).getId());
		bidListEntity.setAccount("newTest");
		bidListEntity.setType("newTest");
		bidListEntity.setBidQuantity(2.0);

		MediaType MEDIA_TYPE_JSON = new MediaType("application", "json");
		MockHttpServletRequestBuilder request = put("/bidList");
		request.content(objectMapper.writeValueAsString(bidListEntity));
		request.accept(MEDIA_TYPE_JSON);
		request.contentType(MEDIA_TYPE_JSON);

		// WHEN
		mockMvc.perform(request).andExpect(status().isOk());

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals("newTest", bidRepository.findAll().get(0).getAccount());
		assertEquals(1, bidRepository.findAll().size());
	}

	@Test
	public void deleteBidList_shouldDeleteBidListInDatabase() throws Exception {
		// GIVEN
		List<BidList> listBefore = bidRepository.findAll();

		// WHEN
		mockMvc.perform(delete("/bidList").param("id", listBefore.get(0).getId().toString()))
				.andExpect(status().isOk());
		// the id changes at each test so we don't put a constant variable

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals(0, bidRepository.findAll().size());
	}

}
