package surveyMonkey.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
public class Response {
    private int questionNum;
    private String response;
    private int submissionNum;
    private String surveyId;
   // private List<String> answers;

    /*public Response(){

    }

    public Response(String documentId){
        this.documentId = documentId;
    }

    public Response(String documentId, List<String> answers){
        this.documentId = documentId;
        this.answers = answers;
    }

    public String toString(){
        String msg = documentId;
        for (String answer: answers){
            msg += " " + answer;
        }
        return msg;
    }*/
}
