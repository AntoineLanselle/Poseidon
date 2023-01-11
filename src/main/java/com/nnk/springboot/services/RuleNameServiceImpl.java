package com.nnk.springboot.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exceptions.RessourceNotFoundException;
import com.nnk.springboot.repositories.RuleNameRepository;

/**
 * Service implementation for RuleName.
 * 
 * @author Antoine Lanselle
 */
@Service
public class RuleNameServiceImpl implements RuleNameService {

	private static final Logger LOGGER = LogManager.getLogger(RuleNameServiceImpl.class);

	@Autowired
	private RuleNameRepository ruleNameRepository;

	/**
	 * Return all Rules in DataBase.
	 *
	 * @return List<RuleName> all the Rules in the DataBase.
	 */
	@Override
	public List<RuleName> findAllRules() {
		String info = "Returning all Rules from DataBase.";
		LOGGER.info(info);

		return ruleNameRepository.findAll();
	}

	/**
	 * Return specific Rule from DataBase with id in parameter.
	 *
	 * @param Integer the id of the Rule.
	 * @return RuleName with id in parameter in DataBase.
	 * @throw RessourceNotFoundException when id in parameter is not in DataBase.
	 */
	@Override
	public RuleName findById(Integer id) throws RessourceNotFoundException {
		String info = "Looking for Rule with id " + id + " in DataBase.";
		String error = "Fail: Rule with id " + id + " not found in DataBase.";
		LOGGER.info(info);

		return ruleNameRepository.findById(id).orElseThrow(() -> new RessourceNotFoundException(error));
	}

	/**
	 * Add Rule in parameter in DataBase.
	 *
	 * @param RuleName you want to add in DataBase.
	 * @return RuleName the added Rule.
	 */
	@Override
	public RuleName addRuleName(RuleName ruleName) {
		String info = "Adding Rule in DataBase.";
		LOGGER.info(info);

		return ruleNameRepository.save(ruleName);
	}

	/**
	 * Update Rule with Rule id in parameter in DataBase with Rule in parameter.
	 *
	 * @param RuleName the new Rule data we want to update.
	 * @return RuleName the updated Rule.
	 * @throw RessourceNotFoundException when Rule id in parameter is not in
	 *        DataBase.
	 */
	@Override
	public RuleName updateRuleName(RuleName ruleName) throws RessourceNotFoundException {
		String info = "Updating Rule in DataBase.";
		LOGGER.info(info);

		if (ruleNameRepository.existsById(ruleName.getId())) {
			return ruleNameRepository.save(ruleName);
		} else {
			String error = "Fail: Rule with id " + ruleName.getId() + " not found in DataBase.";
			throw new RessourceNotFoundException(error);
		}
	}

	/**
	 * Delete Rule with Rule id in parameter from DataBase.
	 *
	 * @param RuleName the Rule you want to delete from DataBase.
	 * @throw RessourceNotFoundException when Rule id in parameter is not in
	 *        DataBase.
	 */
	@Override
	public void deleteRuleName(RuleName ruleName) throws RessourceNotFoundException {
		String info = "Deleting Rule in DataBase.";
		LOGGER.info(info);

		if (ruleNameRepository.existsById(ruleName.getId())) {
			ruleNameRepository.delete(ruleName);
		} else {
			String error = "Fail: Rule with id " + ruleName.getId() + " not found in DataBase.";
			throw new RessourceNotFoundException(error);
		}
	}

}
