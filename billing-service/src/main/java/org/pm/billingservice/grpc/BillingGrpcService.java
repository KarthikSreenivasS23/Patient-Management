package org.pm.billingservice.grpc;

import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase {
    private static final Logger log = LoggerFactory.getLogger(BillingGrpcService.class);

    @Override
    public void createBillingAccount(billing.BillingRequest billingRequest, StreamObserver<BillingResponse> responseStreamObserver){
        log.info("Create billing account for patient {}",billingRequest.getPatientId());

        //to implement business logic like service,save to db,etc

        BillingResponse billingResponse = BillingResponse.newBuilder().setAccountId("12345").setStatus("ACTIVE").build();
        responseStreamObserver.onNext(billingResponse);
        responseStreamObserver.onCompleted();
    }
}
