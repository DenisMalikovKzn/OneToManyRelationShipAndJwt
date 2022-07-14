package com.praveen.unit.controller;

import com.google.gson.Gson;
import com.praveen.cms.CmsApplication;
import com.praveen.cms.api.request.CustomerAddRequest;
import com.praveen.cms.api.request.CustomerSignInRequest;
import com.praveen.cms.api.service.CustomerService;
import net.minidev.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {CmsApplication.class}
)
@AutoConfigureMockMvc
public class RegistrationControllerUnitTest {
    @MockBean
    private CustomerService userService;

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webappContext;

    @Before
    public void init() {
        mvc = MockMvcBuilders.webAppContextSetup(webappContext)
                .build();
    }

    @Test
    public void mustRegisterUserWithSucess()
            throws Exception {
        String payload = "{\r\n" +
                "  \"firstName\": \"ivan\",\r\n" +
                "  \"lastName\": \"Ivanov\",\r\n" +
                "  \"email\": \"Ivanov@mail.ru\",\r\n" +
                "  \"password\": \"Test@123\"\r\n" +
                "}";
        MvcResult mvcResult = mvc.perform(post("http://localhost:8081/cms/customer/signup")
                        .content(mockJsonBody())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
        //.andExpect(status().isCreated());

        String responseData = mvcResult.getResponse().getContentAsString();
        System.out.println(responseData);
    }

    @Test
    public void mustLoginUserWithSuccess()
            throws Exception {

        String payload = "{\r\n" +
                "  \"email\": \"user1\",\r\n" +
                "  \"password\": \"user12345Q%\"\r\n" +
                "}";

        /*MvcResult mvcResult = mvc.perform(post("http://localhost:" + port + "/cms/customer/signin")
                        .content(mockJsonBodyLogin())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();*/
        //.andExpect(jsonPath("$.*", is("")));
        //.andExpect(status().isCreated());

        //String responseData = mvcResult.getResponse().getContentAsString();
        //System.out.println(responseData);


        /*MvcResult mvcResult1 = mvc.perform(post("http://localhost:" + port + "/cms/customer/signin")
                .with(httpBasic("user1","user12345Q%"))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();*/

        /*mvc.perform(post("http://localhost:" + port + "/cms/customer/signin").with(user("user1").password("user12345Q%"))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());*/

       /* mvc.perform(formLogin("http://localhost:" + port + "/cms/customer/signin").user("email","user1").password("password","user12345Q%"))
                .andDo(print());*/

        /*Principal principal = new Principal() {
            @Override
            public String getName() {
                return "user1";
            }
        };

        mvc.perform(post("http://localhost:" + port + "/cms/customer/signin").principal(principal))
                .andExpect(status().isOk())
                .andDo(print());*/



        /*JSONObject json = new JSONObject();
        json.put("email", "user1");
        json.put("password", "user12345Q%");
        this.mvc.perform(post("http://localhost:8081/cms/customer/signin")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json.toString()))
                .andDo(print());*/
                //.andExpect(status().isCreated());




        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/cms/customer/signin")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mockJsonBodyLogin()))
                .andExpect(status().isOk()).andReturn();

        String response = result.getResponse().getContentAsString();
        System.out.println(response);
    }

    /*@Test
    public void mustThrowExceptionWhenEmailAlreadyExists() throws Exception {

        doThrow(new StoreBusinessException("Email!")).when(userService).createUser(mockRegisterRequestDTO());

        mvc.perform(post("/register")
                        .content(mockJsonBody())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Email!")))
                .andExpect(status().isUnprocessableEntity());
    }*/

    public String mockJsonBody() {
        Gson gson = new Gson();
        return gson.toJson(mockRegisterRequestDTO());
    }

    public String mockJsonBodyLogin() {
        Gson gson = new Gson();
        return gson.toJson(mockLogiRequestDTO());
    }

    private CustomerAddRequest mockRegisterRequestDTO() {
        return CustomerAddRequest
                .builder()
                .firstName("Jhon")
                .lastName("Whick")
                .email("jhon1@gmail.com")
                .password("123456")
                .build();
    }

    private CustomerSignInRequest mockLogiRequestDTO() {

        return CustomerSignInRequest.builder()
                .email("user1")
                .password("user12345Q%")
                .build();
    }
}
