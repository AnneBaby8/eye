package cn.com.citydo.utils;


import cn.com.citydo.common.BaseException;
import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * 文件操作工具类
 * </p>
 *
 * @author
 * @since 2021-06-24
 */
@Slf4j
public class FileOperateUtil extends FileUtil {

    /**
     * 将Base64数据保存为图片文件
     *
     * @param base64ImageStr Base64图片数据字符串
     * @param targetFile     要保存的图片目标文件对象
     */
    public static void saveBase64StringToImageFile(String base64ImageStr, File targetFile) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] origin = decoder.decodeBuffer(base64ImageStr);
        InputStream in = new ByteArrayInputStream(origin); // 将b作为输入流；
        FileOperateUtil.writeFromStream(in, targetFile);
    }

    /**
     * 上传图片到服务器
     */
    public static String upload(MultipartFile file, String uploadPath,String pathData) {
        if (file.isEmpty()) {
            return "文件夹为空";
        }
        // 获取文件大小
        long fileSize = file.getSize();
        log.info("文件大小：" + fileSize);

        //获取文件名
        String filename = file.getOriginalFilename();
        log.info("上传的文件名：" + filename);

        // 确定上传的文件的扩展名
        String suffix = "";
        int beginIndex = filename.lastIndexOf(".");
        if (beginIndex != -1) {
            suffix = filename.substring(beginIndex);
        }

        // 设置图片保存名称
        String prefix = filename.substring(0, beginIndex);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        filename = prefix + sdf.format(date) + suffix;
        log.info("================================上传的路径："+uploadPath);
        File dest = new File(uploadPath, filename);

        //检测文件是否存在
        if (dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();//创建文件夹
        }
        try {
            file.transferTo(dest);
            return pathData+filename;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 下载文件
     */
    public static void downloadFiles(HttpServletRequest request, HttpServletResponse response, String path) {
        /*String fileName = filePath.substring(filePath.lastIndexOf("/")+1);
        if (fileName.isEmpty()){
            return "文件名不能为空";
        }
        //设置文件路径
        //ClassPathResource pathResource = new ClassPathResource(filePath + fileName);

        ClassPathResource pathResource = new ClassPathResource("D:\\图片\\aaa20210630111458.jpg");
        File file = null;
        try {
            file = pathResource.getFile();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return "文件不存在！";
        }
        response.setHeader("content-type","application/octet-stream");
        //设置强制下载不打开
        response.setContentType("application/force-download");
        //设置文件名
        response.addHeader("Content-Disposition","attachment;fileName="+fileName);

        byte[] buffer = new byte[1024];
        InputStream fis = null;
        BufferedInputStream bis = null;

        try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            ServletOutputStream stream = response.getOutputStream();
            int read = bis.read(buffer);
            while (read != -1){
                stream.write(buffer,0, read);
                read = bis.read(buffer);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }finally {
            if (bis != null){
                try {
                    bis.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return "文件下载成功";*/


        // path是指欲下载的文件的路径
        if (StringUtils.isEmpty(path)) {
            throw new BaseException(500, "文件地址不能为空");
        }
        File file = new File(path);
        // 取得文件名
        String filename = file.getName();
        // 以流的形式下载文件
        InputStream fis;
        try {
            fis = new BufferedInputStream(new FileInputStream(path));

            byte[] buffer = new byte[fis.available()];

            fis.read(buffer);

            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-disposition", "attachment;filename=" + new String(filename.getBytes()));
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setContentType("application/x-msdownload");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
            System.out.println("下载成功");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 判断文件大小
     *
     * @param file 文件
     * @param size 限制大小
     * @param unit 限制单位（B,K,M,G）
     */
    public static boolean checkFileSize(MultipartFile file, int size, String unit) {
        if (file.isEmpty() || StringUtils.isEmpty(size) || StringUtils.isEmpty(unit)) {
            return false;
        }
        long lent = file.getSize();
        double fileSize = 0;
        if ("B".equals(unit.toUpperCase())) {
            fileSize = (double) lent;
        } else if ("K".equals(unit.toUpperCase())) {
            fileSize = (double) lent / 1024;
        } else if ("M".equals(unit.toUpperCase())) {
            fileSize = (double) lent / 1048576;
        } else if ("G".equals(unit.toUpperCase())) {
            fileSize = (double) lent / 1073741824;
        }
        if (fileSize > size) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        String aa = "/home/citydo/warn";
        String fileName = aa.substring(aa.lastIndexOf("/")+1);
        System.out.println(fileName);
    }

}
