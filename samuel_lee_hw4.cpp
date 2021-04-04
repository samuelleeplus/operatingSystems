#include<iostream>
#include<fstream>
#include <bits/stdc++.h> 

using namespace std;



int main()
{
	
	// CS307 Hw 4
	// Samuel Lee


	// Speed & Performance comparison
	// C++ language using fstream/istream 
	
	

	clock_t start, end;

	ifstream fin("lorempisum.txt");

	int occurence = 0; 


	start = clock(); 

	fin.open("loremipsum.txt");

	char my_character = 'a' ;
	char ch;
	
	
	while (fin) {
		 
		
		fin.get(ch);
		if (ch == 'a') {
			occurence++;
		}
	}

	end = clock(); 

	double time_taken = double(end - start) / double(CLOCKS_PER_SEC);
	cout << "Time taken by program is : " << fixed
		<< time_taken; 
	cout << " sec " << endl;

	cout << "NUMBER OF 'a': " << occurence << endl;

	return 0; 

}
