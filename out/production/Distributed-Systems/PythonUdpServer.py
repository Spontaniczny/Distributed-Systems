import socket;

serverPort = 9008
serverSocket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
serverSocket.bind(('', serverPort))
client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
buff = []

print('PYTHON UDP SERVER')

while True:

    buff, address = serverSocket.recvfrom(1024)
    rec_msg = str(buff, 'cp1250')
    print("python udp server received msg: " + rec_msg)

    if rec_msg == "PYTHON":
        send_buffer = "YOU ARE PYTHON"
    elif rec_msg == "JAVA":
        send_buffer = "YOU ARE JAVA"
    else:
        send_buffer = "WHO ARE YOU"

    client.sendto(bytes(send_buffer, 'cp1250'), (address[0], address[1]))
