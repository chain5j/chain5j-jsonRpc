package com.xwc1125.chain5j.jsonrpc;

import com.xwc1125.chain5j.jsonrpc.http.HttpService;
import com.xwc1125.chain5j.protocol.entity.Request;

import java.io.IOException;
import java.util.Arrays;

/**
 * Description: <br>
 *
 * @author xwc1125 <br>
 * @Copyright: Copyright (c) 2019 <br>
 * @date 2019-01-05  16:30 <br>
 */
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