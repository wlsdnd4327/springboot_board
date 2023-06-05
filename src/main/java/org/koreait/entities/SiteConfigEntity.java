package org.koreait.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;

@Entity
@Data
public class SiteConfigEntity {

    @Id @Column(length=40)
    private String configId;

    @Lob
    @Column(name="_values")
    private String values;
}
