package com.eleksploded.packsum.hash;

import com.eleksploded.packsum.PackSumConfig;

public class HashHandler {
	
	public static Hasher getHasher() {
		PackSumConfig.Method method = PackSumConfig.method;
		
		if(method == PackSumConfig.Method.MD5) {
			return new MD5();
		} else if(method == PackSumConfig.Method.SHA256) {
			return new SHA256();
		} else {
			throw new RuntimeException("Invalid Method in PackSum Config");
		}
	}
	
	public interface Hasher {
		String generateForFile(String fileIn);
		String generateForDir(String dir);
		String generateForString(String in);
	}
}

