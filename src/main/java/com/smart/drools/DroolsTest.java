package com.smart.drools;

import java.io.Reader;
import java.io.StringReader;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message.Level;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;


/**
 * This is a sample class to launch a rule.
 */
public class DroolsTest {
	
	public KieSession readKbase(Reader reader) throws Exception {
		KieServices ks = KieServices.Factory.get(); 
		
		KieFileSystem kfs = ks.newKieFileSystem();
		kfs.write("rule", ks.getResources().newReaderResource(reader).setResourceType(ResourceType.DRL));
		KieBuilder kbuilder = ks.newKieBuilder( kfs ).buildAll();
		
		if (kbuilder.getResults().hasMessages(Level.ERROR)) {
		    throw new RuntimeException("Build Errors:\n" + kbuilder.getResults().toString());
		}
		KieContainer kContainer = ks.newKieContainer(ks.getRepository().getDefaultReleaseId());
		KieSession kSession = kContainer.newKieSession();
		return kSession;
	}
	
	public static void main(String[] args) {  
		StringBuilder builder = new StringBuilder();
		builder.append("package com.smart.drools.clock;\n");
		builder.append("rule \"run\"\n");  
		builder.append("salience 1 \n"); 
		builder.append("when \n"); 
		builder.append("c: Clock(!(hour == 1 && second == 1)) \n"); 
		builder.append("then\n"); 
		builder.append("System.out.println(c);\n"); 
		builder.append("Thread.sleep(1000);\n"); 
		builder.append("c.setSecond(c.getSecond() + 1); \n"); 
		builder.append("update(c); \n"); 
		builder.append("end \n");
		builder.append("rule \"second\" \n");
		builder.append("salience 2 \n");
		builder.append("when \n");
		builder.append("c: Clock(second == 60); \n");
		builder.append("then \n");
		builder.append("c.setMinute(c.getMinute() + 1); \n");
		builder.append("c.setSecond(0); \n");
		builder.append("update(c); \n");
		builder.append("end \n");
		builder.append("rule \"minute\" \n");
		builder.append("salience 3 \n");
		builder.append("when \n");
		builder.append("c: Clock(minute == 60) \n");
		builder.append("then \n");
		builder.append("c.setHour(c.getHour() + 1); \n");
		builder.append("c.setMinute(0); \n");
		builder.append("update(c); \n");
		builder.append("end");  
		
		System.out.println(builder.toString());
		Reader reader = new StringReader(builder.toString());
		KieServices ks = KieServices.Factory.get(); 
		
		/*KieContainer kContainer = ks.getKieClasspathContainer();
		KieSession kSession = kbase.newKieSession();*/ 
		
		KieFileSystem kfs = ks.newKieFileSystem();
		kfs.write("rule", ks.getResources().newReaderResource(reader).setResourceType(ResourceType.DRL));
		KieBuilder kbuilder = ks.newKieBuilder( kfs ).buildAll();
		
		if (kbuilder.getResults().hasMessages(Level.ERROR)) {
		    throw new RuntimeException("Build Errors:\n" + kbuilder.getResults().toString());
		}
		KieContainer kContainer = ks.newKieContainer(ks.getRepository().getDefaultReleaseId());
		KieSession kSession = kContainer.newKieSession();
		
		Clock c = new Clock(); 
		kSession.insert(c);  
		System.out.println(c.getHour() + " " + c.getMinute() + " " + c.getSecond());
        kSession.fireAllRules();
        System.out.println(c.getHour() + " " + c.getMinute() + " " + c.getSecond());
        kSession.dispose(); 
          
    }

}
