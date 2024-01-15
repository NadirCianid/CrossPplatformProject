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
public final class FormulaGrpc {

  private FormulaGrpc() {}

  public static final String SERVICE_NAME = "com.test.grpc.Formula";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.test.grpc.CalculatorService.GetFormulaRequest,
      com.test.grpc.CalculatorService.GetFormulaResponse> getGetFormulaMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getFormula",
      requestType = com.test.grpc.CalculatorService.GetFormulaRequest.class,
      responseType = com.test.grpc.CalculatorService.GetFormulaResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.test.grpc.CalculatorService.GetFormulaRequest,
      com.test.grpc.CalculatorService.GetFormulaResponse> getGetFormulaMethod() {
    io.grpc.MethodDescriptor<com.test.grpc.CalculatorService.GetFormulaRequest, com.test.grpc.CalculatorService.GetFormulaResponse> getGetFormulaMethod;
    if ((getGetFormulaMethod = FormulaGrpc.getGetFormulaMethod) == null) {
      synchronized (FormulaGrpc.class) {
        if ((getGetFormulaMethod = FormulaGrpc.getGetFormulaMethod) == null) {
          FormulaGrpc.getGetFormulaMethod = getGetFormulaMethod =
              io.grpc.MethodDescriptor.<com.test.grpc.CalculatorService.GetFormulaRequest, com.test.grpc.CalculatorService.GetFormulaResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getFormula"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.test.grpc.CalculatorService.GetFormulaRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.test.grpc.CalculatorService.GetFormulaResponse.getDefaultInstance()))
              .setSchemaDescriptor(new FormulaMethodDescriptorSupplier("getFormula"))
              .build();
        }
      }
    }
    return getGetFormulaMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static FormulaStub newStub(io.grpc.Channel channel) {
    return new FormulaStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static FormulaBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new FormulaBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static FormulaFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new FormulaFutureStub(channel);
  }

  /**
   */
  public static abstract class FormulaImplBase implements io.grpc.BindableService {

    /**
     */
    public void getFormula(com.test.grpc.CalculatorService.GetFormulaRequest request,
        io.grpc.stub.StreamObserver<com.test.grpc.CalculatorService.GetFormulaResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetFormulaMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetFormulaMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.test.grpc.CalculatorService.GetFormulaRequest,
                com.test.grpc.CalculatorService.GetFormulaResponse>(
                  this, METHODID_GET_FORMULA)))
          .build();
    }
  }

  /**
   */
  public static final class FormulaStub extends io.grpc.stub.AbstractStub<FormulaStub> {
    private FormulaStub(io.grpc.Channel channel) {
      super(channel);
    }

    private FormulaStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FormulaStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new FormulaStub(channel, callOptions);
    }

    /**
     */
    public void getFormula(com.test.grpc.CalculatorService.GetFormulaRequest request,
        io.grpc.stub.StreamObserver<com.test.grpc.CalculatorService.GetFormulaResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetFormulaMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class FormulaBlockingStub extends io.grpc.stub.AbstractStub<FormulaBlockingStub> {
    private FormulaBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private FormulaBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FormulaBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new FormulaBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.test.grpc.CalculatorService.GetFormulaResponse getFormula(com.test.grpc.CalculatorService.GetFormulaRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetFormulaMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class FormulaFutureStub extends io.grpc.stub.AbstractStub<FormulaFutureStub> {
    private FormulaFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private FormulaFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FormulaFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new FormulaFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.test.grpc.CalculatorService.GetFormulaResponse> getFormula(
        com.test.grpc.CalculatorService.GetFormulaRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetFormulaMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_FORMULA = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final FormulaImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(FormulaImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_FORMULA:
          serviceImpl.getFormula((com.test.grpc.CalculatorService.GetFormulaRequest) request,
              (io.grpc.stub.StreamObserver<com.test.grpc.CalculatorService.GetFormulaResponse>) responseObserver);
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

  private static abstract class FormulaBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    FormulaBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.test.grpc.CalculatorService.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Formula");
    }
  }

  private static final class FormulaFileDescriptorSupplier
      extends FormulaBaseDescriptorSupplier {
    FormulaFileDescriptorSupplier() {}
  }

  private static final class FormulaMethodDescriptorSupplier
      extends FormulaBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    FormulaMethodDescriptorSupplier(String methodName) {
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
      synchronized (FormulaGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new FormulaFileDescriptorSupplier())
              .addMethod(getGetFormulaMethod())
              .build();
        }
      }
    }
    return result;
  }
}
