package org.zhongshuwen.zswjava.metadata;

import com.google.gson.Gson;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;
import org.zhongshuwen.zswjava.abitypes.ZSWAPIV1;
import org.zhongshuwen.zswjava.implementations.ABIProviderImpl;
import org.zhongshuwen.zswjava.interfaces.IABIProvider;
import org.zhongshuwen.zswjava.models.ZSWItems.ZSWItemsSchemaFieldDefinition;
import org.zhongshuwen.zswjava.models.ZSWName;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

public class ZSWMetadataDeserializerTest {
    //04094d34413420536b696e0522122037b8d00f5a5b37181c8fa8a05f7446b2ea06a0bbaaba3f1e95e4e97726a5e67c06ac0807960408ac080916020aff887a0bc0843d0cff887a0dc0bdf0ff0e0000403f0f01100000000000019040

    @Test
    public void testBasic1() {
        String expectedJson = "{\"name\":\"M4A4 Skin\",\"img\":\"QmS6AaitSdut3Te4fagW6jgfyKL73A1NBSSt3K38vQP9xf\",\"rarity1\":534,\"rarity2\":534,\"rarity3\":\"534\",\"rarity4\":534,\"depth1\":-1000000,\"depth2\":1000000,\"depth3\":\"-1000000\",\"depth4\":4293967296,\"wear\":0.75,\"tradeable\":true,\"share\":1024.25}";

        byte[] inputData = Hex.decode("04094d34413420536b696e0522122037b8d00f5a5b37181c8fa8a05f7446b2ea06a0bbaaba3f1e95e4e97726a5e67c06ac0807960408ac080916020aff887a0bc0843d0cff887a0dc0bdf0ff0e0000403f0f01100000000000019040");
        ZSWItemsSchemaFieldDefinition[] fields = new ZSWItemsSchemaFieldDefinition[]{
                new ZSWItemsSchemaFieldDefinition("name", "string"),
                new ZSWItemsSchemaFieldDefinition("img", "ipfs"),
                new ZSWItemsSchemaFieldDefinition("rarity1", "int16"),
                new ZSWItemsSchemaFieldDefinition("rarity2", "uint16"),
                new ZSWItemsSchemaFieldDefinition("rarity3", "int64"),
                new ZSWItemsSchemaFieldDefinition("rarity4", "fixed16"),
                new ZSWItemsSchemaFieldDefinition("depth1", "int32"),
                new ZSWItemsSchemaFieldDefinition("depth2", "uint32"),
                new ZSWItemsSchemaFieldDefinition("depth3", "int64"),
                new ZSWItemsSchemaFieldDefinition("depth4", "fixed32"),
                new ZSWItemsSchemaFieldDefinition("wear", "float"),
                new ZSWItemsSchemaFieldDefinition("tradeable", "bool"),
                new ZSWItemsSchemaFieldDefinition("share", "double")
        };
        try {
            List<ZSWAPIV1.KVPair<String, ZSWAPIV1.KVPair<String, Object>>> outList = ZSWMetadataDeserializer.decodeMetadata(inputData, fields);
            String outJson = new Gson().toJson(ZSWMetadataDeserializer.GenerateLinkedTreeMapFromKVPairs(outList));
            assertEquals(expectedJson, outJson);
        } catch (Exception ex) {
            fail("ZSWMetadataDeserializerTest should not throw error: " + ex.getLocalizedMessage());
        }
    }
}
