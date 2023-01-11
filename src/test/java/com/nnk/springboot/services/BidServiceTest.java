package com.nnk.springboot.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nnk.springboot.repositories.BidListRepository;

@ExtendWith(MockitoExtension.class)
public class BidServiceTest {

	@Mock
	private BidListRepository bidListRepository;
	@InjectMocks
	private BidListService bidService;

	@Test
	public void findAllBids_ShouldReturnListOfAllBidlist() {
		// GIVEN

		// WHEN

		// THEN

	}

}
