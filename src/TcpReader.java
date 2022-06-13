import java.util.*;

// javac TcpReader.java
// sudo tcpdump -nli en0 | java TcpReader
public class TcpReader {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int bytes_in = 0;
        int bytes_out = 0;
        Hashtable<String, Integer> ip_addresses = new Hashtable<String, Integer>();
        int ip_send_ind = 2;
        int ip_recieve_ind = 4;

        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            String[] elems = line.split(" ");
            //System.out.println(elems[ip_send_ind]);
            //System.out.println(elems[ip_recieve_ind]);
            // check if action was sending bytes
            //System.out.println(elems[elems.length-2]);
            //System.out.println(elems[ip_send_ind].lastIndexOf("."));

            if(elems[elems.length-2].equals("length")){
                String ip_send = elems[ip_send_ind].substring(0 , elems[ip_send_ind].lastIndexOf("."));
                String ip_recieve = elems[ip_recieve_ind].substring(0 , elems[ip_recieve_ind].lastIndexOf("."));

                // Count bytes in/out
                if(ip_send.startsWith("192.168.0.")){
                    bytes_out += Integer.parseInt(elems[elems.length-1]);
                }else{
                    bytes_in += Integer.parseInt(elems[elems.length-1]);
                }

                // Store sending IP address
                if(ip_addresses.containsKey(ip_send)) {
                    ip_addresses.put(ip_send, ip_addresses.get(ip_send)+1);
                } else {
                    ip_addresses.put(ip_send, 1);
                }

                // Store recieving IP address
                if(ip_addresses.containsKey(ip_recieve)) {
                    ip_addresses.put(ip_recieve, ip_addresses.get(ip_recieve)+1);
                } else {
                    ip_addresses.put(ip_recieve, 1);
                }
            }
            System.out.println(ip_addresses);
            System.out.println(bytes_out);
            System.out.println(bytes_in);
        }
    }

    /*while stdin
    lesa streng þangað til \n
    Splitta streng á bilum
    Finna 192.168
    Finna ekki 192 168
    Finna það sem kemur eftir length
    Skila öllu þessu í 3x fylki*/

}
