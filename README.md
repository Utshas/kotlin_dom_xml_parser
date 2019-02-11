# kotlin_dom_xml_parser
example code of kotlin dom xml parser for android:

STEP 1:
At first, Create the XML file name, empdetail.xml in src/main/assets directory to parse the data using DOM parser.If there is no such directory, create one. and the code can be like :

    <?xml version="1.0" encoding="utf-8"?>
    <emplayers>
        <player>
            <name>Tazwar Utshas</name>
            <ratings>550</ratings>
            <role>Captain</role>
        </player>
        <player>
            <name>Matthew Hayden</name>
            <ratings>455</ratings>
            <role>Opener</role>
        </player>
        <player>
            <name>Habibul Basher</name>
            <ratings>555</ratings>
            <role>Vice Captain</role>
        </player>
        <player>
            <name>Khaled Masud</name>
            <ratings>425</ratings>
            <role>Wicket Keeper</role>
        </player>
        <player>
            <name>Md. Rafiq</name>
            <ratings>350</ratings>
            <role>Spinner</role>
        </player>
        <player>
            <name>Mashrafee Mortaza</name>
            <ratings>650</ratings>
            <role>Pacer</role>
        </player>
        <player>
            <name>Wasim Akram</name>
            <ratings>750</ratings>
            <role>All Rounder</role>
        </player>
        <player>
            <name>Sachin Tendulker</name>
            <ratings>550</ratings>
            <role>Middle Order</role>
        </player>
        <player>
            <name>Ricky Ponting</name>
            <ratings>650</ratings>
            <role>Middle Order</role>
        </player>
        <player>
            <name>Adam Gilchrist</name>
            <ratings>750</ratings>
            <role>Opener</role>
        </player>
        <player>
            <name>Kumara Sangakkara</name>
            <ratings>550</ratings>
            <role>Keeper Batsman</role>
        </player>
        <player>
            <name>Sanath Jayasuria</name>
            <ratings>755</ratings>
            <role>Back up opener</role>
        </player>
        <player>
            <name>Shoaib Akhter</name>
            <ratings>650</ratings>
            <role>Pacer</role>
        </player>
        <player>
            <name>Brett Lee</name>
            <ratings>750</ratings>
            <role>Pacer</role>
        </player>
    </emplayers>

Step 2:
Now, create a custom row for listview named custom_list.xml. The code will be somewhat like this:

    <?xml version="1.0" encoding="utf-8"?>
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="horizontal">
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:background="#eee"
            android:paddingBottom="2dp"
            android:orientation="vertical">

            <TextView
                android:id="@+id/name"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="name"
                android:textStyle="bold"
                android:layout_marginLeft="15dp"
                android:layout_marginStart="15dp"
                android:textAppearance="@style/Base.TextAppearance.AppCompat.Medium" />


            <TextView
                android:id="@+id/ratings"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="rating"
                android:layout_marginLeft="15dp"
                android:layout_marginStart="15dp"
                android:layout_marginTop="5dp"
                android:textSize="15sp"/>
            <TextView
                android:id="@+id/role"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="role"
                android:layout_marginLeft="15dp"
                android:layout_marginStart="15dp"
                android:layout_marginTop="5dp"
                android:textSize="15sp"/>
        </LinearLayout>
    </LinearLayout>

Step 3:
Now, code your activity_main.xml very simply just with a listview, like this:

    <?xml version="1.0" encoding="utf-8"?>
    <androidx.constraintlayout.widget.ConstraintLayout
            xmlns:android="http://schemas.android.com/apk/res/android"
            xmlns:tools="http://schemas.android.com/tools"
            xmlns:app="http://schemas.android.com/apk/res-auto"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            tools:context=".MainActivity">

    <ListView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/listView">

    </ListView>

    </androidx.constraintlayout.widget.ConstraintLayout>

Step 4:
 Finally, code your MainActivity.kt like this:
 package com.example.xmlparse

    import androidx.appcompat.app.AppCompatActivity
    import android.os.Bundle
    import android.widget.ArrayAdapter
    import android.widget.SimpleAdapter
    import android.widget.ListView
    import org.w3c.dom.Element
    import org.w3c.dom.Node
    import org.xml.sax.SAXException
    import java.io.IOException
    import javax.xml.parsers.DocumentBuilderFactory
    import javax.xml.parsers.ParserConfigurationException

    class MainActivity : AppCompatActivity() {

        var empDataHashMap = HashMap<String, String>()
        var empList: ArrayList<HashMap<String,String>> = ArrayList()

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            try{
                val lv =findViewById<ListView>(R.id.listView)
                val istream = assets.open("empdetail.xml")
                val builderFactory = DocumentBuilderFactory.newInstance()
                val docBuilder = builderFactory.newDocumentBuilder()
                val doc = docBuilder.parse(istream)
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

That's all. You can now change and modify things as you need...
