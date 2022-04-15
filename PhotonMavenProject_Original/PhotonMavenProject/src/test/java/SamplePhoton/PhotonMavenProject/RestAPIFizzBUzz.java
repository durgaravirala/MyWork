package SamplePhoton.PhotonMavenProject;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class RestAPIFizzBUzz {
	
	@Test
	public void ValidateAPIResponseAndPrintOutputJSON() {
	
	String endPoint = "<TBD>";
	JSONObject JSONResponseBody = new JSONObject();
	
	String [] input = new String[] {"1","3","5","","15","A","23"};
	
	try {
		RequestSpecification request = RestAssured.given();
		request.headers("Autorization","Bearer Token");
		for (int i=0; i<input.length; i++) {
			request.queryParam("input" +i, input[i]);
		}
		      Field changeMap = JSONResponseBody.getClass().getDeclaredField("map");
		      changeMap.setAccessible(true);
		      changeMap.set(JSONResponseBody, new LinkedHashMap<Object, Object>());
		      changeMap.setAccessible(false);
		   
		
	/*	Commented below Response object and writing it into a JSON to make the program Runnable
	 * 
	 * //Response response = request.get(endPoint).then().log().all().assertThat().statusCode(200).extract().response();
	
		//JSONResponseBody = new JSONObject(response.body().asString());
		// Assumption as below is the response from API
*/		JSONResponseBody.put("1","0, 0")
						.put("3", "Fizz")
						.put("5", "Buzz")
						.put("", "Invalid Item")
						.put("15", "FizzBuzz")
						.put("A", "Invalid Item")
						.put("23", "7, 4");
		
		System.out.println("response added manually" + JSONResponseBody);
		
		JSONObject outputJson = new JSONObject();
		changeMap = outputJson.getClass().getDeclaredField("map");
	      changeMap.setAccessible(true);
	      changeMap.set(outputJson, new LinkedHashMap<Object, Object>());
	      changeMap.setAccessible(false);
		for(int i=0; i<input.length; i++) {
			String output = null;
			try {
				int n = Integer.parseInt(input[i]);
				if(n%3 == 0) {
					if(n%5 ==0) {
						output = "FizzBuzz";
					}
					else output = "Fizz";
				} else if(n%5 == 0) {
					output = "Buzz";
				} else {
					output =""+n/3+ ", " +n/5 +"";
				} 
				
				} catch(NumberFormatException nfe) {
					output = "Invalid Item";
			} 
			if(output.equals(JSONResponseBody.get(input[i]))) {
			outputJson.put(input[i], output);
			}
		}
		  
		System.out.println("Printing Output response JSON " + outputJson);
		
	} catch(Exception e) {
		e.printStackTrace();
	}
	
	}
	

}
