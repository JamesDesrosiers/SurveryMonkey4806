package surveyMonkey.models;

import com.google.cloud.firestore.annotation.Exclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

@Setter
@Getter
public class Model {

    @Exclude
    public String id;

    public <T extends Model> T withId(@NonNull final String id){
        this.id = id;
        return (T) this;
    }

}
