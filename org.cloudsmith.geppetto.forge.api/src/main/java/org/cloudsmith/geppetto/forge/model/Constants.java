/**
 * Copyright 2012-, Cloudsmith Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package org.cloudsmith.geppetto.forge.model;

import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.List;

import org.cloudsmith.geppetto.forge.v1.model.ModuleInfo;
import org.cloudsmith.geppetto.forge.v2.model.Module;
import org.cloudsmith.geppetto.forge.v2.model.Release;
import org.cloudsmith.geppetto.forge.v2.model.Tag;
import org.cloudsmith.geppetto.forge.v2.model.User;

import com.google.gson.reflect.TypeToken;

/**
 * Constants pertaining to the ForgeAPI v2 API
 */
public interface Constants {
	/**
	 * The default base URL
	 */
	String FORGE_SERVICE_BASE_URL = "http://forgeapi.puppetlabs.com";

	/**
	 * Binding name for base URI
	 */
	String BASE_URL_NAME = "forge.service.baseURI"; //$NON-NLS-1$

	/**
	 * Binding name for OAuth clientId
	 */
	String OAUTH_CLIENT_ID = "forge.oauth.client.id";

	/**
	 * Binding name for OAuth clientSecret
	 */
	String OAUTH_CLIENT_SECRET = "forge.oauth.client.secret";

	/**
	 * Binding name for URL used by the Forge v1 API
	 */
	String API_V1_URL_NAME = "forge.api.v1.url";

	/**
	 * Binding name for URL used by the Forge v2 API
	 */
	String API_V2_URL_NAME = "forge.api.v2.url";

	/**
	 * Binding name for URL used by the Forge OAuth API
	 */
	String API_OAUTH_URL_NAME = "forge.api.oauth.url";

	/**
	 * URI path segment used for commands specific to files
	 */
	String COMMAND_GROUP_FILES = "files";

	/**
	 * URI path segment used for commands specific to user emails
	 */
	String COMMAND_GROUP_EMAIL = "email"; //$NON-NLS-1$

	/**
	 * URI path segment used for commands specific to user passwords
	 */
	String COMMAND_GROUP_PASSWORD = "password"; //$NON-NLS-1$

	/**
	 * URI path segment used for commands specific to users
	 */
	String COMMAND_GROUP_USERS = "users"; //$NON-NLS-1$

	/**
	 * URI path segment used for commands specific to releases
	 */
	String COMMAND_GROUP_RELEASES = "releases"; //$NON-NLS-1$

	/**
	 * URI path segment used for commands specific to modules
	 */
	String COMMAND_GROUP_MODULES = "modules"; //$NON-NLS-1$

	/**
	 * URI path segment used for commands specific to tags
	 */
	String COMMAND_GROUP_TAGS = "tags"; //$NON-NLS-1$

	/**
	 * The content type of posts and responses.
	 */
	String CONTENT_TYPE_JSON = "application/json"; //$NON-NLS-1$

	/**
	 * Injection name for credentials
	 */
	String CREDENTIALS_NAME = "credentials"; //$NON-NLS-1$

	/**
	 * The string used when presenting us to the server
	 */
	String USER_AGENT = "Geppetto/1.0.0"; //$NON-NLS-1$

	/**
	 * The encoding used by the API
	 */
	Charset UTF_8 = Charset.forName("UTF-8"); //$NON-NLS-1$

	// @fmtOff
	/**
	 * A type representing a {@link List} of {@link Module} instances
	 */
	Type LIST_MODULE = new TypeToken<List<Module>>() {}.getType();

	/**
	 * A type representing a {@link List} of {@link Release} instances
	 */
	Type LIST_RELEASE = new TypeToken<List<Release>>() {}.getType();

	/**
	 * A type representing a {@link List} of {@link Release} instances
	 */
	Type LIST_MODULE_INFO = new TypeToken<List<ModuleInfo>>() {}.getType();

	/**
	 * A type representing a {@link List} of {@link Tag} instances
	 */
	Type LIST_TAG = new TypeToken<List<Tag>>() {}.getType();

	/**
	 * A type representing a {@link List} of {@link User} instances
	 */
	Type LIST_USER = new TypeToken<List<User>>() {}.getType();
	// @fmtOff
}
