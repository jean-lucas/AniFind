package se3a04.anifind.Misc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 2016-04-04.
 */
public class Encryption {

    //number of units to shift the encryption
    private static int SHIFT_STEP = 4;


    public static List<String> encrypt(List<String> encryptList) {
        return Encrypt(encryptList, 1);
    }

    public static List<String> decrypt(List<String> decryptList) {
        return Encrypt(decryptList, -1);
    }

    private static List<String> Encrypt(List<String> encryptList, int direction){
        List<String> newEncryptList = new ArrayList<String>();
        if(!encryptList.isEmpty()){
            for(int i=0; i < encryptList.size(); i++){
                String nowEncrypted = "";
                String encryptString = encryptList.get(i);
                for(int j=0; j<encryptString.length(); j++){
                    int a = encryptString.codePointAt(j);
                    int c = a+ (SHIFT_STEP * direction);
                    nowEncrypted = nowEncrypted + (char) c;
                }
                newEncryptList.add(nowEncrypted);
            }
        }
        return newEncryptList;
    }

}
