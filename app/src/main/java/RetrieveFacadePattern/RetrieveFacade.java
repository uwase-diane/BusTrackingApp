package RetrieveFacadePattern;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class RetrieveFacade {
    private Retrieves timebus;
    private Retrieves bustop;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> str;


    public RetrieveFacade(ArrayAdapter<String> arrayAdapter,ArrayList<String> str) {
        timebus = new TimeBus();
        bustop = new BusStop();
        this.arrayAdapter=arrayAdapter;
        this.str=str;
    }

    public void retrieveDataPreferredTime(){
        timebus.retrieveData(arrayAdapter,str);
    }
    public void retrieveDataBusStop(){
        bustop.retrieveData(arrayAdapter,str);
    }
}
