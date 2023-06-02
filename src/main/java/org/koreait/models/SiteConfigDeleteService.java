package org.koreait.models;

import lombok.RequiredArgsConstructor;
import org.koreait.entities.SiteConfigEntity;
import org.koreait.repositories.SiteConfigRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SiteConfigDeleteService {

    private final SiteConfigRepository repository;

    public void delete(String configId){
        SiteConfigEntity config = repository.findById(configId).orElse(null);
        if(config == null) return;
        repository.delete(config);
        repository.flush();
    }
}
