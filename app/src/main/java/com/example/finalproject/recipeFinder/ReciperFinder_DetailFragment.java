package com.example.finalproject.recipeFinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.finalproject.R;
import androidx.fragment.app.Fragment;

public class ReciperFinder_DetailFragment extends Fragment {
    /**
     * START Class for Fragment Detail, Class extends Fragment
     * @param isTablet; initialize local boolean variable to determine layout
     * @param dataFromActivity; Object used in Bundle to pass data from activity to fragment
     * @param id; long local variable to determine which item was selected from the activity to display in fragment
     * @param buttonSaveRecipe; initialize Button and reference to XML
     */
    private boolean isTablet;
    private Bundle dataFromActivity;
    private long id;
    private Button buttonSaveRecipe;

    public void setTablet(boolean tablet) {
        isTablet = tablet;
    } // End setTablet method

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    /**
 * START View onCreateView() layout inflater for either Phone or Tablet v iew
 * @param dataFromActivity; getArguments() or pull data from one activity to fragment
 * @param ITEM_ID; the item selected from RecipeFinder = Display in fragment
 * @param recipes; initialize a TextView object and XML reference
 * @param idView; initialize a TextView object and XML reference
 * @param deleteButton; initialize a TextView object and XML reference
 */
        dataFromActivity = getArguments();
        id = dataFromActivity.getLong(RecipeFinder.ITEM_ID);

        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.activity_recipe_fragment, container, false);

        //show the message
        TextView recipes = (TextView) result.findViewById(R.id.reicpeListPull);
        recipes.setText(dataFromActivity.getString(RecipeFinder.ITEM_SELECTED));

        //show the id:
        TextView idView = (TextView) result.findViewById(R.id.idText);
        idView.setText("ID=" + id);

        // get the delete button, and add a click listener:
        Button deleteButton = (Button) result.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(clk -> {

            if (isTablet) { //both the list and details are on the screen:
                RecipeFinder parent = (RecipeFinder) getActivity();
                parent.deleteMessageId((int) id); //this deletes the item and updates the list
                //now remove the fragment since you deleted it from the database:
                // this is the object to be removed, so remove(this):
                parent.getSupportFragmentManager().beginTransaction().remove(this).commit();

            } // End if statement
            //for Phone:
            else //You are only looking at the details, you need to go back to the previous list page
            {
                EmptyActivity parent = (EmptyActivity) getActivity();
                Intent backToChatRoomActivity = new Intent();
                backToChatRoomActivity.putExtra(RecipeFinder.ITEM_ID, dataFromActivity.getLong(RecipeFinder.ITEM_ID));

                parent.setResult(Activity.RESULT_OK, backToChatRoomActivity); //send data back to FragmentExample in onActivityResult()
                parent.finish(); //go back
            } // End else statement
        }); // End delete button setOnClickListener()

        Button saveButton = (Button) result.findViewById(R.id.saveRecipeButton);
        saveButton.setOnClickListener(clk -> {

        }); // End save button setOnClickListener()
        return result;

    } // End OnCreateView()
}  //End RecipeFinder_DetailFragment







