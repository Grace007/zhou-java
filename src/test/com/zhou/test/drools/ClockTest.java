package com.zhou.test.drools;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * @author eli
 * @date 2018/3/15 15:55
 */
public class ClockTest {
    public static void main(String[] args) {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession kSession = kContainer.newKieSession("session-clock");
        kSession.insert(new Clock());
        kSession.fireAllRules();
        kSession.dispose();

    }
}
