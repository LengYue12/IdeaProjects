package com.lagou.task21;

public class PacketSendFactory implements Provider {
    @Override
    public Sender produce() {
        return new PacketSender();
    }
}
