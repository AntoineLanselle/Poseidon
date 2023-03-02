package com.nnk.springboot.integration;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@WithMockUser(username = "admin", roles = { "ADMIN" })
public class CurvePointIntegration {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CurvePointRepository curveRepository;

	private ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	public void setUp() {
		curveRepository.deleteAll();

		CurvePoint curvePoint = new CurvePoint();
		curvePoint.setCurveId(1);
		curvePoint.setTerm(2.0);
		curvePoint.setValue(2.0);
		curveRepository.save(curvePoint);
	}

	@Test
	public void validateCurve_shouldAddCurveInDatabase() throws Exception {
		// GIVEN
		List<CurvePoint> listBefore = curveRepository.findAll();

		// WHEN
		mockMvc.perform(post("/curvePoint/validate").param("curveId", "2").param("term", "2.0").param("value", "2.0"))
				.andExpect(status().is3xxRedirection());

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals(2, curveRepository.findAll().size());
		assertEquals(Integer.valueOf(2), curveRepository.findAll().get(1).getCurveId());
	}

	@Test
	public void updateCurve_shouldUpdateCurveInDatabase() throws Exception {
		// GIVEN
		List<CurvePoint> listBefore = curveRepository.findAll();

		// WHEN
		mockMvc.perform(post("/curvePoint/update/{id}", listBefore.get(0).getId()).param("curveId", "2")
				.param("term", "2.0").param("value", "2.0")).andExpect(status().is3xxRedirection());

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals(Integer.valueOf(2), curveRepository.findById(listBefore.get(0).getId()).get().getCurveId());
		assertEquals(1, curveRepository.findAll().size());
	}

	@Test
	public void deleteCurve_shouldDeleteCurveInDatabase() throws Exception {
		// GIVEN
		List<CurvePoint> listBefore = curveRepository.findAll();

		// WHEN
		mockMvc.perform(get("/curvePoint/delete/{id}", listBefore.get(0).getId()))
				.andExpect(status().is3xxRedirection());
		// the id changes at each test so we don't put a constant variable

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals(0, curveRepository.findAll().size());
	}

	@Test
	public void getCurvePoint_shouldReturnListOfCurvePoint() throws Exception {
		// GIVEN
		List<CurvePoint> listBefore = curveRepository.findAll();

		// WHEN
		mockMvc.perform(get("/curvePoint/all"));

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals(1, curveRepository.findAll().size());
	}

	@Test
	public void addCurvePoint_shouldAddCurvePointInRepository() throws Exception {
		// GIVEN
		List<CurvePoint> listBefore = curveRepository.findAll();

		CurvePoint curvePointEntity = new CurvePoint();
		curvePointEntity.setCurveId(2);
		curvePointEntity.setTerm(2.0);
		curvePointEntity.setValue(2.0);

		MediaType MEDIA_TYPE_JSON = new MediaType("application", "json");
		MockHttpServletRequestBuilder request = post("/curvePoint");
		request.content(objectMapper.writeValueAsString(curvePointEntity));
		request.accept(MEDIA_TYPE_JSON);
		request.contentType(MEDIA_TYPE_JSON);

		// WHEN
		mockMvc.perform(request).andExpect(status().isOk());

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals(2, curveRepository.findAll().size());
		assertEquals(Integer.valueOf(2), curveRepository.findAll().get(1).getCurveId());
	}

	@Test
	public void updateCurvePoint_shouldUpdateCurvePointInDatabase() throws Exception {
		// GIVEN
		List<CurvePoint> listBefore = curveRepository.findAll();

		CurvePoint curvePointEntity = new CurvePoint();
		curvePointEntity.setId(listBefore.get(0).getId());
		curvePointEntity.setCurveId(10);
		curvePointEntity.setTerm(2.0);
		curvePointEntity.setValue(2.0);

		MediaType MEDIA_TYPE_JSON = new MediaType("application", "json");
		MockHttpServletRequestBuilder request = put("/curvePoint");
		request.content(objectMapper.writeValueAsString(curvePointEntity));
		request.accept(MEDIA_TYPE_JSON);
		request.contentType(MEDIA_TYPE_JSON);

		// WHEN
		mockMvc.perform(request).andExpect(status().isOk());

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals(Integer.valueOf(10), curveRepository.findAll().get(0).getCurveId());
		assertEquals(1, curveRepository.findAll().size());
	}

	@Test
	public void deleteCurvePoint_shouldDeleteCurvePointInDatabase() throws Exception {
		// GIVEN
		List<CurvePoint> listBefore = curveRepository.findAll();

		// WHEN
		mockMvc.perform(delete("/curvePoint").param("id", listBefore.get(0).getId().toString()))
				.andExpect(status().isOk());
		// the id changes at each test so we don't put a constant variable

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals(0, curveRepository.findAll().size());
	}

}
