package com.praveen.integration.controller;

import com.praveen.cms.CmsApplication;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {CmsApplication.class})
//webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
@AutoConfigureMockMvc
public class LoginControllerIntegrityTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    /*@Test
    public void greetingShouldReturnDefaultMessage() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("user1", "user12345Q%");
        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:"+port+"/cms/customer/signin", HttpMethod.GET, request, String.class);
    }*/

    /*@Test
    @SneakyThrows
    public void TestGetSettings(){
        String response = this.restTemplate
                .withBasicAuth("user1", "user12345Q%")
                .getForObject(String.format("http://localhost:%d/cms/customer/signin",
                        port), String.class);
        System.out.println(response);
        //assertThat(dtoClass.getClientAddress()).isNotEmpty();
    }*/

    /*@Test
    public void TestGet(){
        String userAndPass = "user1:user12345Q%";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Basic " + userAndPass);
        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:"+port+"/cms/customer/signin", HttpMethod.GET, request, String.class);
        System.out.println(response);
    }*/

    @Test
    public void mustLoginWithValidCredentials() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject personJsonObject = new JSONObject();
        personJsonObject.put("email", "jhon@gmail.com");           //"jhon@gmail.com"  user1
        personJsonObject.put("password", "123456");                // user12345Q%
        HttpEntity<String> request = new HttpEntity<String>(personJsonObject.toString(), headers);
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/cms/customer/signin", HttpMethod.POST, request, String.class);
        String[] token = response.getHeaders().get("Authorization").get(0).split(" ");
        assertNotNull(token[0]);
        assertNotNull(token[1]);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }


    /*@Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/cms/customer/signin").with(httpBasic("user1", "user12345Q%")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello, World")));
    }*/

    /*@Test
    @WithMockUser(username = "user1", password = "user12345Q", roles = "USER")
    public void okResponseWithMockUser() throws Exception {
        this.mockMvc.perform(get("/cms/customer/signin")).andExpect(status().isOk());
    }

    @Test
    public void unauthorizedResponseWithNoUser() throws Exception {
        this.mockMvc.perform(get("/cms/customer/signin")).andExpect(status().isUnauthorized());
    }

    @Test
    public void okResponseWithBasicAuthCredentialsForKnownUser() throws Exception {
        this.mockMvc
                .perform(get("/cms/customer/signin").header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString("user:secret".getBytes())))
                .andExpect(status().isOk());
    }


    @Test
    public void mustRegisterUserWithSucess() throws Exception {
        mockMvc.perform(get("/cms/customer/signin").with(httpBasic("user1", "user12345Q%"))
                        //.content(mockJsonBody())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", is(" ")));
    }*/
}
