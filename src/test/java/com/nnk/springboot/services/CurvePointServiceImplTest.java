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

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.exceptions.RessourceNotFoundException;
import com.nnk.springboot.repositories.CurvePointRepository;

@ExtendWith(MockitoExtension.class)
public class CurvePointServiceImplTest {

	@Mock
	private CurvePointRepository curvePointRepository;
	@InjectMocks
	private CurvePointServiceImpl curvePointService;
	private static List<CurvePoint> curveRepo;
	
	@BeforeAll
	public static void init() {
		CurvePoint curveOne = new CurvePoint();
		CurvePoint curveTwo = new CurvePoint();
		curveRepo = new ArrayList<CurvePoint>();
		curveRepo.add(curveOne);
		curveRepo.add(curveTwo);
	}
	
	@Test
	public void findAllCurves_ShouldReturnListOfAllCurvePoints() {
		// GIVEN
		when(curvePointRepository.findAll()).thenReturn(curveRepo);
		
		// WHEN
		List<CurvePoint> testResult = curvePointService.findAllCurves();
		
		// THEN
		assertEquals(curveRepo, testResult);
	}
	
	@Test
	public void findById_ShouldReturnSpecificCurvePoint() throws RessourceNotFoundException {
		// GIVEN
		Optional<CurvePoint> optCurve = Optional.of(curveRepo.get(0));
		when(curvePointRepository.findById(1)).thenReturn(optCurve);
		
		// WHEN
		CurvePoint testResult = curvePointService.findById(1);
		
		// THEN
		assertEquals(curveRepo.get(0), testResult);
	}
	
	@Test
	public void findById_ShouldReturnRessourceNotFoundException() throws RessourceNotFoundException {
		// GIVEN
		Optional<CurvePoint> curveNull = Optional.empty();
		when(curvePointRepository.findById(1)).thenReturn(curveNull);
		
		// WHEN // THEN
		assertThrows(RessourceNotFoundException.class, () -> {
			curvePointService.findById(1);
		});
	}
	
	@Test
	public void addCurvePoint_ShouldReturnCurvePointInParameter() throws RessourceNotFoundException {
		// GIVEN
		when(curvePointRepository.save(curveRepo.get(0))).thenReturn(curveRepo.get(0));
		
		// WHEN 
		CurvePoint testResult = curvePointService.addCurvePoint(curveRepo.get(0));
		
		// THEN
		assertEquals(curveRepo.get(0), testResult);
	}
	
	@Test
	public void updateCurvePoint_ShouldReturnCurveInParameter() throws RessourceNotFoundException {
		// GIVEN
		CurvePoint curve = new CurvePoint(); 
		when(curvePointRepository.existsById(curve.getId())).thenReturn(true);
		when(curvePointRepository.save(curve)).thenReturn(curve);
		
		// WHEN 
		CurvePoint testResult = curvePointService.updateCurvePoint(curve);
		
		// THEN
		assertEquals(curve, testResult);
	}
	
	@Test
	public void updateCurvePoint_ShouldReturnRessourceNotFoundException() throws RessourceNotFoundException {
		// GIVEN
		CurvePoint curve = new CurvePoint(); 
		curve.setId(404);
		when(curvePointRepository.existsById(404)).thenReturn(false);
		
		// WHEN // THEN
		assertThrows(RessourceNotFoundException.class, () -> {
			curvePointService.updateCurvePoint(curve);
		});
	}
	
	@Test
	public void deleteCurvePoint_ShouldReturnCurvePointInParameter() throws RessourceNotFoundException {
		// GIVEN
		CurvePoint curve = new CurvePoint(); 
		when(curvePointRepository.existsById(curve.getId())).thenReturn(true);
		
		// WHEN
		curvePointService.deleteCurvePoint(curve);
		
		// THEN
		verify(curvePointRepository).delete(curve);
	}
	
	@Test
	public void deleteCurvePoint_ShouldReturnRessourceNotFoundException() throws RessourceNotFoundException {
		// GIVEN
		CurvePoint curve = new CurvePoint(); 
		curve.setId(404);
		when(curvePointRepository.existsById(404)).thenReturn(false);
		
		// WHEN // THEN
		assertThrows(RessourceNotFoundException.class, () -> {
			curvePointService.deleteCurvePoint(curve);
		});
	}
	
}
