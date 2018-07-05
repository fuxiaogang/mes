package com.mino.mes.vo;

/**
 * @author fuxg
 * @create 2018-05-28 12:54
 **/
public class ElecImg {

    /**
     * 本地图纸路径
     */
    String outPath;

    /**
     * 原始文件名
     */
    String fileName;

    /**
     * ftp文件路径
     */
    String remoteFilePath;

    public ElecImg(String outPath, String fileName) {
        this.outPath = outPath;
        this.fileName = fileName;
    }

    public ElecImg(String outPath) {
        this.outPath = outPath;
    }

    public String getOutPath() {
        return outPath;
    }

    public void setOutPath(String outPath) {
        this.outPath = outPath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRemoteFilePath() {
        return remoteFilePath;
    }

    public void setRemoteFilePath(String remoteFilePath) {
        this.remoteFilePath = remoteFilePath;
    }
}
