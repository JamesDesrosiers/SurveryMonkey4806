package surveyMonkey.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class RangeQuestion extends Question{
    private int lowerLimit;
    private int upperLimit;
}
