package com.smart;

import java.text.SimpleDateFormat;

/**
 * Constant values used throughout the application.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public final class Constants {

    private Constants() {
        // hide me
    }
    //~ Static fields/initializers =============================================

    /**
     * Assets Version constant
     */
    public static final String ASSETS_VERSION = "assetsVersion";
    /**
     * The name of the ResourceBundle used in this application
     */
    public static final String BUNDLE_KEY = "ApplicationResources";

    /**
     * File separator from System properties
     */
    public static final String FILE_SEP = System.getProperty("file.separator");

    /**
     * User home from System properties
     */
    public static final String USER_HOME = System.getProperty("user.home") + FILE_SEP;

    /**
     * The name of the configuration hashmap stored in application scope.
     */
    public static final String CONFIG = "appConfig";

    /**
     * Session scope attribute that holds the locale set by the user. By setting this key
     * to the same one that Struts uses"," we get synchronization in Struts w/o having
     * to do extra work or have two session-level variables.
     */
    public static final String PREFERRED_LOCALE_KEY = "org.apache.struts2.action.LOCALE";

    /**
     * The request scope attribute under which an editable user form is stored
     */
    public static final String USER_KEY = "userForm";

    /**
     * The request scope attribute that holds the user list
     */
    public static final String USER_LIST = "userList";

    /**
     * The request scope attribute for indicating a newly-registered user
     */
    public static final String REGISTERED = "registered";

    /**
     * The name of the Administrator role"," as specified in web.xml
     */
    public static final String ADMIN_ROLE = "ROLE_ADMIN";

    /**
     * The name of the User role"," as specified in web.xml
     */
    public static final String USER_ROLE = "ROLE_USER";

    /**
     * The name of the Worker role"," as specified in web.xml
     */
    public static final String WORKER_ROLE = "ROLE_WORKER";

    /**
     * The name of the user's role list"," a request-scoped attribute
     * when adding/editing a user.
     */
    public static final String USER_ROLES = "userRoles";

    /**
     * The name of the available roles list"," a request-scoped attribute
     * when adding/editing a user.
     */
    public static final String AVAILABLE_ROLES = "availableRoles";

    /**
     * The name of the CSS Theme setting.
     * @deprecated No longer used to set themes.
     */
    public static final String CSS_THEME = "csstheme";
    
	/**
	 * 列表页面每页显示的数量
	 */
	public static final Integer PAGE_SIZE = 15;

	/**
	 * 结果修改时的操作类型
	 */
	public static final String ADD = "添加";
	public static final String DELETE = "删除";
	public static final String EDIT = "修改";
	public static final String DRAG = "拖拽";

	/**
	 * 填充hi","lo","resultFlag的标记
	 */
	public static final Integer FILL_FLAG = 2;

	/**
	 * 修改testresult值的标记
	 */
	public static final Integer EDIT_FLAG = 3;

	/**
	 * 添加testResult整条数据的标记
	 */
	public static final Integer ADD_FLAG = 5;

	/**
	 * 删除标记
	 */
	public static final Integer DELETE_FLAG = 7;
	
	public static final Integer MANUAL_EDIT_FLAG = 33;

	/**
	 * 无结果
	 */
	public static final Integer STATUS_NORESULT = -1;
	/**
	 * 未审核
	 */
	public static final Integer STATUS_UNAUDIT = 0;
	/**
	 * 已通过
	 */
	public static final Integer STATUS_PASSED = 1;
	/**
	 * 未通过
	 */
	public static final Integer STATUS_UNPASS = 2;
	/**
	 * 样本来源、常量
	 */
	public static final String[] SAMPLE_TYPE = new String[]{"血清","血液","全血","尿液","大便","浓缩液","静脉血","肛拭子","卵泡液","胆汁","胸水","脑脊液","腹水","腹透液","透析液","心包积液","痰","唾液","阴道分泌物","精液","咽拭子","洁尿","前列腺液","骨髓","脓液","分泌物","空气","中段尿","引流液","拭子","反渗水","透析液进水","透析液出水","坏死物","纤支镜洗液","囊液","溃疡分泌物","穿刺液","赘生物","渗出液","动脉导管","白带","肉汤","前房积脓液","静脉导管","留置针","皮下积","积液","造血干细胞","血浆","体液","胃液","组织","动脉血","置换液","乳头溢液","刮片","纤支镜毛刷","手术标本","活检","穿刺刷片","呕吐物","尿红细胞位相","结石","宫颈刷片"};
	/**
	 * 不合格标本拒收原因
	 */
	public static final String[] INVALIDSAMPLE_REASON = new String[]{"无","溶血","凝血","标本量不足","空管","抗凝剂血量比例错误","乳糜血","标本容器错误","采样时间错误","标本类型错误","运输时间不合格","运输条件不合格","标本丢失或未收到","标本容器损坏","标本污染","送错部门","无标签","标签信息不全","标签无法识别","出院无法计费","其他"};
	/**
	 * 不合格标本拒收原因
	 */
	public static final String[] INVALIDSAMPLE_MEASURETAKEN = new String[]{"标本拒收，通知临床","标本拒收，通知临床，重新采集标本","标本拒收，通知临床，纠正标签错误","无任何措施","其它"};
	/**
	 * 对
	 */
	public static final String TRUE = "√";
	/**
	 * 错
	 */
	public static final String FALSE = "×";

	/**
	 * 字符串:yyyy-MM-dd hh24:mi:ss
	 */
	public static final String DATEFORMAT = "yyyy-MM-dd hh24:mi:ss";

	/**
	 * 日期格式:yyyy-MM-dd hh:mm:ss
	 */
	public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 日期格式:yyyy-MM-dd hh:mm
	 */
	public static final SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	/**
	 * 日期格式:yyyy-MM-dd
	 */
	public static final SimpleDateFormat DF2 = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 日期格式:yyyyMMdd
	 */
	public static final SimpleDateFormat DF3 = new SimpleDateFormat("yyyyMMdd");
	
	/**
	 * 日期格式:MM/dd
	 */
	public final static SimpleDateFormat DF4 = new SimpleDateFormat("MM/dd");
	
	/**
	 * 日期格式:HH:mm
	 */
	public final static SimpleDateFormat DF5 = new SimpleDateFormat("HH:mm");

	/**
	 * 日期格式:HH:mm:ss
	 */
	public final static SimpleDateFormat DF6 = new SimpleDateFormat("HH:mm:ss");
	
	/**
	 * 日期格式:yyMMdd
	 */
	public final static SimpleDateFormat DF7 = new SimpleDateFormat("yyMMdd");

	/**
	 * 日期格式:yyyy年MM月dd日 HH:mm(EEE)
	 */
	public final static SimpleDateFormat DF8 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm(EEE)" );

	/**
	 * 日期格式:yyyy年MM月dd日 HH:mm(EEE)
	 */
	public final static SimpleDateFormat DF9 = new SimpleDateFormat("yyyy-mm-dd'T'HH:mm:ss.SSSZ" );
	
	/**
	 * 检验流程——申请
	 */
	public static final String PROCESS_REQUEST = "申请";
	
	/**
	 * 检验流程——采样
	 */
	public static final String PROCESS_EXECUTE = "采样";
	
	/**
	 * 检验流程——运送
	 */
	public static final String PROCESS_SEND = "运送";
	
	/**
	 * 检验流程——接收
	 */
	public static final String PROCESS_RECEIVE = "接收";
	public static final String PROCESS_KSRECEIVE = "科室接收";
	/**
	 * 检验流程——报告
	 */
	public static final String PROCESS_CKECK = "报告";
	
	/**
	 * 检验流程——打印
	 */
	public static final String PROCESS_PRINT = "打印";
	
	/**
	 * 任务管理流程——在运行
	 */
	public final static int THREAD_RUNNING = 1;
	
	/**
	 * 任务管理流程——已结束
	 */
	public final static int THREAD_FINISHED = 2;
	
	/**
	 * 任务管理流程——停止
	 */
	public final static int THREAD_STOPPED = 3;
	/**
	 * 阳性
	 */
	public final static String YANG = "阳性";
	/**
	 * 阴性
	 */
	public final static String YIN = "阴性";
	/**
	 * 图片上传备份路径
	 */
	public final static String imageUrl_bak = "/home/imagebak/";
	/**
	 * 图片上传路径
	 */
	public final static String imageUrl = "/home/tomcat/webapps/lab/images/upload/";
	
	public final static String LOG_OPERATE_ADD = "新增";
	public final static String LOG_OPERATE_EDIT = "修改";
	public final static String LOG_OPERATE_DELETE = "删除";
	public final static String LOG_OPERATE_RECOVER = "恢复";
	public final static String LOG_OPERATE_INCREASE = "标本编号增加";
	public final static String LOG_OPERATE_REDUCE = "标本编号减少";
	public final static String LOG_OPERATE_INVERSION = "标本编号倒置";
	public final static String LOG_OPERATE_REPLACE = "标本编号替换";
	
	/**
	 * 医学检验科编号
	 */
	public final static String LaboratoryCode = "21";
	
	/**
	 * 排班特殊班次
	 */
	public final static String defeholidayhis = "历休";
	public final static String holiday = "公休"; //法定节假日
	public final static String holidayOne = "日休"; //法定节假日，不补休
}
