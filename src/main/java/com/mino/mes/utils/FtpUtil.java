package com.mino.mes.utils;


import com.mino.mes.vo.ElecImg;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.SocketException;


@Component
public class FtpUtil {

    @Value("${ftp.host}")
    String host;
    @Value("${ftp.port}")
    Integer port;
    @Value("${ftp.password}")
    String password;
    @Value("${ftp.user}")
    String user;
    @Value("/")
    String rootDir;

    /**
     * 获取FTPClient对象
     *
     * @return
     */
    public FTPClient getFTPClient() throws MesException {
        FTPClient ftpClient;
        try {
            ftpClient = new FTPClient();
            ftpClient.connect(host, port);// 连接FTP服务器
            ftpClient.login(user, password);// 登陆FTP服务器
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                ftpClient.disconnect();
                throw new MesException("未连接到FTP，用户名或密码错误。", 400);
            }
        } catch (IOException e) {
            throw new MesException("连接失败:" + e.getMessage(), 500);
        }
        return ftpClient;
    }

    /*
     * 从FTP服务器下载文件
     *
     * @param ftpPath FTP服务器中文件所在路径 格式： ftptest/aa
     * @param localPath 下载到本地的位置 格式：H:/download
     * @param fileName 文件名称
     */
    public ElecImg downloadFtpFile(String ftpPath, String localPath,
                                   String fileName) throws MesException {
        FTPClient ftpClient = null;
        String parentDir;
        String ftpFileName;
        if (ftpPath.indexOf("/") > 0) {
            parentDir = ftpPath.substring(0, ftpPath.lastIndexOf("/"));
            ftpFileName = ftpPath.substring(ftpPath.lastIndexOf("/") + 1);
        } else if (ftpPath.indexOf("\\") > 0) {
            parentDir = ftpPath.substring(0, ftpPath.lastIndexOf("\\"));
            ftpFileName = ftpPath.substring(ftpPath.lastIndexOf("\\") + 1);
        } else {
            parentDir = "/";
            ftpFileName = ftpPath;
        }
        try {
            ftpClient = getFTPClient();
            ftpClient.setControlEncoding("UTF-8"); // 中文支持
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(parentDir);
            if (!new File(localPath).exists()) {
                new File(localPath).mkdir();
            }
            String localFilePath = localPath + File.separatorChar + fileName;
            File localFile = new File(localFilePath);
            OutputStream os = new FileOutputStream(localFile);
            boolean exists = ftpClient.retrieveFile(ftpFileName, os);
            if(!exists){
                throw new MesException("没有找到" + ftpPath + "图纸", 404);
            }
            os.close();
            ftpClient.logout();
            return new ElecImg(localFilePath, ftpFileName);
        } catch (FileNotFoundException e) {
            throw new MesException("没有找到" + ftpPath + "图纸", 404);
        } catch (SocketException e) {
            throw new MesException("连接FTP失败", 500);
        } catch (IOException e) {
            throw new MesException("文件读取错误", 500);
        } catch (MesException e) {
            throw new MesException(e.getMsg(), e.getCode());
        }
    }


}