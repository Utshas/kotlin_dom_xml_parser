package com.example.xmlparse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SimpleAdapter
import android.widget.ListView
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.xml.sax.InputSource
import org.xml.sax.SAXException
import java.io.IOException
import java.net.URL
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException

class MainActivity : AppCompatActivity() {

    var empDataHashMap = HashMap<String, String>()
    var empList: ArrayList<HashMap<String,String>> = ArrayList()
    // xml parse from url link isn't working
    
    //var url = URL("http://a.cdn.searchspring.net/help/feeds/sample.xml")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        try{
            val lv =findViewById<ListView>(R.id.listView)
            val istream = assets.open("empdetail.xml")
            val builderFactory = DocumentBuilderFactory.newInstance()
            val docBuilder = builderFactory.newDocumentBuilder()
            val doc = docBuilder.parse(istream)
            //val doc = docBuilder.parse(InputSource(url.openStream()))
            // reading player tags
            val nList = doc.getElementsByTagName("player")
            for(i in 0 until nList.length)
            {
                if(nList.item(0).nodeType.equals(Node.ELEMENT_NODE))
                {
                    empDataHashMap = HashMap()
                    val element = nList.item(i) as Element
                    empDataHashMap.put("name", getNodeValue("name",element))
                    empDataHashMap.put("ratings", getNodeValue("ratings",element))
                    empDataHashMap.put("role", getNodeValue("role",element))

                    empList.add(empDataHashMap)
                }
            }
            val adapter = SimpleAdapter(this@MainActivity, empList, R.layout.custom_list, arrayOf("name", "ratings", "role"), intArrayOf(R.id.name, R.id.ratings, R.id.role))
            lv.setAdapter(adapter)
        }
        catch (e: IOException){
            e.printStackTrace()
        }
         catch (e: ParserConfigurationException) {
            e.printStackTrace()
        } catch (e: SAXException) {
            e.printStackTrace()
        }
}

    //return node value
    protected fun getNodeValue(tag: String, element: Element): String{
        val nodeList = element.getElementsByTagName(tag)
        val node = nodeList.item(0)
        if(node != null){
            if(node.hasChildNodes()){
                val child = node.firstChild
                while(child!=null){
                    if(child.nodeType === org.w3c.dom.Node.TEXT_NODE)
                    {
                        return child.nodeValue
                    }
                }
            }
        }
        return ""
    }
}
