package com.eleksploded.packsum.hash;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;

public class SHA256 implements HashHandler.Hasher {
	public String generateForDir(String dir)
	{
	    File[] files = new File(dir).listFiles();
	    System.out.println("Hashing File in dir: " + ArrayUtils.toString(files));
	    return Hashing.sha256().hashString(expandFiles(files, ""), Charsets.UTF_8).toString();
	} 
	
	public String generateForFile(String fileIn) {
		File file = new File(fileIn);
		String sha256 = "";
		try {
			sha256 = Files.hash(file, Hashing.sha256()).toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sha256;
	}
	
	public String generateForString(String in) {
		return Hashing.sha256().hashString(in, Charsets.UTF_8).toString();
	}

	public static String expandFiles(File[] dirFiles, String sha256in)
	{
	    String sha256out = sha256in;
	    for(File file : dirFiles)
	    { 
	        if(file.isHidden())
	        {
	        } 
	        else if (file.isDirectory())
	        {
	            sha256out += Hashing.sha256().hashString(expandFiles(file.listFiles(), sha256out), Charsets.UTF_8).toString();
	        }
	        else
	        {
	            HashCode sha256 = null;
	            try
	            {
	                sha256 = Files.hash(file, Hashing.sha256());
	            }
	            catch (IOException e)
	            {
	                e.printStackTrace();
	            }
	            sha256out += sha256.toString();

	        }
	    }
	    return sha256out;
	}
}
