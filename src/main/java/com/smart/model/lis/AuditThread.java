package com.smart.model.lis;

import com.smart.service.UserManager;
import com.smart.service.lis.SampleManager;

public class AuditThread extends Thread {
	
	private SampleManager sampleManager = null;
	private UserManager userManager = null;
	
	public AuditThread(SampleManager s, UserManager u) {
		sampleManager = s;
		userManager = u;
		
		
	}
	
    public void run() {  
        while (!this.isInterrupted()) {// 线程未中断执行循环  
            try {  
                Thread.sleep(20000);  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
              
            // ------------------ 开始执行 ---------------------------  
            System.out.println("____FUCK TIME:" + System.currentTimeMillis());  
        }  
    }  
}