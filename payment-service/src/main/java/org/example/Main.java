package org.example;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import org.example.proto.GreetingGrpc;
import org.example.proto.HttpRequest;
import org.example.proto.HttpResponse;

import java.io.IOException;

public class Main {
    static public void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(8080)
                .addService(new GreetingServiceImpl()).build();

        System.out.println("Starting server...");
        server.start();
        System.out.println("Server started!");
        server.awaitTermination();
    }

    public static class GreetingServiceImpl extends GreetingGrpc.GreetingImplBase {
        @Override
        public void sayHello(HttpRequest request, StreamObserver<HttpResponse> responseObserver) {
            System.out.println(request);

            String greeting = "Hello there, " + request.getName();

            HttpResponse response = HttpResponse.newBuilder().setMessage(greeting).build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}