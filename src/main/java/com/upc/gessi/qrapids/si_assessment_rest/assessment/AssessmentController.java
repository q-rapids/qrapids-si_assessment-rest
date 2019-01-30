package com.upc.gessi.qrapids.si_assessment_rest.assessment;

import Assessment.DTOSIAssessment;
import Assessment.SIAssessment;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AssessmentController {

    @PostMapping("/api/si/assessment")
    public DTOSIAssessment assessSI (@RequestParam("SIid") String id,
                                     @RequestParam("factorNames") List<String> factorNames,
                                     @RequestParam("factorValues") List<String> factorValues,
                                     @RequestParam("network") MultipartFile network) throws Exception {

        Map<String, String> factors = new HashMap<>();
        if (factorNames.size() == factorValues.size()) {
            for (int i = 0; i < factorNames.size(); i++) {
                factors.put(factorNames.get(i), factorValues.get(i));
            }
        }

        File BNFile = File.createTempFile("network", ".dne", null);
        InputStream fileInput = network.getInputStream();
        FileUtils.copyInputStreamToFile(fileInput, BNFile);

        return SIAssessment.AssessSI(id, factors, BNFile);
    }

}
