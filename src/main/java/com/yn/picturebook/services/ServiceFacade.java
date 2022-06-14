package com.yn.picturebook.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yn.picturebook.domain.Album;
import com.yn.picturebook.domain.Picture;
import com.yn.picturebook.exceptions.AlbumNotEmptyException;

/**
 * Service facade to encapsulate services and delegate calls to them.
 * 
 * @author Yassin.nachite
 *
 */
@Component
public class ServiceFacade implements IServiceFacade{

	@Autowired
	private IPictureService pictureService;

	@Autowired
	private IAlbumService albumService;

	@Autowired
	private IInitService initService;
	
	// ------- INIT SERVICE CALLS ---------	
	public void init(){
		initService.init();
	}
	
	// ------- PICTURE SERVICE CALLS ---------
	public List<Picture> getAllPicturesAlbumId(Integer albumId) {
		return pictureService.getPicturesAlbumId(albumId);
	}

	public Picture getPictureFromAlbum(Integer albumId, Integer pictureId) {
		return pictureService.getPictureFromAlbum(albumId, pictureId);
	}

	public void updatePictureFromAlbum(Integer albumId, Integer pictureId, Picture newPicture) {
		pictureService.updatePictureFromAlbum(albumId, pictureId, newPicture);
	}

	public void addPictureToAlbum(Integer albumId, Picture newPicture) {
		pictureService.addPictureToAlbum(albumId, newPicture);
	}

	public void deletePictureFromAlbum(Integer albumId, Integer pictureId) {
		pictureService.deletePictureFromAlbum(albumId, pictureId);
	}

	// ------- ALBUM SERVICE CALLS ---------
	public Album getAlbumById(Integer albumId) {
		return albumService.getAlbumById(albumId);
	}

	public void addAlbum(Album album) {
		albumService.addAlbum(album);
	}

	public void updateAlbum(Integer albumId, Album album) {
		albumService.updateAlbum(albumId, album);
	}

	public void deleteAlbum(Integer albumId) throws AlbumNotEmptyException {
		albumService.deleteAlbum(albumId);
	}

	public List<Album> getAlbums() {
		return albumService.getAlbums();
	}
}
