
#include <iostream>
#include <cstdlib>
#include <pthread.h>
#include <stdlib.h>
#include <unistd.h>

using namespace std;

int NumOfSeats = 100 ;
#define COUNT_DONE 0
int turn = 0 ; 

void *Agency1(void *seats) {
    int** ptrSeats = (int **) seats ;
    while(NumOfSeats>0){

        while(turn!=0){
            int row = rand() % 2;
            int column = rand() % 50;
            
            //check if seat is reserved or not
            if (ptrSeats[row][column] == 0 )
            {
                cout << "Agency 1 Entered Critical Region" << endl ; 
                //mark seat as 1 for agency 1 if empty
                ptrSeats[row][column] =1 ; 
                //checking seat number based on row & column 
                if(row==0){
                    cout << "Seat number " << column+1 << " is reserved by Agency 1. "<<endl ; 
                }
                else{
                    cout << "Seat number " << column + 51 << " is reserved by Agency 1. "<<endl ; 
                }
                NumOfSeats-- ;
                cout << "Agency 1 exit Critical Region" << endl ; 
            }
           turn = 0 ; 
        }
    }
    return NULL ;
}

void *Agency2 (void *seats) {
   int** ptrSeats = (int **) seats ;
    while(NumOfSeats > 0){
        while(turn!=1){
            int row = rand() % 2;
            int column = rand() % 50;
            //check if seat is reserved or not
            if (ptrSeats[row][column] == 0 )
            {
                cout << "Agency 2 Entered Critical Region" << endl ; 
                //mark seat as 2 for agency 2 if empty
                ptrSeats[row][column] =2 ; 
                //checking seat number based on row & column 
                if(row==0){
                    cout << "Seat number " << column+1 << " is reserved by Agency 2. "<<endl ; 
                }
                else{
                    cout << "Seat number " << column + 51 << " is reserved by Agency 2. "<<endl ; 
                }
                NumOfSeats-- ;
                cout << "Agency 2 exit Critical Region" << endl ; 
            }
           //using strict turns 
           turn = 1 ; 
        }
    }
    return NULL ; 
}



int main () {
	
	
	// CS307 - Operating Systems HW 1
	//  simulate a simple Airline Reservation System which will involve
	//	accessing shared resources.
	
	
	// MAIN IDEA
	// -----------------------------
	// When a customer asks for a seat from a specific flight, the agency checks if
	// there is an empty seat in the aircraft and reserves it immediately (if there is any). Only one
	// reservations can be made on a seat, so there will be no overbooking. There will be one thread
	// per agency simulating the activities of that agency. When an agency thread is booking a seat,
	// the other agency will do busy waiting.
	
	// implement using POSIX threads
	
	
	//-----------------------------
    
    //random seed
    srand(time(NULL));
    //two threads creation
   pthread_t TravelAgency1, TravelAgency2;
   //use of dynamic memory
   //as threads can only access heap 
    int** seats ; 
    
    //initializations
    seats = new int*[2] ; 
    for(int i=0; i<2; i++){
        seats[i] = new int[50];
    }
    for(int i=0; i<2; i++){
        for(int j=0; j<50; j++){
            seats[i][j] = 0 ;
            //cout << seats[i][j]<< ' ';
        }
       // cout << endl ; 
    }
    

   
    //creating two threads, and sending it to function
   pthread_create(&TravelAgency1, NULL, &Agency1, (void *)seats); 
   
    pthread_create(&TravelAgency2, NULL, &Agency2, (void *)seats); 
   

   //joining the threads
   
   pthread_join(TravelAgency1, NULL);
   
   pthread_join(TravelAgency2, NULL);
   
   cout << "No Seats Left\n" << endl ; 
   cout << "Plane is full:\n" << endl ; 
   
      for(int i=0; i<2; i++){
        for(int j=0; j<50; j++){
            //seats[i][j] = 0 ;
            cout << seats[i][j]<< ' ';
        }
        cout << endl ; 
    }
   
   
   return 0 ;
   
}
