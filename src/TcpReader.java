import java.util.*;

// javac TcpReader.java
// sudo tcpdump -nli en0 | java TcpReader
public class TcpReader {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int bytes_in = 0;
        int bytes_out = 0;
        Hashtable<String, int> ip_addresses = new Hashtable<String, int>();

        while (scan.hasNextLine()) {
            String line = scan.nextLine();

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
