package com.linkshrink.backend.controllers.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.linkshrink.backend.dao.implementations.UrlsDaoJPA;
import com.linkshrink.backend.entity.Url;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@AutoConfigureMockMvc
@SpringBootTest
public class UrlsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private UrlsDaoJPA urlsDaoJPA;

    @Autowired
    private ObjectMapper objectMapper;

    private static Map<String, String> generalUrlParams;

    @BeforeEach
    public void setUpMockData() throws Exception {
        generalUrlParams = new HashMap<String, String>();
        generalUrlParams.put("longUrl", "https://cloud.google.com/sql/docs/postgres/users");
        generalUrlParams.put("expiryAfter", "P3DT2H");
        generalUrlParams.put("shortUrl", "short-url");
        String[] shortUrl = { "alfa-beta", "delta", "gamma" };
        for (var s : shortUrl) {
            Url url = new Url();
            url.setLongUrl("https://cloud.google.com/sql/docs/postgres/users");
            url.setShortUrl(s);
            urlsDaoJPA.save(url);
        }
    }

    @AfterEach
    public void deleteMockData() {
        jdbc.execute("delete from urls");
    }

    @Test
    @DisplayName(value = "New urls created")
    public void createNewUrl() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/urls/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(generalUrlParams)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.longUrl").value(generalUrlParams.get("longUrl")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.shortUrl").value(generalUrlParams.get("shortUrl")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.generated").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andReturn();
    }

    @Test
    @DisplayName(value = "Duplicate urls are not created")
    public void dublicateUrlCheck() throws Exception {
        generalUrlParams.put("shortUrl", "delta");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/urls/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(generalUrlParams)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    @DisplayName(value = "Urls and expiry are auto generated when dont supplied")
    public void generateNewUrl() throws Exception {
        generalUrlParams.remove("shortUrl");
        generalUrlParams.remove("expiryAfter");
        var res =mockMvc.perform(MockMvcRequestBuilders.post("/api/urls/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(generalUrlParams)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.longUrl").value(generalUrlParams.get("longUrl")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.shortUrl").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.expiryAfter").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.generated").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andReturn();                
        Assertions.assertTrue(JsonPath.read(res.getResponse().getContentAsString(), "$.shortUrl").toString().startsWith("@"));
    }

    @Test
    @DisplayName(value = "Get back the url objects created")
    public void getUrl() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/urls/get")
                .contentType(MediaType.APPLICATION_JSON)
                .param("short_url", "delta"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.longUrl").value(generalUrlParams.get("longUrl")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.shortUrl").value("delta"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.generated").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andReturn();
    }

}
