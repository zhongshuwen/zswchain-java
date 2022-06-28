package org.zhongshuwen.zswjava.models.rpcProvider.request;

import com.google.gson.annotations.Expose;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

/*

"code": "string",
"table": "string",
"scope": "string",
"index_position": "string",
"key_type": "string",
"encode_type": "string",
"lower_bound": "string",
"upper_bound": "string",
"limit": 10,
"reverse": false,
"show_payer": false
}
 */
/**
 * The request class for getTableRows() RPC call {@link org.zhongshuwen.zswjava.interfaces.IRPCProvider#getBlockInfo(GetBlockInfoRequest)}
 */


public class GetTableRowsRequest {
    private static final int DEFAULT_FETCH_LIMIT = 10;

    @Expose
    private boolean json = true;
    @Expose
    private boolean reverse = false;
    @Expose
    private boolean show_payer = false;

    @Expose
    private String code;

    @Expose
    private String scope;

    @Expose
    private String table;

    @Expose
    private String key_type = "";

    @Expose
    private String index_position = "0";

    @Expose
    private String encode_type = "";

    @Expose
    private String lower_bound = "";

    @Expose
    private String upper_bound = "";

    @Expose
    private int limit;


    public GetTableRowsRequest(String scope, String code, String table,
                           int indexPos, String keyType, String encodeType, String lowerBound, String upperBound, int limit) {
        this.scope = scope;
        this.code = code;
        this.table = table;

        this.key_type = keyType;
        this.encode_type = encodeType;
        this.index_position = (indexPos < 0 ? 0 : indexPos)+"";

        this.lower_bound = lowerBound == null ? "" : lowerBound;
        this.upper_bound = upperBound == null ? "" : upperBound;
        this.limit = limit <= 0 ? DEFAULT_FETCH_LIMIT : limit;
    }
}