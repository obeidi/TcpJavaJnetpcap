package javaPcap2;

import java.io.EOFException;
import java.util.ArrayList;
import java.util.List;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;
import org.pcap4j.core.BpfProgram;
import org.pcap4j.core.BpfProgram.BpfCompileMode;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapAddress;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.PcapNetworkInterface.PromiscuousMode;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.TcpPacket;
import org.pcap4j.packet.UdpPacket;

public class NetworkInterfaces {

    public static void main(String[] args) throws Exception {
        List<PcapIf> alldevs = new ArrayList<PcapIf>(); // Will be filled with NICs
        StringBuilder errbuf = new StringBuilder(); // For any error msgs

        int r = Pcap.findAllDevs(alldevs, errbuf);
        if (r == Pcap.NOT_OK || alldevs.isEmpty()) {
            System.err.printf("Can't read list of devices, error is %s",
                    errbuf.toString());
            return;
        }

        System.out.println("Network devices found:");

        int i = 0;
        for (PcapIf device : alldevs) {
            String description = (device.getDescription() != null) ? device
                    .getDescription() : "No description available";
            System.out.printf("#%d: %s [%s]\n", i++, device.getName(),
                    description);
            System.out.println(device.getName());
        }
        testSetFiltertcp();
//        waitForPing();
    }
    
    
    
    
    public static void waitForPing() throws PcapNativeException, NotOpenException {
        PcapNetworkInterface nif = Pcaps.getDevByName("\\Device\\NPF_{85A4631D-49CA-4BC2-8827-8D9A8CC23190}");
        System.out.println(nif.getName() + " (" + nif.getDescription() + ")");
        for (PcapAddress addr: nif.getAddresses()) {
          if (addr.getAddress() != null) {
            System.out.println("IP address: " + addr.getAddress());
          }
        }
        System.out.println("");

        PcapHandle handle = nif.openLive(65536, PromiscuousMode.PROMISCUOUS, 10);
        handle.setFilter(
          "tcp port 9080",
          BpfCompileMode.OPTIMIZE
        );

        while (true) {
          Packet packet = handle.getNextPacket();
          if (packet == null) {
        	  System.out.println("nichts gefunden");
            continue;
          }
          System.out.println("packet.getRawData()");
          if (packet.contains(TcpPacket.class)) {
        	  System.out.println(packet.getRawData().toString());
            break;
          }
        }

        handle.close();
      }

    
    public static void testSetFiltertcp() throws Exception {
        PcapHandle handle = null;
        BpfProgram prog = null;
        try {
          handle
            = Pcaps.openOffline(
                "tcpdump4.pcap"
              );
          prog = handle.compileFilter(
            "tcp", BpfCompileMode.OPTIMIZE, PcapHandle.PCAP_NETMASK_UNKNOWN
          );
          handle.setFilter(prog);
          int count = 0;
          try {
            while (true) {
              Packet p = handle.getNextPacketEx();
              System.out.println(p.get(TcpPacket.class));
              count++;
            }
          } catch (EOFException e) {}
          System.out.println( count);
        } finally {
          if (handle != null) {
            handle.close();
          }
          if (prog != null) {
            prog.free();
          }
        }
      }
    
    

}