/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.redhat.examples.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A UserScore service which we rest enable from the RestRouteBuilder.
 */
public class ProviderService {

    // use a tree map so they become sorted
    private final Map<String, Provider> providers = new TreeMap<String, Provider>();
    
    private static final Log LOG = LogFactory.getLog(ProviderService.class);

    public ProviderService() {
        providers.put("John", new Provider(1, "John"));
        providers.put("Duck", new Provider(4, "Duck"));
        providers.put("Lizzie", new Provider(9, "Lizzie"));
    }

    /**
     * Gets a user by the given username
     *
     * @param username  the username of the user
     * @return the user, or <code>null</code> if no user exists
     */
    public Provider getProvider(String username) {
		LOG.warn("Got username: " + username);
        return providers.get(username);
    }

    /**
     * List all users
     *
     * @return the list of all users
     */
    public Collection<Provider> listProviders() {
        return providers.values();
    }

    /**
     * Updates the given user
     *
     * @param username the user's username
	 * @param score the user's score
     */
    public String updateProvider(Provider user) {
		providers.put("" + user.getUserName(), user);
		return "Updated : " + user.getUserName();
    }
 
   /**
     * Creates the given user
     *
     * @param username the user's username
     */
    public Provider createProvider(String username) {
		Provider user = new Provider(0, username);

		providers.put("" + user.getUserName(), user);
		return user;

    }
 
}