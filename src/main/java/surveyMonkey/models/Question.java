package surveyMonkey.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Getter
@Setter
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

}
