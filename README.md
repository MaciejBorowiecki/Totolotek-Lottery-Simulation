# Totolotek Lottery System Implementation

## Project Overview

This project implements a complete Polish lottery system called "Totolotek" that handles ticket sales, random drawings, prize calculations, and winner payouts. The system follows object-oriented principles and includes comprehensive financial tracking with proper tax handling and government subsidy management.

## System Architecture

### Core Components

**Central Office**
- Manages all financial operations and drawing ceremonies
- Conducts regular drawings of 6 distinct numbers from 1-49 range
- Maintains prize pools with rollover mechanism for unclaimed top prizes
- Tracks historical drawing results with complete financial transparency
- Handles government subsidies when prize funds are insufficient
- Unique sequential drawing numbers starting from 1

**Collection Points**
- 10 uniquely numbered ticket sales locations
- Support both form-based and quick-pick ticket sales
- Validate winning tickets and process prize claims
- Real-time financial synchronization with central office
- Maintain database of all sold tickets for verification

**Ticket Management System**
- Unique ticket identifiers with format: `ticketNumber-collectorNumber-randomMarker-checksum`
- 9-digit random marker and checksum (sum of digits modulo 100)
- Support for multi-bet (1-8 bets) and multi-drawing (up to 10) tickets
- Automated price calculation (3.00 PLN per bet per drawing)
- Secure validation system to prevent fraud and double redemption

### Financial System

**Revenue Distribution**
- 0.60 PLN tax per bet directed to government budget
- 2.40 PLN remaining for prize pool and operational costs
- 51% of net revenue allocated to prize fund

**Prize Structure**
- **Tier I** (6 numbers): 44% of prize pool, minimum 2,000,000 PLN guarantee
- **Tier II** (5 numbers): 8% of prize pool  
- **Tier III** (4 numbers): Residual allocation, minimum 36.00 PLN per winner
- **Tier IV** (3 numbers): Fixed 24.00 PLN prize

**Taxation Rules**
- 10% tax on individual prizes ≥ 2,280 PLN
- No tax on smaller prizes
- Only high-value portion taxed in multi-prize tickets
- Proper rounding down to nearest grosz in all calculations

### Player System

**Implemented Player Types**

*Minimalist Players*
- Purchase single quick-pick tickets for immediate drawings only
- Loyal to specific collection points
- Simple betting strategy

*Random Players*
- Variable ticket purchases (1-100 tickets per transaction)
- Random betting patterns across different locations
- Diverse initial funding levels (below 1 million PLN)

*Fixed Numbers Players*
- Consistently bet the same 6 lucky numbers
- Purchase tickets covering 10 consecutive drawings
- Buy new tickets only after all previous drawings complete
- Systematic collection point rotation among favorites

*Fixed Form Players*
- Use personalized betting form patterns
- Regular purchase intervals at predefined cycles
- Rotate through multiple favorite collection points
- Maintain consistent betting strategy

## Key Features

### Drawing Management
- Sequential drawing numbers with proper formatting
- 6 unique numbers selected from 1-49 range
- Results displayed with right-aligned numbers for readability
- Public access to winning numbers and prize information
- Complete historical record maintenance

### Betting Slip Processing
- Forms with 8 numbered betting fields (1-8)
- Each field contains 49 numbered boxes plus "cancel" option
- Automatic exclusion of invalid fields (not exactly 6 numbers)
- Support for multi-drawing selections (1-10 drawings)
- Intelligent form interpretation and validation

### Ticket Validation & Security
- Checksum verification for ticket authenticity
- Collection point origin validation
- Prevention of duplicate prize claims
- Automatic voiding of partially used tickets
- Secure identifier generation with random markers

## Implementation Details

### Financial Integrity
- Uses long/BigInteger for all monetary calculations
- Avoids floating-point precision issues
- Proper grosz-level rounding throughout system
- Comprehensive audit trails for all transactions
- Automatic subsidy handling when funds insufficient

### Object-Oriented Design
- Proper encapsulation and separation of concerns
- Extensible player type system with easy addition of new types
- Flexible betting slip and ticket hierarchy
- Collection-based data management with proper equals/hashCode implementations
- Interface-driven design for easy testing and extension

### Government Budget Integration
- Tracks total tax collections from ticket sales and high-value prizes
- Provides automatic subsidies when central office lacks prize funds
- Maintains separate accounting for tax revenue and subsidy payments
- Assumed to have unlimited resources for system stability

## Operational Results

The system successfully conducted 20 complete drawing cycles with:
- Full participation from 200 players of each type
- Automated ticket purchasing before each drawing
- Immediate prize claiming for completed tickets
- Comprehensive financial tracking and reporting

### Final Output Capabilities
- Complete drawing history with financial breakdowns
- Total government tax revenue tracking
- Government subsidy utilization reporting
- Individual collection point performance metrics
- Central office financial status reporting

## Technical Implementation

### Testing Coverage
- JUnit tests for betting slip validation and error detection
- Ticket generation and identifier verification
- Prize calculation algorithms with various scenarios
- Financial transaction verification and tax calculations
- Edge case handling for all player types
- Rollover mechanism and subsidy testing

The implementation demonstrates robust handling of complex lottery operations while maintaining financial accuracy and system integrity across all components, providing a solid foundation for real-world lottery management.
