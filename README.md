# NetworkEmulator

Network/file-encryption emulator (still in build phase).


## Description/Overview

NetworkEmulator is a file/networking emulator. The program starts by asking if the user to select between an independent format (using Javax) or command-line based. If the indepentent format is selected, then a new window will be opened for it. If the command-line version is selected, then the program will continue on the command line.


### Networking

Computers interact via command prompt and a few different programs.
Supported (and in-progress) methods include:
- WEP
- WPA
- WPA2
- WPA3


### File Encryption

Supports file encryption and file decryption from an existing computer.
Supported (and in-progress) methods include:
- RSA
- DSA
- AES
- DES


## Future Updates

As development continues
- More encryption methods and networking protocols will be added, aiming to get closer to current standards.
- The format selection will be able to be selected as a command line argument
  - For Independent format
    > java src\Emulator\NetworkEmulator -a
  - For command-line format
    > java src\Emulator\NetworkEmulator -c
- Text showoing commands will be removed in place of 'help'
- Program will be cleaned up to improve performance


#### Notice:

The program (pseudo-application) contains various JetBrains annotations such as "@NotNull" and "@Contract". Probably will remove them after what is currently in is functional.
