package com.nnk.springboot.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nnk.springboot.services.BidListService;

@ExtendWith(MockitoExtension.class)
public class BidListControllerTest {

	@Mock
	private BidListService bidService;
	@InjectMocks
	private BidListController bidListController;
	
	@Test
	public void home_ShouldReturnStringOfTemplatePath() {
		// GIVEN

		// WHEN

		// THEN

	}
	
}
