package surveyMonkey;

import com.google.api.client.json.Json;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SurveyMonkeyApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@BeforeAll
	public static void setup() throws JSONException {

	}

	@Test
	public void getTest() throws IOException {
		SurveyMonkeyApplication.setupDB();
		Responses response = this.restTemplate.getForObject("http://localhost:" + port + "/get?documentId=testResponse", Responses.class);
		assertEquals(response.toString(), "testResponse A B C");
	}

	@Test
	public void createTest() throws IOException, JSONException {
		SurveyMonkeyApplication.setupDB();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject body = new JSONObject();
		body.put("documentId", "testResponse2");
		JSONArray value = new JSONArray();
		value.put("A");
		value.put("B");
		body.put("answers", value);
		HttpEntity<String> request = new HttpEntity<String>(body.toString(), headers);
		String result = this.restTemplate.postForObject("http://localhost:" + port + "/create", request, String.class);

		Responses response = this.restTemplate.getForObject("http://localhost:" + port + "/get?documentId=testResponse2", Responses.class);
		assertEquals(response.toString(), "testResponse2 A B");

	}

	@Test
	public void updateTest() throws FileNotFoundException, JSONException {
		SurveyMonkeyApplication.setupDB();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject body = new JSONObject();
		body.put("documentId", "testResponse2");
		JSONArray value = new JSONArray();
		value.put("A");
		value.put("B");
		value.put("D");
		body.put("answers", value);
		HttpEntity<String> request = new HttpEntity<String>(body.toString(), headers);
		this.restTemplate.put("http://localhost:" + port + "/update", request, String.class);

		Responses response = this.restTemplate.getForObject("http://localhost:" + port + "/get?documentId=testResponse2", Responses.class);
		assertEquals(response.toString(), "testResponse2 A B D");
	}

	//I added a delay in this test to prevent race between the two requests.
	@Test
	public void deleteTest() throws FileNotFoundException {
		SurveyMonkeyApplication.setupDB();
		this.restTemplate.put("http://localhost:" + port + "/delete?documentId=testResponse2", String.class);
		try {
			Thread.sleep(10);
		}catch(InterruptedException ex){
		}
		Responses response = this.restTemplate.getForObject("http://localhost:" + port + "/get?documentId=testResponse2", Responses.class);
		//rewriting this test to make sure that it's not asking for an input when null is expected
		if (!(response == null)){
			throw new AssertionError("deletedTest failed - We are supposed to get null and got " + response);
		}
	}

}
