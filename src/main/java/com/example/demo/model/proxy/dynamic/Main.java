package com.example.demo.model.proxy.dynamic;

import com.example.demo.model.proxy.Source;
import com.example.demo.model.proxy.Sourceable;
import org.springframework.cglib.proxy.Enhancer;

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

        System.out.println("=================proxy=============================");
        Source source = new Source();
        ProxyFactory2 proxyFactory2 = new ProxyFactory2(source);
        Sourceable source1 = (Sourceable) proxyFactory2.getInstance();
        source1.method();
        System.out.println(source1.getClass());

        System.out.println("=================cglib=============================");
        CGlibFactory factory2 = new CGlibFactory();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Source.class);
        enhancer.setCallback(factory2);

        Sourceable sourceable = (Source)enhancer.create();
        sourceable.method();



    }
}
