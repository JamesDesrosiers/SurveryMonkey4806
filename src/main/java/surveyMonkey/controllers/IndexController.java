package surveyMonkey.controllers;
import com.google.cloud.firestore.CollectionReference;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import surveyMonkey.models.Question;
import surveyMonkey.models.Survey;
import surveyMonkey.services.FirebaseInitializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class IndexController {

    @Autowired
    FirebaseInitializer db;

    @RequestMapping("/")
    public ModelAndView mainPage() {
        return new ModelAndView("index");
    }

    @RequestMapping("/create")
    public Object showCreatePage(@ModelAttribute("survey") Survey survey, String Name, Model model, String type, String text) throws InterruptedException {
        switch (type){
            case "mc" :
                String[] arr = text.split(" ");
                Map<String, Number> mcq  = new HashMap<String, Number>();
                List questions = new ArrayList<Question>();
                for (String ss : arr){
                    mcq.put(ss, 0);
                }
                Question question = new Question();
                question.setType(type);
                question.setQuestion(Name);
                question.setMcq(mcq);
                questions.add(question);
                survey.setQuestions(questions);
                model.addAttribute("survey", survey);
            case "range" :
                break;
            case "text" :
                break;
            case "create" :
                Survey newSurvey = new Survey();
                newSurvey.setTitle(Name);
                model.addAttribute("survey", newSurvey);
        }
        return new ModelAndView("create");
    }

    @RequestMapping("/mc")
    public Object showMcPage() throws InterruptedException {
        return new ModelAndView("mc");
    }

    @RequestMapping("/Food")
    public Object login(String Food) throws InterruptedException {
        CollectionReference responsesCR = db.getFirebase().collection("responses");
        JSONObject body = new JSONObject();
        body.put("documentId", "favFood");
        body.put("Favourite Food", Food);
        responsesCR.add(body);
        return new ModelAndView("/success");
    }
}
