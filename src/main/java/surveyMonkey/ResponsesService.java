package surveyMonkey;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class ResponsesService {

    public String createResponse(Responses response) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection("responses").document(response.getName()).set(response);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public Responses getResponse(String documentid) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("responses").document(documentid);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        Responses response;
        if (document.exists()) {
            response = document.toObject(Responses.class);
            return response;
        }
        return null;
    }

    public String updateResponse(Responses response){
        return "";
    }

    public String deleteResponse(String documentid){
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResuilt = dbFirestore.collection("responses").document(documentid).delete();
        return "Successfully deleted " + documentid;
    }
}
