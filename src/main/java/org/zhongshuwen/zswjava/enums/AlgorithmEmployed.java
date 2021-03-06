package org.zhongshuwen.zswjava.enums;

/**
 * Enum of supported algorithms which are employed in zsw-chain-java library
 */
public enum AlgorithmEmployed {
    /**
     * Supported SECP256r1 (prime256v1) algorithm curve
     */
    SECP256R1("secp256r1"),

    /**
     * Supported SECP256k1 algorithm curve
     */
    SECP256K1("secp256k1"),

    /**
     * Supported sm2p256v1 algorithm curve
     */
    SM2P256V1("sm2p256v1"),

    /**
     * Supported prime256v1 algorithm curve
     */
    PRIME256V1("prime256v1");

    private String str;

    /**
     * Initialize AlgorithmEmployed enum object with a String value
     * @param str - input String value of enums in AlgorithmEmployed
     */
    AlgorithmEmployed(String str) {
        this.str = str;
    }

    /**
     * Gets string value of AlgorithmEmployed's enum
     * @return string value of AlgorithmEmployed's enum
     */
    public String getString() {
        return str;
    }
}
