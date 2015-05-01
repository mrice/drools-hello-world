package com.michaelrice.demo;

import org.kie.api.io.ResourceType;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.definition.KnowledgePackage;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.StatefulKnowledgeSession;

import java.util.Collection;

public class HelloWorldRunner {
    public static void main(String... args) {

        //set up the knowledge base
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newClassPathResource("/rules/rule-1.drl", HelloWorldRunner.class), ResourceType.DRL);
        kbuilder.add(ResourceFactory.newClassPathResource("/rules/rule-2.drl", HelloWorldRunner.class), ResourceType.DRL);
        Collection<KnowledgePackage> pkgs = kbuilder.getKnowledgePackages();
        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addKnowledgePackages(pkgs);

        //set up the sessions
        StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();

        Message message = new Message();
        message.setText("hello");

        System.out.println(String.format("BEFORE the rule: %s", message.getText()));
        ksession.insert(message);
        ksession.fireAllRules();

        System.out.println(String.format("AFTER the rule: %s", message.getText()));

        ksession.dispose();

    }
}
