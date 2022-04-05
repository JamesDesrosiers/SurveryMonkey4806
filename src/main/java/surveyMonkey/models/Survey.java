package surveyMonkey.models;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.common.collect.ArrayListMultimap;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class Survey extends Model{
    private String ownerEmail;
    private Boolean status;
    private String title;
    private List<Question> questions;

    public Survey() {
        questions = new ArrayList<Question>();
        status = true;
    }

    public void setSurvey(DocumentSnapshot document) {
        setId(document.getId());
        setStatus(document.getBoolean("status"));
        setTitle(document.getString("title"));
        setOwnerEmail(document.getString("ownerEmail"));

        ArrayList<HashMap<String, Object>> qs = (ArrayList)document.get("questions");
        ArrayList<Question> questions = new ArrayList();
        for(HashMap<String, Object> question : qs) {
            questions.add(new Question((String)question.get("question"),
                            (HashMap<String, Number>)question.get("ranges"),
                            (HashMap<String, Number>)question.get("mcq"),
                            (List<String>)question.get("answers")));
        }
        setQuestions(questions);
    }

    public Survey(DocumentSnapshot document) {
        setSurvey(document);
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public ArrayList<HashMap<String, Object>> getMapList() {
        ArrayList<HashMap<String, Object>> l = new ArrayList();
        for(Question q : getQuestions()){
            HashMap<String, Object> m = new HashMap();
            m.put("id", null);
            m.put("question", q.getQuestion());
            m.put("type", q.getType());
            m.put("mcq", q.getMcq());
            m.put("answers", q.getAnswers());
            m.put("ranges", q.getRanges());
            l.add(m);
        }
        return l;
    }
}
