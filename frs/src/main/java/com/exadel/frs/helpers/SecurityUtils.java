package com.exadel.frs.helpers;

import com.exadel.frs.entity.Client;
import com.exadel.frs.repository.AppModelRepository;
import com.exadel.frs.repository.AppRepository;
import com.exadel.frs.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public final class SecurityUtils {

    private final ClientRepository clientRepository;
    private final AppRepository appRepository;
    private final AppModelRepository appModelRepository;

    public Client getPrincipal() {
        // todo: get Id from authentication and replace clientRepository.findByUsername method
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return clientRepository.findByUsername(principal.toString()).orElseThrow();
    }

    public boolean isClientAppOwner(Long appId, Long clientId) {
        return appRepository.findByIdAndOwnerId(appId, clientId).isPresent();
    }

    public boolean isAppHasAccessToModel(String appGuid, String modelGuid) {
        return appModelRepository.findByAppGuidAndModelGuid(appGuid, modelGuid).isPresent();
    }

}
