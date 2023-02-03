package com.nnk.springboot.controllers;

import java.util.List;

import javax.validation.Valid;

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

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.RessourceNotFoundException;
import com.nnk.springboot.services.BidListService;
import com.nnk.springboot.services.UserService;

/**
 * Controller for BidList.
 * 
 * @author Antoine Lanselle
 */
@Controller
@RequestMapping(path = "/bidList")
public class BidListController {

	private static final Logger LOGGER = LogManager.getLogger(BidListController.class);

	@Autowired
	private BidListService bidService;
	@Autowired
	private UserService userService;

	/**
	 * Return the template list of all Bids in DataBase.
	 * 
	 * @param Model for adding attributes.
	 * @return String the template path.
	 */
	@GetMapping(path = "/list")
	public String home(Model model) {
		String info = "GET - BidList List page.";
		LOGGER.info(info);
		
		User user = userService.getCurrentUser();
		
		model.addAttribute("userName", user.getUsername());
		model.addAttribute("role", user.getRole());
		model.addAttribute("bids", bidService.findAllBids());
		return "bidList/list";
	}

	/**
	 * Show the form to add a Bid.
	 * 
	 * @param BidList the new Bid to be completed by the user.
	 * @return String of the template path.
	 */
	@GetMapping(path = "/add")
	public String addBidForm(BidList bid) {
		String info = "GET - Creat BidList form.";
		LOGGER.info(info);

		return "bidList/add";
	}

	/**
	 * Add Bid in parameter in DataBase, and redirect to list.
	 * 
	 * @param BidList       the Bid you want to add in DataBase.
	 * @param BindingResult verification if BidList in parameter is valid with
	 *                      constraints.
	 * @param Model         for adding attributes.
	 * 
	 * @return String the template path.
	 */
	@PostMapping(path = "/validate")
	public String validateBid(@Valid BidList bid, BindingResult result, Model model) {
		String info = "POST - Add new BidList.";

		if (!result.hasErrors()) {
			LOGGER.info(info);

			bidService.addBidList(bid);
			model.addAttribute("bids", bidService.findAllBids());
			return "redirect:/bidList/list";
		} else {
			info = info + " Fail: BidList data is not valid.";
			LOGGER.info(info);

			return "bidList/add";
		}
	}

	/**
	 * Show update form for Bid with id in parameter.
	 * 
	 * @param Integer the id of the Bid you want to update.
	 * @param Model   for adding attributes.
	 * 
	 * @return String the template path.
	 * @throws RessourceNotFoundException when id in parameter is not in DataBase.
	 */
	@GetMapping(path = "/update/{id}")
	public String showUpdateBidForm(@PathVariable("id") Integer id, Model model) throws RessourceNotFoundException {
		String info = "GET - Update form for BidList " + id + ".";
		LOGGER.info(info);

		BidList bidList = bidService.findById(id);
		model.addAttribute("bid", bidList);
		return "bidList/update";
	}

	/**
	 * Update Bid with id in parameter in DataBase with Bid in parameter, and
	 * redirect to list.
	 * 
	 * @param Integer       the id of the Bid you want to update.
	 * @param BidList       the new Bid data to update.
	 * @param BindingResult verification if BidList in parameter is valid with
	 *                      constraints.
	 * @param Model         for adding attributes.
	 * 
	 * @return String the template path.
	 * @throws RessourceNotFoundException when id in parameter is not in DataBase.
	 */
	@PostMapping(path = "/update/{id}")
	public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList, BindingResult result, Model model)
			throws RessourceNotFoundException {
		String info = "POST - Update BidList " + id + ".";

		if (!result.hasErrors()) {
			LOGGER.info(info);

			bidList.setId(id);
			bidService.updateBidList(bidList);

			model.addAttribute("bids", bidService.findAllBids());
			return "redirect:/bidList/list";
		} else {
			info = info + " Fail: new BidList datas are not valid.";
			LOGGER.info(info);

			return "bidList/update";
		}
	}

	/**
	 * Delete Bid with id in parameter from DataBase, and redirect to list.
	 * 
	 * @param Integer the id of the Bid you want to delete.
	 * @param Model   for adding attributes.
	 * 
	 * @return String the template path.
	 * @throws RessourceNotFoundException when id in parameter is not in DataBase.
	 */
	@GetMapping(path = "/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id, Model model) throws RessourceNotFoundException {
		String info = "GET - Delete BidList " + id + ".";
		LOGGER.info(info);

		BidList bidList = bidService.findById(id);
		bidService.deleteBidList(bidList);

		model.addAttribute("bids", bidService.findAllBids());
		return "redirect:/bidList/list";
	}

	/**
	 * API request - Return all Bids in DataBase.
	 * 
	 * @return ResponseEntity<List<BidList> a response with https status OK and a
	 *         BidList List of all Bids in DataBase as body.
	 */
	@GetMapping(path = "/all")
	public ResponseEntity<List<BidList>> getBidsList() {
		String info = "API GET - BidList List of all Bids.";
		LOGGER.info(info);

		return ResponseEntity.status(HttpStatus.OK).body(bidService.findAllBids());
	}

	/**
	 * API request - Return the Bid with id in parameter.
	 * 
	 * @param Integer the id of the Bid you want to get.
	 * @return ResponseEntity<BidList> a response with https status OK and a BidList
	 *         as body.
	 * @throws RessourceNotFoundException when id in parameter is not in DataBase.
	 */
	@GetMapping()
	public ResponseEntity<BidList> getBid(@RequestParam(name = "id", required = true) Integer id)
			throws RessourceNotFoundException {
		String info = "API GET - BidList with id: " + id + ".";
		LOGGER.info(info);

		return ResponseEntity.status(HttpStatus.OK).body(bidService.findById(id));
	}

	/**
	 * API request - Add the Bid in parameter in DataBase and return https status OK
	 * with message.
	 * 
	 * @param BidList the Bid data to add in DataBase.
	 * @return ResponseEntity<String> response with https status OK and message as
	 *         body.
	 */
	@PostMapping()
	public ResponseEntity<String> addBidList(@RequestBody BidList bidList) {
		String info = "API POST - Add BidList.";
		LOGGER.info(info);

		bidService.addBidList(bidList);
		String msg = "BidList has been added in DataBase.";
		return ResponseEntity.status(HttpStatus.OK).body(msg);
	}

	/**
	 * API request - Update the Bid with id in parameter in DataBase with BidList
	 * data in parameter and return https status OK with message.
	 * 
	 * @param BidList the Bid data to put in DataBase.
	 * @return ResponseEntity<String> response with https status OK and message as
	 *         body.
	 * @throws RessourceNotFoundException when id in parameter is not in DataBase.
	 */
	@PutMapping()
	public ResponseEntity<String> updateBidList(@RequestBody BidList bidList) throws RessourceNotFoundException {
		String info = "API UPDATE - Update BidList.";
		LOGGER.info(info);

		bidService.updateBidList(bidList);
		String msg = "BidList has been updated in DataBase.";
		return ResponseEntity.status(HttpStatus.OK).body(msg);
	}

	/**
	 * API request - Delete Bid with id in parameter from DataBase.
	 * 
	 * @param Integer the id of the Bid you want to delete.
	 * @return ResponseEntity<String> response with https status OK and message as
	 *         body.
	 * @throws RessourceNotFoundException when id in parameter is not in DataBase.
	 * 
	 */
	@DeleteMapping()
	public ResponseEntity<String> deleteBidList(@RequestParam(name = "id", required = true) Integer id)
			throws RessourceNotFoundException {
		String info = "API DELETE - BidList with id: " + id + ".";
		LOGGER.info(info);

		bidService.deleteBidList(bidService.findById(id));
		String msg = "Bid with id: " + id + " has been delete from DataBase.";
		return ResponseEntity.status(HttpStatus.OK).body(msg);
	}
}
