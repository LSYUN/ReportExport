package com.dx.website.controllers;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by Administrator on 2017/10/27.
 */
public class ReportControllerTest extends UnitTestBase {
    @Before
    public void setup() throws Exception {
        super.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getReport() throws Exception {
        mockMvc.perform(get("/api/report/generate")
                .contentType(contentType))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }
}
