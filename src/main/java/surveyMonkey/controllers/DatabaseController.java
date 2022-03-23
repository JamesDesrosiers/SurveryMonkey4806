package surveyMonkey.controllers;

import org.springframework.web.bind.annotation.*;
import surveyMonkey.services.DatabaseService;

@RestController
public class DatabaseController {
    
    public DatabaseService databaseService;
    
    public DatabaseController(DatabaseService databaseService){
        this.databaseService = databaseService;
    }

    /*@PostMapping("/createUser")
    public String createResponse(@RequestBody User user) throws InterruptedException, ExecutionException {
        return databaseService.createResponse(response);
    }

    @GetMapping("/getUser")
    public database getResponse(@RequestParam String documentId) throws InterruptedException, ExecutionException{
        return databaseService.getResponse(documentId);
    }

    @PutMapping("/updateUser")
    public String getResponse(@RequestBody User user) throws InterruptedException, ExecutionException{
        return databaseService.updateResponse(response);
    }

    @PutMapping("/deleteUser")
    public String deleteResponse(@RequestParam String documentId) throws InterruptedException, ExecutionException{
        return databaseService.deleteResponse(documentId);
    }*/
}
