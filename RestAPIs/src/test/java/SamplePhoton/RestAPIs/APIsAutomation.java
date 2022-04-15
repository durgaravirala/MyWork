package SamplePhoton.RestAPIs;

import java.util.List;

import org.json.JSONObject;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import junit.framework.Assert;

public class APIsAutomation {
	
	public static void main (String[] a) {
	//	Response getresponse = APIsAutomation.getMethodResponseBody();
		Response postResponse = APIsAutomation.postMethodResponseBody();
	//	Response putResponse = APIsAutomation.putMethodResponseBody();
		//Response patchResponse = APIsAutomation.patchMethodResponseBody();
	//	Response deleteResponse = APIsAutomation.deleteMethodResponseBody();
	//	System.out.println("Response is " + postresponse);
	}

	public static Response getMethodResponseBody() { // Here API is a GET method 
		
		Response response = null;
		RestAssured.baseURI = "https://jsonplaceholder.typicode.com";//"https://jsonplaceholder.typicode.com";
		response = RestAssured.given()
				.contentType(ContentType.JSON)
				.param("id", 1) // API returns multiple sets of values with ID's as here I am passing query parameter id as 1 to get only this set from response.. if we comment this line it retrieves whole response
                .when()
                .get("/posts")
                .then()
                .extract().response();

		if(response.getStatusCode()==200) {
			System.out.print("status code is 200");
			System.out.println("Response is " + response.body().asString());
		//	JSONResponseBody = new JSONObject("{" +response.body().asString() + "}" );
		/*	JsonPath jsonPathEvaluator = response.jsonPath();
			List<String> titles = jsonPathEvaluator.get("title");
			Assert.assertEquals("Verify title displayed as expected or not", "sunt aut facere repellat provident occaecati excepturi optio reprehenderit", titles.get(0));*/

		}		
			return response;
	}
	
public static Response postMethodResponseBody() { // Here API is a POST method 
		
		Response response = null;
		RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
		String body ="{\n" +
	            "  \"title\": \"foo\",\n" +
	            "  \"body\": \"bar\",\n" +
	            "  \"userId\": \"1\" \n}";
		
		response = RestAssured.given()
				.contentType(ContentType.JSON)
				.and()
				.body(body)
                .when()
                .post("/posts")
                .then()
                .extract().response();

		if(response.getStatusCode()==201) {
			System.out.print("status code is 201");
			System.out.println("\n Response is " + response.body().asString());
	Assert.assertEquals("Verify title displayed", "foo", response.jsonPath().get("title"));
	Assert.assertEquals("Verify body displayed", "bar", response.jsonPath().get("body"));	
		}		
			return response;
	}

public static Response putMethodResponseBody() { // Here API is a PUT method 
	
	Response response = null;
	RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
	String body ="{\n" +
            "  \"title\": \"foos\",\n" +
            "  \"body\": \"bar\",\n" +
            "  \"id\": \"1\",\n" +
            "  \"userId\": \"1012\" \n}";
	
	response = RestAssured.given()
			.contentType(ContentType.JSON)
			.and()
			.body(body)
            .when()
            .put("/posts/1") // it should be /1 for put otherwise execution terminated
            .then()
            .extract().response();

	if(response.getStatusCode()==200) {
		System.out.print("status code is 201");
		System.out.println("\n Response is " + response.body().asString());
Assert.assertEquals("Verify title displayed", "foo", response.jsonPath().get("title"));
Assert.assertEquals("Verify body displayed", "bar", response.jsonPath().get("body"));	
	}		
		return response;
}

public static Response patchMethodResponseBody() { // Here API is a PATCH method 
	
	Response response = null;
	RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
	String body ="{\n" +
            "  \"title\": \"bax\" \n}";
	
	response = RestAssured.given()
			.contentType(ContentType.JSON)
			.and()
			.body(body)
            .when()
            .patch("/posts/1") // it should be /1 for put otherwise execution terminated
            .then()
            .extract().response();

	if(response.getStatusCode()==200) {
		System.out.print("status code is 200");
		System.out.println("\n Response is " + response.body().asString());
Assert.assertEquals("Verify title displayed", "bax", response.jsonPath().get("title"));
//Assert.assertEquals("Verify body displayed", "bar", response.jsonPath().get("body"));	
	}		
		return response;
}

public static Response deleteMethodResponseBody() { // Here API is a Delete method 
	
	Response response = null;
	RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
	
	response = RestAssured.given()
			.contentType(ContentType.JSON)
            .when()
            .delete("/posts/1") // it should be /1 for put otherwise execution terminated
            .then()
            .extract().response();

	if(response.getStatusCode()==200) {
		System.out.print("status code is 200");
		System.out.println("\n Response is " + response.body().asString());
//Assert.assertEquals("Verify title displayed", "bax", response.jsonPath().get("title"));
//Assert.assertEquals("Verify body displayed", "bar", response.jsonPath().get("body"));	
	}		
		return response;
}
	
	
}
