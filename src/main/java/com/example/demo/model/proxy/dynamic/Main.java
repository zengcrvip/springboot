package com.example.demo.model.proxy.dynamic;

import com.example.demo.model.proxy.Source;
import com.example.demo.model.proxy.Sourceable;

/**
 * @Author changrong.zeng
 * @Description
 * @Date 23:00 2020-03-11
 **/
public class Main {

    public static void main(String[] args) {

        // 目标对象
        Sourceable target  = new Source();
        // 【原始的类型 class cn.itcast.b_dynamic.UserDao】
        System.out.println(target.getClass());

        // 给目标对象，创建代理对象
        Sourceable proxy = (Sourceable) new ProxyFactory(target).getProxyInstance();
        // class $Proxy0   内存中动态生成的代理对象
        System.out.println(proxy.getClass());

        // 执行方法   【代理对象】
        proxy.method();

    }
}
