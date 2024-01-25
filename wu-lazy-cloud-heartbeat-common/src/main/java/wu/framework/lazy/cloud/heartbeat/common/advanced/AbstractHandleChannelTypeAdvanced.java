package wu.framework.lazy.cloud.heartbeat.common.advanced;


import io.netty.channel.Channel;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

public abstract class AbstractHandleChannelTypeAdvanced<MSG> implements HandleChannelTypeAdvanced {

    /**
     * 处理当前数据
     *
     * @param channel 当前通道
     * @param msg     通道数据
     */
    protected abstract void doHandler(Channel channel, MSG msg);

    /**
     * 处理当前数据
     *
     * @param channel 当前通道
     * @param msg     通道数据
     */
    @Override
    public void handler(Channel channel, Object msg) {
        doHandler(channel, (MSG) msg);
    }

    /**
     * 是否支持当前类型
     *
     * @param msg 通道数据
     * @return 布尔类型 是、否
     */
    protected abstract boolean doSupport(MSG msg);

    /**
     * 是否支持当前类型
     *
     * @param msg 通道数据
     * @return 布尔类型 是、否
     */
    @Override
    public boolean support(Object msg) {
        if (msg == null) return false;
        if (!msg.getClass().isAssignableFrom(Objects.requireNonNull(getMsgTypes()))) {
            return false;
        }
        return doSupport((MSG) msg);
    }

    /**
     * 获取当前处理范型
     *
     * @return 范型
     */
    private Class<?> getMsgTypes() {

        Type superClassType = this.getClass().getGenericSuperclass();
        if (superClassType instanceof ParameterizedType parameterizedType) {

            Type[] actualTypes = parameterizedType.getActualTypeArguments();
//            for (Type type : actualTypes) {
//                System.out.println("范型类型：" + ((Class<?>) type).getName());
//            }
            return (Class<?>) actualTypes[0];
        } else {
//            System.out.println("未能获取到范型类型");
            return null;
        }
    }
}
