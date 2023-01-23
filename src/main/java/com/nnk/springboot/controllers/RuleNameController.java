package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exceptions.RessourceNotFoundException;
import com.nnk.springboot.services.RuleNameService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import javax.validation.Valid;

/**
 * Controller for RuleName.
 * 
 * @author Antoine Lanselle
 */
@Controller
@RequestMapping(path = "/ruleName")
public class RuleNameController {

	private static final Logger LOGGER = LogManager.getLogger(RuleNameController.class);

	@Autowired
	private RuleNameService ruleNameService;

	/**
	 * Return the template list of all Rules in DataBase.
	 * 
	 * @param Model for adding attributes.
	 * @return String the template path.
	 */
	@GetMapping(path = "/list")
	public String home(Model model) {
		String info = "GET - RuleName List page.";
		LOGGER.info(info);

		model.addAttribute("rules", ruleNameService.findAllRules());
		return "ruleName/list";
	}

	/**
	 * Show the form to add a Rule.
	 * 
	 * @param RuleName the new Rule to be completed by the user.
	 * @return String of the template path.
	 */
	@GetMapping(path = "/add")
	public String addRuleForm(RuleName ruleName) {
		String info = "GET - Creat RuleName form.";
		LOGGER.info(info);

		return "ruleName/add";
	}

	/**
	 * Add Rule in parameter in DataBase, and redirect to list.
	 * 
	 * @param RuleName      the Rule you want to add in DataBase.
	 * @param BindingResult verification if RuleName in parameter is valid with
	 *                      constraints.
	 * @param Model         for adding attributes.
	 * 
	 * @return String the template path.
	 */
	@PostMapping(path = "/validate")
	public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
		String info = "POST - Add new RuleName.";

		if (!result.hasErrors()) {
			LOGGER.info(info);

			ruleNameService.addRuleName(ruleName);
			model.addAttribute("rules", ruleNameService.findAllRules());
			return "redirect:/ruleName/list";
		} else {
			info = info + " Fail: RuleName data is not valid.";
			LOGGER.info(info);

			return "ruleName/add";
		}
	}

	/**
	 * Show update form for Rule with id in parameter.
	 * 
	 * @param Integer the id of the Rule you want to update.
	 * @param Model   for adding attributes.
	 * 
	 * @return String the template path.
	 * @throws RessourceNotFoundException when id in parameter is not in DataBase.
	 */
	@GetMapping(path = "/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) throws RessourceNotFoundException {
		String info = "GET - Update form for RuleName " + id + ".";
		LOGGER.info(info);

		RuleName ruleName = ruleNameService.findById(id);
		model.addAttribute("ruleName", ruleName);
		return "ruleName/update";
	}

	/**
	 * Update Rule with id in parameter in DataBase with Rule in parameter, and
	 * redirect to list.
	 * 
	 * @param Integer       the id of the Rule you want to update.
	 * @param RuleName      the new Rule data to update.
	 * @param BindingResult verification if Rule in parameter is valid with
	 *                      constraints.
	 * @param Model         for adding attributes.
	 * 
	 * @return String the template path.
	 * @throws RessourceNotFoundException when id in parameter is not in DataBase.
	 */
	@PostMapping(path = "/update/{id}")
	public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName, BindingResult result,
			Model model) throws RessourceNotFoundException {
		String info = "POST - Update RuleName " + id + ".";

		if (!result.hasErrors()) {
			LOGGER.info(info);

			ruleName.setId(id);
			ruleNameService.updateRuleName(ruleName);

			model.addAttribute("rules", ruleNameService.findAllRules());
			return "redirect:/ruleName/list";
		} else {
			info = info + " Fail: RuleName id is not valid.";
			LOGGER.info(info);

			return "ruleName/update";
		}
	}

	/**
	 * Delete Rule with id in parameter from DataBase, and redirect to list.
	 * 
	 * @param Integer the id of the Rule you want to delete.
	 * @param Model   for adding attributes.
	 * 
	 * @return String the template path.
	 * @throws RessourceNotFoundException when id in parameter is not in DataBase.
	 */
	@GetMapping(path = "/delete/{id}")
	public String deleteRuleName(@PathVariable("id") Integer id, Model model) throws RessourceNotFoundException {
		String info = "GET - Delete RuleName " + id + ".";
		LOGGER.info(info);

		RuleName ruleName = ruleNameService.findById(id);
		ruleNameService.deleteRuleName(ruleName);

		model.addAttribute("rules", ruleNameService.findAllRules());
		return "redirect:/ruleName/list";
	}

	/**
	 * API request - Return all Rules in DataBase.
	 * 
	 * @return ResponseEntity<List<RuleName> a response with https status OK and a
	 *         RuleName List of all Rules in DataBase as body.
	 */
	@GetMapping(path = "/all")
	public ResponseEntity<List<RuleName>> getRuleNames() {
		String info = "API GET - RuleName List of all Rules.";
		LOGGER.info(info);

		return ResponseEntity.status(HttpStatus.OK).body(ruleNameService.findAllRules());
	}

	/**
	 * API request - Return the Rule with id in parameter.
	 * 
	 * @param Integer the id of the Rule you want to get.
	 * @return ResponseEntity<RuleName> a response with https status OK and a
	 *         RuleName as body.
	 * @throws RessourceNotFoundException when id in parameter is not in DataBase.
	 */
	@GetMapping()
	public ResponseEntity<RuleName> getRule(@RequestParam(name = "id", required = true) Integer id)
			throws RessourceNotFoundException {
		String info = "API GET - RuleName with id: " + id + ".";
		LOGGER.info(info);

		return ResponseEntity.status(HttpStatus.OK).body(ruleNameService.findById(id));
	}

	/**
	 * API request - Add the Rule in parameter in DataBase and return https status
	 * OK with message.
	 * 
	 * @param RuleName the Rule data to add in DataBase.
	 * @return ResponseEntity<String> response with https status OK and message as
	 *         body.
	 */
	@PostMapping()
	public ResponseEntity<String> addRuleName(@RequestBody RuleName ruleName) {
		String info = "API POST - Add BidList.";
		LOGGER.info(info);

		ruleNameService.addRuleName(ruleName);
		String msg = "RuleName has been added in DataBase.";
		return ResponseEntity.status(HttpStatus.OK).body(msg);
	}

	/**
	 * API request - Update the Rule with id in parameter in DataBase with RuleName
	 * data in parameter and return https status OK with message.
	 * 
	 * @param RuleName the Rule data to put in DataBase.
	 * @param Integer  the id of the Rule you want to update.
	 * 
	 * @return ResponseEntity<String> response with https status OK and message as
	 *         body.
	 * @throws RessourceNotFoundException when id in parameter is not in DataBase.
	 */
	@PutMapping()
	public ResponseEntity<String> updateRuleName(@RequestBody RuleName ruleName,
			@RequestParam(name = "id", required = true) Integer id) throws RessourceNotFoundException {
		String info = "API UPDATE - Update RuleName.";
		LOGGER.info(info);

		ruleNameService.updateRuleName(ruleName);
		String msg = "RuleName has been updated in DataBase.";
		return ResponseEntity.status(HttpStatus.OK).body(msg);
	}

	/**
	 * API request - Delete Rule with id in parameter from DataBase.
	 * 
	 * @param Integer the id of the Rule you want to delete.
	 * @return ResponseEntity<String> response with https status OK and message as
	 *         body.
	 * @throws RessourceNotFoundException when id in parameter is not in DataBase.
	 * 
	 */
	@DeleteMapping()
	public ResponseEntity<String> deleteRuleName(@RequestParam(name = "id", required = true) Integer id)
			throws RessourceNotFoundException {
		String info = "API DELETE - RuleName with id: " + id + ".";
		LOGGER.info(info);

		ruleNameService.deleteRuleName(ruleNameService.findById(id));
		String msg = "Rule with id: " + id + " has been delete from DataBase.";
		return ResponseEntity.status(HttpStatus.OK).body(msg);
	}
}
