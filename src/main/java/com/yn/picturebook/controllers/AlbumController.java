package com.yn.picturebook.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.yn.picturebook.domain.Album;
import com.yn.picturebook.exceptions.AlbumNotEmptyException;
import com.yn.picturebook.services.IServiceFacade;

@RestController
public class AlbumController {

	@Autowired
	IServiceFacade serviceFacade;

	@RequestMapping(value = "/albums", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<Album> getAllAlbums() {
		return serviceFacade.getAlbums();
	}

	@RequestMapping(value = "/albums/{albumId}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public Album getAlbum(@PathVariable("albumId") int albumId) {
		return serviceFacade.getAlbumById(albumId);
	}

	@RequestMapping(value = "/albums", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void addAlbum(@RequestBody Album newAlbum) {
		serviceFacade.addAlbum(newAlbum);
	}

	@RequestMapping(value = "/albums/{albumId}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public void updateAlbum(@PathVariable("albumId") int albumId, @RequestBody Album newAlbum) {
		serviceFacade.updateAlbum(albumId, newAlbum);
	}

	@RequestMapping(value = "/albums/{albumId}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void deleteAlbum(@PathVariable("albumId") int albumId) throws AlbumNotEmptyException {
		serviceFacade.deleteAlbum(albumId);
	}

}
