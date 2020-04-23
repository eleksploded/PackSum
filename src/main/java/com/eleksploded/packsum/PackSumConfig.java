package com.eleksploded.packsum;

import net.minecraftforge.common.config.Config;

@Config(modid = "packsum")
public class PackSumConfig {
	@Config.Comment("List of files/Directories to be checked (Include file extentions for files, Do not put a slash after directory names)")
	public static String[] check = {};
	
	@Config.Comment("Expected CheckSum")
	public static String expected = "";
	
	@Config.Comment("Generate Checksum on launch")
	public static boolean generate = false;
	
	@Config.Comment("Which Method to use when calculating checksum")
	public static Method method = Method.SHA256;
	
	public static enum Method {
		MD5, SHA256
	}
}
