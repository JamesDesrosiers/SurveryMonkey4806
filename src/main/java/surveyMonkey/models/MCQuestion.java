package surveyMonkey.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
public class MCQuestion extends Question{
    private List<String> choices;
}
