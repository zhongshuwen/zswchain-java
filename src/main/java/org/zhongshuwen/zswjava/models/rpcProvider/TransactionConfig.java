package org.zhongshuwen.zswjava.models.rpcProvider;

import org.zhongshuwen.zswjava.interfaces.IRPCProvider;
import org.zhongshuwen.zswjava.models.rpcProvider.request.GetBlockInfoRequest;
import org.zhongshuwen.zswjava.models.rpcProvider.request.GetBlockRequest;
import org.zhongshuwen.zswjava.models.rpcProvider.response.GetInfoResponse;

/**
 * A configuration class that allows the developer to change certain defaults associated with a
 * Transaction.
 */
public class TransactionConfig {

    /**
     * Default blocks behind to use if blocksbehind is not set or instance of this class is not
     * used
     */
    private static final int DEFAULT_BLOCKS_BEHIND = 3;

    /**
     * Default expires seconds to use if expires seconds is not set or instance of this class is not
     * used
     */
    private static final int DEFAULT_EXPIRES_SECONDS = 5 * 60;

    /**
     * Default useLastIrreversible to use last irreversible block rather than blocksBehind when
     * calculating TAPOS
     */
    private static final boolean DEFAULT_USE_LAST_IRREVERSIBLE = true;

    /**
     * The Expires seconds.
     * <br>
     * Append this value to {@link GetInfoResponse#getHeadBlockTime()} in second then assign it to
     * {@link Transaction#setExpiration(String)}
     */
    private int expiresSeconds = DEFAULT_EXPIRES_SECONDS;

    /**
     * The amount blocks behind from head block.
     * <br>
     * It is an argument to calculate head block number to call {@link
     * org.zhongshuwen.zswjava.interfaces.IRPCProvider#getBlockInfo(GetBlockInfoRequest)}
     */
    private int blocksBehind = DEFAULT_BLOCKS_BEHIND;

    /**
     * Use the last irreversible block when calculating TAPOS rather than blocks behind.
     * <br>
     * Mutually exclusive with {@link TransactionConfig#setBlocksBehind(int)}.  If set,
     * {@link TransactionConfig#getBlocksBehind()} will be ignored and TAPOS will be calculated by fetching the last
     * irreversible block with {@link IRPCProvider#getInfo()} and the expiration for the transaction
     * will be set {@link TransactionConfig#setExpiresSeconds(int)} after that block's time.
     */
    private boolean useLastIrreversible = DEFAULT_USE_LAST_IRREVERSIBLE;

    /**
     * Gets the expiration time for the transaction.
     * <br>
     * Append this value to {@link GetInfoResponse#getHeadBlockTime()} in seconds. Assign it to
     * {@link Transaction#setExpiration(String)}.
     *
     * @return when the transaction expires (in seconds)
     */
    public int getExpiresSeconds() {
        return expiresSeconds;
    }

    /**
     * Sets the expiration time for the transaction.
     * <br>
     * Append this value to {@link GetInfoResponse#getHeadBlockTime()} in second then assign it to
     * {@link Transaction#setExpiration(String)}
     *
     * @param expiresSeconds when the transaction expires (in seconds)
     */
    public void setExpiresSeconds(int expiresSeconds) {
        this.expiresSeconds = expiresSeconds;
    }

    /**
     * Gets blocks behind.
     * <br>
     * It is an argument to calculate head block number to call {@link org.zhongshuwen.zswjava.interfaces.IRPCProvider#getBlockInfo(GetBlockInfoRequest)}
     * @return the blocks behind
     */
    public int getBlocksBehind() {
        return blocksBehind;
    }

    /**
     * Sets blocks behind.
     * <br>
     * It is an argument to calculate head block number to call {@link
     * org.zhongshuwen.zswjava.interfaces.IRPCProvider#getBlockInfo(GetBlockInfoRequest)}
     * @param blocksBehind the blocks behind
     */
    public void setBlocksBehind(int blocksBehind) {
        this.blocksBehind = blocksBehind;
    }

    /**
     * Gets useLastIrreversible.
     * <br>
     * It is an argument to calculate TAPOS from the last irreversible block rather than blocks behind the head block
     * @return useLastIrreversible whether to use the last irreversible block for calculating TAPOS
     */
    public boolean getUseLastIrreversible() { return useLastIrreversible; }

    /**
     * Sets useLastIrreversible.
     * <br>
     * It is an argument to calculate TAPOS from the last irreversible block rather than blocks behind the head block
     * @param useLastIrreversible whether to use the last irreversible block
     */
    public void setUseLastIrreversible(boolean useLastIrreversible) {
        this.useLastIrreversible = useLastIrreversible;
    }
}
