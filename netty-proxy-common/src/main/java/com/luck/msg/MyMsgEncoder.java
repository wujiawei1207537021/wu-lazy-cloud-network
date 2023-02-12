package com.luck.msg;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MyMsgEncoder extends MessageToByteEncoder<MyMsg> {

    public MyMsgEncoder() {

    }

    @Override
    protected void encode(ChannelHandlerContext ctx, MyMsg msg, ByteBuf out) throws Exception {
        int bodyLength = 5;
        if (msg.getData() != null) {
            bodyLength += msg.getData().length;
        }

        out.writeInt(bodyLength);

        out.writeByte(msg.getType());

        if (msg.getData() != null) {
            out.writeBytes(msg.getData());
        }
    }
}