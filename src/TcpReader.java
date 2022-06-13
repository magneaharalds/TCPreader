import java.util.*;
import java.net.*;


/*
* Run with
*       sudo tcpdump -nli en0 | java TcpReader
* the interface name may be something else than "en0"
*
* This runs until keyboard interrupted
 */
public class TcpReader {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        //Local ip-address of running computer
        String local_ip = "192.168.";

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
                String ip_send = "";
                String ip_recieve = "";
                /*
                * To take care of ip-addresses that have only four sub-numbers (no port number)
                * we check the number of sub-numbers and ignore the port number if present.
                */
                String[] ip_temp = elems[ip_send_ind].split("[.]");
                if(ip_temp.length >4){
                    ip_send = elems[ip_send_ind].substring(0 , elems[ip_send_ind].lastIndexOf("."));
                }else{
                    ip_send = elems[ip_send_ind];
                }
                ip_temp = elems[ip_recieve_ind].split("[.]");
                if(ip_temp.length > 4){
                    ip_recieve = elems[ip_recieve_ind].substring(0 , elems[ip_recieve_ind].lastIndexOf("."));
                }else{
                    ip_recieve = elems[ip_recieve_ind];
                }


                // Count bytes in/out
                // Here
                if(ip_send.startsWith(local_ip)){
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

            /*
            * Approximately every 10 seconds (10000 millisec):
            *  print out/in-going traffic and top 10 seen ip-addresses in descending order
            */
            if (time+10000 < System.currentTimeMillis()){
                time = System.currentTimeMillis(); // Update time
                System.out.println(bytes_in+ " bytes recieved");
                System.out.println(bytes_out + " bytes sent");
                ArrayList<Map.Entry<String, Integer>> sorted = sortValue(ip_addresses);
                System.out.println("Most frequently seen IPs");
                // Make sure we have seen more than 10 ip-addresses
                // if not then just print all seen
                if(sorted.size()>10){
                    for(int i = 0; i <10; i++){
                        System.out.println(sorted.get(i)+" connections");
                    }
                    int ip_left = sorted.size() - 10;
                    System.out.println("+"+ ip_left +" other IPs");
                }else{
                    for(int i = 0; i < sorted.size();i++){
                        System.out.println(sorted.get(i)+" connections");
                    }
                }

                System.out.println();
                bytes_in = 0;
                bytes_out = 0;
                ip_addresses = new Hashtable<String, Integer>();
            }
        }
    }

    /*
    * Function to sort the hashtable that contains all seen ip addresses and their count
    * Returns an ArrayList
     */
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
