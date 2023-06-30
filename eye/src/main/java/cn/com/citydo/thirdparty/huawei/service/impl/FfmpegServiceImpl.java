package cn.com.citydo.thirdparty.huawei.service.impl;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import cn.com.citydo.thirdparty.huawei.service.FfmpegService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Slf4j
@Service
public class FfmpegServiceImpl implements FfmpegService {

    private Connection getOpenedConnection(String host, String username, String password) throws IOException {
        Connection conn = new Connection(host);
        conn.connect(); // make sure the connection is opened
        boolean isAuthenticated = conn.authenticateWithPassword(username, password);
        if (isAuthenticated == false)
            throw new IOException("Authentication failed.");
        return conn;
    }

    public static void main(String[] args) throws IOException {
        FfmpegServiceImpl i = new FfmpegServiceImpl();
        Connection conn = i.getOpenedConnection("11.64.2.115", "citydo", "EyeEye@123456");
        System.out.println(conn);
    }

    @Override
    public String execShellScript(String host, String username, String password, String cmd, int port) {
        log.info("执行Shell脚本:" + cmd);
        Connection conn = null;
        Session sess = null;
        InputStream stdout = null;
        BufferedReader br = null;
        StringBuffer buffer = new StringBuffer("exec result:");
        buffer.append(System.getProperty("line.separator"));// 换行
        try {
            conn = getOpenedConnection(host, username, password);
            sess = conn.openSession();
            sess.execCommand(cmd);
            stdout = new StreamGobbler(sess.getStdout());
            br = new BufferedReader(new InputStreamReader(stdout));
            while (true) {
                String line = br.readLine();
                if (line == null) {
                    log.error("======转换行[{}]=======",line);
                    break;
                }
                buffer.append(line);
                buffer.append(System.getProperty("line.separator"));// 换行
            }
            log.error("======转换结束=======");
        } catch (Exception e) {
            log.error("视频流转换失败，错误为：[{}][{}]",e.getMessage(), e);
        } finally {
            sess.close();
            conn.close();
        }
        return buffer.toString();
    }

}
