package hello.core.lfeCycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NetworkClient {

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // 서비스 시작시 호출
    public void connect() {
        System.out.println("connect:" + url);
    }

    public void call(String message) {
        System.out.println("call: " + url + ", message: " + message);
    }

    // 서비스 종료시 호출
    public void disconnect() {
        System.out.println("disconnect: " + url);
    }

    @PreDestroy
    public void close() throws Exception {
        call("close method ");
        disconnect();
    }

    @PostConstruct
    public void init() throws Exception {
        call("init method ");
        connect();
    }
}
