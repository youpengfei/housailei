package com.showcase.gateway.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.showcase.gateway.web.rest.dto.RouteVM;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing Gateway configuration.
 */
@RestController
@RequestMapping("/api/gateway")
public class GatewayResource {

    private final Logger log = LoggerFactory.getLogger(GatewayResource.class);

    @Inject
    private RouteLocator routeLocator;

    @Inject
    private DiscoveryClient discoveryClient;

    /**
     * GET  /routes : get the active routes.
     * @return the ResponseEntity with status 200 (OK) and with body the list of routes
     */
    @GetMapping("/routes")
    @Timed
    public ResponseEntity<List<RouteVM>> activeRoutes() {
        List<Route> routes = routeLocator.getRoutes();
        List<RouteVM> routeVMs = new ArrayList<>();
        routes.forEach(route -> {
            RouteVM routeVM = new RouteVM();
            routeVM.setPath(route.getFullPath());
            routeVM.setServiceId(route.getId());
            routeVM.setServiceInstances(discoveryClient.getInstances(route.getId()));
            routeVMs.add(routeVM);
        });
        return new ResponseEntity<>(routeVMs, HttpStatus.OK);
    }
}
