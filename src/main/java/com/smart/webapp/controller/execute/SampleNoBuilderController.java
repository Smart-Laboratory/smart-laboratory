package com.smart.webapp.controller.execute;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.naming.spi.InitialContextFactoryBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.smart.model.lis.Process;
import com.smart.model.lis.Sample;
import com.smart.service.execute.SampleNoBuilderManager;
import com.smart.service.lis.ProcessManager;
import com.smart.service.lis.SampleManager;
import com.google.common.base.Function;
import com.smart.model.execute.SampleNoBuilder;

import net.sf.ehcache.search.expression.And;

@Controller
public class SampleNoBuilderController {

	private SimpleDateFormat hhmm = new SimpleDateFormat("hhmm");
	private SimpleDateFormat dd = new SimpleDateFormat("E");
	private SimpleDateFormat ymd = new SimpleDateFormat("YYYYMMDD");
	private SimpleDateFormat y_md = new SimpleDateFormat("yyyy-MM-dd");
	
	
}
