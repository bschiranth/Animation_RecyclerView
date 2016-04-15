package com.example.bschiranth.animation_hw10_chiranth;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.PopupMenu;

import java.util.HashMap;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

//import android.support.v7.view.ActionMode;

/**
 * A simple {@link Fragment} subclass.
 */
public class recyclerViewFragment extends Fragment {
  //  HashMap movie = new HashMap();
    private MovieData md ;
    private static final String str = "movie";

     MyRecyclerViewAdapter mRecyclerViewAdapter;
     RecyclerView mRecyclerView;

    //interface to handle ticking and clearing check boxes
    public interface tickCheckBox{
        public void puttick(MovieData movieData, HashMap movie);
        public void cleartick(MovieData movieData, HashMap movie);
        public void loadMovie(int position, HashMap movie,View view);

    }
  //  public interface movInterface{public void callMovie(HashMap<String,?> hashMap);}

    public recyclerViewFragment() {
        // Required empty public constructor
    }

///////////////////////////

    /////////////////////////

    ///////////////////////
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);//tell that options menu is there
        md= new MovieData();

        setRetainInstance(true);
    }
/////////////////////////


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


       // final movInterface mi;
      //  mi = (movInterface) getContext();

        final View rootView = inflater.inflate(R.layout.recycler_view_fragment_layout,container,false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv2);
        mRecyclerView.setHasFixedSize(true); // to fix recycle size

        ///////////////////////////////

        ////////////////////////////////////////
        final tickCheckBox tick;
        tick = (tickCheckBox) getContext();

////////////////////////////////////////
                //layout manager needed
                LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(llm); //attach it to recycler view

        //adapter
        mRecyclerViewAdapter = new MyRecyclerViewAdapter(getActivity(),md.getMoviesList());
        mRecyclerView.setAdapter(mRecyclerViewAdapter);


        mRecyclerViewAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {

                @Override
                public void onItemClick(View v, int position) {
                    HashMap<String, ?> movie = (HashMap<String, ?>) md.getItem(position);
                   //   ImageView imageView = (ImageView)v.findViewById(R.id.imageId);
                    v.setTransitionName((String) movie.get("name"));//setname
                            tick.loadMovie(position, movie, v);
                }

                @Override
                public void onItemLongClick(View v, int position) {

                 getActivity().startActionMode(new ActionBarCallBack(position));
                }

                @Override
                public void onOverflowMenuClick(View v, final int position) {
                    //new constructor with anchor view
                    PopupMenu popupMenu = new PopupMenu(getActivity(), v);

                    MenuInflater menuInflater = popupMenu.getMenuInflater();//inflate the options and show
                    menuInflater.inflate(R.menu.popupmenu_layout, popupMenu.getMenu());//get the popupmenu inside inflate method
                    popupMenu.show();

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            //List<Map<String,?>> mList = md.getMoviesList();
                            switch (item.getItemId()) {
                                case R.id.item_duplicate:
                                    md.addItem(position);
                                    mRecyclerViewAdapter.notifyItemInserted(position);
                                    return true;
                                case R.id.item_delete:
                                    md.removeItem(position);
                                    mRecyclerViewAdapter.notifyItemRemoved(position);
                                //   mRecyclerViewAdapter.notifyItemRangeChanged(position ,mList.size() );
                                    return true;
                                default:
                                    return false;
                            }

                        }//end of setonmenuitemclick
                    });//end of Menu
                }//end of overflowclick
            });//end of recyler view onitem click

        itemAnimation();
        adapterAnimation();

        // Inflate the layout for this fragment
        return rootView;
    }//end of oncreateview
    /////animation

    public void itemAnimation(){
       SlideInLeftAnimator animator = new SlideInLeftAnimator();
        animator.setInterpolator(new OvershootInterpolator());
        animator.setAddDuration(10);
        animator.setRemoveDuration(100);


      /*  LandingAnimator animator = new LandingAnimator();
        animator.setAddDuration(3000);
        animator.setRemoveDuration(2000);

        FadeInAnimator animator = new FadeInAnimator();
        animator.setAddDuration(3000);
        animator.setRemoveDuration(2000);*/

        mRecyclerView.setItemAnimator(animator);
    }

    public void adapterAnimation()
    {
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mRecyclerViewAdapter);
        ScaleInAnimationAdapter scaleAdapter  = new ScaleInAnimationAdapter(alphaAdapter);
        mRecyclerView.setAdapter(scaleAdapter);
    }


    public static recyclerViewFragment newInstance(int sectionNumber) {
        recyclerViewFragment rf = new recyclerViewFragment();
        Bundle args = new Bundle();
        args.putSerializable(str, sectionNumber);
        rf.setArguments(args);
        rf.setRetainInstance(true);
        return rf;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

       if(menu.findItem(R.id.seachmovieid)==null) inflater.inflate(R.menu.menu_actionview,menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.seachmovieid).getActionView();


        if(searchView!=null)
        {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    int position = md.findFirst(query);
                    if(position>=0) mRecyclerView.scrollToPosition(position);
                    ///////////////////////
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return true;
                }
            });
        }
        super.onCreateOptionsMenu(menu, inflater);
    }
///////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    class ActionBarCallBack implements ActionMode.Callback{

        int position;
    public ActionBarCallBack(int position) {this.position = position;}//constructor

    @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.contextual_menu_layout,menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int switch_id = item.getItemId();

            switch (switch_id)
            {
                case R.id.item_duplicate_id:
                    md.addItem(position);
                    mRecyclerViewAdapter.notifyItemInserted(position);
                    mode.finish();
                    break;
                case R.id.item_delete_id:
                    md.removeItem(position);
                    mRecyclerViewAdapter.notifyItemRemoved(position);
                    mode.finish();
                    break;
                default:break;
            }

            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }
    }//end of action bar callback class
    /////////////////////////////////////////////////////////////
}//end of fragment class



