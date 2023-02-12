package com.luck.msg;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class MyMsgDecoder extends LengthFieldBasedFrameDecoder {

    public MyMsgDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment,
                        int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }

    public MyMsgDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment,
                        int initialBytesToStrip, boolean failFast) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
    }

    @Override
    protected MyMsg decode(ChannelHandlerContext ctx, ByteBuf in2) throws Exception {
        ByteBuf in = (ByteBuf) super.decode(ctx, in2);
        if (in == null) {
            return null;
        }

        if (in.readableBytes() < 4) {
            return null;
        }

        MyMsg myMsg = new MyMsg();
        int dataLength = in.readInt();
        byte type = in.readByte();
        myMsg.setType(type);
        byte[] data = new byte[dataLength - 5];
        in.readBytes(data);
        myMsg.setData(data);
        in.release();

        return myMsg;
    }
}