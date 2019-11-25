package api_pkg;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.http.HttpClient;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestAPI {
	
	WebDriver driver;
	String url="https://api.github.com/";
	String browser="chrome";
	CloseableHttpClient client;
	CloseableHttpResponse response;
	
	@BeforeMethod
	public void setConn()
	{
		client=HttpClientBuilder.create().build();
	}
	
	//@Test(priority=1)
	public void getReq() throws ClientProtocolException, IOException
	{
		HttpGet get=new HttpGet(url);
		response=client.execute(get);
		System.out.println(response.toString());
		int statusCode=response.getStatusLine().getStatusCode();
		Assert.assertTrue(statusCode==200);
	}
	
	//@Test(priority=2)
	public void getRateLimit() throws ClientProtocolException, IOException
	{
		HttpGet get=new HttpGet(url+"/rate_limit");
		response=client.execute(get);
		System.out.println(response.toString());
		int statusCode=response.getStatusLine().getStatusCode();
		Assert.assertTrue(statusCode==200);
	}
	
	//@Test(priority=3)
	public void getEvents() throws ClientProtocolException, IOException
	{
		HttpGet get=new HttpGet(url+"/events");
		response=client.execute(get);
		System.out.println(response.toString());
		int statusCode=response.getStatusLine().getStatusCode();
		Assert.assertTrue(statusCode==200);
	}
	
	//@Test(priority=4)
	public void getContentType() throws ClientProtocolException, IOException
	{
		HttpGet get=new HttpGet(url+"/events");
		response=client.execute(get);
		System.out.println(response.toString());
		System.out.println(response.getEntity().getContentType());
	}
	
	@Test(priority=5)
	public void serverIsGithub() throws ClientProtocolException, IOException
	{
		HttpGet get=new HttpGet(url+"/events");
		response=client.execute(get);
		System.out.println("================ Headers as below ================");
		getHeaders(response);
		getHeadersJava8Way(response);
	}
	
	private void getHeaders(CloseableHttpResponse response2) {
		
		Header[] headers=response2.getAllHeaders();
		List<Header> httpHeaders=Arrays.asList(headers);
		
		
		for (Header header : httpHeaders) {
			
			System.out.println(header.getName()+" : "+header.getValue());
		}
		
		System.out.println("================ End of Headers ================");
	}
	
	private void getHeadersJava8Way(CloseableHttpResponse response2) {
		
		Header[] headers=response2.getAllHeaders();
		List<Header> httpHeaders=Arrays.asList(headers);
		
		Header headerType=httpHeaders.stream()
				.filter(header -> header.getName().equalsIgnoreCase("GitHub.com"))
				.findFirst().orElseThrow(()-> new RuntimeException("Did not find any headers."));
		
		System.out.println(headerType.getName()+" : "+ headerType.getValue());
	}

	@AfterMethod
	public void closeConn() throws IOException
	{
		response.close();
		client.close();
	}
	
	

}
