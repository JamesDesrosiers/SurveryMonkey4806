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

    private Integer min = null;
    private Integer max = null;

    public Question(){ }

    public Question(String question, Map<String, Number> ranges, Map<String, Number> mcq, List<String> answers){
        setQuestion(question);
        if(ranges!=null){
            setType("range");
            setRanges(ranges);
            for(String r : ranges.keySet()) {
                int c = Integer.parseInt(r);
                if (max==null || c > max) {
                    max = c;
                }
                if (min==null || c < min) {
                    min = c;
                }
            }
        }else if(mcq!=null){
            setType("mc");
            setMcq(mcq);
        }else if(answers!=null){
            setType("text");
            setAnswers(answers);
        }
    }

    public Integer[] printableRange() {
        Integer[] pr = new Integer[ranges.size()];
        for(int i=0; i<=max-min; i++){
            pr[i] = i+min;
        }
        return pr;
    }

    public String printJson() {
        Map<String, Number> r;
        Object[] keySet;
        String output = "";
        if (type.equals("text")){
            return "";
        }
        else if (type.equals("mc")){
            r = getMcq();
            keySet = r.keySet().toArray(new String[0]);
        }else{
            r = getRanges();
            keySet = printableRange();
        }
        for (int i=0; i<r.size(); i++) {
            output += "{label: '"+keySet[i].toString()+"', y: "+r.get(keySet[i].toString())+"}";
            if(i+1<r.size()){
                output += ",";
            }
        }
        return output;
    }

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
