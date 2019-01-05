package com.xwc1125.chain5j.jsonrpc;

/**
 * Description: <br>
 *
 * @author xwc1125 <br>
 * @Copyright: Copyright (c) 2019 <br>
 * @date 2019-01-04  11:27 <br>
 */
public abstract class Eth5j implements EthMethod {
    public static Eth5j build(RpcService rpcService) {
        return new JsonRpc2_Eth(rpcService);
    }
}
