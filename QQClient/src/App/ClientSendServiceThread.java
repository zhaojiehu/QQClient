package App;


import com.QQServer.Server.Key;
import com.QQServer.Server.Massage;
import com.QQServer.Server.mesType;
import org.yaml.snakeyaml.events.Event;

import java.io.*;
import java.net.Socket;

/**
 * 维护socket线程类
 */
public class ClientSendServiceThread extends Thread {
    private Socket socket;
    private String userID;
    private String file;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public ClientSendServiceThread(Socket socket) {
        this.socket = socket;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("socket正在被维护");
        try {
            while (true) {
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                Massage massage  = (Massage) objectInputStream.readObject();
                if (massage.getMesType().equals(Massage.MESSAGE__ONLINE_FRIEND_LIST)) {
                    String[] s = massage.getContent().split(" ");
                    System.out.println("");
                    for (int i = 0; i < s.length; i++) {
                        System.out.println("用户: "+ s[i]);
                    }
                }
                if(massage.getMesType().equals(Massage.MESSAGE_PRIVATE_WECHAT)){
                    System.out.println("用户"+massage.getUser()+"对你说:"+massage.getContent());
                }
                if(massage.getMesType().equals(Massage.MESSAGE_PRIVATE_SENT_SUCCESS)){
                    System.out.println("发送成功");
                }
                if(massage.getMesType().equals(Massage.MESSAGE_PRIVATE_SENT_FALSE)){
                    System.out.println("用户下线或不存在");
                }
                if(massage.getMesType().equals(Massage.MESSAGE_PUBLIC_WECHAT)){
                    System.out.println(massage.getUser()+"对你说:"+massage.getContent());
                }
                //确认是否接收对方发来的文件:
                if(massage.getMesType().equals(Massage.MESSAGE_SEND_FILE)){
                    System.out.println("用户:"+massage.getUser()+"给你发文件:"+massage.getContent()+
                            "是(10)否(0)接收?");
                    String key = Key.getString();
                    if(key.equals("10")){
                        System.out.println("请输入你要存放的文件路径:");
                        String file = Key.getString();
                        Massage massage1 = new Massage();
                        massage1.setContent(file);
                        massage1.setServer(massage.getUser());
                        massage1.setUser(userID);
                        massage1.setMesType(Massage.MESSAGE_SET_FILE_YES);
                        ObjectOutputStream objectOutputStream = new
                                ObjectOutputStream(socket.getOutputStream());
                        objectOutputStream.writeObject(massage1);
                        ObjectInputStream objectInputStream1 = new ObjectInputStream(socket.getInputStream());
                        Massage massage2 = (Massage) objectInputStream1.readObject();
                        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream
                                (new FileOutputStream(file));
                        byte[] bytesFile = massage2.getBytesFile();
                        bufferedOutputStream.write(bytesFile,0, bytesFile.length);
                        bufferedOutputStream.close();
                        System.out.println("传输完毕");
                    }else{
                        Massage massage1 = new Massage();
                        massage1.setMesType(Massage.MESSAGE_SET_FILE_NO);
                        massage1.setServer(massage.getUser());
                        ObjectOutputStream objectOutputStream = new
                                ObjectOutputStream(socket.getOutputStream());
                        objectOutputStream.writeObject(massage1);
                    }
                }
                if(massage.getMesType().equals(Massage.MESSAGE_SET_FILE_NO)){
                    System.out.println("对方拒收你的文件");
                }
                if(massage.getMesType().equals(Massage.MESSAGE_SET_FILE_YES)){
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
                    byte[] bytes = new byte[1024];
                    int readLine = 0;
                    while ((readLine = bufferedInputStream.read(bytes)) != -1){
                        byteArrayOutputStream.write(bytes,0,readLine);
                    }
                    massage.setBytesFile(byteArrayOutputStream.toByteArray());
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    massage.setMesType(Massage.MESSAGE_REAL_SEND_FILE);
                    objectOutputStream.writeObject(massage);
                    byteArrayOutputStream.close();
                    bufferedInputStream.close();
                    System.out.println("传输完毕");
                }
                if(massage.getMesType().equals(Massage.MESSAGE_NEWS)){
                    System.out.println("新闻:"+massage.getContent());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//                try {
//                    ObjectOutputStream objectOutputStream =
//                            new ObjectOutputStream(socket.getOutputStream());
//                    Massage massage = new Massage();
//                    massage.setMesType(Massage.MESSAGE_LOCAL_DISCONNECT);
//                    objectOutputStream.writeObject(massage);
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            try {
                ManageClientSentServiceThread.DeleteClientSentServerThread(userID);
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
