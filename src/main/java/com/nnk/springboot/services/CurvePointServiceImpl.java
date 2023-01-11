package com.nnk.springboot.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.exceptions.RessourceNotFoundException;
import com.nnk.springboot.repositories.CurvePointRepository;

/**
 * Service implementation for CurvePoint.
 * 
 * @author Antoine Lanselle
 */
@Service
public class CurvePointServiceImpl implements CurvePointService {

	private static final Logger LOGGER = LogManager.getLogger(CurvePointServiceImpl.class);

	@Autowired
	private CurvePointRepository curvePointRepository;

	/**
	 * Return all Curves in DataBase.
	 *
	 * @return List<CurvePoint> all the Curves in the DataBase.
	 */
	@Override
	public List<CurvePoint> findAllCurves() {
		String info = "Returning all Curves from DataBase.";
		LOGGER.info(info);

		return curvePointRepository.findAll();
	}

	/**
	 * Return specific Curve from DataBase with id in parameter.
	 *
	 * @param Integer the id of the Bid.
	 * @return CurvePoint with id in parameter in DataBase.
	 * @throw RessourceNotFoundException when id in parameter is not in DataBase.
	 */
	@Override
	public CurvePoint findById(Integer id) throws RessourceNotFoundException {
		String info = "Looking for Curve with id " + id + " in DataBase.";
		String error = "Fail: Curve with id " + id + " not found in DataBase.";
		LOGGER.info(info);

		return curvePointRepository.findById(id).orElseThrow(() -> new RessourceNotFoundException(error));
	}

	/**
	 * Add Curve in parameter in DataBase.
	 *
	 * @param CurvePoint you want to add in DataBase.
	 * @return CurvePoint the added Curve.
	 */
	@Override
	public CurvePoint addCurvePoint(CurvePoint curvePoint) {
		String info = "Adding Curve in DataBase.";
		LOGGER.info(info);

		return curvePointRepository.save(curvePoint);
	}

	/**
	 * Update Curve with Curve id in parameter in DataBase with Curve in parameter.
	 *
	 * @param CurvePoint the new Curve data we want to update.
	 * @return CurvePoint the updated Curve.
	 * @throw RessourceNotFoundException when Curve id in parameter is not in
	 *        DataBase.
	 */
	@Override
	public CurvePoint updateCurvePoint(CurvePoint curvePoint) throws RessourceNotFoundException {
		String info = "Updating Curve in DataBase.";
		LOGGER.info(info);

		if (curvePointRepository.existsById(curvePoint.getId())) {
			return curvePointRepository.save(curvePoint);
		} else {
			String error = "Fail: Curve with id " + curvePoint.getId() + " not found in DataBase.";
			throw new RessourceNotFoundException(error);
		}
	}

	/**
	 * Delete Curve with Curve id in parameter from DataBase.
	 *
	 * @param CurvePoint the Curve you want to delete from DataBase.
	 * @throw RessourceNotFoundException when Curve id in parameter is not in
	 *        DataBase.
	 */
	@Override
	public void deleteCurvePoint(CurvePoint curvePoint) throws RessourceNotFoundException {
		String info = "Deleting Curve in DataBase.";
		LOGGER.info(info);

		if (curvePointRepository.existsById(curvePoint.getId())) {
			curvePointRepository.delete(curvePoint);
		} else {
			String error = "Fail: Curve with id " + curvePoint.getId() + " not found in DataBase.";
			throw new RessourceNotFoundException(error);
		}
	}

}
