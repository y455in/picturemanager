package com.yn.picturebook.testutil;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.yn.picturebook.domain.Album;
import com.yn.picturebook.domain.Picture;

public class DataFactory {

	public static String getFileAsString(String fileName) {
		StringBuilder result = new StringBuilder("");
		// Get file from resources folder
		ClassLoader classLoader = DataFactory.class.getClassLoader();
		URL fileUrl = classLoader.getResource(fileName);
		File file = new File(fileUrl.getFile());

		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				result.append(line);
			}

			scanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return result.toString();
	}

	public static Album[] getTestAlbums() {
		Album a = getTestAlbum(1);
		Album b = getTestAlbum(2);
		return new Album[] { a, b };
	}

	public static Album getTestAlbum(int id) {
		Album a = new Album();
		a.setId(id);
		a.setTitle("album" + id);

		return a;
	}

	public static Picture[] getTestPictures() {
		Picture a = getTestPicture(1, 1);
		Picture b = getTestPicture(3, 1);
		Picture c = getTestPicture(2, 2);
		Picture d = getTestPicture(4, 2);

		return new Picture[] { a, b, c, d };

	}

	public static Picture getTestPicture(int id, int albumId) {
		Picture a = new Picture();
		a.setId(id);
		a.setAlbumId(albumId);
		a.setTitle("pic" + id);
		a.setUrl("http://testurl" + id);
		return a;
	}

	public static Map<Integer, Map<Integer, Picture>> getPicturesByAlbumIdRepo() {
		Map<Integer, Map<Integer, Picture>> result = new HashMap<>();

		Map<Integer, Picture> pics1 = new HashMap<>();
		Picture a = getTestPicture(1, 1);
		Picture b = getTestPicture(3, 1);
		pics1.put(1, a);
		pics1.put(3, b);

		Map<Integer, Picture> pics2 = new HashMap<>();
		Picture c = getTestPicture(2, 2);
		Picture d = getTestPicture(4, 2);
		pics2.put(2, c);
		pics2.put(4, d);

		result.put(1, pics1);
		result.put(2, pics2);
		return result;
	}

	public static Map<Integer, Album> getAlbumsMap() {
		Map<Integer, Album> result = new HashMap<>();
		Album a = getTestAlbum(1);
		Album b = getTestAlbum(2);
		result.put(1, a);
		result.put(2, b);
		return result;
	}

	public static Map<Integer, Picture> getPicturesMap() {
		Map<Integer, Picture> result = new HashMap<>();
		Picture a = getTestPicture(1, 1);
		Picture b = getTestPicture(3, 1);
		Picture c = getTestPicture(2, 2);
		Picture d = getTestPicture(4, 2);
		result.put(1, a);
		result.put(3, b);
		result.put(2, c);
		result.put(4, d);
		return result;
	}
}
