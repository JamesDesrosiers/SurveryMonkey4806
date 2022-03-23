package surveyMonkey;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import surveyMonkey.controllers.ResponsesController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@SpringBootTest
@AutoConfigureMockMvc
public class ResponsesControllerTest {
    @Autowired
    private MockMvc mockmvc;

    @Autowired
    private ResponsesController con;

    @Test
    public void testLoad() throws Exception{
        assert(con != null);
    }

    @Test
    public void testIndex() throws Exception{
        mockmvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("index"));
    }
}
