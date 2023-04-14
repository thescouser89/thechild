package org.jboss.pnc.grogu.repositorycreation.dto;

// TODO: candidate to move to pnc-apij

import lombok.Data;

@Data
public class RepositoryConfiguration {
    String internalUrl;
    String externalUrl;
    boolean preBuildSyncEnabled;
}
