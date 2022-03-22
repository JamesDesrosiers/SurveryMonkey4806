package surveyMonkey.controllers;
import com.google.cloud.firestore.CollectionReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import surveyMonkey.models.Question;
import surveyMonkey.models.Survey;
import surveyMonkey.services.FirebaseInitializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class IndexController {

    private Survey globalSurvey;

    @Autowired
    FirebaseInitializer db;

    @RequestMapping("/")
    public ModelAndView mainPage() {
        return new ModelAndView("index");
    }

    @RequestMapping("/create")
    public Object showCreatePage(String Name, Model model, String type, String text, Integer lower, Integer upper) throws InterruptedException {
        switch (type){
            case "mc" :
                String[] arr = text.split(" ");
                Map<String, Number> mcq  = new HashMap<String, Number>();
                for (String ss : arr){
                    mcq.put(ss, 0);
                }
                Question mcqQuestion = new Question();
                mcqQuestion.setType(type);
                mcqQuestion.setQuestion(Name);
                mcqQuestion.setMcq(mcq);
                globalSurvey.getQuestions().add(mcqQuestion);
                model.addAttribute("survey", globalSurvey);
                break;
            case "range" :
                Question rangeQuestion = new Question();
                rangeQuestion.setType(type);
                rangeQuestion.setQuestion(Name);
                Map<String, Number> range  = new HashMap<String, Number>();
                for (int k = lower; k < upper; k++){
                    range.put(String.valueOf(k), 0);
                }
                rangeQuestion.setRanges(range);
                globalSurvey.getQuestions().add(rangeQuestion);
                model.addAttribute("survey", globalSurvey);
                break;
            case "text" :
                Question textQuestion = new Question();
                textQuestion.setType(type);
                textQuestion.setQuestion(Name);
                textQuestion.setAnswers(new ArrayList<String>());
                globalSurvey.getQuestions().add(textQuestion);
                model.addAttribute("survey", globalSurvey);
                break;
            case "create" :
                globalSurvey = new Survey();
                globalSurvey.setTitle(Name);
                model.addAttribute("survey", globalSurvey);
        }
        return new ModelAndView("create");
    }

    @RequestMapping("/mc")
    public Object showMcPage(Model model) throws InterruptedException {
        model.addAttribute("survey", globalSurvey);
        return new ModelAndView("mc");
    }

    @RequestMapping("/range")
    public Object showRangePage(Model model) throws InterruptedException {
        model.addAttribute("survey", globalSurvey);
        return new ModelAndView("range");
    }

    @RequestMapping("/text")
    public Object showTextPage(Model model) throws InterruptedException {
        model.addAttribute("survey", globalSurvey);
        return new ModelAndView("text");
    }

    @RequestMapping("/success")
    public Object login(Model model) throws InterruptedException {
        CollectionReference responsesCR = db.getFirebase().collection("surveys");
        responsesCR.add(globalSurvey);
        return new ModelAndView("/success");
    }
}
