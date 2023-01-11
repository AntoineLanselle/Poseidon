package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exceptions.RessourceNotFoundException;
import com.nnk.springboot.services.RuleNameService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * Controller for RuleName.
 * 
 * @author Antoine Lanselle
 */
@Controller
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
	@RequestMapping("/ruleName/list")
	public String home(Model model) {
		String info = "REQUEST - RuleName List page.";
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
	@GetMapping("/ruleName/add")
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
	@PostMapping("/ruleName/validate")
	public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
		String info = "CREAT - Add new RuleName.";

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
	@GetMapping("/ruleName/update/{id}")
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
	@PostMapping("/ruleName/update/{id}")
	public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName, BindingResult result,
			Model model) throws RessourceNotFoundException {
		String info = "UPDATE - Update RuleName " + id + ".";

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
	@GetMapping("/ruleName/delete/{id}")
	public String deleteRuleName(@PathVariable("id") Integer id, Model model) throws RessourceNotFoundException {
		String info = "DELETE - Delete RuleName " + id + ".";
		LOGGER.info(info);

		RuleName ruleName = ruleNameService.findById(id);
		ruleNameService.deleteRuleName(ruleName);

		model.addAttribute("rules", ruleNameService.findAllRules());
		return "redirect:/ruleName/list";
	}
}
