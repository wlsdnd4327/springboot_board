package org.koreait.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.koreait.entities.SiteConfigEntity;
import org.koreait.repositories.SiteConfigRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SiteConfigInfoService {

    private final SiteConfigRepository repository;

    public <T> T get(String configId, Class<T> clazz){
        return get(configId,clazz,null);
    }

    public <T> T get(String configId, TypeReference<T> typeReference){
        return get(configId,null,typeReference);
    }

    public <T> T get(String configId, Class<T> clazz, TypeReference<T> typeReference){
        try {
            SiteConfigEntity config = repository.findById(configId).orElse(null);

            if (config == null || config.getValues().isBlank() || config.getValues() == null) {
                return null;
            }

            String values = config.getValues();
            ObjectMapper om = new ObjectMapper();
            T data = null;
            try {
                if (clazz == null) data = om.readValue(values, typeReference);
                else data = om.readValue(values, clazz);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return data;
        }catch(Exception e){return null;}
    }
}
