package com.wanfeng.javalearn.RPC.NIO;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

@Slf4j
public class Client {
    public static void main(String[] args) throws IOException {
        SocketChannel client = SocketChannel.open();
        client.connect(new InetSocketAddress(9999));
        client.write(ByteBuffer.allocate(10).put((byte) 'a').put((byte) 'v'));
        client.close();
    }
}
