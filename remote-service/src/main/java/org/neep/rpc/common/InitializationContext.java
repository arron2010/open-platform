package org.neep.rpc.common;

/**
 * @Title InitializationContext
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company:
 * @Author
 * @Version 1.0.0
 * @Create 19-6-30 下午10:03
 */
public class InitializationContext {
    private static InitializationContext instance = null;
    //是否使用网关调用: false为两个服务直接点对点调用
    private boolean useGateway = false;

    //是否使用注册模式调用，false为两个服务直接点对点调用
    private boolean register = false;

    public static InitializationContext getInstance(){
        if (instance == null){
            synchronized (InitializationContext.class){
                if (instance == null){
                    instance = new InitializationContext();
                }
            }
        }
        return instance;
    }

    public boolean isUseGateway() {
        return useGateway;
    }

    public void setUseGateway(boolean useGateway) {
        synchronized (this){
            this.useGateway = useGateway;
            if (this.useGateway){
                this.register= true;
            }
        }
    }

    public boolean isRegister() {
        return register;
    }

    public void setRegister(boolean register) {
        synchronized (this){
            if (this.useGateway && !register){
                return;
            }
            this.register = register;
        }
    }
}
