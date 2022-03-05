package surveyMonkey;

import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
public class ResponsesController {

    public ResponsesService responsesService;

    public ResponsesController(ResponsesService responsesService){
        this.responsesService = responsesService;
    }

    @PostMapping("/create")
    public String createResponse(@RequestBody Responses response) throws InterruptedException, ExecutionException{
        return responsesService.createResponse(response);
    }

    @GetMapping("/get")
    public Responses getResponse(@RequestParam String documentid) throws InterruptedException, ExecutionException{
        return responsesService.getResponse(documentid);
    }

    @PutMapping("/update")
    public String getResponse(@RequestBody Responses response) throws InterruptedException, ExecutionException{
        return responsesService.updateResponse(response);
    }

    @PutMapping("/delete")
    public String deleteResponse(@RequestParam String documentid) throws InterruptedException, ExecutionException{
        return responsesService.deleteResponse(documentid);
    }

}
