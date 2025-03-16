import java.util.*;
class busManagement
{
	int busNo;
	String from,to;
	int busCapacity;
	int busPrices;
	String departureTime, arrivalTime;
	List <ticketBooking> bookingDetails;
	List<Integer> bookedSeats;
	static Scanner sc=new Scanner(System.in);
	static double bill=0;
	
	// Constructor to initialize bus details
	busManagement(int busNo,String from,String to,int busCapacity,int busPrices, String departureTime, String arrivalTime)
	{
		this.busNo=busNo;
		this.from=from;
		this.to=to;
		this.busCapacity=busCapacity;
		this.busPrices=busPrices;
		this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
		this.bookingDetails = new ArrayList<>();
		this.bookedSeats = new ArrayList<>();
	}
	
	// Displays a list of available buses and takes user input for bus choice
	String yourChoice()
	{
		System.out.println("\n========== LIST OF BUSES  ==========");
		System.out.println("\n1. ahmedabad to baroda ");
		System.out.println("2. ahmedabad to rajkot ");
		System.out.println("3. surat to ahmedabad ");
		System.out.println("4. gandhinagar to ahmedabad ");
		System.out.println("5. ahmedabad to giftcity ");
		String chosenNo;
		while(true)
		{	
			System.out.print("\nEnter bus number for check details : ");
			chosenNo=sc.nextLine();
			if(!chosenNo.matches("\\d+"))
			{
				System.out.println("Enter valid numeric seat number");
				continue;
			}
			return chosenNo;
		}
	}
	
	// Displays details of the selected bus
	void busDetails()
	{
		System.out.println("==============================");
        System.out.println("        BUS DETAILS");
        System.out.println("==============================");
		System.out.println("Bus No : "+this.busNo);
		System.out.println("From "+this.from+" to "+this.to);
		System.out.println("Departure Time: " + this.departureTime);
		System.out.println("Arrival Time   : " + this.arrivalTime);
		System.out.println("Bus Capacity : "+this.busCapacity);
		System.out.println("Bus Ticket Price : "+this.busPrices);
		System.out.println("==============================");
	}
	
	// Takes user input for desired departure time and validates it
	String checkTime()
	{
		while(true)
		{
			System.out.print("Enter desired departure time (e.g., 06:00 AM):");
			String time = sc.nextLine();
			if (time.length() == 8 && time.charAt(2) == ':' &&
			Character.isDigit(time.charAt(0)) &&
			Character.isDigit(time.charAt(1)) &&
			Character.isDigit(time.charAt(3)) &&
			Character.isDigit(time.charAt(4)) &&
			time.substring(6).equalsIgnoreCase("AM") || 
			time.substring(6).equalsIgnoreCase("PM")
			) 
			{
				return time;
			}
			else
			{
				System.out.println("Enter valid time.");
			}
		}
	}
	
	// Handles the booking process: takes number of seats, checks availability, and calls bookSeat()
	void booking()
	{
		String checkSeats;
		int totalSeats;
		while(true)
		{
			System.out.print("How many seats you want to book ? :");
			checkSeats=sc.nextLine();
			if(!checkSeats.matches("\\d+"))
			{
				System.out.println("Enter valid numeric seat number");
				continue;
			}
			totalSeats = Integer.parseInt(checkSeats);
			break;
		}
		
		if (this.bookedSeats.size() >= this.busCapacity)
		{
            System.out.println("Sorry, the bus is full.");
            return;
        }
		else if((this.busCapacity-this.bookedSeats.size())<totalSeats)
		{
			while(true)
			{
				System.out.print("\nThere is "+(this.busCapacity-this.bookedSeats.size())+" seat available.");
				System.out.print("\nDo you want to continue with that seat (yes/no) :");
				String ans = sc.nextLine();
				if((ans.equalsIgnoreCase("yes")))
				{
					totalSeats = this.busCapacity-this.bookedSeats.size();
					bookSeat(totalSeats);
				}
				else if((ans.equalsIgnoreCase("no")))
				{
					return;
				}
				else
					System.out.println("\nEnter ans in only yes or no");
			}
		}
		else
			bookSeat(totalSeats);
	}
	
	// Books the specified number of seats, taking passenger details and applying discounts
	void bookSeat(int totalSeats)
	{
		int totalCost = 0;
		for(int i=0; i<totalSeats; i++)
		{
			int seatNo;
			while (true)
			{
				System.out.print("\nEnter seat number (1-" + busCapacity + "):");
				String tempSeatNo = sc.nextLine();
				if(!tempSeatNo.matches("\\d+"))
				{
					System.out.println("Enter valid numeric seat number");
					continue;
				}
					seatNo = Integer.parseInt(tempSeatNo);
				
				
				if (seatNo < 1 || seatNo > busCapacity) 
				{
					System.out.println("\nInvalid seat number. Choose between 1 and " + busCapacity);
				}
				else 
				{
					boolean isBooked = false;
					for (int bookedSeat : bookedSeats)
					{
						if (bookedSeat == seatNo) 
						{
							isBooked = true;
							break;
						}
					}
					if (isBooked) 
					{
						System.out.println("Seat already booked. Choose another seat.");
					} 
					else 
						break;
				}
			}
			ticketBooking book = new ticketBooking(seatNo);
			totalCost = applyDiscount(this.busPrices, book.passenerAge);
			bookingDetails.add(book);
			bookedSeats.add(seatNo);
			bill = bill+totalCost;
		}
		this.billing(true);
		
	}
	
	// Applies discounts based on passenger age
	int applyDiscount(int price, int age) 
	{
        double discount = 0;
        if (age >= 60) 
		{
            discount = 0.20; // 20% discount for senior citizens
        } 
		else if (age < 12) 
		{
            discount = 0.30; // 30% discount for children
        }
        return (int) (price * (1 - discount));
    }
	
	// Displays booking details for this bus
	void displayBooking()
	{  
		System.out.println("==============================");
        System.out.println("      BOOKING DETAILS");
        System.out.println("==============================");
		System.out.print(this.busNo + " : "+ this.from + " to " + this.to);
		System.out.println();//extra line
		if(bookingDetails.isEmpty())
		{
			System.out.println("No data available.");
		}
		else
		{
			int passengerNo = 0; int counter = 1;
			while(true)
			{
				for(ticketBooking bookingDetail : bookingDetails )
				{
					if(bookingDetail.passengerSeat == counter)
					{
						passengerNo++;
						bookingDetail.display(passengerNo);
					}
				}
				if(this.busCapacity==counter)
				{
					break;
				}
				counter++;
			}
		}
		System.out.println("==============================");
	}
	
	// Cancels a booking based on passenger name
	void cancelBooking()
	{
		boolean b=true;		
		if(this.bookingDetails.isEmpty())
		{
			System.out.println("You have not booked any ticket.");
			return;
		}
		sc.nextLine();
		System.out.println("Enter passener name whose booking you want to cancel ");
		String name = sc.nextLine();
		for(ticketBooking bookingDetail : this.bookingDetails)
		{
			if(bookingDetail.checkName(name))
			{
				deleteBooking(bookingDetail);
				bookedSeats.remove((Integer)bookingDetail.passengerSeat);
				b=false;
				
				double originalPrice = this.busPrices;
				double gstAmount = originalPrice * 0.05; 
				double totalPaid = originalPrice + gstAmount;
				
				double cancellationCharge = originalPrice * 0.10;
				double refundAmount = (originalPrice - cancellationCharge) + gstAmount;

				System.out.println("Cancellation charge (10% of base fare): ₹" + cancellationCharge);
				System.out.println("GST on ticket: ₹" + gstAmount);
				System.out.println("Refund amount (after GST adjustment): ₹" + refundAmount);

				bill = bill - refundAmount;
				return;
			}
		}	
		
		if(b)
		{
			System.out.println("There is no booking by named "+name+" .");
		}
	}
	
	// Removes the booking from the bookingDetails list
	void deleteBooking(ticketBooking booking)
	{
		bookingDetails.remove(booking);
		System.out.println("Booking has been canceled of "+booking.passenerName+" .");
	}
	
	// Calculates GST (5%) on the given total price
	double applyGST(int totalPrice) 
	{
		double gstRate = 0.05; // 5% GST
		return totalPrice * gstRate;
	}
	
	// Generates and displays the bill
	void billing(boolean payment)
	{
		System.out.println("==============================");
        System.out.println("            BILL");
        System.out.println("==============================");

        if (bookingDetails.isEmpty())
		{
            System.out.println("No bookings to display.");
            System.out.println("==============================");
            return;
        }

        int totalBill = 0;
        int passengerNo = 0;
        int counter = 1;

        while(true)
        {
            for (ticketBooking bookingDetail : bookingDetails)
            {
                if(bookingDetail.passengerSeat == counter)
                {
                    passengerNo++;
                    int originalPrice = this.busPrices;
                    int discountedPrice = applyDiscount(originalPrice, bookingDetail.passenerAge);
                    int discountAmount = originalPrice - discountedPrice;

                    System.out.println("Passenger No: " + passengerNo);
                    System.out.println("Bus No: " + this.busNo + " (" + this.from + " to " + this.to + ")");
                    System.out.println("Seat No: " + bookingDetail.passengerSeat);
                    System.out.println("Passenger Name: " + bookingDetail.passenerName);
                    System.out.println("Passenger Age: " + bookingDetail.passenerAge);
                    System.out.println("Original Price: " + originalPrice);
                    System.out.println("Discount: " + discountAmount);
                    System.out.println("Discounted Price: " + discountedPrice);
                    System.out.println("------------------------------");
                    totalBill += discountedPrice;
                }
            }
            if(this.busCapacity==counter)
            {
                break;
            }
            counter++;
        }
		double gstAmount = applyGST(totalBill);
		double finalAmount = totalBill + gstAmount;

		System.out.println("Total Amount Before GST: " + totalBill);
		System.out.println("GST (5%): " + gstAmount);
		System.out.println("Final Amount After GST: " + finalAmount);
		System.out.println("==============================");
		
		if(payment)
			processPayment(finalAmount);
    }
	
	// Handles payment processing based on user choice
	void processPayment(double amount)
	{
        System.out.println("Choose Payment Method: ");
        System.out.println("1. Cash");
        System.out.println("2. Card");
        System.out.println("3. UPI");
        System.out.print("Enter your choice: ");
        
        int choice = Integer.parseInt(sc.nextLine());

        switch (choice) {
            case 1:
                System.out.println("Payment of ₹" + amount + " received via Cash. Thank you!");
                break;
            case 2:
                String cardNumber;
				while (true) 
				{
					System.out.print("Enter your 12-digit Card Number: ");
					cardNumber = sc.nextLine();
					
					if (cardNumber.matches("\\d{12}")) {
						System.out.println("Processing card payment...");
						System.out.println("Payment of ₹" + amount + " successful via Card. Thank you!");
						break;
					} else {
						System.out.println("Invalid card number! Please enter a 12-digit number.");
					}
				}
				break;
            case 3:
                String upiId;
				while (true)
				{
					System.out.print("Enter your UPI ID (e.g., example@upi): ");
					upiId = sc.nextLine().trim();

					// Basic UPI ID validation without regex
					if (upiId.contains("@") && upiId.indexOf('@') != 0 && upiId.indexOf('@') != upiId.length() - 1) 
					{
						System.out.println("Processing UPI payment...");
						System.out.println("Payment of ₹" + amount + " successful via UPI. Thank you!");
						break;
					}
					else 
						System.out.println("Invalid UPI ID! Enter a valid format (e.g., example@upi).");
				}
				break;
            default:
                System.out.println("Invalid choice. Please try again.");
                processPayment(amount); // Retry payment
        }
    }
}

class ticketBooking 
{
	String passenerName;
	int passenerAge; int passengerSeat;
	Scanner sc = new Scanner(System.in);
	
    // Constructor to initialize ticket booking details
	ticketBooking(int id)
	{
		System.out.println("Enter data of passener.");
		
		while(true)
		{
			System.out.print("Enter passenger name : ");
			this.passenerName = sc.nextLine();
			boolean isValid = true;
			for(int i=0; i<passenerName.length();i++)
			{
				char ch = this.passenerName.charAt(i);
				if (!Character.isLetter(ch)) 
				{ 
					isValid = false; 
					break; 
				}
			}
			if(isValid)
				break;
			else 
				System.out.println("Invalid name. Please enter alphabets only");
		}
	
		while(true)
		{
			System.out.print("Enter passenger age : ");
			String tempAge = sc.nextLine();
			if(!tempAge.matches("\\d+"))
			{
				System.out.println("Enter valid numeric age number");
				continue;
			}
			this.passenerAge = Integer.parseInt(tempAge); 
			break;
		}
			
		this.passengerSeat = id;
		System.out.println("Booking successful for " + this.passenerName + "." );
	}
	
    // Displays the ticket booking details
	void display(int passengerNo)
	{
		System.out.println("Passenger No : " + passengerNo);
		System.out.println("Passenger Name : " + this.passenerName);
		System.out.println("Passenger Age : " + this.passenerAge);
		System.out.println("Passenger Seat : " + this.passengerSeat);
		System.out.println("==============================");
		System.out.println();//extra line
	}
	
    // Checks if the given name matches the passenger's name
	boolean checkName(String name)
	{
		return this.passenerName.equals(name);
	}
}

class Main
{
	static Scanner sc = new Scanner(System.in);
    static Random random = new Random();
	public static void main(String args[])
	{
		// Create an array of busManagement objects to represent different buses
		busManagement bus[] =
		{
			new busManagement(1,"ahmedabad","baroda",20,150,"06:00 AM", "08:00 AM"),
			new busManagement(2,"ahmedabad","rajkot",15,230,"09:00 AM", "12:00 PM"),
			new busManagement(3,"surat","ahmedabad",19,300, "07:30 AM", "11:30 AM"),
			new busManagement(4,"gandhinagar","ahmedabad",25,40, "08:00 AM", "09:30 AM"),
			new busManagement(5,"ahmedabad","giftcity",20,28, "05:45 PM", "06:15 PM"),
		};
		
		System.out.println("\n====== WELCOME TO BUS TICKET RESERVATION SYSTEM ======");
		login();// Call the login method to authenticate the user
		String choice = ""; // Variable to store user's main menu choice
		String choice2 = ""; // Variable to store user's bus details choice
		String choice3 = ""; // Variable to store user's booking choice
		String choice4 = ""; // Variable to store user's view booking choice
		String choice5 = ""; // Variable to store user's cancellation choice
		boolean b=true; // Loop control variable
		
		while(b) // Main menu loop
		{
			System.out.println();//extra line
			System.out.println("\n====================== BUS TICKET RESERVATION SYSTEM ======================");
			System.out.println("\n1. Check bus details - Check available buses, routes, timings, and ticket prices. ");
			System.out.println("2. Search buses by time - Search bus details as per timings");
			System.out.println("3. Book ticket - Reserve a seat on a bus of your choice.");
			System.out.println("4. View booking - See details of your confirmed tickets.");
			System.out.println("5. Cancel booking - Cancel your ticket and receive a refund with cancellation charge.");
			System.out.println("6. View bill - View total amount of bill .");
			System.out.println("7. Exit - Close the reservation system.");
			
			while(true) // Input validation loop for main menu choice
			{
				
				System.out.print("\nEnter choice: ");
				choice=sc.nextLine();
				if(choice.matches("\\d+"))
				{
					break;
				}
					System.out.println("Enter valid numeric choice number");
					continue;
			}
			
			switch(choice) // Switch statement to handle user's main menu choice
			{
				case "1": // Check bus details
					choice2=bus[0].yourChoice();
					switch(choice2)
					{
						case "1":
							bus[0].busDetails();break;
						case "2": 
							bus[1].busDetails();break;
						case "3":
							bus[2].busDetails();break;
						case "4":
							bus[3].busDetails();break;
						case "5":
							bus[4].busDetails();break;
						default: 
							System.out.println("Invalid option. Please enter a number between 1 and 5... ");break;
					}break;
				
				case "2": // Search buses by time
					sc.nextLine();
					String time = bus[0].checkTime();
					boolean found = false;
					for (busManagement bm : bus) 
					{               
						if (bm.departureTime.equals(time)) 
						{
							bm.busDetails();
							found = true;
						}
					}
					if (!found) 
					{
						System.out.println("No buses available at the given time.");
					}
                    break;
				
				case "3": // Book ticket	
					if(!choice2.isEmpty())
					{
						while(true)
						{
							System.out.print("\nDo you want to continue with bus details's choice (yes/no) :");
							String ans = sc.nextLine();
							if((ans.equalsIgnoreCase("yes")))
							{
								choice3 = choice2;
								break;
							}
							else if((ans.equalsIgnoreCase("no")))
							{
								choice3 = bus[0].yourChoice();
								break;
							}
							else
								System.out.println("\nEnter ans in only yes or no");
						}
					}
					else
					{
						choice3=bus[0].yourChoice();
						System.out.println();//extra line
					}
					switch(choice3)
					{
						case "1": 
							System.out.println("\n ahmedabad to baroda booking ");
							bus[0].booking();break;
						case "2": 
							System.out.println("\n ahmedabad to rajkot booking ");
							bus[1].booking();break;
						case "3": 
							System.out.println("\n surat to ahmedabad booking");
							bus[2].booking();break;
						case "4":
							System.out.println("\n gandhinagar to ahmedabad booking");
							bus[3].booking();break;
						case "5":
							System.out.println("\n ahmedabad to giftcity booking");
							bus[4].booking();break;
						default:
							System.out.println("Invalid option. Please enter a number between 1 and 5... ");break;
					}
					break;
				
				case "4": // View booking
					if(!choice2.isEmpty())
					{
						while(true)
						{
							System.out.print("\nDo you want to continue with bus details's choice (yes/no) :");
							String ans = sc.nextLine();
							if((ans.substring(0).equalsIgnoreCase("yes"))||(ans.substring(0).equalsIgnoreCase("no")))
							{
								choice4 = choice2;
								break;
							}
							else
								System.out.println("\nEnter ans in only yes or no");
						}
					}
					
					else if(!choice3.isEmpty())
					{
						while(true)
						{
							System.out.print("\nDo you want to continue with book ticket's choice (yes/no) :");
							String ans = sc.nextLine();
							if((ans.substring(0).equalsIgnoreCase("yes")))
							{
								choice4 = choice2;
								break;
							}
							else if((ans.equalsIgnoreCase("no"))) 	
							{
								choice4 = bus[0].yourChoice();
								break;
							}
							else
								System.out.println("\nEnter ans in only yes or no");
						}
					}
					
					else
					{
						choice4=bus[0].yourChoice();
						System.out.println();//extra line
					}
					
					switch(choice4)
					{
						case "1":
							bus[0].displayBooking();break;
						case "2":
							bus[1].displayBooking();break;
						case "3":
							bus[2].displayBooking();break;
						case "4":
							bus[3].displayBooking();break;
						case "5":
							bus[4].displayBooking();break;
						default:
							System.out.println("Invalid option. Please enter a number between 1 and 5... ");break;
					}break;
				
				case "5": // Cancel booking
					System.out.print("enter bus no that booked : ");
					choice5=sc.nextLine();
					switch(choice5)
					{
						case "1":
							bus[0].cancelBooking();break;
						case "2":
							bus[1].cancelBooking();break;
						case "3":
							bus[2].cancelBooking();break;
						case "4":
							bus[3].cancelBooking();break;
						case "5":
							bus[4].cancelBooking();break;
						default:
							System.out.println("Invalid option. Please enter a number between 1 and 5... ");break;
					}break;
					
				case "6": // View bill
					for (busManagement bm : bus) 
					{
                        if (!bm.bookingDetails.isEmpty()) 
						{
                            bm.billing(false); 
						}
                    }
                    if(bus[0].bookingDetails.isEmpty() && bus[1].bookingDetails.isEmpty() && bus[2].bookingDetails.isEmpty() && bus[3].bookingDetails.isEmpty() && bus[4].bookingDetails.isEmpty())
                        System.out.println("No booking available for bill.");
                    break;
				
				case "7": // Exit
					System.out.println("Thank you for using the system!");b=false;break;
					
				default: // Invalid option
					System.out.println("Invalid option. Please enter a number between 1 and 7...");
					break;
			}
		}
	}
	
	// Method to handle user login
	static void login() 
	{
		while(true)
		{
			System.out.print("\nEnter your 10-digit mobile number: ");
			String mobileNumber = sc.nextLine();
			
			if (!isValidMobileNumber(mobileNumber)) 
			{
				System.out.println("\nInvalid mobile number. Please enter a 10-digit number.");
				continue;
			}
			int otp = generateOTP();
			System.out.println("\nYour OTP is: " + otp);
			System.out.print("\nEnter OTP: ");
			int enteredOTP = sc.nextInt();
			if (enteredOTP == otp) 
				System.out.println("\nLogin successful!");
			else 
			{
				System.out.println("\nIncorrect OTP. Try again.");
				sc.nextLine();
				continue;
			}
			break;
		}
	}

	// Method to validate a mobile number (checks if it's 10 digits)
    static boolean isValidMobileNumber(String number) 
	{
        return number.length() == 10 && number.matches("\\d{10}");
    }

	// Method to generate a random 6-digit OTP
    static int generateOTP() 
	{
        return 100000 + random.nextInt(900000);
    }
}