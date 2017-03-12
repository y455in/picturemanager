package com.yn.picturebook.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InitService implements IInitService {
	@Autowired
	IDataProvider dataProvider;

	public void init() {
		dataProvider.init();
	}
}
