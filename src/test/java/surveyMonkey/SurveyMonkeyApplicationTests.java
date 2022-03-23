package surveyMonkey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import surveyMonkey.models.Response;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SurveyMonkeyApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void getTest() throws IOException {
		Response response = this.restTemplate.getForObject("http://localhost:" + port + "/get?documentId=testResponse", Response.class);
		assertEquals(response.toString(), "testResponse A B C");
	}

	@Test
	public void createTest() throws IOException, JSONException {
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

		Response response = this.restTemplate.getForObject("http://localhost:" + port + "/get?documentId=testResponse2", Response.class);
		assertEquals(response.toString(), "testResponse2 A B");

	}

	@Test
	public void updateTest() throws FileNotFoundException, JSONException {
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

		Response response = this.restTemplate.getForObject("http://localhost:" + port + "/get?documentId=testResponse2", Response.class);
		assertEquals(response.toString(), "testResponse2 A B D");
	}

	//This test is working locally, but not on CircleCI
	@Test
	public void deleteTest() throws FileNotFoundException, JSONException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject body = new JSONObject();
		body.put("documentId", "testResponse3");
		JSONArray value = new JSONArray();
		value.put("A");
		value.put("B");
		body.put("answers", value);
		HttpEntity<String> request = new HttpEntity<String>(body.toString(), headers);
		String result = this.restTemplate.postForObject("http://localhost:" + port + "/create", request, String.class);

		int secondsToSleep = 3;
		try {
			Thread.sleep(secondsToSleep * 1000);
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
		}

		this.restTemplate.put("http://localhost:" + port + "/delete?documentId=testResponse3", String.class);

		try {
			Thread.sleep(secondsToSleep * 1000);
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
		}

		Response response = this.restTemplate.getForObject("http://localhost:" + port + "/get?documentId=testResponse3", Response.class);
		//Making the error message more explicit
		if (!(response == null)){
			throw new AssertionError("deletedTest failed - We are supposed to get 'null' and got " + response);
		} else {
			//Why was this added? It will never actually trigger??
			assertEquals(response, null);
		}
	}

}
