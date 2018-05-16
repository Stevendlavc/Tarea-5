package Threads;

// Buffer interface specifies methods called by Producer and Consumer.

import Domain.Ball;

public interface Buffer
{

    public void set(Ball object);  // place value into Buffer

    public Ball get();              // return value from Buffer
}
