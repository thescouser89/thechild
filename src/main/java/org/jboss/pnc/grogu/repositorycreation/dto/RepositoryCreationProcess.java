package org.jboss.pnc.grogu.repositorycreation.dto;

import lombok.Data;

// TODO: candidate to move to pnc-api?
@Data
public class RepositoryCreationProcess {

    RepositoryConfiguration repositoryConfiguration;
    BuildConfiguration buildConfiguration;
    String revision;
}
