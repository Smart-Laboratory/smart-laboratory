package com.smart.service.impl.zy;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.orm.hibernate4.SessionFactoryUtils;

import com.smart.Constants;
import com.smart.model.lis.Process;
import com.zju.api.model.Describe;
import com.zju.api.model.ExecuteInfo;
import com.zju.api.model.FormulaItem;
import com.zju.api.model.Ksdm;
import com.zju.api.model.LabGroupInfo;
import com.zju.api.model.Patient;
import com.zju.api.model.Reference;
import com.zju.api.model.SyncPatient;
import com.zju.api.model.SyncReagent;
import com.zju.api.model.SyncResult;
import com.zju.api.model.YLSF;
import com.zju.api.service.RMIService;

public class RMIServiceImpl implements RMIService {

    private JdbcTemplate jdbcTemplate;
    
    private SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat ymdh = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    
	public List<Ksdm> getAllKsdm() {
		String sql ="select KSDM,KSMC from gy_ksdm";
		return jdbcTemplate.query(sql, new RowMapper<Ksdm>() {

            public Ksdm mapRow(ResultSet rs, int rowNum) throws SQLException {
            	Ksdm ksdm = new Ksdm();
            	ksdm.setId(rs.getString(1));
            	ksdm.setName(rs.getString(2));
                return ksdm;
            }
		});
	}

	public List<FormulaItem> getFormulaItem(String labdepartment) {
		String sql = "select c.formulatype, c.testid, c.sampletype, c.formuladescribe, c.formula, " +
				"c.formulaitem, c.excludeitem, t.isprint from l_calculateformula c, l_testdescribe t where c.testid=t.testid and t.labdepartment like '%" + labdepartment + "%' and t.testid!='4831'";
		return jdbcTemplate.query(sql, new RowMapper<FormulaItem>() {

			public FormulaItem mapRow(ResultSet rs, int rowNum) throws SQLException {
				FormulaItem fItem = new FormulaItem();
				fItem.setType(rs.getInt(1));
				fItem.setTestId(rs.getString(2));
				fItem.setSampleType(rs.getString(3).charAt(0));
				fItem.setDescribe(rs.getString(4));
				fItem.setFormula(rs.getString(5));
				fItem.setFormulaItem(rs.getString(6));
				fItem.setExcludeItem(rs.getString(7));
				fItem.setIsPrint(rs.getInt(8));
				return fItem;
			}
	    });
	}

	public List<LabGroupInfo> getLabGroupInfo() {
		String sql = "select * from lab_group_information";
		return jdbcTemplate.query(sql, new RowMapper<LabGroupInfo>() {

			public LabGroupInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
				LabGroupInfo lab = new LabGroupInfo();
				lab.setSpNo(rs.getString("SPNO"));
				lab.setExpectAvg(rs.getInt("EXPECT_AVG"));
				return lab;
			}
	    });
	}
	
	public List<Describe> getDescribe() {
		return jdbcTemplate.query("select * from l_testdescribe", new RowMapper<Describe>() {
			public Describe mapRow(ResultSet rs, int rowNum) throws SQLException {
				Describe des = new Describe();
				des.setTESTID(rs.getString("TESTID"));
				des.setCHINESENAME(rs.getString("CHINESENAME"));
				des.setENGLISHAB(rs.getString("ENGLISHAB"));
				String type = rs.getString("SAMPLETYPE");
				if (type != null && type.length() > 0)
					des.setSAMPLETYPE(type.charAt(0));
				des.setUNIT(rs.getString("UNIT"));
				des.setPRINTORD(rs.getInt("PRINTORD"));
				des.setYLXH(rs.getInt("YLXH"));
				des.setISPRINT(rs.getInt("ISPRINT"));
				des.setWARNLO1(rs.getString("WARNLO1"));
				des.setWARNHI1(rs.getString("WARNHI1"));
				des.setWARNLO2(rs.getString("WARNLO2"));
				des.setWARNHI2(rs.getString("WARNHI2"));
				des.setWARNLO3(rs.getString("WARNLO3"));
				des.setWARNHI3(rs.getString("WARNHI3"));
				return des;
			}
		});
	}
	
	public List<Reference> getReference() {
		String sql = "select * from l_referencevalue";
		return jdbcTemplate.query(sql, new RowMapper<Reference>() {

			public Reference mapRow(ResultSet rs, int rowNum) throws SQLException {
				Reference ref = new Reference();
				ref.setTESTID(rs.getString("testid"));
				ref.setREFAGE(rs.getInt("refage"));
				ref.setDIRECT(rs.getInt("direct"));
				ref.setFREFHI0(rs.getString("frefhi0"));
				ref.setFREFLO0(rs.getString("freflo0"));
				ref.setMREFHI0(rs.getString("mrefhi0"));
				ref.setMREFLO0(rs.getString("mreflo0"));
				ref.setFREFHI1(rs.getString("frefhi1"));
				ref.setFREFLO1(rs.getString("freflo1"));
				ref.setMREFHI1(rs.getString("mrefhi1"));
				ref.setMREFLO1(rs.getString("mreflo1"));
				ref.setFREFHI2(rs.getString("frefhi2"));
				ref.setFREFLO2(rs.getString("freflo2"));
				ref.setMREFHI2(rs.getString("mrefhi2"));
				ref.setMREFLO2(rs.getString("mreflo2"));
				ref.setFREFHI3(rs.getString("frefhi3"));
				ref.setFREFLO3(rs.getString("freflo3"));
				ref.setMREFHI3(rs.getString("mrefhi3"));
				ref.setMREFLO3(rs.getString("mreflo3"));
				ref.setFREFHI4(rs.getString("frefhi4"));
				ref.setFREFLO4(rs.getString("freflo4"));
				ref.setMREFHI4(rs.getString("mrefhi4"));
				ref.setMREFLO4(rs.getString("mreflo4"));
				ref.setFREFHI5(rs.getString("frefhi5"));
				ref.setFREFLO5(rs.getString("freflo5"));
				ref.setMREFHI5(rs.getString("mrefhi5"));
				ref.setMREFLO5(rs.getString("mreflo5"));
				ref.setFREFHI6(rs.getString("frefhi6"));
				ref.setFREFLO6(rs.getString("freflo6"));
				ref.setMREFHI6(rs.getString("mrefhi6"));
				ref.setMREFLO6(rs.getString("mreflo6"));
				ref.setFREFHI7(rs.getString("frefhi7"));
				ref.setFREFLO7(rs.getString("freflo7"));
				ref.setMREFHI7(rs.getString("mrefhi7"));
				ref.setMREFLO7(rs.getString("mreflo7"));
				ref.setFREFHI8(rs.getString("frefhi8"));
				ref.setFREFLO8(rs.getString("freflo8"));
				ref.setMREFHI8(rs.getString("mrefhi8"));
				ref.setMREFLO8(rs.getString("mreflo8"));
				return ref;
			}
	    });
	}

	public List<Patient> getPatientList(String patientIds) {
		
		List<Patient> list = new ArrayList<Patient>();
		String sql ="select JZKH,LXDZ,LXDH,BAH from gy_brjbxxk where JZKH in ("+patientIds+")";
		list.addAll(jdbcTemplate.query(sql, new RowMapper<Patient>() {

            public Patient mapRow(ResultSet rs, int rowNum) throws SQLException {

                Patient p = new Patient();
                p.setPatientId(rs.getString("JZKH"));
                p.setAddress(rs.getString("LXDZ"));
                p.setPhone(rs.getString("LXDH"));
                p.setBlh(rs.getString("BAH"));
                return p;
            }
		}));
		String sql2 ="select JZKH,LXDZ,LXDH,BAH from gy_brjbxxk where BAH in ("+patientIds+")";
		list.addAll(jdbcTemplate.query(sql2, new RowMapper<Patient>() {

            public Patient mapRow(ResultSet rs, int rowNum) throws SQLException {

                Patient p = new Patient();
                p.setPatientId(rs.getString("JZKH"));
                p.setAddress(rs.getString("LXDZ"));
                p.setPhone(rs.getString("LXDH"));
                p.setBlh(rs.getString("BAH"));
                return p;
            }
		}));
		return list;
	}

	public List<String> getProfileJYZ(String profileName, String deviceId) {
		String time = Constants.DF3.format(new Date());
		if (StringUtils.isEmpty(deviceId)) {
			String sql = "select JYZ from l_profiletest where PROFILENAME=? and JYZTIME>=to_date(?,'yyyyMMdd')";
			return jdbcTemplate.queryForList(sql, new Object[] { profileName, time }, String.class);
		} else {
			String sql = "select JYZ from l_profiletest where PROFILENAME=? and DEVICEID=? and JYZTIME>=to_date(?,'yyyyMMdd')";
			return jdbcTemplate.queryForList(sql, new Object[] { profileName, deviceId, time }, String.class);
		}
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<SyncResult> getEditTests(String sampleNo) {
		String sql = "select * from l_testresult_edit where sampleno='" + sampleNo + "' order by edittime asc";
		return jdbcTemplate.query(sql, new RowMapper<SyncResult>() {
			public SyncResult mapRow(ResultSet rs, int rowNum) throws SQLException {
				SyncResult sr = new SyncResult();
				sr.setSAMPLENO(rs.getString("SAMPLENO"));
				sr.setTESTID(rs.getString("TESTID"));
				sr.setTESTRESULT(rs.getString("TESTRESULT"));
				return sr;
			}
		});
	}

	public SyncPatient getSampleByDoct(long doct) {
		String sql = "select * from l_patientinfo where doctadviseno=" + doct;
		List<SyncPatient> list = jdbcTemplate.query(sql, new RowMapper<SyncPatient>() {
            public SyncPatient mapRow(ResultSet rs, int rowNum) throws SQLException {
                SyncPatient p = new SyncPatient();
                setField(rs, p);
                return p;
            }
        });
		if(list != null && list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	public List<SyncPatient> getSampleByPatientName(String from, String to, String pName) {
		String sql = "select * from l_patientinfo where patientname='" + pName + "' and receivetime between to_date('" + from + "','"
                + Constants.DATEFORMAT + "') and to_date('" + to + "','" + Constants.DATEFORMAT
                + "') order by doctadviseno desc";
		return jdbcTemplate.query(sql, new RowMapper<SyncPatient>() {
		    public SyncPatient mapRow(ResultSet rs, int rowNum) throws SQLException {
		        SyncPatient p = new SyncPatient();
		        setField(rs, p);
		        return p;
		    }
		});
	}

	public List<SyncPatient> getSampleByPid(String patientid) {
		String sql = "select * from l_patientinfo where (patientid='" + patientid + "' or blh='" + patientid + "') order by doctadviseno desc";
		return jdbcTemplate.query(sql, new RowMapper<SyncPatient>() {
            public SyncPatient mapRow(ResultSet rs, int rowNum) throws SQLException {
                SyncPatient p = new SyncPatient();
                setField(rs, p);
                return p;
            }
        });
	}

	public List<SyncPatient> getSampleBySection(String from, String to, String section, int sampleState) {
		String hql = "select * from l_patientinfo p where labdepartment='" + section + "' and REQUESTTIME >to_date('2016-08-24 00:00:00','yyyy-MM-dd hh24:mi:ss') and ksreceivetime between to_date('" + from + "','"
                + Constants.DATEFORMAT + "') and to_date('" + to + "','" + Constants.DATEFORMAT
                + "') ";
		
		switch (sampleState) {//1:全部;2:已采集;3:已送出;4:科室接收;5:组内接受;6:已审核
		case 1:
			
			break;
		case 2:
			hql += " and length(p.executetime)>0 and length(p.sendtime) is null and length(p.ksreceivetime) is null and length(p.receivetime) is null";
			break;
		case 3:
			hql += " and length(p.executetime)>0 and length(p.sendtime)>0  and length(p.ksreceivetime) is null and length(p.receivetime) is null";
			break;
		case 4:
			hql += " and length(p.executetime)>0 and length(p.ksreceivetime)>0 and length(p.receivetime) is null";
			break;
		case 5:
			hql += " and length(p.executetime)>0 and  length(p.receivetime)>0 ";
			hql += " and p.executetime is not null  and p.receivetime is not null ";
			break;
		case 6:
			hql += " and  length(p.checktime)>0 ";
			break;
		default:
			break;
		}
		
		hql += " order by p.ksreceivetime asc";
		System.out.println(hql);
		
		return jdbcTemplate.query(hql, new RowMapper<SyncPatient>() {
		    public SyncPatient mapRow(ResultSet rs, int rowNum) throws SQLException {
		        SyncPatient p = new SyncPatient();
		        setField(rs, p);
		        return p;
		    }
		});
	}

	public List<Ksdm> searchSection(String name) {
		//String sql ="select KSDM,KSMC from gy_ksdm where ksmc like '" + name + "%' or srm1 like '" + name + "%' or srm2 like '" + name + "%' or srm3 like '" + name + "%'";
		String sql ="select KSDM,KSMC from gy_ksdm where ksdm like '" + name + "%' or ksmc like '" + name + "%' order by ksdm";
		List<Ksdm> list = jdbcTemplate.query(sql, new RowMapper<Ksdm>() {

            public Ksdm mapRow(ResultSet rs, int rowNum) throws SQLException {
            	Ksdm ksdm = new Ksdm();
            	ksdm.setId(rs.getString(1));
            	ksdm.setName(rs.getString(2));
                return ksdm;
            }
		});
		if(list.size() > 10) {
			list = list.subList(0, 10);
		}
		return list;
	}
	
	private Object setField(ResultSet rs, Object info) {

        Field[] fields = info.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            String type = field.getType().getName();

            try {
                if (type.equals(String.class.getName())) {
                    field.set(info, rs.getString(name));
                } else if (type.equals(int.class.getName())) {
                    field.setInt(info, rs.getInt(name));
                } else if (type.equals(Date.class.getName())) {
                    field.set(info, new java.util.Date(rs.getTimestamp(name).getTime()));
                } else if (type.equals(long.class.getName())) {
                    field.setLong(info, rs.getLong(name));
                } else if (type.equals(char.class.getName())) {
                    String sampleType = rs.getString(name);
                    if (sampleType != null && sampleType.length() > 0)
                        field.setChar(info, sampleType.charAt(0));
                } else {
                    field.set(info, rs.getObject(name));
                }
            } catch (Exception e) {}
            field.setAccessible(false);
        }
        return info;
    }

	public void sampleReceive(long doct, String operator) {
		jdbcTemplate.update("update l_patientinfo set ksreceivetime=sysdate, ksreceiver='" + operator + "' where doctadviseno=" + doct);
	}
	
	public void sampleOut(long doct, String operator) {
		jdbcTemplate.update("update l_patientinfo set sendtime=sysdate, sender='" + operator + "' where doctadviseno=" + doct);
	}

	public List<SyncReagent> getSyncReagent(String barcode) {
		String sql = "select j.quantity,j.product_lot,j.expired_date,j.wzbm,j.spec_id from jyk_out_detail j where j.out_no=" + barcode;
		return jdbcTemplate.query(sql, new RowMapper<SyncReagent>() {
			public SyncReagent mapRow(ResultSet rs, int rowNum) throws SQLException {
				SyncReagent sr = new SyncReagent();
				sr.setWzbm(rs.getString("wzbm"));
				sr.setSpec_id(rs.getString("spec_id"));
				sr.setExpired_date(rs.getString("expired_date"));
				sr.setQuantity(rs.getInt("quantity"));
				sr.setProduct_lot(rs.getString("product_lot"));
				return sr;
			}
	    });
	}

	public List<SyncResult> getWSWResult(String sampleNo) {
		String sql = "select t.CHINESENAME, o.*, r.*  from LAB_TEST t, LAB_RESULT r LEFT JOIN LAB_RESULT_OTHER o on r.spno=o.spno where t.TESTID=r.TESTID and r.SPNO='" + sampleNo + "' order by r.RESULTFLAG asc";
		//String sql = "select t.CHINESENAME, o.*, r.*  from LAB_MICRO_TEST t, LAB_MICRO_RESULT r LEFT JOIN LAB_MICRO_RESULT_OTHER o on r.spno=o.spno where t.TESTID=r.TESTID and r.SPNO='" + sampleNo + "' order by r.RESULTFLAG desc";

		return jdbcTemplate.query(sql, new RowMapper<SyncResult>() {
			public SyncResult mapRow(ResultSet rs, int rowNum) throws SQLException {
				SyncResult sr = new SyncResult();
				sr.setSAMPLENO(rs.getString(7));
				/*String describe = ConvertUtil.null2String(rs.getString(3));
				if(!describe.equals("") && describe.indexOf("自定义结果")>-1){
					sr.setTESTID(describe);
				}else{
					sr.setTESTID(rs.getString(1));
				}*/
				sr.setTESTID(rs.getString(3) == null ? rs.getString(1) : rs.getString(3));
				sr.setSAMPLETYPE(rs.getString(9).charAt(0));
				sr.setTESTRESULT(rs.getString(10));
				sr.setUNIT(rs.getString(12));
				sr.setRESULTFLAG(rs.getString(13));
				sr.setDEVICEID(rs.getString(14));
				sr.setREFLO(rs.getString(15));
				sr.setREFHI(rs.getString(16));
				sr.setOPERATOR(rs.getString(17));
				sr.setMEASURETIME(rs.getDate(18));
				sr.setHINT(rs.getString(11));
				//select t.CHINESENAME, o.*, r.*  from LAB_TEST t, LAB_RESULT r LEFT JOIN LAB_RESULT_OTHER o on r.spno=o.spno where t.TESTID=r.TESTID and r.SPNO like '20160412BAA%'
				return sr;
			}
		});
	}
	
	public Patient getPatient(String patientId) {
		
		List<Patient> list = new ArrayList<Patient>();
//		String sql ="select JZKH,LXDZ,LXDH,BAH,XM,XB,CSRQ  from gy_brjbxxk where JZKH = '"+patientId+"'";
//		list.addAll(jdbcTemplate.query(sql, new RowMapper<Patient>() {
//
//            public Patient mapRow(ResultSet rs, int rowNum) throws SQLException {
//
//                Patient p = new Patient();
//                p.setPatientId(rs.getString("JZKH"));
//                p.setAddress(rs.getString("LXDZ"));
//                p.setPhone(rs.getString("LXDH"));
//                p.setBlh(rs.getString("BAH"));
//                p.setName(rs.getString("XM"));
//                p.setSex(rs.getString("XB"));
//                p.setCsrq(rs.getString("CSRQ"));
//                return p;
//            }
//		}));
		String sql = "select patientid,address,phone,blh,patientname,sex,birthday from lab_patient where patientid like '%"+patientId+"%'";
		System.out.println(sql);
		list.addAll(jdbcTemplate.query(sql, new RowMapper<Patient>() {

            public Patient mapRow(ResultSet rs, int rowNum) throws SQLException {

                Patient p = new Patient();
                p.setPatientId(rs.getString("patientid"));
                p.setAddress(rs.getString("address"));
                p.setPhone(rs.getString("phone"));
                p.setBlh(rs.getString("blh"));
                p.setName(rs.getString("patientname"));
                p.setSex(rs.getString("sex"));
                p.setCsrq(rs.getString("birthday"));
                return p;
            }
		}));
		if(list == null || list.size()==0)
			return null;
		return list.get(0);
	}
	
	public List<ExecuteInfo> gExecuteInfo(String jzkh, String requestmode, Date from, Date to){
		
		String fromStr = ymd.format(from)+" 00:00:00";
		String toStr = ymd.format(to)+" 23:59:59";
//		System.out.println(jzkh + requestmode+fromStr+toStr);
		
		List<ExecuteInfo> eList = new ArrayList<ExecuteInfo>();
		String sql = "SELECT distinct YJ_YJK1.YJSB yjsb, YJ_YJK1.JZKH jzkh, "+
			"YJ_YJK1.SFSB sfsb,"+   
        " YJ_YJK1.BRXM brxm,"+   
        " YJ_YJK1.SJYSGH sjysgh,"+   
        " YJ_YJK1.SJKSDM sjksdm,  "+ 
        " YJ_YJK1.KDSJ kdsj,"+   
        " YJ_YJK1.MZPB mzpb,"+   
        " YJ_YJK1.LCZD lczd,"+ 
        " YJ_YJK2.SL sl,"+
        " decode(nvl(YJ_YJK2.TCYLXH,0),0,YJ_YJK2.YLXH,YJ_YJK2.TCYLXH)  ylxh, "+ 
        "GY_YLSF.TCPB tcpb," +
        "GY_YLSF.DJ DJ," +
        " GY_YLSF.YLMC ylmc, "+  
        " GY_YLSF.QBGDD qbgdd, "+  
        " GY_YLSF.QBGSJ qbgsj,   "+
        " YJ_YJK2.ZXKSDM zxksdm,   "+
        " GY_YLSF.HYFL hyfl,  "+ 
        " GY_YLSF.YBLX yblx, "+  
        " YJ_YJK2.REQUESTMODE requestmode, "+  
        " GY_YLSF.QBBDD qbbdd, "+  
        " GY_YLSF.JYXMFL jyxmfl, "+  
        " YJ_YJK2.HYJG  hyjg, "+
        " YJ_YJK2.DOCTADVISENO DOCTADVISENO" +
   " FROM GY_YLSF, YJ_YJK1,  YJ_YJK2  "+
  " WHERE ( YJ_YJK1.YJSB = YJ_YJK2.YJSB ) and  "+
 		"	( GY_YLSF.YLXH = decode(nvl(YJ_YJK2.TCYLXH,0),0,YJ_YJK2.YLXH,YJ_YJK2.TCYLXH)) and  "+
        " ((  GY_YLSF.YBLX = 'O') or "+
		"	(  GY_YLSF.YBLX = 'B') or "+
		"	(  GY_YLSF.YBLX = 'Q') or "+
		"	(  GY_YLSF.YBLX = 'C') or "+
		"	(  GY_YLSF.YBLX = '|')) and "+
         "( YJ_YJK1.JZKH = '"+jzkh+"') AND  "+
         "( YJ_YJK2.FYGB = 10 ) AND  ";
		//判断是否未采集
         if(requestmode.equals("999")){
        	 sql+=" zxbz='1' and";
         }else{
        	 sql+="( NVL(YJ_YJK2.REQUESTMODE,0) <= "+requestmode+" ) AND ";
         }

         sql+="	( NVL(YJ_YJK2.REQUESTMODE,0) <> -1 ) AND  "+
		"	( NVL(YJ_YJK2.ZFPB,0) = 0 ) AND "+ 
		"	( YJ_YJK1.SFSB IS NOT NULL OR YJ_YJK1.YHZFID IS NOT NULL OR YJ_YJK1.BRXH IS NOT NULL) AND "+
         "( YJ_YJK1.KDSJ between    to_date('"+fromStr+"','yyyy-MM-dd hh24:mi:ss')  AND    to_date('"+toStr+"','yyyy-MM-dd hh24:mi:ss') ) order by YJ_YJK1.SFSB desc "; 
	
		System.out.println(sql);
		eList.addAll(jdbcTemplate.query(sql, new RowMapper<ExecuteInfo>() {

	        public ExecuteInfo mapRow(ResultSet rs, int rowNum) throws SQLException {

	            ExecuteInfo p = new ExecuteInfo();
	            p.setYjsb(rs.getString("YJSB"));
	            p.setJzkh(rs.getString("JZKH"));
	            p.setSfsb(rs.getString("SFSB"));
	            p.setBrxm(rs.getString("BRXM"));
	            p.setSjysgh(rs.getString("SJYSGH"));
	            p.setSjksdm(rs.getString("SJKSDM"));
	            p.setKdsj(new Date(rs.getTimestamp("KDSJ").getTime()));
	            p.setMzpb(rs.getString("MZPB"));
	            p.setLzcd(rs.getString("LCZD"));
	            
	            p.setSl(rs.getString("SL"));
	            p.setYlxh(rs.getString("YLXH"));
	            p.setZxksdm(rs.getString("ZXKSDM"));
	            p.setRequestmode(rs.getString("REQUESTMODE"));
	            p.setHyjg(rs.getString("HYJG"));
	            p.setDoctadviseno(rs.getString("DOCTADVISENO"));
	            
	            p.setTcpb(rs.getString("tcpb"));
	            p.setDj(rs.getString("DJ"));
	            p.setYlmc(rs.getString("YLMC"));
	            p.setQbgdd(rs.getString("QBGDD"));
	            p.setQbgsj(rs.getString("QBGSJ"));
	            p.setHyfl(rs.getString("HYFL"));
	            p.setYblx(rs.getString("YBLX"));
	            p.setQbbdd(rs.getString("QBBDD"));
	            p.setJyxmfl(rs.getString("JYXMFL"));
	            return p;
	        }
		}));
		return eList;
	}

    public List<ExecuteInfo> getSelectInfo(String yjsbs,String ylxhs){
		
		List<ExecuteInfo> eList = new ArrayList<ExecuteInfo>();
		String sql = "SELECT distinct YJ_YJK1.YJSB yjsb, YJ_YJK1.JZKH jzkh, "+
			"YJ_YJK1.SFSB sfsb,"+   
        " YJ_YJK1.BRXM brxm,"+   
        " YJ_YJK1.SJYSGH sjysgh,"+   
        " YJ_YJK1.SJKSDM sjksdm,  "+ 
        " YJ_YJK1.KDSJ kdsj,"+   
        " YJ_YJK1.MZPB mzpb,"+   
        " YJ_YJK2.SL sl,"+
        " decode(nvl(YJ_YJK2.TCYLXH,0),0,YJ_YJK2.YLXH,YJ_YJK2.TCYLXH)  ylxh, "+ 
        "GY_YLSF.DJ DJ," +
        " GY_YLSF.YLMC ylmc, "+  
        " GY_YLSF.QBGDD qbgdd, "+  
        " GY_YLSF.QBGSJ qbgsj,   "+
        " YJ_YJK2.ZXKSDM zxksdm,   "+
        " GY_YLSF.HYFL hyfl,  "+ 
        " GY_YLSF.YBLX yblx, "+  
        " YJ_YJK2.REQUESTMODE requestmode, "+  
        " GY_YLSF.QBBDD qbbdd, "+  
        " GY_YLSF.JYXMFL jyxmfl, "+  
        " YJ_YJK2.HYJG  hyjg, "+
        " YJ_YJK2.DOCTADVISENO DOCTADVISENO" +
   " FROM GY_YLSF, YJ_YJK1,  YJ_YJK2  "+
  " WHERE ( YJ_YJK1.YJSB = YJ_YJK2.YJSB ) and  "+
 		"	( GY_YLSF.YLXH = decode(nvl(YJ_YJK2.TCYLXH,0),0,YJ_YJK2.YLXH,YJ_YJK2.TCYLXH)) and  "+
        " YJ_YJK1.YJSB in ("+yjsbs+") and" +
		" GY_YLSF.YLXH in ("+ylxhs+")";
	
		eList.addAll(jdbcTemplate.query(sql, new RowMapper<ExecuteInfo>() {

	        public ExecuteInfo mapRow(ResultSet rs, int rowNum) throws SQLException {

	            ExecuteInfo p = new ExecuteInfo();
	            p.setYjsb(rs.getString("YJSB"));
	            p.setJzkh(rs.getString("JZKH"));
	            p.setSfsb(rs.getString("SFSB"));
	            p.setBrxm(rs.getString("BRXM"));
	            p.setSjysgh(rs.getString("SJYSGH"));
	            p.setSjksdm(rs.getString("SJKSDM"));
	            p.setKdsj(rs.getDate("KDSJ"));
	            
	            p.setMzpb(rs.getString("MZPB"));
	            
	            p.setSl(rs.getString("SL"));
	            p.setYlxh(rs.getString("YLXH"));
	            p.setZxksdm(rs.getString("ZXKSDM"));
	            p.setRequestmode(rs.getString("REQUESTMODE"));
	            p.setHyjg(rs.getString("HYJG"));
	            p.setDoctadviseno(rs.getString("DOCTADVISENO"));
	            
	            p.setDj(rs.getString("DJ"));
	            p.setYlmc(rs.getString("YLMC"));
	            p.setQbgdd(rs.getString("QBGDD"));
	            p.setQbgsj(rs.getString("QBGSJ"));
	            p.setHyfl(rs.getString("HYFL"));
	            p.setYblx(rs.getString("YBLX"));
	            p.setQbbdd(rs.getString("QBBDD"));
	            p.setJyxmfl(rs.getString("JYXMFL"));
	            return p;
	        }
		}));
		return eList;
	}
	
	public void updateExecuteInfo(String yjsb, String ylxh, String sl){
		String sql = "update YJ_YJK1 set zxbz='1' where yjsb = '"+yjsb+"'";
		String sql2 = "update YJ_YJK2 set requestmode='"+sl+"' where yjsb = '"+yjsb+"' and (tcylxh = '"+ylxh+"' or ylxh = '"+ylxh+"')";
		
		jdbcTemplate.execute(sql);
		jdbcTemplate.execute(sql2);
		
	}
	
	public List<String> getExamtode(String patientId, Date from, Date to){
		String fromStr = ymd.format(from)+" 00:00:00";
		String toStr = ymd.format(to)+" 23:59:59";
		String sql = "select distinct c.ylmc from YJ_YJK1 a, YJ_YJK2 b, GY_YLSF c where a.jzkh='"+patientId+"' and "
				+ "a.yjsb=b.yjsb and "
				+ "( c.YLXH = decode(nvl(b.TCYLXH,0),0,b.YLXH,b.TCYLXH)) and"
				+ " (a.ZXBZ is null or a.ZXBZ = '0') and"
				+ "( a.KDSJ between    to_date('"+fromStr+"','yyyy-MM-dd hh24:mi:ss')  AND    to_date('"+toStr+"','yyyy-MM-dd hh24:mi:ss') ) order by c.ylmc desc ";
		
		System.out.println(sql);
		List<String> exam = jdbcTemplate.queryForList(sql, String.class);
		return exam;
	}
	
	public YLSF getYlsf(String ylxh){
		String sql = "select * from gy_ylsf where to_char(ylxh) = '"+ylxh+"'";
		List<YLSF> ylsfs = jdbcTemplate.query(sql, new RowMapper<YLSF>() {
		    public YLSF mapRow(ResultSet rs, int rowNum) throws SQLException {
		        YLSF p = new YLSF();
		        setField(rs, p);
		        return p;
		    }
		});
		if(ylsfs != null && ylsfs.size()>0)
			return ylsfs.get(0);
		return null;
	}
	
	public List<SyncPatient> getOutList(String sender,Date sendtime){
		String sql = "select * from l_patientinfo p where p.sender='"+sender+"' and p.sendtime between "
				+ "to_date('"+ymdh.format(sendtime)+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+ymd.format(sendtime)+" 23:59:59','yyyy-mm-dd hh24:mi:ss') order by p.sendtime desc";
		System.out.println(sql);
		return jdbcTemplate.query(sql, new RowMapper<SyncPatient>() {
		    public SyncPatient mapRow(ResultSet rs, int rowNum) throws SQLException {
		        SyncPatient p = new SyncPatient();
		        setField(rs, p);
		        return p;
		    }
		});
	}
	/**
	 * 根据医嘱号字符串 取样本信息
	 * @param doctadvisenos
	 * @return
	 */
	public List<SyncPatient> getByDoctadvisenos(String doctadvisenos){
		String sql = "select * from l_patientinfo p where p.doctadviseno in ("+doctadvisenos+") ";
		System.out.println(sql);
		return jdbcTemplate.query(sql, new RowMapper<SyncPatient>() {
		    public SyncPatient mapRow(ResultSet rs, int rowNum) throws SQLException {
		        SyncPatient p = new SyncPatient();
		        setField(rs, p);
		        return p;
		    }
		});
	}

	public List<SyncPatient> getReceiveList(String receiver, Date starttime, Date endtime,int start,int end){
		String hql = "";
		if(endtime == null){
			hql = "select * from l_patientinfo p where p.ksreceiver='"+receiver+"' and p.ksreceivetime between "
					+ "to_date('"+Constants.SDF.format(starttime)+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+Constants.DF2.format(starttime)+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		}else{
			hql = "select * from l_patientinfo p where p.ksreceiver='"+receiver+"' and p.ksreceivetime between "
					+ "to_date('"+Constants.SDF.format(starttime)+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+Constants.SDF.format(endtime)+"','yyyy-mm-dd hh24:mi:ss') ";
		}
		
		
		if(start!=0 && end !=0 && start<=end){
			hql += " and rownum>="+ start + " and rownum<=" + end; 
		}
		hql += " order by p.ksreceivetime desc";
		
		return jdbcTemplate.query(hql, new RowMapper<SyncPatient>() {
		    public SyncPatient mapRow(ResultSet rs, int rowNum) throws SQLException {
		        SyncPatient p = new SyncPatient();
		        setField(rs, p);
		        return p;
		    }
		});
	}
	
	public int getReceiveListCount(String receiver, Date starttime, Date endtime){
		String hql = "";
		if(endtime == null){
			hql = "select count(*) from l_patientinfo p where p.ksreceiver='"+receiver+"' and p.ksreceivetime between "
					+ "to_date('"+Constants.SDF.format(starttime)+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+Constants.DF2.format(starttime)+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		}else{
			hql = "select count(*) from l_patientinfo p where p.ksreceiver='"+receiver+"' and p.ksreceivetime between "
					+ "to_date('"+Constants.SDF.format(starttime)+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+Constants.SDF.format(endtime)+"','yyyy-mm-dd hh24:mi:ss') ";
		}
		
		hql += " order by p.ksreceivetime desc";
		
		return jdbcTemplate.queryForObject(hql, Integer.class);
	}


	public List<SyncPatient> getSendList(String arg0, Date arg1, Date arg2,
			String arg3, String arg4, int arg5, int arg6, int arg7) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getSendListCount(String arg0, Date arg1, Date arg2, String arg3,
			String arg4, int arg5) {
		// TODO Auto-generated method stub
		return 0;
	}
}