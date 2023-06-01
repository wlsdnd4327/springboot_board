package org.koreait.models;

import lombok.RequiredArgsConstructor;
import org.koreait.controllers.admin.SiteConfigData;
import org.koreait.repositories.SiteConfigRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SiteConfigSaveService {

    private final SiteConfigRepository repository;

    public void save(SiteConfigData siteConfigdata){

    }

}
