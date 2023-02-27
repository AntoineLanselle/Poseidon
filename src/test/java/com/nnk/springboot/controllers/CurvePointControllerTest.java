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

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.RessourceNotFoundException;
import com.nnk.springboot.services.CurvePointService;
import com.nnk.springboot.services.UserService;

@ExtendWith(MockitoExtension.class)
public class CurvePointControllerTest {

	@Mock
	private CurvePointService curvePointService;
	@Mock
	private UserService userService;
	@Mock
	private Model model;
	@InjectMocks
	private CurvePointController curvePointController;

	@Test
	public void showCurves_ShouldReturnStringOfTemplatePath() {
		// GIVEN
		User user = new User();
		user.setRole("USER");
		when(userService.getCurrentUser()).thenReturn(user);
		when(curvePointService.findAllCurves()).thenReturn(new ArrayList<CurvePoint>());

		// WHEN
		String testResult = curvePointController.showCurves(model);

		// THEN
		assertEquals("curvePoint/list", testResult);
	}

	@Test
	public void addCurveForm_ShouldReturnStringOfTemplatePath() {
		// GIVEN

		// WHEN
		String testResult = curvePointController.addCurveForm(new CurvePoint());

		// THEN
		assertEquals("curvePoint/add", testResult);
	}

	@Test
	public void validate_ShouldRedirectToCurvePoint() {
		// GIVEN
		BindingResult result = mock(BindingResult.class);
		when(!result.hasErrors()).thenReturn(false);

		// WHEN
		String testResult = curvePointController.validateCurve(new CurvePoint(), result, model);

		// THEN
		assertEquals("redirect:/curvePoint/list", testResult);
	}

	@Test
	public void validate_ShouldStringOfTemplatePathCurveAdd() {
		// GIVEN
		BindingResult result = mock(BindingResult.class);
		when(!result.hasErrors()).thenReturn(true);

		// WHEN
		String testResult = curvePointController.validateCurve(new CurvePoint(), result, model);

		// THEN
		assertEquals("curvePoint/add", testResult);
	}

	@Test
	public void showUpdateCurveForm_ShouldReturnStringOfTemplatePath() throws RessourceNotFoundException {
		// GIVEN
		when(curvePointService.findById(1)).thenReturn(new CurvePoint());

		// WHEN
		String testResult = curvePointController.showUpdateCurveForm(1, model);

		// THEN
		assertEquals("curvePoint/update", testResult);
	}

	@Test
	public void updateCurve_ShouldRedirectToCurvePoint() throws RessourceNotFoundException {
		// GIVEN
		BindingResult result = mock(BindingResult.class);
		when(!result.hasErrors()).thenReturn(false);

		// WHEN
		String testResult = curvePointController.updateCurve(1, new CurvePoint(), result, model);

		// THEN
		assertEquals("redirect:/curvePoint/list", testResult);
	}

	@Test
	public void updateCurve_ShouldReturnStringOfTemplatePath() throws RessourceNotFoundException {
		// GIVEN
		BindingResult result = mock(BindingResult.class);
		when(!result.hasErrors()).thenReturn(true);

		// WHEN
		String testResult = curvePointController.updateCurve(1, new CurvePoint(), result, model);

		// THEN
		assertEquals("curvePoint/update", testResult);
	}

	@Test
	public void deleteCurve_ShouldRedirectToCurvePoint() throws RessourceNotFoundException {
		// GIVEN
		when(curvePointService.findById(1)).thenReturn(new CurvePoint());
		when(curvePointService.findAllCurves()).thenReturn(new ArrayList<CurvePoint>());

		// WHEN
		String testResult = curvePointController.deleteCurve(1, model);

		// THEN
		assertEquals("redirect:/curvePoint/list", testResult);
	}

	@Test
	public void getCurvePoint_ShouldReturnResponseEntityWithStatusOKAndListOfCurvesAsBody()
			throws RessourceNotFoundException {
		// GIVEN
		CurvePoint curve = new CurvePoint();
		List<CurvePoint> curves = new ArrayList<CurvePoint>();
		curves.add(curve);
		when(curvePointService.findAllCurves()).thenReturn(curves);

		// WHEN
		ResponseEntity<List<CurvePoint>> testResult = curvePointController.getCurvePoints();

		// THEN
		assertEquals(HttpStatus.OK, testResult.getStatusCode());
		assertEquals(1, testResult.getBody().size());
		assertTrue(testResult.getBody().contains(curve));
	}

	@Test
	public void getCurve_ShouldReturnResponseEntityWithStatusOKAndCurveAsBody() throws RessourceNotFoundException {
		// GIVEN
		CurvePoint curve = new CurvePoint();
		when(curvePointService.findById(1)).thenReturn(curve);

		// WHEN
		ResponseEntity<CurvePoint> testResult = curvePointController.getCurve(1);

		// THEN
		assertEquals(HttpStatus.OK, testResult.getStatusCode());
		assertEquals(curve, testResult.getBody());
	}

	@Test
	public void addCurvePoint_ShouldReturnResponseEntityWithStatusOKAndMessageAsBody()
			throws RessourceNotFoundException {
		// GIVEN
		CurvePoint curve = new CurvePoint();
		BindingResult result = mock(BindingResult.class);

		// WHEN
		ResponseEntity<String> testResult = curvePointController.addCurvePoint(curve, result);

		// THEN
		assertEquals(HttpStatus.OK, testResult.getStatusCode());
		assertEquals("CurvePoint has been added in DataBase.", testResult.getBody());
	}
	
	@Test
	public void addCurvePoint_ShouldReturnResponseEntityWithStatusCONFLICTAndMessageAsBody() throws RessourceNotFoundException {
		// GIVEN
		CurvePoint curve = new CurvePoint();
		BindingResult result = mock(BindingResult.class);
		when(result.hasErrors()).thenReturn(true);

		// WHEN
		ResponseEntity<String> testResult = curvePointController.addCurvePoint(curve, result);

		// THEN
		assertEquals(HttpStatus.CONFLICT, testResult.getStatusCode());
		assertEquals("Fail: Curve data is not valid.", testResult.getBody());
	}

	@Test
	public void updateCurvePoint_ShouldReturnResponseEntityWithStatusOKAndMessageAsBody()
			throws RessourceNotFoundException {
		// GIVEN
		CurvePoint curve = new CurvePoint();
		BindingResult result = mock(BindingResult.class);

		// WHEN
		ResponseEntity<String> testResult = curvePointController.updateCurvePoint(curve, result);

		// THEN
		assertEquals(HttpStatus.OK, testResult.getStatusCode());
		assertEquals("CurvePoint has been updated in DataBase.", testResult.getBody());
	}
	
	@Test
	public void updateCurvePoint_ShouldReturnResponseEntityWithStatusCONFLICTAndMessageAsBody() throws RessourceNotFoundException {
		// GIVEN
		CurvePoint curve = new CurvePoint();
		BindingResult result = mock(BindingResult.class);
		when(result.hasErrors()).thenReturn(true);

		// WHEN
		ResponseEntity<String> testResult = curvePointController.updateCurvePoint(curve, result);

		// THEN
		assertEquals(HttpStatus.CONFLICT, testResult.getStatusCode());
		assertEquals("Fail: Curve data is not valid.", testResult.getBody());
	}

	@Test
	public void deleteCurvePoint_ShouldReturnResponseEntityWithStatusOKAndMessageAsBody()
			throws RessourceNotFoundException {
		// GIVEN

		// WHEN
		ResponseEntity<String> testResult = curvePointController.deleteCurvePoint(1);

		// THEN
		assertEquals(HttpStatus.OK, testResult.getStatusCode());
		assertEquals("Curve with id: 1 has been delete from DataBase.", testResult.getBody());
	}

}
