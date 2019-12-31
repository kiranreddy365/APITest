package api_pkg;

import java.io.IOException;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class APITestAdv {

	WebDriver driver;
	String url="https://api.github.com/";
	CloseableHttpClient client;
	CloseableHttpResponse response;

	@BeforeMethod
	public void setConn()
	{
		client=HttpClientBuilder.create().build();
	}

	@Test
	public void GetTest() throws ClientProtocolException, IOException
	{
		HttpGet httpGet=new HttpGet(url+"users/kiranreddy365");
		response=client.execute(httpGet);
		String jsonBody=EntityUtils.toString(response.getEntity());
		JSONObject jsonObj=new JSONObject(jsonBody);
		System.out.println(jsonObj.get("id"));
	}

	@Test
	public void TestUnmarshall() throws ClientProtocolException, IOException
	{
		HttpGet httpGet=new HttpGet(url+"users/kiranreddy365");
		response=client.execute(httpGet);
		User user=unmarshall(response, User.class);
		System.out.println(user.getId()+ " : "+user.getLogin());
	}

	
	
	@Test
	public void TestUnmarshallGeneric() throws ClientProtocolException, IOException
	{
		HttpGet httpGet=new HttpGet(url+"users/kiranreddy365");
		response=client.execute(httpGet);
		User user=unmarshallGeneric(response, User.class);
		System.out.println(user.getId()+ " : "+user.getLogin());
	}
	
	private User unmarshall(CloseableHttpResponse response2, Class<User> class1) throws ParseException, IOException {

		String jsonBody=EntityUtils.toString(response2.getEntity());

		return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).
				readValue(jsonBody, class1);
	}

	private <T>T unmarshallGeneric(CloseableHttpResponse response2, Class<T> class1) throws ParseException, IOException {

		String jsonBody=EntityUtils.toString(response2.getEntity());

		return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).
				readValue(jsonBody, class1);
	}

	@AfterMethod
	public void closeConn() throws IOException
	{
		response.close();
		client.close();
	}

}
