package surveyMonkey.controllers;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import surveyMonkey.models.Question;
import surveyMonkey.models.Survey;
import surveyMonkey.models.User;
import surveyMonkey.services.FirebaseInitializer;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RestController
public class IndexController {

    private Survey globalSurvey;
    private User user;

    @Autowired
    FirebaseInitializer db;

    @RequestMapping("/")
    public ModelAndView mainPage(@ModelAttribute("surveyList") ArrayList<Survey> surveyList) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = db.getFirebase().collection("surveys").whereEqualTo("status", true).get();
        QuerySnapshot qs = query.get();

        List<QueryDocumentSnapshot> documents = qs.getDocuments();
        for(QueryDocumentSnapshot document : documents){
            Survey s = new Survey(document);
            surveyList.add(s);
        }

        return new ModelAndView("index");
    }

    @RequestMapping("/dashboard")
    public ModelAndView dashboardPage(@ModelAttribute("surveyList") ArrayList<Survey> surveyList, @ModelAttribute("surveyList2") ArrayList<Survey> surveyList2) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = db.getFirebase().collection("surveys").whereEqualTo("status", true).whereEqualTo("ownerEmail", user.getEmail()).get();
        ApiFuture<QuerySnapshot> query2 = db.getFirebase().collection("surveys").whereEqualTo("status", false).whereEqualTo("ownerEmail", user.getEmail()).get();

        QuerySnapshot qs = query.get();
        QuerySnapshot qs2 = query2.get();

        List<QueryDocumentSnapshot> documents = qs.getDocuments();
        List<QueryDocumentSnapshot> documents2 = qs2.getDocuments();

        for(QueryDocumentSnapshot document : documents){
            Survey s = new Survey(document);
            surveyList.add(s);
        }

        for(QueryDocumentSnapshot document : documents2){
            Survey s = new Survey(document);
            surveyList2.add(s);
        }

        return new ModelAndView("dashboard");
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
                globalSurvey.setOwnerEmail(user.getEmail());
                globalSurvey.setStatus(true);
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
        return new ModelAndView("success");
    }

    @RequestMapping("/auth")
    public Object signUp(String password, String email) throws ExecutionException, InterruptedException {
        List<User> userList = getUsers();

        List<User> loggedIn = userList.stream()
                .filter(u -> email.equals(u.getEmail()))
                .collect(Collectors.toList());

        if(loggedIn.size() == 0){
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setPassword(password);
            CollectionReference userCR = db.getFirebase().collection("users");
            userCR.add(newUser);
            user = newUser;
            return new RedirectView("/dashboard");
        } else if(loggedIn.size() == 1 && loggedIn.get(0).getPassword().equals(password)) {
            user = loggedIn.get(0);
            return new RedirectView("/dashboard");
        }else {
            return new RedirectView("/");
        }
    }

    private List<User> getUsers() throws InterruptedException, ExecutionException {
        List<User> userList = new ArrayList<User>();
        CollectionReference books = db.getFirebase().collection("users");
        ApiFuture<QuerySnapshot> querySnapshot = books.get();
        for(DocumentSnapshot doc:querySnapshot.get().getDocuments()) {
            User emp = Objects.requireNonNull(doc.toObject(User.class)).withId(doc.getId());
            userList.add(emp);
        }
        return userList;
    }
}
