package com.headspin.hackathon.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class Utils {

	public static AppConfig readAppConfig() {
		AppConfig appConfig = null;
		try {
			ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
			InputStream envFile = AppConfig.class.getResourceAsStream("/application/app.yaml");
			appConfig = mapper.readValue(envFile, AppConfig.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return appConfig;
	}

	public static boolean createFile(File screenshot) {
		boolean fileCreated = false;

		if (screenshot.exists()) {
			fileCreated = true;
		} else {
			File parentDirectory = new File(screenshot.getParent());
			if (parentDirectory.exists() || parentDirectory.mkdirs()) {
				try {
					fileCreated = screenshot.createNewFile();
				} catch (IOException errorCreatingScreenshot) {
					errorCreatingScreenshot.printStackTrace();
				}
			}
		}

		return fileCreated;
	}

	public static String getBase64EncodedString(File screenshot) {

		try {
			byte[] failedAt = Base64.getEncoder().encode(FileUtils.readFileToByteArray(screenshot));
			return new String(failedAt);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
