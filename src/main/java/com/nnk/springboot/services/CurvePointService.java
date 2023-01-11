package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.exceptions.RessourceNotFoundException;

/**
 * Service for CurvePoint.
 * 
 * @author Antoine Lanselle
 */
public interface CurvePointService {

	public List<CurvePoint> findAllCurves();

	public CurvePoint findById(Integer id) throws RessourceNotFoundException;

	public CurvePoint addCurvePoint(CurvePoint curvePoint);

	public CurvePoint updateCurvePoint(CurvePoint curvePoint) throws RessourceNotFoundException;

	public void deleteCurvePoint(CurvePoint curvePoint) throws RessourceNotFoundException;
	
}
