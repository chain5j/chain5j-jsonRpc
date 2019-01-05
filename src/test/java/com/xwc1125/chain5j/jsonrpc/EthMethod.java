package com.xwc1125.chain5j.jsonrpc;

import com.xwc1125.chain5j.protocol.entity.Request;

/**
 * Description: <br>
 *
 * @author xwc1125 <br>
 * @Copyright: Copyright (c) 2019 <br>
 * @date 2019-01-04  11:38 <br>
 */
public interface EthMethod {

    Request<?, EthBalance> getBalance(String address, String blockParameter);

}
