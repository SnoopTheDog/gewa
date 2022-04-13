package snoopie.api;

import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.geojson.*;
import org.geojson.Point;
import org.geojson.FeatureCollection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/* Should or should I not */
class IncomingJson 
{

}

@SpringBootApplication
public class Application 
{
	// Reuse objmapper
	private static ObjectMapper om = new ObjectMapper();
	static String weatherKey = "72b390c3c9e77e7a8590daa3ecb56003";

	public static void main(String[] args) 
	{
		Analytics.init();
		SpringApplication.run(Application.class, args);
		System.out.println("Application started");
	}

	// Maybe next time
	public static <T> T getNestles(Map map, String... keys) 
	{
		Object val = map;

		for (String key : keys) {
			val = ((Map) val).get(key);
		}

		return (T) val;
	}

	public static String getWeather(double lat, double lon) throws Exception 
	{
		String url = "https://api.openweathermap.org/data/2.5/weather?lat="
				+ lat + "&lon="
				+ lon + "&type=hour&appid=" + weatherKey;

		// Not sure how to conform to Map<String,ArrayList<Map<String,String>>>
		Map<String, ArrayList<Map<String, String>>> map = 
			om.readValue(new URL(url), Map.class);

		ArrayList<Map<String, String>> weather = map.get("weather");
		Map<String, String> donzo = weather.get(0);

		return donzo.get("main");
	}

	// Too many objects for my taste
	public static String getHighestMagQuake(String... par) throws Exception
	{
		boolean timeFlag = false;
		if (par.length > 1)
			timeFlag = true;

		FeatureCollection fc = null;
		List<Feature> features = null;

		try 
		{
			 fc = om.readValue(new URL(par[0]), 
				FeatureCollection.class);
			 
			if (fc != null) {
				features = fc.getFeatures();
			}
			else {
				System.out.println("[ERR] failed to retrieve quake data");
				return "Failed to get quake data";
			}
		} 
		catch (Exception e) 
		{
			System.out.println("[ERR]");
			e.printStackTrace();
		}

		String result = null;
		String place = null;
		double mag = -1.0f;
		LngLatAlt geo = null;
		long time = 0;


		for (Feature f : features) {
			Point po = (Point) f.getGeometry();
			LngLatAlt grr = po.getCoordinates();

			String placeTmp = f.getProperty("place");
			// Millisecond UTC epoch
			long timeTmp = f.getProperty("time");

			double magTmp;
			if (f.getProperty("mag") instanceof Integer) {
				magTmp = (double) (int) f.getProperty("mag");
			} else {
				magTmp = f.getProperty("mag");
			}

			if (timeTmp < time && timeFlag)
				continue;
			if (magTmp < mag)
				continue;

			place = placeTmp;
			mag = magTmp;
			geo = grr;
		}

		result = "\nLocation: " + place
				+ "\nLatitude: " + geo.getLatitude()
				+ "\nLongitude: " + geo.getLongitude()
				+ "\nAltitude: " + geo.getAltitude()
				+ "\nMagnitude: " + mag
				+ "\nTime: " + time
				+ "\nWeather: " 
				+ getWeather(geo.getLatitude(), geo.getLongitude());

		System.out.println(result);

		return result;
	}

	// ISO8601 Date/Time format
	public static String weekAgoDate() 
	{
		return LocalDate.now().minus(Period.ofDays(7)).toString();
	}

	public static String monthAgoDate() 
	{
		return LocalDate.now().minus(Period.ofDays(31)).toString();
	}

	public static String generateQuakeUrl(String starttime) 
	{
		String url = 
			"https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime="
			+ starttime;
		return url;
	}

	public static String getBodyFromUrl(String url) throws Exception 
	{
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.build();

		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

		return response.body();
	}
}