package cn.com.citydo.utils;

import cn.com.citydo.common.BaseException;
import cn.com.citydo.common.Status;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

/**
 * <p>
 *  图片公共方法
 * </p>
 *
 * @author wx227336
 * @Date 2021/8/19 14:16
 * @Version 1.0
 */
@Service
@Slf4j
public class ImageHandleUtils {
    /**
     * 获取图片名称
     * @param taskId
     * @return
     */
    public static String getImageName(String taskId) {
        StringBuilder sb = new StringBuilder();
        sb.append(taskId);
        sb.append("-");
        sb.append(DateUtil.format(new Date(), DatePattern.PURE_DATETIME_FORMAT));
        sb.append(".jpeg");
        return sb.toString();
    }

    //java 通过url下载图片保存到本地  urlString 图片链接地址  imgName 图片名称
    public static void download(String path,String urlString, String imgName) {
        log.info(urlString);
        try {
            // 构造URL
            URL url = new URL(urlString);
            log.info("打开连接");
            URLConnection con = url.openConnection();
            con.setConnectTimeout(30*1000);
            log.info("创建输入流");
            InputStream is = con.getInputStream();
            // 1K的数据缓冲
            log.info("1K的数据缓冲");
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流
            String filename = path + imgName;
            log.info("创建文件");
            File file = new File(filename);
            log.info("创建输出流");
            FileOutputStream os = new FileOutputStream(file, true);
            log.info("开始读取");
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }

            log.info(filename);
            log.info("文件上传完成");
            os.close();
            is.close();
        }catch (Exception e) {
            log.info("图片上传失败");
            e.printStackTrace();
            throw new BaseException(Status.ERROR.getCode(),"图片保存失败");
        }
    }
}