package free.api.service.test;



import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class NewsAPITest {
	
	public static final String baseURL="https://jgentes-crime-data-v1.p.rapidapi.com/crime?startdate=9%252F19%252F2015&enddate=9%252F25%252F2015&lat=37.757815&long=-122.5076392";
	
	public static void main(String[] args) throws UnirestException{
		
		HttpResponse<String> response= Unirest.get(baseURL)
				.header("x-rapidapi-host", "jgentes-Crime-Data-v1.p.rapidapi.com")
				.header("x-rapidapi-key", "4bff7cdd0bmshad03e43c3729d3fp1ca3f2jsn018df96c6f55").asString();
		System.out.println(response.getStatus());
		System.out.println(response.getStatusText());
		System.out.println(response.getBody().toString());

	}

}
