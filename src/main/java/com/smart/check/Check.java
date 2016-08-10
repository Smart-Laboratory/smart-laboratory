package com.smart.check;

import java.util.List;

import com.smart.drools.R;
import com.smart.model.lis.Sample;
import com.smart.model.lis.TestResult;

public interface Check {

	int PASS = 1;
	int UNPASS = 2;
	
	int AUTO_MARK = 1;
	int DIFF_MARK = 2;
	int RATIO_MARK = 3;
	int LACK_MARK = 4;
	int RETEST_MARK = 5;
	int DANGER_MARK = 6;
	int ALARM2_MARK = 7;
	int ALARM3_MARK = 8;
	int EXTREME_MARK = 9;
	int BAYES_MARK = 10;
	
	String DIFF_COLOR = ":1;";
	String RATIO_COLOR = ":2;";
	String RETEST_COLOR = ":4;";
	String DANGER_COLOR = ":3;";
	String ALARM2_COLOR = ":5;";
	String ALARM3_COLOR = ":6;";
	String EXTREME_COLOR = ":7;";
	
	int DEFAULT_RULE = 0;
	int DIFF_RULE = 1;
	int RATIO_RULE = 2;
	int RETEST_RULE = 3;
	int DANGER_RULE = 4;
	int ALARM2_RULE = 5;
	int ALARM3_RULE = 6;
	int EXTREME_RULE = 7;
	
	String AUTO_AUDIT = "自动审核";
	String MANUAL_AUDIT = "人工审核";
	
	boolean doCheck(Sample info, List<TestResult> list);
	boolean doCheck(Sample info, R r, List<TestResult> list);
	
}
