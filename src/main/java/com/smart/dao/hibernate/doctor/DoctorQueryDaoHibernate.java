package com.smart.dao.hibernate.doctor;

import com.smart.dao.doctor.DoctorQueryDao;
import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.model.doctor.LeftVo;
import com.smart.model.lis.Sample;
import com.smart.model.pb.Arrange;
import com.smart.util.ConvertUtil;
import org.mvel2.util.Make;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Title: .IntelliJ IDEA
 * Description:
 *
 * @Author:zhou
 * @Date:2016/6/22 11:32
 * @Version:
 */
@Repository("doctorQueryDao")
public class DoctorQueryDaoHibernate implements DoctorQueryDao {
    private JdbcTemplate jdbcTemplate;
    /**
     * 获取报告单列表
     * @param query         查询值
     * @param type          类型 1:病历号 2:姓名 3:医嘱号
     * @param fromDate      起始时间 2016-06-22
     * @param toDate        结束时间
     * @return
     */
    public List<LeftVo> getReportList(String query, int type, String fromDate, String toDate) {
        String dateFormat = "yyyymmdd";
        String Sql = "select a.* from (select  to_char( t2.requesttime,'yyyy-mm-dd') as requesttime,t1.patientblh, count(*) as cnt,LISTAGG(t1.sampleno,',') WITHIN GROUP( ORDER BY t1.id) AS SAMPLENO  from l_sample t1 ,l_process t2 where t1.id= t2.sample_id " +
                " and t1.sampleno !='0'" +
                "  and (to_date(substr(sampleno, 0, 8), '"+dateFormat+"'))  between to_date('"+fromDate+"','"+dateFormat+"') and to_date('"+toDate+"','"+dateFormat+"')" ;
        if(type == 1){
            Sql += " and t1.patientblh='"+query+"'";
        }else if(type == 2){
            Sql += " and t1.patientname='"+query+"'";
        }else if(type == 3){
            Sql += " and t1.id ='"+query+"'";
        }
        Sql += "  group by  to_char( t2.requesttime,'yyyy-mm-dd'),t1.patientblh ) a ORDER by requesttime";
        System.out.println(Sql);
        return jdbcTemplate.query(Sql, new RowMapper<LeftVo>() {
            public LeftVo mapRow(ResultSet rs, int rowNum) throws SQLException {
                LeftVo leftVo = new LeftVo();
                leftVo.setDateTime(rs.getString("requesttime"));
                leftVo.setPatientBlh(rs.getString("patientblh"));
                String reportNo= ConvertUtil.null2String(rs.getString("cnt"));
                if(reportNo.equals("")){
                    reportNo="无结果";
                } else{
                    reportNo = "共" +reportNo +"份报告单";
                }
                leftVo.setSampleNos(rs.getString("sampleno"));
                leftVo.setReportNote(reportNo);
                return leftVo;
            }
        });
    }

    /**
     * 根据病历号查询样本信息
     * @param patientBlh    病历号
     * @param fromDate      开始日期
     * @return
     */
    public Sample getSampleByPatientBlh(String patientBlh,String fromDate){
        String dateFormat = "yyyy-mm-dd hh24:mi:ss";
        String toDate =  fromDate.equals("")?"":fromDate+" 23:59:59";
        fromDate += fromDate.equals("")?"":" 00:00:00";

        String Sql = "select t1.*  from l_sample t1, l_process t2 ";
        Sql += " where t1.id = t2.sample_id and t1.sampleno != '0'";
        if(!fromDate.equals("")){
            Sql += " and t2.requesttime between to_date('"+fromDate+"', '"+dateFormat+"')";
            Sql += " and to_date('"+toDate+"', '"+dateFormat+"') ";
        }else {
            Sql += " and t2.requesttime is null";
        }

        Sql +=" and t1.patientblh = '"+patientBlh+"' and rownum =1" ;
        System.out.println(Sql);
        return  (Sample) this.jdbcTemplate.queryForObject(Sql,
                new RowMapper() {
                    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Sample sample = new Sample();
                        sample.setId(rs.getLong("ID"));
                        sample.setPatientId(rs.getString("PATIENTID"));
                        sample.setPatientname(rs.getString("PATIENTNAME"));
                        sample.setAge(rs.getString("AGE"));
                        sample.setInspectionName(rs.getString("INSPECTIONNAME"));
                        sample.setDiagnostic(rs.getString("DIAGNOSTIC"));
                        sample.setSectionId(rs.getString("SECTION_ID"));
                        sample.setSex(rs.getString("SEX"));
                        sample.setPatientblh(rs.getString("PATIENTBLH"));
                        sample.setDepartBed(rs.getString("DEPART_BED"));
                        sample.setSampleType(rs.getString("SAMPLETYPE"));
                        return sample;
                    }
                });
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
