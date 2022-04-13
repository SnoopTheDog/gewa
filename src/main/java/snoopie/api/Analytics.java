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
	
	private static long clockStart;
	private static long clockEnd;
	
	static void init()
	{
		//,,,
	}

	static long getAvgResponseTime()
	{
		return (totalResponseTime/apiCallCount);
	}

	static void startClock()
	{
		clockStart = System.currentTimeMillis();
	}

	static void stopClock()
	{
		clockEnd = System.currentTimeMillis();
		apiCallCount += 1;
		totalResponseTime += (clockEnd - clockStart);
	}
}