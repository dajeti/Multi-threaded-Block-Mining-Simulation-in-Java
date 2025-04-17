# Multi-threaded Block Mining Simulation in Java

This project is a Java-based multi-threaded simulation of blockchain block mining. It illustrates essential blockchain concepts such as proof-of-work, hashing, and concurrency. Blocks are mined concurrently using multiple threads to efficiently achieve a specified difficulty level.

## Features

- **SHA-256 Hashing**: Ensures data integrity and uniqueness of each mined block.
- **Proof-of-Work Simulation**: Requires hashes to have a configurable number of leading zero bits.
- **Concurrency**: Uses multi-threading to parallelize mining across all available CPU cores.
- **Dynamic Nonce Generation**: Generates random nonces to find valid hashes meeting the difficulty criteria.

## Code Overview

### Core Components

#### Main Class (`BlockchainMine`):

- **Metadata**:
  - `PREVIOUS_HASH`: Hash of the previous block (empty in this simulation).
  - `MINER_PSEUDONYM`: Name of the miner.
  - `DIFFICULTY`: Number of leading zero bits required in the hash.

- **Key Methods**:
  - `main(String[] args)`: Initiates mining across multiple threads and outputs results upon successful mining.
  - `meetsDifficulty(byte[] hash, int difficulty)`: Validates if a hash meets the required difficulty.
  - `bytesToHex(byte[] bytes)`: Converts hash bytes into a hexadecimal string.

#### Miner Task Class (`MinerTask`):

- Performs the mining operation on individual threads, generating random nonces until the hash meets the difficulty requirement.

## Prerequisites

### Tools Required
- **Java Development Kit (JDK)**: Version 8 or later.
- **IDE or Text Editor**: IntelliJ, Eclipse, or any Java-compatible editor.

### Knowledge Required
- Basic Java programming experience.
- Understanding of blockchain fundamentals, especially:
  - Proof-of-Work (PoW)
  - Hashing algorithms (SHA-256)
  - Concurrent programming (threads)

## How to Run

### Clone the Repository:

```bash
git clone <repository_url>
cd <repository_directory>
```

### Compile the Program:

```bash
javac <your-user>.java
```

### Run the Program:

```bash
java <your-user>
```

### Example Output:

```
Starting mining with difficulty 26
Block mined!
Block: 0000003c29a2...basedminerrr-5421582637958412364
Time taken: 12.34 seconds
```

## How It Works

### Mining Process:

1. Concatenates `PREVIOUS_HASH`, `MINER_PSEUDONYM`, and a randomly generated nonce.
2. Computes SHA-256 hash of the combined data.
3. Validates if the hash meets the specified difficulty (leading zero bits).
4. Repeats steps 1–3 across multiple threads until a valid hash is found.

### Concurrency:

- The program leverages all CPU cores available, significantly speeding up mining by parallelizing nonce generation and hash validation.

