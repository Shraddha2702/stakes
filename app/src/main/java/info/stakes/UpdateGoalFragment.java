package info.stakes;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by SHRADDHA on 18-09-2016.
 */
public class UpdateGoalFragment extends Fragment {
    DatabaseHelper db;

    TextView amt;
    TextView goal;

    EditText newgoal;
    EditText newamt;
    Button goalupdate;

    TextView tv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_updategoal, container, false);

        tv = (TextView)v.findViewById(R.id.backup);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                SettingsFragment ss = new SettingsFragment();
                ft.replace(R.id.frame, ss);
                ft.commit();
            }
        });

        db= new DatabaseHelper(getActivity());

        goal= (TextView)v.findViewById(R.id.updfraggoalold);
        amt = (TextView)v.findViewById(R.id.updfragamtold);

        newgoal = (EditText)v.findViewById(R.id.updfraggoalnew);
        newamt = (EditText)v.findViewById(R.id.updfragamtnew);
        goalupdate = (Button)v.findViewById(R.id.btnupdategoal);

        Cursor res= db.getAllGoals();

        if(res.getCount()== 0)
        {
            Log.d("Update"," "+res.getCount());
        }
        else
        {
            res.moveToLast();

            goal.setText(res.getString(0));
            amt.setText(" "+res.getInt(1));

        }

        goalupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                String ngoal = newgoal.getText().toString();
                String a= newamt.getText().toString();
                int namt = Integer.parseInt(a);

                boolean isUpdated = db.UpdateGoal(ngoal,namt);

                if(isUpdated == true)
                {
                    Toast.makeText(getContext(),"Updated Successfully",Toast.LENGTH_LONG).show();
                    //GoalFragment gf = new GoalFragment();
                    //gf.printgoal.setText(ngoal);
                    //gf.printamount.setText(" "+namt);


                }
                else
                {
                    Toast.makeText(getContext(),"Not Updated",Toast.LENGTH_LONG).show();
                }}catch (Exception e){
                    Toast.makeText(getActivity(),"Enter some values",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }

}
