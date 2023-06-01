package org.koreait.controllers.admin;

import lombok.Data;

@Data
public class SiteConfigData {

    private String siteTitle;

    private String siteDescription;

    private String cssJsVersion;

    private String joinTerms;
}
