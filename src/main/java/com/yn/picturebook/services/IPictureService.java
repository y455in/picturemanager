package com.yn.picturebook.services;

import java.util.List;

import com.yn.picturebook.domain.Picture;

public interface IPictureService {

	// public Future<List<Picture>> getPicturesAlbumId(Integer albumId);
	//
	// public Future<Picture> getPictureFromAlbum(Integer albumId, Integer
	// pictureId);

	public List<Picture> getPicturesAlbumId(Integer albumId);

	public Picture getPictureFromAlbum(Integer albumId, Integer pictureId);

	public void updatePictureFromAlbum(Integer albumId, Integer pictureId, Picture newPicture);

	public void addPictureToAlbum(Integer albumId, Picture newPicture);

	public void deletePictureFromAlbum(Integer albumId, Integer pictureId);

}