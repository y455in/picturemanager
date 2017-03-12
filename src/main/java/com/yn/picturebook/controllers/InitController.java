package com.yn.picturebook.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.yn.picturebook.services.IServiceFacade;

@RestController
public class InitController {

	@Autowired
	IServiceFacade serviceFacade;

	@RequestMapping(value = "/init")
	@ResponseStatus(value = HttpStatus.OK)
	public void init() {
		serviceFacade.init();
	}

}
