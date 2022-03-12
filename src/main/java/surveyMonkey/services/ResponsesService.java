package surveyMonkey.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import surveyMonkey.models.Response;

import java.util.concurrent.ExecutionException;

@Service
public class ResponsesService {

    @Autowired
    FirebaseInitializer db;

    public String createResponse(Response response) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> collectionsApiFuture = db.getFirebase().collection("responses").document(response.getDocumentId()).set(response);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public Response getResponse(String documentId) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = db.getFirebase().collection("responses").document(documentId);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        Response response;
        if (document.exists()) {
            response = document.toObject(Response.class);
            return response;
        } else {
            return null;
        }
    }

    public String updateResponse(Response response) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> writeResult = db.getFirebase().collection("responses").document(response.getDocumentId()).set(response);
        return writeResult.get().getUpdateTime().toString();
    }

    public String deleteResponse(String documentId){
        ApiFuture<WriteResult> writeResuilt = db.getFirebase().collection("responses").document(documentId).delete();
        return "Successfully deleted " + documentId;
    }
}
