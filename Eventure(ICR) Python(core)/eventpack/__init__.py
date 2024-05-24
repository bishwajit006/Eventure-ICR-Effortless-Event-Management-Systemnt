import numpy as np
def display(matrix,code):
    i=code-1  #for proper indexing
    j=0
    for j in range(5):
        if j==0:
            print("Seminar Name: ",matrix[i][j])
        elif j==1:
            print("Speaker Name: ",matrix[i][j])
        elif j==2:
            print("Date: ",matrix[i][j])
        elif j==3:
            print("Time: ",matrix[i][j])
        else:
            print("Available Seats: ",matrix[i][j])
    print("--------Thank You-------\n\n")


def find_date(calender,date):
    for i in calender:
        if i==date:
            return 0
    return 1

#Authority Part
def authority(matrix,code):
    booked = int(input("\nHAve any Booked Slot?\n1.YES\n2.NO\n-->"))
    d=0
    #codes=0
    codedecision = 0
    if booked == 2:
        while d==0:
            date = int(input("\nEnter Your Desire Date: "))
            d = find_date(calender,date)
            if d==0:
                print("*******OOP's This slot is Already Booked!")
            else:
                print("YES, This slot is Empty.")
                while codedecision == 0:

                    print("\nYour Seminar Code Is: ",len(matrix)+1)
                    code = int(input("\nKeep Code for Your Seminar: "))
                    codedecision = find_date(codearray,code)  #if have same code for another seminar

                    if codedecision == 0:
                        print("\nOOP's Invalid Code!\nPlease Try Again.")
                    else:
                        codearray.append(code)
                calender.insert(code,date)
        a=[]
        print("\nTaking Information for Seminar")
        a.append( input("\nEnter the Seminar Name: "))
        a.append ( input("\nEnter the Speaker name: "))
        a.append(date)
        a.append ( input("\nEnter the Time: "))
        a.append( int(input("\nEnter the Number of Seats: ")))
        matrix.append(a)
        code+=1
    else:
        decision = 0
        decision = int(input("\nIf You want see your Booked Slot, Please Enter:\n1.YES\n2.NO\n-->"))
        if decision == 1:
            c = int(input("\nEnter Your Seminar Code: "))
            display(matrix,c)
        else:
            print("---THANK YOU---")




#Audience Part
def registration(audienceList, seat):
     aa=[]
     print("Your Seat Number: ",seat)
     print("Fill The Requirment Form with Appropriate Information: ")
     aa.append(input("Name: "))
     aa.append(input("Student/Faculty ID: "))
     aa.append(input("Department: "))
     aa.append(input("Batch: "))

def audience(matrix, audienceList):
    s=int(input("Want To See Seminar (1/2): "))
    if s==1:
        code = int(input("Enter The Seminar Code: "))
        display(matrix,code)
        r = int(input("Are You Want To Register For This Seminar? (1/2) :"))
        if r==1:
            row = code-1
            cols = len(matrix[0])  #total numbers of column
            arr = [[row] * cols]  #all elements of a row

            seat = matrix[row][4]
            matrix[row][4] -=1  #decrementing available seats number
            registration(audienceList, seat)






#Main Function
codearray = seminarname = speakername = calender = time = seats= []
run = 1
matrix=[] #seminar information
audienceList = []  #Audience information
code=0

while run==1:
    inp = int(input("What You will be?\n1. Authority\n2. Audience\n-->"))
    if inp == 1:
        authority(matrix, code)
    else:
        audience(matrix, audienceList)
    run = int(input("Are You Want to Run Again This Programme?\n1.YES\n2.NO\n-->"))
    if run == 2:
        print("\n       ------------THANK YOU------------")
        print("\n--------------FOR USING OUR SYSTEM---------------")
        break