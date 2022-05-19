package org.zhongshuwen.zswjava.interfaces;

import org.zhongshuwen.zswjava.error.rpcProvider.GetBlockInfoRpcError;
import org.zhongshuwen.zswjava.error.rpcProvider.GetInfoRpcError;
import org.zhongshuwen.zswjava.error.rpcProvider.GetRawAbiRpcError;
import org.zhongshuwen.zswjava.error.rpcProvider.GetRequiredKeysRpcError;
import org.zhongshuwen.zswjava.error.rpcProvider.SendTransactionRpcError;
import org.zhongshuwen.zswjava.models.rpcProvider.request.GetBlockInfoRequest;
import org.zhongshuwen.zswjava.models.rpcProvider.request.GetRawAbiRequest;
import org.zhongshuwen.zswjava.models.rpcProvider.request.GetRequiredKeysRequest;
import org.zhongshuwen.zswjava.models.rpcProvider.request.SendTransactionRequest;

import org.zhongshuwen.zswjava.models.rpcProvider.response.GetBlockInfoResponse;
import org.zhongshuwen.zswjava.models.rpcProvider.response.GetInfoResponse;
import org.zhongshuwen.zswjava.models.rpcProvider.response.GetRawAbiResponse;
import org.zhongshuwen.zswjava.models.rpcProvider.response.GetRequiredKeysResponse;
import org.zhongshuwen.zswjava.models.rpcProvider.response.SendTransactionResponse;
import org.jetbrains.annotations.NotNull;

/**
 * The interface of an RPC provider.
 */
public interface IRPCProvider {

    /**
     * Returns an object containing various details about the blockchain.
     *
     * @return the latest info/status of a chain.
     * @throws GetInfoRpcError thrown if there are any exceptions/backend errors during the
     * getInfo() process.
     */
    @NotNull
    GetInfoResponse getInfo() throws GetInfoRpcError;

    /**
     * Returns an object containing various details about a specific block on the blockchain.
     *
     * @param getBlockInfoRequest Info of a specific block.
     * @return the info/status of a specific block in the request
     * @throws GetBlockInfoRpcError thrown if there are any exceptions/backend error during the
     * getBlockInfo() process.
     */
    @NotNull
    GetBlockInfoResponse getBlockInfo(GetBlockInfoRequest getBlockInfoRequest) throws GetBlockInfoRpcError;

    /**
     * Gets raw abi for a given contract.
     *
     * @param getRawAbiRequest Info of a specific smart contract.
     * @return the serialized ABI of a smart contract in the request.
     * @throws GetRawAbiRpcError thrown if there are any exceptions/backend error during the
     * getRawAbi() process.
     */
    @NotNull
    GetRawAbiResponse getRawAbi(GetRawAbiRequest getRawAbiRequest) throws GetRawAbiRpcError;

    /**
     * Returns the required keys needed to sign a transaction.
     *
     * @param getRequiredKeysRequest Info to get required keys
     * @return the required keys to sign a transaction
     * @throws GetRequiredKeysRpcError thrown if there are any exceptions/backend error during the
     * getRequiredKeys() process.
     */
    @NotNull
    GetRequiredKeysResponse getRequiredKeys(GetRequiredKeysRequest getRequiredKeysRequest) throws GetRequiredKeysRpcError;

    /**
     * This method expects a transaction in JSON format and will attempt to apply it to the blockchain.
     *
     * @param sendTransactionRequest the transaction to push with signatures.
     * @return the send transaction response
     * @throws SendTransactionRpcError thrown if there are any exceptions/backend error during the
     * sendTransaction() process.
     */
    @NotNull
    SendTransactionResponse sendTransaction(SendTransactionRequest sendTransactionRequest) throws SendTransactionRpcError;
}
