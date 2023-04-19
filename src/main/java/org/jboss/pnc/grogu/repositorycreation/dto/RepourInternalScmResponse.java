package org.jboss.pnc.grogu.repositorycreation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RepourInternalScmResponse {

    String status;

    @JsonProperty("readonly_url")
    String readonlyUrl;
    @JsonProperty("readwrite_url")
    String readWriteUrl;
}
