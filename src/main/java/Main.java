
import service.impl.TransactionServiceImp;

import javax.xml.ws.Endpoint;

public class Main {

    public static void main(String[] args) {
        try {
            Endpoint.publish("http://localhost:8000/ws/transaction", new TransactionServiceImp());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
