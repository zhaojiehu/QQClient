package App;

import com.QQServer.Server.Massage;
import com.QQServer.Server.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * 负责网络传输
 */
public class ClientService {
    private User user = new User();//传输的用户类
    private Socket socket;//网络
    public boolean CreateConnect(String ID,String name,String password,View view){
        boolean b = false;//返回变量.
        try {
            user.setID(ID);
            user.setName(name);
            user.setPassword(password);
            socket = new Socket(InetAddress.getLocalHost(), 9999);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(user);//发送一个对象给服务端
            //得到还送的对象:
            ObjectInputStream iis = new ObjectInputStream(socket.getInputStream());
            Massage massage = (Massage) iis.readObject();
            view.setMassage(massage);
            if(massage.getMesType().equals(Massage.MESSAGE_LOCAL_SUCCESS) ||
            massage.getMesType().equals(Massage.MESSAGE_CREATE_SUCCESS)){
                //启动线程维护socket
                ClientSendServiceThread CSST = new ClientSendServiceThread(socket);
                CSST.setUserID(user.getID());
                CSST.start();
                //为了方便管理线程,和扩展功能,把线程添加到集合中去.
                ManageClientSentServiceThread.AddManageClientSentServiceThread(ID,CSST);
                return true;
            }else{
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }
    //获取用户在线列表:
    public static void getOnlineFriend(String userID){
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(
                    ManageClientSentServiceThread.getClientSentServiceThread(userID)
                            .getSocket().getOutputStream());
            Massage massage = new Massage();
            massage.setMesType(Massage.MESSAGE_GET_ONLINE_FRIEND);
            objectOutputStream.writeObject(massage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //finally {
//            try {
//                objectOutputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }
    //退出程序:
    public static void Exit(String userID){
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream
                    (ManageClientSentServiceThread.getClientSentServiceThread(userID).
                            getSocket().getOutputStream());
            Massage massage = new Massage();
            massage.setMesType(Massage.MESSAGE_LOCAL_DISCONNECT);
            massage.setUser(userID);
            objectOutputStream.writeObject(massage);
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //私发信息:

    /**
     * 注意!!!
     * @param user/表示当前用户.
     * @param Contact/表示当前用户要联系的用户.
     * @param massage/表示联系内容.
     */
    public static void SendPrivateMassage(String user,String Contact,String massage){
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                    ManageClientSentServiceThread.getClientSentServiceThread(user).
                            getSocket().getOutputStream());
            Massage massage1 = new Massage();
            massage1.setUser(user);
            massage1.setServer(Contact);
            massage1.setContent(massage);
            massage1.setMesType(Massage.MESSAGE_PRIVATE_WECHAT);
            objectOutputStream.writeObject(massage1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //群发信息:
    public static void SendPublicMassage(String userID,String massage){
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                    ManageClientSentServiceThread.getClientSentServiceThread(userID).
                            getSocket().getOutputStream());
            Massage massage1 = new Massage();
            massage1.setUser(userID);
            massage1.setContent(massage);
            massage1.setMesType(Massage.MESSAGE_PUBLIC_WECHAT);
            objectOutputStream.writeObject(massage1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //发送文件:
    public static void SendFile(String user,String server,String file){
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream
                    (ManageClientSentServiceThread.getClientSentServiceThread(user).
                            getSocket().getOutputStream());
            ManageClientSentServiceThread.getClientSentServiceThread(user).setFile(file);
            Massage massage = new Massage();
            massage.setUser(user);
            massage.setServer(server);
            massage.setMesType(Massage.MESSAGE_SEND_FILE);
            massage.setContent(file);
            objectOutputStream.writeObject(massage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
