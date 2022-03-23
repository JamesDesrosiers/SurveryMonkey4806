package surveyMonkey.models;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Survey extends Model{
    private String ownerId;
    private Boolean status;
    private String title;
    private List<Question> questions;

    public Survey() {
        questions = new ArrayList<Question>();
        status = true;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
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

    public String getOwnerId() {
        return ownerId;
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
}
