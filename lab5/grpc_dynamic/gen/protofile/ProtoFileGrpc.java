package protofile;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.48.2)",
    comments = "Source: protofile.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ProtoFileGrpc {

  private ProtoFileGrpc() {}

  public static final String SERVICE_NAME = "protofile.ProtoFile";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<protofile.Protofile.AddRequest,
      protofile.Protofile.AddResponse> getAddMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Add",
      requestType = protofile.Protofile.AddRequest.class,
      responseType = protofile.Protofile.AddResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<protofile.Protofile.AddRequest,
      protofile.Protofile.AddResponse> getAddMethod() {
    io.grpc.MethodDescriptor<protofile.Protofile.AddRequest, protofile.Protofile.AddResponse> getAddMethod;
    if ((getAddMethod = ProtoFileGrpc.getAddMethod) == null) {
      synchronized (ProtoFileGrpc.class) {
        if ((getAddMethod = ProtoFileGrpc.getAddMethod) == null) {
          ProtoFileGrpc.getAddMethod = getAddMethod =
              io.grpc.MethodDescriptor.<protofile.Protofile.AddRequest, protofile.Protofile.AddResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Add"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  protofile.Protofile.AddRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  protofile.Protofile.AddResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProtoFileMethodDescriptorSupplier("Add"))
              .build();
        }
      }
    }
    return getAddMethod;
  }

  private static volatile io.grpc.MethodDescriptor<protofile.Protofile.ConcatenateRequest,
      protofile.Protofile.ConcatenateResponse> getConcatenateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Concatenate",
      requestType = protofile.Protofile.ConcatenateRequest.class,
      responseType = protofile.Protofile.ConcatenateResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<protofile.Protofile.ConcatenateRequest,
      protofile.Protofile.ConcatenateResponse> getConcatenateMethod() {
    io.grpc.MethodDescriptor<protofile.Protofile.ConcatenateRequest, protofile.Protofile.ConcatenateResponse> getConcatenateMethod;
    if ((getConcatenateMethod = ProtoFileGrpc.getConcatenateMethod) == null) {
      synchronized (ProtoFileGrpc.class) {
        if ((getConcatenateMethod = ProtoFileGrpc.getConcatenateMethod) == null) {
          ProtoFileGrpc.getConcatenateMethod = getConcatenateMethod =
              io.grpc.MethodDescriptor.<protofile.Protofile.ConcatenateRequest, protofile.Protofile.ConcatenateResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Concatenate"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  protofile.Protofile.ConcatenateRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  protofile.Protofile.ConcatenateResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProtoFileMethodDescriptorSupplier("Concatenate"))
              .build();
        }
      }
    }
    return getConcatenateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<protofile.Protofile.CombineSetsRequest,
      protofile.Protofile.CombineSetsResponse> getCombineSetsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CombineSets",
      requestType = protofile.Protofile.CombineSetsRequest.class,
      responseType = protofile.Protofile.CombineSetsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<protofile.Protofile.CombineSetsRequest,
      protofile.Protofile.CombineSetsResponse> getCombineSetsMethod() {
    io.grpc.MethodDescriptor<protofile.Protofile.CombineSetsRequest, protofile.Protofile.CombineSetsResponse> getCombineSetsMethod;
    if ((getCombineSetsMethod = ProtoFileGrpc.getCombineSetsMethod) == null) {
      synchronized (ProtoFileGrpc.class) {
        if ((getCombineSetsMethod = ProtoFileGrpc.getCombineSetsMethod) == null) {
          ProtoFileGrpc.getCombineSetsMethod = getCombineSetsMethod =
              io.grpc.MethodDescriptor.<protofile.Protofile.CombineSetsRequest, protofile.Protofile.CombineSetsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CombineSets"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  protofile.Protofile.CombineSetsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  protofile.Protofile.CombineSetsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProtoFileMethodDescriptorSupplier("CombineSets"))
              .build();
        }
      }
    }
    return getCombineSetsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ProtoFileStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ProtoFileStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ProtoFileStub>() {
        @java.lang.Override
        public ProtoFileStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ProtoFileStub(channel, callOptions);
        }
      };
    return ProtoFileStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ProtoFileBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ProtoFileBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ProtoFileBlockingStub>() {
        @java.lang.Override
        public ProtoFileBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ProtoFileBlockingStub(channel, callOptions);
        }
      };
    return ProtoFileBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ProtoFileFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ProtoFileFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ProtoFileFutureStub>() {
        @java.lang.Override
        public ProtoFileFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ProtoFileFutureStub(channel, callOptions);
        }
      };
    return ProtoFileFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ProtoFileImplBase implements io.grpc.BindableService {

    /**
     */
    public void add(protofile.Protofile.AddRequest request,
        io.grpc.stub.StreamObserver<protofile.Protofile.AddResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAddMethod(), responseObserver);
    }

    /**
     */
    public void concatenate(protofile.Protofile.ConcatenateRequest request,
        io.grpc.stub.StreamObserver<protofile.Protofile.ConcatenateResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getConcatenateMethod(), responseObserver);
    }

    /**
     */
    public void combineSets(protofile.Protofile.CombineSetsRequest request,
        io.grpc.stub.StreamObserver<protofile.Protofile.CombineSetsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCombineSetsMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getAddMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                protofile.Protofile.AddRequest,
                protofile.Protofile.AddResponse>(
                  this, METHODID_ADD)))
          .addMethod(
            getConcatenateMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                protofile.Protofile.ConcatenateRequest,
                protofile.Protofile.ConcatenateResponse>(
                  this, METHODID_CONCATENATE)))
          .addMethod(
            getCombineSetsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                protofile.Protofile.CombineSetsRequest,
                protofile.Protofile.CombineSetsResponse>(
                  this, METHODID_COMBINE_SETS)))
          .build();
    }
  }

  /**
   */
  public static final class ProtoFileStub extends io.grpc.stub.AbstractAsyncStub<ProtoFileStub> {
    private ProtoFileStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProtoFileStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ProtoFileStub(channel, callOptions);
    }

    /**
     */
    public void add(protofile.Protofile.AddRequest request,
        io.grpc.stub.StreamObserver<protofile.Protofile.AddResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAddMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void concatenate(protofile.Protofile.ConcatenateRequest request,
        io.grpc.stub.StreamObserver<protofile.Protofile.ConcatenateResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getConcatenateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void combineSets(protofile.Protofile.CombineSetsRequest request,
        io.grpc.stub.StreamObserver<protofile.Protofile.CombineSetsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCombineSetsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ProtoFileBlockingStub extends io.grpc.stub.AbstractBlockingStub<ProtoFileBlockingStub> {
    private ProtoFileBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProtoFileBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ProtoFileBlockingStub(channel, callOptions);
    }

    /**
     */
    public protofile.Protofile.AddResponse add(protofile.Protofile.AddRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAddMethod(), getCallOptions(), request);
    }

    /**
     */
    public protofile.Protofile.ConcatenateResponse concatenate(protofile.Protofile.ConcatenateRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getConcatenateMethod(), getCallOptions(), request);
    }

    /**
     */
    public protofile.Protofile.CombineSetsResponse combineSets(protofile.Protofile.CombineSetsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCombineSetsMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ProtoFileFutureStub extends io.grpc.stub.AbstractFutureStub<ProtoFileFutureStub> {
    private ProtoFileFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProtoFileFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ProtoFileFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<protofile.Protofile.AddResponse> add(
        protofile.Protofile.AddRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAddMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<protofile.Protofile.ConcatenateResponse> concatenate(
        protofile.Protofile.ConcatenateRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getConcatenateMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<protofile.Protofile.CombineSetsResponse> combineSets(
        protofile.Protofile.CombineSetsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCombineSetsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_ADD = 0;
  private static final int METHODID_CONCATENATE = 1;
  private static final int METHODID_COMBINE_SETS = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ProtoFileImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ProtoFileImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ADD:
          serviceImpl.add((protofile.Protofile.AddRequest) request,
              (io.grpc.stub.StreamObserver<protofile.Protofile.AddResponse>) responseObserver);
          break;
        case METHODID_CONCATENATE:
          serviceImpl.concatenate((protofile.Protofile.ConcatenateRequest) request,
              (io.grpc.stub.StreamObserver<protofile.Protofile.ConcatenateResponse>) responseObserver);
          break;
        case METHODID_COMBINE_SETS:
          serviceImpl.combineSets((protofile.Protofile.CombineSetsRequest) request,
              (io.grpc.stub.StreamObserver<protofile.Protofile.CombineSetsResponse>) responseObserver);
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

  private static abstract class ProtoFileBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ProtoFileBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return protofile.Protofile.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ProtoFile");
    }
  }

  private static final class ProtoFileFileDescriptorSupplier
      extends ProtoFileBaseDescriptorSupplier {
    ProtoFileFileDescriptorSupplier() {}
  }

  private static final class ProtoFileMethodDescriptorSupplier
      extends ProtoFileBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ProtoFileMethodDescriptorSupplier(String methodName) {
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
      synchronized (ProtoFileGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ProtoFileFileDescriptorSupplier())
              .addMethod(getAddMethod())
              .addMethod(getConcatenateMethod())
              .addMethod(getCombineSetsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
