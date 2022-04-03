package surveyMonkey.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import surveyMonkey.models.Survey;
import surveyMonkey.models.Question;

public class CanvasjsChartData {

    static Map<Object, Object> map = null;
    static List<List<Map<Object,Object>>> list = new ArrayList<List<Map<Object,Object>>>();
    static List<Map<Object,Object>> dataPoints1 = new ArrayList<Map<Object,Object>>();

    static {
        map = new HashMap<Object,Object>();
        map.put("label", "18 yrs and Under");
        map.put("y", 7);
        dataPoints1.add(map);

        map = new HashMap<Object, Object>();
        map.put("label", "19 to 32 yrs");
        map.put("y", 51);
        dataPoints1.add(map);

        map = new HashMap<Object,Object>();
        map.put("label", "32 to 45 yrs");
        map.put("y", 12);
        dataPoints1.add(map);

        map = new HashMap<Object,Object>();
        map.put("label", "45 to 60 yrs");
        map.put("y", 17);
        dataPoints1.add(map);

        map = new HashMap<Object,Object>();
        map.put("label", "60 yrs and over");
        map.put("y", 13);
        dataPoints1.add(map);

        list.add(dataPoints1);
    }

    //TODO: UPdate format to allow multiple questions to be rendered via this!
    public static List<List<Map<Object, Object>>> getCanvasjsDataList(Survey survey){

        //get list of questions
        List<Question> li = survey.getQuestions();
        List<Map<Object,Object>> dataPoints1 = new ArrayList<Map<Object,Object>>();
        List<List<Map<Object,Object>>> question= new ArrayList<List<Map<Object,Object>>>();
        List<Object> quiz = new ArrayList<Object>();

        for(Question q : li) {
            if(q.getMcq() != null){
                for(Map.Entry<String, Number> val : q.getMcq().entrySet()){
                    map = new HashMap<Object, Object>();
                    map.put("label", val.getKey());
                    map.put("y", val.getValue());
                    dataPoints1.add(map);
                    //temp
                    break;
                }
                question.add(dataPoints1);
            }else if(q.getRanges() != null){
                for(Map.Entry<String, Number> val : q.getRanges().entrySet()){
                    map = new HashMap<Object, Object>();
                    map.put("label", val.getKey());
                    map.put("y", val.getValue());

                    //temp
                    break;
                }
                question.add(dataPoints1);
            }else{
                quiz.add(q.getAnswers());
                continue;
            }
            quiz.add(question);
        }
        return question;
    }
}
