package tcpip;

import java.net.*;
import java.io.*;

public class TcpSenderClient {
	private static String remoteServerAddr = "192.168.1.100";
	private static int remoteServerPort = 5555;

	public static void main(String[] args) throws Exception {
		InetAddress remoteServerInetAddr = InetAddress.getByName(remoteServerAddr);
		Socket localSocket = new Socket(remoteServerInetAddr, remoteServerPort);

		String message = "This is string sent from TcpSenderClient";

		System.out.println("Connected to remote client: " + localSocket.getRemoteSocketAddress());
		OutputStream outToServer = localSocket.getOutputStream(); // pointer to stream which can send data to server
		DataOutputStream out = new DataOutputStream(outToServer);

		out.writeUTF(message + " :  " + localSocket.getLocalSocketAddress()); // data to send to server

		InputStream inFromServer = localSocket.getInputStream(); // pointer to stream which can receive data from server
		DataInputStream in = new DataInputStream(inFromServer);

		System.out.println("Server sent us reply as: " + in.readUTF()); // data received from server
		localSocket.close();
	}
}