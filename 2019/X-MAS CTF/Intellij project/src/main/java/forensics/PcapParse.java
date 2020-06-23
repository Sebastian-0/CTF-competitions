package forensics;

import io.pkts.PacketHandler;
import io.pkts.Pcap;

import java.io.IOException;

public class PcapParse {
	public static void main(String[] args) throws IOException {
		Pcap cap = Pcap.openStream("C:\\Users\\MelonBag\\Desktop\\CTF\\Forensics\\converted.pcap");
		cap.loop( packet -> {
//			System.out.println(packet.getProtocol());
			System.out.println(packet.getPayload().readLine());
			return true;
		});
	}
}
