package surveyMonkey.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
public class Survey extends Model{
    private String ownerId;
    private Boolean status;
    private String title;
    private List<Question> questions;

}
