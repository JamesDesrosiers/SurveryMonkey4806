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
    public ModelAndView resultsPage(@RequestParam(value="id", required=true) String id, @ModelAttribute("survey") Survey survey) throws ExecutionException, InterruptedException {
        DocumentReference query = db.getFirebase().document("surveys/"+id);

        survey.setSurvey(query.get().get());
        return new ModelAndView("surveys");
    }

}