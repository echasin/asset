package com.innvo.service;

import java.io.FileReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.innvo.web.rest.AssetResource;
import com.innvo.web.rest.AssetassetmbrResource;

/**
 * 
 * @author ali
 *
 */
public class YMLService {
	

	public Object getData() throws IOException{
		AssetassetmbrResource receiver=new AssetassetmbrResource();
		
		     String fullfilename =URLDecoder.decode(receiver.getClass().getResource("/config/application-dev.yml").getFile(), "UTF-8");
		     YamlReader reader = new YamlReader(new FileReader(fullfilename));
		     Object fileContent = reader.read();
		     Map map = (Map) fileContent;
		     Object colors = map.get("color");
		     return colors;
}

}