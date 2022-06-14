package com.yn.picturebook.domain.util;

import java.util.Comparator;

import com.yn.picturebook.domain.Album;

public class AlbumIdComparator implements Comparator<Album> {
	public int compare(Album a, Album b) {
		return a.getId() - b.getId();
	}
}