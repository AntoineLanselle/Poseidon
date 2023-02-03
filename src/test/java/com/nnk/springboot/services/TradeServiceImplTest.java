package com.nnk.springboot.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.exceptions.RessourceNotFoundException;
import com.nnk.springboot.repositories.TradeRepository;


@ExtendWith(MockitoExtension.class)
public class TradeServiceImplTest {

	@Mock
	private TradeRepository tradeRepository;
	@InjectMocks
	private TradeServiceImpl tradeService;
	private static List<Trade> tradeRepo;
	
	@BeforeAll
	public static void init() {
		Trade tradeOne = new Trade();
		Trade tradeTwo = new Trade();
		tradeRepo = new ArrayList<Trade>();
		tradeRepo.add(tradeOne);
		tradeRepo.add(tradeTwo);
	}
	
	@Test
	public void findAllTrades_ShouldReturnListOfAllTrades() {
		// GIVEN
		when(tradeRepository.findAll()).thenReturn(tradeRepo);
		
		// WHEN
		List<Trade> testResult = tradeService.findAllTrades();
		
		// THEN
		assertEquals(tradeRepo, testResult);
	}
	
	@Test
	public void findById_ShouldReturnSpecificTrade() throws RessourceNotFoundException {
		// GIVEN
		Optional<Trade> optTrade = Optional.of(tradeRepo.get(0));
		when(tradeRepository.findById(1)).thenReturn(optTrade);
		
		// WHEN
		Trade testResult = tradeService.findById(1);
		
		// THEN
		assertEquals(tradeRepo.get(0), testResult);
	}
	
	@Test
	public void findById_ShouldReturnRessourceNotFoundException() throws RessourceNotFoundException {
		// GIVEN
		Optional<Trade> tradeNull = Optional.empty();
		when(tradeRepository.findById(1)).thenReturn(tradeNull);
		
		// WHEN // THEN
		assertThrows(RessourceNotFoundException.class, () -> {
			tradeService.findById(1);
		});
	}
	
	@Test
	public void addTrade_ShouldReturnTradeInParameter() throws RessourceNotFoundException {
		// GIVEN
		when(tradeRepository.save(tradeRepo.get(0))).thenReturn(tradeRepo.get(0));
		
		// WHEN 
		Trade testResult = tradeService.addTrade(tradeRepo.get(0));
		
		// THEN
		assertEquals(tradeRepo.get(0), testResult);
	}
	
	@Test
	public void updateTrade_ShouldReturnTradeInParameter() throws RessourceNotFoundException {
		// GIVEN
		Trade trade = new Trade(); 
		when(tradeRepository.existsById(trade.getId())).thenReturn(true);
		when(tradeRepository.save(trade)).thenReturn(trade);
		
		// WHEN 
		Trade testResult = tradeService.updateTrade(trade);
		
		// THEN
		assertEquals(trade, testResult);
	}
	
	@Test
	public void updateTrade_ShouldReturnRessourceNotFoundException() throws RessourceNotFoundException {
		// GIVEN
		Trade trade = new Trade(); 
		trade.setId(404);
		when(tradeRepository.existsById(404)).thenReturn(false);
		
		// WHEN // THEN
		assertThrows(RessourceNotFoundException.class, () -> {
			tradeService.updateTrade(trade);
		});
	}
	
	@Test
	public void deleteTrade_ShouldReturnBidInParameter() throws RessourceNotFoundException {
		// GIVEN
		Trade trade = new Trade(); 
		when(tradeRepository.existsById(trade.getId())).thenReturn(true);
		
		// WHEN
		tradeService.deleteTrade(trade);
		
		// THEN
		verify(tradeRepository).delete(trade);
	}
	
	@Test
	public void deleteTrade_ShouldReturnRessourceNotFoundException() throws RessourceNotFoundException {
		// GIVEN
		Trade trade = new Trade(); 
		trade.setId(404);
		when(tradeRepository.existsById(404)).thenReturn(false);
		
		// WHEN // THEN
		assertThrows(RessourceNotFoundException.class, () -> {
			tradeService.deleteTrade(trade);
		});
	}
	
}
