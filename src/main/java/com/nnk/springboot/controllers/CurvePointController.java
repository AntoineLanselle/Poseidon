package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.exceptions.RessourceNotFoundException;
import com.nnk.springboot.services.CurvePointService;

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
 * Controller for CurvePoint.
 * 
 * @author Antoine Lanselle
 */
@Controller
public class CurvePointController {

	private static final Logger LOGGER = LogManager.getLogger(CurvePointController.class);

	@Autowired
	private CurvePointService curvePointService;

	/**
	 * Return the template list of all Curves in DataBase.
	 * 
	 * @param Model for adding attributes.
	 * @return String the template path.
	 */
	@RequestMapping("/curvePoint/list")
	public String home(Model model) {
		String info = "REQUEST - CurvePoint List page.";
		LOGGER.info(info);

		model.addAttribute("curves", curvePointService.findAllCurves());
		return "curvePoint/list";
	}

	/**
	 * Show the form to add a Curve.
	 * 
	 * @param CurvePoint the new Curve to be completed by the user.
	 * @return String of the template path.
	 */
	@GetMapping("/curvePoint/add")
	public String addBidForm(CurvePoint curve) {
		String info = "GET - Creat CurvePoint form.";
		LOGGER.info(info);

		return "curvePoint/add";
	}

	/**
	 * Add Curve in parameter in DataBase, and redirect to list.
	 * 
	 * @param CurvePoint    the Curve you want to add in DataBase.
	 * @param BindingResult verification if CurvePoint in parameter is valid with
	 *                      constraints.
	 * @param Model         for adding attributes.
	 * 
	 * @return String the template path.
	 */
	@PostMapping("/curvePoint/validate")
	public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
		String info = "CREAT - Add new CurvePoint.";

		if (!result.hasErrors()) {
			LOGGER.info(info);

			curvePointService.addCurvePoint(curvePoint);
			model.addAttribute("curves", curvePointService.findAllCurves());
			return "redirect:/curvePoint/list";
		} else {
			info = info + " Fail: CurvePoint data is not valid.";
			LOGGER.info(info);

			return "curvePoint/add";
		}
	}

	/**
	 * Show update form for Curve with id in parameter.
	 * 
	 * @param Integer the id of the Curve you want to update.
	 * @param Model   for adding attributes.
	 * 
	 * @return String the template path.
	 * @throws RessourceNotFoundException when id in parameter is not in DataBase.
	 */
	@GetMapping("/curvePoint/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) throws RessourceNotFoundException {
		String info = "GET - Update form for CurvePoint " + id + ".";
		LOGGER.info(info);

		CurvePoint curvePoint = curvePointService.findById(id);
		model.addAttribute("curvePoint", curvePoint);
		return "curvePoint/update";
	}

	/**
	 * Update Curve with id in parameter in DataBase with Curve in parameter, and
	 * redirect to list.
	 * 
	 * @param Integer       the id of the Curve you want to update.
	 * @param CurvePoint    the new Curve data to update.
	 * @param BindingResult verification if CurvePoint in parameter is valid with
	 *                      constraints.
	 * @param Model         for adding attributes.
	 * 
	 * @return String the template path.
	 * @throws RessourceNotFoundException when id in parameter is not in DataBase.
	 */
	@PostMapping("/curvePoint/update/{id}")
	public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint, BindingResult result,
			Model model) throws RessourceNotFoundException {
		String info = "UPDATE - Update CurvePoint " + id + ".";

		if (!result.hasErrors()) {
			LOGGER.info(info);

			curvePoint.setId(id);
			curvePointService.updateCurvePoint(curvePoint);

			model.addAttribute("curves", curvePointService.findAllCurves());
			return "redirect:/curvePoint/list";
		} else {
			info = info + " Fail: CurvePoint id is not valid.";
			LOGGER.info(info);

			return "curvePoint/update";
		}
	}

	/**
	 * Delete Curve with id in parameter from DataBase, and redirect to list.
	 * 
	 * @param Integer the id of the Curve you want to delete.
	 * @param Model   for adding attributes.
	 * 
	 * @return String the template path.
	 * @throws RessourceNotFoundException when id in parameter is not in DataBase.
	 */
	@GetMapping("/curvePoint/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id, Model model) throws RessourceNotFoundException {
		String info = "DELETE - Delete CurvePoint " + id + ".";
		LOGGER.info(info);

		CurvePoint curvePoint = curvePointService.findById(id);
		curvePointService.deleteCurvePoint(curvePoint);

		model.addAttribute("curves", curvePointService.findAllCurves());
		return "redirect:/curvePoint/list";
	}
}
