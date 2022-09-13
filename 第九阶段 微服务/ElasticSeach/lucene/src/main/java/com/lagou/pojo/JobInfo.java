package com.lagou.pojo;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("job_info")
public class JobInfo {

  @TableId
  private long id;
  private String companyName;
  private String companyAddr;
  private String companyInfo;
  private String jobName;
  private String jobAddr;
  private String jobInfo;
  private int salaryMin;
  private int salaryMax;
  private String url;
  private String time;
}
