 package com.zhl.example.processengine.wrapper.config;

import com.zhl.example.processengine.enums.InvokeMethod;
import com.zhl.example.processengine.wrapper.model.ProcessModel;
import com.zhl.example.processengine.wrapper.model.ProcessNodeModel;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hailang.zhang
 * @since 2023-06-06
 */
public abstract class XmlProcessParser implements ProcessParser{

    @Override
    public List<ProcessModel> parse() throws Exception {
        Document document = getDocument();
        Element rootElement = document.getRootElement();
        List<Element> processElements = rootElement.elements();
        List<ProcessModel> processModels = new ArrayList<>();
        for (Element process : processElements) {
            ProcessModel model = new ProcessModel();
            model.setName(process.attributeValue("name"));
            List<Element> elements = process.elements();
            for (Element node : elements) {
                ProcessNodeModel processNodeModel = new ProcessNodeModel();
                processNodeModel.setName(node.attributeValue("name"));
                processNodeModel.setClassName(node.attributeValue("class"));
                String next = node.attributeValue("next");
                if (next != null) {
                    processNodeModel.setNextNode(next);
                }
                String begin = node.attributeValue("begin");
                processNodeModel.setBegin(Boolean.parseBoolean(begin));
                String invokeMethodStr = node.attributeValue("invoke-method");
                InvokeMethod invokeMethod = invokeMethodStr == null ? InvokeMethod.SYNC :
                        InvokeMethod.valueOf(invokeMethodStr.toUpperCase());
                processNodeModel.setInvokeMethod(invokeMethod);
                model.addNode(processNodeModel);
            }
            processModels.add(model);
        }
        return processModels;
    }

    protected abstract Document getDocument() throws DocumentException;
}