package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exceptions.RessourceNotFoundException;

/**
 * Service for RuleName.
 * 
 * @author Antoine Lanselle
 */
public interface RuleNameService {

	public List<RuleName> findAllRules();

	public RuleName findById(Integer id) throws RessourceNotFoundException;

	public RuleName addRuleName(RuleName ruleName);

	public RuleName updateRuleName(RuleName ruleName) throws RessourceNotFoundException;

	public void deleteRuleName(RuleName ruleName) throws RessourceNotFoundException;

}
