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
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@WithMockUser(username = "admin", roles = { "ADMIN" })
public class RuleNameIntegration {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private RuleNameRepository ruleNameRepository;

	private ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	public void setUp() {
		ruleNameRepository.deleteAll();

		RuleName ruleName = new RuleName();
		ruleName.setName("test");
		ruleName.setDescription("test");
		ruleName.setJson("test");
		ruleName.setTemplate("test");
		ruleName.setSqlStr("test");
		ruleName.setSqlPart("test");
		ruleNameRepository.save(ruleName);
	}

	@Test
	public void validateRuleName_shouldAddRuleNameInDatabase() throws Exception {
		// GIVEN
		List<RuleName> listBefore = ruleNameRepository.findAll();

		// WHEN
		mockMvc.perform(post("/ruleName/validate").param("name", "newTest").param("description", "newTest")
				.param("json", "newTest").param("template", "newTest").param("sqlStr", "newTest")
				.param("sqlPart", "newTest")).andExpect(status().is3xxRedirection());

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals(2, ruleNameRepository.findAll().size());
		assertEquals("newTest", ruleNameRepository.findAll().get(1).getName());
	}

	@Test
	public void updateRule_shouldUpdateRuleInDatabase() throws Exception {
		// GIVEN
		List<RuleName> listBefore = ruleNameRepository.findAll();

		// WHEN
		mockMvc.perform(post("/ruleName/update/{id}", listBefore.get(0).getId()).param("name", "newTest")
				.param("description", "newTest").param("json", "newTest").param("template", "newTest")
				.param("sqlStr", "newTest").param("sqlPart", "newTest")).andExpect(status().is3xxRedirection());

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals("newTest", ruleNameRepository.findById(listBefore.get(0).getId()).get().getName());
		assertEquals(1, ruleNameRepository.findAll().size());
	}

	@Test
	public void deleteRule_shouldDeleteRuleInDatabase() throws Exception {
		// GIVEN
		List<RuleName> listBefore = ruleNameRepository.findAll();

		// WHEN
		mockMvc.perform(get("/ruleName/delete/{id}", listBefore.get(0).getId())).andExpect(status().is3xxRedirection());
		// the id changes at each test so we don't put a constant variable

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals(0, ruleNameRepository.findAll().size());
	}

	@Test
	public void getRuleName_shouldReturnListOfRuleName() throws Exception {
		// GIVEN
		List<RuleName> listBefore = ruleNameRepository.findAll();

		// WHEN
		mockMvc.perform(get("/ruleName/all"));

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals(1, ruleNameRepository.findAll().size());
	}

	@Test
	public void addRuleName_shouldAddRuleNameInRepository() throws Exception {
		// GIVEN
		List<RuleName> listBefore = ruleNameRepository.findAll();

		RuleName ruleName = new RuleName();
		ruleName.setName("newTest");
		ruleName.setDescription("newTest");
		ruleName.setJson("newTest");
		ruleName.setTemplate("newTest");
		ruleName.setSqlStr("newTest");
		ruleName.setSqlPart("newTest");

		MediaType MEDIA_TYPE_JSON = new MediaType("application", "json");
		MockHttpServletRequestBuilder request = post("/ruleName");
		request.content(objectMapper.writeValueAsString(ruleName));
		request.accept(MEDIA_TYPE_JSON);
		request.contentType(MEDIA_TYPE_JSON);

		// WHEN
		mockMvc.perform(request).andExpect(status().isOk());

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals(2, ruleNameRepository.findAll().size());
		assertEquals("newTest", ruleNameRepository.findAll().get(1).getName());
	}

	@Test
	public void updateRuleName_shouldUpdateRuleNameInDatabase() throws Exception {
		// GIVEN
		List<RuleName> listBefore = ruleNameRepository.findAll();

		RuleName ruleName = new RuleName();
		ruleName.setId(listBefore.get(0).getId());
		ruleName.setName("newTest");
		ruleName.setDescription("newTest");
		ruleName.setJson("newTest");
		ruleName.setTemplate("newTest");
		ruleName.setSqlStr("newTest");
		ruleName.setSqlPart("newTest");

		MediaType MEDIA_TYPE_JSON = new MediaType("application", "json");
		MockHttpServletRequestBuilder request = put("/ruleName");
		request.content(objectMapper.writeValueAsString(ruleName));
		request.accept(MEDIA_TYPE_JSON);
		request.contentType(MEDIA_TYPE_JSON);

		// WHEN
		mockMvc.perform(request).andExpect(status().isOk());

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals("newTest", ruleNameRepository.findAll().get(0).getName());
		assertEquals(1, ruleNameRepository.findAll().size());
	}

	@Test
	public void deleteRuleName_shouldDeleteRuleNameInDatabase() throws Exception {
		// GIVEN
		List<RuleName> listBefore = ruleNameRepository.findAll();

		// WHEN
		mockMvc.perform(delete("/ruleName").param("id", listBefore.get(0).getId().toString()))
				.andExpect(status().isOk());
		// the id changes at each test so we don't put a constant variable

		// THEN
		assertEquals(1, listBefore.size());
		assertEquals(0, ruleNameRepository.findAll().size());
	}

}
