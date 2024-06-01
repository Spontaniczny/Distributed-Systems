import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.Descriptors;
import com.google.protobuf.DynamicMessage;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.MethodDescriptor;
import io.grpc.protobuf.ProtoUtils;
import io.grpc.stub.ClientCalls;

import java.util.concurrent.TimeUnit;

public class DynamicJavaClient {
    private final ManagedChannel channel;

    public DynamicJavaClient(String host, int port) {
        this.channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void run() throws Exception {
        // Load file descriptors dynamically
        Descriptors.FileDescriptor fileDescriptor = getFileDescriptor();

//        for (Descriptors.ServiceDescriptor serviceDescriptor : fileDescriptor.getServices()) {
//            System.out.println("Service: " + serviceDescriptor.getFullName());
//            for (Descriptors.MethodDescriptor methodDescriptor : serviceDescriptor.getMethods()) {
//                System.out.println("  Method: " + methodDescriptor.getName());
//            }
//        }

        // Call methods
        callAddMethod(fileDescriptor);
        callConcatenateMethod(fileDescriptor);
        callCombineSetsMethod(fileDescriptor);
    }

    private void callAddMethod(Descriptors.FileDescriptor fileDescriptor) throws Exception {
        Descriptors.MethodDescriptor methodDescriptor = fileDescriptor.findServiceByName("ProtoFile").findMethodByName("Add");

        DynamicMessage request = DynamicMessage.newBuilder(methodDescriptor.getInputType())
                .setField(methodDescriptor.getInputType().findFieldByName("num1"), 10)
                .setField(methodDescriptor.getInputType().findFieldByName("num2"), 5)
                .build();

        DynamicMessage response = callUnaryMethod(methodDescriptor, request);
        System.out.println("Add Response: " + response);
    }

    private void callConcatenateMethod(Descriptors.FileDescriptor fileDescriptor) throws Exception {
        Descriptors.MethodDescriptor methodDescriptor = fileDescriptor.findServiceByName("ProtoFile").findMethodByName("Concatenate");

        DynamicMessage request = DynamicMessage.newBuilder(methodDescriptor.getInputType())
                .setField(methodDescriptor.getInputType().findFieldByName("str1"), "Hello")
                .setField(methodDescriptor.getInputType().findFieldByName("str2"), "World")
                .build();

        DynamicMessage response = callUnaryMethod(methodDescriptor, request);
        System.out.println("Concatenate Response: " + response);
    }

    private void callCombineSetsMethod(Descriptors.FileDescriptor fileDescriptor) throws Exception {
        Descriptors.MethodDescriptor methodDescriptor = fileDescriptor.findServiceByName("ProtoFile").findMethodByName("CombineSets");

        Descriptors.Descriptor setDescriptor = methodDescriptor.getInputType().findFieldByName("sets").getMessageType();

        DynamicMessage set1 = DynamicMessage.newBuilder(setDescriptor)
                .addRepeatedField(setDescriptor.findFieldByName("values"), 1)
                .addRepeatedField(setDescriptor.findFieldByName("values"), 2)
                .addRepeatedField(setDescriptor.findFieldByName("values"), 3)
                .build();

        DynamicMessage set2 = DynamicMessage.newBuilder(setDescriptor)
                .addRepeatedField(setDescriptor.findFieldByName("values"), 3)
                .addRepeatedField(setDescriptor.findFieldByName("values"), 4)
                .build();

        DynamicMessage set3 = DynamicMessage.newBuilder(setDescriptor)
                .addRepeatedField(setDescriptor.findFieldByName("values"), 4)
                .addRepeatedField(setDescriptor.findFieldByName("values"), 5)
                .build();

        DynamicMessage request = DynamicMessage.newBuilder(methodDescriptor.getInputType())
                .addRepeatedField(methodDescriptor.getInputType().findFieldByName("sets"), set1)
                .addRepeatedField(methodDescriptor.getInputType().findFieldByName("sets"), set2)
                .addRepeatedField(methodDescriptor.getInputType().findFieldByName("sets"), set3)
                .build();

        DynamicMessage response = callUnaryMethod(methodDescriptor, request);
        System.out.println("CombineSets Response: " + response);
    }

    private DynamicMessage callUnaryMethod(Descriptors.MethodDescriptor methodDescriptor, DynamicMessage request) throws Exception {
        MethodDescriptor<DynamicMessage, DynamicMessage> grpcMethodDescriptor =
                MethodDescriptor.<DynamicMessage, DynamicMessage>newBuilder()
                        .setType(MethodDescriptor.MethodType.UNARY)
                        .setFullMethodName(MethodDescriptor.generateFullMethodName(methodDescriptor.getService().getFullName(), methodDescriptor.getName()))
                        .setRequestMarshaller(ProtoUtils.marshaller(request))
                        .setResponseMarshaller(ProtoUtils.marshaller(DynamicMessage.getDefaultInstance(methodDescriptor.getOutputType())))
                        .build();

        return ClientCalls.blockingUnaryCall(channel, grpcMethodDescriptor, io.grpc.CallOptions.DEFAULT, request);
    }

    private Descriptors.FileDescriptor getFileDescriptor() throws Exception {
        DescriptorProtos.FileDescriptorProto fileDescriptorProto = DescriptorProtos.FileDescriptorProto.newBuilder()
                .setName("protofile.proto")
                .setPackage("protofile")
                .addService(DescriptorProtos.ServiceDescriptorProto.newBuilder()
                        .setName("ProtoFile")
                        .addMethod(DescriptorProtos.MethodDescriptorProto.newBuilder()
                                .setName("Add")
                                .setInputType("protofile.AddRequest")
                                .setOutputType("protofile.AddResponse")
                                .build())
                        .addMethod(DescriptorProtos.MethodDescriptorProto.newBuilder()
                                .setName("Concatenate")
                                .setInputType("protofile.ConcatenateRequest")
                                .setOutputType("protofile.ConcatenateResponse")
                                .build())
                        .addMethod(DescriptorProtos.MethodDescriptorProto.newBuilder()
                                .setName("CombineSets")
                                .setInputType("protofile.CombineSetsRequest")
                                .setOutputType("protofile.CombineSetsResponse")
                                .build())
                        .build())
                .addMessageType(DescriptorProtos.DescriptorProto.newBuilder()
                        .setName("CombineSetsRequest")
                        .addField(DescriptorProtos.FieldDescriptorProto.newBuilder()
                                .setName("sets")
                                .setNumber(1)
                                .setLabel(DescriptorProtos.FieldDescriptorProto.Label.LABEL_REPEATED)
                                .setType(DescriptorProtos.FieldDescriptorProto.Type.TYPE_MESSAGE)
                                .setTypeName("protofile.Set")
                                .build())
                        .build())
                .addMessageType(DescriptorProtos.DescriptorProto.newBuilder()
                        .setName("CombineSetsResponse")
                        .addField(DescriptorProtos.FieldDescriptorProto.newBuilder()
                                .setName("combined_set")
                                .setNumber(1)
                                .setType(DescriptorProtos.FieldDescriptorProto.Type.TYPE_MESSAGE)
                                .setTypeName("protofile.Set")
                                .build())
                        .build())
                .addMessageType(DescriptorProtos.DescriptorProto.newBuilder()
                        .setName("Set")
                        .addField(DescriptorProtos.FieldDescriptorProto.newBuilder()
                                .setName("values")
                                .setNumber(1)
                                .setLabel(DescriptorProtos.FieldDescriptorProto.Label.LABEL_REPEATED)
                                .setType(DescriptorProtos.FieldDescriptorProto.Type.TYPE_INT32)
                                .build())
                        .build())
                .addMessageType(DescriptorProtos.DescriptorProto.newBuilder()
                        .setName("AddRequest")
                        .addField(DescriptorProtos.FieldDescriptorProto.newBuilder()
                                .setName("num1")
                                .setNumber(1)
                                .setType(DescriptorProtos.FieldDescriptorProto.Type.TYPE_INT32)
                                .build())
                        .addField(DescriptorProtos.FieldDescriptorProto.newBuilder()
                                .setName("num2")
                                .setNumber(2)
                                .setType(DescriptorProtos.FieldDescriptorProto.Type.TYPE_INT32)
                                .build())
                        .build())
                .addMessageType(DescriptorProtos.DescriptorProto.newBuilder()
                        .setName("AddResponse")
                        .addField(DescriptorProtos.FieldDescriptorProto.newBuilder()
                                .setName("result")
                                .setNumber(1)
                                .setType(DescriptorProtos.FieldDescriptorProto.Type.TYPE_INT32)
                                .build())
                        .build())
                .addMessageType(DescriptorProtos.DescriptorProto.newBuilder()
                        .setName("ConcatenateRequest")
                        .addField(DescriptorProtos.FieldDescriptorProto.newBuilder()
                                .setName("str1")
                                .setNumber(1)
                                .setType(DescriptorProtos.FieldDescriptorProto.Type.TYPE_STRING)
                                .build())
                        .addField(DescriptorProtos.FieldDescriptorProto.newBuilder()
                                .setName("str2")
                                .setNumber(2)
                                .setType(DescriptorProtos.FieldDescriptorProto.Type.TYPE_STRING)
                                .build())
                        .build())
                .addMessageType(DescriptorProtos.DescriptorProto.newBuilder()
                        .setName("ConcatenateResponse")
                        .addField(DescriptorProtos.FieldDescriptorProto.newBuilder()
                                .setName("result")
                                .setNumber(1)
                                .setType(DescriptorProtos.FieldDescriptorProto.Type.TYPE_STRING)
                                .build())
                        .build())
                .build();

        return Descriptors.FileDescriptor.buildFrom(fileDescriptorProto, new Descriptors.FileDescriptor[]{});
    }

    public static void main(String[] args) throws Exception {
        DynamicJavaClient client = new DynamicJavaClient("localhost", 50051);
        try {
            client.run();
        } finally {
            client.shutdown();
        }
    }
}
