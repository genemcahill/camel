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
package org.apache.camel.component.jsch;

import java.net.URI;
import java.util.Map;

import com.jcraft.jsch.JSch;

import org.apache.camel.CamelContext;
import org.apache.camel.component.file.GenericFileEndpoint;
import org.apache.camel.component.file.remote.RemoteFileComponent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  Component providing secure messaging using JSch
 */
public class JschComponent extends RemoteFileComponent<ScpFile> {
    private static final transient Logger LOG = LoggerFactory.getLogger(JschComponent.class);
    static {
        JSch.setConfig("StrictHostKeyChecking",  "yes");
        JSch.setLogger(new com.jcraft.jsch.Logger() {
            @Override
            public boolean isEnabled(int level) {
                return level == FATAL || level == ERROR ? LOG.isErrorEnabled()
                    : level == WARN ? LOG.isWarnEnabled()
                    : level == INFO ? LOG.isInfoEnabled() : LOG.isDebugEnabled();
            }
            @Override
            public void log(int level, String message) {
                if (level == FATAL || level == ERROR) {
                    LOG.error("[JSCH] {}", message);
                } else if (level == WARN) {
                    LOG.warn("[JSCH] {}", message);
                } else if (level == INFO) {
                    LOG.info("[JSCH] {}", message);
                } else {
                    LOG.debug("[JSCH] {}", message);
                }
            }
        });
    }

    public JschComponent() {
    }

    public JschComponent(CamelContext context) {
        super(context);
    }

    @Override
    protected GenericFileEndpoint<ScpFile> buildFileEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        // TODO: revisit stripping the query part; should not be needed with valid uris
        int query = uri.indexOf("?");
        return new ScpEndpoint(uri, this, new ScpConfiguration(new URI(query >= 0 ? uri.substring(0, query) : uri)));
    }

    protected void afterPropertiesSet(GenericFileEndpoint<ScpFile> endpoint) throws Exception {
        // noop
    }

    @Override
    public void doStop() throws Exception {
        // TODO: close all sessions
        super.doStop();
    }
}

