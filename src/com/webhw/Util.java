package com.webhw;

import java.util.Random;

class Util {
    // Returns a random number from the range given
    static int getRandomNumber(int bottom_limit, int upper_limit) {
        Random random = new Random();
        return random.nextInt(upper_limit - bottom_limit) + bottom_limit;
    }

}
