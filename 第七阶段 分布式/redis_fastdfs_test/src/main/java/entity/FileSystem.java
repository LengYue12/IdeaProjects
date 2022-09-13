package entity;

import java.io.Serializable;

/**
 * @BelongsProject lagou-imageServer
 * @Author lengy
 * @CreateTime 2022/7/30 20:22
 * @Description 上传的文件
 */
public class FileSystem implements Serializable {

    private String fileId;
    private String filePath;
    private String fileName;

    public FileSystem(String fileId, String filePath, String fileName) {
        this.fileId = fileId;
        this.filePath = filePath;
        this.fileName = fileName;
    }

    public FileSystem() {
    }

    @Override
    public String toString() {
        return "FileSystem{" +
            "fileId='" + fileId + '\'' +
            ", filePath='" + filePath + '\'' +
            ", fileName='" + fileName + '\'' +
            '}';
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
