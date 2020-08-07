package org.vansoft.karmanyak.solidity;

import android.content.Context;
import android.net.wifi.hotspot2.pps.Credential;
import android.os.Environment;
import android.widget.Toast;

import org.bouncycastle.util.encoders.Hex;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletFile;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;


import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class Solidity {
    public final static String CONTRACT_OWNER_PRIVATE_KEY = "C67C25316F8051E48D477CFC360800BE450EEBC0E0FD623CA194163A835BC2E7";
    public final static String CONTRACT_OWNER_PUBLIC_KEY = "0x34bbc8563f111F874e755b33e76edE97b81688E3";

    private final static BigInteger GAS_LIMIT = BigInteger.valueOf(6721975L);
    private final static BigInteger GAS_PRICE = BigInteger.valueOf(20000000000L);

 //   private final static String RECIPIENT = "0xA9e0f302BcAd4C87823b5f91B5710212B2941Dec";

    //        private final static String CONTRACT_ADDRESS = "0x54ad118b1670b1bbc7d5dec25dc7e7c4daf166f3";
    private final static String CONTRACT_ADDRESS = "0x84f91270886ef0b6d7361b5ce0642004a56ff1b3";

    Web3j web3j = null;
    Context context;

    public Solidity(Context context){

        web3j = Web3j.build(new HttpService("https://rinkeby.infura.io/v3/73da972d7aab4015814cfacbc196fc77"));
        this.context = context;

    }

    public MyCoin initMyCoin(Credentials credentials){
        MyCoin myCoin = loadContract(CONTRACT_ADDRESS, web3j, credentials);
        return myCoin;
    }
    public String ReedemByUser(Credentials credentials,int reedemAmount){
        //String USER_CONTRACT_PRIVATE_KEY = getPrivateKeyFromCredentials(credentials);
        MyCoin myCoin = loadContract(CONTRACT_ADDRESS, web3j, credentials);
        try {
            TransactionReceipt callBack = myCoin.transfer(CONTRACT_OWNER_PUBLIC_KEY, BigInteger.valueOf(reedemAmount)).send();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return "success";
    }
    public String RewardUser(final String recipentAddress,int reward) {
        Credentials credentials = getCredentialsFromPrivateKey();
        MyCoin myCoin = loadContract(CONTRACT_ADDRESS, web3j, credentials);
        try {
            TransactionReceipt callBack = myCoin.transfer(recipentAddress, BigInteger.valueOf(reward)).send();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return "success";
    }

    public Credentials getCredentialsFromWallet(String password,String source) {
        try {
            return WalletUtils.loadCredentials(
                    password,
                    source
            );
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context,"IOException "+e.getMessage(),Toast.LENGTH_LONG).show();

        } catch (CipherException e) {
            e.printStackTrace();
            Toast.makeText(context,"CipherException "+e.getMessage(),Toast.LENGTH_LONG).show();

        }
        return null;
    }

    public String getPrivateKeyFromCredentials(Credentials credentials){
        return String.valueOf(credentials.getEcKeyPair().getPrivateKey());
    }

    public String getBalance(String s){
        MyCoin coin =initMyCoin(getCredentialsFromPrivateKey());
        try {
            return String.valueOf(coin.balanceOf(s).send());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String createWallet(String password){
        String fileName = null;
        String path = null;
        try {
//            fileName = WalletUtils.generateNewWalletFile(password, file);

            path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
            fileName = WalletUtils.generateLightNewWalletFile(password, new File(path));

        } catch (CipherException e) {
            e.printStackTrace();
         //   Toast.makeText(context,"Cipher Exception "+e.getMessage(),Toast.LENGTH_LONG).show();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
       //     Toast.makeText(context,"InvalidAlgorithmParameterException Exception "+e.getMessage(),Toast.LENGTH_LONG).show();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
     //       Toast.makeText(context,"NoSuchAlgorithmException Exception "+e.getMessage(),Toast.LENGTH_LONG).show();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
   //         Toast.makeText(context,"NoSuchProviderException Exception "+e.getMessage(),Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            e.printStackTrace();
 //           Toast.makeText(context,"IOException Exception "+e.getMessage(),Toast.LENGTH_LONG).show();

        }
//        Toast.makeText(context,"New Wallet Created as "+fileName+"\nOn: "+path,Toast.LENGTH_LONG).show();
        return path+"/"+fileName;
    }


    private Credentials getCredentialsFromPrivateKey() {
        return Credentials.create(CONTRACT_OWNER_PRIVATE_KEY);
    }


    private String deployContract(Web3j web3j, Credentials credentials) throws Exception {
        return MyCoin.deploy(web3j, credentials, GAS_PRICE, GAS_LIMIT)
                .send()
                .getContractAddress();
    }

    public String getPublicKeyFromCredential(Credentials credentials){
        return credentials.getAddress();
    }

    private MyCoin loadContract(String contractAddress, Web3j web3j, Credentials credentials) {
        return MyCoin.load(contractAddress, web3j, credentials, GAS_PRICE, GAS_LIMIT);
    }

//    private void transferEthereum(Web3j web3j, Credentials credentials) throws Exception {
//        TransactionManager transactionManager = new RawTransactionManager(
//                web3j,
//                credentials
//        );
//
//        Transfer transfer = new Transfer(web3j, transactionManager);
//
//        TransactionReceipt transactionReceipt = transfer.sendFunds(
//                RECIPIENT,
//                BigDecimal.ONE,
//                Convert.Unit.ETHER,
//                GAS_PRICE,
//                GAS_LIMIT
//        ).send();
//
//        System.out.print("Transaction = " + transactionReceipt.getTransactionHash());
//    }

}