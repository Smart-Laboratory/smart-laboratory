package com.smart.model.lis;

import java.util.Date;

public class TestResult {

	private String sampleNo; //样本编号
    private String testId;  //检验项目id（每个id对应一个确定的检验项目）
    private String testName;
    private String testResult; //检验结果
    private String resultFlag; //标注 检验结果 异常情况
    private int testStatus;
    private String correctFlag;
    private String sampleType; //检验项目类型、来源
    private String ckz; //参考值
    private String refLo; // 参考范围低值
    private String refHi; // 参考范围高值
    private String deviceId; // 设备号
    private Date measureTime; // 检验时间
    private String operator; // 操作者
    private String unit;
    private String chineseName;
    private int isprint;
    private int editMark;
}
