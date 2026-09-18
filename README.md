
# Parking Space Manager

A console-based parking lot management system built in Java, built as an
OOP/interfaces exam project. See `DESIGN.md` for the UML diagram and
design rationale, and `TESTING_AND_REFLECTION.md` for Parts E and F.

## Part A — Problem Definition

**Scenario.** Small parking lots (e.g. a single-building lot with a
fixed number of spaces) are often managed manually — an attendant
tracks who's parked where on paper or from memory, calculates fees by
hand, and has no reliable record of past activity. This leads to
mistakes (double-booking a space, forgetting entry times,
miscalculating fees) and no way to review history later. This
application gives a single attendant a simple console tool to check a
vehicle in, assign it a space automatically, calculate the fee based on
how long it stayed and what kind of vehicle it is, take payment, and log
the transaction — replacing the manual/paper process with something
fast and consistent.

**Target users.** A parking lot attendant or small business owner
operating a single lot with a fixed, modest number of spaces, who wants
a lightweight tool at a terminal/counter rather than a full commercial
parking-management system.

**Core pain points.**
- Manually tracking which spaces are free/occupied is error-prone
  (double-booking, losing track of who's where).
- Calculating parking fees by hand (entry time, exit time, hourly rate,
  vehicle type) is slow and mistake-prone.
- No persistent record of past vehicles/payments to refer back to later.

**Core use cases.**
1. **Park a vehicle** — attendant enters plate, brand/model, and
   vehicle type; the system assigns an available space and records the
   entry time.
2. **View available spots** — attendant checks current occupancy at a
   glance.
3. **Exit a vehicle & take payment** — attendant enters a plate; the
   system calculates the fee from elapsed time and vehicle type,
   processes payment (credit/debit card), and frees the space.
4. **View parking history** — attendant can see where past transactions
   have been logged.

**Non-functional constraints / assumptions.**
- Single attendant, single terminal session — no concurrent access or
  multi-user locking is handled.
- Fixed lot size, set at startup (currently hardcoded to 15 spaces) —
  not dynamically resizable at runtime.
- Persistence is a simple append-only text log (`parking_history.txt`)
  for historical record only — active parking state (who's currently
  parked where) is **not** reloaded on restart; it resets each run.
  This is a deliberate simplification for the scope of this project,
  not an oversight.
- Payment is simulated: card numbers are format-validated (16 digits)
  but no real payment gateway is contacted.
- Pricing is a base hourly rate ($5.00/hr) adjusted by a per-vehicle-type
  multiplier (motorcycle 0.5x, car 1.0x, truck 1.5x), with a minimum
  charge of one hour.

## Build & Run

Requires **JDK 17 or later**. From the project root:

```bash
javac -d out $(find src/main/java -name "*.java")
java -cp out com.example.parkingspacemanager.ParkingSpaceApplicationMain
```

The app runs as an interactive menu loop until you choose option 6 (Exit).

## Quick Start — Example Sessions

### Session 1 — parking a car

```
================================
     PARKING SPACE MANAGER
================================

------------- MENU -------------
1. Park Vehicle
2. View Available Spots
3. Exit Vehicle & Pay
4. View Parking History
5. Help
6. Exit
--------------------------------
Choose an option: 1

--- Park Vehicle ---
License plate: abc123

Brand/model: Toyota Corolla

Vehicle Type:
1. Car
2. Truck
3. Motorcycle
Choose vehicle type: 1

Vehicle parked successfully!
Parking space: 1
Entry time: 2026-09-18T10:15:02.123
```

### Session 2 — viewing available spots

```
Choose an option: 2

--- Parking Spaces ---
Space 1: OCCUPIED - ABC123
Space 2: AVAILABLE
Space 3: AVAILABLE
...
Available spaces: 14
Occupied spaces: 1
```

### Session 3 — exiting and paying (note the vehicle-type rate multiplier)

```
Choose an option: 3

--- Exit Vehicle & Pay ---
License plate: abc123

Vehicle: ABC123
Parking space: 1
Entry time: 2026-09-18T10:15:02.123
Exit time: 2026-09-18T11:20:44.987
Parking time: 2.0 hour(s)

Payment Method:
1. Credit Card
2. Debit Card
Choose payment method: 1
Card number (16 digits): 1234567812345678

Payment successful! Amount: $10.00
Vehicle exited successfully.
Parking space 1 is now available.
```
(2 hours × $5.00/hr × 1.0 car multiplier = $10.00. The same session
with a truck instead of a car would charge $15.00; a motorcycle, $5.00 —
this is the `Vehicle.getRateMultiplier()` polymorphism in action.)

### Session 4 — error handling (no silent failures)

```
Choose an option: 1

--- Park Vehicle ---
License plate:
Error: License plate cannot be empty.
```

```
Choose an option: 3

--- Exit Vehicle & Pay ---
License plate: zzz999
Error: Vehicle not found.
```

```
Choose an option: 9
Invalid option. Please choose 1 to 6.
```

### Session 5 — viewing parking history

```
Choose an option: 4

--- Parking History ---
Parking history is stored in:
parking_history.txt
```

## Command Reference

| Menu Option | What it does |
|---|---|
| `1` Park Vehicle | Prompts for plate, brand/model, and vehicle type; assigns the first available space. |
| `2` View Available Spots | Lists every space and its status (available/occupied + plate). |
| `3` Exit Vehicle & Pay | Prompts for plate; computes fee from duration × vehicle rate multiplier; prompts for payment method; frees the space on success. |
| `4` View Parking History | Shows the name of the log file where all transactions are recorded. |
| `5` Help | Displays a description of every menu option. |
| `6` Exit | Closes the application. |

**Known vehicle types:** Car (1.0x rate), Truck (1.5x rate), Motorcycle (0.5x rate).
**Known payment methods:** Credit Card, Debit Card (both require a 16-digit card number).

