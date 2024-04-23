#include <bits/stdc++.h>
using namespace std;
void display(int calender[], string seminarName[], string speakerName[], string time[], int code, int size, int seats[])
{

  for (int i = 0; i < size; i++)
  {
    // cout<<"\ndate="<<calender[i]<<endl;
  }
  cout << "\nDATE: " << calender[code];
  cout << "\nSeminar name: " << seminarName[code];
  cout << "\nSpeakername: " << speakerName[code];
  cout << "\nTIME: " << time[code];
  cout << "\n\n.........Available Seats: " << seats[code] << " ..........." << endl;
}

int find_date(int calender[], int size, int date)
{
  int empty = 1;
  for (int i = 0; i < size; i++)
  {
    if (calender[i] == date)
    {
      empty = 0; // if not empty
      return 0;
      break;
    }
  }
  if (empty)
    return 1;
}
void Authority(int codearray[], int calender[], string seminarName[], string speakerName[], string time[], int code, int size, int seats[])
{
  int d = 0, date, i = 0;
  int codedecision = 0;

  int booked;
  cout << "\nHave any booked slot?\n 1.YES\n 2.NO?\n";
  cin >> booked;
  if (booked == 2)
  {

    while (d == 0)
    {
      cout << "Enter Your Desired Date:";
      cin >> date;
      d = find_date(calender, size, date); // if not empty this date then return 0; n is the size os array
      // cout<<"\nSTATEMENT===="<<d<<endl;
      if (d == 0)
        cout << "\n*******OOP's This slot is Already Booked*******" << endl;
      else
      {

        cout << "\nYES, This slot is Empty.";
        while (codedecision == 0)
        {

          cout << "\nKeep code for your seminar(0-6): ";
          cin >> code;
          codedecision = find_date(codearray, size, code);
          if (codedecision == 0)
          {
            cout << "\nOOP's, Invalid code!\nPlease try Again." << endl;
          }
          else
          {
            codearray[i] = code;
            i++;
          }
        }

        calender[code] = date;
      }
    }
    cout << "Taking Information About Seminar:" << endl;

    cout << "\nEnter the Seminar Name:";
    cin >> seminarName[code]; // index is date for store informataion in a specific date.
    cout << "\nEnter the speakername: ";
    cin >> speakerName[code];
    cout << "\nEnter The Time:";
    cin >> time[code];
    cout << "\nEnter the Number the seats:";
    cin >> seats[code];
  }

  else
  {
    int decision;
    cout << "\nIf you want to see your slot please enter 1 otherwise 2 : ";
    cin >> decision;
    if (decision == 1)
    {
      cout << "\nPLease Enter Your Seminar Code: ";
      cin >> code;
      display(calender, seminarName, speakerName, time, code, size, seats);
    }
    else
      cout << "THANK YOU" << endl;
  }
}

void Audience(int codearray[], int calender[], string seminarName[], string speakerName[], string time[], int code, int size, int seats[7]);
int main()
{
  // variables(3-rows) declaration for Authority and display;
  int input, run = 1, size = 7, code;
  int calender[7], codearray[7], seats[7];
  string seminarName[7], speakerName[7], time[7];
  // variables declaration for Audience;

  while (run = 1)
  {

    cout << "\nWho are you? \n1. Authority\n2. Audience" << endl;
    cin >> input;

    if (input == 1)
    {
      Authority(codearray, calender, seminarName, speakerName, time, code, size, seats);
    }
    else
    {
      Audience(codearray, calender, seminarName, speakerName, time, code, size, seats);
    }

    cout << "\nARE YOU WANT TO RUN AGAIN THIS PROGRAMME\n 1.YES\n 2.NO" << endl;
    cin >> run;
    if (run == 2)
    {
      cout << "------------THANK YOU--------------" << endl;
      break;
    }
  }

  return 0;
}

void registration(int seats)
{
  //seats--;
  cout<<"Your seat number: "<<seats<<endl;
}

    void Audience(int codearray[], int calender[], string seminarName[], string speakerName[], string time[], int code, int size, int seats[])
{
  cout << "\nwant to see seminar(1/2): ";
  int c;
  cin >> c;
  if (c == 1)
  {
    cout << "\nEnter the code: ";
    int code;
    cin >> code;
    display(calender, seminarName, speakerName, time, code, size, seats);

    int reg;
    cout << "Are You want to Register for this Seminar(1/2): ";
    cin >> reg;
    if (reg == 1)
    {
       seats[code]--;
       registration(seats[code]);
    }
  }
}