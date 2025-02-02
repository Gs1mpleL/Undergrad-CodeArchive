package com.wanfeng.javalearn.RPC.Netty.codec;

import com.wanfeng.javalearn.RPC.Netty.common.CompressTypeEnum;
import com.wanfeng.javalearn.RPC.Netty.common.RpcConstants;
import com.wanfeng.javalearn.RPC.Netty.common.RpcMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class RpcMessageEncoder extends MessageToByteEncoder<RpcMessage> {
    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(0);
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RpcMessage rpcMessage, ByteBuf byteBuf) throws Exception {
        try {
            // 魔数
            byteBuf.writeBytes(RpcConstants.MAGIC_NUMBER);
            // 版本
            byteBuf.writeByte(RpcConstants.VERSION);
            // 留出总长字段
            byteBuf.writerIndex(byteBuf.writerIndex() + 4);
            byte messageType = rpcMessage.getMessageType();
            // 消息类型
            byteBuf.writeByte(messageType);
            // 序列化方式
            byteBuf.writeByte(rpcMessage.getCodec());
            // 压缩方式
            byteBuf.writeByte(CompressTypeEnum.GZIP.getCode());
            // 原子计数器++
            byteBuf.writeInt(ATOMIC_INTEGER.getAndIncrement());
            // 如果是心跳请求，总长度就是Header的长度
            int fullLength = RpcConstants.HEAD_LENGTH;



        }catch (Exception e){
            log.info("encoder error",e);
        }
    }
}
