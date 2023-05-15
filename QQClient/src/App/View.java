package App;

import com.QQServer.Server.Key;
import com.QQServer.Server.Massage;
import org.testng.annotations.Test;

/**
 * 界面信息
 *
 */
public class View {
    private boolean loop = true;//控制循环;
    private String ID;//用户ID;
    private String name;//用户名字;
    private String password;//用户密码.
    private Massage massage = null;

    public void setMassage(Massage massage) {
        this.massage = massage;
    }

    public static void main(String[] args) {
        new View().MainView1();
    }
   @Test
    public void MainView1(){
        while (loop){
            System.out.println("==========欢迎登录QQ===========");
            System.out.println("\t\t1 登录界面");
            System.out.println("\t\t9 退出登录");
            System.out.print("请输入你的选择:");
            int a = Key.getInt();
            if(a == 1){
                if(setIDAndName()){
                    System.out.println(massage.getMesType());
                    MainView2();//进入二级界面;
                }else{
                    System.out.println(massage.getMesType());
                }
            }
            if(a == 9){
                loop = false;
                System.out.println("退出登录");
            }
        }
    }
    public void MainView2(){
        while (loop){
            System.out.println("=========QQ二级菜单 用户:"+ID+"==========");
            System.out.println("\t\t1 显示用户在线信息");
            System.out.println("\t\t2 群发信息");
            System.out.println("\t\t3 私发信息");
            System.out.println("\t\t4 发送文件");
            System.out.println("\t\t9 退出登录");
            System.out.print("请输入你的选择:");
            String a = Key.getString();
            switch (a){
                case "1":
                    ClientService.getOnlineFriend(ID);
                    break;
                case "2":
                    setPublicMassage();
                    break;
                case "3":
                    setPrivateMassage();
                    break;
                case "4":
                    setFile();
                    break;
                case "9":
                    loop = false;
                    System.out.println("退出登录");
                    ClientService.Exit(ID);
                    break;
            }


        }
    }
    //读取用户信息:
    public boolean setIDAndName(){
        System.out.println("请输入ID");
        ID = Key.getString();
        System.out.println("请输入名字");
        name = Key.getString();
        System.out.println("请输入密码");
        password = Key.getString();
        ClientService clientService = new ClientService();
        return clientService.CreateConnect(ID, name, password,this);
    }
    //读取用户私发信息内容
    public void setPrivateMassage(){
        System.out.println("请输入联系用户ID:");
        String Contact = Key.getString();
        System.out.println("请输入信息:");
        String massage = Key.getString();
        ClientService.SendPrivateMassage(ID,Contact,massage);
    }
    //读取用户群发信息内容
    public void setPublicMassage(){
        System.out.println("请输入聊天内容");
        String massage = Key.getString();
        ClientService.SendPublicMassage(ID,massage);
    }
    //读取用户文件传输信息
    public void setFile(){
        System.out.println("请输入你要发送的用户的ID:");
        String server = Key.getString();
        System.out.println("请输入文件路径:");
        String file = Key.getString();
        ClientService.SendFile(ID,server,file);
    }
}
