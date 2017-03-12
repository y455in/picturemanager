package com.yn.picturebook.services;

import java.util.List;

import com.yn.picturebook.domain.Album;
import com.yn.picturebook.exceptions.AlbumNotEmptyException;

public interface IAlbumService {

	public Album getAlbumById(Integer albumId);

	public void addAlbum(Album album);

	public void updateAlbum(Integer albumId, Album album);

	public void deleteAlbum(Integer albumId) throws AlbumNotEmptyException;

	public List<Album> getAlbums();

}