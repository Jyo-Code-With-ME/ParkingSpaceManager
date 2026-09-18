# Part E — Testing Strategy (≤200 words)

Testing for this project was done through structured **manual CLI
walkthroughs** covering both happy paths and error paths, since the
core logic (`ParkingLot`, `Payment`, `Vehicle`) is exercised through a
`Scanner`-driven menu loop rather than through injectable, isolated
units.

**Happy paths tested:** parking a Car, a Truck, and a Motorcycle in
turn and confirming each is assigned the next available space with a
correct entry time; viewing available spots and confirming counts match
reality; exiting a vehicle and confirming the fee reflects both the
elapsed time *and* the vehicle's rate multiplier (e.g. a 2-hour Car
session charges $10.00, while the same duration for a Truck charges
$15.00 and a Motorcycle charges $5.00 — proving the polymorphic
`getRateMultiplier()` dispatch actually changes output); confirming a
successful payment removes the vehicle and frees its space; confirming
`parking_history.txt` receives a new line after each park/exit.

**Error paths tested:** blank license plate, blank brand/model, an
already-parked plate, an unknown vehicle-type/payment-type menu choice,
exiting a plate that was never parked, an invalid main-menu option, and
an invalid (non-16-digit) card number correctly failing `validate()`
and blocking the exit.

With more time, this would be restructured so `ParkingLot`, `Payment`,
and `Vehicle` could be tested with JUnit independent of `Scanner`
input, by separating input-reading from business logic in
`ParkingManager`.

---

# Part F — Reflection (200–400 words)

**Biggest design trade-off.** The largest trade-off was choosing a
simple append-only log file (`FileManager`) over reloading full
parking-lot state on restart. This keeps persistence trivial — one
static method, no serialization format to design or maintain — but it
means the "active" state of the lot (who's parked where) is lost every
time the program restarts; only the historical record survives. For a
single continuous shift at a small lot that's an acceptable trade, but
it would not hold up if the attendant needed to close and reopen the
application mid-shift without losing track of parked vehicles.

**Where I deliberately avoided inheritance, and why it helped.**
`ParkingLot` and `ParkingManager` are plain, non-hierarchical classes
that *hold* their collaborators (`List<ParkingSpot>`,
`ParkingLot`) rather than extend anything. Early in this project it
would have been tempting to create some shared "AbstractManager" base
class between `ParkingManager` and a hypothetical future manager class,
but there is no real shared *behavior* between them — only a vague,
coincidental similarity ("both coordinate other objects"). Keeping
`ParkingLot` as a single concrete, `final` class kept it simple to
reason about and safe to lock down, since nothing in this domain needed
it to be extended.

**What I'd refactor with more time.** First, I'd separate
`ParkingManager`'s input-reading (`Scanner` prompts) from its business
logic, so the actual park/exit/fee-calculation logic could be unit
tested without simulating console input — right now testing is
necessarily manual because the two are interleaved in the same methods.
Second, I'd move active-ticket state into the persisted file (not just
history), so the application could resume correctly after a restart
without losing track of who's currently parked. Third, I'd replace
`double` with `BigDecimal` for all monetary calculations in `Payment` —
`double` works for a class exam but is not the correct choice for real
currency arithmetic, since binary floating point cannot represent every
decimal fraction exactly.
