package snoopie.api;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	
	@GetMapping("/potresi/rekordi/tedenski")
	String quakeRecordWeek() throws Exception 
	{
		Analytics.startClock();
		
		String url = Application.generateQuakeUrl(Application.weekAgoDate());
		String returnee = Application.getHighestMagQuake(url);
		
		Analytics.stopClock();
		
		return returnee;
	}

	@GetMapping("/potresi/rekordi/mesecni")
	String quakeRecordMonth() throws Exception 
	{
		Analytics.startClock();

		String url = Application.generateQuakeUrl(Application.monthAgoDate());
		String res = Application.getHighestMagQuake(url);

		Analytics.stopClock();
		
		return res;
	}

	@GetMapping("/potresi/zadnji")
	String quakeLast() throws Exception 
	{
		Analytics.startClock();

		String url = Application.generateQuakeUrl("");
		String res = Application.getHighestMagQuake(url, "nyahaha");
		
		Analytics.stopClock();
		
		return res;
	}

	@GetMapping("/statistika/klici")
	String getCalls() throws JsonProcessingException
	{
		return Application.om.writeValueAsString(Analytics.apiCallCount);
	}

	@GetMapping("/statistika/avg")
	String getTime() throws JsonProcessingException
	{

		return Application.om.writeValueAsString(Analytics.getAvgResponseTime());
	}
}