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

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.RessourceNotFoundException;
import com.nnk.springboot.services.BidListService;
import com.nnk.springboot.services.UserService;

@ExtendWith(MockitoExtension.class)
public class BidListControllerTest {

	@Mock
	private BidListService bidService;
	@Mock
	private UserService userService;
	@Mock
	private Model model;
	@InjectMocks
	private BidListController bidListController;

	@Test
	public void home_ShouldReturnStringOfTemplatePath() {
		// GIVEN
		User user = new User();
		user.setRole("USER");
		when(userService.getCurrentUser()).thenReturn(user);
		when(bidService.findAllBids()).thenReturn(new ArrayList<BidList>());

		// WHEN
		String testResult = bidListController.home(model);

		// THEN
		assertEquals("bidList/list", testResult);
	}

	@Test
	public void addBidForm_ShouldReturnStringOfTemplatePath() {
		// GIVEN

		// WHEN
		String testResult = bidListController.addBidForm(new BidList());

		// THEN
		assertEquals("bidList/add", testResult);
	}

	@Test
	public void validate_ShouldRedirectToBidList() {
		// GIVEN
		BindingResult result = mock(BindingResult.class);
		when(!result.hasErrors()).thenReturn(false);

		// WHEN
		String testResult = bidListController.validateBid(new BidList(), result, model);

		// THEN
		assertEquals("redirect:/bidList/list", testResult);
	}

	@Test
	public void validate_ShouldStringOfTemplatePathBidAdd() {
		// GIVEN
		BindingResult result = mock(BindingResult.class);
		when(!result.hasErrors()).thenReturn(true);

		// WHEN
		String testResult = bidListController.validateBid(new BidList(), result, model);

		// THEN
		assertEquals("bidList/add", testResult);
	}

	@Test
	public void showUpdateBidForm_ShouldReturnStringOfTemplatePath() throws RessourceNotFoundException {
		// GIVEN
		when(bidService.findById(1)).thenReturn(new BidList());

		// WHEN
		String testResult = bidListController.showUpdateBidForm(1, model);

		// THEN
		assertEquals("bidList/update", testResult);
	}

	@Test
	public void updateBid_ShouldRedirectToBidList() throws RessourceNotFoundException {
		// GIVEN
		BindingResult result = mock(BindingResult.class);
		when(!result.hasErrors()).thenReturn(false);

		// WHEN
		String testResult = bidListController.updateBid(1, new BidList(), result, model);

		// THEN
		assertEquals("redirect:/bidList/list", testResult);
	}

	@Test
	public void updateBid_ShouldReturnStringOfTemplatePath() throws RessourceNotFoundException {
		// GIVEN
		BindingResult result = mock(BindingResult.class);
		when(!result.hasErrors()).thenReturn(true);

		// WHEN
		String testResult = bidListController.updateBid(1, new BidList(), result, model);

		// THEN
		assertEquals("bidList/update", testResult);
	}

	@Test
	public void deleteBid_ShouldRedirectToBidList() throws RessourceNotFoundException {
		// GIVEN
		when(bidService.findById(1)).thenReturn(new BidList());
		when(bidService.findAllBids()).thenReturn(new ArrayList<BidList>());

		// WHEN
		String testResult = bidListController.deleteBid(1, model);

		// THEN
		assertEquals("redirect:/bidList/list", testResult);
	}

	@Test
	public void getBidsList_ShouldReturnResponseEntityWithStatusOKAndListOfBidsAsBody()
			throws RessourceNotFoundException {
		// GIVEN
		BidList bid = new BidList();
		List<BidList> bids = new ArrayList<BidList>();
		bids.add(bid);
		when(bidService.findAllBids()).thenReturn(bids);

		// WHEN
		ResponseEntity<List<BidList>> testResult = bidListController.getBidsList();

		// THEN
		assertEquals(HttpStatus.OK, testResult.getStatusCode());
		assertEquals(1, testResult.getBody().size());
		assertTrue(testResult.getBody().contains(bid));
	}

	@Test
	public void getBid_ShouldReturnResponseEntityWithStatusOKAndBidAsBody() throws RessourceNotFoundException {
		// GIVEN
		BidList bid = new BidList();
		when(bidService.findById(1)).thenReturn(bid);

		// WHEN
		ResponseEntity<BidList> testResult = bidListController.getBid(1);

		// THEN
		assertEquals(HttpStatus.OK, testResult.getStatusCode());
		assertEquals(bid, testResult.getBody());
	}

	@Test
	public void addBidList_ShouldReturnResponseEntityWithStatusOKAndMessageAsBody() throws RessourceNotFoundException {
		// GIVEN
		BidList bid = new BidList();
		BindingResult result = mock(BindingResult.class);

		// WHEN
		ResponseEntity<String> testResult = bidListController.addBidList(bid, result);

		// THEN
		assertEquals(HttpStatus.OK, testResult.getStatusCode());
		assertEquals("BidList has been added in DataBase.", testResult.getBody());
	}
	
	@Test
	public void addBidList_ShouldReturnResponseEntityWithStatusCONFLICTAndMessageAsBody() throws RessourceNotFoundException {
		// GIVEN
		BidList bid = new BidList();
		BindingResult result = mock(BindingResult.class);
		when(result.hasErrors()).thenReturn(true);

		// WHEN
		ResponseEntity<String> testResult = bidListController.addBidList(bid, result);

		// THEN
		assertEquals(HttpStatus.CONFLICT, testResult.getStatusCode());
		assertEquals("Fail: BidList data is not valid.", testResult.getBody());
	}

	@Test
	public void updateBidList_ShouldReturnResponseEntityWithStatusOKAndMessageAsBody()
			throws RessourceNotFoundException {
		// GIVEN
		BidList bid = new BidList();
		BindingResult result = mock(BindingResult.class);

		// WHEN
		ResponseEntity<String> testResult = bidListController.updateBidList(bid, result);

		// THEN
		assertEquals(HttpStatus.OK, testResult.getStatusCode());
		assertEquals("BidList has been updated in DataBase.", testResult.getBody());
	}

	
	@Test
	public void updateBidList_ShouldReturnResponseEntityWithStatusCONFLICTAndMessageAsBody() throws RessourceNotFoundException {
		// GIVEN
		BidList bid = new BidList();
		BindingResult result = mock(BindingResult.class);
		when(result.hasErrors()).thenReturn(true);

		// WHEN
		ResponseEntity<String> testResult = bidListController.updateBidList(bid, result);

		// THEN
		assertEquals(HttpStatus.CONFLICT, testResult.getStatusCode());
		assertEquals("Fail: BidList data is not valid.", testResult.getBody());
	}
	
	@Test
	public void deleteBidList_ShouldReturnResponseEntityWithStatusOKAndMessageAsBody()
			throws RessourceNotFoundException {
		// GIVEN

		// WHEN
		ResponseEntity<String> testResult = bidListController.deleteBidList(1);

		// THEN
		assertEquals(HttpStatus.OK, testResult.getStatusCode());
		assertEquals("Bid with id: 1 has been delete from DataBase.", testResult.getBody());
	}

}
