package com.smart.service.impl.zy;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.smart.Constants;
import com.zju.api.model.Describe;
import com.zju.api.model.FormulaItem;
import com.zju.api.model.Ksdm;
import com.zju.api.model.LabGroupInfo;
import com.zju.api.model.Patient;
import com.zju.api.model.Reference;
import com.zju.api.model.SyncPatient;
import com.zju.api.model.SyncReagent;
import com.zju.api.model.SyncResult;
import com.zju.api.service.RMIService;

public class RMIServiceImpl implements RMIService {

    private JdbcTemplate jdbcTemplate;
    
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
		if(list != null) {
			return list.get(0);
		}
		return null;
	}

	public List<SyncPatient> getSampleByPatientName(String from, String to, String pName) {
		String sql = "select * from l_patientinfo where patientname='" + pName + "' and receivetime between to_date('" + from + " 00:00:00','"
                + Constants.DATEFORMAT + "') and to_date('" + to + " 23:59:59','" + Constants.DATEFORMAT
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
		String sql = "select * from l_patientinfo where patientid='" + patientid + "' order by doctadviseno desc";
		return jdbcTemplate.query(sql, new RowMapper<SyncPatient>() {
            public SyncPatient mapRow(ResultSet rs, int rowNum) throws SQLException {
                SyncPatient p = new SyncPatient();
                setField(rs, p);
                return p;
            }
        });
	}

	public List<SyncPatient> getSampleBySection(String from, String to, String section) {
		String sql = "select * from l_patientinfo where section='" + section + "' and receivetime between to_date('" + from + " 00:00:00','"
                + Constants.DATEFORMAT + "') and to_date('" + to + " 23:59:59','" + Constants.DATEFORMAT
                + "') order by doctadviseno desc";
		return jdbcTemplate.query(sql, new RowMapper<SyncPatient>() {
		    public SyncPatient mapRow(ResultSet rs, int rowNum) throws SQLException {
		        SyncPatient p = new SyncPatient();
		        setField(rs, p);
		        return p;
		    }
		});
	}

	public List<Ksdm> searchSection(String name) {
		String sql ="select KSDM,KSMC from gy_ksdm where ksmc like '" + name + "%' or srm1 like '" + name + "%' or srm2 like '" + name + "%' or srm3 like '" + name + "%'";
		return jdbcTemplate.query(sql, new RowMapper<Ksdm>() {

            public Ksdm mapRow(ResultSet rs, int rowNum) throws SQLException {
            	Ksdm ksdm = new Ksdm();
            	ksdm.setId(rs.getString(1));
            	ksdm.setName(rs.getString(2));
                return ksdm;
            }
		});
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
		String sql = "select t.CHINESENAME, o.*, r.*  from LAB_TEST t, LAB_RESULT r LEFT JOIN LAB_RESULT_OTHER o on r.spno=o.spno where t.TESTID=r.TESTID and r.SPNO='" + sampleNo + "' order by r.RESULTFLAG desc";
		return jdbcTemplate.query(sql, new RowMapper<SyncResult>() {
			public SyncResult mapRow(ResultSet rs, int rowNum) throws SQLException {
				SyncResult sr = new SyncResult();
				sr.setSAMPLENO(rs.getString(7));
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
}