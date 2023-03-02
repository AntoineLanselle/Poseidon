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
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@WithMockUser(username = "admin", roles = { "ADMIN" })
public class TradeIntegration {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TradeRepository tradeRepository;

	private ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	public void setUp() {
		tradeRepository.deleteAll();

		Trade trade = new Trade();
		trade.setAccount("test");
		trade.setType("test");
		trade.setBuyQuantity(2.0);
		tradeRepository.save(trade);
	}

	@Test
	public void validateTrade_shouldAddTradeInDatabase() throws Exception {
		// GIVEN
		List<Trade> listBefore = tradeRepository.findAll();

		// WHEN
		mockMvc.perform(post("/trade/validate").param("account", "newTest").param("type", "newTest")
				.param("buyQuantity", "2.0")).andExpect(status().is3xxRedirection());

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals(2, tradeRepository.findAll().size());
		assertEquals("newTest", tradeRepository.findAll().get(1).getAccount());
	}

	@Test
	public void updateTrade_shouldUpdateTradeInDatabase() throws Exception {
		// GIVEN
		List<Trade> listBefore = tradeRepository.findAll();

		// WHEN
		mockMvc.perform(post("/trade/update/{id}", listBefore.get(0).getId()).param("account", "newTest")
				.param("type", "newTest").param("buyQuantity", "2.0")).andExpect(status().is3xxRedirection());

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals("newTest", tradeRepository.findById(listBefore.get(0).getId()).get().getAccount());
		assertEquals(1, tradeRepository.findAll().size());
	}

	@Test
	public void deleteTrade_shouldDeleteTradeInDatabase() throws Exception {
		// GIVEN
		List<Trade> listBefore = tradeRepository.findAll();

		// WHEN
		mockMvc.perform(get("/trade/delete/{id}", listBefore.get(0).getId())).andExpect(status().is3xxRedirection());
		// the id changes at each test so we don't put a constant variable

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals(0, tradeRepository.findAll().size());
	}

	@Test
	public void getTrade_shouldReturnListOfTrade() throws Exception {
		// GIVEN
		List<Trade> listBefore = tradeRepository.findAll();

		// WHEN
		mockMvc.perform(get("/trade/all"));

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals(1, tradeRepository.findAll().size());
	}

	@Test
	public void addTrade_shouldAddTradeInRepository() throws Exception {
		// GIVEN
		List<Trade> listBefore = tradeRepository.findAll();

		Trade trade = new Trade();
		trade.setAccount("newTest");
		trade.setType("newTest");
		trade.setBuyQuantity(2.0);

		MediaType MEDIA_TYPE_JSON = new MediaType("application", "json");
		MockHttpServletRequestBuilder request = post("/trade");
		request.content(objectMapper.writeValueAsString(trade));
		request.accept(MEDIA_TYPE_JSON);
		request.contentType(MEDIA_TYPE_JSON);

		// WHEN
		mockMvc.perform(request).andExpect(status().isOk());

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals(2, tradeRepository.findAll().size());
		assertEquals("newTest", tradeRepository.findAll().get(1).getAccount());
	}

	@Test
	public void updateTrade_shouldUpdateTheTradeInDatabase() throws Exception {
		// GIVEN
		List<Trade> listBefore = tradeRepository.findAll();

		Trade trade = new Trade();
		trade.setId(listBefore.get(0).getId());
		trade.setAccount("newTest");
		trade.setType("newTest");
		trade.setBuyQuantity(2.0);

		MediaType MEDIA_TYPE_JSON = new MediaType("application", "json");
		MockHttpServletRequestBuilder request = put("/trade");
		request.content(objectMapper.writeValueAsString(trade));
		request.accept(MEDIA_TYPE_JSON);
		request.contentType(MEDIA_TYPE_JSON);

		// WHEN
		mockMvc.perform(request).andExpect(status().isOk());

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals("newTest", tradeRepository.findAll().get(0).getAccount());
		assertEquals(1, tradeRepository.findAll().size());
	}

	@Test
	public void deleteTrade_shouldDeleteTheTradeInDatabase() throws Exception {
		// GIVEN
		List<Trade> listBefore = tradeRepository.findAll();

		// WHEN
		mockMvc.perform(delete("/trade").param("id", listBefore.get(0).getId().toString())).andExpect(status().isOk());
		// the id changes at each test so we don't put a constant variable

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals(0, tradeRepository.findAll().size());
	}

}
