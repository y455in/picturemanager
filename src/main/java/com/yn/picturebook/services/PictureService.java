package com.yn.picturebook.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yn.picturebook.domain.Picture;

@Service
public class PictureService implements IPictureService {

	@Autowired
	IDataProvider dataProvider;

	public List<Picture> getPicturesAlbumId(Integer albumId) {
		List<Picture> results = dataProvider.getPicturesByAlbumId(albumId);
		return results;
	}

	public Picture getPictureFromAlbum(Integer albumId, Integer pictureId) {
		Picture result = dataProvider.getPictureFromAlbum(albumId, pictureId);
		return result;
	}

	public void updatePictureFromAlbum(Integer albumId, Integer pictureId, Picture newPicture) {
		dataProvider.updatePictureInAlbum(albumId, pictureId, newPicture);
	}

	public void addPictureToAlbum(Integer albumId, Picture newPicture) {
		dataProvider.addPictureToAlbum(albumId, newPicture);
	}

	public void deletePictureFromAlbum(Integer albumId, Integer pictureId) {
		dataProvider.deletePictureInAlbum(albumId, pictureId);
	}

}
