package snoopie.api;

public class Analytics
{
	static class Response
	{
		int id;
		String url;
		String date;
		long elapsedTime;
		String body;

		Response(int id, String url, String date, long elapsedTime, String body)
		{
			this.id = id;
			this.url = url;
			this.date = date;
			this.elapsedTime = elapsedTime;
			this.body = body;
		}
	}

	static long apiCallCount = 0;
	static long totalResponseTime = 0;
	
	static void init()
	{
		//,,,
	}

	static long getAvgResponseTime()
	{
		return (totalResponseTime/apiCallCount);
	}

	static long startClock()
	{
		return System.currentTimeMillis();
	}

	static void measure()
	{

	}

	static void stopClock(long start)
	{
		long end = System.currentTimeMillis();
		apiCallCount += 1;
		totalResponseTime += (end - start);
	}
}