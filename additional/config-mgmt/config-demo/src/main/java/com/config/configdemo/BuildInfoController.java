package com.config.configdemo;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RefreshScope
public class BuildInfoController {

//    @Value("${build.id}")
//    private String buildId;
//
//    @Value("${build.version}")
//    private String buildVersion;
//
//    @Value("${build.name}")
//    private String buildName;

    private BuildInfo buildInfo;

    @GetMapping("/build-info")
    public String getBuildInfo(){
        return "Build Id: " + buildInfo.getId() + "\nBuild Version: " + buildInfo.getVersion() + "\nBuild Name: " + buildInfo.getName();
    }
}
