package surveyMonkey;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class IndexController {

    @RequestMapping("/")
    public ModelAndView mainPage() {
        return new ModelAndView("index");
    }

    @RequestMapping("/Food")
    public Object login(String Food) throws InterruptedException {
        HttpHeaders headers = new HttpHeaders();
        CollectionReference responsesCR = FirestoreClient.getFirestore().collection("responses");
        JSONObject body = new JSONObject();
        body.put("documentId", "favFood");
        body.put("Favourite Food", Food);
        responsesCR.add(body);
        return new ModelAndView("/success");
    }
}
