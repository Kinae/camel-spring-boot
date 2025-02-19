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
package org.apache.camel.language.xquery.springboot;

import java.util.List;
import javax.annotation.Generated;
import org.apache.camel.model.PropertyDefinition;
import org.apache.camel.spring.boot.LanguageConfigurationPropertiesCommon;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Evaluates an XQuery expressions against an XML payload.
 * 
 * Generated by camel-package-maven-plugin - do not edit this file!
 */
@Generated("org.apache.camel.springboot.maven.SpringBootAutoConfigurationMojo")
@ConfigurationProperties(prefix = "camel.language.xquery")
public class XQueryLanguageConfiguration
        extends
            LanguageConfigurationPropertiesCommon {

    /**
     * Whether to enable auto configuration of the xquery language. This is
     * enabled by default.
     */
    private Boolean enabled;
    /**
     * Sets the class name of the result type (type from output) The default
     * result type is NodeSet
     */
    private String type;
    /**
     * Reference to a saxon configuration instance in the registry to use for
     * xquery (requires camel-saxon). This may be needed to add custom functions
     * to a saxon configuration, so these custom functions can be used in xquery
     * expressions.
     */
    private String configurationRef;
    /**
     * Injects the XML Namespaces of prefix - uri mappings
     */
    private List<PropertyDefinition> namespace;
    /**
     * Whether to trim the value to remove leading and trailing whitespaces and
     * line breaks
     */
    private Boolean trim = true;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getConfigurationRef() {
        return configurationRef;
    }

    public void setConfigurationRef(String configurationRef) {
        this.configurationRef = configurationRef;
    }

    public List<PropertyDefinition> getNamespace() {
        return namespace;
    }

    public void setNamespace(List<PropertyDefinition> namespace) {
        this.namespace = namespace;
    }

    public Boolean getTrim() {
        return trim;
    }

    public void setTrim(Boolean trim) {
        this.trim = trim;
    }
}