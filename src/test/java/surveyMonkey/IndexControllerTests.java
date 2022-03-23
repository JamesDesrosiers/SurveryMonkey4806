package surveyMonkey;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import surveyMonkey.controllers.IndexController;

@SpringBootTest
@AutoConfigureMockMvc
public class IndexControllerTests {
    @Autowired
    private MockMvc mockmvc;

    @Autowired
    private IndexController con;

    @Test
    public void testLoad() throws Exception{
        assert(con != null);
    }

    @Test
    public void testIndex() throws Exception{
        mockmvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("index"));
    }
}
