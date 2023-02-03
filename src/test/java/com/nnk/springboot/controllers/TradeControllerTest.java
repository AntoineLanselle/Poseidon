package com.nnk.springboot.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.RessourceNotFoundException;
import com.nnk.springboot.services.TradeService;
import com.nnk.springboot.services.UserService;

@ExtendWith(MockitoExtension.class)
public class TradeControllerTest {

	@Mock
	private TradeService tradeService;
	@Mock
	private UserService userService;
	@Mock
	private Model model;
	@InjectMocks
	private TradeController tradeController;

	@Test
	public void showTrades_ShouldReturnStringOfTemplatePath() {
		// GIVEN
		User user = new User();
		user.setRole("USER");
		when(userService.getCurrentUser()).thenReturn(user);
		when(tradeService.findAllTrades()).thenReturn(new ArrayList<Trade>());

		// WHEN
		String testResult = tradeController.showTrades(model);

		// THEN
		assertEquals("trade/list", testResult);
	}

	@Test
	public void addTradeForm_ShouldReturnStringOfTemplatePath() {
		// GIVEN

		// WHEN
		String testResult = tradeController.addTradeForm(new Trade());

		// THEN
		assertEquals("trade/add", testResult);
	}

	@Test
	public void validate_ShouldRedirectToTrade() {
		// GIVEN
		BindingResult result = mock(BindingResult.class);
		when(!result.hasErrors()).thenReturn(false);

		// WHEN
		String testResult = tradeController.validateTrade(new Trade(), result, model);

		// THEN
		assertEquals("redirect:/trade/list", testResult);
	}

	@Test
	public void validate_ShouldStringOfTemplatePathTradeAdd() {
		// GIVEN
		BindingResult result = mock(BindingResult.class);
		when(!result.hasErrors()).thenReturn(true);

		// WHEN
		String testResult = tradeController.validateTrade(new Trade(), result, model);

		// THEN
		assertEquals("trade/add", testResult);
	}

	@Test
	public void showUpdateTradeForm_ShouldReturnStringOfTemplatePath() throws RessourceNotFoundException {
		// GIVEN
		when(tradeService.findById(1)).thenReturn(new Trade());

		// WHEN
		String testResult = tradeController.showUpdateTradeForm(1, model);

		// THEN
		assertEquals("trade/update", testResult);
	}

	@Test
	public void updateTrade_ShouldRedirectToTrade() throws RessourceNotFoundException {
		// GIVEN
		BindingResult result = mock(BindingResult.class);
		when(!result.hasErrors()).thenReturn(false);

		// WHEN
		String testResult = tradeController.updateTrade(1, new Trade(), result, model);

		// THEN
		assertEquals("redirect:/trade/list", testResult);
	}

	@Test
	public void updateTrade_ShouldReturnStringOfTemplatePath() throws RessourceNotFoundException {
		// GIVEN
		BindingResult result = mock(BindingResult.class);
		when(!result.hasErrors()).thenReturn(true);

		// WHEN
		String testResult = tradeController.updateTrade(1, new Trade(), result, model);

		// THEN
		assertEquals("trade/update", testResult);
	}

	@Test
	public void deleteTrade_ShouldRedirectToTrade() throws RessourceNotFoundException {
		// GIVEN
		when(tradeService.findById(1)).thenReturn(new Trade());
		when(tradeService.findAllTrades()).thenReturn(new ArrayList<Trade>());

		// WHEN
		String testResult = tradeController.deleteTrade(1, model);

		// THEN
		assertEquals("redirect:/trade/list", testResult);
	}

	@Test
	public void getTrade_ShouldReturnResponseEntityWithStatusOKAndListOfTradesAsBody()
			throws RessourceNotFoundException {
		// GIVEN
		Trade trade = new Trade();
		List<Trade> trades = new ArrayList<Trade>();
		trades.add(trade);
		when(tradeService.findAllTrades()).thenReturn(trades);

		// WHEN
		ResponseEntity<List<Trade>> testResult = tradeController.getTrades();

		// THEN
		assertEquals(HttpStatus.OK, testResult.getStatusCode());
		assertEquals(1, testResult.getBody().size());
		assertTrue(testResult.getBody().contains(trade));
	}

	@Test
	public void getTrade_ShouldReturnResponseEntityWithStatusOKAndTradeAsBody() throws RessourceNotFoundException {
		// GIVEN
		Trade trade = new Trade();
		when(tradeService.findById(1)).thenReturn(trade);

		// WHEN
		ResponseEntity<Trade> testResult = tradeController.getTrade(1);

		// THEN
		assertEquals(HttpStatus.OK, testResult.getStatusCode());
		assertEquals(trade, testResult.getBody());
	}

	@Test
	public void addTrade_ShouldReturnResponseEntityWithStatusOKAndMessageAsBody() throws RessourceNotFoundException {
		// GIVEN
		Trade trade = new Trade();

		// WHEN
		ResponseEntity<String> testResult = tradeController.addTrade(trade);

		// THEN
		assertEquals(HttpStatus.OK, testResult.getStatusCode());
		assertEquals("Trade has been added in DataBase.", testResult.getBody());
	}

	@Test
	public void updateTrade_ShouldReturnResponseEntityWithStatusOKAndMessageAsBody() throws RessourceNotFoundException {
		// GIVEN
		Trade trade = new Trade();

		// WHEN
		ResponseEntity<String> testResult = tradeController.updateTrade(trade);

		// THEN
		assertEquals(HttpStatus.OK, testResult.getStatusCode());
		assertEquals("Trade has been updated in DataBase.", testResult.getBody());
	}

	@Test
	public void deleteTrade_ShouldReturnResponseEntityWithStatusOKAndMessageAsBody() throws RessourceNotFoundException {
		// GIVEN

		// WHEN
		ResponseEntity<String> testResult = tradeController.deleteTrade(1);

		// THEN
		assertEquals(HttpStatus.OK, testResult.getStatusCode());
		assertEquals("Trade with id: 1 has been delete from DataBase.", testResult.getBody());
	}

}
