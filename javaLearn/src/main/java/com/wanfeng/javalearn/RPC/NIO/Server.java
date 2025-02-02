package com.wanfeng.javalearn.RPC.NIO;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

@Slf4j
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel server = ServerSocketChannel.open();
        server.configureBlocking(false);
        Selector selector = Selector.open();
        // 服务器绑定端口
        server.socket().bind(new InetSocketAddress(9999));
        // 服务器channel注册到Selector，并监听accept事件
        server.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            // 阻塞等待Selector监听到读写事件
            selector.select();
            // 获取监听到的事件
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()){
                SelectionKey next = iterator.next();
                iterator.remove();
                System.out.println(next.isAcceptable());
                System.out.println(next.isReadable());
                System.out.println(next.isWritable());
                if (next.isAcceptable()){
                    log.info("连接请求");
                    // 如果是连接请求，说明这个channel就是服务器的chanel，直接cast
                    ServerSocketChannel channel = (ServerSocketChannel) next.channel();
                    SocketChannel accept = channel.accept();
                    // 把新的channel也注册到Selector   监听读事件
                    accept.configureBlocking(false);
                    accept.register(selector,SelectionKey.OP_READ);
                }
                if (next.isReadable()){
                    SocketChannel channel = (SocketChannel) next.channel();
                    // 处理读事件
                    ByteBuffer allocate = ByteBuffer.allocate(10);
                    int read = channel.read(allocate);
                    if (read <= 0){
                        channel.close();
                    }
                    System.out.println(allocate.get());
                }
            }
        }
    }
}
