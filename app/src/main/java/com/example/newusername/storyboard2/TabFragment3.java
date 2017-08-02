package com.example.newusername.storyboard2;

/**
 * Created by NewUsername on 7/28/2016.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TabFragment3  extends Fragment implements RecyclerView.OnClickListener {
    RecyclerView recyclerView;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(view==null)
        {
            view = inflater.inflate(R.layout.tab_fragment_3, container, false);
            recyclerView=(RecyclerView)view.findViewById(R.id.recyclerview);
            ReadRss1 readRss=new ReadRss1(getActivity(),recyclerView);
            readRss.execute();
            return view;
        }
        else
        {
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.removeView(view);
        }
        return view;
    }

    @Override
    public void onClick(View v) {

    }
}