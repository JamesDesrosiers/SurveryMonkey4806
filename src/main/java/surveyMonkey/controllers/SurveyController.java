package surveyMonkey.controllers;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import surveyMonkey.models.Question;
import surveyMonkey.models.Response;
import surveyMonkey.models.Survey;
import surveyMonkey.services.FirebaseInitializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
public class SurveyController {

    @Autowired
    FirebaseInitializer db;

    @RequestMapping("/survey")
    public ModelAndView surveyPage(@RequestParam(value="id", required=true) String id, @ModelAttribute("survey") Survey survey) throws ExecutionException, InterruptedException {
        DocumentReference query = db.getFirebase().document("surveys/"+id);

        survey.setSurvey(query.get().get());
        return new ModelAndView("survey");
    }

    @RequestMapping(value="/submit", method = RequestMethod.GET)
    public @ResponseBody Response submission(@RequestParam Map<String, String> queryParameters) throws ExecutionException, InterruptedException {
        Survey s = new Survey(
            db.getFirebase().document("surveys/"+queryParameters.get("id")).get().get()
        );

        //System.out.println(s.getQuestions());

        return null;
    }

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
                HashMap<String, Number> a = new HashMap<>();
                for(String range : responseList.get(q)){
                    if(!a.containsKey(Integer.parseInt(range))){
                        a.put(range, 1);
                    }else{
                        a.replace(range, (Integer)(a.get(Integer.parseInt(range)))+1);
                    }
                }
                nlq.setRanges(a);
            }
            questions.add(nlq);
        }

        return new ModelAndView("surveys");
    }

}
