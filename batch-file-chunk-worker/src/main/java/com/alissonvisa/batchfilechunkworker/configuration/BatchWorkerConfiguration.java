package com.alissonvisa.batchfilechunkworker.configuration;

import com.alissonvisa.batchfilechunkworker.domain.DataType;
import com.alissonvisa.batchfilechunkworker.messaging.MessageProducer;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.integration.chunk.RemoteChunkingWorkerBuilder;
import org.springframework.batch.integration.config.annotation.EnableBatchIntegration;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.jms.dsl.Jms;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.time.LocalDateTime;
import java.util.UUID;

@Configuration
@EnableBatchProcessing
@EnableBatchIntegration
@EnableIntegration
@Log4j2
public class BatchWorkerConfiguration {

    @Value("${broker.url}")
    private String brokerUrl;

    @Value("${jms.salesman.queue}")
    private String salesmanQueue;

    @Value("${jms.sales.queue}")
    private String salesQueue;

    @Value("${jms.customer.queue}")
    private String customerQueue;

    @Value("${jms.chunk.responseQueue}")
    private String chunkResponseQueue;

    @Autowired
    private RemoteChunkingWorkerBuilder<String, String> remoteChunkingWorkerBuilder;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(this.brokerUrl);
        connectionFactory.setTrustAllPackages(true);
        return connectionFactory;
    }

    /*
     * Configure inbound flow (requests coming from the master)
     */
    @Bean
    public DirectChannel requests() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow inboundFlow(ActiveMQConnectionFactory connectionFactory) {
        return IntegrationFlows
                .from(Jms.messageDrivenChannelAdapter(connectionFactory).destination("requests"))
                .channel(requests())
                .get();
    }

    /*
     * Configure outbound flow (replies going to the master)
     */
    @Bean
    public DirectChannel replies() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow outboundFlow(ActiveMQConnectionFactory connectionFactory) {
        return IntegrationFlows
                .from(replies())
                .handle(Jms.outboundAdapter(connectionFactory).destination("replies"))
                .get();
    }

    /*
     * Configure worker components
     */
    @Bean
    public ItemProcessor<String, String> itemProcessor() {
        return item -> {
            System.out.println("processing item " + item + " timestamp: " + LocalDateTime.now().toString() );
            return item;
        };
    }

    @Bean
    public ItemWriter<String> itemWriter() {
        return items -> {
            for (String item : items) {
                System.out.println("writing item " + item + " timestamp: " + LocalDateTime.now().toString());
                try {
                    final TextMessage textMessage = messageProducer().sendAndReceiveMessage(getBusinessQueueName(item), item);
                    if(!Boolean.valueOf(textMessage.getText())) {
                        log.warn("Item not processed: '{}'", item);
                    }
                } catch (IllegalArgumentException | JMSException e) {
                    log.warn(e.getMessage() + " Ignoring item processing " + item);
                }
            }
        };
    }

    private String getBusinessQueueName(String item) {
        String queueName = null;
        switch (DataType.getBusinessQueueType(item)) {
            case SALESMAN:
                queueName = salesmanQueue;
                break;
            case CUSTOMER:
                queueName = customerQueue;
                break;
            case SALES:
                queueName = salesQueue;
                break;
            default:
                log.warn("No queues found for " + item);
        }
        return queueName;
    }

    @Bean
    public IntegrationFlow workerIntegrationFlow() {
        return this.remoteChunkingWorkerBuilder
                .itemWriter(itemWriter())
                .inputChannel(requests())
                .outputChannel(replies())
                .build();
    }

    @Bean
    public MessageProducer messageProducer() {
        return (queueName, message) -> {
            if(StringUtils.isEmpty(queueName)) {
                throw new IllegalArgumentException("Queue must not be empty");
            }
            log.debug("Sending message " + message + "to queue - " + queueName);
            return (TextMessage) jmsTemplate.sendAndReceive(queueName, session -> {
                TextMessage messageToSend = session.createTextMessage();
                messageToSend.setText(message);
                messageToSend.setJMSCorrelationID(UUID.randomUUID().toString());
                messageToSend.setJMSReplyTo(new ActiveMQQueue(chunkResponseQueue));
                return messageToSend;
            });
        };
    }

}
