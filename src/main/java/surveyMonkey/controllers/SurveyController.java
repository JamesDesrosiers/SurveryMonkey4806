package surveyMonkey.controllers;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import surveyMonkey.models.Question;
import surveyMonkey.models.Survey;
import surveyMonkey.services.FirebaseInitializer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@RestController
public class SurveyController {

    @Autowired
    FirebaseInitializer db;

    @RequestMapping("/surveys")
    public ModelAndView resultsPage(@ModelAttribute("survey") Survey survey, @ModelAttribute("questions") ArrayList<Question> questions) throws Exception {

        survey.setTitle("Hot Dogs");
        survey.setId("hotDogs");

        ApiFuture<QuerySnapshot> query = db.getFirebase().collection("responses").whereEqualTo("documentId", survey.getId()).get();
        QuerySnapshot qs = query.get();
        HashMap<String, ArrayList<String>> responseList = new HashMap<>();
        HashMap<String, String> questionList = new HashMap<>();
        questionList.put("Hot Dog Rating", "range");
        questionList.put("Yearly hot dog consumption", "text");

        List<QueryDocumentSnapshot> documents = qs.getDocuments();
        for(QueryDocumentSnapshot document : documents){
            for(String q : questionList.keySet()){
                if(!responseList.containsKey(q)){
                    responseList.put(q, new ArrayList<>());
                }
                if(questionList.get(q).equals("range")){
                    responseList.get(q).add(document.getLong(q).toString());
                }else {
                    responseList.get(q).add(document.getString(q));
                }
            }
        }

        for(String q : questionList.keySet()){
            Question nlq = new Question();
            nlq.setType(questionList.get(q));
            nlq.setQuestion(q);
            if (questionList.get(q).equals("text")){
                nlq.setAnswers(responseList.get(q));
            }
            else if (questionList.get(q).equals("range")){
                HashMap<Number, Number> a = new HashMap<>();
                for(String range : responseList.get(q)){
                    if(!a.containsKey(Integer.parseInt(range))){
                        a.put(Integer.parseInt(range), 1);
                    }else{
                        a.replace(Integer.parseInt(range), (Integer)(a.get(Integer.parseInt(range)))+1);
                    }
                }
                nlq.setRanges(a);
            }
            questions.add(nlq);
        }

        return new ModelAndView("surveys");
    }

}
