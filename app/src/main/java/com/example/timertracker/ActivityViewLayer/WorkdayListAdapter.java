package com.example.timertracker.ActivityViewLayer;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.timertracker.DetailClickListener;
import com.example.timertracker.Model.Workday;
import com.example.timertracker.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

public class WorkdayListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<Long>> _listDataChild;
    private List<Workday> workdayList;

    public WorkdayListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<Long>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }


    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        Workday parentWorkday = this.workdayList.get(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        this.configureButtonsInChildView(convertView, parentWorkday.getWorkIntervalls().get(childPosition), parentWorkday, childPosition);

        return convertView;
    }

    private void configureButtonsInChildView(View convertView, ArrayList<Long> currentWorkInterval, Workday parentWorkday, int childPosition) {

            TextView startTimeButton = convertView.findViewById(R.id.startTime);
            startTimeButton.setOnClickListener(new DetailClickListener(parentWorkday.getId(), childPosition, this._context, true));
            startTimeButton.setText(Workday.toHourMinuteString(currentWorkInterval.get(0)));

            TextView endTimeButton = convertView.findViewById(R.id.endTime);
            endTimeButton.setOnClickListener(new DetailClickListener(parentWorkday.getId(), childPosition, this._context, false));
            endTimeButton.setText(Workday.toHourMinuteString(currentWorkInterval.size() > 1 ? currentWorkInterval.get(1) : 0)); // in case the last workinterval is still running

    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

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

    public void setWorkdays(List<Workday> workdays){
        this.workdayList = workdays;
        ListIterator<Workday> workdayListIterator= workdays.listIterator();
        Workday currentWorkday;
        this._listDataHeader.clear();
        while(workdayListIterator.hasNext()) {
            currentWorkday = workdayListIterator.next();
            this._listDataHeader.add(currentWorkday.toString());

            ArrayList<Long> details = new ArrayList<>();

            ListIterator<ArrayList<Long>> listIteratorWorkIntervals = currentWorkday.getWorkIntervalls().listIterator();

            while(listIteratorWorkIntervals.hasNext()) {
                details.add((long) listIteratorWorkIntervals.nextIndex());
                listIteratorWorkIntervals.next();
            }

            this._listDataChild.put(currentWorkday.toString(), details);
        }
        notifyDataSetChanged();
    }

}
