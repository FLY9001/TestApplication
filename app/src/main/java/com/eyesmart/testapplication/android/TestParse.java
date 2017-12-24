package com.eyesmart.testapplication.android;

import android.util.Log;
import android.util.Xml;

import com.eyesmart.testapplication.TestApplication;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * 数据解析
 */

public class TestParse {
    private static final String TAG = "TestParse";

    void test() throws Exception {
        /**
         * 1. 内存占用：SAX、Pull比DOM要好；
         * 2. 编程方式：SAX采用事件驱动，在相应事件触发的时候，会调用用户编好的方法，也即每解析一类XML，就要编写一个新的适合该类XML的处理类。DOM是W3C的规范，Pull简洁。
         * 3. 访问与修改:SAX采用流式解析，DOM随机访问。
         * 4. 访问方式:SAX，Pull解析的方式是同步的，DOM逐字逐句
         */
        testDom();
        testSax();
        testPull();
        testPullCreate();

        testJSONObject(null);
        testGSON(null);
    }

    /**
     * 第一种：DOM解析
     * 直接访问XML文档中所有部分。
     * 特点：一次性解析整个文档。
     * 缺点：加载大文件时效率低下。
     */
    public List<Person> testDom() throws Exception {
        InputStream is = TestApplication.getContext().getAssets().open("persons.xml");

        List<Person> persons = new ArrayList<>();
        //获取DOM解析器工厂
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //获DOM解析器
        DocumentBuilder builder = factory.newDocumentBuilder();
        //将解析树放入内存，通过返回值Document来描述结果
        Document document = builder.parse(is);
        //取得根元素<personos>
        Element root = document.getDocumentElement();
        //取得所有person节点集合
        NodeList personNodes = root.getElementsByTagName("person");
        for (int i = 0; i < personNodes.getLength(); i++) {
            Person person = new Person();
            //取得person节点元素
            Element personElement = (Element) personNodes.item(i);
            //取得属性值并设置ID
            person.setId(Integer.parseInt(personElement.getAttribute("id")));
            //获取person的子节点
            NodeList personChilds = personElement.getChildNodes();
            for (int j = 0; j < personChilds.getLength(); j++) {
                //判断当前节点是否是元素类型的节点
                if (personChilds.item(j).getNodeType() == Node.ELEMENT_NODE) {
                    Element childElement = (Element) personChilds.item(j);
                    if ("name".equals(childElement.getNodeName())) {
                        //获取孙节点的值
                        person.setName(childElement.getFirstChild().getNodeValue());
                    } else if ("age".equals(childElement.getNodeName())) {
                        person.setAge(childElement.getFirstChild().getNodeValue());
                    }
                }
            }
            persons.add(person);
        }
        return persons;
    }


    /**
     * 第二种SAX解析：
     * 使用流的形式处理，一种以基于事件驱动的解析器，使用回调函数来实现。
     * 优点：边读边解析，解析速度快，占用内存少，
     * 缺点：不能倒退
     */
    public List<Person> testSax() throws Exception {
        InputStream is = TestApplication.getContext().getAssets().open("persons.xml");

        //得到SAX解析工厂
        SAXParserFactory factory = SAXParserFactory.newInstance();
        //得到SAX解析器
        SAXParser parser = factory.newSAXParser();
        PersonParser personParser = new PersonParser();
        parser.parse(is, personParser);
        is.close();
        return personParser.getPersons();
    }

    private final class PersonParser extends DefaultHandler {

        private List<Person> persons = null;
        private String tag = null;//记录当前解析到了那个元素节点名称
        private Person person;

        public List<Person> getPersons() {
            return persons;
        }

        //一开始会执行这个方法，所以在这里面完成初始化
        @Override
        public void startDocument() throws SAXException {
            persons = new ArrayList<>();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            //判断元素节点是否等于person
            if ("person".equals(localName)) {
                person = new Person();
                //获取数据,参数为索引下标
                person.setId(Integer.parseInt(attributes.getValue(0)));
            }
            tag = localName;
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if ("person".equals(localName)) {
                persons.add(person);
                person = null;
            }
            tag = null;
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            if (tag != null) {
                //获取文本节点的数据
                String data = new String(ch, start, length);
                if ("name".equals(tag)) {
                    person.setName(data);
                } else if ("age".equals(tag)) {
                    person.setAge(data);
                }
            }
        }
    }

    /**
     * Pull解析
     * 与SAX解析器性质类似，都是基于事件处理模式。
     * 不同的是用法：Pull解析器需要自己获取事件后做出相当于的操作
     * <p>
     * 特点：解析速度快、使用方便、效率高
     */
    public List<Person> testPull() throws Exception {
        InputStream is = TestApplication.getContext().getAssets().open("persons.xml");

        List<Person> persons = null;
        Person person = null;
        // 方式二：使用工厂类XmlPullParserFactory的方式
//        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//        XmlPullParser xmlPullParser = factory.newPullParser();
        //得到PULL解析器
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(is, "UTF-8");
        //产生事件
        int eventType = parser.getEventType();
        //如果不是文档结束事件就循环推进
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT://开始文档事件
                    persons = new ArrayList<>();
                    break;
                case XmlPullParser.START_TAG://开始元素事件
                    //获取解析器当前指向的元素的名称
                    String name = parser.getName();
                    if ("person".equals(name)) {
                        person = new Person();
                        person.setId(Integer.parseInt(parser.getAttributeValue(0)));
                    }
                    if (person != null) {
                        if ("name".equals(name)) {
                            //获取解析器当前指向元素的下一个文本节点的值
                            person.setName(parser.nextText());
                        }
                        if ("age".equals(name)) {
                            person.setAge(parser.nextText());
                        }
                    }
                    break;
                case XmlPullParser.END_TAG://结束元素事件
                    //判断是都是person的结束事件
                    if ("person".equals(parser.getName())) {
                        persons.add(person);
                        person = null;
                    }
                    break;
            }
            //进入下一个元素并触发相应的事件
            eventType = parser.next();
        }
        return persons;
    }

    /**
     * Pull创建xml
     */
    public void testPullCreate() throws Exception {
        Person[] persons = new Person[3];       // 创建节点Person对象
        persons[0] = new Person(1, "AAA", "111");
        persons[1] = new Person(2, "BBB", "222");
        persons[2] = new Person(3, "CCC", "333");

        StringWriter xmlWriter = new StringWriter();
        // 方式二：使用工厂类XmlPullParserFactory的方式
//        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//        XmlSerializer xmlSerializer = factory.newSerializer();

        // 方式一：使用Android提供的实用工具类android.util.Xml
        XmlSerializer xmlSerializer = Xml.newSerializer();
        xmlSerializer.setOutput(xmlWriter);// 保存创建的xml

//          xmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
//          xmlSerializer.setProperty("http://xmlpull.org/v1/doc/properties.html#serializer-indentation", " ");         // 设置属性
//          xmlSerializer.setProperty("http://xmlpull.org/v1/doc/properties.html#serializer-line-separator", "\n");

        xmlSerializer.startDocument("utf-8", null);     // <?xml version='1.0' encoding='UTF-8' standalone='yes' ?>
        xmlSerializer.startTag("", "persons");
        int personsLen = persons.length;
        for (int i = 0; i < personsLen; i++) {
            xmlSerializer.startTag("", "person");       // 创建person节点
            xmlSerializer.attribute("", "id", persons[i].getId() + "");

            xmlSerializer.startTag("", "name");
            xmlSerializer.text(persons[i].getName());
            xmlSerializer.endTag("", "name");

            xmlSerializer.startTag("", "age");
            xmlSerializer.text(persons[i].getAge());
            xmlSerializer.endTag("", "age");

            xmlSerializer.endTag("", "person");
        }
        xmlSerializer.endTag("", "persons");
        xmlSerializer.endDocument();

        Log.d(TAG, "xmlWriter \n" + xmlWriter.toString());
    }

    private void testJSONObject(String jsonData) throws JSONException {
        JSONArray jsonArray = new JSONArray(jsonData);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String id = jsonObject.getString("id");
            String name = jsonObject.getString("name");
            String version = jsonObject.getString("version");

            Log.d(TAG, "id is " + id);
            Log.d(TAG, "name is " + name);
            Log.d(TAG, "version is " + version);
        }
    }

    private void testGSON(String jsonData) {
        Gson gson = new Gson();
        List<Bean> beanList = gson.fromJson(jsonData, new TypeToken<List<Bean>>() {
        }.getType());
        for (Bean bean : beanList) {
            Log.d(TAG, "id is " + bean.getId());
            Log.d(TAG, "name is " + bean.getName());
            Log.d(TAG, "version is " + bean.getVersion());
        }
    }

    public class Bean {

        private String id;

        private String name;

        private String version;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

    }

    public class Person {
        private int id;
        private String name;
        private String age;

        public Person() {
            this.id = -1;
            this.name = "";
            this.age = "";
        }

        public Person(int id, String name, String age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }

        public Person(Person person) {
            this.id = person.id;
            this.name = person.name;
            this.age = person.age;
        }

        public Person getPerson() {
            return this;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getAge() {
            return this.age;
        }

        public String toString() {
            return "Person \nid = " + id + "\nname = " + name + "\nage = " + age + "\n";
        }
    }
}
