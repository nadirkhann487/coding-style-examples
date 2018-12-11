using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;

namespace Application2
{
    //WCF services to be deployed on Azure Cloud
    public class Service1 : HollydayPackageBroker, TransportIdentifier, ProcessPayments
    {

	//find number of destinations to be visited by the student
        public int getDestinations(double budget, string timePeriod)
        {
            if(timePeriod == "SUMMER")
            {
                return returnSummerDestinationsCount(budget);
            }
            if (timePeriod == "EASTER")
            {
                return returnEasterDestinationsCount(budget);
            }
            if (timePeriod == "CHRISTMAS")
            {
                return returnChristmasDestinationsCount(budget);
            }
            return 0;
        }

	//Gives number of destinations that could be visited in summer with a given budget
        public int returnSummerDestinationsCount(double budget)
        {
            if (budget >= 40 && budget <= 100)
                return 2;
            if (budget > 100 && budget <= 200)
                return 3;
            if (budget > 200 && budget <= 400)
                return 5;
            if (budget > 400 && budget <= 600)
                return 6;
            if (budget > 600)
                return 10;


            return 0;
        }

	//Gives number of destinations that could be visited in easter with a given budget
        public int returnEasterDestinationsCount(double budget)
        {
            if (budget >= 40 && budget <= 100)
                return 2;
            if (budget > 100 && budget <= 200)
                return 3;
            if (budget > 400)
                return 5;
           
            return 0;
        }

	//Gives number of destinations that could be visited in Christmas with a given budget
        public int returnChristmasDestinationsCount(double budget)
        {
            if (budget >= 40 && budget <= 100)
                return 2;
            if (budget > 100 && budget <= 200)
                return 3;
            if (budget > 400 && budget <=600)
                return 5;
            if (budget > 600 )
                return 6;
           
            return 0;
        }

	//Finds vacation period from the university term time 
        public int getVacationPeriod(string university,string timePeriod)
        {
            if(timePeriod == "SUMMER")
            {
                return getSummerVacationperiod(university);
            }
            if (timePeriod == "EASTER")
            {
                return getEasterVacationperiod(university);
            }
            if (timePeriod == "CHRISTMAS")
            {
                return getChristmasVacationperiod(university);
            }
            return 0;
        }

	//Finds vacation period during summer for different universities
        public int getSummerVacationperiod(string university)
        {
            if (university == "LEICESTER")
                return 90;
            if (university == "NOTTINGHAM")
                return 92;
            if (university == "DERBY")
                return 90;
            if (university == "LONDON")
                return 95;
            if (university == "YORK")
                return 91;

            return 0;
        }

	//Finds vacation period during christmas for different universities
        public int getChristmasVacationperiod(string university)
        {
            if (university == "LEICESTER")
                return 23;
            if (university == "NOTTINGHAM")
                return 25;
            if (university == "DERBY")
                return 22;
            if (university == "LONDON")
                return 25;
            if (university == "YORK")summer
                return 24;

            return 0;
        }

	//Finds vacation period during easter for different universities
        public int getEasterVacationperiod(string university)
        {
            if (university == "LEICESTER")
                return 32;
            if (university == "NOTTINGHAM")
                return 31;
            if (university == "DERBY")
                return 33;
            if (university == "LONDON")
                return 35;
            if (university == "YORK")
                return 35;

            return 0;
        }

	//Get nearest airport to the given university
        public string getAirport(string university,string timePeriod)
        {
            if (university == "LEICESTER")
                return getLeicesterAirport(timePeriod);
            if (university == "NOTTINGHAM")
                return getNottinghamAirport(timePeriod);
            if (university == "DERBY")
                return getDerbyAirport(timePeriod);
            if (university == "LONDON")
                return getLondonAirport(timePeriod);
            if (university == "YORK")
                return getYorkAirport(timePeriod);

            return null;
        }

	//Finds nearest airport to Leicester
        public string getLeicesterAirport(string timePeriod)
        {
            if (timePeriod == "SUMMER")
            {
                return "Birmingham";
            }
            if (timePeriod == "EASTER")
            {
                return "Heathrow";
            }
            if (timePeriod == "CHRISTMAS")
            {
                return "Stansted";
            }
            return null;
        }

	//Finds nearest airport to Derby
        public string getDerbyAirport(string timePeriod)
        {
            if (timePeriod == "SUMMER")
            {
                return "Leeds";
            }
            if (timePeriod == "EASTER")
            {
                return "Birmingham";
            }
            if (timePeriod == "CHRISTMAS")
            {
                return "Heathrow";
            }
            return null;
        }

	//Finds nearest airport to London
        public string getLondonAirport(string timePeriod)
        {
            if (timePeriod == "SUMMER")
            {
                return "Heathrow";
            }
            if (timePeriod == "EASTER")
            {
                return "Stansted";
            }
            if (timePeriod == "CHRISTMAS")
            {
                return "Birmingham";
            }
            return null;
        }

	//Finds nearest airport to Nottingham
        public string getNottinghamAirport(string timePeriod)
        {
            if (timePeriod == "SUMMER")
            {
                return "Stansted";
            }
            if (timePeriod == "EASTER")
            {
                return "Birmingham";
            }
            if (timePeriod == "CHRISTMAS")
            {
                return "Heathrow";
            }
            return null;
        }

	//Finds nearest airport to York
        public string getYorkAirport(string timePeriod)
        {
            if (timePeriod == "SUMMER")
            {
                return "Leeds";
            }
            if (timePeriod == "EASTER")
            {
                return "Manchester";
            }
            if (timePeriod == "CHRISTMAS")
            {
                return "Manchester";
            }
            return null;
        }

	//Get cost to get to respective airport 
        public int getCost(string airport)
        {
            if (airport == "Heathrow")
                return 40;
            if (airport == "Manchester")
                return 30;
            if (airport == "Leeds")
                return 350;
            if (airport == "Stansted")
                return 20;
            if (airport == "Birmingham")
                return 42;

            return 0;
        }

	//Process poyment and generates a unique reference number
        public string getPayment()
        {
            Random r = new Random();
            const string chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            return new string(Enumerable.Repeat(chars, 10)
              .Select(s => s[r.Next(s.Length)]).ToArray());
        }

	//Calculate total cost of the tour
        public int calculateTourCost(string timPeriod,double budget)
        {
            if (timPeriod == "SUMMER")
               return returnSummerCost(budget);
            if (timPeriod == "EASTER")
                return returnEasterCost(budget);
            if (timPeriod == "CHRISTMAS")
                return returnChristmasCostt(budget);

            return 0;
        }

	//Calculate total cost of the tour if travelling in summer
        public int returnSummerCost(double budget)
        {
            if (budget >= 40 && budget <= 100)
                return 40;
            if (budget > 100 && budget <= 200)
                return 120;
            if (budget > 200 && budget <= 400)
                return 300;
            if (budget > 400 && budget <= 600)
                return 450;
            if (budget > 600)
                return 600;


            return 0;
        }

	//Calculate total cost of the tour if travelling in easter
        public int returnEasterCost(double budget)
        {
            if (budget >= 40 && budget <= 100)
                return 40;
            if (budget > 100 && budget <= 200)
                return 100;
            if (budget > 400)
                return 400;

            return 0;
        }

	//Calculate total cost of the tour if travelling in christmas
        public int returnChristmasCostt(double budget)
        {
            if (budget >= 40 && budget <= 100)
                return 30;
            if (budget > 100 && budget <= 200)
                return 100;
            if (budget > 400 && budget <= 600)
                return 450;
            if (budget > 600)
                return 500;

            return 0;
        }

    }
}
