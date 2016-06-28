package com.smart.model.doctor;

import com.smart.model.lis.Process;
import com.smart.model.lis.TestResult;
import  com.smart.model.lis.Sample;

/**
 * Title: .IntelliJ IDEA
 * Description:
 *
 * @Author:zhou
 * @Date:2016/6/27 11:30
 * @Version:
 */
public class SampleAndResultVo {
    private Sample sample;          //标本信息
    private Process process;        //时间信息
    private TestResult testResult;  //结果信息
    
    public SampleAndResultVo(Sample s, Process p, TestResult t) {
    	this.sample = s;
    	this.process = p;
    	this.testResult = t;
    }
    
    public Sample getSample() {
        return sample;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public TestResult getTestResult() {
        return testResult;
    }

    public void setTestResult(TestResult testResult) {
        this.testResult = testResult;
    }




}
