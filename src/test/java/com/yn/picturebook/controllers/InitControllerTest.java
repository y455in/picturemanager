package com.yn.picturebook.controllers;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.yn.picturebook.config.TestAppConfig;
import com.yn.picturebook.domain.Picture;
import com.yn.picturebook.services.IDataProvider;

import mockit.Deencapsulation;

/**
 * Unit test for the picture Controller
 * 
 * @author Yassin.nachite
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestAppConfig.class)
public class InitControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;
	private IDataProvider dataProvider;

	@Before
	public void setup() {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		this.mockMvc = builder.build();
		dataProvider = wac.getBean(IDataProvider.class);
		ReflectionTestUtils.setField(dataProvider, "picturesByAlbumIdRepo", new HashMap<>());
		ReflectionTestUtils.setField(dataProvider, "albumsMap", new HashMap<>());
	}

	@Test
	public void testInit() throws Exception {
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/init");
		this.mockMvc.perform(builder).andExpect(ok);

		Map<Integer, Map<Integer, Picture>> picturesByAlbumIdRepo = Deencapsulation.getField(dataProvider,
				"picturesByAlbumIdRepo");
		assertTrue(picturesByAlbumIdRepo.size() > 0);
	}
}
