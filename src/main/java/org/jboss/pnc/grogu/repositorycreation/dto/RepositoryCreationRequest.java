package org.jboss.pnc.grogu.repositorycreation.dto;

import lombok.Data;

// candidate to move to pnc-api
@Data
public class RepositoryCreationRequest {

    String pncBaseUrl;
    String repourBaseUrl;
    RepositoryCreationProcess taskData;
}
