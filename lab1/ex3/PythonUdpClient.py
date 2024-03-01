import socket;

serverIP = "127.0.0.1"
serverPort = 9008
msg = (300).to_bytes(4, byteorder='little')


print('PYTHON UDP CLIENT')
client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
client.sendto(msg, (serverIP, serverPort))
rec_msg = int.from_bytes(client.recv(4), byteorder="little")
print(rec_msg)





