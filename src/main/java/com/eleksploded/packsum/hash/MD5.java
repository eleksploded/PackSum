package com.eleksploded.packsum.hash;

import java.io.File;
import java.io.IOException;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;

public class MD5 implements HashHandler.Hasher {
	public String generateForDir(String dir)
	{
	    File[] files = new File(dir).listFiles();
	    return Hashing.md5().hashString(expandFiles(files, ""), Charsets.UTF_8).toString();
	}
	
	public String generateForFile(String fileIn) {
		File file = new File(fileIn);
		String md5 = "";
		try {
			md5 = Files.hash(file, Hashing.md5()).toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return md5;
	}
	
	public String generateForString(String in) {
		return Hashing.md5().hashString(in, Charsets.UTF_8).toString();
	}

	public static String expandFiles(File[] dirFiles, String md5in)
	{
	    String md5out = md5in;
	    for(File file : dirFiles)
	    { 
	        if(file.isHidden())
	        {
	        } 
	        else if (file.isDirectory())
	        {
	            md5out += Hashing.md5().hashString(expandFiles(file.listFiles(), md5out), Charsets.UTF_8).toString();
	        }
	        else
	        {
	            HashCode md5 = null;
	            try
	            {
	            	if(file.getName() == "packsum") {
	            		System.out.println("SelfLoad");
	            	}
	                md5 = Files.hash(file, Hashing.md5());
	            }
	            catch (IOException e)
	            {
	                e.printStackTrace();
	            }
	            md5out += md5.toString();

	        }
	    }
	    return md5out;
	}
}
