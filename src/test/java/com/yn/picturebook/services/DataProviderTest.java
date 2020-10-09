package com.yn.picturebook.services;

import com.yn.picturebook.config.Constants;
import com.yn.picturebook.domain.Album;
import com.yn.picturebook.domain.Picture;
import com.yn.picturebook.testutil.DataFactory;
import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Unit Test for data provider class.
 * <p>
 * Note: This test only illustrates writing test cases for the class. It is not
 * extensive. Especially it does not cover corner cases or negative tests.
 *
 * @author Yassin.Nachite
 */
@RunWith(JMockit.class)
public class DataProviderTest {

    private DataProvider dataProvider;

    @Mocked
    public RestTemplate restTemplate;

    @Before
    public void setup() {
        dataProvider = new DataProvider(restTemplate);
        ReflectionTestUtils.setField(dataProvider, "albums", DataFactory.getTestAlbums());
        ReflectionTestUtils.setField(dataProvider, "pictures", DataFactory.getTestPictures());
    }

    /**
     * Tests init method of data provider.
     */
    @Test
    public void testInit() {
        new Expectations() {
            {
                restTemplate.getForObject(Constants.ALBUM_URL, Album[].class);
                result = DataFactory.getTestAlbums();

                restTemplate.getForObject(Constants.PICTURE_URL, Picture[].class);
                result = DataFactory.getTestPictures();
            }
        };

        dataProvider.init();

        Map<Integer, Map<Integer, Picture>> picturesByAlbumIdRepo = Deencapsulation.getField(dataProvider,
                "picturesByAlbumIdRepo");
        assertNotNull(picturesByAlbumIdRepo);
        assertTrue(picturesByAlbumIdRepo.size() == 2);

        assertTrue(picturesByAlbumIdRepo.containsKey(1));
        Collection<Picture> pics = picturesByAlbumIdRepo.get(1).values();
        assertTrue(pics.contains(DataFactory.getTestPicture(1, 1)));
        assertTrue(pics.contains(DataFactory.getTestPicture(3, 1)));

        assertTrue(picturesByAlbumIdRepo.containsKey(2));
        pics = picturesByAlbumIdRepo.get(2).values();
        assertTrue(pics.contains(DataFactory.getTestPicture(2, 2)));
        assertTrue(pics.contains(DataFactory.getTestPicture(4, 2)));

        Map<Integer, Album> albumsMap = Deencapsulation.getField(dataProvider, "albumsMap");
        assertNotNull(albumsMap);
        assertTrue(albumsMap.size() == 2);
        assertTrue(albumsMap.containsKey(1));
        assertTrue(albumsMap.get(1) instanceof Album);
        assertEquals(((Album) albumsMap.get(1)).getId(), Integer.valueOf(1));

        assertTrue(albumsMap.containsKey(2));
        assertTrue(albumsMap.get(2) instanceof Album);
        assertEquals(((Album) albumsMap.get(2)).getId(), Integer.valueOf(2));

        int nextId = Deencapsulation.getField(dataProvider, "nextPictureId");
        assertEquals(nextId, 4);
    }

    /**
     * Tests init method of data provider with no albums available
     */
    @Test
    public void testInitNoAlbums() {
        new Expectations() {
            {
                restTemplate.getForObject(Constants.ALBUM_URL, Album[].class);
                result = new Album[]{};
            }
        };

        dataProvider.init();

        Map<Integer, Map<Integer, Picture>> picturesByAlbumIdRepo = Deencapsulation.getField(dataProvider,
                "picturesByAlbumIdRepo");
        assertNotNull(picturesByAlbumIdRepo);
        assertTrue(picturesByAlbumIdRepo.size() == 0);

        Map<Integer, Album> albumsMap = Deencapsulation.getField(dataProvider, "albumsMap");
        assertNotNull(albumsMap);
        assertTrue(albumsMap.size() == 0);
    }

    @Test
    public void testInitNoPic() {
        new Expectations() {
            {
                restTemplate.getForObject(Constants.ALBUM_URL, Album[].class);
                result = DataFactory.getTestAlbums();

                restTemplate.getForObject(Constants.PICTURE_URL, Picture[].class);
                result = new Picture[]{};
            }
        };

        dataProvider.init();

        Map<Integer, Map<Integer, Picture>> picturesByAlbumIdRepo = Deencapsulation.getField(dataProvider,
                "picturesByAlbumIdRepo");
        assertNotNull(picturesByAlbumIdRepo);
        assertTrue(picturesByAlbumIdRepo.size() == 2);

        assertTrue(picturesByAlbumIdRepo.containsKey(1));
        Collection<Picture> pics = picturesByAlbumIdRepo.get(1).values();
        assertTrue(pics.size() == 0);
        assertTrue(picturesByAlbumIdRepo.containsKey(2));
        pics = picturesByAlbumIdRepo.get(2).values();
        assertTrue(pics.size() == 0);

        Map<Integer, Album> albumsMap = Deencapsulation.getField(dataProvider, "albumsMap");
        assertNotNull(albumsMap);
        assertTrue(albumsMap.size() == 2);
        assertTrue(albumsMap.containsKey(1));
        assertTrue(albumsMap.get(1) instanceof Album);
        assertEquals(((Album) albumsMap.get(1)).getId(), Integer.valueOf(1));

        assertTrue(albumsMap.containsKey(2));
        assertTrue(albumsMap.get(2) instanceof Album);
        assertEquals(((Album) albumsMap.get(2)).getId(), Integer.valueOf(2));

    }
}
