package surveyMonkey;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;



@SpringBootApplication
public class SurveyMonkeyApplication {

	public static void main(String[] args) throws IOException {
		setupDB();
		SpringApplication.run(SurveyMonkeyApplication.class, args);
	}

	public static void setupDB() throws FileNotFoundException {
		FileInputStream serviceAccount = new FileInputStream("src/main/resources/serviceAccountKey.json");

		try {
			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.build();
			if (FirebaseApp.getApps().isEmpty()) {
				FirebaseApp.initializeApp(options);
			}
		} catch(IOException e){
			e.printStackTrace();
		}
	}

}
