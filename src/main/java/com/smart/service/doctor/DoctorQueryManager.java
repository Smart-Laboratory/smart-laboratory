package com.smart.service.doctor;

import com.smart.model.doctor.LeftVo;
import com.smart.model.doctor.SampleAndResultVo;
import com.smart.model.lis.Sample;

import java.util.List;
import java.util.Map;

/**
 * Title: DoctorQueryManager
 * Description:医生工作站报告单查询
 *
 * @Author:zhou
 * @Date:2016/6/22 12:00
 * @Version:
 */
public interface DoctorQueryManager  {

    /**
     * 获取报告单列表
     * @param query         查询值
     * @param type          类型 1:病历号 2:姓名 3:医嘱号
     * @param fromDate      起始时间 2016-06-22
     * @param toDate        结束时间
     * @return
     */
    List<LeftVo> getReportList(String query, int type, String fromDate, String toDate);

    /**
     * 根据病历号查询样本信息
     * @param patientBlh    病历号
     * @param fromDate      开始日期
     * @return
     */
    Sample getSampleByPatientBlh(String patientBlh, String fromDate);

    List<Object[]> getSampleAndResult(String patientBlh,String fromDate,String nowDate);
}
