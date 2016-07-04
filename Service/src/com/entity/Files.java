package com.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

/**
 * Created by xfcq on 2016/7/3.
 */
@Entity
public class Files {
    private int fileNo;
    private String fileName;
    private Integer userNo;
    private String fileFormat;
    private Integer fileType;
    private String fileMd5;
    private Integer supFolder;
    private String filePath;
    private String downloadPath;
    private Date creatTime;
    private Integer fileStatus;
    private Integer shareStatus;

    @Id
    @Column(name = "FileNo", nullable = false)
    public int getFileNo() {
        return fileNo;
    }

    public void setFileNo(int fileNo) {
        this.fileNo = fileNo;
    }

    @Basic
    @Column(name = "FileName", nullable = true, length = 100)
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Basic
    @Column(name = "UserNo", nullable = true)
    public Integer getUserNo() {
        return userNo;
    }

    public void setUserNo(Integer userNo) {
        this.userNo = userNo;
    }

    @Basic
    @Column(name = "FileFormat", nullable = true, length = 10)
    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    @Basic
    @Column(name = "FileType", nullable = true)
    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    @Basic
    @Column(name = "FileMD5", nullable = true, length = 40)
    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }

    @Basic
    @Column(name = "SupFolder", nullable = true)
    public Integer getSupFolder() {
        return supFolder;
    }

    public void setSupFolder(Integer supFolder) {
        this.supFolder = supFolder;
    }

    @Basic
    @Column(name = "FilePath", nullable = true, length = 150)
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Basic
    @Column(name = "DownloadPath", nullable = true, length = 150)
    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    @Basic
    @Column(name = "CreatTime", nullable = true)
    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    @Basic
    @Column(name = "FileStatus", nullable = true)
    public Integer getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(Integer fileStatus) {
        this.fileStatus = fileStatus;
    }

    @Basic
    @Column(name = "ShareStatus", nullable = true)
    public Integer getShareStatus() {
        return shareStatus;
    }

    public void setShareStatus(Integer shareStatus) {
        this.shareStatus = shareStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Files files = (Files) o;

        if (fileNo != files.fileNo) return false;
        if (fileName != null ? !fileName.equals(files.fileName) : files.fileName != null) return false;
        if (userNo != null ? !userNo.equals(files.userNo) : files.userNo != null) return false;
        if (fileFormat != null ? !fileFormat.equals(files.fileFormat) : files.fileFormat != null) return false;
        if (fileType != null ? !fileType.equals(files.fileType) : files.fileType != null) return false;
        if (fileMd5 != null ? !fileMd5.equals(files.fileMd5) : files.fileMd5 != null) return false;
        if (supFolder != null ? !supFolder.equals(files.supFolder) : files.supFolder != null) return false;
        if (filePath != null ? !filePath.equals(files.filePath) : files.filePath != null) return false;
        if (downloadPath != null ? !downloadPath.equals(files.downloadPath) : files.downloadPath != null) return false;
        if (creatTime != null ? !creatTime.equals(files.creatTime) : files.creatTime != null) return false;
        if (fileStatus != null ? !fileStatus.equals(files.fileStatus) : files.fileStatus != null) return false;
        if (shareStatus != null ? !shareStatus.equals(files.shareStatus) : files.shareStatus != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = fileNo;
        result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
        result = 31 * result + (userNo != null ? userNo.hashCode() : 0);
        result = 31 * result + (fileFormat != null ? fileFormat.hashCode() : 0);
        result = 31 * result + (fileType != null ? fileType.hashCode() : 0);
        result = 31 * result + (fileMd5 != null ? fileMd5.hashCode() : 0);
        result = 31 * result + (supFolder != null ? supFolder.hashCode() : 0);
        result = 31 * result + (filePath != null ? filePath.hashCode() : 0);
        result = 31 * result + (downloadPath != null ? downloadPath.hashCode() : 0);
        result = 31 * result + (creatTime != null ? creatTime.hashCode() : 0);
        result = 31 * result + (fileStatus != null ? fileStatus.hashCode() : 0);
        result = 31 * result + (shareStatus != null ? shareStatus.hashCode() : 0);
        return result;
    }
}
