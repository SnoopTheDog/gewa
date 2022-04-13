package snoopie.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/* No integration tests here ¯\_(ツ)_/¯ */

@SpringBootTest
class ApplicationTests
{
	@Test
	void getWeather_LatLonOutOfBounds() 
	{
		long lat = 91L;
		long lon = 100L;
		Assertions.assertThrows(Exception.class,
			() -> Application.getWeather(lat, lon));
	}

	@Test
	void getHighestQuake_WithInvalidUrl()
	{
		String url = "loremusipsimus";

		Assertions.assertThrows(NullPointerException.class, 
			() -> Application.getHighestMagQuake(url));
	}

	@Test
	void testWeatherApi()
	{

	}

	@Test
	void testQuaekApi()
	{
		
	}
}
