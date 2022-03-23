package surveyMonkey.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
public class User extends Model{
    private long pass_Hash;
    private String userId;
    private List<Survey> surveys;
}
