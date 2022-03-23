package surveyMonkey.models;

import com.google.cloud.firestore.annotation.ServerTimestamp;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Component
public class Submission extends Model {
    private long submissionNum;
    private String surveyId;
    private List<Response> responses;

    @ServerTimestamp
    private Date timestamp;
}
