package surveyMonkey.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Responses {
    private String documentId;
    private List<String> answers;

    public Responses(){

    }

    public Responses(String documentId){
        this.documentId = documentId;
    }

    public Responses(String documentId, List<String> answers){
        this.documentId = documentId;
        this.answers = answers;
    }

    public String toString(){
        String msg = documentId;
        for (String answer: answers){
            msg += " " + answer;
        }
        return msg;
    }
}
