package network;

import java.io.*;
import java.net.Socket;
import java.util.Properties;

import static java.lang.Thread.sleep;

public class Client {
    ObjectOutputStream serverWriter;
    ObjectInputStream serverReader;
    Socket clientSocket;
    Properties props = new Properties();

    public Respons sendRequest(Request request) throws InterruptedException {
        try {
            this.openClientSocket();
            serverWriter.writeObject(request);
            serverWriter.flush();
            Respons respons = (Respons) serverReader.readObject();
            this.closeClientSocet();
            return respons;
        } catch (Exception exception) {
            System.out.println("Сервер не работает.");
            return new Respons("");
        }
    }

    private void openClientSocket() throws IOException {
//        final int port;
//        final String host;
//
//        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("res.properties");
//
//        this.props.load(in);
//        port = Integer.parseInt(this.props.getProperty("port"));
//        host = String.valueOf(this.props.getProperty("host"));
        try {
            clientSocket = new Socket("localhost", 8080);
            serverWriter = new ObjectOutputStream(clientSocket.getOutputStream());
            serverReader = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException exception) {
            System.out.println(".........");
        }
    }

    private void closeClientSocet() throws IOException {
         try {
             clientSocket.close();
             serverWriter.close();
             serverReader.close();
         } catch (Exception exception) {
             System.out.println("Не удалось закрыть подключение к серверу.");
         }

    }

}
