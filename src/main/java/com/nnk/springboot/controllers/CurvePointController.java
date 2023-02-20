package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.RessourceNotFoundException;
import com.nnk.springboot.services.CurvePointService;
import com.nnk.springboot.services.UserService;

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
 * Controller for CurvePoint.
 * 
 * @author Antoine Lanselle
 */
@Controller
@RequestMapping(path = "/curvePoint")
public class CurvePointController {

	private static final Logger LOGGER = LogManager.getLogger(CurvePointController.class);

	@Autowired
	private CurvePointService curvePointService;
	@Autowired
	private UserService userService;

	/**
	 * Return the template list of all Curves in DataBase.
	 * 
	 * @param Model for adding attributes.
	 * @return String the template path.
	 */
	@GetMapping(path = "/list")
	public String showCurves(Model model) {
		String info = "GET - CurvePoint List page.";
		LOGGER.info(info);

		User user = userService.getCurrentUser();

		model.addAttribute("userName", user.getUsername());
		model.addAttribute("role", user.getRole());
		model.addAttribute("curves", curvePointService.findAllCurves());
		return "curvePoint/list";
	}

	/**
	 * Show the form to add a Curve.
	 * 
	 * @param CurvePoint the new Curve to be completed by the user.
	 * @return String of the template path.
	 */
	@GetMapping(path = "/add")
	public String addCurveForm(CurvePoint curve) {
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
	@PostMapping(path = "/validate")
	public String validateCurve(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
		String info = "POST - Add new CurvePoint.";

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
	@GetMapping(path = "/update/{id}")
	public String showUpdateCurveForm(@PathVariable("id") Integer id, Model model) throws RessourceNotFoundException {
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
	@PostMapping(path = "/update/{id}")
	public String updateCurve(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint, BindingResult result,
			Model model) throws RessourceNotFoundException {
		String info = "POST - Update CurvePoint " + id + ".";

		if (!result.hasErrors()) {
			LOGGER.info(info);

			curvePoint.setId(id);
			curvePointService.updateCurvePoint(curvePoint);

			model.addAttribute("curves", curvePointService.findAllCurves());
			return "redirect:/curvePoint/list";
		} else {
			info = info + " Fail: new CurvePoint datas are not valid.";
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
	@GetMapping(path = "/delete/{id}")
	public String deleteCurve(@PathVariable("id") Integer id, Model model) throws RessourceNotFoundException {
		String info = "GET - Delete CurvePoint " + id + ".";
		LOGGER.info(info);

		CurvePoint curvePoint = curvePointService.findById(id);
		curvePointService.deleteCurvePoint(curvePoint);

		model.addAttribute("curves", curvePointService.findAllCurves());
		return "redirect:/curvePoint/list";
	}

	/**
	 * API request - Return all Curves in DataBase.
	 * 
	 * @return ResponseEntity<List<CurvePoint> a response with https status OK and a
	 *         CurvePoint List of all Curves in DataBase as body.
	 */
	@GetMapping(path = "/all")
	public ResponseEntity<List<CurvePoint>> getCurvePoints() {
		String info = "API GET - CurvePoint List of all Curves.";
		LOGGER.info(info);

		return ResponseEntity.status(HttpStatus.OK).body(curvePointService.findAllCurves());
	}

	/**
	 * API request - Return the Curve with id in parameter.
	 * 
	 * @param Integer the id of the Curve you want to get.
	 * @return ResponseEntity<CurvePoint> a response with https status OK and a
	 *         CurvePoint as body.
	 * @throws RessourceNotFoundException when id in parameter is not in DataBase.
	 */
	@GetMapping()
	public ResponseEntity<CurvePoint> getCurve(@RequestParam(name = "id", required = true) Integer id)
			throws RessourceNotFoundException {
		String info = "API GET - CurvePoint with id: " + id + ".";
		LOGGER.info(info);

		return ResponseEntity.status(HttpStatus.OK).body(curvePointService.findById(id));
	}

	/**
	 * API request - Add the Curve in parameter in DataBase and return https status
	 * OK with message or CONFLICT if curve data is not valid..
	 * 
	 * @param CurvePoint the Curve data to add in DataBase.
	 * @return ResponseEntity<String> response with https status OK and message as
	 *         body or CONFLICT if curve data is not valid..
	 */
	@PostMapping()
	public ResponseEntity<String> addCurvePoint(@RequestBody @Valid CurvePoint curvePoint, BindingResult result) {
		String info = "API POST - Add CurvePoint.";

		if (!result.hasErrors()) {
			LOGGER.info(info);

			curvePointService.addCurvePoint(curvePoint);
			String msg = "CurvePoint has been added in DataBase.";
			return ResponseEntity.status(HttpStatus.OK).body(msg);
		} else {
			String msg = "Fail: Curve data is not valid.";
			info = info + msg;
			LOGGER.info(info);

			return ResponseEntity.status(HttpStatus.CONFLICT).body(msg);
		}
	}

	/**
	 * API request - Update the Curve with id in parameter in DataBase with
	 * CurvePoint data in parameter and return https status OK with message or
	 * CONFLICT if curve data is not valid..
	 * 
	 * @param CurvePoint the Curve data to put in DataBase.
	 * @return ResponseEntity<String> response with https status OK and message as
	 *         body or CONFLICT if curve data is not valid..
	 * @throws RessourceNotFoundException when id in parameter is not in DataBase.
	 */
	@PutMapping()
	public ResponseEntity<String> updateCurvePoint(@RequestBody @Valid CurvePoint curvePoint, BindingResult result)
			throws RessourceNotFoundException {
		String info = "API UPDATE - Update CurvePoint.";

		if (!result.hasErrors()) {
			LOGGER.info(info);

			curvePointService.updateCurvePoint(curvePoint);
			String msg = "CurvePoint has been updated in DataBase.";
			return ResponseEntity.status(HttpStatus.OK).body(msg);
		} else {
			String msg = "Fail: Curve data is not valid.";
			info = info + msg;
			LOGGER.info(info);

			return ResponseEntity.status(HttpStatus.CONFLICT).body(msg);
		}
	}

	/**
	 * API request - Delete Curve with id in parameter from DataBase.
	 * 
	 * @param Integer the id of the Curve you want to delete.
	 * @return ResponseEntity<String> response with https status OK and message as
	 *         body.
	 * @throws RessourceNotFoundException when id in parameter is not in DataBase.
	 * 
	 */
	@DeleteMapping()
	public ResponseEntity<String> deleteCurvePoint(@RequestParam(name = "id", required = true) Integer id)
			throws RessourceNotFoundException {
		String info = "API DELETE - CurvePoint with id: " + id + ".";
		LOGGER.info(info);

		curvePointService.deleteCurvePoint(curvePointService.findById(id));
		String msg = "Curve with id: " + id + " has been delete from DataBase.";
		return ResponseEntity.status(HttpStatus.OK).body(msg);
	}
}
