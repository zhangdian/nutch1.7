package org.bd17kaka.nutch.plugins.parseimage;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.nutch.metadata.Metadata;
import org.apache.nutch.parse.ParseResult;
import org.apache.nutch.parse.Parser;
import org.apache.nutch.protocol.Content;
import org.apache.solr.common.util.ContentStreamBase.FileStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageParser implements Parser {

	 public static final Logger LOG = LoggerFactory.getLogger("rg.bd17kaka.nutch.plugins.parseimage.ImageParser");
	private Configuration conf;
	
	@Override
	public Configuration getConf() {
		return this.conf;
	}

	@Override
	public void setConf(Configuration conf) {
		this.conf = conf;
	}

	@Override
	public ParseResult getParse(Content c) {
		
		// print debug message
		LOG.info("**********************************************************");
		LOG.info(c.getBaseUrl());
		LOG.info(c.getUrl());
		LOG.info(c.getContentType());
		Metadata metaData = c.getMetadata();
		String[] names = metaData.names();
		for (String name : names) {
			LOG.info(name + " : " + metaData.get(name));
		}
		LOG.info("**********************************************************");
		
		Calendar cal = Calendar.getInstance();
		long ts = cal.getTimeInMillis();
		String path = "/home/bd17kaka/image/" + ts + "_" + RandomUtils.nextInt() + ".jpg"; 
		
        if(!new File(path).exists()){  
            try {
				new File(path).createNewFile();
			} catch (IOException e) {
				return null;
			}  
        }  
		
        DataOutputStream dos = null;
        try {
			dos = new DataOutputStream(new FileOutputStream(new File(path)));
		} catch (FileNotFoundException e) {
			return null;
		}  
        
        try {
			dos.write(c.getContent());
		} catch (IOException e) {
		}
		return null;
	}

}
