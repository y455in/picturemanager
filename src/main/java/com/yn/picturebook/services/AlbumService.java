package com.yn.picturebook.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yn.picturebook.domain.Album;
import com.yn.picturebook.domain.Picture;
import com.yn.picturebook.exceptions.AlbumNotEmptyException;

/**
 * Implementation of the Album service. Acts as a simple delegate for the data
 * provider. Processes requests asynchronously.
 * 
 * @author Yassin.nachite
 *
 */
@Service
public class AlbumService implements IAlbumService {

	@Autowired
	IDataProvider dataProvider;

	@Override
	public Album getAlbumById(Integer albumId) {
		Album result = dataProvider.getAlbumById(albumId);
		return result;
	}

	@Override
	public void addAlbum(Album album) {
		dataProvider.addAlbum(album);
	}

	@Override
	public void updateAlbum(Integer albumId, Album album) {
		dataProvider.updateAlbum(albumId, album);
	}

	@Override
	public void deleteAlbum(Integer albumId) throws AlbumNotEmptyException {
		// does not support deletion of non empty albums
		List<Picture> results = dataProvider.getPicturesByAlbumId(albumId);
		if (results != null && results.size() > 0)
			throw new AlbumNotEmptyException();
		dataProvider.deleteAlbum(albumId);
	}

	@Override
	public List<Album> getAlbums() {
		List<Album> results = dataProvider.getAlbums();
		return results;
	}

}
