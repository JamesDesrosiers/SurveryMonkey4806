package surveyMonkey.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
public class Survey {
    private String owner;
    private String status;
    private String title;
    private String surveyId;
    private List<Question> questions;
    private List<Submission> submissions;
}
