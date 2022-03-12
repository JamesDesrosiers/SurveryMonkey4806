package surveyMonkey.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;
import surveyMonkey.models.Responses;

import java.util.concurrent.ExecutionException;

@Service
public class ResponsesService {

    public String createResponse(Responses response) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection("responses").document(response.getDocumentId()).set(response);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public Responses getResponse(String documentId) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("responses").document(documentId);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        Responses response;
        if (document.exists()) {
            response = document.toObject(Responses.class);
            return response;
        } else {
            return null;
        }
    }

    public String updateResponse(Responses response) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = dbFirestore.collection("responses").document(response.getDocumentId()).set(response);
        return writeResult.get().getUpdateTime().toString();
    }

    public String deleteResponse(String documentId){
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResuilt = dbFirestore.collection("responses").document(documentId).delete();
        return "Successfully deleted " + documentId;
    }
}
