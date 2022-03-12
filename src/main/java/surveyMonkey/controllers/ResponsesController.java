package surveyMonkey.controllers;

import org.springframework.web.bind.annotation.*;
import surveyMonkey.services.ResponsesService;
import surveyMonkey.models.Response;

import java.util.concurrent.ExecutionException;

@RestController
public class ResponsesController {

    public ResponsesService responsesService;

    public ResponsesController(ResponsesService responsesService){
        this.responsesService = responsesService;
    }

    @PostMapping("/create")
    public String createResponse(@RequestBody Response response) throws InterruptedException, ExecutionException{
        return responsesService.createResponse(response);
    }

    @GetMapping("/get")
    public Response getResponse(@RequestParam String documentId) throws InterruptedException, ExecutionException{
        return responsesService.getResponse(documentId);
    }

    @PutMapping("/update")
    public String getResponse(@RequestBody Response response) throws InterruptedException, ExecutionException{
        return responsesService.updateResponse(response);
    }

    @PutMapping("/delete")
    public String deleteResponse(@RequestParam String documentId) throws InterruptedException, ExecutionException{
        return responsesService.deleteResponse(documentId);
    }

}
