package com.wanfeng.javalearn.RPC.BIO;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class Server {
    public static void main(String[] args){
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            ExecutorService pool = Executors.newFixedThreadPool(10);
            while (true) {
                Socket accept = serverSocket.accept();
                pool.execute(() ->{
                    try {
                        handleAccept(accept);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }catch (Exception e){
            log.error("BIO服务器错误");
            e.printStackTrace();
        }
    }

    public static void handleAccept(Socket socket) throws IOException {
        log.info("请求连接 [{}]", socket.getInetAddress());
        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1];
        inputStream.read(bytes);
        System.out.println(Arrays.toString(bytes));
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write('a');
    }
}
