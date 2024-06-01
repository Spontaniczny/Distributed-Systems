import grpc
from google.protobuf import descriptor_pb2, symbol_database
from grpc_reflection.v1alpha import reflection_pb2, reflection_pb2_grpc, reflection


def get_service_descriptors(channel):
    stub = reflection_pb2_grpc.ServerReflectionStub(channel)
    request = reflection_pb2.ServerReflectionRequest(
        list_services=""
    )
    response_iterator = stub.ServerReflectionInfo(iter([request]))

    services = []
    for response in response_iterator:
        services.extend(response.list_services_response.service)

    return services


def get_file_descriptors(channel, service_name):
    stub = reflection_pb2_grpc.ServerReflectionStub(channel)
    request = reflection_pb2.ServerReflectionRequest(
        file_containing_symbol=service_name
    )
    response_iterator = stub.ServerReflectionInfo(iter([request]))

    file_descriptors = []
    for response in response_iterator:
        file_descriptors.extend(response.file_descriptor_response.file_descriptor_proto)

    return file_descriptors


def create_dynamic_stub(channel, service_descriptor):
    class DynamicStub:
        def __init__(self, channel, service_descriptor):
            self.channel = channel
            self.service_descriptor = service_descriptor
            self.methods = {method.name: method for method in service_descriptor.methods}

        def __getattr__(self, method_name):
            def call_method(request):
                method_descriptor = self.methods[method_name]
                method = grpc.unary_unary_rpc_method_handler(
                    lambda req, _: self.channel.unary_unary(
                        f"/{self.service_descriptor.full_name}/{method_descriptor.name}",
                        request_serializer=req.SerializeToString,
                        response_deserializer=method_descriptor.output_type._concrete_class.FromString
                    )(req, timeout=10)
                )
                return method(request)

            return call_method

    return DynamicStub(channel, service_descriptor)


def main():
    channel = grpc.insecure_channel('localhost:50051')

    # Get service descriptors using reflection
    services = get_service_descriptors(channel)
    sym_db = symbol_database.Default()

    print("Available services:")
    for service in services:
        print(service.name)
        if service.name == reflection.SERVICE_NAME:
            continue

        file_descriptors = get_file_descriptors(channel, service.name)
        for fd_proto in file_descriptors:
            fd = descriptor_pb2.FileDescriptorProto.FromString(fd_proto)
            print(fd)
            sym_db.pool.Add(fd)



if __name__ == '__main__':
    main()
    input("XD")
