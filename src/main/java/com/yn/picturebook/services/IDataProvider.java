package com.yn.picturebook.services;

import java.util.List;

import com.yn.picturebook.domain.Album;
import com.yn.picturebook.domain.Picture;
import com.yn.picturebook.exceptions.AlbumNotEmptyException;

public interface IDataProvider {

	public void init();
	
	//Picture Methods
	public Picture getPictureFromAlbum(Integer albumId, Integer pictureId);
		
	public void addPictureToAlbum(Integer albumId, Picture picture);
	
	public void updatePictureInAlbum(Integer albumId, Integer pictureId, Picture picture);
	
	public void deletePictureInAlbum(Integer albumId, Integer pictureId);
	
	public List<Picture> getPicturesByAlbumId(Integer albumId);
	
	
	//Album Methods
	public Album getAlbumById(Integer albumId);
	
	public void addAlbum(Album album);
	
	public void updateAlbum(Integer albumId, Album album);
	
	public void deleteAlbum(Integer albumId) throws AlbumNotEmptyException;
	
	public List<Album> getAlbums();

	
}
