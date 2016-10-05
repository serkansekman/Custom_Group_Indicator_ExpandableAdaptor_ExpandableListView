package com.demo.serkansekman.apiexample;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by serkan.sekman on 10/3/2016.
 */
public class ExpandableAdaptor extends BaseExpandableListAdapter {

    Context context;
    List<PopularPersonItem> listNavItems;
    public LayoutInflater inflater;
    public ImageView img, img2;
    ExpandableListView expandlist;

    View child;


    public ExpandableAdaptor(Activity act, List<PopularPersonItem> listNavItems) {
        this.context = act;
        this.listNavItems = listNavItems;
        inflater = act.getLayoutInflater();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }


    /**
     * Gets the number of groups.
     *
     * @return the number of groups
     */
    @Override
    public int getGroupCount() {
        return listNavItems.size();
    }

    /**
     * Gets the number of children in a specified group.
     *
     * @param groupPosition the position of the group for which the children
     *                      count should be returned
     * @return the children count in the specified group
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return listNavItems.get(groupPosition).getMovies().size();
    }

    /**
     * Gets the data associated with the given group.
     *
     * @param groupPosition the position of the group
     * @return the data child for the specified group
     */
    @Override
    public Object getGroup(int groupPosition) {
        return listNavItems.get(groupPosition);
    }

    /**
     * Gets the data associated with the given child within the given group.
     *
     * @param groupPosition the position of the group that the child resides in
     * @param childPosition the position of the child with respect to other
     *                      children in the group
     * @return the data of the child
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listNavItems.get(groupPosition).getMovies().get(childPosition);
    }

    /**
     * Gets the ID for the group at the given position. This group ID must be
     * unique across groups. The combined ID (see
     * {@link #getCombinedGroupId(long)}) must be unique across ALL items
     * (groups and all children).
     *
     * @param groupPosition the position of the group for which the ID is wanted
     * @return the ID associated with the group
     */
    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    /**
     * Gets the ID for the given child within the given group. This ID must be
     * unique across all children within the group. The combined ID (see
     * {@link #getCombinedChildId(long, long)}) must be unique across ALL items
     * (groups and all children).
     *
     * @param groupPosition the position of the group that contains the child
     * @param childPosition the position of the child within the group for which
     *                      the ID is wanted
     * @return the ID associated with the child
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    /**
     * Indicates whether the child and group IDs are stable across changes to the
     * underlying data.
     *
     * @return whether or not the same ID always refers to the same object
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * Gets a View that displays the given group. This View is only for the
     * group--the Views for the group's children will be fetched using
     * {@link #getChildView(int, int, boolean, View, ViewGroup)}.
     *
     * @param groupPosition the position of the group for which the View is
     *                      returned
     * @param isExpanded    whether the group is expanded or collapsed
     * @param convertView   the old view to reuse, if possible. You should check
     *                      that this view is non-null and of an appropriate type before
     *                      using. If it is not possible to convert this view to display
     *                      the correct data, this method can create a new view. It is not
     *                      guaranteed that the convertView will have been previously
     *                      created by
     *                      {@link #getGroupView(int, boolean, View, ViewGroup)}.
     * @param parent        the parent that this view will eventually be attached to
     * @return the View corresponding to the group at the specified position
     */
    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, final ViewGroup parent) {
        final PopularPersonItem popularPersonItem = (PopularPersonItem) getGroup(groupPosition);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_nav_list, null);
        }

        final TextView name = (TextView) convertView.findViewById(R.id.name);
        ImageView profile_path = (ImageView) convertView.findViewById(R.id.profile_path);

        name.setText(popularPersonItem.getName());


        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/w185/" + popularPersonItem.getProfile_path())
                .resize(100, 120)
                .centerCrop()
                .into(profile_path);


        // initialize ImageView


//kapat butonu clik aksiyonu
        final ImageView close = (ImageView) convertView.findViewById(R.id.closebtn);

        close.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                listNavItems.remove(groupPosition);
                notifyDataSetChanged();

                CharSequence text = popularPersonItem.getName().toString() + " listeden çıkartılmıştır"; //name.setText(popularPersonItem.getName());
                int duration = Toast.LENGTH_SHORT;


                Toast toast = Toast.makeText(context, text, duration);
                toast.setGravity(Gravity.BOTTOM, 0, 10);
                toast.show();

            }
        });


//Expand butonu click aksiyonu
        final ImageButton img = (ImageButton) convertView.findViewById(R.id.imgExpandCollapse);

        final View finalConvertView = convertView;
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ExpandableListView listview = (ExpandableListView) parent;


                if (isExpanded) {

                    img.setBackgroundResource(R.drawable.spinnerbuton);
                    listview.collapseGroup(groupPosition);

                } else {
                    img.setBackgroundResource(R.drawable.spinnerbutonchack);
                    listview.expandGroup(groupPosition);


                }

            }

        });


        // check if GroupView is expanded and set imageview for expand/collapse-action


        return convertView;
    }

    /**
     * Gets a View that displays the data for the given child within the given
     * group.
     *
     * @param groupPosition the position of the group that contains the child
     * @param childPosition the position of the child (for which the View is
     *                      returned) within the group
     * @param isLastChild   Whether the child is the last child within the group
     * @param convertView   the old view to reuse, if possible. You should check
     *                      that this view is non-null and of an appropriate type before
     *                      using. If it is not possible to convert this view to display
     *                      the correct data, this method can create a new view. It is not
     *                      guaranteed that the convertView will have been previously
     *                      created by
     *                      {@link #getChildView(int, int, boolean, View, ViewGroup)}.
     * @param parent        the parent that this view will eventually be attached to
     * @return the View corresponding to the child at the specified position
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        MovieDetail movieDetail = (MovieDetail) getChild(groupPosition, childPosition);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.movie_nav_list, null);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name);
        ImageView profile_path = (ImageView) convertView.findViewById(R.id.profile_path);
        TextView movie = (TextView) convertView.findViewById(R.id.title);

        name.setText(movieDetail.getName());

        movie.setText("Oyuncunun Oynadığı Filmler");

        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/w185/" + movieDetail.getImage())
                .resize(100, 120)
                .centerCrop()
                .into(profile_path);


        View child = (View) convertView;

        child.setVisibility(View.GONE);

        if (child.getVisibility() == View.GONE) {
            Animation out = AnimationUtils.makeInAnimation(child.getContext(), true);
            child.startAnimation(out);
            child.setVisibility(View.VISIBLE);
        } else {
            Animation in = AnimationUtils.loadAnimation(child.getContext(), R.anim.slide_up);
            child.startAnimation(in);
            child.setVisibility(View.GONE);


        }


        return convertView;
    }

    /**
     * Whether the child at the specified position is selectable.
     * public android.content.Context getApplicationContext() {
     * return applicationContext;
     * }
     *
     * @param groupPosition the position of the group that contains the child
     * @param childPosition the position of the child within the group
     * @return whether the child is selectable.
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


}
