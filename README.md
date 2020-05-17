# chain5j-jsonrpc

基于Okhttp的jsonRpc

## Dependency

* Maven:

```
<dependency>
  <groupId>com.xwc1125.chain5j</groupId>
  <artifactId>chain5j-jsonrpc</artifactId>
  <version>1.0.1</version>
</dependency>
```

* Gradle:

```
implementation 'com.xwc1125.chain5j:chain5j-jsonrpc:1.0.1'
```

## 示例代码

```java
public class RpcServiceTest {
    private static Eth5j getService() {
        HttpService httpService = new HttpService("http://127.0.0.1:7545");
        Eth5j eth5j = Eth5j.build(httpService);
        return eth5j;
    }

    public static void main(String[] args) {
        try {
            // 方式一
            HttpService httpService = new HttpService("http://127.0.0.1:7545");
            Request request = new Request("eth_getBalance", Arrays.asList("0x9254E62FBCA63769DFd4Cc8e23f630F0785610CE", "latest"), httpService, EthBalance.class);
            EthBalance balance = httpService.send(request, EthBalance.class);
            System.out.println(balance.getResult());

            // 方式二
            String address = "0x9254E62FBCA63769DFd4Cc8e23f630F0785610CE";
            Request<?, EthBalance> ethBalance = getService().getBalance(address, "latest");
            EthBalance send = ethBalance.send();
            System.out.println(send.getResult());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

```
