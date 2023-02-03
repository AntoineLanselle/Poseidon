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

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exceptions.RessourceNotFoundException;
import com.nnk.springboot.repositories.RuleNameRepository;

@ExtendWith(MockitoExtension.class)
public class RuleNameServiceImplTest {

	@Mock
	private RuleNameRepository ruleNameRepository;
	@InjectMocks
	private RuleNameServiceImpl ruleNameService;
	private static List<RuleName> ruleRepo;

	@BeforeAll
	public static void init() {
		RuleName ruleOne = new RuleName();
		RuleName ruleTwo = new RuleName();
		ruleRepo = new ArrayList<RuleName>();
		ruleRepo.add(ruleOne);
		ruleRepo.add(ruleTwo);
	}

	@Test
	public void findAllRules_ShouldReturnListOfAllRuleNames() {
		// GIVEN
		when(ruleNameRepository.findAll()).thenReturn(ruleRepo);

		// WHEN
		List<RuleName> testResult = ruleNameService.findAllRules();

		// THEN
		assertEquals(ruleRepo, testResult);
	}

	@Test
	public void findById_ShouldReturnSpecificRule() throws RessourceNotFoundException {
		// GIVEN
		Optional<RuleName> optRule = Optional.of(ruleRepo.get(0));
		when(ruleNameRepository.findById(1)).thenReturn(optRule);

		// WHEN
		RuleName testResult = ruleNameService.findById(1);

		// THEN
		assertEquals(ruleRepo.get(0), testResult);
	}

	@Test
	public void findById_ShouldReturnRessourceNotFoundException() throws RessourceNotFoundException {
		// GIVEN
		Optional<RuleName> ruleNull = Optional.empty();
		when(ruleNameRepository.findById(1)).thenReturn(ruleNull);

		// WHEN // THEN
		assertThrows(RessourceNotFoundException.class, () -> {
			ruleNameService.findById(1);
		});
	}

	@Test
	public void addRuleName_ShouldReturnRuleInParameter() throws RessourceNotFoundException {
		// GIVEN
		when(ruleNameRepository.save(ruleRepo.get(0))).thenReturn(ruleRepo.get(0));

		// WHEN
		RuleName testResult = ruleNameService.addRuleName(ruleRepo.get(0));

		// THEN
		assertEquals(ruleRepo.get(0), testResult);
	}

	@Test
	public void updateRuleName_ShouldReturnRuleInParameter() throws RessourceNotFoundException {
		// GIVEN
		RuleName rule = new RuleName();
		when(ruleNameRepository.existsById(rule.getId())).thenReturn(true);
		when(ruleNameRepository.save(rule)).thenReturn(rule);

		// WHEN
		RuleName testResult = ruleNameService.updateRuleName(rule);

		// THEN
		assertEquals(rule, testResult);
	}

	@Test
	public void updateBidList_ShouldReturnRessourceNotFoundException() throws RessourceNotFoundException {
		// GIVEN
		RuleName rule = new RuleName();
		rule.setId(404);
		when(ruleNameRepository.existsById(404)).thenReturn(false);

		// WHEN // THEN
		assertThrows(RessourceNotFoundException.class, () -> {
			ruleNameService.updateRuleName(rule);
		});
	}

	@Test
	public void deleteRuleName_ShouldReturnRuleInParameter() throws RessourceNotFoundException {
		// GIVEN
		RuleName rule = new RuleName();
		when(ruleNameRepository.existsById(rule.getId())).thenReturn(true);

		// WHEN
		ruleNameService.deleteRuleName(rule);
		
		// THEN
		verify(ruleNameRepository).delete(rule);
	}

	@Test
	public void deleteRuleName_ShouldReturnRessourceNotFoundException() throws RessourceNotFoundException {
		// GIVEN
		RuleName rule = new RuleName();
		rule.setId(404);
		when(ruleNameRepository.existsById(404)).thenReturn(false);

		// WHEN // THEN
		assertThrows(RessourceNotFoundException.class, () -> {
			ruleNameService.deleteRuleName(rule);
		});
	}

}
