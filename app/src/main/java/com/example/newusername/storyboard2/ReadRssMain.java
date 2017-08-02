package com.example.newusername.storyboard2;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by NewUsername on 8/1/2016.
 */
public class ReadRssMain extends AsyncTask<Void,Void,Void>{
    Context  context;
    String address="http://indianexpress.com/section/india/feed/";
    ProgressDialog progressDialog;
    URL url;
    ArrayList<Feeditem> feedItems;
    RecyclerView recyclerView;

    public ReadRssMain(Context context, RecyclerView recyclerView)
    {
        this.recyclerView=recyclerView;
        this.context=context;
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
    }
    @Override
    protected void onPreExecute() {
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
        MyAdapterMain adapter=new MyAdapterMain(context,feedItems);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new VerticalSpace(50));
    }

    @Override
    protected Void doInBackground(Void... params) {
        ProcessXml ((Getdata()));
        return null;
    }

    private void ProcessXml(Document data) {
        if (data != null) {

            feedItems=new ArrayList<Feeditem>();
            Element root=data.getDocumentElement();
            Node channel=root.getChildNodes().item(1);
            NodeList items=channel.getChildNodes();

            for (int i=0;i<items.getLength();i++)
            {

                Node cureentchild=items.item(i);
                if (cureentchild.getNodeName().equalsIgnoreCase("item")){
                    Feeditem item=new Feeditem();
                    NodeList itemchilds=cureentchild.getChildNodes();
                    for(int j=0;j<itemchilds.getLength();j++)
                    {
                        Node cureent=itemchilds.item(j);
                        if(cureent.getNodeName().equalsIgnoreCase("title"))
                        {
                            item.setTitle(cureent.getTextContent());
                        }

                        else if(cureent.getNodeName().equalsIgnoreCase("pubDate"))
                        {
                            item.setPubdate(cureent.getTextContent());
                        }
                        else if(cureent.getNodeName().equalsIgnoreCase("link"))
                        {
                            item.setLink(cureent.getTextContent());
                        }
                        else if(cureent.getNodeName().equalsIgnoreCase("media:content"))
                        {
                            String url=cureent.getAttributes().item(0).getTextContent();
                            item.setThumbnailUrl(url);
                        }

                    }
                    feedItems.add(item);

                }
            }
        }
    }

    public Document Getdata()
    {
        try {
            url=new URL(address);
            HttpURLConnection connection=(HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            DocumentBuilderFactory builderFactory=DocumentBuilderFactory.newInstance();
            DocumentBuilder builder=builderFactory.newDocumentBuilder();
            Document xmlDoc= builder.parse(inputStream);
            return  xmlDoc;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void onCancelled() {
        super.onCancelled();
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}



