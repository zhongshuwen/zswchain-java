package org.zhongshuwen.zswjava;

import org.junit.Test;
import org.zhongshuwen.zswjava.implementations.ABIProviderImpl;
import org.zhongshuwen.zswjava.implementations.ZswChainRpcProviderImpl;
import org.zhongshuwen.zswjava.models.ZSWItems.ZSWItemsMint;
import org.zhongshuwen.zswjava.models.ZSWItems.ZSWItemsTransfer;
import org.zhongshuwen.zswjava.models.ZSWTokenTransfer;
import org.zhongshuwen.zswjava.models.rpcProvider.Action;
import org.zhongshuwen.zswjava.models.rpcProvider.Authorization;
import org.zhongshuwen.zswjava.models.rpcProvider.TransactionConfig;
import org.zhongshuwen.zswjava.models.rpcProvider.response.SendTransactionResponse;
import org.zhongshuwen.zswjava.session.TransactionProcessor;
import org.zhongshuwen.zswjava.session.TransactionSession;
import org.zhongshuwen.zswjavacrossplatformabi.ZswCoreSerializationProvider;

import java.util.ArrayList;
import java.util.List;

public class ZswFullTest {
    @Test
    public void Test() {

        try {
            System.out.println("Started Example!");
            ZswChainRpcProviderImpl rpcProvider = new ZswChainRpcProviderImpl("http://localhost:3031/");
            ZswCoreSerializationProvider serializationProvider = new ZswCoreSerializationProvider();
            serializationProvider.registerActionType("zswhq.token","transfer", ZSWTokenTransfer.class);
            ABIProviderImpl abiProvider = new ABIProviderImpl(rpcProvider, serializationProvider);
            SoftSM2KeySignatureProviderImpl signatureProvider = new SoftSM2KeySignatureProviderImpl();

            //可以使用https://tools.banquan.sh.cn/zsw-key-generator.html生成测试密钥，改成docker-compose设置的admin密钥
            signatureProvider.importKey("PVT_GM_uvCF2fm8AjG8aoLf7csAdw4fCCYxLdG1SqybJkS6ikBY6xZgv");
            System.out.println("Imported Private Key with Public Key: "+signatureProvider.getAvailableKeys().get(0));

            TransactionSession session = new TransactionSession(
                    serializationProvider,
                    rpcProvider,
                    abiProvider,
                    signatureProvider
            );

            TransactionProcessor processor = session.getTransactionProcessor();

// Now the TransactionConfig can be altered, if desired
            TransactionConfig transactionConfig = processor.getTransactionConfig();

// Use blocksBehind (default 3) the current head block to calculate TAPOS
            transactionConfig.setUseLastIrreversible(false);
// Set the expiration time of transactions 600 seconds later than the timestamp
// of the block used to calculate TAPOS
            transactionConfig.setExpiresSeconds(600);

// Update the TransactionProcessor with the config changes
            processor.setTransactionConfig(transactionConfig);
            String senderName = "zsw.admin";
            String recipientName = "zswblkprod1a";
            String memo = "Hello World!";

            //admin给用户给recipientName移10个不可转移的计算分
            String jsonData = "{\n" +
                    "\"from\": \""+senderName+"\",\n" +
                    "\"to\": \""+recipientName+"\",\n" +
                    "\"quantity\": \"10.0000 ZSWCC\",\n" +
                    "\"memo\" : \""+memo+"\"\n" +
                    "}";

            List<Authorization> authorizations = new ArrayList<>();
            authorizations.add(new Authorization("zsw.admin", "active"));
            List<Action> actions = new ArrayList<>();
            actions.add(new Action("zswhq.token", "transfer", authorizations, jsonData));

            processor.prepare(actions);

            SendTransactionResponse sendTransactionResponse = processor.signAndBroadcast();
            System.out.println(sendTransactionResponse.getTransactionId());
        }catch(Exception e){
            System.err.println("ERROR: "+e.toString());
        }
    }


    @Test
    public void Test2() {

        try {
            System.out.println("Started Example!");
            ZswChainRpcProviderImpl rpcProvider = new ZswChainRpcProviderImpl("http://localhost:3031/");
            ZswCoreSerializationProvider serializationProvider = new ZswCoreSerializationProvider();
            //serializationProvider.registerActionType("zswhq.token","transfer", ZSWTokenTransfer.class);
            ABIProviderImpl abiProvider = new ABIProviderImpl(rpcProvider, serializationProvider);
            SoftSM2KeySignatureProviderImpl signatureProvider = new SoftSM2KeySignatureProviderImpl();

            //可以使用https://tools.banquan.sh.cn/zsw-key-generator.html生成测试密钥，改成docker-compose设置的admin密钥
            signatureProvider.importKey("PVT_GM_2uzgmWfht8ZiKH1wjTV26CP4GKW8BXTXR4zApqcQh8qxNaRssU");
            System.out.println("Imported Private Key with Public Key: "+signatureProvider.getAvailableKeys().get(0));

            TransactionSession session = new TransactionSession(
                    serializationProvider,
                    rpcProvider,
                    abiProvider,
                    signatureProvider
            );
serializationProvider.setUpGSON();
            TransactionProcessor processor = session.getTransactionProcessor();

// Now the TransactionConfig can be altered, if desired
            TransactionConfig transactionConfig = processor.getTransactionConfig();

// Use blocksBehind (default 3) the current head block to calculate TAPOS
            transactionConfig.setUseLastIrreversible(false);
// Set the expiration time of transactions 600 seconds later than the timestamp
// of the block used to calculate TAPOS
            transactionConfig.setExpiresSeconds(600);

// Update the TransactionProcessor with the config changes
            processor.setTransactionConfig(transactionConfig);


            Action mintAction = Action.fromSerializable(
                    "zsw.items",
                    "mint",
                    List.of(new Authorization[]{
                            new Authorization("kxjdzzzz111a", "active"),
                    }),
                    new ZSWItemsMint(
                            "kxjdzzzz111a",
                            "useruser111a",
                            "kxjdzzzz111a",
                            1,
                            new String[]{"29148"},
                            new String[]{"1"},
                            "hi"
                    )
            );
            Action transferAction = Action.fromSerializable(
                    "zsw.items",
                    "transfer",
                    List.of(new Authorization[]{
                            new Authorization("kxjdzzzz111a", "active"),
                    }),
                    new ZSWItemsTransfer(
                            "kxjdzzzz111a",
                            "useruser111a",
                            "kxjdzzzz111a",
                            "useruser111b",
                            "kxjdzzzz111a",
                            1,
                            false,
                            2,
                            new String[]{"29148"},
                            new String[]{"1"},
                            "hi"
                    )
            );
            /*

            //admin给用户给recipientName移10个不可转移的计算分
            String jsonData = "{\n" +
                    "      \"minter\": \"kxjdzzzz111a\",\n" +
                    "      \"to\": \"useruser111a\",\n" +
                    "      \"to_custodian\": \"kxjdzzzz111a\",\n" +
                    "      \"item_ids\": [\"29148\"],\n" +
                    "      \"amounts\": [50],\n" +
                    "      \"memo\": \"test\",\n" +
                    "      \"freeze_time\": 0\n" +
                    "    }";

            List<Authorization> authorizations = new ArrayList<>();
            authorizations.add(new Authorization("kxjdzzzz111a", "active"));
            List<Action> actions = new ArrayList<>();
            actions.add(new Action("zsw.items", "mint", authorizations, jsonData));
*/
            List<Action> actions = new ArrayList<>();
            actions.add(mintAction);
            actions.add(transferAction);
            processor.prepare(actions);

            SendTransactionResponse sendTransactionResponse = processor.signAndBroadcast();
            System.out.println(sendTransactionResponse.getTransactionId());
        }catch(Exception e){
            System.err.println("ERROR: "+e.toString());
        }
    }
}
