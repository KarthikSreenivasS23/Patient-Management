package org.pm.patientservice.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BillingServiceGrpcClient {
    private static final Logger log = LoggerFactory.getLogger(BillingServiceGrpcClient.class);
    BillingServiceGrpc.BillingServiceBlockingStub stub;

    public BillingServiceGrpcClient(@Value("${billing.service.address:localhost}") String serviceAddress,@Value("${billing.service.port:9001}") int servicePort) {
        log.info("Creating BillingServiceGrpcClient");
        ManagedChannel channel = ManagedChannelBuilder.forAddress(serviceAddress, servicePort).usePlaintext().build();
        stub = BillingServiceGrpc.newBlockingStub(channel);
    }

    public BillingResponse createBillingAccount(String patientId,String name,String email,String address) {
        BillingRequest billingRequest = BillingRequest.newBuilder()
                .setPatientId(patientId).setName(name).setEmail(email).setAddress(address)
                .build();

        BillingResponse billingResponse = stub.createBillingAccount(billingRequest);

        return billingResponse;
    }
}
