package com.yn.picturebook.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
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
import org.springframework.web.util.NestedServletException;

import com.yn.picturebook.config.TestAppConfig;
import com.yn.picturebook.domain.Album;
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
public class AlbumControllerTest {

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
	public void testGetAllAlbums() throws Exception {
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/albums");
		MvcResult result = this.mockMvc.perform(builder).andExpect(ok).andReturn();
		String content = result.getResponse().getContentAsString();
		assertTrue(content.split("\\},\\{").length == 2);
	}

	@Test
	public void testGetAlbum() throws Exception {
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/albums/1");
		MvcResult result = this.mockMvc.perform(builder).andExpect(ok).andReturn();
		String content = result.getResponse().getContentAsString();
		assertTrue(content.contains("\"id\":1"));
	}

	@Test
	public void testAddAlbum() throws Exception {
		ReflectionTestUtils.setField(dataProvider, "nextAlbumId", 2);
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();

		Album album = DataFactory.getTestAlbum(3);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/albums");
		builder.contentType(MediaType.APPLICATION_JSON).content(album.toString());
		this.mockMvc.perform(builder).andExpect(ok);

		Map<Integer, Album> albumsMap = Deencapsulation.getField(dataProvider, "albumsMap");
		assertNotNull(albumsMap.get(3));
	}

	@Test
	public void testUpdateAlbum() throws Exception {
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();

		Album album = DataFactory.getTestAlbum(1);
		album.setTitle("Updated Title");
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/albums/1");
		builder.contentType(MediaType.APPLICATION_JSON).content(album.toString());
		this.mockMvc.perform(builder).andExpect(ok);

		Map<Integer, Album> albumsMap = Deencapsulation.getField(dataProvider, "albumsMap");
		assertEquals("Updated Title", albumsMap.get(1).getTitle());
	}

	@Test
	public void testDeleteAlbum_Success() throws Exception {
		Map<Integer, Map<Integer, Picture>> myPicturesByAlbumIdRepo = new HashMap<>();
		myPicturesByAlbumIdRepo.put(1, new HashMap<>());
		ReflectionTestUtils.setField(dataProvider, "picturesByAlbumIdRepo", myPicturesByAlbumIdRepo);
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/albums/1");
		this.mockMvc.perform(builder).andExpect(ok);

		Map<Integer, Album> albumsMap = Deencapsulation.getField(dataProvider, "albumsMap");
		assertFalse(albumsMap.keySet().contains(1));
	}

	@Test(expected = NestedServletException.class)
	public void testDeleteAlbum_Fail() throws Exception {
		ResultMatcher ok = MockMvcResultMatchers.status().isBadRequest();

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/albums/1");
		this.mockMvc.perform(builder).andExpect(ok);
	}
}
