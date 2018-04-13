package com.showcase.securitydemo.config;

import com.google.common.collect.Lists;
import com.showcase.securitydemo.service.AuthorityService;
import com.showcase.securitydemo.service.AuthorityService;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Service("myRoleHierarchy")
public class MyRoleHierarchyImpl implements RoleHierarchy {

    @Autowired
    AuthorityService authorityService;

    /**
     *
     * @return
     */
    public Map<String, Set<String>> getAllSonAuthoritiesMap() {
        return  authorityService.getChildrenAuthorities();
    }


    @Override
    public Collection<GrantedAuthority> getReachableGrantedAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Map<String,  Set<String>> allSonAuthoritiesMap = getAllSonAuthoritiesMap();
        Set<GrantedAuthority> reachableRoles = new HashSet<GrantedAuthority>();
        for (GrantedAuthority authority : authorities) {
            String authority1 = authority.getAuthority();
            Set<String> grantedAuthorities = allSonAuthoritiesMap.get(authority1);
            if (CollectionUtils.isNotEmpty(grantedAuthorities)) {
                reachableRoles.addAll(grantedAuthorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
            }
            reachableRoles.add(authority);
        }
        return reachableRoles;
    }
}
