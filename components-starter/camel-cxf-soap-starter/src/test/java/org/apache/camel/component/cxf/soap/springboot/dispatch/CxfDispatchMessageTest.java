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
package org.apache.camel.component.cxf.soap.springboot.dispatch;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.w3c.dom.Document;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.common.DataFormat;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.apache.camel.component.cxf.jaxws.CxfEndpoint;
import org.apache.camel.component.cxf.spring.jaxws.CxfSpringEndpoint;
import org.apache.camel.spring.boot.CamelAutoConfiguration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.cxf.spring.boot.autoconfigure.CxfAutoConfiguration;

@DirtiesContext
@CamelSpringBootTest
@SpringBootTest(classes = {
                           CamelAutoConfiguration.class, 
                           CxfDispatchMessageTest.class,
                           CxfDispatchMessageTest.TestConfiguration.class,
                           CxfDispatchTestSupport.ServletConfiguration.class,
                           CxfAutoConfiguration.class
}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CxfDispatchMessageTest extends CxfDispatchTestSupport {

    @Autowired
    ProducerTemplate template;
    
    
    
    
    
    @Test
    public void testDipatchMessage() throws Exception {
        final String name = "Tila";
        Exchange exchange = sendJaxWsDispatchMessage(name, false);
        assertEquals(false, exchange.isFailed(), "The request should be handled sucessfully");

        org.apache.camel.Message response = exchange.getMessage();
        assertNotNull(response, "The response message must not be null");

        String value = decodeResponseFromMessage(response.getBody(InputStream.class), exchange);
        assertTrue(value.endsWith(name), "The response body must match the request");
    }

    @Test
    public void testDipatchMessageOneway() throws Exception {
        final String name = "Tila";
        Exchange exchange = sendJaxWsDispatchMessage(name, true);
        assertEquals(false, exchange.isFailed(), "The request should be handled sucessfully");

        org.apache.camel.Message response = exchange.getOut();
        assertNotNull(response, "The response message must not be null");

        assertNull(response.getBody(), "The response body must be null");
    }

    protected Exchange sendJaxWsDispatchMessage(final String name, final boolean oneway) {
        Exchange exchange = template.send("direct:producer", new Processor() {
            public void process(final Exchange exchange) {
                InputStream request
                        = encodeRequestInMessage(oneway ? MESSAGE_ONEWAY_TEMPLATE : MESSAGE_TEMPLATE, name, exchange);
                exchange.getIn().setBody(request, InputStream.class);
                // set the operation for oneway; otherwise use the default operation                
                if (oneway) {
                    exchange.getIn().setHeader(CxfConstants.OPERATION_NAME, INVOKE_ONEWAY_NAME);
                }
            }
        });
        return exchange;
    }

    private static InputStream encodeRequestInMessage(String form, String name, Exchange exchange) {
        String payloadstr = String.format(form, name);
        InputStream message = null;
        try {
            message = new ByteArrayInputStream(payloadstr.getBytes("utf-8"));
        } catch (Exception e) {
            // ignore and let it fail
        }
        return message;
    }

    private String decodeResponseFromMessage(InputStream message, Exchange exchange) {
        String value = null;
        try {
            Document doc = getDocumentBuilderFactory().newDocumentBuilder().parse(message);
            value = getResponseType(doc.getDocumentElement());
        } catch (Exception e) {
            // ignore and let it fail
        }
        return value;
    }

    // *************************************
    // Config
    // *************************************

    @Configuration
    public class TestConfiguration {
        
        @Bean
        CxfEndpoint serviceEndpoint() {
            CxfSpringEndpoint cxfEndpoint = new CxfSpringEndpoint();
            
            cxfEndpoint.setAddress("http://localhost:" + port + "/services" 
                + "/CxfDispatchMessageTest/SoapContext/GreeterPort");
            cxfEndpoint.setDataFormat(DataFormat.RAW);
            cxfEndpoint.setSynchronous(true);
            return cxfEndpoint;
        }

        @Bean
        public RouteBuilder routeBuilder() {
            return new RouteBuilder() {
                @Override
                public void configure() {
                    from("direct:producer")
                            .to("cxf:bean:serviceEndpoint");
                }
            };
        }
    }

}
