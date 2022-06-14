package com.yn.picturebook.services;

import java.util.List;

import com.yn.picturebook.domain.Album;
import com.yn.picturebook.domain.Picture;
import com.yn.picturebook.exceptions.AlbumNotEmptyException;

public interface IServiceFacade {

	public void init();

	public List<Picture> getAllPicturesAlbumId(Integer albumId);

	public Picture getPictureFromAlbum(Integer albumId, Integer pictureId);

	public void updatePictureFromAlbum(Integer albumId, Integer pictureId, Picture newPicture);

	public void addPictureToAlbum(Integer albumId, Picture newPicture);

	public void deletePictureFromAlbum(Integer albumId, Integer pictureId);

	public Album getAlbumById(Integer albumId);

	public void addAlbum(Album album);

	public void updateAlbum(Integer albumId, Album album);

	public void deleteAlbum(Integer albumId) throws AlbumNotEmptyException;

	public List<Album> getAlbums();
}
