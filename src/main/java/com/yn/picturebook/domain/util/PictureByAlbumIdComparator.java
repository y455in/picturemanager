package com.yn.picturebook.domain.util;

import java.util.Comparator;

import com.yn.picturebook.domain.Picture;

public class PictureByAlbumIdComparator implements Comparator<Picture> {
	public int compare(Picture a, Picture b) {
		if (a.getAlbumId().equals(b.getAlbumId()))
			return a.getId() - b.getId();
		return a.getAlbumId() - b.getAlbumId();

	}
}