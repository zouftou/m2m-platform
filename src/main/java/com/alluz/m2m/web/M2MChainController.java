package com.alluz.m2m.web;

import com.alluz.m2m.domain.M2MChainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class M2MChainController {
    private static final Logger LOGGER = LoggerFactory.getLogger(M2MChainController.class);

    private final M2MChainService m2mChainService;

    public M2MChainController(M2MChainService m2mChainService) {
        this.m2mChainService = m2mChainService;
    }

    @RequestMapping("/chains/{chainId}/start")
    public ResponseEntity<String> startChain(@PathVariable("chainId") Long chainId) {
        m2mChainService.startChain(chainId);
        LOGGER.debug("Starting chain : {}",chainId);
        return ResponseEntity.ok("Up");
    }

    @RequestMapping("/chains/{chainId}/stop")
    public ResponseEntity<String> stopChain(@PathVariable("chainId") Long chainId) {
        m2mChainService.stopChain(chainId);
        LOGGER.debug("Stopping chain : {}",chainId);
        return ResponseEntity.ok("Down");
    }
}
