package com.test.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.24.0)",
    comments = "Source: CalculatorService.proto")
public final class OperationsGrpc {

  private OperationsGrpc() {}

  public static final String SERVICE_NAME = "com.test.grpc.Operations";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.test.grpc.CalculatorService.GetOperationsRequest,
      com.test.grpc.CalculatorService.GetOperationsResponse> getGetOperationNamesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getOperationNames",
      requestType = com.test.grpc.CalculatorService.GetOperationsRequest.class,
      responseType = com.test.grpc.CalculatorService.GetOperationsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.test.grpc.CalculatorService.GetOperationsRequest,
      com.test.grpc.CalculatorService.GetOperationsResponse> getGetOperationNamesMethod() {
    io.grpc.MethodDescriptor<com.test.grpc.CalculatorService.GetOperationsRequest, com.test.grpc.CalculatorService.GetOperationsResponse> getGetOperationNamesMethod;
    if ((getGetOperationNamesMethod = OperationsGrpc.getGetOperationNamesMethod) == null) {
      synchronized (OperationsGrpc.class) {
        if ((getGetOperationNamesMethod = OperationsGrpc.getGetOperationNamesMethod) == null) {
          OperationsGrpc.getGetOperationNamesMethod = getGetOperationNamesMethod =
              io.grpc.MethodDescriptor.<com.test.grpc.CalculatorService.GetOperationsRequest, com.test.grpc.CalculatorService.GetOperationsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getOperationNames"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.test.grpc.CalculatorService.GetOperationsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.test.grpc.CalculatorService.GetOperationsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new OperationsMethodDescriptorSupplier("getOperationNames"))
              .build();
        }
      }
    }
    return getGetOperationNamesMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static OperationsStub newStub(io.grpc.Channel channel) {
    return new OperationsStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static OperationsBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new OperationsBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static OperationsFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new OperationsFutureStub(channel);
  }

  /**
   */
  public static abstract class OperationsImplBase implements io.grpc.BindableService {

    /**
     */
    public void getOperationNames(com.test.grpc.CalculatorService.GetOperationsRequest request,
        io.grpc.stub.StreamObserver<com.test.grpc.CalculatorService.GetOperationsResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetOperationNamesMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetOperationNamesMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.test.grpc.CalculatorService.GetOperationsRequest,
                com.test.grpc.CalculatorService.GetOperationsResponse>(
                  this, METHODID_GET_OPERATION_NAMES)))
          .build();
    }
  }

  /**
   */
  public static final class OperationsStub extends io.grpc.stub.AbstractStub<OperationsStub> {
    private OperationsStub(io.grpc.Channel channel) {
      super(channel);
    }

    private OperationsStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OperationsStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new OperationsStub(channel, callOptions);
    }

    /**
     */
    public void getOperationNames(com.test.grpc.CalculatorService.GetOperationsRequest request,
        io.grpc.stub.StreamObserver<com.test.grpc.CalculatorService.GetOperationsResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetOperationNamesMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class OperationsBlockingStub extends io.grpc.stub.AbstractStub<OperationsBlockingStub> {
    private OperationsBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private OperationsBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OperationsBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new OperationsBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.test.grpc.CalculatorService.GetOperationsResponse getOperationNames(com.test.grpc.CalculatorService.GetOperationsRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetOperationNamesMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class OperationsFutureStub extends io.grpc.stub.AbstractStub<OperationsFutureStub> {
    private OperationsFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private OperationsFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OperationsFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new OperationsFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.test.grpc.CalculatorService.GetOperationsResponse> getOperationNames(
        com.test.grpc.CalculatorService.GetOperationsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetOperationNamesMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_OPERATION_NAMES = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final OperationsImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(OperationsImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_OPERATION_NAMES:
          serviceImpl.getOperationNames((com.test.grpc.CalculatorService.GetOperationsRequest) request,
              (io.grpc.stub.StreamObserver<com.test.grpc.CalculatorService.GetOperationsResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class OperationsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    OperationsBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.test.grpc.CalculatorService.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Operations");
    }
  }

  private static final class OperationsFileDescriptorSupplier
      extends OperationsBaseDescriptorSupplier {
    OperationsFileDescriptorSupplier() {}
  }

  private static final class OperationsMethodDescriptorSupplier
      extends OperationsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    OperationsMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (OperationsGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new OperationsFileDescriptorSupplier())
              .addMethod(getGetOperationNamesMethod())
              .build();
        }
      }
    }
    return result;
  }
}
