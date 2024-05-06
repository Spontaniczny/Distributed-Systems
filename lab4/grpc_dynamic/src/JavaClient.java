import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import protofile.*;
import protofile.ProtoFileGrpc;

import java.util.ArrayList;
import java.util.List;

public class JavaClient {
    private final ManagedChannel channel;
    private final ProtoFileGrpc.ProtoFileBlockingStub blockingStub;

    public JavaClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext().build());
    }

    private JavaClient(ManagedChannel channel) {
        this.channel = channel;
        blockingStub = ProtoFileGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, java.util.concurrent.TimeUnit.SECONDS);
    }

    public void run() {
        // Call the Add RPC method
        Protofile.AddResponse addResponse = blockingStub.add(Protofile.AddRequest.newBuilder().setNum1(10).setNum2(5).build());
        System.out.println("Addition Result: " + addResponse.getResult());

        // Call the Concatenate RPC method
        Protofile.ConcatenateResponse concatenateResponse = blockingStub.concatenate(Protofile.ConcatenateRequest.newBuilder().setStr1("Hello").setStr2("World").build());
        System.out.println("Concatenation Result: " + concatenateResponse.getResult());

        // Create sets
        List<Protofile.Set> sets = new ArrayList<>();
        sets.add(Protofile.Set.newBuilder().addValues(1).addValues(2).addValues(3).build());
        sets.add(Protofile.Set.newBuilder().addValues(3).addValues(3).addValues(4).build());
        sets.add(Protofile.Set.newBuilder().addValues(4).addValues(5).addValues(6).build());

        // Call the CombineSets RPC method
        Protofile.CombineSetsResponse combineSetsResponse = blockingStub.combineSets(Protofile.CombineSetsRequest.newBuilder().addAllSets(sets).build());
        Protofile.Set combinedSet = combineSetsResponse.getCombinedSet();
        StringBuilder sb = new StringBuilder("[");
        for (int value : combinedSet.getValuesList()) {
            sb.append(value).append(", ");
        }
        sb.setLength(sb.length() - 2);
        sb.append("]");
        System.out.println("Combined Set: " + sb.toString());
    }

    public static void main(String[] args) throws InterruptedException {
        JavaClient client = new JavaClient("localhost", 50051);
        try {
            client.run();
        } finally {
            client.shutdown();
        }
    }
}