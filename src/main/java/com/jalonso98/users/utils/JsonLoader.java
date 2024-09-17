package com.jalonso98.users.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.ClassPathResource;

public class JsonLoader {

	public static String loadJsonExample(String fileName) {
		try {
			Path path = Paths.get(new ClassPathResource(fileName).getURI());
			return Files.readString(path);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
