# TCPServer, Kurose and Ross, 7th edition, page 196
from socket import *
serverPort = 7789
serverSocket = socket(AF_INET, SOCK_STREAM)
serverSocket.bind(('localhost',serverPort))
serverSocket.listen(1)
print('The server is ready to receive')
while True:
    connectionSocket, addr = serverSocket.accept()
    sentence = connectionSocket.recv(1024).decode()
    print(sentence)
    # capitalizedSentence = sentence.upper()
    # connectionSocket.send(capitalizedSentence.encode())

connectionSocket.close()