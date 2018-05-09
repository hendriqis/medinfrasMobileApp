package com.example.lenovo.medinfras.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.lenovo.medinfras.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Lenovo on 4/3/2018.
 */

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> _parentList;
    private HashMap<String, List<String>> _childList;

    public ExpandableListViewAdapter(Context context, List<String> parentList, HashMap<String,
            List<String>> childList) {
        this.context = context;
        this._parentList = parentList;
        this._childList = childList;
    }

    @Override
    public int getGroupCount() {
        return this._parentList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._parentList.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup
            parent) {

        String headerText = (String) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.group_drug, null);
        }

        TextView txtListParent = (TextView) convertView.findViewById(R.id.groupDrugTV);
        txtListParent.setTypeface(null, Typeface.BOLD);
        txtListParent.setText(headerText);

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._childList.get(this._parentList.get(groupPosition)).size();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this._childList.get(this._parentList.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    //Inflate child_drug.xml
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View
            convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.child_drug, null);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.childDrugTV);
        txtListChild.setText(childText);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
