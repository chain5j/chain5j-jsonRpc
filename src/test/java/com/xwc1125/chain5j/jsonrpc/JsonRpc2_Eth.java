package com.xwc1125.chain5j.jsonrpc;

import com.xwc1125.chain5j.protocol.entity.Request;

import java.util.Arrays;

/**
 * Description: <br>
 *
 * @author xwc1125 <br>
 * @Copyright: Copyright (c) 2019 <br>
 * @date 2019-01-04  11:20 <br>
 */
public class JsonRpc2_Eth extends Eth5j {
    protected final RpcService rpcService;

    public JsonRpc2_Eth(RpcService rpcService) {
        this.rpcService = rpcService;
    }

    @Override
    public Request<?, EthBalance> getBalance(String address, String blockParameter) {
        return new Request("eth_getBalance", Arrays.asList(address, blockParameter), this.rpcService, EthBalance.class);

    }
}
