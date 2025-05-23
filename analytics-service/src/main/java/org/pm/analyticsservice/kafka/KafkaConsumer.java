package org.pm.analyticsservice.kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patient.event.PatientEvent;

@Service
public class KafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "patient" , groupId = "analyticservice")
    public void consumePatientEvent(byte[] event) {
        try {
            PatientEvent patientEvent = PatientEvent.parseFrom(event);

            log.info("Received Patient Event id:{},name:{},email:{},eventType:{}", patientEvent.getPatientId()
            ,patientEvent.getName(),patientEvent.getEmail(),patientEvent.getEventType());
        } catch (InvalidProtocolBufferException e) {
            log.error("Error deserializing patient event {}", e.getMessage());
        }
    }
}
