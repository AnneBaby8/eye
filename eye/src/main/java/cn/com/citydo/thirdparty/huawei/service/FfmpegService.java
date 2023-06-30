package cn.com.citydo.thirdparty.huawei.service;

import java.io.IOException;

public interface FfmpegService {

    /**
     * 执行Shell脚本
     * @param host
     * @param username
     * @param password
     * @param cmd
     * @param port
     * @return
     */
    String execShellScript(String host, String username,
                                         String password,
                                         String cmd, int port) throws IOException;


}
