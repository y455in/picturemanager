package com.yn.picturebook.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.yn.picturebook.config.TestAppConfig;
import com.yn.picturebook.domain.Picture;
import com.yn.picturebook.services.IDataProvider;
import com.yn.picturebook.testutil.DataFactory;

import mockit.Deencapsulation;
import mockit.Mocked;

/**
 * Unit test for the picture Controller
 * 
 * @author Yassin.nachite
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestAppConfig.class)
public class PictureControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;
	private IDataProvider dataProvider;

	@Mocked
	public RestTemplate restTemplate;

	@Before
	public void setup() {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		this.mockMvc = builder.build();
		dataProvider = wac.getBean(IDataProvider.class);
		ReflectionTestUtils.setField(dataProvider, "picturesByAlbumIdRepo", DataFactory.getPicturesByAlbumIdRepo());
		ReflectionTestUtils.setField(dataProvider, "albumsMap", DataFactory.getAlbumsMap());
	}

	@Test
	public void testGetAllPicturesForAlbum() throws Exception {
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/albums/1/pictures");
		MvcResult result = this.mockMvc.perform(builder).andExpect(ok).andReturn();
		String content = result.getResponse().getContentAsString();
		assertTrue(content.split("\\},\\{").length == 2);
	}

	@Test
	public void testGetPicture() throws Exception {
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/albums/1/pictures/1");
		MvcResult result = this.mockMvc.perform(builder).andExpect(ok).andReturn();
		String content = result.getResponse().getContentAsString();
		assertTrue(content.contains("\"id\":1"));
	}

	@Test
	public void testAddPicture() throws Exception {
		ReflectionTestUtils.setField(dataProvider, "nextPictureId", 4);
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();

		Picture pic = DataFactory.getTestPicture(5, 1);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/albums/1/pictures");
		builder.contentType(MediaType.APPLICATION_JSON).content(pic.toString());
		this.mockMvc.perform(builder).andExpect(ok);

		Map<Integer, Map<Integer, Picture>> picturesByAlbumIdRepo = Deencapsulation.getField(dataProvider,
				"picturesByAlbumIdRepo");
		Map<Integer, Picture> pictures = picturesByAlbumIdRepo.get(1);
		assertNotNull(pictures.get(5));
	}

	@Test
	public void testUpdatePicture() throws Exception {
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();

		Picture pic = DataFactory.getTestPicture(1, 1);
		pic.setTitle("Updated Title");
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/albums/1/pictures/1");
		builder.contentType(MediaType.APPLICATION_JSON).content(pic.toString());
		this.mockMvc.perform(builder).andExpect(ok);

		Map<Integer, Map<Integer, Picture>> picturesByAlbumIdRepo = Deencapsulation.getField(dataProvider,
				"picturesByAlbumIdRepo");
		Map<Integer, Picture> pictures = picturesByAlbumIdRepo.get(1);
		assertEquals("Updated Title", pictures.get(1).getTitle());
	}

	@Test
	public void testDeletePicture() throws Exception {
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/albums/1/pictures/1");
		this.mockMvc.perform(builder).andExpect(ok);

		Map<Integer, Map<Integer, Picture>> picturesByAlbumIdRepo = Deencapsulation.getField(dataProvider,
				"picturesByAlbumIdRepo");
		Map<Integer, Picture> pictures = picturesByAlbumIdRepo.get(1);
		assertFalse(pictures.keySet().contains(1));
	}

}
