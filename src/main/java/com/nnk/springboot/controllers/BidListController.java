package com.nnk.springboot.controllers;

import javax.validation.Valid;

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

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exceptions.RessourceNotFoundException;
import com.nnk.springboot.services.BidListService;

/**
 * Controller for BidList.
 * 
 * @author Antoine Lanselle
 */
@Controller
public class BidListController {

	private static final Logger LOGGER = LogManager.getLogger(BidListController.class);

	@Autowired
	private BidListService bidService;

	/**
	 * Return the template list of all Bids in DataBase.
	 * 
	 * @param Model for adding attributes.
	 * @return String the template path.
	 */
	@RequestMapping("/bidList/list")
	public String home(Model model) {
		String info = "REQUEST - BidList List page.";
		LOGGER.info(info);

		model.addAttribute("bids", bidService.findAllBids());
		return "bidList/list";
	}

	/**
	 * Show the form to add a Bid.
	 * 
	 * @param BidList the new Bid to be completed by the user.
	 * @return String of the template path.
	 */
	@GetMapping("/bidList/add")
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
	@PostMapping("/bidList/validate")
	public String validate(@Valid BidList bid, BindingResult result, Model model) {
		String info = "CREAT - Add new BidList.";

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
	@GetMapping("/bidList/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) throws RessourceNotFoundException {
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
	@PostMapping("/bidList/update/{id}")
	public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList, BindingResult result, Model model)
			throws RessourceNotFoundException {
		String info = "UPDATE - Update BidList " + id + ".";

		if (!result.hasErrors()) {
			LOGGER.info(info);

			bidList.setId(id);
			bidService.updateBidList(bidList);

			model.addAttribute("bids", bidService.findAllBids());
			return "redirect:/bidList/list";
		} else {
			info = info + " Fail: BidList id is not valid.";
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
	@GetMapping("/bidList/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id, Model model) throws RessourceNotFoundException {
		String info = "DELETE - Delete BidList " + id + ".";
		LOGGER.info(info);

		BidList bidList = bidService.findById(id);
		bidService.deleteBidList(bidList);

		model.addAttribute("bids", bidService.findAllBids());
		return "redirect:/bidList/list";
	}
}
