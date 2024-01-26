package com.linkshrink.backend.controllers.web;

import java.util.HashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.linkshrink.backend.dao.implementations.UrlsDaoJPA;
import com.linkshrink.backend.entity.Url;

@AutoConfigureMockMvc
@SpringBootTest
public class RedirectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private UrlsDaoJPA urlsDaoJPA;

    @Value("${fallbacks.viewUrl}")
    private String fallBackUrl;

    @BeforeEach
    public void setUpMockData() throws Exception {
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
    @DisplayName(value = "Redirected to right url")
    public void succesfullRedirectUrl() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/delta"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("https://cloud.google.com/sql/docs/postgres/users"))
                .andReturn();
    }

    @Test
    @DisplayName(value = "Redirected to right fallback url")
    public void unsuccesfullRedirectUrl() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/random"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl(fallBackUrl))
                .andReturn();
    }

}
