package com.eleksploded.packsum;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;

import com.eleksploded.packsum.hash.HashHandler;
import com.eleksploded.packsum.hash.HashHandler.Hasher;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = PackSum.MODID, name = PackSum.NAME, version = PackSum.VERSION)
public class PackSum
{
    public static final String MODID = "packsum";
    public static final String NAME = "PackSum";
    public static final String VERSION = "1.0";

    public static Logger logger;
    
    @EventHandler
    public void pre(FMLPreInitializationEvent event) {
    	logger = event.getModLog();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        List<String> hashes = new ArrayList<String>();
        
        Hasher hasher = HashHandler.getHasher();
        
        for(String in : PackSumConfig.check) {
        	File file = null;
			try {
				file = new File(Minecraft.getMinecraft().mcDataDir.getCanonicalPath() + "/" + in);
			} catch (IOException e) {
				e.printStackTrace();
			}
        	if(file.isDirectory()) {
        		System.out.println("Hashing Dir: " + file.getAbsolutePath());
        		hashes.add(hasher.generateForDir(file.getAbsolutePath()));
        	} else {
        		System.out.println("Hashing File: " + file.getAbsolutePath());
        		hashes.add(hasher.generateForFile(file.getAbsolutePath()));
        	}
        }
        
        StringBuilder builder = new StringBuilder();
        hashes.forEach(hash -> builder.append(hash));
        String finalHash = hasher.generateForString(builder.toString());
        
        if(!PackSumConfig.generate && !finalHash.equals(PackSumConfig.expected)) {
        	throw new RuntimeException();
        }
        //6fd727a15e4404c35ad5754343f0c7d4b1b57b80e42fdc334f7488ac7b429b06
        if(PackSumConfig.generate) {
        	logger.info(finalHash != null ? "Generated Hash: " + finalHash : "No Hash Generated");
        }
    }
}