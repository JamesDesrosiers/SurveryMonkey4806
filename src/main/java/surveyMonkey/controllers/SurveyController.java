package surveyMonkey.controllers;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firestore.v1.Write;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
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

    @RequestMapping("/status")
    public ModelAndView statusPage(@RequestParam(value="id", required=true) String id, @ModelAttribute("survey") Survey survey) throws ExecutionException, InterruptedException {
        DocumentReference query = db.getFirebase().document("surveys/"+id);

        survey.setSurvey(query.get().get());
        return new ModelAndView("status");
    }

    @RequestMapping(value="/submit", method = RequestMethod.GET)
    public @ResponseBody RedirectView submission(@RequestParam Map<String, String> queryParameters) throws ExecutionException, InterruptedException {
        Survey s = new Survey(
            db.getFirebase().document("surveys/"+queryParameters.get("id")).get().get()
        );

        for(Question q : s.getQuestions()){
            if (q.getType().equals("text")) {
                q.getAnswers().add(queryParameters.get(q.getQuestion()));
            }
            else if(q.getType().equals("mc")) {
                q.getMcq().replace(queryParameters.get(q.getQuestion()), q.getMcq().get(queryParameters.get(q.getQuestion())).intValue()+1);
            }
            else {
                q.getRanges().replace(queryParameters.get(q.getQuestion()), q.getRanges().get(queryParameters.get(q.getQuestion())).intValue()+1);
            }
            ApiFuture<WriteResult> write = db.getFirebase().document("surveys/"+s.getId()).update("questions", s.getMapList());
        }

        return new RedirectView("/");
    }

    @RequestMapping(value="/close", method = RequestMethod.GET)
    public Object close(@RequestParam Map<String, String> queryParameters) throws ExecutionException, InterruptedException {

        ApiFuture<WriteResult> write = db.getFirebase().document("surveys/"+queryParameters.get("id")).update("status", false);

        return new RedirectView("/dashboard");
    }

    @RequestMapping("/surveys")
    public ModelAndView resultsPage(@RequestParam(value="id", required=true) String id, @ModelAttribute("survey") Survey survey) throws ExecutionException, InterruptedException {
        DocumentReference query = db.getFirebase().document("surveys/"+id);

        survey.setSurvey(query.get().get());
        return new ModelAndView("surveys");
    }

}
