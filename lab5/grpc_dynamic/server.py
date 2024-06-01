import grpc
from concurrent import futures
import time

# Import reflection service
from grpc_reflection.v1alpha import reflection

# Import the generated code from the modified protofile.proto
import protofile_pb2
import protofile_pb2_grpc

class ProtoFileServicer(protofile_pb2_grpc.ProtoFileServicer):
    def Add(self, request, context):
        return protofile_pb2.AddResponse(result=request.num1 + request.num2)

    def Concatenate(self, request, context):
        return protofile_pb2.ConcatenateResponse(result=request.str1 + request.str2)

    def CombineSets(self, request, context):
        combined_values = set()
        for set_ in request.sets:
            combined_values.update(set_.values)
        return protofile_pb2.CombineSetsResponse(combined_set=protofile_pb2.Set(values=list(combined_values)))

def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))

    protofile_pb2_grpc.add_ProtoFileServicer_to_server(ProtoFileServicer(), server)

    SERVICE_NAMES = (
        protofile_pb2.DESCRIPTOR.services_by_name['ProtoFile'].full_name,
        reflection.SERVICE_NAME,
    )
    print(SERVICE_NAMES)
    reflection.enable_server_reflection(SERVICE_NAMES, server)

    server.add_insecure_port('[::]:50051')
    server.start()
    print("Server started. Listening on port 50051.")
    try:
        while True:
            time.sleep(86400)  # One day in seconds
    except KeyboardInterrupt:
        server.stop(0)

if __name__ == '__main__':
    serve()
