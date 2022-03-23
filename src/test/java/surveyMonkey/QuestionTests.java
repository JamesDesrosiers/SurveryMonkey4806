package surveyMonkey;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import surveyMonkey.models.Question;
import java.util.HashMap;
import java.util.ArrayList;

public class QuestionTests {
    Question temp;

    @BeforeEach
    public void setup(){
        temp = new Question();
    }

    @Test
    public void TypeTest(){
        temp.setType("Multiple Choice");
        assert("Multiple Choice".equals(temp.getType()));
    }

    @Test
    public void QuestionTest(){
        temp.setQuestion("What?");
        assert("What?".equals(temp.getQuestion()));
    }

    @Test
    public void RangesTest(){
        HashMap<String, Number> range = new HashMap<String, Number>();
        range.put("Hey", 3);
        temp.setRanges(range);
        assert(range.equals(temp.getRanges()));
    }

    @Test
    public void McqTest(){
        HashMap<String, Number> range = new HashMap<String, Number>();
        range.put("Hey", 3);
        temp.setMcq(range);
        assert(range.equals(temp.getMcq()));
    }

    @Test
    public void AnswersTest(){
        ArrayList<String> stuff = new ArrayList<String>();
        stuff.add("123");
        temp.setAnswers(stuff);
        assert(stuff.equals(temp.getAnswers()));
    }
}
