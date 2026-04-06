package ru.practicum.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaProducerWrapper<K, V> implements AutoCloseable {
    private static final Logger log = LoggerFactory.getLogger(KafkaProducerWrapper.class);
    private final KafkaProducer<K, V> producer;

    public KafkaProducerWrapper(KafkaProducer<K, V> producer) {
        this.producer = producer;
    }

    public void send(ProducerRecord<K, V> record) {
        producer.send(record, (metadata, ex) -> {
            if (ex == null) {
                log.info("Отправлено: topic={}, partition={}, offset={}",
                        metadata.topic(),
                        metadata.partition(),
                        metadata.offset());
            } else {
                log.error("Ошибка отправки", ex);
            }
        });
        producer.flush(); // немедленная отправка
    }

    @Override
    public void close() {
        try {
            producer.flush();
            producer.close();
            log.info("KafkaProducer закрыт");
        } catch (Exception e) {
            log.error("Ошибка при закрытии KafkaProducer", e);
        }
    }
}