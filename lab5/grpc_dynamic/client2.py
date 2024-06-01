import grpc
from google.protobuf import descriptor_pb2, symbol_database
from grpc_reflection.v1alpha import reflection_pb2, reflection_pb2_grpc


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
                response_type = method_descriptor.output_type._concrete_class
                method = grpc.unary_unary_rpc_method_handler(
                    lambda req, _: self.channel.unary_unary(
                        f"/{self.service_descriptor.full_name}/{method_descriptor.name}",
                        request_serializer=req.SerializeToString,
                        response_deserializer=response_type.FromString
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
        # if service.name == reflection.SERVICE_NAME:
        #     continue

        file_descriptors = get_file_descriptors(channel, service.name)
        for fd_proto in file_descriptors:
            fd = descriptor_pb2.FileDescriptorProto.FromString(fd_proto)
            sym_db.pool.Add(fd)

    # Find all service descriptors
    # for fd in sym_db.pool.FindAllFileDescriptors():
    #     for service in fd.services:
    #         print(f"Service: {service.full_name}")
    #         for method in service.methods:
    #             print(f"  Method: {method.name}")

    # Use the correct service name based on the available services
    service_name = 'protofile.ProtoFile'  # Ensure this matches your actual service name
    try:
        service_descriptor = sym_db.pool.FindServiceByName(service_name)
    except KeyError:
        print(f"Service {service_name} not found.")
        return

    stub = create_dynamic_stub(channel, service_descriptor)

    # Dynamically create request and response types
    add_request_type = sym_db.GetSymbol('protofile.AddRequest')  # Update this based on your proto package
    add_request = add_request_type(num1=10, num2=5)
    add_response = stub.Add(add_request)
    print("Addition Result:", add_response.result)

    try:
        concatenate_request_type = sym_db.GetSymbol(
            'protofile.ConcatenateRequest')  # Update this based on your proto package
        print("ConcatenateRequest type:", concatenate_request_type)  # Debugging
        concatenate_request = concatenate_request_type()  # Ensure that the request is correctly created
        concatenate_request.str1 = "Hello"
        concatenate_request.str2 = "World"
        concatenate_response = stub.Concatenate(concatenate_request)
        print("Concatenation Result:", concatenate_response.result)
    except KeyError as e:
        print(f"Error creating ConcatenateRequest: {e}")
        return

    try:
        set_type = sym_db.GetSymbol('protofile.Set')  # Update this based on your proto package
        sets = [
            set_type(values=[1, 2, 3]),
            set_type(values=[3, 3, 4]),
            set_type(values=[4, 5, 6]),
        ]

        combine_sets_request_type = sym_db.GetSymbol(
            'protofile.CombineSetsRequest')  # Update this based on your proto package
        combine_sets_request = combine_sets_request_type(sets=sets)
        combine_sets_response = stub.CombineSets(combine_sets_request)
        print("Combined Set:", combine_sets_response.combined_set.values)
    except KeyError as e:
        print(f"Error creating CombineSetsRequest: {e}")


if __name__ == '__main__':
    main()
