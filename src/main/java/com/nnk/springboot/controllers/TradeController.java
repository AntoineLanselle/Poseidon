package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.exceptions.RessourceNotFoundException;
import com.nnk.springboot.services.TradeService;

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
 * Controller for Trade.
 * 
 * @author Antoine Lanselle
 */
@Controller
public class TradeController {

	private static final Logger LOGGER = LogManager.getLogger(TradeController.class);

	@Autowired
	private TradeService tradeService;

	/**
	 * Return the template list of all Trades in DataBase.
	 * 
	 * @param Model for adding attributes.
	 * @return String the template path.
	 */
	@RequestMapping("/trade/list")
	public String home(Model model) {
		String info = "REQUEST - Trade List page.";
		LOGGER.info(info);

		model.addAttribute("trades", tradeService.findAllTrades());
		return "trade/list";
	}

	/**
	 * Show the form to add a Trade.
	 * 
	 * @param Trade the new Trade to be completed by the user.
	 * @return String of the template path.
	 */
	@GetMapping("/trade/add")
	public String addUser(Trade trade) {
		String info = "GET - Creat Trade form.";
		LOGGER.info(info);

		return "trade/add";
	}

	/**
	 * Add Trade in parameter in DataBase, and redirect to list.
	 * 
	 * @param Trade         the Trade you want to add in DataBase.
	 * @param BindingResult verification if Trade in parameter is valid with
	 *                      constraints.
	 * @param Model         for adding attributes.
	 * 
	 * @return String the template path.
	 */
	@PostMapping("/trade/validate")
	public String validate(@Valid Trade trade, BindingResult result, Model model) {
		String info = "CREAT - Add new Trade.";

		if (!result.hasErrors()) {
			LOGGER.info(info);

			tradeService.addTrade(trade);
			model.addAttribute("trades", tradeService.findAllTrades());
			return "redirect:/trade/list";
		} else {
			info = info + " Fail: Trade data is not valid.";
			LOGGER.info(info);

			return "trade/add";
		}
	}

	/**
	 * Show update form for Trade with id in parameter.
	 * 
	 * @param Integer the id of the Trade you want to update.
	 * @param Model   for adding attributes.
	 * 
	 * @return String the template path.
	 * @throws RessourceNotFoundException when id in parameter is not in DataBase.
	 */
	@GetMapping("/trade/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) throws RessourceNotFoundException {
		String info = "GET - Update form for Trade " + id + ".";
		LOGGER.info(info);

		Trade trade = tradeService.findById(id);
		model.addAttribute("trade", trade);
		return "trade/update";
	}

	/**
	 * Update Trade with id in parameter in DataBase with Trade in parameter, and
	 * redirect to list.
	 * 
	 * @param Integer       the id of the Trade you want to update.
	 * @param Trade         the new Trade data to update.
	 * @param BindingResult verification if Trade in parameter is valid with
	 *                      constraints.
	 * @param Model         for adding attributes.
	 * 
	 * @return String the template path.
	 * @throws RessourceNotFoundException when id in parameter is not in DataBase.
	 */
	@PostMapping("/trade/update/{id}")
	public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade, BindingResult result, Model model)
			throws RessourceNotFoundException {
		String info = "UPDATE - Update Trade " + id + ".";

		if (!result.hasErrors()) {
			LOGGER.info(info);

			trade.setId(id);
			tradeService.updateTrade(trade);

			model.addAttribute("trades", tradeService.findAllTrades());
			return "redirect:/trade/list";
		} else {
			info = info + " Fail: Trade id is not valid.";
			LOGGER.info(info);

			return "trade/update";
		}
	}

	/**
	 * Delete Trade with id in parameter from DataBase, and redirect to list.
	 * 
	 * @param Integer the id of the Trade you want to delete.
	 * @param Model   for adding attributes.
	 * 
	 * @return String the template path.
	 * @throws RessourceNotFoundException when id in parameter is not in DataBase.
	 */
	@GetMapping("/trade/delete/{id}")
	public String deleteTrade(@PathVariable("id") Integer id, Model model) throws RessourceNotFoundException {
		String info = "DELETE - Delete Trade " + id + ".";
		LOGGER.info(info);

		Trade trade = tradeService.findById(id);
		tradeService.deleteTrade(trade);

		model.addAttribute("trades", tradeService.findAllTrades());
		return "redirect:/trade/list";
	}
}
