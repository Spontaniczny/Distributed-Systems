import grpc
import protofile_pb2
import protofile_pb2_grpc

def run():
    channel = grpc.insecure_channel('localhost:50051')
    stub = protofile_pb2_grpc.ProtoFileStub(channel)

    # Call the Add RPC method
    add_response = stub.Add(protofile_pb2.AddRequest(num1=10, num2=5))
    print("Addition Result:", add_response.result)

    # Call the Concatenate RPC method
    concatenate_response = stub.Concatenate(protofile_pb2.ConcatenateRequest(str1="Hello", str2="World"))
    print("Concatenation Result:", concatenate_response.result)

    # Create sets
    sets = [
        protofile_pb2.Set(values=[1, 2, 3]),
        protofile_pb2.Set(values=[3, 3, 4]),
        protofile_pb2.Set(values=[4, 5, 6]),
    ]

    # Call the CombineSets RPC method
    combine_sets_response = stub.CombineSets(protofile_pb2.CombineSetsRequest(sets=sets))
    print("Combined Set:", combine_sets_response.combined_set.values)

if __name__ == '__main__':
    run()
