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
package org.apache.camel.component.mail.springboot;



import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mail.DefaultJavaMailSender;
import org.apache.camel.component.mail.JavaMailSender;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.spring.boot.CamelAutoConfiguration;
import org.apache.camel.support.processor.idempotent.MemoryIdempotentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.jvnet.mock_javamail.Mailbox;


@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@CamelSpringBootTest
@SpringBootTest(
    classes = {
        CamelAutoConfiguration.class,
        MailIdempotentRepositoryDuplicateTest.class,
        MailIdempotentRepositoryDuplicateTest.TestConfiguration.class
    }
)
public class MailIdempotentRepositoryDuplicateTest {

    
    @Autowired
    ProducerTemplate template;
    
    @Autowired
    CamelContext context;
    
    @EndpointInject("mock:result")
    MockEndpoint mock;
    
    static MemoryIdempotentRepository myRepo;
    @Bean("myRepo")
    private MemoryIdempotentRepository getMemoryIdempotentRepository() {
        myRepo = new MemoryIdempotentRepository();
        myRepo.add("myuid-3");
        return myRepo;
    }

    
    @BeforeEach
    public void setUp() throws Exception {
              

        prepareMailbox();
        
    }

    @Test
    public void testIdempotent() throws Exception {
        assertEquals(1, myRepo.getCacheSize());

        
        // no 3 is already in the idempotent repo
        mock.expectedBodiesReceived("Message 0", "Message 1", "Message 2", "Message 4");

        context.getRouteController().startRoute("foo");

        mock.assertIsSatisfied();

        // windows need a little slack
        Thread.sleep(500);

        assertEquals(0, Mailbox.get("jones@localhost").getNewMessageCount());

        // they are removed on confirm
        assertEquals(1, myRepo.getCacheSize());
    }

    private void prepareMailbox() throws Exception {
        // connect to mailbox
        Mailbox.clearAll();
        JavaMailSender sender = new DefaultJavaMailSender();
        Store store = sender.getSession().getStore("pop3");
        store.connect("localhost", 25, "jones", "secret");
        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_WRITE);
        folder.expunge();

        // inserts 5 new messages
        Message[] messages = new Message[5];
        for (int i = 0; i < 5; i++) {
            messages[i] = new MimeMessage(sender.getSession());
            messages[i].setText("Message " + i);
            messages[i].setHeader("Message-ID", "myuid-" + i);
        }
        folder.appendMessages(messages);
        folder.close(true);
    }
    
    // *************************************
    // Config
    // *************************************

    @Configuration
    public class TestConfiguration {

        @Bean
        public RouteBuilder routeBuilder() {
            return new RouteBuilder() {
                @Override
                public void configure() {
                    from("imap://jones@localhost?password=secret&idempotentRepository=#myRepo&initialDelay=100&delay=100")
                            .routeId("foo").noAutoStartup()
                            .to("mock:result");
                }
            };
        }
    }
    
   

}
