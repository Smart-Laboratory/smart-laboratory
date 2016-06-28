package com.smart.service.impl.doctor;

import com.smart.dao.doctor.DoctorQueryDao;
import com.smart.model.doctor.LeftVo;
import com.smart.model.doctor.SampleAndResultVo;
import com.smart.model.lis.Sample;
import com.smart.service.doctor.DoctorQueryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Title: .IntelliJ IDEA
 * Description:
 *
 * @Author:zhou
 * @Date:2016/6/22 12:01
 * @Version:
 */

@Service("doctorQueryManager")
public class DoctorQueryManagerImpl implements DoctorQueryManager {
    private DoctorQueryDao doctorQueryDao;

    @Autowired
    public void setArrangeDao(DoctorQueryDao doctorQueryDao) {
        this.doctorQueryDao = doctorQueryDao;
    }

    /**
     * 获取报告单列表
     * @param query         查询值
     * @param type          类型 1:病历号 2:姓名 3:医嘱号
     * @param fromDate      起始时间 2016-06-22
     * @param toDate        结束时间
     * @return
     */
    public List<LeftVo> getReportList(String query, int type, String fromDate, String toDate) {
        return doctorQueryDao.getReportList(query,type,fromDate,toDate);
    }


    /**
     * 根据病历号查询样本信息
     * @param patientBlh    病历号
     * @param fromDate      开始日期
     * @return
     */
    public Sample getSampleByPatientBlh(String patientBlh, String fromDate) {
        return doctorQueryDao.getSampleByPatientBlh(patientBlh,fromDate);
    }

    public List<Object[]> getSampleAndResult(String patientBlh,String fromDate,String nowDate) {
        return doctorQueryDao.getSampleAndResult(patientBlh,fromDate,nowDate);
    }


}
