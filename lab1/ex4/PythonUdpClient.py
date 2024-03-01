import socket;

serverIP = "127.0.0.1"
serverPort = 9008
msg = "PYTHON"

print('PYTHON')
client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
client.sendto(bytes(msg, 'cp1250'), (serverIP, serverPort))

rec_msg = str(client.recv(100))
print(rec_msg)




