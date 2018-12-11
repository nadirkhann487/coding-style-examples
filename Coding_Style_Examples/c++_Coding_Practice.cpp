#include <iostream>
#include "Line.h"
#include <math.h>
using std::ostream;

Line::Line(){
   // Creates a line with both points null
  point1=nullptr;
  point2=nullptr;
}

// Creates a line defined by the two 
  // points given as parameters as pointers
  // to Point objects. If the Point objects
  // are equal, we have a degenerate line
  // that is a single point.
Line::Line(Point* p1, Point* p2){ 
	if(!p1 || !p2){
		point1=nullptr;point2=nullptr;
		return;
	}
  point1 = p1;
  point2 = p2;
  double xDistance = point1->getX()-point2->getX();
  double yDistance = point1->getY()-point2->getY();
  if(xDistance==0){
	  point1=nullptr;point2=nullptr;
  }else{
	  if(*point1 == *point2){
		 isDegenerateLine = true;
	  }
	  else {
		 isDegenerateLine = false;
		  slope = yDistance/xDistance; 
		  offset = point1->getY() - slope*point1->getX();
	  }
  }
}

 // Copy constructor 
  // Performs a shallow copy
Line::Line(const Line& other){
  point1 = other.point1;
  point2 = other.point2;
  offset = other.offset;
  slope = other.slope;
  isDegenerateLine = other.isDegenerateLine;
}


// Move constructor  
Line::Line(Line&& other){
  point1 = other.point1;
  point2 = other.point2;
  offset = other.offset;
  slope = other.slope;
  isDegenerateLine = other.isDegenerateLine;
}


 // Assignment operator (copy)
 // Performs a shallow copy
Line& Line::operator=(const Line& other){
  Point point1(*other.point1);
  point2 = other.point2;
  offset = other.offset;
  slope = other.slope;
  isDegenerateLine = other.isDegenerateLine;
  return *this;
}

 // Assignment operator (move)
Line& Line::operator=(Line&& other){
 
  point1 = other.point1;
  point2 = other.point2;
  offset = other.offset;
  slope = other.slope;
  isDegenerateLine = other.isDegenerateLine;
  return *this;
}
 
// destructor
Line::~Line(){
 
  delete point1;
  delete point2;
}

 // Returns true if the given point 
  // lies on the line and false otherwise 
bool Line::isIncident(const Point& point)const{
   
  bool isIncident=false;
  if(point.getY() == point.getX()*slope+offset){
	  isIncident=true;
  }
  return isIncident;
}

 // Compares two lines for equivalence.
  // Returns true if both lines have both of their
  // points set to non-null values, and 
  // the two pairs of points define the same line 
  // (that is, either both lines are single points
  // that are equal, or both are "proper" lines
  // and all points from the two pairs are on the
  // same line). Returns false otherwise.
bool Line::operator==(const Line& other) const{
   
  bool isEqual=false;
  if(approxEqual(slope,other.slope) && approxEqual(offset,other.offset)){
		isEqual=true;
  }else if(isDegenerateLine && isDegenerateLine){
	  isEqual = true;
  }else{
  }
  return isEqual;
}

// Returns the first Point pointer 
const Point* Line::getPoint1()const{
  Point *removeMe =nullptr;
  if(point1)
  removeMe=point1;
  return removeMe;
}

// Returns the second Point pointer etY());
}
const Point* Line::getPoint2()const{
  Point *removeMe =nullptr;
  if(point2)
  removeMe=point2;
  return removeMe;
}

 // Prints the coordinates of the two points
  // defining the line, or nothing if some of them
  // is not set (i.e., if some is a null pointer)
void Line::setData(Line& other){
	slope=other.slope;
	offset = other.offset;
	isDegenerateLine = other.isDegenerateLine;
	point1=new Point;
	point2=new Point;
	point1->setData(other.getPoint1()->getX(),other.getPoint1()->getY());
	point2->setData(other.getPoint2()->getX(),other.getPoint2()->getY());
}

 // Prints the coordinates of the two points
  // defining the line or nothing if some of them
  // is not set (i.e., if some is a null pointer)
void Line::print(std::ostream& stream) const{

	if(point1 && point2){
		stream<<point1->getX()<<point1->getY()<<point2->getX()<<point2->getY();

	}
}
  
// Prints the coordinates of the two points
  // defining the line, or nothing if some of them
  // is not set (i.e., if some is a null pointer)
ostream& operator<<(std::ostream& stream, const Line& line){
  	
	if(line.point1 && line.point2){
		stream<<line.point1->getX()<<" "<<line.point1->getY()<<" "<<line.point2->getX()<<" "<<line.point2->getY();		
	}
  return stream; 
}
