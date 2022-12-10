package RetrieveFactoryPattern;

import com.example.bustrackingapp.RouteDetailsActivity;
import com.example.bustrackingapp.StudentFeedbackActivity;
import com.example.bustrackingapp.TimeBusActivity;

public class RetrieveFactory {

    public  static Retrieve getData(String data)
    {
        if(data == null){
            return null;
        }
        if(data.equalsIgnoreCase("timebus")){
            return new TimeBus();

        }
        if(data.equalsIgnoreCase("studentfeedback")){
            return new StudentFeedback();

        }
        if(data.equalsIgnoreCase("routeDetails")){
            return new RouteDetails();
        }

        return null;
    }
    }


