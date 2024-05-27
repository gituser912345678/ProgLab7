package network;

import collectionManager.StartCommand;
import exception.NoElementException;
import exception.UnknowElementException;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class Server {
    private ServerSocketChannel serverSocketChannel;
    ObjectInputStream userRequest;
    ObjectOutputStream toUser;
    Properties props = new Properties();
    private final ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    private Respons respons;
    private final ForkJoinPool forkJoinPool = new ForkJoinPool(3);

    public void start() {

        try {
            openServerSocket();
            while (true) {
                SocketChannel clientSocket = serverSocketChannel.accept();
                if (clientSocket != null) {
                    clientRequest(clientSocket);
                }
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }

    }

    private void clientRequest(SocketChannel socket) throws IOException {

        try (socket) {

            userRequest = new ObjectInputStream(socket.socket().getInputStream());
            toUser = new ObjectOutputStream(socket.socket().getOutputStream());
            ObjectWriter objectWriter = new ObjectWriter();
            ObjectReader objectReader = new ObjectReader();

            cachedThreadPool.submit(() -> {
                try {
                    objectReader.objectReader(userRequest);
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            }).get();
            forkJoinPool.submit(() -> {
                try {
                    return commandExecution(objectReader.getRequest());
                } catch (NoElementException e) {
                    System.out.println(e.getMessage());
                }
                return null;
            }).get();
            cachedThreadPool.submit(() -> {
                try {
                    objectWriter.objectWriter(toUser, respons);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }).get();


        } catch (IOException | InterruptedException | ExecutionException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private Respons commandExecution(Request request) throws UnknowElementException, IOException, NoElementException {
        StartCommand startCommand = new StartCommand();
        respons = startCommand.startExecute(request);
        return respons;
    }


    private void openServerSocket() throws IOException {

        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("res.properties");
        this.props.load(in);
        final int port = Integer.parseInt(this.props.getProperty("port"));

        String host = String.valueOf(this.props.getProperty("host"));

        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(host, port));
            serverSocketChannel.configureBlocking(false);
        } catch (IOException exception) {
            System.out.println("Проблема подключения сервера");
        }
    }
}