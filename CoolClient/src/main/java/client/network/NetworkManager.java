package client.network;

import com.test.grpc.CalculatorGrpc;
import com.test.grpc.FormulaGrpc;
import com.test.grpc.OperationsGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.experimental.UtilityClass;

@UtilityClass
public class NetworkManager {

    private static final String TARGET = "localhost:8082";

    private final ManagedChannel channel = ManagedChannelBuilder.forTarget(TARGET).usePlaintext().build();

    public static CalculatorGrpc.CalculatorBlockingStub buildCalculatorStub() {
        return CalculatorGrpc.newBlockingStub(channel);
    }

    public static OperationsGrpc.OperationsBlockingStub buildOperationsStub() {
        return OperationsGrpc.newBlockingStub(channel);
    }

    public static FormulaGrpc.FormulaBlockingStub buildFormulaStub() {
        return FormulaGrpc.newBlockingStub(channel);
    }

    public static void shutdownChannel() {
        channel.shutdownNow();
    }
}
