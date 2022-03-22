package surveyMonkey;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import surveyMonkey.models.Response;

import surveyMonkey.models.Survey;
import surveyMonkey.models.Question;
import java.util.ArrayList;

public class SurveyTests {

    Survey temp;

    @BeforeEach
    public void setup(){
        temp = new Survey();
    }

    @Test
    public void OwnerIDTest(){
        temp.setOwnerId("22");
        assert("22".equals(temp.getOwnerId()));
    }

    @Test
    public void StatusTest(){
        temp.setStatus(true);
        assert(temp.getStatus() == true);
    }

    @Test
    public void setTitleTest(){
        temp.setTitle("22");
        assert("22".equals(temp.getTitle()));
    }

    @Test
    public void QuestionTest(){
        ArrayList<Question> questions = new ArrayList<Question>();
        questions.add(new Question());
        questions.add(new Question());
        questions.add(new Question());
        temp.setQuestions(questions);
        assert(temp.getQuestions().equals(questions));

    }

}
