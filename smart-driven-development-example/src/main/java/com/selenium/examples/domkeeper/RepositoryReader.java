package com.selenium.examples.domkeeper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RepositoryReader {
	
	public  Map<String, String> getXpath(String jsonPath){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> xpathPosition = new HashMap<String, String>();

		try {

			// JSON file to Java object
			MultiRepo objects = mapper.readValue(new File(jsonPath),
					MultiRepo.class);
			List<Command> targets;
			AtomicInteger element = new AtomicInteger(-1);
			targets = objects.getTests().get(0).getCommands();
			targets.forEach((targetlist)  -> 
			{
				
				targetlist.getTargets().forEach((list) -> {
					if(list.get(1).equals("xpath:position")) {
						String value = "element"+element.incrementAndGet();
//						System.out.println(value + ":::"+list.get(0).toString());
						xpathPosition.put(value, list.get(0).toString());
					}
				});
			}
			);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return xpathPosition;
		
	}
	
	public Map<String, String> getExecutionTarget(String jsonPath){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> target = new HashMap<String, String>();

		try {

			// JSON file to Java object
			LocalRepo objects = mapper.readValue(new File(jsonPath),
					LocalRepo.class);		
			List<List<String>> listOfLists = objects.getWebElements();
			listOfLists.forEach((list)  -> 
			{
				
				target.put(list.get(0), list.get(1));
				list.forEach((elements)->System.out.println(elements));
			}
					);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return target;
		
	}
	
	
}
