using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;

namespace Application2
{

	// service interface to get the vacation period and number of destinations of the tour
    [ServiceContract]
    public interface HollydayPackageBroker
    {

        [OperationContract]
        int getVacationPeriod(string university, string timePeriod);

        [OperationContract]
        int getDestinations(double budget,string timePeriod);

       
    }

	// service interface to find the nearest airport and cost to get to that airport
    [ServiceContract]
    public interface TransportIdentifier
    {

        [OperationContract]
        string getAirport(string university,string timePeriod);

        [OperationContract]
        int getCost(string university);


    }

	// service interface to process payments and calculates total tour cost
    [ServiceContract]
    public interface ProcessPayments
    {

        [OperationContract]
        int calculateTourCost(string timPeriod, double budget);

        [OperationContract]
        string getPayment();


    }

    
}
