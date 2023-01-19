import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    Set<String> set = new HashSet<>();

    public String handleRequest(URI url) {
        System.out.println("Path: " + url.getPath());
        if (url.getPath().equals("/")) {
            return String.format("Goooogle");
        } else if (url.getPath().contains("/add")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                set.add(parameters[1]);
            }
            return String.format("%s added to search database", parameters[1]);
        } else if (url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    String key = parameters[1];
                    Set<String> results = new HashSet<>();
                    for (String item:set) {
                        if (item.contains(key)) {
                            results.add(item);
                        }
                    }
                    return String.format("Results: %s", results);
                }
        } 
        return "404 Not Found!";
    }
}


public class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
