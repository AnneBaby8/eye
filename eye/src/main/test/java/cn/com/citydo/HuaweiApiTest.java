package cn.com.citydo;


import cn.com.citydo.common.BaseException;
import cn.com.citydo.common.EyeStatus;
import cn.com.citydo.module.screen.service.HuaweiService;
import cn.com.citydo.thirdparty.huawei.response.login.LoginResponse;
import cn.com.citydo.thirdparty.huawei.service.FfmpegService;
import com.github.kevinsawicki.http.HttpRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;

@SpringBootTest
@RunWith(SpringRunner.class)
public class HuaweiApiTest {

    @Autowired
    private HuaweiService huaweiService;

    @Autowired
    private FfmpegService ffmpegService;

    int SUCCESS = 200;
    private final String RESULTCODE = "resultCode";
    private String url = "https://111.33.175.77:18447";
    private final String contentType = "application/x-www-form-urlencoded";
//    private String loginUrl = url + "/sdk_service/rest/users/login/v1.1?account=connect&pwd=Huawei@123";
    private String loginUrl = "https://111.33.175.77:18447/sdk_service/rest/users/login/v1.1?account=test02&pwd=huawei@123";

    @Test
    public void loginTest1() {
        String cookie = huaweiService.login();
        System.out.println("Cookie: " + cookie);
    }


    @Test
    public void getDeviceListTest() {
        String cookie = huaweiService.login();
        HttpRequest videoRequest = HttpRequest.post("https://111.33.175.77:18531/device/deviceList/v1.0").contentType(contentType)
                .connectTimeout(3000).readTimeout(5000);
        videoRequest.header("Cookie", cookie);
        videoRequest.trustAllCerts();
        videoRequest.trustAllHosts();
        String body = videoRequest.body();
        System.out.println("Body: " + body);
    }

    @Test
    public void loginTest() {
        HttpRequest request = HttpRequest.post(loginUrl).contentType(contentType).connectTimeout(3000).readTimeout(5000);
        request.trustAllCerts();
        request.trustAllHosts();
        String body = request.body();
        if (request.code() != SUCCESS) {
            throw new BaseException(EyeStatus.LOGIN);
        }

        try {
            JAXBContext context = JAXBContext.newInstance(LoginResponse.class);
            Unmarshaller unMar = context.createUnmarshaller();
            LoginResponse response = (LoginResponse) unMar.unmarshal(new StringReader(body));
        } catch (JAXBException e) {
            e.printStackTrace();
        }

//        JSONObject response = new JSONObject(body);
//        int resultCode = response.getInt(RESULTCODE);
//        if (ZERO != resultCode) {
//            throw new BaseException(resultCode, "登陆请求失败");
//        }
        String cookie = request.header("Set-Cookie");
        System.out.println("cookie: " + cookie);

    }


    public String login() {
        HttpRequest request = HttpRequest.post(loginUrl).contentType(contentType).connectTimeout(3000).readTimeout(5000);
        request.trustAllCerts();
        request.trustAllHosts();
        String body = request.body();
        if (request.code() != SUCCESS) {
            throw new BaseException(EyeStatus.LOGIN);
        }

        try {
            JAXBContext context = JAXBContext.newInstance(LoginResponse.class);
            Unmarshaller unMar = context.createUnmarshaller();
            LoginResponse response = (LoginResponse) unMar.unmarshal(new StringReader(body));
        } catch (JAXBException e) {
            e.printStackTrace();
        }

//        JSONObject response = new JSONObject(body);
//        int resultCode = response.getInt(RESULTCODE);
//        if (ZERO != resultCode) {
//            throw new BaseException(resultCode, "登陆请求失败");
//        }
        String cookie = request.header("Set-Cookie");
        System.out.println("cookie: " + cookie);
        return cookie;
    }


    @Test
    public void queryPeople() {

        String cookie = login();

        HttpRequest httpRequest = new HttpRequest("https://111.33.175.77:18447/sdk_service/rest/facerepositories/peoples?ids=60a4869220158600ce32d842&page=1&no=1&size=10&sort=asc&orderName=time", "POST");
        httpRequest.contentType("application/xml", "UTF-8");
        httpRequest.header("Cookie", cookie);
        httpRequest.trustAllCerts();
        httpRequest.trustAllHosts();

//        FaceRepositoriesPeopleParam param = new FaceRepositoriesPeopleParam();
//        param.setIds("123");
//        param.setRepositorIds("123");

//        JSONObject jsonObject = new JSONObject();
//        jsonObject.putIfAbsent("repositorIds", "123");
//        jsonObject.putIfAbsent("ids", "123");

        // 将请求体信息放入send中
        //httpRequest.send(jsonObject.toString());

        System.out.println("----------------------------------");
        System.out.println(httpRequest.body());
        System.out.println("----------------------------------");


    }


    @Test
    public void FfmpegTest() throws IOException {
        //"05437430813607450101#c3bff537b2544782ae8f292c9007a9a6"
        String[] cameraArr = new String[]{
                "05437430819740530101#c3bff537b2544782ae8f292c9007a9a6"
        };
        String cookie = huaweiService.login();
        for(String cameraCode : cameraArr){
            try {
              /*  String rtspUrl = huaweiService.getRtspUrl(cameraCode, cookie);
                System.out.println("rtspUrl(" + cameraCode + ")：\n" + rtspUrl);
                //rtspUrl = rtspUrl.replaceAll("192.168.255.2", "111.33.175.77");
                //System.out.println("调整后的rtspUrl：\n" + rtspUrl);
                rtspUrl = rtspUrl.replaceAll("111.33.175.77", "172.30.1.229");
                System.out.println("调整后的rtspUrl：\n" + rtspUrl);
                String cmd = "ffmpeg -rtsp_transport tcp -y -i \"" + rtspUrl + "\" -ss 00:00:01 -vframes 1 -s 1280x720 -f image2 -vcodec png /Users/gelu/Documents/Documents/image-3.png;echo end";
                System.out.println("cmd : " + cmd);
                String result = ffmpegService.execShellScript("11.64.2.115", "citydo", "City@123456", cmd, 22);
                System.out.println("-----" + result);*/
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        System.out.println("--------- end ----------");
    }


    @Test
    public void test() {
        try {
            HttpRequest request = HttpRequest.post(loginUrl).contentType(contentType).connectTimeout(3000).readTimeout(5000);
            request.trustAllCerts();
            request.trustAllHosts();
            String body = request.body();
            if (request.code() != SUCCESS) {
                throw new BaseException(EyeStatus.LOGIN);
            }
            JAXBContext context = JAXBContext.newInstance(LoginResponse.class);
            Unmarshaller unMar = context.createUnmarshaller();
            LoginResponse response = (LoginResponse) unMar.unmarshal(new StringReader(body));
            String cookie = request.header("Set-Cookie");
            System.out.println("cookie: " + cookie);

            HttpRequest httpRequest = new HttpRequest("https://111.33.175.77:18447/sdk_service/rest/video-analysis/search-suspect-task/v1.5", "POST");
            httpRequest.contentType("application/xml", "UTF-8");
            httpRequest.header("Cookie", cookie);
            httpRequest.trustAllCerts();
            httpRequest.trustAllHosts();

            System.out.println("----------------------------------");
            System.out.println(httpRequest.body());
            System.out.println("----------------------------------");
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void sendAliveConnect() {
//        String result = "";
//        try {
//            URL httpurl = new URL("https://111.33.175.77:1191/AlarmServer/resource/handle/pushAlarmToClient?token=63185573-8d51-49aa-9fec-428cb7aedbd2");
//            HttpURLConnection httpConn = (HttpURLConnection) httpurl.openConnection();
//            httpConn.setDoOutput(true);
//            httpConn.setDoInput(true);
//
//
//            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");//第一个参数为 返回实现指定安全套接字协议的SSLContext对象。第二个为提供者
//            TrustManager[] tm = {new MyX509TrustManager()};
//            sslContext.init(null, tm, new SecureRandom());
//            SSLSocketFactory ssf = sslContext.getSocketFactory();
//
//            PrintWriter out = new PrintWriter(httpConn.getOutputStream());
//            //out.print(param);
//            out.flush();
//            out.close();
//            BufferedReader in = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
//            String line;
//            while ((line = in.readLine()) != null) {
//                result += line;
//            }
//            System.out.println("result: " + result);
//            in.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("没有结果！" + e);
//        }


        try {
            HttpRequest httpRequest = new HttpRequest("https://111.33.175.77:1191/AlarmServer/resource/handle/pushAlarmToClient?token=209c384f-182b-4a5b-afcb-675d6afd6032", "POST");
            httpRequest.contentType("application/xml", "UTF-8");
            httpRequest.trustAllCerts();
            httpRequest.trustAllHosts();

            System.out.println("----------------------------------");
            OutputStreamWriter writer = httpRequest.writer();
            writer.flush();

            BufferedReader in = httpRequest.bufferedReader();
            String line;
            String result = "";
            while ((line = in.readLine()) != null) {
                result += line;
            }
            System.out.println("result: " + result);
            in.close();
            System.out.println("----------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
