/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.keycloak.models;

import org.keycloak.provider.ProviderEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public interface RoleContainerModel {

    interface RoleRemovedEvent extends ProviderEvent {
        RoleModel getRole();
        KeycloakSession getKeycloakSession();
    }

    String getId();

    RoleModel getRole(String name);

    RoleModel addRole(String name);

    RoleModel addRole(String id, String name);

    boolean removeRole(RoleModel role);

    Set<RoleModel> getRoles();
    
    Set<RoleModel> getRoles(Integer firstResult, Integer maxResults);

    Set<RoleModel> searchForRoles(String search, Integer first, Integer max);
    
    List<String> getDefaultRoles();

    void addDefaultRole(String name);

    default void updateDefaultRoles(String... defaultRoles) {
        List<String> defaultRolesArray = Arrays.asList(defaultRoles);
        Collection<String> entities = getDefaultRoles();
        Set<String> already = new HashSet<>();
        ArrayList<String> remove = new ArrayList<>();
        for (String rel : entities) {
            if (! defaultRolesArray.contains(rel)) {
                remove.add(rel);
            } else {
                already.add(rel);
            }
        }
        removeDefaultRoles(remove.toArray(new String[] {}));

        for (String roleName : defaultRoles) {
            if (!already.contains(roleName)) {
                addDefaultRole(roleName);
            }
        }
    }

    void removeDefaultRoles(String... defaultRoles);

}
