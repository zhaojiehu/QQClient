package App;

import java.util.*;

/**
 * 线程管理集合
 */
public class ManageClientSentServiceThread {
    //Key 用户id,Value 用户线程集合;
    private static HashMap<String, List<ClientSendServiceThread>> hm = new HashMap<>();
    public static void AddManageClientSentServiceThread(String UserID,ClientSendServiceThread thread){

        if(!(hm.containsKey(UserID))){
            Vector<ClientSendServiceThread> clientSendServiceThreads = new Vector<>();
            clientSendServiceThreads.add(thread);
            hm.put(UserID,clientSendServiceThreads);
        }else{
            List<ClientSendServiceThread> clientSendServiceThreads = hm.get(UserID);
            clientSendServiceThreads.add(thread);
        }
    }
    public static List<ClientSendServiceThread> getClientSentServiceThreads(String UserId){
       return hm.get(UserId);
    }
    public static ClientSendServiceThread getClientSentServiceThread(String user){
        List<ClientSendServiceThread> clientSentServiceThreads = getClientSentServiceThreads(user);
        for (ClientSendServiceThread o :clientSentServiceThreads) {
            return o;
        }
        return null;
    }
    public static void DeleteClientSentServerThread(String userID){
        hm.remove(userID);
    }
}
