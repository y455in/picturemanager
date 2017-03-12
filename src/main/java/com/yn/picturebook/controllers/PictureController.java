package com.yn.picturebook.controllers;

import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.yn.picturebook.domain.Picture;
import com.yn.picturebook.services.IServiceFacade;

@RestController
public class PictureController {

	@Autowired
	IServiceFacade serviceFacade;

	@RequestMapping(value = "/albums/{albumId}/pictures", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<Picture> getAllPicturesForAlbum(@PathVariable("albumId") int albumId) {
		return serviceFacade.getAllPicturesAlbumId(albumId);
	}

	@RequestMapping(value = "/albums/{albumId}/pictures/{pictureId}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public Picture getPicture(@PathVariable("albumId") int albumId, @PathVariable("pictureId") int pictureId) {
		return serviceFacade.getPictureFromAlbum(albumId, pictureId);
	}

	@RequestMapping(value = "/albums/{albumId}/pictures", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void addPicture(@PathVariable("albumId") int albumId, @RequestBody Picture newPicture) {
		serviceFacade.addPictureToAlbum(albumId, newPicture);
	}

	@RequestMapping(value = "/albums/{albumId}/pictures/{pictureId}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public void updatePicture(@PathVariable("albumId") int albumId, @PathVariable("pictureId") int pictureId,
			@RequestBody Picture newPicture) {
		serviceFacade.updatePictureFromAlbum(albumId, pictureId, newPicture);
	}

	@RequestMapping(value = "/albums/{albumId}/pictures/{pictureId}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void deletePicture(@PathVariable("albumId") int albumId, @PathVariable("pictureId") int pictureId) {
		serviceFacade.deletePictureFromAlbum(albumId, pictureId);
	}

}
