package com.nnk.springboot.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exceptions.RessourceNotFoundException;
import com.nnk.springboot.repositories.BidListRepository;

@ExtendWith(MockitoExtension.class)
public class BidListServiceImplTest {

	@Mock
	private BidListRepository bidListRepository;
	@InjectMocks
	private BidListServiceImpl bidService;
	private static List<BidList> bidRepo;
	
	@BeforeAll
	public static void init() {
		BidList bidOne = new BidList();
		BidList bidTwo = new BidList();
		bidRepo = new ArrayList<BidList>();
		bidRepo.add(bidOne);
		bidRepo.add(bidTwo);
	}
	
	@Test
	public void findAllBids_ShouldReturnListOfAllBidlist() {
		// GIVEN
		when(bidListRepository.findAll()).thenReturn(bidRepo);
		
		// WHEN
		List<BidList> testResult = bidService.findAllBids();
		
		// THEN
		assertEquals(bidRepo, testResult);
	}
	
	@Test
	public void findById_ShouldReturnSpecificBid() throws RessourceNotFoundException {
		// GIVEN
		Optional<BidList> optBid = Optional.of(bidRepo.get(0));
		when(bidListRepository.findById(1)).thenReturn(optBid);
		
		// WHEN
		BidList testResult = bidService.findById(1);
		
		// THEN
		assertEquals(bidRepo.get(0), testResult);
	}
	
	@Test
	public void findById_ShouldReturnRessourceNotFoundException() throws RessourceNotFoundException {
		// GIVEN
		Optional<BidList> bidNull = Optional.empty();
		when(bidListRepository.findById(1)).thenReturn(bidNull);
		
		// WHEN // THEN
		assertThrows(RessourceNotFoundException.class, () -> {
			bidService.findById(1);
		});
	}
	
	@Test
	public void addBidList_ShouldReturnBidInParameter() throws RessourceNotFoundException {
		// GIVEN
		when(bidListRepository.save(bidRepo.get(0))).thenReturn(bidRepo.get(0));
		
		// WHEN 
		BidList testResult = bidService.addBidList(bidRepo.get(0));
		
		// THEN
		assertEquals(bidRepo.get(0), testResult);
	}
	
	@Test
	public void updateBidList_ShouldReturnBidInParameter() throws RessourceNotFoundException {
		// GIVEN
		BidList bid = new BidList(); 
		when(bidListRepository.existsById(bid.getId())).thenReturn(true);
		when(bidListRepository.save(bid)).thenReturn(bid);
		
		// WHEN 
		BidList testResult = bidService.updateBidList(bid);
		
		// THEN
		assertEquals(bid, testResult);
	}
	
	@Test
	public void updateBidList_ShouldReturnRessourceNotFoundException() throws RessourceNotFoundException {
		// GIVEN
		BidList bid = new BidList(); 
		bid.setId(404);
		when(bidListRepository.existsById(404)).thenReturn(false);
		
		// WHEN // THEN
		assertThrows(RessourceNotFoundException.class, () -> {
			bidService.updateBidList(bid);
		});
	}
	
	@Test
	public void deleteBidList_ShouldReturnBidInParameter() throws RessourceNotFoundException {
		// GIVEN
		BidList bid = new BidList(); 
		when(bidListRepository.existsById(bid.getId())).thenReturn(true);
		
		// WHEN
		bidService.deleteBidList(bid);
		
		// THEN
		verify(bidListRepository).delete(bid);
	}
	
	@Test
	public void deleteBidList_ShouldReturnRessourceNotFoundException() throws RessourceNotFoundException {
		// GIVEN
		BidList bid = new BidList(); 
		bid.setId(404);
		when(bidListRepository.existsById(404)).thenReturn(false);
		
		// WHEN // THEN
		assertThrows(RessourceNotFoundException.class, () -> {
			bidService.deleteBidList(bid);
		});
	}
	
}
