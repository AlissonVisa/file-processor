package com.alissonvisa.salesmanapi.infrastructure.configuration.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.broker.region.policy.RedeliveryPolicyMap;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.jms.util.JmsAdapterUtils;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

import javax.jms.JMSException;
import java.util.List;

@Configuration
@EnableJms
public class ReceiverConfig {

    private static final String SALESMAN_QUEUE = "salesman_queue";

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Bean
    public ActiveMQConnectionFactory receiverActiveMQConnectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setBrokerURL(brokerUrl);
        activeMQConnectionFactory.setTrustAllPackages(true);

        List<ActiveMQQueue> activeMQQueues = List.of(
                new ActiveMQQueue(SALESMAN_QUEUE)
        );

        RedeliveryPolicyMap redeliveryPolicyMap = getRedeliveryPolicyMap(activeMQConnectionFactory, activeMQQueues, 5000);
        activeMQConnectionFactory.setRedeliveryPolicyMap(redeliveryPolicyMap);
        return activeMQConnectionFactory;
    }

    private RedeliveryPolicyMap getRedeliveryPolicyMap(ActiveMQConnectionFactory connectionFactory, List<ActiveMQQueue> activeMQQueues, Integer delayMs) {
        RedeliveryPolicyMap redeliveryPolicyMap = connectionFactory.getRedeliveryPolicyMap();
        for (ActiveMQQueue queue: activeMQQueues) {
            RedeliveryPolicy redeliveryPolicy = getRedeliveryPolicy(delayMs, queue);
            redeliveryPolicyMap.put(queue, redeliveryPolicy);
        }
        return redeliveryPolicyMap;
    }

    private RedeliveryPolicy getRedeliveryPolicy(Integer delayMs, ActiveMQQueue queue) {
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        redeliveryPolicy.setInitialRedeliveryDelay(delayMs);
        redeliveryPolicy.setUseCollisionAvoidance(true);
        redeliveryPolicy.setRedeliveryDelay(delayMs);
        redeliveryPolicy.setUseExponentialBackOff(false);
        redeliveryPolicy.setMaximumRedeliveries(3);
        redeliveryPolicy.setDestination(queue);
        return redeliveryPolicy;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() throws JMSException {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setSessionAcknowledgeMode(JmsAdapterUtils.CLIENT_ACKNOWLEDGE);
        factory.setSessionTransacted(false);
        factory.setConnectionFactory(receiverActiveMQConnectionFactory());

        return factory;
    }
}
