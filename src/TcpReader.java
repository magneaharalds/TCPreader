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
        long time = System.currentTimeMillis();
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            String[] elems = line.split(" ");

            // check if action was sending bytes
            if(elems[elems.length-2].equals("length") && elems[1].equals("IP")){
                /*
                * Problems with IP addresses with no port number
                 */
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
            //sortValue(ip_addresses);

            if (time+10000 < System.currentTimeMillis()){
                time = System.currentTimeMillis();
                System.out.println("Sum of ingoing traffic in bytes: " + bytes_in);
                System.out.println("Sum of outgoing traffic in bytes: " + bytes_out);
                ArrayList<Map.Entry<String, Integer>> sorted = sortValue(ip_addresses);
                System.out.println("Most frequently seen ip-addresses:");
                if(sorted.size()>10){
                    for(int i = 0; i <10; i++){
                        System.out.println(sorted.get(i));
                    }
                }else{
                    for(int i = 0; i < sorted.size();i++){
                        System.out.println(sorted.get(i));
                    }

                }
                System.out.println();
            }
        }
    }

    public static ArrayList<Map.Entry<String, Integer>> sortValue(Hashtable<String, Integer> t){

        //Transfer as List and sort it
        ArrayList<Map.Entry<String, Integer>> l = new ArrayList(t.entrySet());
        Collections.sort(l, new Comparator<Map.Entry<String, Integer>>(){

            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }});

        return l;
    }
}
