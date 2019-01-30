package com.upc.gessi.qrapids.si_assessment_rest.assessment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.nio.file.Files;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AssessmentController.class)
@AutoConfigureRestDocs
public class AssessmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void assessSI() throws Exception {

        File networkFile = new File("src/test/java/com/upc/gessi/qrapids/si_assessment_rest/assessment/WSA_ProductQuality.dne");
        MockMultipartFile network = new MockMultipartFile("network", "network.dne", "text/plain", Files.readAllBytes(networkFile.toPath()));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .multipart("/api/si/assessment")
                .file(network)
                .param("SIid", "productquality")
                .param("factorNames", "softwarestability", "codequality", "testingstatus")
                .param("factorValues", "Medium", "High", "High");

        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andDo(document("si/assessment",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("SIid")
                                        .description("ID of the Strategic Indicator to be assessed. Has to coincide with its corresponding node's name in the BN file to use (network)."),
                                parameterWithName("factorNames")
                                        .description("List containing the names (IDs) of the Strategic Indicator parent nodes. IDs need to be consistent with the BN file in use (network)."),
                                parameterWithName("factorValues")
                                        .description("List containing the states for the Strategic Indicator parent nodes. States need to be consistent with the BN file in use (network) and be defined in the same order as in factorNames.")
                        ),
                        requestParts(
                                partWithName("network")
                                        .description("File containing the BN to use for the assessment, in DNE format.")
                        ),
                        responseFields(
                                fieldWithPath("probsSICategories")
                                        .description("Array containing objects representing the probability for each category."),
                                fieldWithPath("probsSICategories[].probSICategory")
                                        .description("The category probability, normalized from 0 to 1."),
                                fieldWithPath("probsSICategories[].idSICategory")
                                        .description("The category name.")
                        )));
    }
}