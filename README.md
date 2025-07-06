# 🚌 Bus Ticket Reservation System

A **Java-based console application** for booking bus tickets, checking seat availability, managing passengers, billing, and cancellations — all inside a terminal.

---

## 🚀 Features

- 🔍 View available buses by route or departure time  
- 🧾 Detailed bus info (routes, capacity, fare, timing)  
- 🎫 Book tickets with **auto-discount by age**  
- 💳 Payment via Cash, Card, or UPI  
- ❌ Cancel bookings with proper refund & cancellation charge  
- 🧮 Final bill with GST breakdown  
- 🔐 OTP-based login using mobile number

---

## 🧰 Technologies Used

- Java (`BTR.java`)
- Core Java concepts (OOP, arrays, lists, loops, classes)
- Console-based user interaction (`Scanner`, `Random`)
- No external libraries or database

---

## 💻 How to Run

1. **Clone this repository**
   ```bash
   git clone https://github.com/student-prithvipatel/Bus-Ticket-Reservation.git
   cd Bus-Ticket-Reservation

2. **Clone this repository**
   ```bash
   javac BTR.java

3. **Clone this repository**
   ```bash
   java BTR

   ---

## 📝 Usage Guide

1. Enter your mobile number → receive OTP → log in.
2. Choose from the main menu options:
   - `1` View bus details
   - `2` Search buses by time
   - `3` Book a ticket
   - `4` View your bookings
   - `5` Cancel a booking
   - `6` View your bill
   - `7` Exit the system
3. Age-based discounts:
   - 20% for seniors (60+)
   - 30% for children (<12)
4. GST: 5% applied on total amount
5. Cancelation policy:
   - 10% base fare deduction
   - GST preserved in refund

---

## 🛠️ Possible Improvements

- [ ] Add file-based or database persistence for bookings
- [ ] Create an admin panel to manage buses and schedules
- [ ] Implement a GUI (Swing or JavaFX)
- [ ] Generate printable or downloadable tickets (PDF format)
- [ ] User account system with login history
- [ ] Show seat map for visual booking experience

---

## 👤 Author

- **Prithvi Patel**  
  [@student-prithvipatel](https://github.com/student-prithvipatel)

