package com.smartvid.directory.controller;

import com.smartvid.directory.DirectoryApplication;
import com.smartvid.directory.model.DirectoryItem;
import com.smartvid.directory.model.enums.ItemType;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
//@WebMvcTest(FileManagerController.class)
@SpringBootTest(classes = DirectoryApplication.class)
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
@WebAppConfiguration
public class DirectoryApplicationServiceTest {
    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    private RestDocumentationResultHandler document;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Before
    public void before() {
        this.document = document("{method-name}",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()));
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .apply(documentationConfiguration(this.restDocumentation).snippets())
                .alwaysDo(this.document).build();
    }

    @Test
    public void shouldReturnSubDirsWithFilesCount() throws Exception {
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/dir/{dirName}", "one")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$[0].path", containsString("one\\sub_one")))
                .andDo(document("{method-name}",
                        pathParameters(
                                parameterWithName("dirName").description("Directory name")
                        ),
                        responseFields(
                                fieldWithPath("[].path").type(String.class).description("The path to directory"),
                                fieldWithPath("[].children").type(DirectoryItem.class)
                                        .description("Directory sub folders"),
                                fieldWithPath("[].filesCount").type(Integer.class)
                                        .description("Files count in a directory"),
                                fieldWithPath("[].type").type(ItemType.class).description("Maybe directory or file")
                        )));
    }

    @Test
    public void shouldReturnListOfFilesInDir() throws Exception {
        String dirPath = "one/sub_one";
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/files/{dirPath}", dirPath)
                .servletPath("/api/files/" + dirPath))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andDo(document("{method-name}",
                        pathParameters(
                                parameterWithName("dirPath").description("Directory path")
                        ),
                        responseFields(
                                fieldWithPath("[].path").type(String.class).description("The path to the file"),
                                fieldWithPath("[].type").type(ItemType.class).description("Maybe directory or file")
                        )));
    }

    @Test
    public void shouldReturnFileAttributesForAGivenDir() throws Exception {
        String filePath = "one/sub_one/file.json";
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/attributes/{filePath}", filePath)
                .servletPath("/api/attributes/" + filePath)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andDo(document("{method-name}",
                        pathParameters(
                                parameterWithName("filePath").description("File path")
                        ),
                        responseFields(
                                fieldWithPath("size").type(Integer.class).description("File size in Kb"),
                                fieldWithPath("hidden").type(Boolean.class).description("File is hidden"),
                                fieldWithPath("createdDate").type(String.class)
                                        .description("File creation date in UTC"),
                                fieldWithPath("modifiedDate").type(String.class)
                                        .description("File modification date in UTC"),
                                fieldWithPath("rights").type(String.class).description("File permissions")
                        )));
    }
}