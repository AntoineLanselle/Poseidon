package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.exceptions.RessourceNotFoundException;
import com.nnk.springboot.services.TradeService;

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
 * Controller for Trade.
 * 
 * @author Antoine Lanselle
 */
@Controller
@RequestMapping(path = "/trade")
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
	@GetMapping(path = "/list")
	public String home(Model model) {
		String info = "GET - Trade List page.";
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
	@GetMapping(path = "/add")
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
	@PostMapping(path = "/validate")
	public String validate(@Valid Trade trade, BindingResult result, Model model) {
		String info = "POST - Add new Trade.";

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
	@GetMapping(path = "/update/{id}")
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
	@PostMapping(path = "/update/{id}")
	public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade, BindingResult result, Model model)
			throws RessourceNotFoundException {
		String info = "POST - Update Trade " + id + ".";

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
	@GetMapping(path = "/delete/{id}")
	public String deleteTrade(@PathVariable("id") Integer id, Model model) throws RessourceNotFoundException {
		String info = "GET - Delete Trade " + id + ".";
		LOGGER.info(info);

		Trade trade = tradeService.findById(id);
		tradeService.deleteTrade(trade);

		model.addAttribute("trades", tradeService.findAllTrades());
		return "redirect:/trade/list";
	}
	
	/**
	 * API request - Return all Trades in DataBase.
	 * 
	 * @return ResponseEntity<List<Trade> a response with https status OK and a
	 *         Trade List of all Trades in DataBase as body.
	 */
	@GetMapping(path = "/all")
	public ResponseEntity<List<Trade>> getTrades() {
		String info = "API GET - Trade List of all Trades.";
		LOGGER.info(info);

		return ResponseEntity.status(HttpStatus.OK).body(tradeService.findAllTrades());
	}

	/**
	 * API request - Return the Trade with id in parameter.
	 * 
	 * @param Integer the id of the Trade you want to get.
	 * @return ResponseEntity<Trade> a response with https status OK and a Trade
	 *         as body.
	 * @throws RessourceNotFoundException when id in parameter is not in DataBase.
	 */
	@GetMapping()
	public ResponseEntity<Trade> getTrade(@RequestParam(name = "id", required = true) Integer id)
			throws RessourceNotFoundException {
		String info = "API GET - Trade with id: " + id + ".";
		LOGGER.info(info);

		return ResponseEntity.status(HttpStatus.OK).body(tradeService.findById(id));
	}

	/**
	 * API request - Add the Trade in parameter in DataBase and return https status OK
	 * with message.
	 * 
	 * @param Trade the Trade data to add in DataBase.
	 * @return ResponseEntity<String> response with https status OK and message as
	 *         body.
	 */
	@PostMapping()
	public ResponseEntity<String> addTrade(@RequestBody Trade trade) {
		String info = "API POST - Add Trade.";
		LOGGER.info(info);

		tradeService.addTrade(trade);
		String msg = "Trade has been added in DataBase.";
		return ResponseEntity.status(HttpStatus.OK).body(msg);
	}

	/**
	 * API request - Update the Trade with id in parameter in DataBase with Trade
	 * data in parameter and return https status OK with message.
	 * 
	 * @param Trade the Trade data to put in DataBase.
	 * @param Integer the id of the Trade you want to update.
	 * 
	 * @return ResponseEntity<String> response with https status OK and message as
	 *         body.
	 * @throws RessourceNotFoundException when id in parameter is not in DataBase.
	 */
	@PutMapping()
	public ResponseEntity<String> updateTrade(@RequestBody Trade trade,
			@RequestParam(name = "id", required = true) Integer id) throws RessourceNotFoundException {
		String info = "API UPDATE - Update Trade.";
		LOGGER.info(info);

		tradeService.updateTrade(trade);
		String msg = "Trade has been updated in DataBase.";
		return ResponseEntity.status(HttpStatus.OK).body(msg);
	}

	/**
	 * API request - Delete Trade with id in parameter from DataBase.
	 * 
	 * @param Integer the id of the Trade you want to delete.
	 * @return ResponseEntity<String> response with https status OK and message as
	 *         body.
	 * @throws RessourceNotFoundException when id in parameter is not in DataBase.
	 * 
	 */
	@DeleteMapping()
	public ResponseEntity<String> deleteTrade(@RequestParam(name = "id", required = true) Integer id)
			throws RessourceNotFoundException {
		String info = "API DELETE - Trade with id: " + id + ".";
		LOGGER.info(info);

		tradeService.deleteTrade(tradeService.findById(id));
		String msg = "Trade with id: " + id + " has been delete from DataBase.";
		return ResponseEntity.status(HttpStatus.OK).body(msg);
	}
}
