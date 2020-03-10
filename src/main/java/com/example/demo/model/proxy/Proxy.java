package com.example.demo.model.proxy;

/**
 * @Author changrong.zeng
 * @Description
 * @Date 14:19 2020-03-06
 **/
public class Proxy implements Sourceable {

    private Source source;

    public Proxy(Source source) {
        this.source = source;
    }

    @Override
    public void method() {

        source.method();

    }
}
