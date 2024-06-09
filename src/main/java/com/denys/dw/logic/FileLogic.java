package com.denys.dw.logic;

import java.io.File;

public class FileLogic {

	public static boolean deleteFile(String dirName) {
		File directory = new File(dirName);
		
		if(!directory.isDirectory()) {
			return false;
		}
		
		File[] files = directory.listFiles();
		
		if(files == null) {
			return false;
		}
		
		boolean allFilesDeleted = true;
		for(File file : files) {
			if(!file.delete()) {
				allFilesDeleted = false;
			}
		}
		return allFilesDeleted;
	}
}
