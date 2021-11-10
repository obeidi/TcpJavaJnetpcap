package javapcap;

import io.pkts.PacketHandler;
import io.pkts.Pcap;
import io.pkts.buffer.Buffer;
import io.pkts.packet.Packet;
import io.pkts.packet.TCPPacket;
import io.pkts.packet.UDPPacket;
import io.pkts.protocol.Protocol;

import java.io.IOException;

public class JavaPcap {

    public static void main(String[] args) throws IOException {

        final Pcap pcap = Pcap.openStream("tcpdump4.pcap");
//        final Pcap pcap2 = Pcap;

        pcap.loop(new PacketHandler() {
            @Override
            public boolean nextPacket(Packet packet) throws IOException {

//                TCPPacket tcpPacket = (TCPPacket) packet.getPacket(Protocol.TCP);
//                Buffer buffer = tcpPacket.getPayload();
//                if (buffer != null) {
//                	System.out.println("TCP: " + buffer);
//                }

                StringBuilder out = new StringBuilder(); 
                		
                out.append(packet.getName() + "\n" + 
                packet.getProtocol() +  "\n" +
                packet.getPayload() +  "\n" +
                packet.getClass());
                
                System.out.println(out);
                
//                if (packet.hasProtocol(Protocol.TCP)) {
//
//                    TCPPacket tcpPacket = (TCPPacket) packet.getPacket(Protocol.TCP);
//                    Buffer buffer = tcpPacket.getPayload();
//                    if (buffer != null) {
//                        System.out.println("TCP: " + buffer);
//                    }
//                } else if (packet.hasProtocol(Protocol.UDP)) {
//
//                    UDPPacket udpPacket = (UDPPacket) packet.getPacket(Protocol.UDP);
//                    Buffer buffer = udpPacket.getPayload();
//                    if (buffer != null) {
//                        System.out.println("UDP: " + buffer);
//                    }
//                }
                return true;
            }
        });
    }
}