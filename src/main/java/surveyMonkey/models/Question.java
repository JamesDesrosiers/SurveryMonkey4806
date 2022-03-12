package surveyMonkey.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class Question extends Model{
    private String type;
    private String question;
    private int questionNum;
    private String surveyId;
}
