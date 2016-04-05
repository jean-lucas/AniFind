package se3a04.anifind;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 2016-04-04.
 */
public class Encryption {



    protected static List<String> Encrypt(List<String> encrypList){
        List<String> newEncryptList = new ArrayList<String>();
        if(encrypList.isEmpty()== false){
            for(int i=0; i < encrypList.size(); i++){
                String nowEncrypted = "";
                String encryptString = encrypList.get(i);
                for(int j=0; j<encryptString.length(); j++){
                    int a = encryptString.codePointAt(j);
                    int c = a+4;
                    nowEncrypted = nowEncrypted + (char) c;
                }
                newEncryptList.add(nowEncrypted);
            }
        }
        return newEncryptList;
    }

    protected static List<String> Dencrypt(List<String> encrypList){
        List<String> newEncryptList = new ArrayList<String>();
        if(encrypList.isEmpty()== false){
            for(int i=0; i < encrypList.size(); i++){
                String nowEncrypted = "";
                String encryptString = encrypList.get(i);
                for(int j=0; j<encryptString.length(); j++){
                    int a = encryptString.codePointAt(j);
                    int c = a-4;
                    nowEncrypted = nowEncrypted + (char) c;
                }
                newEncryptList.add(nowEncrypted);
            }
        }
        return newEncryptList;
    }

}
