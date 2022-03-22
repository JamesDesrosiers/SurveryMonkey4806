package surveyMonkey.models;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class Question extends Model {
    private String type;
    private String question;
    private List<String> surveyIds;
    private Number upperLimit;
    private Number lowerLimit;
    private Map<Number, Number> ranges;
    private Map<String, Number> mcq;
    private List<String> answers;

    public void setType(String type) {
        this.type = type;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setSurveyIds(List<String> surveyIds) {
        this.surveyIds = surveyIds;
    }

    public void setUpperLimit(Number upperLimit) {
        this.upperLimit = upperLimit;
    }

    public void setLowerLimit(Number lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public void setRanges(Map<Number, Number> ranges) {
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

    public List<String> getSurveyIds() {
        return surveyIds;
    }

    public Number getUpperLimit() {
        return upperLimit;
    }

    public Number getLowerLimit() {
        return lowerLimit;
    }

    public Map<Number, Number> getRanges() {
        return ranges;
    }

    public Map<String, Number> getMcq() {
        return mcq;
    }

    public List<String> getAnswers() {
        return answers;
    }
}
