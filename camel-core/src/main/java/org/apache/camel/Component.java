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
package org.apache.camel;

/**
 * A <a href="http://camel.apache.org/component.html">component</a> is
 * a factory of {@link Endpoint} objects.
 * 
 * @version 
 */
public interface Component extends CamelContextAware {

    /**
     * Attempt to resolve an endpoint for the given URI if the component is
     * capable of handling the URI.
     * <p/>
     * See {@link #useRawUri()} for controlling whether the passed in uri
     * should be as-is (raw), or encoded (default).
     * 
     * @param uri the URI to create; either raw or encoded (default)
     * @return a newly created {@link Endpoint} or null if this component cannot create
     *         {@link Endpoint} instances using the given uri
     * @throws Exception is thrown if error creating the endpoint
     * @see #useRawUri()
     */
    Endpoint createEndpoint(String uri) throws Exception;

    /**
     * Whether to use raw or encoded uri, when creating endpoints.
     * <p/>
     * <b>Notice:</b> When using raw uris, then the parameter values is raw as well.
     *
     * @return <tt>true</tt> to use raw uris, <tt>false</tt> to use encoded uris (default).
     *
     * @since Camel 2.11.0
     */
    boolean useRawUri();

    /**
     * Attempt to create a configuration object from the given uri
     *
     * @param uri the configuration URI
     * @return a newly created {@link EndpointConfiguration}
     * @throws Exception is thrown if the configuration URI is invalid
     *
     * @since Camel 2.9.0
     */
    EndpointConfiguration createConfiguration(String uri) throws Exception;
}
