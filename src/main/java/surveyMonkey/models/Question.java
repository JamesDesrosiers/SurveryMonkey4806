package surveyMonkey.models;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class Question extends Model {
    private String type;
    private String question;
    private Map<String, Number> ranges;
    private Map<String, Number> mcq;
    private List<String> answers;

    public void setType(String type) {
        this.type = type;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setRanges(Map<String, Number> ranges) {
        this.ranges = ranges;
    }

    public void setMcq(Map<String, Number> mcq) {
        this.mcq = mcq;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public String getType() {
        return type;
    }

    public String getQuestion() {
        return question;
    }

    public Map<String, Number> getRanges() {
        return ranges;
    }

    public Map<String, Number> getMcq() {
        return mcq;
    }

    public List<String> getAnswers() {
        return answers;
    }
}
