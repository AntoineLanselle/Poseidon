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

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.RessourceNotFoundException;
import com.nnk.springboot.services.RuleNameService;
import com.nnk.springboot.services.UserService;

@ExtendWith(MockitoExtension.class)
public class RuleNameControllerTest {

	@Mock
	private RuleNameService ruleNameService;
	@Mock
	private UserService userService;
	@Mock
	private Model model;
	@InjectMocks
	private RuleNameController ruleNameController;

	@Test
	public void showRules_ShouldReturnStringOfTemplatePath() {
		// GIVEN
		User user = new User();
		user.setRole("USER");
		when(userService.getCurrentUser()).thenReturn(user);
		when(ruleNameService.findAllRules()).thenReturn(new ArrayList<RuleName>());

		// WHEN
		String testResult = ruleNameController.showRules(model);

		// THEN
		assertEquals("ruleName/list", testResult);
	}

	@Test
	public void addRuleForm_ShouldReturnStringOfTemplatePath() {
		// GIVEN

		// WHEN
		String testResult = ruleNameController.addRuleForm(new RuleName());

		// THEN
		assertEquals("ruleName/add", testResult);
	}

	@Test
	public void validate_ShouldRedirectToRuleName() {
		// GIVEN
		BindingResult result = mock(BindingResult.class);
		when(!result.hasErrors()).thenReturn(false);

		// WHEN
		String testResult = ruleNameController.validateRule(new RuleName(), result, model);

		// THEN
		assertEquals("redirect:/ruleName/list", testResult);
	}

	@Test
	public void validate_ShouldStringOfTemplatePathRuleAdd() {
		// GIVEN
		BindingResult result = mock(BindingResult.class);
		when(!result.hasErrors()).thenReturn(true);

		// WHEN
		String testResult = ruleNameController.validateRule(new RuleName(), result, model);

		// THEN
		assertEquals("ruleName/add", testResult);
	}

	@Test
	public void showUpdateRuleForm_ShouldReturnStringOfTemplatePath() throws RessourceNotFoundException {
		// GIVEN
		when(ruleNameService.findById(1)).thenReturn(new RuleName());

		// WHEN
		String testResult = ruleNameController.showUpdateRuleForm(1, model);

		// THEN
		assertEquals("ruleName/update", testResult);
	}

	@Test
	public void updateRule_ShouldRedirectToRuleName() throws RessourceNotFoundException {
		// GIVEN
		BindingResult result = mock(BindingResult.class);
		when(!result.hasErrors()).thenReturn(false);

		// WHEN
		String testResult = ruleNameController.updateRuleName(1, new RuleName(), result, model);

		// THEN
		assertEquals("redirect:/ruleName/list", testResult);
	}

	@Test
	public void updateRule_ShouldReturnStringOfTemplatePath() throws RessourceNotFoundException {
		// GIVEN
		BindingResult result = mock(BindingResult.class);
		when(!result.hasErrors()).thenReturn(true);

		// WHEN
		String testResult = ruleNameController.updateRuleName(1, new RuleName(), result, model);

		// THEN
		assertEquals("ruleName/update", testResult);
	}

	@Test
	public void deleteRule_ShouldRedirectToRuleName() throws RessourceNotFoundException {
		// GIVEN
		when(ruleNameService.findById(1)).thenReturn(new RuleName());
		when(ruleNameService.findAllRules()).thenReturn(new ArrayList<RuleName>());

		// WHEN
		String testResult = ruleNameController.deleteRuleName(1, model);

		// THEN
		assertEquals("redirect:/ruleName/list", testResult);
	}

	@Test
	public void getRuleName_ShouldReturnResponseEntityWithStatusOKAndListOfRulesAsBody()
			throws RessourceNotFoundException {
		// GIVEN
		RuleName rule = new RuleName();
		List<RuleName> rules = new ArrayList<RuleName>();
		rules.add(rule);
		when(ruleNameService.findAllRules()).thenReturn(rules);

		// WHEN
		ResponseEntity<List<RuleName>> testResult = ruleNameController.getRuleNames();

		// THEN
		assertEquals(HttpStatus.OK, testResult.getStatusCode());
		assertEquals(1, testResult.getBody().size());
		assertTrue(testResult.getBody().contains(rule));
	}

	@Test
	public void getRule_ShouldReturnResponseEntityWithStatusOKAndRuleAsBody() throws RessourceNotFoundException {
		// GIVEN
		RuleName rule = new RuleName();
		when(ruleNameService.findById(1)).thenReturn(rule);

		// WHEN
		ResponseEntity<RuleName> testResult = ruleNameController.getRule(1);

		// THEN
		assertEquals(HttpStatus.OK, testResult.getStatusCode());
		assertEquals(rule, testResult.getBody());
	}

	@Test
	public void addRuleName_ShouldReturnResponseEntityWithStatusOKAndMessageAsBody() throws RessourceNotFoundException {
		// GIVEN
		RuleName rule = new RuleName();
		BindingResult result = mock(BindingResult.class);

		// WHEN
		ResponseEntity<String> testResult = ruleNameController.addRuleName(rule, result);

		// THEN
		assertEquals(HttpStatus.OK, testResult.getStatusCode());
		assertEquals("RuleName has been added in DataBase.", testResult.getBody());
	}
	
	@Test
	public void addRuleName_ShouldReturnResponseEntityWithStatusCONFLICTAndMessageAsBody() throws RessourceNotFoundException {
		// GIVEN
		RuleName rule = new RuleName();
		BindingResult result = mock(BindingResult.class);
		when(result.hasErrors()).thenReturn(true);

		// WHEN
		ResponseEntity<String> testResult = ruleNameController.addRuleName(rule, result);

		// THEN
		assertEquals(HttpStatus.CONFLICT, testResult.getStatusCode());
		assertEquals("Fail: RuleName data is not valid.", testResult.getBody());
	}

	@Test
	public void updateRuleName_ShouldReturnResponseEntityWithStatusOKAndMessageAsBody()
			throws RessourceNotFoundException {
		// GIVEN
		RuleName rule = new RuleName();
		BindingResult result = mock(BindingResult.class);

		// WHEN
		ResponseEntity<String> testResult = ruleNameController.updateRuleName(rule, result);

		// THEN
		assertEquals(HttpStatus.OK, testResult.getStatusCode());
		assertEquals("RuleName has been updated in DataBase.", testResult.getBody());
	}
	
	@Test
	public void updateRuleName_ShouldReturnResponseEntityWithStatusCONFLICTAndMessageAsBody() throws RessourceNotFoundException {
		// GIVEN
		RuleName rule = new RuleName();
		BindingResult result = mock(BindingResult.class);
		when(result.hasErrors()).thenReturn(true);

		// WHEN
		ResponseEntity<String> testResult = ruleNameController.addRuleName(rule, result);

		// THEN
		assertEquals(HttpStatus.CONFLICT, testResult.getStatusCode());
		assertEquals("Fail: RuleName data is not valid.", testResult.getBody());
	}

	@Test
	public void deleteRuleName_ShouldReturnResponseEntityWithStatusOKAndMessageAsBody()
			throws RessourceNotFoundException {
		// GIVEN

		// WHEN
		ResponseEntity<String> testResult = ruleNameController.deleteRuleName(1);

		// THEN
		assertEquals(HttpStatus.OK, testResult.getStatusCode());
		assertEquals("Rule with id: 1 has been delete from DataBase.", testResult.getBody());
	}

}
