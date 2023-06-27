package org.koreait.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.koreait.entities.SiteConfigEntity;
import org.koreait.repositories.SiteConfigRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SiteConfigSaveService {

    private final SiteConfigRepository repository;

    public <T> void save(String configId,T t){
        SiteConfigEntity configs = repository.findById(configId).orElseGet(SiteConfigEntity::new);
        ObjectMapper om = new ObjectMapper();
        String values = null;

        try{
            values = om.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        configs.setConfigId(configId);
        configs.setValues(values);
        repository.saveAndFlush(configs);
    }
}
