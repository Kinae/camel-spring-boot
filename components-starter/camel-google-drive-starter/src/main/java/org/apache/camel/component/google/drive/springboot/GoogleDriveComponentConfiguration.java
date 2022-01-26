/*
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
package org.apache.camel.component.google.drive.springboot;

import java.util.List;
import javax.annotation.Generated;
import org.apache.camel.component.google.drive.GoogleDriveClientFactory;
import org.apache.camel.component.google.drive.GoogleDriveConfiguration;
import org.apache.camel.spring.boot.ComponentConfigurationPropertiesCommon;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Manage files in Google Drive.
 * 
 * Generated by camel-package-maven-plugin - do not edit this file!
 */
@Generated("org.apache.camel.springboot.maven.SpringBootAutoConfigurationMojo")
@ConfigurationProperties(prefix = "camel.component.google-drive")
public class GoogleDriveComponentConfiguration
        extends
            ComponentConfigurationPropertiesCommon {

    /**
     * Whether to enable auto configuration of the google-drive component. This
     * is enabled by default.
     */
    private Boolean enabled;
    /**
     * Google drive application name. Example would be camel-google-drive/1.0
     */
    private String applicationName;
    /**
     * Client ID of the drive application
     */
    private String clientId;
    /**
     * To use the shared configuration. The option is a
     * org.apache.camel.component.google.drive.GoogleDriveConfiguration type.
     */
    private GoogleDriveConfiguration configuration;
    /**
     * Delegate for wide-domain service account
     */
    private String delegate;
    /**
     * Specifies the level of permissions you want a drive application to have
     * to a user account. See https://developers.google.com/drive/web/scopes for
     * more info.
     */
    private List<String> scopes;
    /**
     * Allows for bridging the consumer to the Camel routing Error Handler,
     * which mean any exceptions occurred while the consumer is trying to pickup
     * incoming messages, or the likes, will now be processed as a message and
     * handled by the routing Error Handler. By default the consumer will use
     * the org.apache.camel.spi.ExceptionHandler to deal with exceptions, that
     * will be logged at WARN or ERROR level and ignored.
     */
    private Boolean bridgeErrorHandler = false;
    /**
     * Whether the producer should be started lazy (on the first message). By
     * starting lazy you can use this to allow CamelContext and routes to
     * startup in situations where a producer may otherwise fail during starting
     * and cause the route to fail being started. By deferring this startup to
     * be lazy then the startup failure can be handled during routing messages
     * via Camel's routing error handlers. Beware that when the first message is
     * processed then creating and starting the producer may take a little time
     * and prolong the total processing time of the processing.
     */
    private Boolean lazyStartProducer = false;
    /**
     * Whether autowiring is enabled. This is used for automatic autowiring
     * options (the option must be marked as autowired) by looking up in the
     * registry to find if there is a single instance of matching type, which
     * then gets configured on the component. This can be used for automatic
     * configuring JDBC data sources, JMS connection factories, AWS Clients,
     * etc.
     */
    private Boolean autowiredEnabled = true;
    /**
     * To use the GoogleCalendarClientFactory as factory for creating the
     * client. Will by default use BatchGoogleDriveClientFactory. The option is
     * a org.apache.camel.component.google.drive.GoogleDriveClientFactory type.
     */
    private GoogleDriveClientFactory clientFactory;
    /**
     * OAuth 2 access token. This typically expires after an hour so
     * refreshToken is recommended for long term usage.
     */
    private String accessToken;
    /**
     * Client secret of the drive application
     */
    private String clientSecret;
    /**
     * Sets .json file with credentials for Service account
     */
    private String keyResource;
    /**
     * OAuth 2 refresh token. Using this, the Google Calendar component can
     * obtain a new accessToken whenever the current one expires - a necessity
     * if the application is long-lived.
     */
    private String refreshToken;

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public GoogleDriveConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(GoogleDriveConfiguration configuration) {
        this.configuration = configuration;
    }

    public String getDelegate() {
        return delegate;
    }

    public void setDelegate(String delegate) {
        this.delegate = delegate;
    }

    public List<String> getScopes() {
        return scopes;
    }

    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }

    public Boolean getBridgeErrorHandler() {
        return bridgeErrorHandler;
    }

    public void setBridgeErrorHandler(Boolean bridgeErrorHandler) {
        this.bridgeErrorHandler = bridgeErrorHandler;
    }

    public Boolean getLazyStartProducer() {
        return lazyStartProducer;
    }

    public void setLazyStartProducer(Boolean lazyStartProducer) {
        this.lazyStartProducer = lazyStartProducer;
    }

    public Boolean getAutowiredEnabled() {
        return autowiredEnabled;
    }

    public void setAutowiredEnabled(Boolean autowiredEnabled) {
        this.autowiredEnabled = autowiredEnabled;
    }

    public GoogleDriveClientFactory getClientFactory() {
        return clientFactory;
    }

    public void setClientFactory(GoogleDriveClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getKeyResource() {
        return keyResource;
    }

    public void setKeyResource(String keyResource) {
        this.keyResource = keyResource;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}