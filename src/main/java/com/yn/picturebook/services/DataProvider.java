package com.yn.picturebook.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.yn.picturebook.config.Constants;
import com.yn.picturebook.domain.Album;
import com.yn.picturebook.domain.Picture;
import com.yn.picturebook.domain.util.AlbumIdComparator;
import com.yn.picturebook.domain.util.PictureByAlbumIdComparator;
import com.yn.picturebook.exceptions.AlbumNotEmptyException;

/**
 * 
 * @author Yassin.Nachite
 *
 */
@Repository
public class DataProvider implements IDataProvider {

	private RestTemplate restTemplate;

	// Original Data
	private Album[] albums;
	private Picture[] pictures;

	private Map<Integer, Map<Integer, Picture>> picturesByAlbumIdRepo = new HashMap<>();
	private Map<Integer, Album> albumsMap = new HashMap<>();

	private String pictureUrl = Constants.PICTURE_URL;
	private String albumUrl = Constants.ALBUM_URL;

	private int nextPictureId = -1;
	private int nextAlbumId = -1;

	@Autowired
	public DataProvider(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
		init();
	}

	/**
	 * Initializes the picture manager with json data from pictureUrl and
	 * albumUrl
	 */
	public void init() {
		picturesByAlbumIdRepo = new HashMap<>();
		albumsMap = new HashMap<>();

		albums = restTemplate.getForObject(albumUrl, Album[].class);

		// If no albums were retrieved continue with an empty repo
		if (albums == null || albums.length == 0) {
			return;
		}
		Arrays.sort(albums, new AlbumIdComparator());
		for (Album album : albums) {
			albumsMap.put(album.getId(), album);
			if (album.getId() > nextAlbumId)
				nextAlbumId = album.getId();
		}

		pictures = restTemplate.getForObject(pictureUrl, Picture[].class);

		if (pictures == null || pictures.length == 0) {
			for (Album album : albums) {
				picturesByAlbumIdRepo.put(album.getId(), new HashMap<Integer, Picture>());
			}
			return;
		}
		Arrays.sort(pictures, new PictureByAlbumIdComparator());

		for (Picture picture : pictures) {
			if (picture.getId() > nextPictureId)
				nextPictureId = picture.getId();
		}

		// for each album id get all pictures.
		// NOTE: Only works as long as there mustn't be pictures that do not
		// belong to albums
		for (int i = 0, j = 0, k = 0; i < albums.length; i++) {
			while (k < pictures.length && pictures[k].getAlbumId() == albums[i].getId()) {
				k++;
			}
			if (j != k) {
				picturesByAlbumIdRepo.put(albums[i].getId(),
						createMapFromPictureArray(Arrays.copyOfRange(pictures, j, k)));
				j = k;
			}
		}
	}

	private Map<Integer, Picture> createMapFromPictureArray(Picture[] pics) {
		Map<Integer, Picture> result = new HashMap<>();
		for (Picture picture : pics) {
			result.put(picture.getId(), picture);
		}
		return result;
	}

	// Picture Methods
	public Picture getPictureFromAlbum(Integer albumId, Integer pictureId) {
		Map<Integer, Picture> pics = picturesByAlbumIdRepo.get(albumId);
		if (pics != null) {
			return pics.get(pictureId);
		}
		return null;
	}

	public void addPictureToAlbum(Integer albumId, Picture picture) {
		Map<Integer, Picture> pics = picturesByAlbumIdRepo.get(albumId);
		if (pics != null)
			pics.put(getNextPictureId(), picture);
	}

	public void updatePictureInAlbum(Integer albumId, Integer pictureId, Picture picture) {
		Map<Integer, Picture> pics = picturesByAlbumIdRepo.get(albumId);
		if (pics != null && pics.containsKey(pictureId)) {
			picture.setId(pictureId);
			pics.put(pictureId, picture);
		}
	}

	public void deletePictureInAlbum(Integer albumId, Integer pictureId) {
		Map<Integer, Picture> pics = picturesByAlbumIdRepo.get(albumId);
		if (pics != null && pics.containsKey(pictureId)) {
			pics.remove(pictureId);
		}
	}

	public List<Picture> getPicturesByAlbumId(Integer albumId) {
		Map<Integer, Picture> pics = picturesByAlbumIdRepo.get(albumId);
		if (pics != null)
			return new ArrayList<Picture>(pics.values());
		return null;
	}

	// Album Methods
	public Album getAlbumById(Integer albumId) {
		return albumsMap.get(albumId);
	}

	public void addAlbum(Album album) {
		album.setId(getNextAlbumId());
		albumsMap.put(album.getId(), album);
		picturesByAlbumIdRepo.put(album.getId(), new HashMap<Integer, Picture>());
	}

	public void updateAlbum(Integer albumId, Album newAlbum) {
		if (albumsMap.containsKey(albumId)) {
			newAlbum.setId(albumId);
			albumsMap.put(albumId, newAlbum);
		}
	}

	public void deleteAlbum(Integer albumId) throws AlbumNotEmptyException {
		albumsMap.remove(albumId);
		picturesByAlbumIdRepo.remove(albumId);
	}

	public List<Album> getAlbums() {
		return new ArrayList<Album>(albumsMap.values());
	}

	private synchronized Integer getNextPictureId() {
		return ++nextPictureId;
	}

	private synchronized Integer getNextAlbumId() {
		return ++nextAlbumId;
	}
}
